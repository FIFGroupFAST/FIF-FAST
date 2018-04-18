package co.id.fifgroup.workstructure.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;

public interface OrganizationHierarchyFinder {

	List<OrgHierarchyDTO> findByInquiry(OrgHierarchyDTO subject);
	List<OrgHierarchyDTO> findByExample(OrgHierarchyDTO subject);
	OrgHierarchyDTO findByIdAndVersionNumber(@Param("id") Long id, @Param("versionNumber") Integer versionNumber);
	List<Integer> findVersionsById(@Param("id") Long id);
	List<OrgHierarchyDTO> findAll(@Param("companyId") Long companyId);
	List<OrgHierarchyDTO> findHierarchyIsDeptOwnerByOrgId(@Param("organizationId") Long organizationId);
	OrgHierarchyDTO findById(@Param("id") Long id);
	Integer isHaveFuture(@Param("id") Long id);
	List<OrgHierarchyDTO> findHierarchy(@Param("type") String organizationHierarchyType, @Param("date") Date date, @Param("companyId") Long companyId);
	List<Long> findOrganizationInHierarchy(@Param("companyId") Long companyId);
	Integer isFutureExist(@Param("id") Long id);
}
