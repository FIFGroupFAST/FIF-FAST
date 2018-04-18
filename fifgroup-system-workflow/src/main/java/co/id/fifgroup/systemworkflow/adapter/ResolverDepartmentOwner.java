package co.id.fifgroup.systemworkflow.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.systemworkflow.constants.BasedOn;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.constants.WorkflowReference;
import co.id.fifgroup.systemworkflow.domain.Employee;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class ResolverDepartmentOwner {
	
	/**
	 * 
	 * @param basedLineApprover for based line to find approver
	 * @param approver 
	 * @param trx for based line to get new assignment approver
	 * @param orgHierId for get hierarchy to resolving approver 
	 * @return list approver
	 */
	public static List<AVMApprovalHistory> getApprovers(PersonDTO personBasedLine, AVMApprover approver, CommonTrx trx) {
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		AVMApprovalHistory history = new AVMApprovalHistory();
		List<Employee> employees = new ArrayList<Employee>();
		
		Long basedLineOrgId = null;

		// handle for transaction mpp 
		if (trx.getTransactionType() != null && (trx.getTransactionType().equals(TrxType.MPP_OPTIMUM_REQUEST.getCode())
				|| trx.getTransactionType().equals(TrxType.MPP_EMPLOYEE_REQUISITION.getCode()))) {
			basedLineOrgId = trx.getOrganizationId();
		} else {
			basedLineOrgId = personBasedLine.getPrimaryAssignmentDTO().getOrganizationId(); 
		}
		
		// check based line approver is new assignment
		if (approver.getBasedOn() == BasedOn.NewAssignment.getCode()) {
			basedLineOrgId = trx.getOrganizationDestinationId();
		}
		
		OrganizationDTO org = (OrganizationDTO) getOrganizationSetupService().findById(basedLineOrgId);
		
		if (!org.getIsHeadOffice()) {
			List<OrgHierarchyDTO> listDepartmentOwner = getOrganizationHierarchySetupService().findHierarchyIsDeptOwnerByOrgId(org.getId());	
			for (OrgHierarchyDTO hierarchy : listDepartmentOwner) {
				OrganizationDTO orgParent = getOrganizationSetupService().findOrgParentByHierarchyIdAndOrgId(hierarchy.getId(), org.getId());
				employees.addAll(getEmployeePICDO(orgParent));
			}
		} else {
			employees = getEmployeePICDO(org);
		}
		
		if (employees.size() > 0) {
			//vacation rule
			VacationRule vacationRule = null;
			if (employees.size() == 1) {
				vacationRule = getVacationRuleService().getActiveVacationRuleByApproverId(employees.get(0).getEmployeeUUID(), DateUtil.truncate(new Date()));				
			}
			
			if (vacationRule != null) {
				history.setApproverId(vacationRule.getSubstituteApproverId());
				history.setVacationApproverId(employees.get(0).getEmployeeUUID());
				history.getApproverUUIDs().add(vacationRule.getSubstituteApproverId());
			} else {
				if (!org.getIsHeadOffice()) {
					history.setApproverId(approver.getApproverId());
					history.setOrganizationId(employees.get(0).getOrganizationId());					
				} else {
					JobDTO job = getJobSetupService().findById(org.getOrganizationHeadId());
					history.setApproverId(job.getJobUuid());
					history.setOrganizationId(employees.get(0).getOrganizationId());
				}
				for(Employee employee : employees) {
					history.getApproverUUIDs().add(employee.getEmployeeUUID());
				}
			}
			
			approvalHistories.add(history);
		}
		return approvalHistories;
	}

	// find employee has a role department owner in node parent
	private static List<Employee> getEmployeePICDO(
			OrganizationDTO organization) {
		List<Employee> employees = new ArrayList<Employee>();
		if (!organization.getIsHeadOffice()) {
			List<PersonAssignmentDTO> listPerson = getPersonService().selectPersonByAssignment(null, organization.getId(), null, WorkflowReference.PICDO.name(), null, null, null);
			if (listPerson.size() > 0) {
				for (PersonAssignmentDTO person : listPerson) {
					Employee employee = new Employee();
					employee.setEmployeeUUID(person.getPersonUUID());
					employee.setOrganizationId(organization.getId());
					employees.add(employee);						
				}
			}
		} else {
			// get employee head of organization;
			List<PersonAssignmentDTO> listPerson = getPersonService().selectPersonByAssignment(organization.getOrganizationHeadId(), organization.getId(), null, null, null, null, null);
			for (PersonAssignmentDTO person : listPerson) {
				Employee employee = new Employee();
				employee.setEmployeeUUID(person.getPersonUUID());
				employee.setOrganizationId(organization.getId());
				employees.add(employee);						
			}
		}
		return employees;
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
	
	public static JobSetupService getJobSetupService() {
		return (JobSetupService) ApplicationContextUtil.getApplicationContext().getBean("jobSetupServiceImpl");
	}
}
