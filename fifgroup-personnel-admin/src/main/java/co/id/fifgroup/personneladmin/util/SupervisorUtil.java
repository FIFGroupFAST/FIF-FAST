package co.id.fifgroup.personneladmin.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.workstructure.constant.HierarchyType;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;
import co.id.fifgroup.workstructure.domain.OrganizationNode;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class SupervisorUtil {

	public static List<PersonAssignmentDTO> getSupervisor(Long jobId, Long organizationId, Long companyId, int countLevel) {
		Date currentDate = DateUtil.truncate(new Date());
		List<PersonAssignmentDTO> listPerson = new ArrayList<PersonAssignmentDTO>();
		
		OrganizationHierarchyExample example = new OrganizationHierarchyExample();
		example.createCriteria().andCompanyIdEqualTo(companyId).andOrgHierTypeEqualTo(HierarchyType.STRUCTURAL.getDesc());
		Long orgHierId = getOrganizationHierarchySetupService().findByExample(example).get(0).getId();
		OrganizationNode orgNode = null;
		OrganizationDTO orgSpv = new OrganizationDTO();
		
		if (orgHierId != null) {
			orgNode = getOrganizationHierarchySetupService().findOrganizationNode(orgHierId, organizationId, null, currentDate, true);
			if (orgNode != null) {				
				do {			
					if (orgNode.getData().getOrganizationHeadId() != null) {
						if (!orgNode.getData().getOrganizationHeadId().equals(jobId) || !orgNode.getData().getId().equals(organizationId)) {
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
											if (listPerson.size() > 1) {
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
								if (listPerson.size() > 1) {
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
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}
	
	public static OrganizationSetupService getOrganizationSetupService() {
		return (OrganizationSetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationSetupServiceImpl");
	}
}
