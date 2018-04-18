package co.id.fifgroup.ssoa.controller;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.ssoa.domain.SOPeriod;
import co.id.fifgroup.ssoa.domain.SOPeriodDetail;
import co.id.fifgroup.ssoa.domain.SOPeriodExample;
import co.id.fifgroup.ssoa.domain.SOPeriodExample.Criteria;
import co.id.fifgroup.ssoa.dto.AssetLabelingDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.service.SOPeriodService;
import co.id.fifgroup.ssoa.service.SSOACommonService;
import co.id.fifgroup.ssoa.constants.SOApprovalStatus;
import co.id.fifgroup.ssoa.constants.SOPeriodStatus;
import co.id.fifgroup.ssoa.domain.AssetTransfer;
import co.id.fifgroup.ssoa.domain.ParameterDetail;
import co.id.fifgroup.ssoa.domain.RetirementExample;
import co.id.fifgroup.ssoa.service.ParameterService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Textbox;
import org.modelmapper.ModelMapper;
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
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;


@VariableResolver(DelegatingVariableResolver.class)
public class SOPeriodComposer extends SelectorComposer<Window> {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(SOPeriodComposer.class);

	@WireVariable(rewireOnActivate = true)
	private transient SOPeriodService soPeriodService;
	@WireVariable(rewireOnActivate = true)
	private transient SSOACommonService ssoaCommonService;
	@WireVariable(rewireOnActivate = true)
	private transient ParameterService parameterService;
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@Wire
	private Datebox dtSearchPeriodStartDateFrom;
	@Wire
	private Datebox  dtSearchPeriodStartDateTo;
	@Wire
	private Searchtextbox txtDesc;
	@Wire
	private Listbox soPeriodListbox;
	@Wire
	private Listbox soPeriodListboxForDownload;
	@Wire
	private Listbox statusListbox;
	@Wire
	private Listbox cmbStatus;
	@Wire
	private Textbox txtDetailPeriodName;
	@Wire
	private Textbox txtDetailPeriodStartDate;
	@Wire
	private Textbox txtDetailPeriodEndDates;
	@Wire
	private Button btnNew;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Groupbox grbSearch;
	@Wire
	private Panel panelDetailStockOpnamePeriod;
	@Wire
	private Combobox cmbDownloadAs;
	@Wire
	private Listheader hdrPeriodName;
	@Wire
	private Listheader hdrPeriodStartDate;
	@Wire
	private Listheader hdrPeriodEndDate;
	@Wire
	private Listheader hdrInvolvedBranch;
	@Wire
	private Listheader hdrNotStartedBranch;
	@Wire
	private Listheader hdrInProcessBranch;
	@Wire
	private Listheader hdrSubmitBranch;
	@Wire
	private Listheader hdrApproveBranch;
	@Wire
	private Listheader hdrClosedBranch;
	@Wire
	private Listheader hdrStatus;
	@Wire
	private Listheader hdrLastNotificationDate;
	@Wire
	private Listheader hdrLastUpdateBy;
	@Wire
	private Listheader hdrLastUpdateDate;
	@Wire
	private Paging pgListOfValueTop;
	
	private SOPeriodDTO soPeriod;
	private SOPeriodDTO soPeriodDTO;
	private SOPeriodDTO selected;
	private List<SOPeriodDTO> listSOPeriodDto;
	private ListModelList<SOPeriodDTO> listModelSOPeriodDto;
	private String periodName;
	private FunctionPermission functionPermission;
	private String orderBy = "HD.START_DATE DESC";

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		//updateCountStatus();
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
		setComboBoxList();
	}

	private void sortAction(){
		hdrPeriodName.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.DESCRIPTION"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrPeriodStartDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.START_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrPeriodEndDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.END_DATE"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrInvolvedBranch.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.INVOLVE_BRANCH"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrNotStartedBranch.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.NOT_STARTED_BRANCH"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrInProcessBranch.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.IN_PROCESS_BRANCH"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrSubmitBranch.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.SUBMIT_BRANCH"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrApproveBranch.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.APPROVED_BRANCH"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrClosedBranch.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.CLOSED_BRANCH"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrStatus.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "L.DESCRIPTION"+(se.isAscending()?" ASC":" DESC");
				generateDataAndPaging();
			}
		});
		
		hdrLastNotificationDate.addEventListener(Events.ON_SORT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				SortEvent se = (SortEvent)event;
				orderBy = "HD.LAST_NOTIFICATION_DATE"+(se.isAscending()?" ASC":" DESC");
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
	private void initFunctionSecurity() {
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}

	private void init() {
		listModelSOPeriodDto = new ListModelList<SOPeriodDTO>();
		soPeriodListbox.setModel(listModelSOPeriodDto);
	}
	/*
	public void updateCountStatus(){
		SOPeriod soPeriod = new SOPeriod();
		soPeriodService.updateCountStatus(soPeriod);
	}
	*/
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue all = new KeyValue();
		all.setKey("");
		all.setValue("%%");
		all.setDescription("--Select--");
		data.add(all);
		data.addAll(soPeriodService.getStatusSOPeriod());
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cmbStatus.setModel(model);
		cmbStatus.renderAll();
		cmbStatus.setSelectedIndex(0);
	}

	@Listen ("onClick = button#btnFind")
	public void find() {
		
		periodName = txtDesc.getValue();
		Date startDate = dtSearchPeriodStartDateFrom.getValue();
		Date endDate = dtSearchPeriodStartDateTo.getValue();
		int status = cmbStatus.getSelectedIndex();

		if((periodName == null ||periodName.isEmpty() || periodName.equals("%%")) && startDate == null && endDate == null && status == 0 ) {
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
		}
		else {
			search();
		}
		
	}

	 private SOPeriodExample searchCriteria(){
    	SOPeriodExample example = new SOPeriodExample();
    	Criteria criteria = example.createCriteria();
		if(txtDesc.getValue() != null && !txtDesc.getValue().toString().isEmpty()){
			criteria = criteria.andDescriptioLike(txtDesc.getValue());
		}
		
		if(dtSearchPeriodStartDateFrom.getValue() != null){
			criteria = criteria.andStartDateGreaterThanOrEqualTo(dtSearchPeriodStartDateFrom.getValue());
		}
		
		if(dtSearchPeriodStartDateTo.getValue() != null){
			criteria = criteria.andStartDateLessThanOrEqualTo(dtSearchPeriodStartDateTo.getValue());
		}
		
		if(cmbStatus.getSelectedCount() > 0){
			KeyValue keyResult = (KeyValue)cmbStatus.getModel().getElementAt(cmbStatus.getSelectedIndex());
			criteria = criteria.andStatusLike(keyResult.getValue().toString());
		}
		
		criteria = criteria.andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId().toString());
		example.setOrderByClause(orderBy);
		return example;
    }
    
    private void search() {
		try {
			
			pgListOfValue.setTotalSize(soPeriodService.countByExample(searchCriteria()));
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

	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/ssoa/so_period_add.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

	@Listen("onDetail= #winSOPeriod")
	public void onDetail(ForwardEvent event) {
		SOPeriodDTO soPeriodDto = (SOPeriodDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("soPeriodDto", soPeriodDto);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("detail", true);
		Executions.createComponents("~./hcms/ssoa/so_period_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onResendNotification= #winSOPeriod")
	public void onResendNotification(ForwardEvent event) {

		final SOPeriodDTO soPeriodDto = (SOPeriodDTO) event.getData();
		soPeriodDto.setObjectEmployeeUUID(securityServiceImpl.getSecurityProfile().getPersonUUID());
		
		Messagebox.show("Are you sure want to resend notification?", "Confirmation", Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {
			
			private static final long serialVersionUID = -8756250972566999901L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					
					Thread thread = new Thread() {
						public void run() {
							try {
								soPeriodService.resendNotification(soPeriodDto);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					thread.start();
					Messagebox.show("Notification has been send.", "Information", Messagebox.YES,
							Messagebox.INFORMATION);
				}
				else {
					return;
				}
			}
		});
	}

	
	@Listen("onClick= button#backButton")
	public void btnBack() {
		panelDetailStockOpnamePeriod.setVisible(false);
		grbSearch.setVisible(true);
	}

	@Listen("onInvolve= #winSOPeriod")
	public void onInvolve(ForwardEvent event){
		SOPeriodDTO soPeriodDto = (SOPeriodDTO) event.getData();
		SOPeriodDetail soPeriodDetail= new SOPeriodDetail();
		soPeriodDetail.setStatusCode(null);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("soPeriodDto", soPeriodDto);
		param.put("status", soPeriodDetail);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
	
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/so_period_popup_branch.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onNotStarted= #winSOPeriod")
	public void onStatus(ForwardEvent event){
		SOPeriodDTO soPeriodDto = (SOPeriodDTO) event.getData();
		SOPeriodDetail soPeriodDetail= new SOPeriodDetail();
		soPeriodDetail.setStatusCode(SOApprovalStatus.NOT_STARTED.getCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("soPeriodDto", soPeriodDto);
		param.put("status", soPeriodDetail);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
	
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/so_period_popup_branch.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onInProcess= #winSOPeriod")
	public void onInProcess(ForwardEvent event){
		SOPeriodDTO soPeriodDto = (SOPeriodDTO) event.getData();
		SOPeriodDetail soPeriodDetail= new SOPeriodDetail();
		soPeriodDetail.setStatusCode(SOApprovalStatus.ON_PROGRESS.getCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("soPeriodDto", soPeriodDto);
		param.put("status", soPeriodDetail);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);

		Window win = (Window) Executions.createComponents("~./hcms/ssoa/so_period_popup_branch.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onSubmit= #winSOPeriod")
	public void onSubmit(ForwardEvent event){
		SOPeriodDTO soPeriodDto = (SOPeriodDTO) event.getData();
		SOPeriodDetail soPeriodDetail= new SOPeriodDetail();
		soPeriodDetail.setStatusCode(SOApprovalStatus.SUBMIT.getCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("soPeriodDto", soPeriodDto);
		param.put("status", soPeriodDetail);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);

		Window win = (Window) Executions.createComponents("~./hcms/ssoa/so_period_popup_branch.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onApproved= #winSOPeriod")
	public void onApproved(ForwardEvent event){
		SOPeriodDTO soPeriodDto = (SOPeriodDTO) event.getData();
		SOPeriodDetail soPeriodDetail= new SOPeriodDetail();
		soPeriodDetail.setStatusCode(SOApprovalStatus.APPROVED.getCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("soPeriodDto", soPeriodDto);
		param.put("status", soPeriodDetail);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);

		Window win = (Window) Executions.createComponents("~./hcms/ssoa/so_period_popup_branch.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onFinish= #winSOPeriod")
	public void onFinish(ForwardEvent event){
		SOPeriodDTO soPeriodDto = (SOPeriodDTO) event.getData();
		SOPeriodDetail soPeriodDetail= new SOPeriodDetail();
		soPeriodDetail.setStatusCode(SOApprovalStatus.CLOSED.getCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("soPeriodDto", soPeriodDto);
		param.put("status", soPeriodDetail);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);

		Window win = (Window) Executions.createComponents("~./hcms/ssoa/so_period_popup_branch.zul", getSelf().getParent(), param);
		win.doModal();
	}
	

	private void generateDataAndPaging() {
		pgListOfValueTop.setActivePage(pgListOfValue.getActivePage());
		listSOPeriodDto = soPeriodService.getSOPeriodDtoByExample(searchCriteria(),pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());		
		ListModelList<SOPeriodDTO> model = new ListModelList<SOPeriodDTO>(listSOPeriodDto);
		soPeriodListbox.setModel(model);
		soPeriodListbox.renderAll();
	}
	
	@Listen("onPaging = #pgListOfValueTop")
	public void onPagingTop() {
		generateDataAndPagingTop();
	}
	
	private void generateDataAndPagingTop() {
		pgListOfValue.setActivePage(pgListOfValueTop.getActivePage());
		listSOPeriodDto = soPeriodService.getSOPeriodDtoByExample(searchCriteria(),pgListOfValueTop.getPageSize(), pgListOfValueTop.getActivePage() * pgListOfValueTop.getPageSize());		
		ListModelList<SOPeriodDTO> model = new ListModelList<SOPeriodDTO>(listSOPeriodDto);
		soPeriodListbox.setModel(model);
		soPeriodListbox.renderAll();
	}
	
	private void generateDataForDownload() {
		List listSOPeriodDtoForDownload = soPeriodService.getSOPeriodByExample(searchCriteria());		
		ListModelList<SOPeriodDTO> model = new ListModelList<SOPeriodDTO>(listSOPeriodDtoForDownload);
		soPeriodListboxForDownload.setModel(model);
		soPeriodListboxForDownload.renderAll();
	}

	@Listen("onClick = #btnDownload")
	public void export() throws Exception {
    	generateDataForDownload();
		Listbox listbox = soPeriodListboxForDownload;
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