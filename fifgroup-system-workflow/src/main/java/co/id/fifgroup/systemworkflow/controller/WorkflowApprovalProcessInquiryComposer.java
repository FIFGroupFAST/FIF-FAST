package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVMApprovalProcessData;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.service.WorkflowLookupServiceAdapter;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowApprovalProcessInquiryComposer extends SelectorComposer<Window>{

private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowApprovalProcessInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;

	private WorkflowApprovalProcessInquiryComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private WorkflowLookupServiceAdapter workflowLookupServiceAdapterImpl;
	
	@Wire
	private Listbox cmbCompany;
	@Wire
	private CommonPopupDoubleBandbox bnbEmployee;
	@Wire
	private Listbox cmbTrxType;
	@Wire
	private Listbox cmbActionType;
	@Wire
	private Datebox dtbReceivedFrom;
	@Wire
	private Datebox dtbReceivedTo;
	@Wire
	private Listbox lbxApprovalProcess;
	
	ListModelList<AVMApprovalProcessData> dataModelList = new ListModelList<AVMApprovalProcessData>();
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
	}
	
	@Listen("onDownloadUserManual = #winWorkflowApprovalProcessInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	public void initComponent() {
		lbxApprovalProcess.setMold("paging");
		lbxApprovalProcess.setPageSize(GlobalVariable.getMaxRowPerPage());
		
		dtbReceivedFrom.setFormat(FormatDateUI.getDateFormat());
		dtbReceivedTo.setFormat(FormatDateUI.getDateFormat());
		loadEmployee();
		getCompany();
		getTransactionType();
		getActionType();
	}
	
	private void loadEmployee() {
		bnbEmployee.setConcatValueDescription(true);
		bnbEmployee.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

			private static final long serialVersionUID = -3390151775044854377L;

			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria1, String searchCriteria2, int limit, int offset) {
				return workflowLookupServiceAdapterImpl.getAllEmployeeCriteria(searchCriteria1, searchCriteria2, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1, String searchCriteria2) {
				return workflowLookupServiceAdapterImpl.countAllEmployeeCriteria(searchCriteria1, searchCriteria2);
			}

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				keyValue.setKey((UUID) data.getKey());
				keyValue.setValue(data.getValue().toString());
				keyValue.setDescription(data.getDescription().toString());
			}
		});
	}
	
	private void getCompany() {
		cmbCompany.setMold("select");
		List<Company> companies = new ArrayList<Company>();
				
		Company select = new Company();
		select.setCompanyId(ScopeType.SELECT.getValue());
		select.setCompanyName(ScopeType.SELECT.getDesc());
		
		CompanyExample example = new CompanyExample();
		example.createCriteria().andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
			.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
		example.setOrderByClause("UPPER(COMPANY_NAME) ASC");
		List<Company> listCompany = companyServiceImpl.getCompanyByExample(example);
		
		companies.add(select);
		companies.addAll(listCompany);
		
		ListModelList<Company> listModelCompany = new ListModelList<Company>(companies);
		cmbCompany.setModel(listModelCompany);
		cmbCompany.setItemRenderer(new SerializableListItemRenderer<Company>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, Company data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getCompanyName()));
			}
		});
		cmbCompany.renderAll();
		listModelCompany.addToSelection(select);
	}
	
	private void getTransactionType() {
		cmbTrxType.setMold("select");
		ListModelList<TrxType> model = new ListModelList<TrxType>(TrxType.values());
		model.remove(TrxType.DEFAULT);
		cmbTrxType.setModel(model);
		cmbTrxType.setItemRenderer(new SerializableListItemRenderer<TrxType>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, TrxType data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getMessage()));
			}
		});
		cmbTrxType.renderAll();
		cmbTrxType.setSelectedIndex(0);
	}
	
	private void getActionType() {
		cmbActionType.setMold("select");
		ListModelList<ActionType> model = new ListModelList<ActionType>(ActionType.values());
		cmbActionType.setModel(model);
		cmbActionType.setItemRenderer(new SerializableListItemRenderer<ActionType>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, ActionType data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getMessage()));
			}
		});
		cmbActionType.renderAll();
		cmbActionType.setSelectedIndex(0);
	}
	
	@Listen("onClick = button#btnFind; onOK = #txtApproverName")
	public void doFind() {
		if (cmbCompany.getSelectedIndex() == 0 && bnbEmployee.getKeyValue() == null && cmbTrxType.getSelectedIndex() == 0
				&& cmbActionType.getSelectedIndex() == 0 && dtbReceivedFrom.getValue() == null && dtbReceivedTo.getValue() == null) {
			Messagebox.show(Labels.getLabel("common.searchMightBeSlow"), "Question", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new SerializableEventListener<Event>() {				

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getName().equals("onYes")) {
						doSearch();
					}
				}
			});			
		} else {
			doSearch();
		}
	}
	
	public void doSearch() {
		List<AVMApprovalProcessData> dataList = new ArrayList<AVMApprovalProcessData>();
		try {
			int companyId = 0;
			if (cmbCompany.getSelectedIndex() != -1 && (((Company) cmbCompany.getSelectedItem().getValue()).getCompanyId() != null))
				companyId = ((Company) cmbCompany.getSelectedItem().getValue()).getCompanyId().intValue();
			int trxType = 0;
			if (cmbTrxType.getSelectedIndex() != -1 && !((TrxType) cmbTrxType.getSelectedItem().getValue()).equals(TrxType.SELECT))
				trxType = ((TrxType) cmbTrxType.getSelectedItem().getValue()).getCode().intValue();
			int actionType = -2;
			if (cmbActionType.getSelectedIndex() != -1 && !((ActionType) cmbActionType.getSelectedItem().getValue()).equals(ActionType.SELECT))
				actionType = ((ActionType) cmbActionType.getSelectedItem().getValue()).getCode();
			Date dateFrom = dtbReceivedFrom.getValue() != null ? DateUtils.truncate(dtbReceivedFrom.getValue(), Calendar.DATE) : null;
			Date dateTo = dtbReceivedTo.getValue() != null ? DateUtils.truncate(dtbReceivedTo.getValue(), Calendar.DATE) : null;
			
			List<PersonDTO> listPerson = null;
			if (bnbEmployee.getKeyValue() != null) {
				listPerson = new ArrayList<PersonDTO>();
				PersonDTO person = new PersonDTO();
				person.setPersonUUID((UUID) bnbEmployee.getKeyValue().getKey());
				listPerson.add(person);		
			}
			
			dataList = avmAdapterServiceImpl.getApprovalProcess(companyId, trxType, actionType, dateFrom, dateTo, listPerson);
			dataModelList = new ListModelList<AVMApprovalProcessData>(dataList);
			lbxApprovalProcess.setModel(dataModelList);
			lbxApprovalProcess.setItemRenderer(getItemRenderer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ListitemRenderer<AVMApprovalProcessData> getItemRenderer() {
		ListitemRenderer<AVMApprovalProcessData> listitemRenderer = new SerializableListItemRenderer<AVMApprovalProcessData>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApprovalProcessData data, final int index)
					throws Exception {
				String companyName = avmAdapterServiceImpl.getCompanyName((long) data.getCompanyId());
				
				String fullName = avmAdapterServiceImpl.getName(data.getApproverId(), (long) data.getCompanyId());
				
				new Listcell(companyName).setParent(item);
				new Listcell(fullName).setParent(item);
				new Listcell(TrxType.fromCode((long) data.getTrxType()).getMessage()).setParent(item);
				new Listcell(ActionType.fromCode(data.getActionType()).getMessage()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getAvmReceivedTimestamp())).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getAvmActionTimestamp())).setParent(item);
				String progressTime = DateUtil.getProgressTime(data.getAvmReceivedTimestamp(), data.getAvmActionTimestamp());
				new Listcell(progressTime).setParent(item);
				
				A link = new A(Labels.getLabel("common.detail"));
				link.setStyle("color:blue");
//				link.setDisabled(disabledHyperlinkDetail);
				link.addEventListener("onClick", new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event arg0) throws Exception {
						params = new HashMap<String, Object>();
						params.put("AVMApprovalProcessData", data);
						
						Window window = (Window) Executions.createComponents("~./hcms/workflow/workflow_notification_message_detail.zul", getSelf().getParent(), params);
						window.setClosable(true);
						window.setMaximized(false);
						window.setWidth("80%");
						window.doModal();
					}
				});
				Listcell lc = new Listcell();
				link.setParent(lc);
				lc.setParent(item);		
			}
		};
		
		return listitemRenderer;
	}	
}
