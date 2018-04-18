package co.id.fifgroup.systemworkflow.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
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
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.constants.WorkflowReference;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowConfirmationApprovalComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowConfirmationApprovalComposer.class);

	private WorkflowConfirmationApprovalComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	
	@Wire
	private Listbox lbxMultipleTask;
	@Wire
	private Textbox txtRemark;
	@Wire
	private Button btnApprove;
	@Wire
	private Button btnReject;
	
	private Set<AVMApplicationData> selected;
	private UUID actualApproverId;
	private ActionType action;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateData();
	}
	
	public void populateData() {
		actualApproverId = (UUID) arg.get("personUUID");
		selected = (Set<AVMApplicationData>) arg.get("selected");
		action = (ActionType) arg.get("action");
		if (action.equals(ActionType.APPROVE)) {
			btnApprove.setVisible(true);
			btnReject.setVisible(false);
		} else {
			btnApprove.setVisible(false);
			btnReject.setVisible(true);
		}
		ListModelList<AVMApplicationData> listModelAVMApplicationData = new ListModelList<>(selected);
		lbxMultipleTask.setModel(listModelAVMApplicationData);
		lbxMultipleTask.setItemRenderer(getItemRendererApprovalTask());
	}
	
	public ListitemRenderer<AVMApplicationData> getItemRendererApprovalTask() {
		ListitemRenderer<AVMApplicationData> listitemRenderer = new SerializableListItemRenderer<AVMApplicationData>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApplicationData data, final int index)
					throws Exception {
				new Listcell((index+1) +"").setParent(item);
				new Listcell(data.getSubject()).setParent(item);
				new Listcell(TrxType.fromCode((long)data.getTrxType()).getMessage()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getActionTime())).setParent(item);
				AVMApprovalHistory approvalHistory = avmAdapterServiceImpl.getLastApprovalHistory(data.getAvmTrxId());
				if (approvalHistory != null) {
					String fullName = avmAdapterServiceImpl.getActualName(approvalHistory, data.getCompanyId());
					new Listcell(fullName).setParent(item);
					new Listcell(approvalHistory.getRemarks()).setParent(item);
					new Listcell(FormatDateUI.formatDateTime(approvalHistory.getAvmActionTimeStamp())).setParent(item);					
				} else {
					new Listcell("").setParent(item);
					new Listcell("").setParent(item);
					new Listcell("").setParent(item);
				}
			}
		};
		
		return listitemRenderer;
	}
	
	@Listen("onClick = button#btnApprove")
	public void doApprove() {
		Messagebox.show(Labels.getLabel("common.confirmationApprove"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						if (doValidateTransactionType(selected)) {
							AVMReturnCode returnCode = avmAdapterServiceImpl.doMultipleApproveTransaction(selected, txtRemark.getValue(), actualApproverId);
							if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_COMPLETED) 
									|| returnCode.equals(AVMReturnCode.TRANSACTION_IS_IN_PROGRESS)) {
								Messagebox.show(Labels.getLabel("swf.approveSuccessfully"));
								Executions.createComponents("~./hcms/workflow/workflow_dashboard.zul", getSelf().getParent(), null);
								getSelf().detach();				
							} else {
								logger.info(returnCode.getMessage());
							}				
						}
					} catch (ValidationException ve) {
						Messagebox.show(ve.getConstraintViolations().get(WorkflowReference.ERROR_MESSAGE.name()));
					} catch (WrongValueException e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(Labels.getLabel("swf.err.processError"));
					} catch (FifException e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(Labels.getLabel("swf.err.processError"));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(Labels.getLabel("swf.err.processError"));
					}		
				}
			}
		});	
		
	}
	
	@Listen("onClick = button#btnReject")
	public void doReject() {
		Messagebox.show(Labels.getLabel("common.confirmationReject"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						if (doValidateTransactionType(selected)) {
							AVMReturnCode returnCode = avmAdapterServiceImpl.doMultipleRejectTransaction(selected, txtRemark.getValue(), actualApproverId);
							
							if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_REJECTED)) {
								Messagebox.show(Labels.getLabel("swf.rejectSuccessfully"));
								Executions.createComponents("~./hcms/workflow/workflow_dashboard.zul", getSelf().getParent(), null);
								getSelf().detach();				
							} else {
								logger.info(returnCode.getMessage());
							}
						}
					} catch (ValidationException ve) {
						Messagebox.show(ve.getConstraintViolations().get(WorkflowReference.ERROR_MESSAGE.name()));
					} catch (WrongValueException e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(Labels.getLabel("swf.err.processError"));
					} catch (FifException e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(Labels.getLabel("swf.err.processError"));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(Labels.getLabel("swf.err.processError"));
					}		
				}
			}
		});	
		
	}
	
	private boolean doValidateTransactionType(Set<AVMApplicationData> listApproval) throws FifException {
		int index = 0;
		int trxType = 0;
		Iterator<AVMApplicationData> iterator = new LinkedHashSet<AVMApplicationData>(listApproval).iterator();
		while (iterator.hasNext()) {
			AVMApplicationData applicationData = iterator.next();
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
	
	@Listen("onClick = button#btnCancel")
	public void doCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					Executions.createComponents("~./hcms/workflow/workflow_dashboard.zul", getSelf().getParent(), null);
					getSelf().detach();
				}
			}
		});	
		
	}
}
