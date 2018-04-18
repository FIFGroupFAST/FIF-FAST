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
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;
import co.id.fifgroup.workstructure.domain.OrganizationNode;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class ResolverJobGroup {

	/**
	 * 
	 * @param basedLineApprover for based line to find approver
	 * @param approver 
	 * @param trx for based line to get new assignment approver
	 * @param orgHierId for get hierarchy to resolving approver 
	 * @return list approver
	 */
	public List<AVMApprovalHistory> getApprovers(PersonDTO personBasedLine, AVMApprover approver, CommonTrx trx, Long orgHierId) {
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		AVMApprovalHistory history = new AVMApprovalHistory();
		List<PersonAssignmentDTO> personApprover = new ArrayList<PersonAssignmentDTO>();
		
		// get person in branch having job group code
		// when branh not head office tracing hierarchy to get employee having a job group code
		
		ApproverMapping approverMapping = getApproverMappingService().getApproverMappingByApproverId(approver.getApproverId());
		Long branchId = personBasedLine.getPrimaryAssignmentDTO().getBranchId();
		Date currentDate = DateUtil.truncate(new Date());
		OrganizationDTO parentBranch = null;
		OrganizationNode orgNode = null;
		
		if (approver.getBasedOn() == BasedOn.NewAssignment.getCode()) {
			if (getOrganizationSetupService().isHeadOffice(trx.getOrganizationDestinationId())) {
				branchId = -1L;
			} else {
				OrganizationDTO branchNew = getOrganizationSetupService().getBranch(trx.getOrganizationDestinationId(), DateUtil.truncate(new Date()), personBasedLine.getCompanyId());
				branchId = branchNew != null ? branchNew.getId() : 0L;
			}
		} 
		
		if (branchId != null) {
			personApprover = getPersonService().selectPersonByAssignment(null, null,
					branchId, null, null, null, approverMapping.getApproverName());
			
			if (personApprover.size() < 1 && !branchId.equals(-1L)) {
				parentBranch = getOrganizationSetupService().findParentOrganization(branchId, currentDate);
				if (parentBranch != null) {
					orgNode = getOrganizationHierarchySetupService().findOrganizationNode(orgHierId, parentBranch.getId(), null, currentDate, true);			
					
					if (orgNode != null) {
						do {
							personApprover = getPersonService().selectPersonByAssignment(null, null,
									orgNode.getData().getId(), null, null, null, approverMapping.getApproverName());
							if (personApprover.size() < 1) {
								orgNode = orgNode.getParent();
							}						
						} while (personApprover.size() < 1 && orgNode != null);			
					}
				}
			}
		}
		//add by hendra auto approve by system
		if (personApprover.size() < 1) {
			// autoapprove by system */
			history.setApproverId(UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM));
			approvalHistories.add(history);
			return approvalHistories;
		}
		
		//vacation rule
		if (personApprover.size() > 0) {
			VacationRule vacationRule = null;
			if (personApprover.size() == 1) {
				vacationRule = getVacationRuleService()
						.getActiveVacationRuleByApproverId(personApprover.get(0).getPersonUUID(),
								DateUtil.truncate(new Date()));
			}
			if (vacationRule != null) {
				history.setApproverId(vacationRule.getSubstituteApproverId());
				history.setVacationApproverId(personApprover.get(0).getPersonUUID());
				history.getApproverUUIDs().add(vacationRule.getSubstituteApproverId());
			} else {
				history.setApproverId(approver.getApproverId());
				if (orgNode != null) {
					history.setOrganizationId(orgNode.getData().getId());
				} else {
					history.setBranchId(branchId);					
				}
				for(PersonAssignmentDTO personAssignmentDTO : personApprover) {
					history.getApproverUUIDs().add(personAssignmentDTO.getPersonUUID());
				}
			}
			approvalHistories.add(history);
		}
				
		return approvalHistories;
	}
	
	public static VacationRuleService getVacationRuleService() {
		return (VacationRuleService) ApplicationContextUtil.getApplicationContext().getBean("vacationRuleServiceImpl");
	}
	
	public static OrganizationSetupService getOrganizationSetupService() {
		return (OrganizationSetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationSetupServiceImpl");
	}
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}
	
	public static ApproverMappingService getApproverMappingService() {
		return (ApproverMappingService) ApplicationContextUtil.getApplicationContext().getBean("approverMappingServiceImpl");
	}
	
	public static OrganizationHierarchySetupService getOrganizationHierarchySetupService() {
		return (OrganizationHierarchySetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationHierarchySetupServiceImpl");
	}
}
