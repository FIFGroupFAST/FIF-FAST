package co.id.fifgroup.workstructure.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.version.VersioningService;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchy;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;
import co.id.fifgroup.workstructure.domain.OrganizationNode;

import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;

public interface OrganizationHierarchySetupService extends VersioningService<OrgHierarchyDTO> {
	
	void save(OrgHierarchyDTO subject) throws Exception;	
	void delete(OrgHierarchyDTO subject);	
	List<OrgHierarchyDTO> findByInquiry(OrgHierarchyDTO subject);	
	List<OrgHierarchyDTO> findByExample(OrgHierarchyDTO subject);	
	List<OrgHierarchyDTO> findAll();	
	List<OrganizationHierarchy> findByExample(OrganizationHierarchyExample example);
	List<OrganizationHierarchy> findByExampleWithRowBounds(OrganizationHierarchyExample example, int limit, int offset);
	List<OrgHierarchyDTO> findHierarchyIsDeptOwnerByOrgId(Long organizationId);	
	OrganizationNode findOrganizationNode(Long hierarchyId, Long organizationId, Long versionId, Date date, Boolean isActiveOrg);
	OrganizationNode findOrganizationNode(Long hierarchyId, Long organizationId, Boolean isActiveOrg);
	Integer countByExample(OrganizationHierarchyExample example);
	OrgHierarchyDTO findById(Long id);
	List<OrgHierarchyDTO> findHierarchy(String organizationHierarchyType, Date date, Long companyId);
	List<Long> findOrganizationInHierarchy(Long companyId);
	Boolean isFutureExist(Long id);
}
