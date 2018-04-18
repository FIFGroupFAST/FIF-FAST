package co.id.fifgroup.systemworkflow.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.personneladmin.util.SupervisorUtil;
import co.id.fifgroup.systemworkflow.constants.BasedOn;
import co.id.fifgroup.systemworkflow.constants.WorkflowReference;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class ResolverJobRole {

	/**
	 * 
	 * @param basedLineApprover for based line to find approver
	 * @param approver
	 * @param trx for based line to get new assignment approver
	 * @param orgHierId for get hierarchy to resolving approver
	 * @return list approver
	 */
	public static List<AVMApprovalHistory> getApprovers(PersonDTO personBasedLine,
			AVMApprover approver, CommonTrx trx, Long orgHierId) {
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		AVMApprovalHistory history = new AVMApprovalHistory();
		List<PersonAssignmentDTO> personApprover = new ArrayList<PersonAssignmentDTO>();

		// TODO get employee which have assignment jobRole in branch based line approver
		// if not found find employee with role jobRole in branch based line approver
		Long branchOwnerId = getOrganizationSetupService().getBranchOwnerByOrganizationId(personBasedLine.getPrimaryAssignmentDTO().getOrganizationId());
		if (approver.getBasedOn() == BasedOn.NewAssignment.getCode()) {
			if (getOrganizationSetupService().isHeadOffice(trx.getOrganizationDestinationId())) {
				branchOwnerId = -1L;
			} else {
				branchOwnerId = getOrganizationSetupService().getBranchOwnerByOrganizationId(trx.getOrganizationDestinationId());
			}
		}
		// get assignment basedlineapprover
		ApproverMapping approverMapping = getApproverMappingService()
				.getApproverMappingByApproverId(approver.getApproverId());
		
		OrganizationDTO orgBranch = null;
		if (branchOwnerId != null) {
			if (branchOwnerId.equals(-1L)) {
				orgBranch = getOrganizationSetupService().getHeadOffice();
			} else {
				orgBranch = getOrganizationSetupService().findById(branchOwnerId);
			}
			if (orgBranch != null) {
				// check apakah requestor adalah branch manager
				if (!orgBranch.getIsHeadOffice() && approverMapping.getApproverName().equals(WorkflowReference.OSSH.name()) 
						&& (personBasedLine.getPrimaryAssignmentDTO().getOrganizationId().equals(orgBranch.getId()) 
						&& personBasedLine.getPrimaryAssignmentDTO().getJobId().equals(orgBranch.getOrganizationHeadId()))) {
					
//					OrganizationDTO orgParent = getOrganizationSetupService().findOrgParentByHierarchyIdAndOrgId(orgHierId, 
//							personBasedLine.getPrimaryAssignmentDTO().getOrganizationId());
//					
//					if (orgParent != null) {
//						personApprover = getPersonService().selectPersonByAssignment(
//								orgParent.getOrganizationHeadId(), orgParent.getId(),
//								null, null, null, null, null);
						personApprover = SupervisorUtil.getSupervisor(personBasedLine.getPrimaryAssignmentDTO().getJobId(), personBasedLine.getPrimaryAssignmentDTO().getOrganizationId(), personBasedLine.getCompanyId(), 1);
						if (personApprover.size() > 0) {
							// vacation rule
							VacationRule vacationRule = null;
							if (personApprover.size() == 1) {
								vacationRule = getVacationRuleService()
										.getActiveVacationRuleByApproverId(personApprover.get(0).getPersonUUID(),
												DateUtil.truncate(new Date()));
							}
							
							JobDTO job = getJobSetupService().findById(personApprover.get(0).getPrimaryAssignmentDTO().getJobId());
							
							if (vacationRule != null) {
								history.setApproverId(vacationRule.getSubstituteApproverId());
								history.getApproverUUIDs().add(vacationRule.getSubstituteApproverId());
							} else {
								history.setApproverId(job.getJobUuid());
								history.setOrganizationId(personApprover.get(0).getPrimaryAssignmentDTO().getOrganizationId());
								for(PersonAssignmentDTO personAssignmentDTO : personApprover) {
									history.getApproverUUIDs().add(personAssignmentDTO.getPersonUUID());
								}
							}
							
							approvalHistories.add(history);
						}				
//					}
				} else {
					// ketika tidak ditemukan cari orang yang punya role hc/ossh di branch
					if (personApprover.size() < 1) {
						String roleCode = approverMapping.getApproverName();
						personApprover = getPersonService().selectPersonByAssignment(null, null,
								orgBranch.getId(),
								roleCode, null, null, null);
						if (personApprover.size() > 0) {
							VacationRule vacationRule = null;
							if (personApprover.size() == 1) {
								vacationRule = getVacationRuleService()
										.getActiveVacationRuleByApproverId(personApprover.get(0).getPersonUUID(),
												DateUtil.truncate(new Date()));
							}
							if (vacationRule != null) {
								history.setApproverId(vacationRule.getSubstituteApproverId());
								history.getApproverUUIDs().add(vacationRule.getSubstituteApproverId());
							} else {
								history.setApproverId(approver.getApproverId());
								history.setBranchId(orgBranch.getId());
								for(PersonAssignmentDTO personAssignmentDTO : personApprover) {
									history.getApproverUUIDs().add(personAssignmentDTO.getPersonUUID());
								}
							}
							approvalHistories.add(history);
						}
					}
					
					// ketika tidak ditemukan untuk ossh, ambil head of organization
					if (!orgBranch.getIsHeadOffice() && personApprover.size() < 1 
							&& approverMapping.getApproverName().equals(WorkflowReference.OSSH.name())) {
						
						OrganizationDTO organization = getOrganizationSetupService()
								.findById(orgBranch.getId());
						personApprover = getPersonService().selectPersonByAssignment(
								organization.getOrganizationHeadId(), organization.getId(),
								null, null, null, null, null);
						
						if (personApprover.isEmpty()) {
							personApprover = SupervisorUtil.getSupervisor(organization.getOrganizationHeadId(), organization.getId(), organization.getCompanyId(), 1);
						}
						
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
								JobDTO job = getJobSetupService().findById(personApprover.get(0).getPrimaryAssignmentDTO().getJobId());
								history.setApproverId(job.getJobUuid());
								history.setOrganizationId(organization.getId());
								for(PersonAssignmentDTO personAssignmentDTO : personApprover) {
									history.getApproverUUIDs().add(personAssignmentDTO.getPersonUUID());
								}
							}
							approvalHistories.add(history);
						}
					}			
				}
			}
		}
		return approvalHistories;
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
	
	public static JobSetupService getJobSetupService() {
		return (JobSetupService) ApplicationContextUtil.getApplicationContext().getBean("jobSetupServiceImpl");
	}
	
	public static ApproverMappingService getApproverMappingService() {
		return (ApproverMappingService) ApplicationContextUtil.getApplicationContext().getBean("approverMappingServiceImpl");
	}
}
