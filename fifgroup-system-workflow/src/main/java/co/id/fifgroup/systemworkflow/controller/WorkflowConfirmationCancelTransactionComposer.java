package co.id.fifgroup.systemworkflow.controller;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.systemworkflow.constants.TrxStatus;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowConfirmationCancelTransactionComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowConfirmationCancelTransactionComposer.class);

	private WorkflowConfirmationCancelTransactionComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	
	@Wire
	private Listbox lbxMultipleTask;
	@Wire
	private Textbox txtRemark;
	
	private Set<AVMOutgoingReport> selected;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		selected = (Set<AVMOutgoingReport>) arg.get("selected");
		ListModelList<AVMOutgoingReport> listModelAVMOutgoingReport = new ListModelList<>(selected);
		lbxMultipleTask.setModel(listModelAVMOutgoingReport);
		lbxMultipleTask.setItemRenderer(getItemRenderer());
	}
	
	private ListitemRenderer<AVMOutgoingReport> getItemRenderer() {
		ListitemRenderer<AVMOutgoingReport> listitemRenderer = new SerializableListItemRenderer<AVMOutgoingReport>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMOutgoingReport data, final int index)
					throws Exception {
				
				new Listcell((index+1)+"").setParent(item);
				
				String companyName = avmAdapterServiceImpl.getCompanyName(data.getCompanyId());
				new Listcell(companyName).setParent(item);
				new Listcell(data.getSubject()).setParent(item);
				new Listcell(TrxType.fromCode((long) data.getTrxType()).getMessage()).setParent(item);
				new Listcell(TrxStatus.fromCode(data.getTrxStatus()).getMessage()).setParent(item);
				final UUID submitterId = avmAdapterServiceImpl.getTransactionSubmitter(data.getAvmTrxId());
				new Listcell(avmAdapterServiceImpl.getName(submitterId, data.getCompanyId())).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getSubmittedTime())).setParent(item);
				final Date completedTime = avmAdapterServiceImpl.getLastCompletedActionApproval(data.getAvmTrxId());
				new Listcell(FormatDateUI.formatDateTime(completedTime)).setParent(item);
			}
		};
		
		return listitemRenderer;
	}
	
	@Listen("onClick = button#btnCancelTrx")
	public void doCancelTransaction() {
		Messagebox.show(Labels.getLabel("common.confirmationCancelTrx"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						if (doValidateRemarks()) {
							avmAdapterServiceImpl.doMultipleCancelTransaction(selected, UUID.fromString(AVMApprovalManager.APPROVER_SWF_ADMIN), txtRemark.getValue());
							Messagebox.show(Labels.getLabel("swf.cancelSuccessfully"));
							Executions.createComponents("~./hcms/workflow/workflow_monitoring_transaction.zul", getSelf().getParent(), null);
							getSelf().detach();							
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(Labels.getLabel("swf.err.processError"));
					}
				}
			}
		});			
	}
	
	private boolean doValidateRemarks() {
		ErrorMessageUtil.clearErrorMessage(txtRemark);
		if (isNullOrEmpty(txtRemark.getValue())) {
			ErrorMessageUtil.setErrorMessage(txtRemark, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.remarks")}));
			return false;
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
					Executions.createComponents("~./hcms/workflow/workflow_monitoring_transaction.zul", getSelf().getParent(), null);
					getSelf().detach();
				}
			}
		});	
		
	}
}
