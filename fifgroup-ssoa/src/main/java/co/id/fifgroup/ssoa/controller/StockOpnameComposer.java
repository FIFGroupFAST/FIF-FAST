package co.id.fifgroup.ssoa.controller;

import java.io.ByteArrayOutputStream;
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
import org.zkoss.zk.ui.Component;
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
import co.id.fifgroup.ssoa.constants.SOApprovalStatus;
import co.id.fifgroup.ssoa.constants.SOReportStatus;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.StockOpnameExample;
import co.id.fifgroup.ssoa.domain.StockOpnameExample.Criteria;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.service.StockOpnameService;

@VariableResolver(DelegatingVariableResolver.class)
public class StockOpnameComposer extends SelectorComposer<Component> {

	@Wire
    private Combobox lbxSearchBranch;
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
	/*@Wire
	private CommonBranchBandbox bnbBranch;*/
	@Wire
    private Listbox lbxRegisteredResult;
	@Wire
    private Listbox lbxUnregisteredResult;
	@Wire
    private Listbox lbxRegisteredDetail;
	@Wire
    private Listbox lbxUnregisteredDetail;
	@Wire
    private Textbox txtSearchPeriodName;
    @Wire
    private Textbox txtPeriodName;
    @Wire
    private Textbox txtBranch;
	@Wire
    private Datebox dtSearchPeriodStartDate1;
	@Wire
    private Datebox dtSearchPeriodStartDate2;
	@Wire
    private Datebox dtSearchPeriodEndDate1;
	@Wire
    private Datebox dtSearchPeriodEndDate2;
    @Wire
    private Datebox dtPeriodStartDate;
    @Wire
    private Datebox dtPeriodEndDate;
	@Wire
    private Combobox cmbDownload;
	@Wire
	private Combobox cmbDownloadAs;
	@Wire
	private Button btnNew;
	
	private Long branchId;
	
	private Long selectedBranchId;
	
	@Wire
	private Listbox stockOpnameListbox;
	
	@Wire
	private Listbox stockOpnameListboxForDownload;
	
	@Wire
	private Listheader hdrPeriodName;
	
	@Wire
	private Listheader hdrInstructionBy;
	
	@Wire
	private Listheader hdrPeriodStartDate;
	
	@Wire
	private Listheader hdrPeriodEndDate;
	
	@Wire
	private Listheader hdrStatus;
	
	@Wire
	private Listheader hdrReportNo;
	
	@Wire
	private Listheader hdrReportStatus;
	
	@Wire
	private Listheader hdrLastUpdateBy;
	
	@Wire
	private Listheader hdrLastUpdateDate;
	
	@Wire
	private Paging pgListOfValueTop;
	
	private List<StockOpnameDTO> listStockOpnameDto;
	
	private List<StockOpnameDTO> listStockOpnameDtoForDownload;
	
	private ListModelList<StockOpnameDTO> listModelStockOpnameDto;
	
	private static Logger log = LoggerFactory.getLogger(StockOpnameComposer.class);
    
    private static final long serialVersionUID = 1L;
   
    @WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
    
    @WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
    
    private FunctionPermission functionPermission;
    @WireVariable("arg")
	private Map<String, Object> arg;
    @Wire
	private Paging pgListOfValue;
    private String orderBy = "START_DATE DESC";

	@Override
    public void doAfterCompose(Component comp) throws Exception
    {
    	super.doAfterCompose(comp);
    	
    	init();
    	action();
    	functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
    	clear();
    	getSelf().setAttribute("win$composer", this, false);
    }
	
	private void sortAction(){
		hdrPeriodName.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "DESCRIPTION"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrInstructionBy.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(case when BRANCH_ID = -1 then 'Head Office' else (SELECT ORGANIZATION_NAME from WOS_ORGANIZATIONS where ORGANIZATION_ID = BRANCH_ID and rownum = 1) end )"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrPeriodStartDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "START_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrPeriodEndDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "END_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStatus.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(select DESCRIPTION from BSE_LOOKUP_DEPENDENTS where LOOKUP_ID = STATUS_ID and DETAIL_CODE = STATUS_CODE)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrReportNo.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(SELECT REPORT_NO FROM (  SELECT stock_opname_id, REPORT_NO FROM SOC_STOCK_OPNAME_REPORTING ORDER BY stock_opname_reporting_id DESC) WHERE stock_opname_id = hd.stock_opname_id AND ROWNUM = 1)"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrReportStatus.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "(SELECT STATUS_CODE FROM (  SELECT stock_opname_id, STATUS_CODE FROM SOC_STOCK_OPNAME_REPORTING ORDER BY stock_opname_reporting_id DESC) WHERE stock_opname_id = hd.stock_opname_id AND ROWNUM = 1)"+(se.isAscending()?" ASC":" DESC");
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
				orderBy = "HD.LAST_UPDATE_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
	}
	
	private void action(){
		bnbBranch.addEventListener(Events.ON_FOCUS, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if(!bnbBranch.getValue().isEmpty()){
					KeyValue keyValue = (KeyValue)bnbBranch.getKeyValue();
					if(branchId != (Long)keyValue.getKey()){
						btnNew.setDisabled(true);
					}else{
						btnNew.setDisabled(false);
					}
				}else{
					btnNew.setDisabled(false);
				}
			}
		});
			
	}
	
	public String getDescByCode(String code){
		/*List<KeyValue> listKey=  (List<KeyValue>)stockOpnameService.getLookupKeyValues(SOReportStatus.CODE.getCode(), code);
		if(listKey.size()>0){
			KeyValue key =(KeyValue)listKey.get(0);
			return key.getDescription().toString();
		}else{
			return null;
		}*/
		return SOReportStatus.getValueByCode(code);
	}
	
	private void init() {
		listModelStockOpnameDto = new ListModelList<StockOpnameDTO>();
		stockOpnameListbox.setModel(listModelStockOpnameDto);
		loadBranch(bnbBranch);
		branchId = securityServiceImpl.getSecurityProfile().getBranchId();
		System.out.println("securityServiceImpl=="+securityServiceImpl.getSecurityProfile().getBranchId());
		System.out.println("securityServiceImpl=="+securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(securityServiceImpl.getSecurityProfile().getBranchId().longValue() != -1){
		Branch branch = (Branch) stockOpnameService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(branch!=null && branch.getBranchCode()!=null){
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(branch.getBranchId());
			keyValue.setValue(branch.getBranchName());
			keyValue.setDescription(branch.getBranchCode());
			bnbBranch.setRawValue(keyValue);
			
		}
		
		bnbBranch.setDisabled(true);
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
			listBranch = stockOpnameService.getBranchByExample(example, limit, offset);

			return listBranch;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return stockOpnameService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}

    public void clear(){
    	txtSearchPeriodName.setText("");
    	dtSearchPeriodStartDate1.setText("");
    	dtSearchPeriodStartDate2.setText("");
    	//dtSearchPeriodEndDate1.setText("");
    	//dtSearchPeriodEndDate2.setText("");
    }
    
    @Listen("onStatus= #winStockOpnameInquiry")
	public void onStatus(ForwardEvent event){
		StockOpnameDTO stockOpnameDTO = (StockOpnameDTO) event.getData();
		//if(stockOpnameDTO.getReportStatusCode()!=null && !stockOpnameDTO.getReportStatusCode().equals(SOApprovalStatus.ON_PROGRESS.getCode())&& !stockOpnameDTO.getReportStatusCode().equals(SOApprovalStatus.NOT_STARTED.getCode())){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("avmTrxId", UUID.fromString(stockOpnameDTO.getAvmTrxIdString()));
		param.put("companyId", securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/approver_popup.zul",
				getSelf().getParent(), param);
		win.doModal();
		//}
	}
    
    @Listen("onResult= #winStockOpnameInquiry")
	public void onResult(ForwardEvent event){
		StockOpnameDTO stockOpnameDTO = (StockOpnameDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDTO);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("action", "result");
		param.put("Branch", bnbBranch.getKeyValue());
		Executions.createComponents("~./hcms/ssoa/stock_opname_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
    
    @Listen("onDetail= #winStockOpnameInquiry")
	public void onDetail(ForwardEvent event){
		StockOpnameDTO stockOpnameDTO = (StockOpnameDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDTO);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("action", "detail");
		param.put("Branch", bnbBranch.getKeyValue());
		Executions.createComponents("~./hcms/ssoa/stock_opname_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
    
    @Listen("onEdit= #winStockOpnameInquiry")
	public void onEdit(ForwardEvent event){
		StockOpnameDTO stockOpnameDTO = (StockOpnameDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDTO);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("action", "edit");
		param.put("Branch", bnbBranch.getKeyValue());
		Executions.createComponents("~./hcms/ssoa/stock_opname_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
    
    @Listen("onReport= #winStockOpnameInquiry")
	public void onReport(ForwardEvent event){
		StockOpnameDTO stockOpnameDTO = (StockOpnameDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDTO);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("action", "result");
		param.put("Branch", bnbBranch.getKeyValue());
		Executions.createComponents("~./hcms/ssoa/stock_opname_reporting.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
    
    @Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		System.out.println("branch=="+bnbBranch.getValue());
		param.put("Branch", bnbBranch.getKeyValue());
		param.put("action", "addNew");
		Executions.createComponents("~./hcms/ssoa/stock_opname_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
    
    
    @Listen ("onClick = button#btnFind")
	public void find() {
		String periodName = txtSearchPeriodName.getValue();
		Date periodStartDate1 = dtSearchPeriodStartDate1.getValue();
		Date periodStartDate2 = dtSearchPeriodStartDate2.getValue();
		//Date periodEndDate1 = dtSearchPeriodStartDate1.getValue();
		//Date periodEndDate2 = dtSearchPeriodStartDate2.getValue();
		String branch = bnbBranch.getValue();
		if((periodName == null ||periodName.isEmpty() || periodName.equals("%%")) && periodStartDate1 == null && periodStartDate2 == null &&  (branch == null || branch.isEmpty())) {
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
    
    private StockOpnameExample searchCriteria(){
    	StockOpnameExample example = new StockOpnameExample();
    	Criteria criteria = example.createCriteria();
		if(txtSearchPeriodName.getValue() != null && !txtSearchPeriodName.getValue().toString().isEmpty()){
			criteria = criteria.andDescriptionLike("%"+txtSearchPeriodName.getValue()+"%");
		}
		
		if(dtSearchPeriodStartDate1.getValue() != null){
			criteria = criteria.andStartDateGreaterThanOrEqualTo(dtSearchPeriodStartDate1.getValue());
		}
		
		if(dtSearchPeriodStartDate2.getValue() != null){
			criteria = criteria.andStartDateLessThanOrEqualTo(dtSearchPeriodStartDate2.getValue());
		}
		/*
		if(dtSearchPeriodEndDate1.getValue() != null){
			criteria = criteria.andStartDateGreaterThanOrEqualTo(dtSearchPeriodEndDate1.getValue());
		}
		
		if(dtSearchPeriodEndDate2.getValue() != null){
			criteria = criteria.andStartDateLessThanOrEqualTo(dtSearchPeriodEndDate2.getValue());
		}
		*/
		if(bnbBranch.getKeyValue()!=null && bnbBranch.getKeyValue().getKey() != null){
			criteria = criteria.andBranchIdLike(bnbBranch.getKeyValue().getKey().toString());
			selectedBranchId = (Long)bnbBranch.getKeyValue().getKey();
		}else{
			selectedBranchId = securityServiceImpl.getSecurityProfile().getBranchId();
		}
		
		criteria = criteria.andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId().toString());
		example.setOrderByClause(orderBy);
		return example;
    }
    
    private void search() {
		try {
			
			
			pgListOfValue.setTotalSize(stockOpnameService.countByExample(searchCriteria()));
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
    
    private void generateDataForDownload(){
    	listStockOpnameDtoForDownload = stockOpnameService.getStockOpnameByExample(searchCriteria());
		ListModelList<StockOpnameDTO> model2 = new ListModelList<StockOpnameDTO>(listStockOpnameDtoForDownload);
		stockOpnameListboxForDownload.setModel(model2);
		stockOpnameListboxForDownload.renderAll();
    }
    
    @Listen("onClick = #btnDownload")
	public void export() throws Exception {
    	generateDataForDownload();
		Listbox listbox = stockOpnameListboxForDownload;
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

	private void generateDataAndPaging() {

		pgListOfValueTop.setActivePage(pgListOfValue.getActivePage());
		listStockOpnameDto = stockOpnameService.getStockOpnameDtoByExample(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());

		ListModelList<StockOpnameDTO> model = new ListModelList<StockOpnameDTO>(listStockOpnameDto);
		stockOpnameListbox.setModel(model);
		stockOpnameListbox.renderAll();
		
		
		
	}
	
	@Listen("onPaging = #pgListOfValueTop")
	public void onPagingTop() {
		generateDataAndPagingTop();
	}
	
	private void generateDataAndPagingTop() {

		pgListOfValue.setActivePage(pgListOfValueTop.getActivePage());
		listStockOpnameDto = stockOpnameService.getStockOpnameDtoByExample(searchCriteria(),pgListOfValueTop.getPageSize(), pgListOfValueTop.getActivePage() * pgListOfValueTop.getPageSize());

		ListModelList<StockOpnameDTO> model = new ListModelList<StockOpnameDTO>(listStockOpnameDto);
		stockOpnameListbox.setModel(model);
		stockOpnameListbox.renderAll();
		
		
		
	}
    
	@Listen("a[label='Submit']")
	public void popupSubmit(Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_status.zul", null, null);
		win.doModal();
	}

	@Listen("a[label='Approved']")
	public void popupApproved(Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_status.zul", null, null);
		win.doModal();
	}
	
	@Listen("a[label='Result']")
	public void popupResult(Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_result.zul", null, null);
		win.doModal();
	}

	@Listen("a[label='Image']")
	public void popupImage(Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_image.zul", null, null);
		win.doModal();
	}
	
	@Listen("a[label='Edit']")
	public void popupEdit(Event e) {
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_edit.zul", null, null);
		win.doModal();
	}
	
	@Listen("a[label='Delete']")
	public void delete(){
		msgYesNo("Do you really want to delete this data?", "Confirmation");
	}


    
	 @SuppressWarnings({ "unchecked", "rawtypes"})
	 private void msgYesNo(String question, String title){
		 Messagebox.show(question, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener(){
			 public void onEvent(Event e){
				 if(Messagebox.ON_YES.equals(e.getName())){
					 //alert("Click Ok");
				 }else if(Messagebox.ON_NO.equals(e.getName())){
					 //alert("Click Cancel");
				 }
			 }
		 });
	 }

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getSelectedBranchId() {
		return selectedBranchId;
	}

	public void setSelectedBranchId(Long selectedBranchId) {
		this.selectedBranchId = selectedBranchId;
	}
	 
	 
}
     
