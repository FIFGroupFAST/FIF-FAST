package co.id.fifgroup.personneladmin.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.workstructure.constant.HierarchyType;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;
import co.id.fifgroup.workstructure.domain.OrganizationNode;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class ManagerUtil {

	public static List<PersonAssignmentDTO> getManager(Long jobId, Long organizationId, Long companyId, int countLevel) {
		Date currentDate = DateUtil.truncate(new Date());
		List<PersonAssignmentDTO> listPerson = new ArrayList<PersonAssignmentDTO>();
		
		OrganizationHierarchyExample example = new OrganizationHierarchyExample();
		example.createCriteria().andCompanyIdEqualTo(companyId).andOrgHierTypeEqualTo(HierarchyType.STRUCTURAL.getDesc());
		// closed TPS, bikin error waktu comparison. Tidak diperlukan, akan dihandle sendiri di PA entry assesor. 
		// ini kalau kasusnya adalah orang FIF punya atasan orang AMF
		//if (getOrganizationHierarchySetupService().countByExample(example) < 1)
		//	return new ArrayList<PersonAssignmentDTO>();
		Long orgHierId = getOrganizationHierarchySetupService().findByExample(example).get(0).getId();
		OrganizationNode orgNode = null;
		OrganizationDTO orgSpv = new OrganizationDTO();
		if (orgHierId != null) {
			orgNode = getOrganizationHierarchySetupService().findOrganizationNode(orgHierId, organizationId, null, currentDate, true);
			if (orgNode != null) {				
				do {
					if (orgNode.getData() != null && orgNode.getData().getOrganizationHeadId() != null) {
						JobDTO jobHead = getJobSetupService().findById(orgNode.getData().getOrganizationHeadId());
						if (!orgNode.getData().getOrganizationHeadId().equals(jobId) || !orgNode.getData().getId().equals(organizationId)) {
							if (null != jobHead && jobHead.getIsManager()) {
								listPerson = getPersonService().selectPersonByAssignment(jobHead.getId(), orgNode.getData().getId(), null, null, null, null, null);
								if (listPerson.size() < 1) {
									if (orgNode.getData().getOrganizationSpvId() != null) {
										orgSpv = getOrganizationSetupService().findById(orgNode.getData().getOrganizationSpvId());
										if (orgSpv != null) {								
											if (orgSpv.getOrganizationHeadId() != null) {									
												JobDTO jobSpv = getJobSetupService().findById(orgSpv.getOrganizationHeadId());
												listPerson = getPersonService().selectPersonByAssignment(jobSpv.getId(), orgSpv.getId(), null, null, null, null, null);
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
						}
					}
					
					if (listPerson.size() > 0) {
						countLevel--;
					}
					
					if (listPerson.size() < 1 || countLevel != 0) {
						if (orgNode.getParent() == null) {
							return listPerson;
						} else {
							orgNode = orgNode.getParent();
						}				
					} 
				} while (!(listPerson.size() > 0 && countLevel == 0) && orgNode != null);
			}
		}
		return listPerson;
	}
	
	public static OrganizationHierarchySetupService getOrganizationHierarchySetupService() {
		return (OrganizationHierarchySetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationHierarchySetupServiceImpl");
	}
	
	public static JobSetupService getJobSetupService() {
		return (JobSetupService) ApplicationContextUtil.getApplicationContext().getBean("jobSetupServiceImpl");
	}
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}
	
	public static OrganizationSetupService getOrganizationSetupService() {
		return (OrganizationSetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationSetupServiceImpl");
	}
	
	/*public static List<KeyValue> getSubordinates(Long managerId, String employeeNumber, String fullName) {
		Date currentDate = DateUtil.truncate(new Date());
		List<KeyValue> employees = new ArrayList<KeyValue>();
		
		PersonDTO manager = getPersonService().getPersonById(managerId, currentDate);
		
		OrganizationHierarchyExample example = new OrganizationHierarchyExample();
		example.createCriteria()
				.andCompanyIdEqualTo(manager.getPrimaryAssignmentDTO().getCompanyId())
				.andOrgHierTypeEqualTo(HierarchyType.STRUCTURAL.getDesc());
		Long orgHierId = getOrganizationHierarchySetupService().findByExample(example).get(0).getId();
		Long organizationId = manager.getPrimaryAssignmentDTO().getOrganizationId();
		
		OrganizationNode orgNode = getOrganizationHierarchySetupService()
				.findOrganizationNode(orgHierId, organizationId, null, currentDate, true);
		
		// if person job is job head of organization
		if (manager.getPrimaryAssignmentDTO().getJobId().equals(orgNode.getData().getOrganizationHeadId())) {
			getEmployeeInOrganization(orgNode, employees, managerId, 
					manager.getPrimaryAssignmentDTO().getCompanyId(),
					currentDate, employeeNumber, fullName);
		}
		return employees;
		
	}*/
	
	public static List<PersonDTO> getSubordinates(List<Long> inOrganizationId, List<Long> notInOrganizationId, Long companyId, Long managerId, String employeeNumber, String fullName,
			int offset, int limit) {
		List<PersonDTO> employees = new ArrayList<PersonDTO>();
		
		Date currentDate = DateUtil.truncate(new Date());
		PersonDTO manager = getPersonService().getPersonById(managerId, currentDate);
		OrganizationDTO organizationDTO = getOrganizationSetupService().findById(manager.getPrimaryAssignmentDTO().getOrganizationId());
		
		// if person job is job head of organization
		if (manager.getPrimaryAssignmentDTO().getJobId().equals(organizationDTO.getOrganizationHeadId())) {
			PersonCriteriaDTO criteria = new PersonCriteriaDTO();
			criteria.setEmployeeNumber(employeeNumber);
			criteria.setFullName(fullName);
			List<Long> inManagerRootBranch = getPersonService().getManagerRootBranch(organizationDTO.getId());
			List<Long> inManagerBranchWithHead = getPersonService().getManagerBranchWithHead(inOrganizationId, notInOrganizationId, companyId, managerId);
			if (inManagerBranchWithHead.size() == 0 ) inManagerBranchWithHead.add(-1L);
			employees = getPersonService().findEmployeeByManagerId(inManagerRootBranch, inManagerBranchWithHead, companyId, managerId, criteria, offset, limit);
		}
		return employees;
	}
	
	public static List<PersonDTO> getSubordinatesSortByEmployeeNumber(List<Long> inOrganizationId, List<Long> notInOrganizationId, Long companyId, Long managerId, String employeeNumber, String fullName,
			int offset, int limit) {
		List<PersonDTO> employees = new ArrayList<PersonDTO>();
		
		Date currentDate = DateUtil.truncate(new Date());
		PersonDTO manager = getPersonService().getPersonById(managerId, currentDate);
		OrganizationDTO organizationDTO = getOrganizationSetupService().findById(manager.getPrimaryAssignmentDTO().getOrganizationId());
		
		// if person job is job head of organization
		if (manager.getPrimaryAssignmentDTO().getJobId().equals(organizationDTO.getOrganizationHeadId())) {
			PersonCriteriaDTO criteria = new PersonCriteriaDTO();
			criteria.setEmployeeNumber(employeeNumber);
			criteria.setFullName(fullName);
			List<Long> inManagerRootBranch = getPersonService().getManagerRootBranch(organizationDTO.getId());
			List<Long> inManagerBranchWithHead = getPersonService().getManagerBranchWithHead(inOrganizationId, notInOrganizationId, companyId, managerId);
			if (inManagerBranchWithHead.size() == 0 ) inManagerBranchWithHead.add(-1L);
			employees = getPersonService().findEmployeeByManagerIdSortByEmployeeNumber(inManagerRootBranch, inManagerBranchWithHead, companyId, managerId, criteria, offset, limit);
		}
		return employees;
	}
	
	//add by JTS [15123108364420] [HCMS-CAM]BUGFIX-SubDep tdk bisa entry People Revi
	public static List<PersonDTO> getSubordinatesWithCriteria(List<Long> inOrganizationId, List<Long> notInOrganizationId, Long companyId, Long managerId, PersonCriteriaDTO criteria,
			int offset, int limit) {
		List<PersonDTO> employees = new ArrayList<PersonDTO>();
		
		Date currentDate = DateUtil.truncate(new Date());
		PersonDTO manager = getPersonService().getPersonById(managerId, currentDate);
		OrganizationDTO organizationDTO = getOrganizationSetupService().findById(manager.getPrimaryAssignmentDTO().getOrganizationId());
		
		// if person job is job head of organization
		if (manager.getPrimaryAssignmentDTO().getJobId().equals(organizationDTO.getOrganizationHeadId())) {
			List<Long> inManagerRootBranch = getPersonService().getManagerRootBranch(organizationDTO.getId());
			List<Long> inManagerBranchWithHead = getPersonService().getManagerBranchWithHead(inOrganizationId, notInOrganizationId, companyId, managerId);
			if (inManagerBranchWithHead.size() == 0 ) inManagerBranchWithHead.add(-1L);
			employees = getPersonService().findEmployeeByManagerIdSortByEmployeeNumber(inManagerRootBranch, inManagerBranchWithHead, companyId, managerId, criteria, offset, limit);
		}
		return employees;
	}
	
	
	public static List<PersonDTO> getSubordinatesForPeopleReview(List<Long> inOrganizationId, List<Long> notInOrganizationId, Long companyId, Long managerId, Long personId, String fullName, Long jobId) {
		List<PersonDTO> employees = new ArrayList<PersonDTO>();
		
		Date currentDate = DateUtil.truncate(new Date());
		PersonDTO manager = getPersonService().getPersonById(managerId, currentDate);
		OrganizationDTO organizationDTO = getOrganizationSetupService().findById(manager.getPrimaryAssignmentDTO().getOrganizationId());
		
		String employeeNumber = null;
		if (personId != null){
			PersonDTO person = getPersonService().getPersonById(personId, currentDate);
			employeeNumber = person.getEmployeeNumber();
		}
		
		// if person job is job head of organization
		if (manager.getPrimaryAssignmentDTO().getJobId().equals(organizationDTO.getOrganizationHeadId())) {
			PersonCriteriaDTO criteria = new PersonCriteriaDTO();
			
			criteria.setEmployeeNumber(employeeNumber);
			criteria.setFullName(fullName);
			criteria.setJobId(jobId);
			List<Long> inManagerRootBranch = getPersonService().getManagerRootBranch(organizationDTO.getId());
			List<Long> inManagerBranchWithHead = getPersonService().getManagerBranchWithHead(inOrganizationId, notInOrganizationId, companyId, managerId);
			if (inManagerBranchWithHead.size() == 0 ) inManagerBranchWithHead.add(-1L);
			employees = getPersonService().getSubordinatesForPeopleReview(inManagerRootBranch, inManagerBranchWithHead, companyId, managerId, criteria);
		}
		return employees;
	}
	//end by JTS [15123108364420] [HCMS-CAM]BUGFIX-SubDep tdk bisa entry People Revi
	
	public static int countSubordinates(List<Long> inOrganizationId, List<Long> notInOrganizationId, Long companyId, Long managerId, String employeeNumber, String fullName) {
		int count = 0;
		
		Date currentDate = DateUtil.truncate(new Date());
		PersonDTO manager = getPersonService().getPersonById(managerId, currentDate);
		OrganizationDTO organizationDTO = getOrganizationSetupService().findById(manager.getPrimaryAssignmentDTO().getOrganizationId());
		
		// if person job is job head of organization
		if (manager.getPrimaryAssignmentDTO().getJobId().equals(organizationDTO.getOrganizationHeadId())) {
			PersonCriteriaDTO criteria = new PersonCriteriaDTO();
			criteria.setEmployeeNumber(employeeNumber);
			criteria.setFullName(fullName);
			List<Long> inManagerRootBranch = getPersonService().getManagerRootBranch(organizationDTO.getId());
			List<Long> inManagerBranchWithHead = getPersonService().getManagerBranchWithHead(inOrganizationId, notInOrganizationId, companyId, managerId);
			if (inManagerBranchWithHead.size() == 0 ) inManagerBranchWithHead.add(-1L);
			count = getPersonService().countEmployeeByManagerId(inManagerRootBranch, inManagerBranchWithHead, companyId, managerId, criteria);
		}
		return count;
	}
	
	//add by JTS [15123108364420] [HCMS-CAM]BUGFIX-SubDep tdk bisa entry People Revi
	public static int countSubordinatesWithCriteria(List<Long> inOrganizationId, List<Long> notInOrganizationId, Long companyId, Long managerId, PersonCriteriaDTO criteria) {
		int count = 0;
		
		Date currentDate = DateUtil.truncate(new Date());
		PersonDTO manager = getPersonService().getPersonById(managerId, currentDate);
		OrganizationDTO organizationDTO = getOrganizationSetupService().findById(manager.getPrimaryAssignmentDTO().getOrganizationId());
		
		// if person job is job head of organization
		if (manager.getPrimaryAssignmentDTO().getJobId().equals(organizationDTO.getOrganizationHeadId())) {
			List<Long> inManagerRootBranch = getPersonService().getManagerRootBranch(organizationDTO.getId());
			List<Long> inManagerBranchWithHead = getPersonService().getManagerBranchWithHead(inOrganizationId, notInOrganizationId, companyId, managerId);
			if (inManagerBranchWithHead.size() == 0 ) inManagerBranchWithHead.add(-1L);
			count = getPersonService().countEmployeeByManagerId(inManagerRootBranch, inManagerBranchWithHead, companyId, managerId, criteria);
		}
		return count;
	}
	//end by JTS [15123108364420] [HCMS-CAM]BUGFIX-SubDep tdk bisa entry People Revi
	
	/*private static void getEmployeeInOrganization(OrganizationNode orgNode, List<KeyValue> employees, 
			Long managerId, Long companyId, Date currentDate, String employeeNumber, String fullName){
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setCompanyId(companyId);
		List<Long> inOrganizationId = new ArrayList<Long>();
		inOrganizationId.add(orgNode.getData().getId());
		criteria.setInOrganizationId(inOrganizationId);
		criteria.setSupervisorId(managerId);
		criteria.setEffectiveOnDate(currentDate);
		criteria.setEmployeeNumber(employeeNumber);
		criteria.setFullName(fullName);
		criteria.setPeopleType("EMPLOYEE");
		employees.addAll(getPersonService().getAllPersonByCompany(criteria, companyId, 
				RowBounds.NO_ROW_LIMIT, RowBounds.NO_ROW_OFFSET));
		
		for (OrganizationNode child : orgNode.getChildren()){
			if (child.getData().getOrganizationHeadId() == null ||
					getPersonService().selectPersonByAssignment(child.getData().getOrganizationHeadId(), 
					child.getData().getId(), null, null, null, null, null).size() == 0){
				getEmployeeInOrganization(child, employees, managerId, companyId, 
						currentDate, employeeNumber, fullName);
			}
		}
	}*/
}
