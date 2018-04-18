package co.id.fifgroup.ssoa.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.exporter.excel.ExcelExporter;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.domain.AssetRegistration;
import co.id.fifgroup.ssoa.domain.AssetRegistrationExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.AssetRegistrationExample.Criteria;
import co.id.fifgroup.ssoa.dto.AssetDTO;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.service.AssetRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import co.id.fifgroup.core.constant.ContentType;
import co.id.fifgroup.core.util.DownloadUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;


@VariableResolver(DelegatingVariableResolver.class)
public class AssetRegistrationComposer extends SelectorComposer<Component> {

	private static final long serialVersionUID = 4373064548488803096L;
	
	
	@Wire
    private Combobox lbxSearchBranch;
	@Wire
	private Button btnNew;
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
	@Wire
    private Listbox lbxAssetRegistration;
	@Wire
    private Listbox lbxAssetRegistrationDownload;
	@Wire
    private Textbox txtDocumentNo;
	@Wire
    private Textbox txtBranchName;
	@Wire
    private Textbox txtBranchId;
	@Wire
    private Textbox txtSearchRegistrationNo;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Combobox cmbDownloadAs;
	@Wire
	private Listheader hdrRegistrationNo;
	@Wire
	private Listheader hdrRegistrationDate;
	@Wire
	private Listheader hdrRemarks;
	@Wire
	private Listheader hdrStatus;
	@Wire
	private Listheader hdrLastUpdateBy;
	@Wire
	private Listheader hdrLastUpdateDate;
	@Wire
	private Paging pgListOfValueTop;
	
	private Long branchId;
	private Long bnbBranchId;
	
	@Wire
	private List<AssetRegistrationDTO> listAssetRegistrationDto;
	private List<AssetRegistrationDTO> listAssetDtoForDownload;
	private ListModelList<AssetRegistrationDTO> listModelAssetRegistrationDto;
	
	private static Logger log = LoggerFactory.getLogger(AssetRegistrationComposer.class);
   
    @WireVariable(rewireOnActivate = true)
	private transient AssetRegistrationService assetRegistrationService;
    
    @WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
    
    private FunctionPermission functionPermission;
    @WireVariable("arg")
	private Map<String, Object> arg;

    @Autowired
	private DataSource dataSource;
    
    private String orderBy = "REGISTRATION_DATE DESC";
    
	@Override
    public void doAfterCompose(Component comp) throws Exception
    {
    	super.doAfterCompose(comp);
    	init();
    	functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
    	getSelf().setAttribute("win$composer", this, false);
    }
	
	private void sortAction(){
		hdrRegistrationNo.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "AR.REGRISTRATION_NO"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrRegistrationDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "AR.REGRISTRATION_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrRemarks.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(case when AR.REMARKS is null then '-' else T.REMARKS end )"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStatus.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "AR.STATUS_CODE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLastUpdateBy.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "USER_NAME"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLastUpdateDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "AR.LAST_UPDATE_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
	}
	
	private void init() {
		listModelAssetRegistrationDto = new ListModelList<AssetRegistrationDTO>();
		lbxAssetRegistration.setModel(listModelAssetRegistrationDto);
		loadBranch(bnbBranch);
		Branch branch1 = (Branch) assetRegistrationService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		bnbBranchId= branch1.getBranchId();
		System.out.println("bnbBranchId" + bnbBranchId);
		Branch branch = (Branch) assetRegistrationService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(securityServiceImpl.getSecurityProfile().getBranchId().longValue() != -1)
		{
			if(branch!=null && branch.getBranchCode()!=null){
				KeyValue keyValue = new KeyValue();
				keyValue.setKey(branch.getBranchId());
				keyValue.setValue(branch.getBranchName());
				keyValue.setDescription(branch.getBranchCode());
				bnbBranch.setRawValue(keyValue);
			}
			bnbBranch.setDisabled(true);
		}else{
			btnNew.setDisabled(false);
		}
		
		try{
			bnbBranch.addEventListener(Events.ON_FOCUS, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					if(!bnbBranch.getValue().isEmpty()){
						KeyValue keyValue = (KeyValue)bnbBranch.getKeyValue();
						Long  branchIdLogin= securityServiceImpl.getSecurityProfile().getBranchId().longValue();
						System.out.println("bnbBranch.getKeyValue().getValue()" + bnbBranch.getKeyValue().getValue());
						btnNew.setDisabled(true);
						if(branchIdLogin == bnbBranch.getKeyValue().getKey()){
							btnNew.setDisabled(false);
							if(bnbBranch.getKeyValue().getKey().toString() == branchIdLogin.toString() )
							{
								btnNew.setDisabled(false);
							}
							}
						}
					else
					{
						Branch branch = (Branch) assetRegistrationService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
						btnNew.setDisabled(false);
					}
				}
			});
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	
	private void loadBranch(SSOACommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearch());
	}

	class DelegateSearch implements SerializableSearchDelegateDoubleCriteria<Branch> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<Branch> findBySearchCriteria(String searchCriteriaCode,String searchCriteriaName, int limit, int offset) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			
			List<Branch> listBranch = new ArrayList<Branch>();
			listBranch = assetRegistrationService.getBranchByExample(example, limit, offset);

			return listBranch;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return assetRegistrationService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}

    
    @Listen("onDetail= #winAssetRegistration")
	public void onDetail(ForwardEvent event){
		AssetRegistrationDTO assetRegistrationDTO = (AssetRegistrationDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetRegistrationDTO", assetRegistrationDTO);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("action", "detail");
		param.put("Branch", bnbBranch.getKeyValue());
		Executions.createComponents("~./hcms/ssoa/asset_registration_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
    
    
    @Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("Branch", bnbBranch.getKeyValue());
		param.put("action", "addNew");
		Executions.createComponents("~./hcms/ssoa/asset_registration_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
    
    @Listen ("onClick = button#btnFind")
	public void find() {
		String documentNo = txtSearchRegistrationNo.getValue();
		String branch = bnbBranch.getValue();
		if(documentNo == null ||documentNo.isEmpty()&& (branch == null || branch.isEmpty())) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = -8756250972566999901L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
						sortAction();
					}
				}
			});
		} else {
			search();
		}
	}
    
    private AssetRegistrationExample searchCriteria(){
    	AssetRegistrationExample example = new AssetRegistrationExample();
    	Criteria criteria = example.createCriteria();
		if(txtSearchRegistrationNo.getValue() != null && !txtSearchRegistrationNo.getValue().toString().isEmpty()){
			criteria = criteria.andRequestNoLikeInsensitive("%" + txtSearchRegistrationNo.getValue() + "%");
		}
		
		if(bnbBranch.getKeyValue()!=null && bnbBranch.getKeyValue().getKey() != null){
			criteria = criteria.andBranchIdLike(bnbBranch.getKeyValue().getKey().toString());
		}else{
			criteria = criteria.andBranchIdLike("%");
		}
		criteria = criteria.andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId().toString());
		example.setOrderByClause(orderBy);
		return example;
    }
    
    private void search() {
		try {
			
			pgListOfValue.setTotalSize(assetRegistrationService.countByExample(searchCriteria()));
			pgListOfValue.setPageSize(20);
			pgListOfValue.setActivePage(0);
			
			pgListOfValueTop.setTotalSize(pgListOfValue.getTotalSize());
			pgListOfValueTop.setPageSize(20);
			pgListOfValueTop.setActivePage(0);
			
			generateDataAndPaging();
			//generateDataForDownload();
		} catch (Exception e) {
			log.error("error", e);
		}
	}
    
    @Listen("onPaging = #pgListOfValue")
	public void onPaging() {
    	generateDataAndPaging();
	}

	private void generateDataAndPaging() {
		pgListOfValueTop.setActivePage(pgListOfValue.getActivePage());
		List listAssetRegistrationDto = assetRegistrationService.getAssetRegistrationDtoByExample(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
		ListModelList<AssetRegistrationDTO> model = new ListModelList<AssetRegistrationDTO>(listAssetRegistrationDto);
		lbxAssetRegistration.setModel(model);
		lbxAssetRegistration.renderAll();
	}
	
	@Listen("onPaging = #pgListOfValueTop")
	public void onPagingTop() {
		generateDataAndPagingTop();
	}
	
	private void generateDataAndPagingTop() {
		pgListOfValue.setActivePage(pgListOfValueTop.getActivePage());
		List listAssetRegistrationDto = assetRegistrationService.getAssetRegistrationDtoByExample(searchCriteria(),pgListOfValueTop.getPageSize(), pgListOfValueTop.getActivePage() * pgListOfValueTop.getPageSize());
		ListModelList<AssetRegistrationDTO> model = new ListModelList<AssetRegistrationDTO>(listAssetRegistrationDto);
		lbxAssetRegistration.setModel(model);
		lbxAssetRegistration.renderAll();
	}
	
	private void generateDataForDownload(){
		listAssetDtoForDownload = assetRegistrationService.getAssetRegistrationDtoByExample(searchCriteria());
		ListModelList<AssetRegistrationDTO> model2 = new ListModelList<AssetRegistrationDTO>(listAssetDtoForDownload);
		lbxAssetRegistrationDownload.setModel(model2);
		lbxAssetRegistrationDownload.renderAll();
    }
    
	@Listen("a[label='In Process']")
	public void popupSubmit(Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_registration_popup_status.zul", null, null);
		win.doModal();
	}

	@Listen("a[label='Approved']")
	public void popupApproved(Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_registration_popup_status.zul", null, null);
		win.doModal();
	}
	
	@Listen("a[label='Detail']")
	public void detail (Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_registration_add.zul", null, null);
		win.doModal();
	}
    
	
	@Listen("onStatus= #winAssetRegistration")
	public void onStatus(ForwardEvent event){
		AssetRegistrationDTO assetRegistrationDTO = (AssetRegistrationDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("avmTrxId", UUID.fromString(assetRegistrationDTO.getAvmTrxIdString()));
		param.put("companyId", securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/approver_popup.zul",
				getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onClick = #btnDownload")
	public void export() throws Exception {
    	generateDataForDownload();
		Listbox listbox = lbxAssetRegistrationDownload;
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    if(cmbDownloadAs.getValue()!=null && cmbDownloadAs.getValue().equals("XLS")){
	    ExcelExporter exporter = new ExcelExporter();
	    exporter.export(listbox, out);
	    AMedia amedia = new AMedia("Report.xls", "xls", "application/file", out.toByteArray());
	    Filedownload.save(amedia);
	    out.close();
	    }else{
	    PdfExporter exporter = new PdfExporter();
	    exporter.export(listbox, out);
	    AMedia amedia = new AMedia("Report.pdf", "pdf", "application/pdf", out.toByteArray());
	    Filedownload.save(amedia);
	    out.close();
	    }
	}
	
	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getBnbBranchId() {
		return bnbBranchId;
	}

	public void setBnbBranchId(Long bnbBranchId) {
		this.bnbBranchId = bnbBranchId;
	}
	
}

     

