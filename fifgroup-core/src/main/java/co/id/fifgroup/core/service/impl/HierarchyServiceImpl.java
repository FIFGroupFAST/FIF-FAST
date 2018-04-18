package co.id.fifgroup.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.dao.HierarchyFinder;
import co.id.fifgroup.core.service.OrganizationHierarchyServiceAdapter;

@Transactional
@Service
public class HierarchyServiceImpl implements OrganizationHierarchyServiceAdapter {

	@Autowired
	private HierarchyFinder hierarchyFinder;
	
	@Override
	public List<Long> findOrganizationByParent(Long organizationId, Long workspaceCoyId) {
		return hierarchyFinder.findOrganizationByParent(organizationId, workspaceCoyId);
	}

	@Override
	public List<Long> findBranchOrganizations(Long organizationId, Long workspaceCoyId) {
		return hierarchyFinder.findBranchOrganizations(organizationId, workspaceCoyId, null);
	}
	
	@Override
	public List<Long> findBranchOrganizations(Long organizationId, Long workspaceCoyId, Date effectiveDate) {
		return hierarchyFinder.findBranchOrganizations(organizationId, workspaceCoyId, effectiveDate);
	}


	@Override
	public List<Long> findAllOrgainzations(Long workspaceCoyId) {
		return hierarchyFinder.findAllOrgainzations(workspaceCoyId);
	}

	@Override
	public List<Long> findBranchWithoutPosByBranchId(Long organizationId,
			Long workspaceCoyId) {
		return hierarchyFinder.findBranchWithoutPosByBranchId(organizationId, workspaceCoyId);
	}

	@Override
	public List<Long> findHoOrganization(Long workspaceCoyId) {
		return hierarchyFinder.findHoOrganization(workspaceCoyId);
	}

	@Override
	public List<Long> findPosByBranch(Long organizationId, Long workspaceCoyId) {
		return hierarchyFinder.findPosByBranch(organizationId, workspaceCoyId);
	}

	@Override
	public Long findOrganizationIdByName(String name) {
		return hierarchyFinder.findOrganizationIdByName(name);
	}

	@Override
	public List<Long> findAuthorizedOrganizations(Long organizationId, Long branchId, 
			Boolean isWithoutPos, List<Long> inOrganizationId, List<Long> notInOrganizationId, Long companyId) {
		return hierarchyFinder.findAuthorizedOrganizations(organizationId, branchId, isWithoutPos, inOrganizationId, notInOrganizationId, companyId);
	}

	@Override
	public List<Long> findChildOrganizationDepartmentOwner(Long organizationId,
			Long workspaceCoyId) {
		return hierarchyFinder.findChildOrganizationDepartmentOwner(organizationId, workspaceCoyId);
	}
}
