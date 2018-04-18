package co.id.fifgroup.systemworkflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.WorkflowReference;
import co.id.fifgroup.systemworkflow.dto.AVMApplicationDataDTO;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.util.Serialization;
import co.id.fifgroup.core.ui.NavigationTransactionForm;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.notification.domain.NotificationMessage;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowApprovalMessageDetailComposer extends
		SelectorComposer<Window> implements WorkflowTransaction {

	private static final long serialVersionUID = 1L;

	private UUID actualApproverId;
	private AVMApplicationData applicationData;
	private AVMApprovalHistory approval;
	private int approverLevel;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@Wire
	private Button btnApprove;
	@Wire
	private Button btnNext;
	@Wire
	private Button btnPrev;
	@Wire
	private Button btnReject;
	@Wire
	private Hbox hboxLinkDetail;
	@Wire
	private Hbox hbxInfo;
	@Wire
	private Html htmlDetailMessage;
	@Wire
	private Label lblFrom;
	@Wire
	private Label lblPage;
	@Wire
	private Label lblPath;
	@Wire
	private Label lblReceivedTime;
	@Wire
	private Label lblSubject;
	@Wire
	private Label lblTo;
	@Wire
	private Listbox lbxApprovalHistory;
	private Logger logger = LoggerFactory
			.getLogger(WorkflowApprovalMessageDetailComposer.class);
	private NotificationMessage message;
	private Map<String, Object> params = new HashMap<String, Object>();
	private List<AVMApplicationDataDTO> selectedApplicationDataDTOs;
	private int selectedIndex;
	private byte[] serializedNewData;
	@WireVariable(rewireOnActivate = true)
	private transient TemplateMessageService templateMessageServiceImpl;
	private WorkflowApprovalMessageDetailComposer thisComposer = this;
	@Wire
	private Textbox txtRemarks;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateData();
		setPageElements();
	}

	@Listen("onClick = button#btnApprove")
	public void doApprove() throws FifException {
//		(GAL - [15091510574968] Perbaikan NPK double setelah approve) 
		if(avmAdapterServiceImpl.checkStatusIsApproved(applicationData.getAvmTrxId()) != 1){
			Messagebox.show(Labels.getLabel("common.dataHasBeenApproved"));
		}else {
			//Messagebox.show(Labels.getLabel("common.confirmationApprove"),
					Messagebox.show("Are you sure to save this data ?",
					Labels.getLabel("title.question"), Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
					new SerializableEventListener<Event>() {
	
						private static final long serialVersionUID = 1L;
	
						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onYes")) {
								try {
									AVMReturnCode returnCode = avmAdapterServiceImpl
											.doApproveTransaction(applicationData
													.getAvmTrxId(), approval
													.getApproverId(), txtRemarks
													.getValue(), serializedNewData,
													applicationData
															.getSerializedData(),
													actualApproverId);
									if (returnCode
											.equals(AVMReturnCode.TRANSACTION_IS_COMPLETED)
											|| returnCode
													.equals(AVMReturnCode.TRANSACTION_IS_IN_PROGRESS)) {
										if (returnCode
												.equals(AVMReturnCode.TRANSACTION_IS_COMPLETED)) {
											params = new HashMap<String, Object>();
											params.put("applicationData",
													applicationData);
											
											Window window = (Window) Executions
													.createComponents(
															"~./hcms/workflow/workflow_send_notification_to_others.zul",
															getSelf().getParent(),
															params);
											window.setClosable(true);
											window.setMaximized(false);
											window.setWidth("50%");
											window.doModal();
										}
	
										if (selectedApplicationDataDTOs != null
												&& !selectedApplicationDataDTOs
														.isEmpty()) {
											selectedApplicationDataDTOs.get(
													selectedIndex).setProcessed(
													true);
	
											if (serializedNewData != null) {
												selectedApplicationDataDTOs.get(
														selectedIndex)
														.setSerializedData(
																serializedNewData);
											}
	
											if (selectedIndex < (selectedApplicationDataDTOs
													.size() - 1)) {
												doOpenNextTask();
											} else {
												setPageElements();
											}
										} else {
											Messagebox.show("Data has been saved");
											Executions
													.createComponents(
															"~./hcms/workflow/workflow_dashboard.zul",
															getSelf().getParent(),
															null);
											getSelf().detach();
										}
									} else {
										logger.info(returnCode.getMessage());
										Messagebox.show(returnCode.getMessage());
									}
								} catch (ValidationException ve) {
									if (ve.getConstraintViolations().containsKey(
											WorkflowReference.ERROR_MESSAGE.name()))
										Messagebox
												.show(ve.getConstraintViolations()
														.get(WorkflowReference.ERROR_MESSAGE
																.name()));
									else
										Messagebox.show(ve.getAllMessages());
								} catch (WrongValueException e) {
									logger.error(e.getMessage(), e);
									Messagebox.show(Labels
											.getLabel("swf.err.processError"));
								} catch (FifException e) {
									logger.error(e.getMessage(), e);
									Messagebox.show(Labels
											.getLabel("swf.err.processError"));
								} catch (Exception e) {
									logger.error(e.getMessage(), e);
									Messagebox.show(Labels
											.getLabel("swf.err.processError"));
								}
							}
						}
					});
		}
		
	}

	@Listen("onClick = button#btnCancel")
	public void doCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"),
				Labels.getLabel("title.question"), Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onYes")) {
							params = new HashMap<String, Object>();
							params.put("workspaceMain", (NavigationTransactionForm) arg.get("workspaceMain"));
							Executions.createComponents(
									"~./hcms/workflow/workflow_dashboard.zul",
									getSelf().getParent(), params);
							getSelf().detach();
						}
					}
				});

	}

	@Listen("onClick = #btnNext")
	public void doOpenNextTask() throws FifException {
		selectedIndex++;
		applicationData = selectedApplicationDataDTOs.get(selectedIndex);
		serializedNewData = null;

		populateData();
		setPageElements();
	}

	@Listen("onClick = #btnPrev")
	public void doOpenPrevTask() throws FifException {
		selectedIndex--;
		applicationData = selectedApplicationDataDTOs.get(selectedIndex);
		serializedNewData = null;

		populateData();
		setPageElements();
	}

	@Listen("onClick = button#btnReject")
	public void doReject() {
		//Messagebox.show(Labels.getLabel("common.confirmationReject"),
		Messagebox.show("Are you sure to save this data ?",
				Labels.getLabel("title.question"), Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onYes")) {
							try {
								AVMReturnCode returnCode = avmAdapterServiceImpl
										.doRejectTransaction(applicationData
												.getAvmTrxId(), approval
												.getApproverId(), txtRemarks
												.getValue(), applicationData
												.getSerializedData(),
												actualApproverId);
								if (returnCode
										.equals(AVMReturnCode.TRANSACTION_IS_REJECTED)) {
									/*Messagebox.show(Labels
											.getLabel("swf.rejectSuccessfully"));*/
									Messagebox.show("Data has been saved");

									if (selectedApplicationDataDTOs != null
											&& !selectedApplicationDataDTOs
													.isEmpty()) {
										selectedApplicationDataDTOs.get(
												selectedIndex).setProcessed(
												true);

										if (selectedIndex < (selectedApplicationDataDTOs
												.size() - 1)) {
											doOpenNextTask();
										} else {
											setPageElements();
										}
									} else {
										Executions
												.createComponents(
														"~./hcms/workflow/workflow_dashboard.zul",
														getSelf().getParent(),
														null);
										getSelf().detach();
									}
								} else {
									logger.info(returnCode.getMessage());
									Messagebox.show(returnCode.getMessage());
								}
							} catch (ValidationException ve) {
								Messagebox.show(ve.getConstraintViolations()
										.get(WorkflowReference.ERROR_MESSAGE
												.name()));
							} catch (WrongValueException e) {
								logger.error(e.getMessage(), e);
								Messagebox.show(Labels
										.getLabel("swf.err.processError"));
							} catch (FifException e) {
								logger.error(e.getMessage(), e);
								Messagebox.show(Labels
										.getLabel("swf.err.processError"));
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
								Messagebox.show(Labels
										.getLabel("swf.err.processError"));
							}
						}
					}
				});

	}

	private void getApprovalHistory(UUID avmTrxId) {
		try {
			List<AVMApprovalHistory> listHistory = avmAdapterServiceImpl
					.getApprovalHistoryByAVMTrxId(avmTrxId);
			ListModelList<AVMApprovalHistory> listModel = new ListModelList<AVMApprovalHistory>(
					listHistory);
			lbxApprovalHistory.setModel(listModel);
			lbxApprovalHistory.setItemRenderer(getItemRenderer());

			txtRemarks.setValue(null);
			txtRemarks.setDisabled(false);

			for (AVMApprovalHistory avmApprovalHistory : listHistory) {
				if (avmApprovalHistory.getApproverId().equals(actualApproverId)) {
					if (avmApprovalHistory.getRemarks() != null) {
						txtRemarks.setValue(avmApprovalHistory.getRemarks());
						txtRemarks.setDisabled(true);
						break;
					}
				}
			}
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public ListitemRenderer<AVMApprovalHistory> getItemRenderer() {
		ListitemRenderer<AVMApprovalHistory> listitemRenderer = new SerializableListItemRenderer<AVMApprovalHistory>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApprovalHistory data,
					final int index) throws Exception {
				new Listcell((index + 1) + "").setParent(item);
				new Listcell(avmAdapterServiceImpl.getActualName(data, 1L))
						.setParent(item);
				new Listcell(data.getRemarks()).setParent(item);
				String actionTime = data.getAvmActionTimeStamp() != null ? FormatDateUI
						.formatDateTime(data.getAvmActionTimeStamp()) : "";
				new Listcell(actionTime).setParent(item);
			}
		};

		return listitemRenderer;
	}

	@Listen("onClick = #linkDetailTrx")
	public void onClickLink() {
		try {
			Object application = Serialization
					.deserializedObject(applicationData.getSerializedData());
			if (serializedNewData != null) {
				application = Serialization
						.deserializedObject(serializedNewData);
			}
			params = new HashMap<String, Object>();
			params.put("applicationData", application);
			params.put("parentComposer", thisComposer);
			params.put("isEditable", true);
			params.put("approverLevel", approverLevel);
			Window window = (Window) Executions.createComponents(
					message.getUrlDetail(), getSelf().getParent(), params);
			window.setClosable(true);
			window.setMaximized(false);
			window.setWidth("80%");
			window.setHeight("600px");
			window.setContentStyle("overflow:auto");
			window.doModal();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void populateData() throws FifException {
		try {
			actualApproverId = (UUID) arg.get("personUUID");
			selectedApplicationDataDTOs = (List<AVMApplicationDataDTO>) arg
					.get("selected");

			// Rachmad Agus D. 20140425
			if (selectedApplicationDataDTOs != null
					&& !selectedApplicationDataDTOs.isEmpty()) {
				applicationData = selectedApplicationDataDTOs
						.get(selectedIndex);
			} else {
				applicationData = (AVMApplicationData) arg
						.get("applicationData");
			}

			approval = avmAdapterServiceImpl.getRecentApprovalHistory(
					applicationData.getAvmTrxId(),
					applicationData.getSequenceNumber());
			message = templateMessageServiceImpl
					.getNotificationMessageByHistorySeqNumber((long) applicationData
							.getSequenceNumber());
			lblSubject.setValue(message.getSubject());
			lblFrom.setValue(avmAdapterServiceImpl.getName(message.getFromId(),
					null));
			lblReceivedTime.setValue(FormatDateUI.formatDateTime(message
					.getReceivedTime()));
			lblTo.setValue(avmAdapterServiceImpl.getName(message.getToId(),
					null));
			htmlDetailMessage.setContent(message.getContentMessage());

			if (message.getUrlDetail() != null) {
				hboxLinkDetail.setVisible(true);
			} else {
				// Rachmad Agus D. 14040808581611 - CR HCMS Sys Workflow
				hboxLinkDetail.setVisible(false);
			}

			lblPath.setValue(avmAdapterServiceImpl
					.getApprovalPath(applicationData));
			approverLevel = avmAdapterServiceImpl
					.getApproverLevel(applicationData);
			getApprovalHistory(applicationData.getAvmTrxId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void setNewTransactionObject(Object application) {
		serializedNewData = Serialization.serializeObject(application);
	}

	private void setPageElements() {
		btnApprove.setDisabled(false);
		btnReject.setDisabled(false);
		hbxInfo.setVisible(false);

		if (selectedApplicationDataDTOs != null
				&& !selectedApplicationDataDTOs.isEmpty()) {
			if (selectedApplicationDataDTOs.get(selectedIndex).isProcessed()) {
				btnApprove.setDisabled(true);
				btnReject.setDisabled(true);
			}

			btnPrev.setVisible(true);
			btnNext.setVisible(true);
			lblPage.setVisible(true);

			btnPrev.setDisabled(false);
			btnNext.setDisabled(false);
			lblPage.setValue((selectedIndex + 1) + " of "
					+ selectedApplicationDataDTOs.size());

			if (selectedIndex == 0) {
				btnPrev.setDisabled(true);
			}

			if (selectedIndex == selectedApplicationDataDTOs.size() - 1) {
				btnNext.setDisabled(true);
			}

			hbxInfo.setVisible(true);
		} else {
			btnPrev.setVisible(false);
			btnNext.setVisible(false);
			lblPage.setVisible(false);
		}
	}

}
