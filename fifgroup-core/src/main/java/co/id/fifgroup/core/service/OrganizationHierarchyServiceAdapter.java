package co.id.fifgroup.core.service;

import java.util.Date;
import java.util.List;

public interface OrganizationHierarchyServiceAdapter {
	
	public List<Long> findOrganizationByParent(Long organizationId, Long workspaceCoyId);
	public List<Long> findBranchOrganizations(Long organizationId, Long workspaceCoyId);
	public List<Long> findBranchOrganizations(Long organizationId, Long workspaceCoyId, Date effectiveDate);
	public List<Long> findAllOrgainzations(Long workspaceCoyId);
	public List<Long> findBranchWithoutPosByBranchId(Long organizationId, Long workspaceCoyId);
	public List<Long> findHoOrganization(Long workspaceCoyId);
	public List<Long> findPosByBranch(Long organizationId, Long workspaceCoyId);
	public Long findOrganizationIdByName(String name);
	public List<Long> findAuthorizedOrganizations(Long organizationId, Long branchId, 
			Boolean isWithoutPos, List<Long> inOrganizationId, List<Long> notInOrganizationId, Long companyId);
	
	public List<Long> findChildOrganizationDepartmentOwner(Long organizationId, Long workspaceCoyId);
}
