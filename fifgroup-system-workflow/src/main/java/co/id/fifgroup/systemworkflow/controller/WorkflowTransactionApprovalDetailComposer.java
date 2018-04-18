package co.id.fifgroup.systemworkflow.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.zkoss.zul.A;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.TrxStatus;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowTransactionApprovalDetailComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowConfirmationCancelTransactionComposer.class);

	private WorkflowTransactionApprovalDetailComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	
	@Wire
	private Label lblCompany;
	@Wire
	private Label lblSubject;
	@Wire
	private Label lblTrxType;
	@Wire
	private Label lblTrxStatus;
	@Wire
	private Label lblSubmitter;
	@Wire
	private Label lblSubmittedTime;
	@Wire
	private Label lblCompletedTime;
	@Wire 
	private Listbox lbxApprovalHistory;
	
	private AVMOutgoingReport avmOutgoingReport;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateData();
	}
	
	public void populateData() {
		avmOutgoingReport = (AVMOutgoingReport) arg.get("selected");
		Date completedTime = (Date) arg.get("completedTime");
		String companyName = (String) arg.get("companyName");
		String submitterName = (String) arg.get("submitterName");
		lblCompany.setValue(companyName);
		lblSubject.setValue(avmOutgoingReport.getSubject());
		lblTrxType.setValue(TrxType.fromCode((long) avmOutgoingReport.getTrxType()).getMessage());
		lblTrxStatus.setValue(TrxStatus.fromCode(avmOutgoingReport.getTrxStatus()).getMessage());
		lblSubmitter.setValue(submitterName);
		lblSubmittedTime.setValue(FormatDateUI.formatDateTime(avmOutgoingReport.getSubmittedTime()));
		lblCompletedTime.setValue(FormatDateUI.formatDateTime(completedTime));
		getApprovalHistory(avmOutgoingReport.getAvmTrxId());
	}
	
	private void getApprovalHistory(UUID avmTrxId) {
		try {
			List<AVMApprovalHistory> listHistory = avmAdapterServiceImpl.getApprovalHistoryByAVMTrxId(avmTrxId);
			ListModelList<AVMApprovalHistory> listModel = new ListModelList<AVMApprovalHistory>(listHistory);
			lbxApprovalHistory.setModel(listModel);
			lbxApprovalHistory.setItemRenderer(getItemRenderer());
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ListitemRenderer<AVMApprovalHistory> getItemRenderer() {
		ListitemRenderer<AVMApprovalHistory> listitemRenderer = new SerializableListItemRenderer<AVMApprovalHistory>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApprovalHistory data, final int index)
					throws Exception {
				
				new Listcell((index+1)+"").setParent(item);
				new Listcell(avmAdapterServiceImpl.getActualName(data, avmOutgoingReport.getCompanyId())).setParent(item);
				new Listcell(ActionType.fromCode(data.getAvmActionType()).getMessage()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getAvmReceivedTimeStamp())).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getAvmActionTimeStamp())).setParent(item);
								
				A link = new A(Labels.getLabel("common.detail"));
				link.setStyle("color:blue");
//				link.setDisabled(disabledHyperlinkDetail);
				link.addEventListener("onClick", new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event arg0) throws Exception {
						params = new HashMap<String, Object>();
						params.put("AVMApprovalHistory", data);
						try {
							Window window = (Window) Executions.createComponents("~./hcms/workflow/workflow_notification_message_detail.zul", null, params);
							window.setClosable(true);
							window.setMaximized(false);
							window.setWidth("80%");
							window.doModal();
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
				});
				Listcell lc = new Listcell();
				link.setParent(lc);
				lc.setParent(item);				
			}
		};
		
		return listitemRenderer;
	}
	
	@Listen("onClick = button#btnBack")
	public void doBack() {
		Executions.createComponents("~./hcms/workflow/workflow_monitoring_transaction.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
}
