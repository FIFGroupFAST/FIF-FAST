package co.id.fifgroup.systemworkflow.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.systemworkflow.constants.BasedOn;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;
import co.id.fifgroup.workstructure.domain.OrganizationNode;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.workstructure.dto.JobDTO;

public class ResolverJob {

	/**
	 * get head of organization when job equals head of organization in structural hierarchy
	 * @param basedLineApprover for based line to find approver
	 * @param approver 
	 * @param trx for based line to get new assignment approver
	 * @param orgHierId for get hierarchy to resolving approver 
	 * @return list approver
	 */
	public static List<AVMApprovalHistory> getApprovers(PersonDTO personBasedLine, AVMApprover approver, CommonTrx trx, Long orgHierId) {
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		AVMApprovalHistory history = new AVMApprovalHistory();		

		int topHierarchy = 0;
		List<PersonAssignmentDTO> listPerson = new ArrayList<PersonAssignmentDTO>();
		JobDTO job = getJobSetupService().findByUuid(approver.getApproverId());
		JobDTO jobHead = null;
				
		// get based line organization
		/*OrganizationNode orgNode = null;
		if (approver.getBasedOn() == BasedOn.NewAssignment.getCode()) {
			orgNode = getOrganizationHierarchySetupService().findOrganizationNode(orgHierId, trx.getOrganizationDestinationId(), null, new Date(), true);
		} else {
			orgNode = getOrganizationHierarchySetupService().findOrganizationNode(orgHierId, personBasedLine.getPrimaryAssignmentDTO().getOrganizationId(), null, new Date(), true);			
		}
		
		do {			
			if (orgNode.getData().getOrganizationHeadId() != null) {
				jobHead = getJobSetupService().findById(orgNode.getData().getOrganizationHeadId());
				if (jobHead.getJobUuid().equals(job.getJobUuid())) {
					// cari employee yang punya assignment tersebut
					listPerson = getPersonService().selectPersonByAssignment(jobHead.getId(), orgNode.getData().getId(), null, null, null, null, null); 
				}				
			}
			
			if (listPerson.size() < 1) {
				if (topHierarchy == 0 
						&& orgNode.getParent() == null) {
					// autoapprove by system
					history.setApproverId(UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM));
					approvalHistories.add(history);
					return approvalHistories;
				} else {
					orgNode = orgNode.getParent();
				}				
			}
			topHierarchy++;
		} while (!jobHead.getJobUuid().equals(job.getJobUuid()) && orgNode != null);*/
		Long orgId= null;
		if (approver.getBasedOn() == BasedOn.NewAssignment.getCode()) {
			orgId = trx.getOrganizationDestinationId();
			
		} else {
			orgId = personBasedLine.getPrimaryAssignmentDTO().getOrganizationId();
			
		}
		
		listPerson = getPersonService().selectPersonByAssignment(job.getId(),
				null, orgId, null, null, trx.getCompanyId(), null);

		if (listPerson.size() < 1) {
			// autoapprove by system */
			history.setApproverId(UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM));
			approvalHistories.add(history);
			return approvalHistories;
		}
		
		
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
				history.setApproverId(job.getJobUuid());			
				//history.setOrganizationId(orgNode.getData().getId());
				history.setOrganizationId(listPerson.get(0).getPrimaryAssignmentDTO().getOrganizationId());
				for(PersonAssignmentDTO personAssignmentDTO : listPerson) {
					history.getApproverUUIDs().add(personAssignmentDTO.getPersonUUID());
				}
			}
			
			approvalHistories.add(history);
				
		} 
		
		return approvalHistories;
	}
	
	public static JobSetupService getJobSetupService() {
		return (JobSetupService) ApplicationContextUtil.getApplicationContext().getBean("jobSetupServiceImpl");
	}
	
	public static OrganizationHierarchySetupService getOrganizationHierarchySetupService() {
		return (OrganizationHierarchySetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationHierarchySetupServiceImpl");
	}
	
	public static OrganizationSetupService getOrganizationSetupService() {
		return (OrganizationSetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationSetupServiceImpl");
	}
	
	public static VacationRuleService getVacationRuleService() {
		return (VacationRuleService) ApplicationContextUtil.getApplicationContext().getBean("vacationRuleServiceImpl");
	}
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}
}
