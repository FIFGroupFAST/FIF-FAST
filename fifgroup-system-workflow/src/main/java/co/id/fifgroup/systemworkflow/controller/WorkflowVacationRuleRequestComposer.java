package co.id.fifgroup.systemworkflow.controller;

import java.util.Date;
import java.util.Map;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.systemworkflow.constants.FieldPermissionsWorkflow;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.dto.VacationRuleDTO;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;
import co.id.fifgroup.systemworkflow.validation.VacationRuleValidator;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonEmployeeNumberBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowVacationRuleRequestComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowVacationRuleRequestComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient VacationRuleService vacationRuleServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient PersonService personService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	@Wire
	private CommonEmployeeNumberBandbox bnbApprover;
	@Wire
	private CommonEmployeeNumberBandbox bnbSubstituteApprover;
	@Wire
	private Label lblOrganizationApprover;
	@Wire
	private Label lblJobApprover;
	@Wire
	private Label lblOrganization;
	@Wire
	private Label lblJob;
	@Wire
	private Datebox dtbStartDate;
	@Wire
	private Datebox dtbEndDate;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnDelete;
	@Wire
	private Row rowEmployeeApprover;
	@Wire
	private Row rowOrganizationApprover;
	@Wire
	private Row rowJobApprover;
	
	private VacationRule vacationRule;
	private PersonDTO personApprover = new PersonDTO();
	private PersonDTO personSubstitute = new PersonDTO();
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();		
		populateData();
	}
	
	public void initComponent() {
		dtbStartDate.setFormat(FormatDateUI.getDateFormat());
		dtbEndDate.setFormat(FormatDateUI.getDateFormat());
		
		if (!GlobalVariable.hasPermission(FieldPermissionsWorkflow.VACATION_RULE_BNB_APPROVER_VISIBLE)) {
			rowEmployeeApprover.setVisible(false);
			rowJobApprover.setVisible(false);
			rowOrganizationApprover.setVisible(false);
		}
	}
	
	public void disabledComponent(boolean disabled) {
		bnbApprover.setDisabled(disabled);
		bnbSubstituteApprover.setDisabled(disabled);
		dtbStartDate.setDisabled(disabled);
		dtbEndDate.setDisabled(disabled);
		btnSave.setDisabled(disabled);
	}
	
	public void populateData() {
		
		if (arg.containsKey("vacationRule")) {
			VacationRuleDTO selected = (VacationRuleDTO) arg.get("vacationRule");
			vacationRule = new VacationRule();
			vacationRule.setVacationRuleId(selected.getVacationRuleId());
			vacationRule.setApproverId(selected.getApproverId());
			vacationRule.setSubstituteApproverId(selected.getSubstituteId());
			vacationRule.setEffectiveDateFrom(selected.getEffectiveDateFrom());
			vacationRule.setEffectiveDateTo(selected.getEffectiveDateTo());
			vacationRule.setCreatedBy(selected.getCreatedBy());
			vacationRule.setCreatedDate(selected.getCreatedDate());
			personApprover = personService.getSimplePersonByUUID(selected.getApproverId(), DateUtil.truncate(new Date()));
			KeyValue keyValueApprover = new KeyValue(personApprover.getPersonId(), personApprover.getEmployeeNumber(), personApprover.getFullName());
			bnbApprover.setRawValue(keyValueApprover);
			setValueApprover();
			personSubstitute = personService.getSimplePersonByUUID(selected.getSubstituteId(), DateUtil.truncate(new Date()));
			KeyValue keyValueSubstitute = new KeyValue(personSubstitute.getPersonId(), personSubstitute.getEmployeeNumber(), personSubstitute.getFullName());
			bnbSubstituteApprover.setRawValue(keyValueSubstitute);
			setValueSubstitute();
			dtbStartDate.setValue(vacationRule.getEffectiveDateFrom());
			dtbEndDate.setValue(vacationRule.getEffectiveDateTo());
			if (vacationRuleServiceImpl.isCurrent(vacationRule)) {
				bnbSubstituteApprover.setDisabled(true);
				bnbApprover.setDisabled(true);
				dtbStartDate.setDisabled(true);
				dtbEndDate.setDisabled(false);
				btnSave.setDisabled(false);
				btnDelete.setDisabled(true);
			} else if (vacationRuleServiceImpl.isFuture(vacationRule)) {
				disabledComponent(false);
				btnDelete.setDisabled(false);
			} else {
				disabledComponent(true);
				btnDelete.setDisabled(true);
			}
		} else {
			vacationRule = new VacationRule();
			if (!GlobalVariable.hasPermission(FieldPermissionsWorkflow.VACATION_RULE_BNB_APPROVER_VISIBLE)) {
				personApprover = personService.getSimplePersonByUUID(securityServiceImpl.getSecurityProfile().getPersonUUID(), DateUtil.truncate(new Date()));
				KeyValue keyValueApprover = new KeyValue(securityServiceImpl.getSecurityProfile().getPersonId(), 
						securityServiceImpl.getSecurityProfile().getEmployeeNumber(), 
						securityServiceImpl.getSecurityProfile().getFullName());
				bnbApprover.setRawValue(keyValueApprover);
			}
			disabledComponent(false);
			btnDelete.setDisabled(true);
		}
	}
	
	@Listen("onClose = #bnbSubstituteApprover")
	public void setValueSubstitute() {
		logger.info("set value substitue");
		String jobName = "";
		String organizationName = "";
		KeyValue keyValueEmployee = bnbSubstituteApprover.getKeyValue();
		if (keyValueEmployee != null) {
			personSubstitute = personService.getLastPersonInfo(Long.parseLong(keyValueEmployee.getKey().toString()), DateUtil.truncate(new Date()));
			if (personSubstitute != null) {
				jobName = personSubstitute.getPrimaryAssignmentDTO().getJobName();
				organizationName = personSubstitute.getPrimaryAssignmentDTO().getOrganizationName();
			}
		}
		lblJob.setValue(jobName);
		lblOrganization.setValue(organizationName);
	}
	
	@Listen("onClose = #bnbApprover")
	public void setValueApprover() {
		logger.info("set value approver");
		String jobName = "";
		String organizationName = "";
		KeyValue keyValueEmployee = bnbApprover.getKeyValue();
		if (keyValueEmployee != null) {
			personApprover = personService.getLastPersonInfo(Long.parseLong(keyValueEmployee.getKey().toString()), DateUtil.truncate(new Date()));
			if (personApprover != null) {
				jobName = personApprover.getPrimaryAssignmentDTO().getJobName();
				organizationName = personApprover.getPrimaryAssignmentDTO().getOrganizationName();
			}
		}
		lblJobApprover.setValue(jobName);
		lblOrganizationApprover.setValue(organizationName);
	}
	
	@Listen("onClick = button#btnSave")
	public void onSave() {
		String confirmation = vacationRule.getVacationRuleId() == null ? Labels.getLabel("swf.confirmationRequest") : Labels.getLabel("common.confirmationSaveVersion");
		Messagebox.show(confirmation, Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						clearErrorMessage();
						vacationRule.setApproverId(personApprover.getPersonUUID());
						vacationRule.setSubstituteApproverId(personSubstitute.getPersonUUID());
						vacationRule.setEffectiveDateFrom(dtbStartDate.getValue() != null ? DateUtil.truncate(dtbStartDate.getValue()) : null);
						vacationRule.setEffectiveDateTo(dtbEndDate.getValue() != null ? DateUtil.truncate(dtbEndDate.getValue()) : null);
						
						vacationRuleServiceImpl.saveVacationRule(vacationRule);
						Messagebox.show(Labels.getLabel("common.saveSuccessfully"));
						Executions.createComponents("~./hcms/workflow/workflow_vacation_rule_inquiry.zul", getSelf().getParent(), null);
						getSelf().detach();
					} catch (ValidationException ve) {
						showErrorMessage(ve.getConstraintViolations());
					}		
				}
			}
		});				
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(bnbApprover);
		ErrorMessageUtil.clearErrorMessage(bnbSubstituteApprover);
		ErrorMessageUtil.clearErrorMessage(dtbEndDate);
	}
	
	private void showErrorMessage(Map<String, String> maps) {
		if(maps.get(VacationRuleValidator.APPROVER_ID_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(bnbApprover, 
				maps.get(VacationRuleValidator.APPROVER_ID_VALIDATE));
		}
		if(maps.get(VacationRuleValidator.SUBSTITUTE_APPROVER_ID_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(bnbSubstituteApprover, 
				maps.get(VacationRuleValidator.SUBSTITUTE_APPROVER_ID_VALIDATE));
		}		
		if(maps.get(VacationRuleValidator.EFFECTIVE_DATE_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(dtbEndDate, 
				maps.get(VacationRuleValidator.EFFECTIVE_DATE_VALIDATE));
		}
		
	}
	
	@Listen("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					doClose();
				}
			}			
		});		
	}
	
	private void doClose() {
		Executions.createComponents("~./hcms/workflow/workflow_vacation_rule_inquiry.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnDelete")
	public void onDelete() {
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					vacationRuleServiceImpl.deleteVacationRule(vacationRule);
					Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
					doClose();
				}
			}
		});		
	}
}
