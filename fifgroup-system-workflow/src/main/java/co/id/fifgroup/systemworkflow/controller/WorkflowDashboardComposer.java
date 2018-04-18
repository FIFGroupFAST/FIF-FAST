package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.dto.AVMApplicationDataDTO;
import co.id.fifgroup.systemworkflow.dto.AVMOutgoingReportDTO;
import co.id.fifgroup.systemworkflow.dto.NotificationMessageDTO;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;

import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.basicsetup.domain.TransactionType;
import co.id.fifgroup.basicsetup.domain.TransactionTypeExample;
import co.id.fifgroup.basicsetup.service.TransactionTypeService;
import co.id.fifgroup.core.constant.ResponsibilitiesType;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.NavigationTransactionForm;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.notification.constant.MessageStatus;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowDashboardComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory
			.getLogger(WorkflowDashboardComposer.class);

	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;

	@WireVariable(rewireOnActivate = true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient TemplateMessageService templateMessageServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient TransactionTypeService transactionTypeServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;

	@Wire
	private Window winWorkflowDashboard;
	@Wire
	private Button btnRejectApproval;
	@Wire
	private Tabbox tabboxDekstop;
	@Wire
	private Listbox lbxNotificationMessage;
	@Wire
	private Listbox lbxApprovalTask;
	@Wire
	private Textbox txtFilterSubjectApproval;
	@Wire
	private CommonPopupBandbox bnbTrxTypeApproval;
	@Wire
	private Listbox lbxOutgoingTask;
	@Wire
	private Textbox txtFilterSubjectOutgoing;
	@Wire
	private CommonPopupBandbox bnbTrxTypeOutgoing;
	@Wire
	private Listbox lbxRejectedTask;
	@Wire
	private Textbox txtFilterSubjectRejected;
	@Wire
	private CommonPopupBandbox bnbTrxTypeRejected;
	@Wire
	private Tab tabNotificationMessage;
	@Wire
	private Tab tabApprovalTask;
	@Wire
	private Tab tabOutgoingTask;
	@Wire
	private Tab tabRejectedTask;
	
	//MOBILE ------------------------------------------------
	@Wire
	private Tabbox tabboxMobile;
	@Wire
	private Listbox lbxNotificationMessageMobile;
	@Wire
	private Listbox lbxApprovalTaskMobile;
	@Wire
	private Textbox txtFilterSubjectApprovalMobile;
	@Wire
	private CommonPopupBandbox bnbTrxTypeApprovalMobile;
	@Wire
	private Listbox lbxOutgoingTaskMobile;
	@Wire
	private Textbox txtFilterSubjectOutgoingMobile;
	@Wire
	private CommonPopupBandbox bnbTrxTypeOutgoingMobile;
	@Wire
	private Listbox lbxRejectedTaskMobile;
	@Wire
	private Textbox txtFilterSubjectRejectedMobile;
	@Wire
	private CommonPopupBandbox bnbTrxTypeRejectedMobile;
	@Wire
	private Tab tabNotificationMessageMobile;
	@Wire
	private Tab tabApprovalTaskMobile;
	@Wire
	private Tab tabOutgoingTaskMobile;
	@Wire
	private Tab tabRejectedTaskMobile;
	//MOBILE ------------------------------------------------

	private List<NotificationMessageDTO> listNotificationMessages = new ArrayList<NotificationMessageDTO>();
	private ListModelList<NotificationMessageDTO> listModelNotificationMessage;

	private List<TransactionType> listTrxType = new ArrayList<TransactionType>();

	private List<AVMApplicationDataDTO> listApprovalTask = new ArrayList<AVMApplicationDataDTO>();
	private ListModelList<AVMApplicationDataDTO> listModelApprovalTask;

	private List<AVMOutgoingReportDTO> listOutgoingTask = new ArrayList<AVMOutgoingReportDTO>();
	private ListModelList<AVMOutgoingReportDTO> listModelOutgoingTask;

	private List<AVMApplicationDataDTO> listRejectedTask = new ArrayList<AVMApplicationDataDTO>();
	private ListModelList<AVMApplicationDataDTO> listModelRejectedTask;

	private SecurityProfile profile;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		profile = securityServiceImpl.getSecurityProfile();
		initComponent();
		populateData();
	}

	private void initComponent() {
		btnRejectApproval.setLabel("Reject or Revise");
		if (Executions.getCurrent().getBrowser("mobile") != null) {
			tabboxMobile.setVisible(true);
			tabboxDekstop.setVisible(false);
			winWorkflowDashboard.setStyle("div.z-listbox-body{"
					+ "height: 500px !important;"
					+ "}");
		} else {
			tabboxMobile.setVisible(false);
			tabboxDekstop.setVisible(true);
		}
		
		lbxNotificationMessage.setMold("paging");
		lbxNotificationMessage.setPageSize(GlobalVariable.getMaxRowPerPage());

		lbxApprovalTask.setMold("paging");
		lbxApprovalTask.setPageSize(GlobalVariable.getMaxRowPerPage());
		loadTrxType(bnbTrxTypeApproval);

		lbxOutgoingTask.setMold("paging");
		lbxOutgoingTask.setPageSize(GlobalVariable.getMaxRowPerPage());
		loadTrxType(bnbTrxTypeOutgoing);

		lbxRejectedTask.setMold("paging");
		lbxRejectedTask.setPageSize(GlobalVariable.getMaxRowPerPage());
		loadTrxType(bnbTrxTypeRejected);
		
		//MOBILE ------------------------------------------------
			lbxNotificationMessageMobile.setMold("paging");
			lbxNotificationMessageMobile.setPageSize(GlobalVariable.getMaxRowPerPage());
	
			lbxApprovalTaskMobile.setMold("paging");
			lbxApprovalTaskMobile.setPageSize(GlobalVariable.getMaxRowPerPage());
			loadTrxType(bnbTrxTypeApprovalMobile);
	
			lbxOutgoingTaskMobile.setMold("paging");
			lbxOutgoingTaskMobile.setPageSize(GlobalVariable.getMaxRowPerPage());
			loadTrxType(bnbTrxTypeOutgoingMobile);
	
			lbxRejectedTaskMobile.setMold("paging");
			lbxRejectedTaskMobile.setPageSize(GlobalVariable.getMaxRowPerPage());
			loadTrxType(bnbTrxTypeRejectedMobile);
		//MOBILE ------------------------------------------------
	}

	class DelegateSearch implements SerializableSearchDelegate<TransactionType> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<TransactionType> findBySearchCriteria(
				String searchCriteria, int limit, int offset) {
			TransactionTypeExample example = new TransactionTypeExample();
			example.createCriteria().andTrxTypeLikeInsensitive(searchCriteria);
			example.setOrderByClause("UPPER(TRX_TYPE) ASC");
			listTrxType = transactionTypeServiceImpl
					.getTransactionTypeByExample(example, limit, offset);
			return listTrxType;
		}

		@Override
		public int countBySearchCriteria(String searchCriteria) {
			TransactionTypeExample example = new TransactionTypeExample();
			example.createCriteria().andTrxTypeLikeInsensitive(searchCriteria);
			return transactionTypeServiceImpl.countByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, TransactionType data) {
			keyValue.setKey(data.getTrxTypeId());
			keyValue.setDescription(data.getTrxType());
		}
	}

	private void loadTrxType(CommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearch());
	}

	public void populateData() {
		populateDataNotificationMessage();
		populateDataApprovalTask(null, 0L);
		populateDataOutgoingTask(null, 0L);
		populateDataRejectedTask(null, 0L);
		// start added for phase 2 training admin
		populateTrainingDashboard();
		// end added for phase 2 training admin
		
		//MOBILE ------------------------------------------------
			populateDataNotificationMessageMobile();
			populateDataApprovalTaskMobile(null, 0L);
			populateDataOutgoingTaskMobile(null, 0L);
			populateDataRejectedTaskMobile(null, 0L);
		//MOBILE ------------------------------------------------
	}

	@Listen("onClick = button#btnRefresh")
	public void refreshDashboard() {
		populateData();
	}
	
	// start added for phase 2 training admin
	@Wire
	private Div divTrainingAdmin;
	@Wire
	private Tab tabTraining;
	public void populateTrainingDashboard(){
		if (securityServiceImpl.getSecurityProfile() != null
				&& securityServiceImpl.getSecurityProfile().getSecurity() != null
				&& securityServiceImpl.getSecurityProfile().getSecurity().getResponsibilityName() != null
				&& securityServiceImpl.getSecurityProfile().getSecurity().getResponsibilityName()
						.equalsIgnoreCase(ResponsibilitiesType.DEVELOPMENT_ADMINISTRATOR_SELF_SERVICE.getValue())){
			tabTraining.setVisible(true);
			try{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("parentPage", this);
				Executions.createComponents("~./hcms/trainingadmin/tra_dashboard.zul", divTrainingAdmin, params);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		else {
			tabTraining.setVisible(false);
		}
	}
	// end added for phase 2 training admin

	public void populateDataNotificationMessage() {
		try {
			listNotificationMessages = templateMessageServiceImpl
					.getNotificationMessageByPersonUUID(profile.getPersonUUID() != null ? profile
							.getPersonUUID() : UUID
							.fromString(AVMApprovalManager.APPROVER_SWF_ADMIN));
			listModelNotificationMessage = new ListModelList<NotificationMessageDTO>(
					listNotificationMessages);
			listModelNotificationMessage.setMultiple(true);
			lbxNotificationMessage.setModel(listModelNotificationMessage);
			lbxNotificationMessage
					.setItemRenderer(getItemRendererNotificationMessage());
			tabNotificationMessage.setLabel(Labels
					.getLabel("swf.notificationMessage")
					+ " ("
					+ listModelNotificationMessage.getSize() + ")");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//MOBILE ------------------------------------------------
		public void populateDataNotificationMessageMobile() {
			try {
				listNotificationMessages = templateMessageServiceImpl
						.getNotificationMessageByPersonUUID(profile.getPersonUUID() != null ? profile
								.getPersonUUID() : UUID
								.fromString(AVMApprovalManager.APPROVER_SWF_ADMIN));
				listModelNotificationMessage = new ListModelList<NotificationMessageDTO>(
						listNotificationMessages);
				listModelNotificationMessage.setMultiple(true);
				lbxNotificationMessageMobile.setModel(listModelNotificationMessage);
				lbxNotificationMessageMobile
						.setItemRenderer(getItemRendererNotificationMessageMobile());
				tabNotificationMessageMobile.setLabel(Labels
						.getLabel("swf.notificationMessage")
						+ " ("
						+ listModelNotificationMessage.getSize() + ")");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	//MOBILE ------------------------------------------------

	public ListitemRenderer<NotificationMessageDTO> getItemRendererNotificationMessage() {
		ListitemRenderer<NotificationMessageDTO> listitemRenderer = new SerializableListItemRenderer<NotificationMessageDTO>() {

			private static final long serialVersionUID = -1952278343197706374L;

			@Override
			public void render(Listitem item,
					final NotificationMessageDTO data, final int index)
					throws Exception {
				new Listcell("").setParent(item);

				A link = new A(data.getSubject());
				link.setStyle("color:blue");
				link.addEventListener("onClick",
						new SerializableEventListener<Event>() {

							private static final long serialVersionUID = 1L;

							@Override
							public void onEvent(Event arg0) throws Exception {
								params = new HashMap<String, Object>();
								params.put("notificationMessage", data);
								params.put("uriParent",
										"~./hcms/workflow/workflow_dashboard.zul");
								params.put("workspaceMain", (NavigationTransactionForm) arg.get("workspaceMain"));
								Executions
										.createComponents(
												"~./hcms/workflow/workflow_notification_message_detail.zul",
												getSelf().getParent(), params);
								getSelf().detach();
							}
						});
				Listcell lc = new Listcell();
				link.setParent(lc);
				lc.setParent(item);

				new Listcell(data.getName()).setParent(item);
				new Listcell(
						FormatDateUI.formatDateTime(data.getReceivedTime()))
						.setParent(item);
			}
		};

		return listitemRenderer;
	}
	
	//MOBILE ------------------------------------------------
		public ListitemRenderer<NotificationMessageDTO> getItemRendererNotificationMessageMobile() {
			ListitemRenderer<NotificationMessageDTO> listitemRenderer = new SerializableListItemRenderer<NotificationMessageDTO>() {
	
				private static final long serialVersionUID = -1952278343197706374L;
	
				@Override
				public void render(Listitem item,
						final NotificationMessageDTO data, final int index)
						throws Exception {
					new Listcell("").setParent(item);
	
					A link = new A(data.getSubject());
					link.setStyle("color:blue");
					link.addEventListener("onClick",
							new SerializableEventListener<Event>() {
	
								private static final long serialVersionUID = 1L;
	
								@Override
								public void onEvent(Event arg0) throws Exception {
									params = new HashMap<String, Object>();
									params.put("notificationMessage", data);
									params.put("uriParent",
											"~./hcms/workflow/workflow_dashboard.zul");
									params.put("workspaceMain", (NavigationTransactionForm) arg.get("workspaceMain"));
									Executions
											.createComponents(
													"~./hcms/workflow/workflow_notification_message_detail.zul",
													getSelf().getParent(), params);
									getSelf().detach();
								}
							});
					Listcell lc = new Listcell();
					link.setParent(lc);
					lc.setParent(item);
	
				}
			};
	
			return listitemRenderer;
		}
	//MOBILE ------------------------------------------------

	@Listen("onClick = button#btnCloseNotif")
	public void closeNotifications() {
		if (listModelNotificationMessage.getSelection().size() > 0) {
			Messagebox.show(Labels.getLabel("common.confirmationClose"),
					Labels.getLabel("title.question"), Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
					new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onYes")) {
								logger.info("Size notification message : "
										+ listModelNotificationMessage
												.getSelection().size());
								templateMessageServiceImpl
										.updateMultipleMessageStatus(
												listModelNotificationMessage
														.getSelection(),
												MessageStatus.CLOSE.getCode());
								populateDataNotificationMessage();
							}
						}
					});
		}
	}
	
	//MOBILE ------------------------------------------------
		@Listen("onClick = button#btnCloseNotifMobile")
		public void closeNotificationsMobile() {
			if (listModelNotificationMessage.getSelection().size() > 0) {
				Messagebox.show(Labels.getLabel("common.confirmationClose"),
						Labels.getLabel("title.question"), Messagebox.YES
								| Messagebox.NO, Messagebox.QUESTION,
						new SerializableEventListener<Event>() {
	
							private static final long serialVersionUID = 1L;
	
							@Override
							public void onEvent(Event event) throws Exception {
								if (event.getName().equals("onYes")) {
									logger.info("Size notification message : "
											+ listModelNotificationMessage
													.getSelection().size());
									templateMessageServiceImpl
											.updateMultipleMessageStatus(
													listModelNotificationMessage
															.getSelection(),
													MessageStatus.CLOSE.getCode());
									populateDataNotificationMessageMobile();
								}
							}
						});
			}
		}
	//MOBILE ------------------------------------------------

	public void populateDataApprovalTask(String subject, Long transactionId) {
		try {
			listApprovalTask = avmAdapterServiceImpl.getPendingApproval(
					profile, subject, transactionId.intValue(),
					securityServiceImpl.getSecurityProfile()
							.getWorkspaceCompanyId(), null, null);
			listModelApprovalTask = new ListModelList<AVMApplicationDataDTO>(
					listApprovalTask);
			listModelApprovalTask.setMultiple(true);
			lbxApprovalTask.setModel(listModelApprovalTask);
			lbxApprovalTask.setItemRenderer(getItemRendererApprovalTask());
			tabApprovalTask.setLabel(Labels.getLabel("swf.approvalTask") + " ("
					+ listModelApprovalTask.getSize() + ")");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//MOBILE ------------------------------------------------
		public void populateDataApprovalTaskMobile(String subject, Long transactionId) {
			try {
				listApprovalTask = avmAdapterServiceImpl.getPendingApproval(
						profile, subject, transactionId.intValue(),
						securityServiceImpl.getSecurityProfile()
								.getWorkspaceCompanyId(), null, null);
				listModelApprovalTask = new ListModelList<AVMApplicationDataDTO>(
						listApprovalTask);
				listModelApprovalTask.setMultiple(true);
				lbxApprovalTaskMobile.setModel(listModelApprovalTask);
				lbxApprovalTaskMobile.setItemRenderer(getItemRendererApprovalTaskMobile());
				tabApprovalTaskMobile.setLabel(Labels.getLabel("swf.approvalTask") + " ("
						+ listModelApprovalTask.getSize() + ")");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	//MOBILE ------------------------------------------------

	public ListitemRenderer<AVMApplicationDataDTO> getItemRendererApprovalTask() {
		ListitemRenderer<AVMApplicationDataDTO> listitemRenderer = new SerializableListItemRenderer<AVMApplicationDataDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApplicationDataDTO data,
					final int index) throws Exception {
				new Listcell("").setParent(item);

				A link = new A(data.getSubject());
				link.setStyle("color:blue");
				link.addEventListener("onClick",
						new SerializableEventListener<Event>() {

							private static final long serialVersionUID = 1L;

							@Override
							public void onEvent(Event arg0) throws Exception {
								params = new HashMap<String, Object>();
								params.put("applicationData", data);
								params.put("personUUID",
										profile.getPersonUUID());
								Executions
										.createComponents(
												"~./hcms/workflow/workflow_approval_message_detail.zul",
												getSelf().getParent(), params);
								getSelf().detach();
							}
						});
				Listcell lc = new Listcell();
				link.setParent(lc);
				lc.setParent(item);

				new Listcell(data.getTransactionName()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getActionTime()))
						.setParent(item);
				new Listcell(data.getName()).setParent(item);
				new Listcell(data.getLastRemarks()).setParent(item);
				new Listcell(
						data.getApprovedTime() != null ? FormatDateUI
								.formatDateTime(data.getApprovedTime()) : "")
						.setParent(item);
			}
		};

		return listitemRenderer;
	}
	
	//MOBILE ------------------------------------------------
		public ListitemRenderer<AVMApplicationDataDTO> getItemRendererApprovalTaskMobile() {
			ListitemRenderer<AVMApplicationDataDTO> listitemRenderer = new SerializableListItemRenderer<AVMApplicationDataDTO>() {
	
				private static final long serialVersionUID = 1L;
	
				@Override
				public void render(Listitem item, final AVMApplicationDataDTO data,
						final int index) throws Exception {
					new Listcell("").setParent(item);
	
					A link = new A(data.getSubject());
					link.setStyle("color:blue");
					link.addEventListener("onClick",
							new SerializableEventListener<Event>() {
	
								private static final long serialVersionUID = 1L;
	
								@Override
								public void onEvent(Event arg0) throws Exception {
									params = new HashMap<String, Object>();
									params.put("applicationData", data);
									params.put("personUUID",
											profile.getPersonUUID());
									Executions
											.createComponents(
													"~./hcms/workflow/workflow_approval_message_detail.zul",
													getSelf().getParent(), params);
									getSelf().detach();
								}
							});
					Listcell lc = new Listcell();
					link.setParent(lc);
					lc.setParent(item);
				}
			};
	
			return listitemRenderer;
		}
	//MOBILE ------------------------------------------------

	@Listen("onClick = #btnApproveApproval")
	public void doApproveTask() {
		
		//add by shushuwar
		ListModel<AVMApplicationDataDTO> model = lbxApprovalTask.getListModel();
		ListModelList<AVMApplicationDataDTO> list = (ListModelList<AVMApplicationDataDTO>)model;
		if (list.getSelection().size() > 0) {
			if (doValidateTransactionType(list.getSelection())) {
				params = new HashMap<String, Object>();
				params.put("action", ActionType.APPROVE);
				params.put("selected", list.getSelection());
				params.put("personUUID", profile.getPersonUUID());
				Executions.createComponents(
						"~./hcms/workflow/workflow_confirmation_approval.zul",
						getSelf().getParent(), params);
				getSelf().detach();
			} else {
				Messagebox.show(Labels
						.getLabel("swf.err.validateMultipleApprovalProcess"));
			}
		}
		//end add by shushuwar
		
		/*if (listModelApprovalTask.getSelection().size() > 0) {
			if (doValidateTransactionType(listModelApprovalTask.getSelection())) {
				params = new HashMap<String, Object>();
				params.put("action", ActionType.APPROVE);
				params.put("selected", listModelApprovalTask.getSelection());
				params.put("personUUID", profile.getPersonUUID());
				Executions.createComponents(
						"~./hcms/workflow/workflow_confirmation_approval.zul",
						getSelf().getParent(), params);
				getSelf().detach();
			} else {
				Messagebox.show(Labels
						.getLabel("swf.err.validateMultipleApprovalProcess"));
			}
		}*/
	}

	/**
	 * @author Rachmad Agus D.
	 * @date 20140429
	 * @ticket 14040808581611 CR HCMS Sys Workflow
	 * 
	 */
	@Listen("onClick = #btnOpen")
	public void doOpenTask() {
		//add by shushuwar
	    ListModel<AVMApplicationDataDTO> model = lbxApprovalTask.getListModel();
	    ListModelList<AVMApplicationDataDTO> list = (ListModelList<AVMApplicationDataDTO>)model;
	    if (list.getSelection().size() > 0) {
			List<AVMApplicationDataDTO> avmApplicationDataDTOs = new ArrayList<>();

			for (AVMApplicationDataDTO avmApplicationDataDTO : list
					.getSelection()) {
				avmApplicationDataDTOs.add(avmApplicationDataDTO);
			}

			params = new HashMap<String, Object>();
			params.put("selected", avmApplicationDataDTOs);
			params.put("personUUID", profile.getPersonUUID());

			Executions.createComponents(
					"~./hcms/workflow/workflow_approval_message_detail.zul",
					getSelf().getParent(), params);
			getSelf().detach();
		} else {
			Messagebox.show("Belum ada Subject yang dipilih");
		}
	    // end add by shushuwar
	    
		/*if (listModelApprovalTask.getSelection().size() > 0) {
			List<AVMApplicationDataDTO> avmApplicationDataDTOs = new ArrayList<>();

			for (AVMApplicationDataDTO avmApplicationDataDTO : listModelApprovalTask
					.getSelection()) {
				avmApplicationDataDTOs.add(avmApplicationDataDTO);
			}

			params = new HashMap<String, Object>();
			params.put("selected", avmApplicationDataDTOs);
			params.put("personUUID", profile.getPersonUUID());

			Executions.createComponents(
					"~./hcms/workflow/workflow_approval_message_detail.zul",
					getSelf().getParent(), params);
			getSelf().detach();
		} else {
			Messagebox.show("Belum ada Subject yang dipilih");
		}*/
	}

	@Listen("onClick = #btnRejectApproval")
	public void doRejectTask() {
		//add by shushuwar
		ListModel<AVMApplicationDataDTO> model = lbxApprovalTask.getListModel();
		ListModelList<AVMApplicationDataDTO> list = (ListModelList<AVMApplicationDataDTO>)model;
		if (list.getSelection().size() > 0) {
			if (doValidateTransactionType(list.getSelection())) {
				params = new HashMap<String, Object>();
				params.put("action", ActionType.REJECT);
				params.put("selected", list.getSelection());
				params.put("personUUID", profile.getPersonUUID());
				Executions.createComponents(
						"~./hcms/workflow/workflow_confirmation_approval.zul",
						getSelf().getParent(), params);
				getSelf().detach();
			} else {
				Messagebox.show(Labels
						.getLabel("swf.err.validateMultipleApprovalProcess"));
			}
		}
		//end add by shushuwar
		/*if (listModelApprovalTask.getSelection().size() > 0) {
			if (doValidateTransactionType(listModelApprovalTask.getSelection())) {
				params = new HashMap<String, Object>();
				params.put("action", ActionType.REJECT);
				params.put("selected", listModelApprovalTask.getSelection());
				params.put("personUUID", profile.getPersonUUID());
				Executions.createComponents(
						"~./hcms/workflow/workflow_confirmation_approval.zul",
						getSelf().getParent(), params);
				getSelf().detach();
			} else {
				Messagebox.show(Labels
						.getLabel("swf.err.validateMultipleApprovalProcess"));
			}
		}*/
	}
	
	@Listen("onOK = textbox#txtFilterSubjectApproval")
	public void onFilterSubjectApproval() {
		String subject = txtFilterSubjectApproval.getValue();
		KeyValue keyValue = bnbTrxTypeApproval.getKeyValue();
		Long transactionId = keyValue != null ? (long) keyValue.getKey()
				: (long) 0;
		populateDataApprovalTask(subject, transactionId);
	}

	@Listen("onClose = #bnbTrxTypeApproval")
	public void onCloseBandboxApproval() throws FifException {
		String subject = txtFilterSubjectApproval.getValue();
		KeyValue keyValue = bnbTrxTypeApproval.getKeyValue();
		Long transactionId = keyValue != null ? (long) keyValue.getKey()
				: (long) 0;
		populateDataApprovalTask(subject, transactionId);
	}
	
	//MOBILE ------------------------------------------------
		@Listen("onClick = #btnApproveApprovalMobile")
		public void doApproveTaskMobile() {
			if (listModelApprovalTask.getSelection().size() > 0) {
				if (doValidateTransactionType(listModelApprovalTask.getSelection())) {
					params = new HashMap<String, Object>();
					params.put("action", ActionType.APPROVE);
					params.put("selected", listModelApprovalTask.getSelection());
					params.put("personUUID", profile.getPersonUUID());
					Executions.createComponents(
							"~./hcms/workflow/workflow_confirmation_approval.zul",
							getSelf().getParent(), params);
					getSelf().detach();
				} else {
					Messagebox.show(Labels
							.getLabel("swf.err.validateMultipleApprovalProcess"));
				}
			}
		}

		@Listen("onClick = #btnOpenMobile")
		public void doOpenTaskMobile() {
			if (listModelApprovalTask.getSelection().size() > 0) {
				List<AVMApplicationDataDTO> avmApplicationDataDTOs = new ArrayList<>();

				for (AVMApplicationDataDTO avmApplicationDataDTO : listModelApprovalTask
						.getSelection()) {
					avmApplicationDataDTOs.add(avmApplicationDataDTO);
				}

				params = new HashMap<String, Object>();
				params.put("selected", avmApplicationDataDTOs);
				params.put("personUUID", profile.getPersonUUID());

				Executions.createComponents(
						"~./hcms/workflow/workflow_approval_message_detail.zul",
						getSelf().getParent(), params);
				getSelf().detach();
			} else {
				Messagebox.show("Belum ada Subject yang dipilih");
			}
		}

		@Listen("onClick = #btnRejectApprovalMobile")
		public void doRejectTaskMobile() {
			if (listModelApprovalTask.getSelection().size() > 0) {
				if (doValidateTransactionType(listModelApprovalTask.getSelection())) {
					params = new HashMap<String, Object>();
					params.put("action", ActionType.REJECT);
					params.put("selected", listModelApprovalTask.getSelection());
					params.put("personUUID", profile.getPersonUUID());
					Executions.createComponents(
							"~./hcms/workflow/workflow_confirmation_approval.zul",
							getSelf().getParent(), params);
					getSelf().detach();
				} else {
					Messagebox.show(Labels
							.getLabel("swf.err.validateMultipleApprovalProcess"));
				}
			}
		}
		
		@Listen("onOK = textbox#txtFilterSubjectApprovalMobile")
		public void onFilterSubjectApprovalMobile() {
			String subject = txtFilterSubjectApprovalMobile.getValue();
			KeyValue keyValue = bnbTrxTypeApprovalMobile.getKeyValue();
			Long transactionId = keyValue != null ? (long) keyValue.getKey()
					: (long) 0;
			populateDataApprovalTaskMobile(subject, transactionId);
		}

		@Listen("onClose = #bnbTrxTypeApprovalMobile")
		public void onCloseBandboxApprovalMobile() throws FifException {
			String subject = txtFilterSubjectApprovalMobile.getValue();
			KeyValue keyValue = bnbTrxTypeApprovalMobile.getKeyValue();
			Long transactionId = keyValue != null ? (long) keyValue.getKey()
					: (long) 0;
			populateDataApprovalTaskMobile(subject, transactionId);
		}
	//MOBILE ------------------------------------------------

	public void populateDataOutgoingTask(String subject, Long transactionId) {
		try {
			listOutgoingTask = avmAdapterServiceImpl
					.getAVMOutgoingReport(
							profile.getPersonUUID() != null ? profile
									.getPersonUUID()
									: UUID.fromString(AVMApprovalManager.APPROVER_SWF_ADMIN),
							transactionId.intValue(), subject, 0, 0, null, null);
			listModelOutgoingTask = new ListModelList<AVMOutgoingReportDTO>(
					listOutgoingTask);
			lbxOutgoingTask.setModel(listModelOutgoingTask);
			lbxOutgoingTask.setItemRenderer(getItemRendererOutgoingTask());
			tabOutgoingTask.setLabel(Labels.getLabel("swf.outgoingTask") + " ("
					+ listModelOutgoingTask.getSize() + ")");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//MOBILE ------------------------------------------------
		public void populateDataOutgoingTaskMobile(String subject, Long transactionId) {
			try {
				listOutgoingTask = avmAdapterServiceImpl
						.getAVMOutgoingReport(
								profile.getPersonUUID() != null ? profile
										.getPersonUUID()
										: UUID.fromString(AVMApprovalManager.APPROVER_SWF_ADMIN),
								transactionId.intValue(), subject, 0, 0, null, null);
				listModelOutgoingTask = new ListModelList<AVMOutgoingReportDTO>(
						listOutgoingTask);
				lbxOutgoingTaskMobile.setModel(listModelOutgoingTask);
				lbxOutgoingTaskMobile.setItemRenderer(getItemRendererOutgoingTaskMobile());
				tabOutgoingTaskMobile.setLabel(Labels.getLabel("swf.outgoingTask") + " ("
						+ listModelOutgoingTask.getSize() + ")");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	//MOBILE ------------------------------------------------

	public ListitemRenderer<AVMOutgoingReportDTO> getItemRendererOutgoingTask() {
		ListitemRenderer<AVMOutgoingReportDTO> listitemRenderer = new SerializableListItemRenderer<AVMOutgoingReportDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMOutgoingReportDTO data,
					final int index) throws Exception {
				new Listcell(data.getSubject()).setParent(item);
				new Listcell(data.getTransactionName()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data
						.getSubmittedTime())).setParent(item);

				List<String> approverList = new ArrayList<String>();
				for (AVMApprover approver : data.getApproverList()) {
					approverList.add(avmAdapterServiceImpl.getName(
							approver.getApproverId(), null));
				}
				new Listcell(StringUtils.join(approverList, ", "))
						.setParent(item);

				StringBuilder curretApprovalState = new StringBuilder();
				curretApprovalState.append("Minimum Required : ")
						.append(data.getMinimumRequiredApproval())
						.append(" Remaining Approval : ")
						.append(data.getRemainingApproval())
						.append(" Complete Approval : ")
						.append(data.getCompleteApproval());
				new Listcell(curretApprovalState.toString()).setParent(item);
			}
		};

		return listitemRenderer;
	}
	
	//MOBILE ------------------------------------------------
		public ListitemRenderer<AVMOutgoingReportDTO> getItemRendererOutgoingTaskMobile() {
			ListitemRenderer<AVMOutgoingReportDTO> listitemRenderer = new SerializableListItemRenderer<AVMOutgoingReportDTO>() {
	
				private static final long serialVersionUID = 1L;
	
				@Override
				public void render(Listitem item, final AVMOutgoingReportDTO data,
						final int index) throws Exception {
					new Listcell(data.getSubject()).setParent(item);
				}
			};
	
			return listitemRenderer;
		}
	//MOBILE ------------------------------------------------

	@Listen("onOK = textbox#txtFilterSubjectOutgoing")
	public void onFilterSubjectOutgoing() {
		String subject = txtFilterSubjectOutgoing.getValue();
		KeyValue keyValue = bnbTrxTypeOutgoing.getKeyValue();
		Long transactionId = keyValue != null ? (long) keyValue.getKey()
				: (long) 0;
		populateDataOutgoingTask(subject, transactionId);
	}

	@Listen("onClose = #bnbTrxTypeOutgoing")
	public void onCloseBandboxOutgoing() throws FifException {
		String subject = txtFilterSubjectOutgoing.getValue();
		KeyValue keyValue = bnbTrxTypeOutgoing.getKeyValue();
		Long transactionId = keyValue != null ? (long) keyValue.getKey()
				: (long) 0;
		populateDataOutgoingTask(subject, transactionId);
	}
	
	//MOBILE ------------------------------------------------
		@Listen("onOK = textbox#txtFilterSubjectOutgoingMobile")
		public void onFilterSubjectOutgoingMobile() {
			String subject = txtFilterSubjectOutgoingMobile.getValue();
			KeyValue keyValue = bnbTrxTypeOutgoingMobile.getKeyValue();
			Long transactionId = keyValue != null ? (long) keyValue.getKey()
					: (long) 0;
			populateDataOutgoingTaskMobile(subject, transactionId);
		}
	
		@Listen("onClose = #bnbTrxTypeOutgoingMobile")
		public void onCloseBandboxOutgoingMobile() throws FifException {
			String subject = txtFilterSubjectOutgoingMobile.getValue();
			KeyValue keyValue = bnbTrxTypeOutgoingMobile.getKeyValue();
			Long transactionId = keyValue != null ? (long) keyValue.getKey()
					: (long) 0;
			populateDataOutgoingTaskMobile(subject, transactionId);
		}
	//MOBILE ------------------------------------------------

	public void populateDataRejectedTask(String subject, Long transactionId) {
		try {
			listRejectedTask = avmAdapterServiceImpl.getRejectedTransaction(
					profile.getPersonUUID(), transactionId.intValue(), subject);
			listModelRejectedTask = new ListModelList<AVMApplicationDataDTO>(
					listRejectedTask);
			listModelRejectedTask.setMultiple(true);
			lbxRejectedTask.setModel(listModelRejectedTask);
			lbxRejectedTask.setItemRenderer(getItemRendererRejectedTask());
			tabRejectedTask.setLabel("Rejected & Revised Task" + " ("
					+ listModelRejectedTask.getSize() + ")");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//MOBILE ------------------------------------------------
		public void populateDataRejectedTaskMobile(String subject, Long transactionId) {
			try {
				listRejectedTask = avmAdapterServiceImpl.getRejectedTransaction(
						profile.getPersonUUID(), transactionId.intValue(), subject);
				listModelRejectedTask = new ListModelList<AVMApplicationDataDTO>(
						listRejectedTask);
				listModelRejectedTask.setMultiple(true);
				lbxRejectedTaskMobile.setModel(listModelRejectedTask);
				lbxRejectedTaskMobile.setItemRenderer(getItemRendererRejectedTaskMobile());
				tabRejectedTaskMobile.setLabel(Labels.getLabel("swf.rejectedTask") + " ("
						+ listModelRejectedTask.getSize() + ")");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	//MOBILE ------------------------------------------------

	public ListitemRenderer<AVMApplicationDataDTO> getItemRendererRejectedTask() {
		ListitemRenderer<AVMApplicationDataDTO> listitemRenderer = new SerializableListItemRenderer<AVMApplicationDataDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApplicationDataDTO data,
					final int index) throws Exception {
				new Listcell("").setParent(item);
				new Listcell(data.getSubject()).setParent(item);
				new Listcell(data.getTransactionName()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getSubmitTime()))
						.setParent(item);
				new Listcell(data.getName()).setParent(item);
				new Listcell(data.getRemarks()).setParent(item);
			}
		};

		return listitemRenderer;
	}
	
	//MOBILE ------------------------------------------------
		public ListitemRenderer<AVMApplicationDataDTO> getItemRendererRejectedTaskMobile() {
			ListitemRenderer<AVMApplicationDataDTO> listitemRenderer = new SerializableListItemRenderer<AVMApplicationDataDTO>() {
	
				private static final long serialVersionUID = 1L;
	
				@Override
				public void render(Listitem item, final AVMApplicationDataDTO data,
						final int index) throws Exception {
					new Listcell("").setParent(item);
					new Listcell(data.getSubject()).setParent(item);
				}
			};
	
			return listitemRenderer;
		}
	//MOBILE ------------------------------------------------

	@Listen("onOK = textbox#txtFilterSubjectRejected")
	public void onFilterSubjectRejected() {
		String subject = txtFilterSubjectRejected.getValue();
		KeyValue keyValue = bnbTrxTypeRejected.getKeyValue();
		Long transactionId = keyValue != null ? (long) keyValue.getKey()
				: (long) 0;
		populateDataRejectedTask(subject, transactionId);
	}

	@Listen("onClose = #bnbTrxTypeRejected")
	public void onCloseBandboxRejected() throws FifException {
		String subject = txtFilterSubjectRejected.getValue();
		KeyValue keyValue = bnbTrxTypeRejected.getKeyValue();
		Long transactionId = keyValue != null ? (long) keyValue.getKey()
				: (long) 0;
		populateDataRejectedTask(subject, transactionId);
	}

	@Listen("onClick = button#btnCloseRejected")
	public void closeRejectedTransaction() {
		if (listModelRejectedTask.getSelection().size() > 0) {
			Messagebox.show(Labels.getLabel("common.confirmationClose"),
					Labels.getLabel("title.question"), Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
					new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onYes")) {
								logger.info("Size rejected task : "
										+ listModelRejectedTask.getSelection()
												.size());
								try {
									avmAdapterServiceImpl
											.closeMultipleTransaction(listModelRejectedTask
													.getSelection());
									populateDataRejectedTask("", 0L);
								} catch (FifException e) {
									logger.error(e.getMessage(), e);
								}
							}
						}
					});
		}
	}
	
	//MOBILE ------------------------------------------------
		@Listen("onOK = textbox#txtFilterSubjectRejectedMobile")
		public void onFilterSubjectRejectedMobile() {
			String subject = txtFilterSubjectRejectedMobile.getValue();
			KeyValue keyValue = bnbTrxTypeRejectedMobile.getKeyValue();
			Long transactionId = keyValue != null ? (long) keyValue.getKey()
					: (long) 0;
			populateDataRejectedTaskMobile(subject, transactionId);
		}
	
		@Listen("onClose = #bnbTrxTypeRejectedMobile")
		public void onCloseBandboxRejectedMobile() throws FifException {
			String subject = txtFilterSubjectRejectedMobile.getValue();
			KeyValue keyValue = bnbTrxTypeRejectedMobile.getKeyValue();
			Long transactionId = keyValue != null ? (long) keyValue.getKey()
					: (long) 0;
			populateDataRejectedTaskMobile(subject, transactionId);
		}
	
		@Listen("onClick = button#btnCloseRejectedMobile")
		public void closeRejectedTransactionMobile() {
			if (listModelRejectedTask.getSelection().size() > 0) {
				Messagebox.show(Labels.getLabel("common.confirmationClose"),
						Labels.getLabel("title.question"), Messagebox.YES
								| Messagebox.NO, Messagebox.QUESTION,
						new SerializableEventListener<Event>() {
	
							private static final long serialVersionUID = 1L;
	
							@Override
							public void onEvent(Event event) throws Exception {
								if (event.getName().equals("onYes")) {
									logger.info("Size rejected task : "
											+ listModelRejectedTask.getSelection()
													.size());
									try {
										avmAdapterServiceImpl
												.closeMultipleTransaction(listModelRejectedTask
														.getSelection());
										populateDataRejectedTaskMobile("", 0L);
									} catch (FifException e) {
										logger.error(e.getMessage(), e);
									}
								}
							}
						});
			}
		}
	//MOBILE ------------------------------------------------

	private boolean doValidateTransactionType(
			Set<AVMApplicationDataDTO> listApproval) {
		int index = 0;
		int trxType = 0;
		Iterator<AVMApplicationDataDTO> iterator = new LinkedHashSet<AVMApplicationDataDTO>(
				listApproval).iterator();
		while (iterator.hasNext()) {
			AVMApplicationDataDTO applicationData = iterator.next();
			if (index == 0) {
				trxType = applicationData.getTrxType();
			}

			if (trxType != applicationData.getTrxType()) {
				return false;
			}
			index++;
		}
		return true;
	}
}
