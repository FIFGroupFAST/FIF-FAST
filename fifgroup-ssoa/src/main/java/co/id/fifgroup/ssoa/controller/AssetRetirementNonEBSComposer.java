package co.id.fifgroup.ssoa.controller;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.exporter.excel.ExcelExporter;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.domain.interfacing.RvInterface;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.FIFAppsServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.constants.RetirementType;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.RetirementExample;
import co.id.fifgroup.ssoa.domain.RetirementExample.Criteria;
import co.id.fifgroup.ssoa.dto.AssetDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.service.RetirementNonEBSService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;



@VariableResolver(DelegatingVariableResolver.class)
public class AssetRetirementNonEBSComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 4373064548488803096L;

	@Wire
	private Datebox dtRequestDateFrom;
	@Wire
	private Label lblErrorDate;
	@Wire
	private Long bnbBranchId;
	@Wire
	private Datebox dtRequestDateTo;
	@Wire
	private Textbox txtDocNo;
	@Wire
	private Listbox cbRetirementType;
	@Wire
	private Listbox lstAssetRetirements;
	@Wire
	private Listbox lstAssetRetirementsDownload;
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
	@WireVariable("arg")
	private Map<String, Object> arg;
    @Wire
	private Paging pgListOfValue;
    @Wire
	private Button btnNew;
    @Wire
	private Combobox cmbDownloadAs;
    @Wire
	private Listheader hdrDocNo;
    @Wire
	private Listheader hdrRequestDate;
    @Wire
	private Listheader hdrRetirementType;
    @Wire
	private Listheader hdrPenerima;
    @Wire
	private Listheader hdrNilaiJual;
    @Wire
	private Listheader hdrStatus;
    @Wire
	private Listheader hdrBastStatus;
    @Wire
	private Listheader hdrRVNumber;
    @Wire
	private Listheader hdrLastUpdateBy;
    @Wire
	private Listheader hdrLastUpdateDate;
    @Wire
	private Paging pgListOfValueTop;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient RetirementNonEBSService retirementNonEBSService;
	
	private ListModelList<RetirementDTO> listModelRetirementDto;
	private List<RetirementDTO> listRetirementDto;
	private List<RetirementDTO> listRetirementDtoForDownload;
	private FunctionPermission functionPermission;
    private static Logger log = LoggerFactory.getLogger(AssetRetirementNonEBSComposer.class);
    private String orderBy = "REQUEST_DATE DESC";
    
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		init();
		getSelf().setAttribute("win$composer", this, false);
	}
	
	private void sortAction(){
		hdrDocNo.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "REQUEST_NO"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrRequestDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "REQUEST_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrRetirementType.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "RETIREMENT_TYPE_CODE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrPenerima.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(case when RECIPIENT is null then '-' else RECIPIENT end)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrNilaiJual.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(case when QUOTATION_PRICE is null then 0 else QUOTATION_PRICE end)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStatus.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "STATUS_CODE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrBastStatus.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(case when BAST_STATUS_CODE is null then '-' else BAST_STATUS_CODE end)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrRVNumber.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "RV_NUMBER"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLastUpdateBy.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.LAST_UPDATE_BY"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLastUpdateDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.LAST_UPDATE_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
	}
	
	private void init(){
		setComboBoxList();
		listModelRetirementDto = new ListModelList<RetirementDTO>();
		lstAssetRetirements.setModel(listModelRetirementDto);
		loadBranch(bnbBranch);
		Branch branch1 = (Branch) retirementNonEBSService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		bnbBranchId= branch1.getBranchId();
		System.out.println("bnbBranchId" + bnbBranchId);
		Branch branch = (Branch) retirementNonEBSService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
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
		}
		else
		{
			bnbBranch.setDisabled(false);
			btnNew.setDisabled(false);
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(branch.getBranchId());
			keyValue.setValue(branch.getBranchName());
			keyValue.setDescription(branch.getBranchCode());
			bnbBranch.setRawValue(keyValue);
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
						Branch branch = (Branch) retirementNonEBSService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
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
			listBranch = retirementNonEBSService.getBranchByExample(example, limit, offset);

			return listBranch;
			
			
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return retirementNonEBSService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue key = new KeyValue();
		key.setKey("%");
		key.setValue("%");
		key.setDescription("--Pilih--");
		data.add(key);
		data.addAll(retirementNonEBSService.getLookupKeyValues(RetirementType.CODE.getCode(), null));
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cbRetirementType.setModel(model);
		cbRetirementType.renderAll();
		cbRetirementType.setSelectedIndex(0);
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("action", "addNew");
		param.put("Branch", bnbBranch.getKeyValue());
		Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
   
	@Listen ("onClick = button#btnFind")
	public void find() {
		String docNo = txtDocNo.getValue();
		String branch = bnbBranch.getValue();
		Date requestDateFrom = dtRequestDateFrom.getValue();
		Date requestDateTo = dtRequestDateTo.getValue();
		int retirementType = cbRetirementType.getSelectedIndex();
		if((docNo == null || docNo.isEmpty() || docNo.equals("%%")) && requestDateFrom == null && requestDateTo == null && retirementType == 0 && (branch == null || branch.isEmpty())) {
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
    
    private RetirementExample searchCriteria(){
    	RetirementExample example = new RetirementExample();
    	Criteria criteria = example.createCriteria();
		if(txtDocNo.getValue() != null && !txtDocNo.getValue().toString().isEmpty()){
			criteria = criteria.andRequestNoLikeInsensitive("%"+ txtDocNo.getValue() +"%");
		}
		
		if(dtRequestDateFrom.getValue() != null){
			criteria = criteria.andRequestDateGreaterThanOrEqualTo(dtRequestDateFrom.getValue());
		}
		
		if(dtRequestDateTo.getValue() != null){
			criteria = criteria.andRequestDateLessThanOrEqualTo(dtRequestDateTo.getValue());
		}
		
		if(bnbBranch.getKeyValue()!=null && bnbBranch.getKeyValue().getKey() != null){
			criteria = criteria.andBranchIdLike(bnbBranch.getKeyValue().getKey().toString());
		}else{
			criteria = criteria.andBranchIdLike("%");
		}
		
		if(cbRetirementType.getSelectedCount() > 0){
			KeyValue keyResult = (KeyValue)cbRetirementType.getModel().getElementAt(cbRetirementType.getSelectedIndex());
			criteria = criteria.andRetirementTypeCodeLike(keyResult.getValue().toString());
		}
		
		criteria = criteria.andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId().toString());
		example.setOrderByClause(orderBy);
		return example;
    }
    
    private void search() {
    	clearErrorMessage();
    	if(!validate()) {
			try {
				pgListOfValue.setTotalSize(retirementNonEBSService.countByExample(searchCriteria()));
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
	}
    
    @Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}

	private void generateDataAndPaging() {

		pgListOfValueTop.setActivePage(pgListOfValue.getActivePage());
		listRetirementDto = retirementNonEBSService.getRetirementDtoByExample(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());

		ListModelList<RetirementDTO> model = new ListModelList<RetirementDTO>(listRetirementDto);
		lstAssetRetirements.setModel(model);
		lstAssetRetirements.renderAll();
		
	}
	
	@Listen("onPaging = #pgListOfValueTop")
	public void onPagingTop() {
		generateDataAndPagingTop();
	}
	
    private void generateDataAndPagingTop() {

    	pgListOfValue.setActivePage(pgListOfValueTop.getActivePage());
		listRetirementDto = retirementNonEBSService.getRetirementDtoByExample(searchCriteria(),pgListOfValueTop.getPageSize(), pgListOfValueTop.getActivePage() * pgListOfValueTop.getPageSize());

		ListModelList<RetirementDTO> model = new ListModelList<RetirementDTO>(listRetirementDto);
		lstAssetRetirements.setModel(model);
		lstAssetRetirements.renderAll();
		
	}
	
	private Boolean validate(){
		Boolean flag = false;
		
		if(dtRequestDateFrom.getValue()!= null && dtRequestDateTo.getValue() != null)
		{
			if(dtRequestDateTo.getValue().getTime() < dtRequestDateFrom.getValue().getTime()) {
				ErrorMessageUtil.setErrorMessage(lblErrorDate, "Request Date To must be greater than Request Date From");
				flag = true;
			}
		}
		
		return flag;
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(lblErrorDate);
	}
	
	
	private void generateDataForDownload(){
		listRetirementDtoForDownload = retirementNonEBSService.getRetirementDtoByExample(searchCriteria());
		ListModelList<RetirementDTO> model2 = new ListModelList<RetirementDTO>(listRetirementDtoForDownload);
		lstAssetRetirementsDownload.setModel(model2);
		lstAssetRetirementsDownload.renderAll();
    }
	
	@Listen("onClick = #btnDownload")
	public void export() throws Exception {
    	generateDataForDownload();
		Listbox listbox = lstAssetRetirementsDownload;
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
	
	@Listen("onDetail= #winAssetRetirement")
	public void onDetail(ForwardEvent event){
		RetirementDTO retirementDTO = (RetirementDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("retirementDTO", retirementDTO);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("action", "detail");
		param.put("Branch", bnbBranch.getKeyValue());
		Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onBAST= #winAssetRetirement")
	public void onBAST(ForwardEvent event){
		RetirementDTO retirementDTO = (RetirementDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("retirementDTO", retirementDTO);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("action", "BAST");
		param.put("Branch", bnbBranch.getKeyValue());
		Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	
	@Listen("onPrintBast= #winAssetRetirement")
	public void onPrintBast(ForwardEvent event) throws Exception{
		RetirementDTO retirementDTO = (RetirementDTO) event.getData();
		retirementNonEBSService.printBAST(retirementDTO);
		
	}
	
	@Listen("onStatus= #winAssetRetirement")
	public void onStatus(ForwardEvent event){
		RetirementDTO retirementDTO = (RetirementDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("avmTrxId", UUID.fromString(retirementDTO.getAvmTrxIdString()));
		param.put("companyId", securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/approver_popup.zul",
				getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onStatusBast= #winAssetRetirement")
	public void onStatusBast(ForwardEvent event){
		RetirementDTO retirementDTO = (RetirementDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("avmTrxId", UUID.fromString(retirementDTO.getAvmTrxIdBastString()));
		param.put("companyId", securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/approver_popup.zul",
				getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onCreateRV= #winAssetRetirement")
	public void onCreateRV(ForwardEvent event) {
		RetirementDTO retirementDTO = (RetirementDTO) event.getData();
		try {
			retirementNonEBSService.createRV(retirementDTO);
			Messagebox.show("Create RV for Asset Retirement " + retirementDTO.getRequestNo() + " success",
					"Information", Messagebox.YES, Messagebox.INFORMATION);
			generateDataAndPaging();
		} catch (Exception e) {
			Messagebox.show("Create RV for Asset Retirement " + retirementDTO.getRequestNo() + " failed ",
					"Information", Messagebox.YES, Messagebox.ERROR);
			System.out.println(e);
		}

	}
	public Long getBnbBranchId() {
		return bnbBranchId;
	}

	public void setBnbBranchId(Long bnbBranchId) {
		this.bnbBranchId = bnbBranchId;
	}
	
}