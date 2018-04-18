package co.id.fifgroup.systemworkflow.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.core.service.WorkflowLookupServiceAdapter;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowConfirmationReassignComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowConfirmationReassignComposer.class);

	private WorkflowConfirmationReassignComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient PersonService personService;
	@WireVariable(rewireOnActivate=true)
	private transient WorkflowLookupServiceAdapter workflowLookupServiceAdapterImpl;
	
	@Wire
	private Window winWorkflowConfirmationReassign;	
	@Wire
	private CommonPopupDoubleBandbox bnbAssignApprover;
	@Wire
	private Label lblOrganization;
	@Wire
	private Label lblJob;
	@Wire
	private Textbox txtRemarks;
	@Wire
	private Listbox lbxMultipleTask;
	
	private Set<AVMApplicationData> selected;
	private KeyValue keyValueEmployee;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
		populateData();
	}
	
	public void initComponent() {
		loadEmployee();
	}
	
	public void populateData() {
		selected = (Set<AVMApplicationData>) arg.get("selected");
		ListModelList<AVMApplicationData> listModelAVMOutgoingReport = new ListModelList<>(selected);
		lbxMultipleTask.setModel(listModelAVMOutgoingReport);
		lbxMultipleTask.setItemRenderer(getItemRenderer());
	}
	
	private void loadEmployee() {
		bnbAssignApprover.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

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
				keyValue.setKey((UUID)data.getKey());
				keyValue.setDescription(data.getDescription().toString());
				keyValue.setValue(data.getValue().toString());
			}
		});
	}
	
	@Listen("onClose = #bnbAssignApprover")
	public void setValueSubstitute() {
		keyValueEmployee = bnbAssignApprover.getKeyValue();
		String jobName = "";
		String organizationName = "";
		if (keyValueEmployee != null) {
			PersonDTO person = (PersonDTO) personService.getPersonByPersonUUID((UUID)keyValueEmployee.getKey(), DateUtil.truncate(new Date()), -1L);
			if (person != null) {
				jobName = person.getPrimaryAssignmentDTO().getJobName();
				organizationName = person.getPrimaryAssignmentDTO().getOrganizationName();				
			}
		}
		lblJob.setValue(jobName);
		lblOrganization.setValue(organizationName);			
	}
	
	public ListitemRenderer<AVMApplicationData> getItemRenderer() {
		ListitemRenderer<AVMApplicationData> listitemRenderer = new SerializableListItemRenderer<AVMApplicationData>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApplicationData data, final int index)
					throws Exception {
				String companyName = avmAdapterServiceImpl.getCompanyName((long) data.getCompanyId());
				
				
				new Listcell((index+1)+"").setParent(item);
				new Listcell(companyName).setParent(item);
				new Listcell(data.getSubject()).setParent(item);
				new Listcell(TrxType.fromCode((long) data.getTrxType()).getMessage()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getActionTime())).setParent(item);
				AVMApprovalHistory recentApprover = avmAdapterServiceImpl.getRecentApprovalHistory(data.getAvmTrxId(), data.getSequenceNumber());
				new Listcell(avmAdapterServiceImpl.getActualName(recentApprover, data.getCompanyId())).setParent(item);
				new Listcell(DateUtil.getProgressTime(recentApprover.getAvmReceivedTimeStamp(), new Date())).setParent(item);
				
				AVMApprovalHistory lastApproverResponse = avmAdapterServiceImpl.getLastApprovalHistory(data.getAvmTrxId());
				if (lastApproverResponse != null) {
					new Listcell(avmAdapterServiceImpl.getActualName(lastApproverResponse, data.getCompanyId())).setParent(item);
					new Listcell(lastApproverResponse.getRemarks()).setParent(item);					
				} else {
					new Listcell("").setParent(item);
					new Listcell("").setParent(item);
				}
								
			}
		};
		
		return listitemRenderer;
	}
	
	@Listen("onClick = button#btnReassign")
	public void doReassign() {
		ErrorMessageUtil.clearErrorMessage(bnbAssignApprover);
		if (keyValueEmployee != null) {
			Messagebox.show(Labels.getLabel("common.confirmationReassign"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new SerializableEventListener<Event>() {				
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getName().equals("onYes")) {
						try {
							AVMReturnCode returnCode = avmAdapterServiceImpl.doMultipleReassignApprover(selected, (UUID) keyValueEmployee.getKey(), txtRemarks.getValue());
							Messagebox.show(Labels.getLabel("swf.reassignSuccessfully"));
							Executions.createComponents("~./hcms/workflow/workflow_pending_approval_inquiry.zul", winWorkflowConfirmationReassign.getParent(), null);
							getSelf().detach();
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							Messagebox.show(Labels.getLabel("swf.err.processError"));
						}
					}
				}
			});			
		} else {
			ErrorMessageUtil.setErrorMessage(bnbAssignApprover, 
					Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.assigneApprover")}));
		}
		
	}
	
	@Listen("onClick = button#btnCancel")
	public void doCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					Executions.createComponents("~./hcms/workflow/workflow_pending_approval_inquiry.zul", winWorkflowConfirmationReassign.getParent(), null);
					getSelf().detach();
				}
			}
		});		
	
	}	
}
