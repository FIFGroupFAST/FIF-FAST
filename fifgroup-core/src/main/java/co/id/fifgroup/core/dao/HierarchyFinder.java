package co.id.fifgroup.core.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface HierarchyFinder {
	
	List<Long> findOrganizationByParent(@Param("organizationId") Long organizationId, @Param("companyId") Long companyId);
	List<Long> findBranchOrganizations(@Param("organizationId") Long organizationId, @Param("companyId") Long companyId, @Param("effectiveDate") Date effectiveDate);
	List<Long> findAllOrgainzations(@Param("companyId") Long companyId);
	List<Long> findBranchWithoutPosByBranchId(@Param("organizationId") Long organizationId, @Param("companyId") Long companyId);
	List<Long> findHoOrganization(@Param("companyId") Long companyId);
	List<Long> findPosByBranch(@Param("organizationId") Long organizationId, @Param("companyId") Long companyId);
	Long findOrganizationIdByName(@Param("name") String name);
	List<Long> findAuthorizedOrganizations(@Param("organizationId") Long organizationId, @Param("branchId") Long branchId, 
			@Param("isWithoutPos") Boolean isWithoutPos, @Param("inOrganizationId") List<Long> inOrganizationId, 
			@Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("companyId")Long companyId);
	
	List<Long> findChildOrganizationDepartmentOwner(@Param("organizationId") Long organizationId, @Param("companyId") Long companyId);
}
