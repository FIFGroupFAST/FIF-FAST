package co.id.fifgroup.ssoa.controller;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.exporter.excel.ExcelExporter;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
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
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonEmployeeNumberBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.domain.AssetLabelingExample;
import co.id.fifgroup.ssoa.domain.AssetLabelingExample.Criteria;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.dto.AssetLabelingDTO;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;
import co.id.fifgroup.ssoa.service.AssetLabelingService;


@VariableResolver(DelegatingVariableResolver.class)
public class AssetLabelingComposer extends SelectorComposer<Window> {
	private static Logger log = LoggerFactory.getLogger(AssetLabelingComposer.class);

	private static final long serialVersionUID = 1L;
	
	@Wire
	private Listitem lstAsset1;
	@Wire
	private Listitem lstAsset2;
	@Wire
	private Listitem lstAsset3;
	@Wire
	private Listbox cbReprintingReason;
	@Wire
	private Button btnDelete;
	@Wire
	private Button btnSearch;
	@Wire
	private Button btnAddAsset;
	@Wire
    private Textbox txtBranchName;
	@Wire
    private Label lblErrorDate;
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
	@Wire
	private Combobox cmbBranch;
	@Wire
	private Datebox startDate;
	@Wire
	private Datebox endDate;
	@Wire
	private Datebox labelingDate;
	@Wire
	private Textbox branchOwner;
	@Wire
	private Textbox description;
	@Wire
	private Textbox branchOwnerAdd;
	@Wire
	private Listcell assetNumber;
	@Wire
	private Datebox dtDateStart;
	@Wire
	private Datebox dtDateEnd;
	@Wire
	private Groupbox gbAssetLabeling;
	@Wire
	private Groupbox gbAssetLabelingAdd;
	@Wire
	private Groupbox gbAssetLabelingDetail;
	@Wire
	private Label lbldelete;
	@Wire
	private Listbox lstAssetLabeling;
	@Wire
	private Listbox lstAssetLabelingDownload;
	@Wire
	private TabularEntry<AssetLabelingDetailDTO> lstAssetAdd;
	@Wire
	private Listbox lbxAssetSearch;
	@Wire
	private Window winGenericDoubleLov;
	@Wire
	private Window winAssetLabeling;
	@Wire
	private CommonEmployeeNumberBandbox bnbApprover;
	@Wire
	private TabularEntry<AssetLabelingDetailDTO> assetLabelingDetail;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Paging pgListOfValueTop;
	@Wire
	private Button btnNew;
	@Wire
	private Combobox cmbDownloadAs;
	
	@Wire
	private Listheader hdrLabelingDate;
	@Wire
	private Listheader hdrDesc;
	@Wire
	private Listheader hdrLastUpdateBy;
	@Wire
	private Listheader hdrLastUpdateDate;
	@Wire
	private Iframe iFramePdf;
	
	
	private List<AssetLabelingDTO> assetLabelingDTO;
	
	private List<AssetLabelingDTO> listAssetLabelingDtoForDownload;
	
	private List<AssetLabelingDetailDTO> assetLabelingDetailList;
	
	private ListModelList<AssetLabelingDTO> listModelAssetLabelingDto;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
	private FunctionPermission functionPermission;

	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient AssetLabelingService assetLabelingService;
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private Map<Long, AssetLabelingDetailDTO> editAssetLabelingDtl = new HashMap<Long, AssetLabelingDetailDTO>();
	private String orderBy = "LH.LABELING_DATE DESC";
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		//System.out.println("msk doAfterCompose");
		init();
		
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		
		if(arg.get("aMedia")!=null){
			iFramePdf.setContent((AMedia)arg.get("aMedia"));
		}
	}
	private void sortAction(){
		hdrLabelingDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "LH.LABELING_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		hdrDesc.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(CASE WHEN LH.DESCRIPTION IS NULL THEN '-' ELSE LH.DESCRIPTION end)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		hdrLastUpdateBy.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "LH.LAST_UPDATE_BY"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		hdrLastUpdateDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "LH.LAST_UPDATE_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
	}
	
	private void init() {
		listModelAssetLabelingDto = new ListModelList<AssetLabelingDTO>();
		lstAssetLabeling.setModel(listModelAssetLabelingDto);
		loadBranch(bnbBranch);
		Branch branch = (Branch) assetLabelingService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
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
						//btnNew.setDisabled(true);
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
						Branch branch = (Branch) assetLabelingService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
						btnNew.setDisabled(false);
					}
				}
			});
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private Boolean validate(){
		Boolean flag = false;
		
		if(startDate.getValue()!= null && endDate.getValue() != null)
		{
			if(endDate.getValue().getTime() < startDate.getValue().getTime()) {
				ErrorMessageUtil.setErrorMessage(lblErrorDate, "Labeling Date To must be greater than Labeling Date From");
				flag = true;
			}
		}
		
		return flag;
	}
	
	@Listen("onClick = button#btnFind")
	public void find() {

		Date values = startDate.getValue();
		Date values2 = endDate.getValue();
		String branch = bnbBranch.getValue();
		if (values == null && values2 == null && (branch == null || branch.isEmpty())) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"),
					Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

						private static final long serialVersionUID = -8756250972566999901L;

						@Override
						public void onEvent(Event event) throws Exception {
							int resultButton = (int) event.getData();
							if (resultButton == Messagebox.YES) {
								search();
								sortAction();
							}
						}
					});
		} else {
			search();
		}
	}

	private AssetLabelingExample searchCriteria(){
    	AssetLabelingExample example = new AssetLabelingExample();
    	Criteria criteria = example.createCriteria();
		if(startDate.getValue() != null){
			criteria = criteria.andRequestDateGreaterThanOrEqualTo(startDate.getValue());
		}
		
		if(endDate.getValue() != null){
			criteria = criteria.andRequestDateLessThanOrEqualTo(endDate.getValue());
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
    	clearErrorMessage();
    	if(!validate()){
			try {
				pgListOfValue.setTotalSize(assetLabelingService.countByExample(searchCriteria()));
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
    
    private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(lblErrorDate);
	}
    
    @Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		generateDataAndPaging();
	}

	private void generateDataAndPaging() {
		pgListOfValueTop.setActivePage(pgListOfValue.getActivePage());
		assetLabelingDTO = assetLabelingService.getParameterDtoByExample(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
		ListModelList<AssetLabelingDTO> model = new ListModelList<AssetLabelingDTO>(assetLabelingDTO);
		lstAssetLabeling.setModel(model);
		lstAssetLabeling.renderAll();
	}
	
	@Listen("onPaging = #pgListOfValueTop")
	public void onPagingTop() {
		generateDataAndPagingTop();
	}

	private void generateDataAndPagingTop() {
		pgListOfValue.setActivePage(pgListOfValueTop.getActivePage());
		assetLabelingDTO = assetLabelingService.getParameterDtoByExample(searchCriteria(),pgListOfValueTop.getPageSize(), pgListOfValueTop.getActivePage() * pgListOfValueTop.getPageSize());
		ListModelList<AssetLabelingDTO> model = new ListModelList<AssetLabelingDTO>(assetLabelingDTO);
		lstAssetLabeling.setModel(model);
		lstAssetLabeling.renderAll();
	}
	
	private void generateDataForDownload(){
		listAssetLabelingDtoForDownload = assetLabelingService.getParameterDtoByExample(searchCriteria());
		ListModelList<AssetLabelingDTO> model2 = new ListModelList<AssetLabelingDTO>(listAssetLabelingDtoForDownload);
		lstAssetLabelingDownload.setModel(model2);
		lstAssetLabelingDownload.renderAll();
    } 

	@Listen("onClick = #btnNew")
	public void add() {
		String branchName= null;
		String branchId= null;
		Branch branch = (Branch) assetLabelingService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(!bnbBranch.getValue().isEmpty()){
			branchName= bnbBranch.getValue();
			branchId = bnbBranch.getKeyValue().getKey().toString();
		}
		else
		{
			branchName =branch.getBranchName();
			branchId = branch.getBranchId().toString();
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("Branch", branchName);
		param.put("BranchId", branchId);
		param.put("action", "addNew");
		Executions.createComponents("~./hcms/ssoa/asset_labeling_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

	@Listen("onDetail= #winAssetLabeling")
	public void onDetail(ForwardEvent event){
		AssetLabelingDTO assetLabelingDto = (AssetLabelingDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetLabelingDto", assetLabelingDto);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("edit", true);
		Executions.createComponents("~./hcms/ssoa/asset_labeling_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = #lbldelete")
	public void lbldelete() {
		msgOkCancel("Do you really want to delete this data?", "Confirmation");
	}

	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void msgOkCancel(String question, String title) {
		Messagebox.show(question, title, Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
			public void onEvent(Event e) {
				if (Messagebox.ON_OK.equals(e.getName())) {
					// alert("Click Ok");
				} else if (Messagebox.ON_CANCEL.equals(e.getName())) {
					// alert("Click Cancel");
				}
			}
		});
	}

	@Listen("onDelete= #winAssetLabeling")
	public void onDelete(ForwardEvent event){
		final AssetLabelingDTO assetLabelingDto = (AssetLabelingDTO) event.getData();
		Messagebox.show("Are you sure want to delete this data?", "Confirmation", Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = -8756250972566999901L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						assetLabelingService.delete(assetLabelingDto);
						search();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});}
	
	
	
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
			listBranch = assetLabelingService.getBranchByExample(example, limit, offset);

			return listBranch;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return assetLabelingService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}
	
	@Listen("onClick = #btnDownload")
	public void export() throws Exception {
    	generateDataForDownload();
		Listbox listbox = lstAssetLabelingDownload;
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
}