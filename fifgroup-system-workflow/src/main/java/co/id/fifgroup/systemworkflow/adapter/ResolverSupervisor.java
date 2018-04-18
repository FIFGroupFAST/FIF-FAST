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
import co.id.fifgroup.systemworkflow.constants.ApproverType;
import co.id.fifgroup.systemworkflow.constants.BasedOn;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.dto.SupervisorDTO;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;
import co.id.fifgroup.workstructure.constant.HierarchyType;
import co.id.fifgroup.workstructure.domain.OrganizationNode;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class ResolverSupervisor {
	
	/**
	 * get head of organization based on structural hierarchy
	 * if not found employee has a job head of organization, get head of organization in organization supervisor
	 * @param basedLineApprover for based line to find approver
	 * @param approver 
	 * @param trx for based line to get new assignment approver
	 * @param orgHierId for get hierarchy to resolving approver 
	 * @return list approver
	 */
	public static List<AVMApprovalHistory> getApprovers(PersonDTO personBasedLine, AVMApprover approver, CommonTrx trx, Long orgHierId, UUID avmTrxId) {
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		AVMApprovalHistory history = new AVMApprovalHistory();
		Date currentDate = DateUtil.truncate(new Date());

		int topHierarchy = 0;
		ApproverMapping approverMapping = getApproverMappingService().getApproverMappingByApproverId(approver.getApproverId());
		int countLevel = approverMapping.getSequence().intValue();
		List<PersonAssignmentDTO> listPerson = new ArrayList<PersonAssignmentDTO>();
		
		// get based line organization
		OrganizationNode orgNode = null;
		if (approver.getBasedOn() == BasedOn.NewAssignment.getCode()) {
			orgNode = getOrganizationHierarchySetupService().findOrganizationNode(orgHierId, trx.getOrganizationDestinationId(), null, currentDate, true);
		} else {
			orgNode = getOrganizationHierarchySetupService().findOrganizationNode(orgHierId, personBasedLine.getPrimaryAssignmentDTO().getOrganizationId(), null, currentDate, true);			
		}
		OrganizationDTO orgSpv = new OrganizationDTO();
		
		if (countLevel > 1) {
			// get previous supervisor
			Integer maxSequence = getApproverMappingService().maxLevelSequence(ApproverType.Supervisor.name(), avmTrxId);
			if (maxSequence != null) {
				SupervisorDTO previousSupervisor = getApproverMappingService().getPreviousSupervisor(ApproverType.Supervisor.name(), avmTrxId, maxSequence.intValue(), approver.getBasedOn());
				if (previousSupervisor != null) {
					if (previousSupervisor.getVacationApproverId() != null) {
						PersonDTO spv = getPersonService().getPersonByPersonUUID(previousSupervisor.getVacationApproverId(), currentDate, -1L);
						orgNode = getOrganizationHierarchySetupService().findOrganizationNode(orgHierId, spv.getPrimaryAssignmentDTO().getOrganizationId(), null, currentDate, true);
					} else {
						orgNode = getOrganizationHierarchySetupService().findOrganizationNode(orgHierId, previousSupervisor.getOrganizationId(), null, currentDate, true);						
					}
					if (orgNode == null) {
						OrganizationDTO orgPreviousSupervisor = getOrganizationSetupService().findById(previousSupervisor.getOrganizationId());
						List<OrgHierarchyDTO> hierarchyDTOs = getOrganizationHierarchySetupService().findHierarchy(HierarchyType.STRUCTURAL.name(), currentDate, orgPreviousSupervisor.getCompanyId());
						if (hierarchyDTOs.size() > 0) {
							orgNode = getOrganizationHierarchySetupService().findOrganizationNode(hierarchyDTOs.get(0).getId(), previousSupervisor.getOrganizationId(), null, currentDate, true);							
						}
					}
					countLevel = countLevel - previousSupervisor.getSequence();
					if (orgNode != null) {
						orgNode = orgNode.getParent();
						if (orgNode == null) {
							// autoapprove by system
							history.setApproverId(UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM));
							approvalHistories.add(history);
							return approvalHistories;							
						}
					} else {
						return approvalHistories;
					}				
				}
			}
		} 	
		
		if (orgNode != null) {
			do {			
				if (orgNode.getData().getOrganizationHeadId() != null) {
					if (!orgNode.getData().getOrganizationHeadId().equals(personBasedLine.getPrimaryAssignmentDTO().getJobId()) || !orgNode.getData().getId().equals(personBasedLine.getPrimaryAssignmentDTO().getOrganizationId())) {
						// cari employee yang punya assignment tersebut
						listPerson = getPersonService().selectPersonByAssignment(orgNode.getData().getOrganizationHeadId(), orgNode.getData().getId(), null, null, null, null, null);
						if (listPerson.size() < 1) {
							// cek ke organization supervisor
							if (orgNode.getData().getOrganizationSpvId() != null) {
								orgSpv = getOrganizationSetupService().findById(orgNode.getData().getOrganizationSpvId());
								if (orgSpv != null) {
									if (orgSpv.getOrganizationHeadId() != null) {
										// cari employee yang punya assignment tersebut
										listPerson = getPersonService().selectPersonByAssignment(orgSpv.getOrganizationHeadId(), orgSpv.getId(), null, null, null, null, null);
										if (listPerson.size() > 0) {
											OrganizationNode newOrg = new OrganizationNode();
											newOrg.setData(orgSpv);
											orgNode = newOrg;
										}
									}
								}
							} 				
						}
						
					}
				} else if (orgNode.getData().getOrganizationSpvId() != null) {
					orgSpv = getOrganizationSetupService().findById(orgNode.getData().getOrganizationHeadId());
					if (orgSpv != null) {
						if (orgSpv.getOrganizationHeadId() != null) {
							// cari employee yang punya assignment tersebut
							listPerson = getPersonService().selectPersonByAssignment(orgSpv.getOrganizationHeadId(), orgSpv.getId(), null, null, null, null, null);
							if (listPerson.size() > 0) {
								OrganizationNode newOrg = new OrganizationNode();
								newOrg.setData(orgSpv);
								orgNode = newOrg;
							}
						}
					}
				}
				
				if (listPerson.size() > 0) {
					countLevel--;
				}
				
				if (listPerson.size() < 1 || countLevel != 0) {
					if (topHierarchy == 0 && orgNode.getParent() == null) {
						// autoapprove by system
						history.setApproverId(UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM));
						approvalHistories.add(history);
						return approvalHistories;
					} else {
						orgNode = orgNode.getParent();
					}				
				}
				topHierarchy++;
			} while (!(listPerson.size() > 0 && countLevel == 0) && orgNode != null);
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
				JobDTO job = getJobSetupService().findById(orgNode.getData().getOrganizationHeadId());
				history.setApproverId(job.getJobUuid());			
				history.setOrganizationId(orgNode.getData().getId());
				for(PersonAssignmentDTO personAssignmentDTO : listPerson) {
					history.getApproverUUIDs().add(personAssignmentDTO.getPersonUUID());
				}
			}
			
			approvalHistories.add(history);			
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
	
	public static OrganizationHierarchySetupService getOrganizationHierarchySetupService() {
		return (OrganizationHierarchySetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationHierarchySetupServiceImpl");
	}
}
