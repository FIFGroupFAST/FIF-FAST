package co.id.fifgroup.systemworkflow.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;

public class ResolverRole {

	public static List<AVMApprovalHistory> getApprovers(PersonDTO personBasedLine, AVMApprover approver) {
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		AVMApprovalHistory history = new AVMApprovalHistory();
		ApproverMapping approverMapping = getApproverMappingService().getApproverMappingByApproverId(approver.getApproverId());
		String roleCode = approverMapping.getApproverName();
		
		// TODO get employee has role in company
		List<PersonAssignmentDTO> listPerson = getPersonService().selectPersonByAssignment(null, null, null, roleCode, null, personBasedLine.getCompanyId(), null);
		
		// Added by Jatis
		// requirement for cwk because PIC CWK can be internship people
		// add implementation to get employee who have role in company with people type == INTERNSHIP
		List<PersonAssignmentDTO> listPersonInternship = getPersonService().selectPersonByAssignmentInternship(null, null, null, roleCode, null, personBasedLine.getCompanyId(), null);
		
		if (listPersonInternship != null){
			listPerson.addAll(listPersonInternship);
		}
		// End Added by Jatis
		
		if (listPerson.size() > 0) {
			//vacation rule
			VacationRule vacationRule = null;
			if (listPerson.size() == 1) {
				vacationRule = getVacationRuleService().getActiveVacationRuleByApproverId(listPerson.get(0).getPersonUUID(), DateUtil.truncate(new Date()));				
			}
			
			if (vacationRule != null) {
				history.setApproverId(vacationRule.getSubstituteApproverId());
				history.setVacationApproverId(listPerson.get(0).getPersonUUID());
				history.getApproverUUIDs().add(vacationRule.getSubstituteApproverId());
			} else {
				history.setApproverId(approver.getApproverId());
				for(PersonAssignmentDTO personAssignmentDTO : listPerson) {
					history.getApproverUUIDs().add(personAssignmentDTO.getPersonUUID());
				}
			}
			approvalHistories.add(history);	
		} 
		
		return approvalHistories;
	}
	
	public static ApproverMappingService getApproverMappingService() {
		return (ApproverMappingService) ApplicationContextUtil.getApplicationContext().getBean("approverMappingServiceImpl");
	}
	
	public static VacationRuleService getVacationRuleService() {
		return (VacationRuleService) ApplicationContextUtil.getApplicationContext().getBean("vacationRuleServiceImpl");
	}
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}
}
