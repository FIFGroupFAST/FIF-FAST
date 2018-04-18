package co.id.fifgroup.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zul.DefaultTreeModel;

import co.id.fifgroup.core.constant.FilterType;
import co.id.fifgroup.core.constant.LoginStatus;
import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.dao.SecurityProfileFinder;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.security.Security;
import co.id.fifgroup.core.security.SecurityFilter;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.OrganizationHierarchyServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.AuthorizedMenuDTO;
import co.id.fifgroup.core.ui.SimpleTreeNode;
import co.id.fifgroup.core.ui.SimpleTreeNodeCollection;
import co.id.fifgroup.core.ui.TreeInfo;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@Transactional(readOnly=true)
@Service
public class SecurityServiceImpl implements SecurityService{
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	@Autowired 
	private SecurityProfileFinder securityProfileFinder;
	
	@Autowired
	private OrganizationHierarchyServiceAdapter hierarchyServiceImpl;
	
	@Override
	public SecurityProfile getSecurityProfile() {
		try {
			SecurityProfile securityProfile = (SecurityProfile) SecurityContextHolder.getContext().getAuthentication().getDetails();
			return securityProfile;
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		}
		return null;
		
	}

	@Override
	public List<KeyValue> getEmployeeAuthorizedCompanies(String username) {
		return securityProfileFinder.selectEmployeeAuthorizedCompanies(username);
	}

	@Override
	public List<KeyValue> getResponsibilityByCompany(String username, Long companyId, LoginStatus loginStatus) {
		List<KeyValue> responsibilities = null;
		switch (loginStatus) {
		case EMPLOYEE:
			responsibilities = securityProfileFinder.selectEmployeeResponsibilityByCompany(username, companyId);
			break;
		case NON_EMPOYEE:
			responsibilities =  securityProfileFinder.selectNonEmployeeResponsibilitiesByCompany(username, companyId);
			break;
		case CWK:
			responsibilities = securityProfileFinder.selectCwkResponsibilityByCompany(username, companyId);
			break;
		}
		
		return responsibilities;
		
	}

	@Override
	public SecurityProfile getSecurityProfile(String userName, Long companyId) {
		// Modified by Jatis for CWK
		// Bug 10155 : don't get security profile by company, can caused inconsistent data if the employee is transfered, get by people type instead
		// SecurityProfile securityProfile = securityProfileFinder.getSecurityProfile(userName, companyId);
		SecurityProfile securityProfile = securityProfileFinder.getSecurityProfileIsEmployee(userName);
		if (securityProfile != null && (securityProfile.getOrganizationId() == null && securityProfile.getPersonId() != null)) {
			securityProfile = securityProfileFinder.getSecurityProfileIsEmployee(userName);
			
			// add for cwk handling
			if (securityProfile == null || securityProfile.getPersonId() == null) {
				securityProfile = securityProfileFinder.getSecurityProfileIsCwk(userName);
			}
			// end

		}
		//reload cwk if person info in security profile is null
		if (securityProfile == null || securityProfile.getPersonId() == null) {
			securityProfile = securityProfileFinder.getSecurityProfileIsCwk(userName);
		}
		// End modified by Jatis for CWK
		
		//add by jatis. ITSM ticket : 15060309111598 (ERROR LOGIN HCMS ADMIN). 26 Jun 2015
		//start
		if(securityProfile == null){
			securityProfile = securityProfileFinder.getSecurityProfile(userName, companyId);
		}
		//end
		
		if(securityProfile != null && securityProfile.getJobId() != null) //If has't assignment in this company
			securityProfile.setRoles(securityProfileFinder.getRolesByPersonIdAndJobId(securityProfile.getPersonId(), securityProfile.getJobId()));
		
		return securityProfile;
	}

	@Override
	public Security getSecurityByResponsibilityId(Long respId, Long companyId, boolean isAssignment) {
		Security security = new Security();
		if(isAssignment){
			security = securityProfileFinder.selectSecurityByAssignment(respId, companyId);
		}else{
			security = securityProfileFinder.selectSecurityMultiCompany(respId, companyId);
		}
		security.setSecurityFilter(getSecurityFilter(security, companyId, isAssignment));
		security.setFieldPermissions(securityProfileFinder.selectFieldPermissionByResponsibilityId(respId));
		security.setFunctionPermissions(securityProfileFinder.selectFunctionPermissionsByResponsibilityId(respId));
		List<Long> menuItemExclusions = securityProfileFinder.selectMenuItemExclusionByResponsibilityId(respId);
		if(menuItemExclusions == null || menuItemExclusions.size() == 0)
			security.setMenuItemExclusions(null);
		else
			security.setMenuItemExclusions(securityProfileFinder.selectMenuItemExclusionByResponsibilityId(respId));
		List<Long> functionExclusions = securityProfileFinder.selectFunctionItemExclusionByResponsibilityId(respId);
		if(functionExclusions == null || functionExclusions.size() == 0)
			security.setFunctionItemExclusions(null);
		else
			security.setFunctionItemExclusions(securityProfileFinder.selectFunctionItemExclusionByResponsibilityId(respId));
		
		return security;
	}
	
	private SecurityFilter getSecurityFilter(Security security, Long companyId, boolean isAssignment){
		SecurityFilter securityFilter = new SecurityFilter();
		SecurityProfile profile = (SecurityProfile) SecurityContextHolder.getContext().getAuthentication().getDetails();
		List<Long> gradeIds = securityProfileFinder.selectGradeAssignmentByResponsibilityId(security.getResponsibilityId());
		
		if(security.getSecurityType().equals(SecurityType.VIEW_OWN.toString())){
			securityFilter.setPersonId(profile.getPersonId());
			securityFilter.setInOrganizations(null);
			securityFilter.setNotInOrganizations(null);
			securityFilter.setGradeExclusions(null);
		} else if(security.getSecurityType().equals(SecurityType.VIEW_SINGLE.toString())){
			List<Long> org = new ArrayList<Long>();
			org.add(profile.getOrganizationId());
			securityFilter.setInOrganizations(org);
			securityFilter.setNotInOrganizations(null);
			if(!gradeIds.isEmpty())
				securityFilter.setGradeExclusions(gradeIds);
			else
				securityFilter.setGradeExclusions(null);
			securityFilter.setAuthorizedOrganizations(org);
		} else if(security.getSecurityType().equals(SecurityType.VIEW_HIERARCHY.toString())){
			//get List of organization hierarchy by company id and root organization id
			
			List<Long> orgs = new ArrayList<Long>();
			if(isAssignment){
				orgs = hierarchyServiceImpl.findOrganizationByParent(profile.getOrganizationId(), profile.getWorkspaceCompanyId());
			}else{
				List<Long> orgList = securityProfileFinder.selectOrgIdByOrgSvpId(profile.getOrganizationId(), profile.getWorkspaceCompanyId());
				for (Long org : orgList) {
					orgs.addAll(hierarchyServiceImpl.findOrganizationByParent(org, profile.getWorkspaceCompanyId()));
				}
				if(orgs.isEmpty())
					orgs.add(-1L);
			}
			
			
			if (!orgs.contains(profile.getOrganizationId()) && isAssignment)
				orgs.add(profile.getOrganizationId());
			
			if(profile.getBranchId() != null && profile.getBranchId().equals(-1L))
				orgs.add(-1L);
			
			securityFilter.setInOrganizations(orgs);
			securityFilter.setAuthorizedOrganizations(orgs);
			if(!gradeIds.isEmpty())
				securityFilter.setGradeExclusions(gradeIds);
			else
				securityFilter.setGradeExclusions(null);
			securityFilter.setNotInOrganizations(null);
		} else if(security.getSecurityType().equals(SecurityType.VIEW_BRANCH.toString())){
			//get List of organization in a branch by company id and root organization id
			List<Long> orgs = hierarchyServiceImpl.findBranchOrganizations(profile.getOrganizationId(), profile.getWorkspaceCompanyId(), new LocalDate().toDate());
			if(profile.getBranchId() != null && profile.getBranchId().equals(-1L))
				orgs.add(-1L);
			if (orgs.isEmpty())
				orgs.add(0L);
			securityFilter.setInOrganizations(orgs);
			securityFilter.setNotInOrganizations(null);
			securityFilter.setAuthorizedOrganizations(orgs);
			if(!gradeIds.isEmpty())
				securityFilter.setGradeExclusions(gradeIds);
			else
				securityFilter.setGradeExclusions(null);
		} else if(security.getSecurityType().equals(SecurityType.VIEW_FUNCTIONAL.toString())){
			//get List of organization by Functional Hierarchy
			List<Long> orgs = hierarchyServiceImpl.findChildOrganizationDepartmentOwner(profile.getOrganizationId(), profile.getWorkspaceCompanyId());
			orgs.add(0L);
			securityFilter.setInOrganizations(orgs);
			securityFilter.setNotInOrganizations(null);
			securityFilter.setAuthorizedOrganizations(orgs);
			if(!gradeIds.isEmpty())
				securityFilter.setGradeExclusions(gradeIds);
			else
				securityFilter.setGradeExclusions(null);
		} else if(security.getSecurityType().equals(SecurityType.VIEW_ALL.toString())){
			//If security type is View All Organization
				
			if(isAssignment){
				
				if(!gradeIds.isEmpty())
					securityFilter.setGradeExclusions(gradeIds);
				else
					securityFilter.setGradeExclusions(null);

				List<Long> orgs = securityProfileFinder.selectOrganizationAssignmentByResponsibilityId(security.getResponsibilityId());
				List<Long> allOrgs = hierarchyServiceImpl.findAllOrgainzations(profile.getWorkspaceCompanyId());
				if(security.isRecursive()){
					List<Long> recureOrgs = new ArrayList<Long>();
					for (Long org : orgs) {
						recureOrgs.addAll(hierarchyServiceImpl.findOrganizationByParent(org, profile.getWorkspaceCompanyId()));
					}
					
					if(security.getOrgFilter().equals(FilterType.INCLUDE.toString())){
						securityFilter.setInOrganizations(recureOrgs);
						securityFilter.setAuthorizedOrganizations(recureOrgs);
					}else{
						if(recureOrgs != null && recureOrgs.size() != 0){
							securityFilter.setNotInOrganizations(recureOrgs);
							allOrgs.removeAll(recureOrgs);
							securityFilter.setAuthorizedOrganizations(allOrgs);
						}else{
							securityFilter.setNotInOrganizations(null);
							//securityFilter.setAuthorizedOrganizations(allOrgs);
						}
					}
				}else{
					if(security.getOrgFilter().equals(FilterType.INCLUDE.toString())){
						securityFilter.setInOrganizations(orgs);
						securityFilter.setAuthorizedOrganizations(orgs);
					}else{
						if(orgs != null && orgs.size() != 0){
							securityFilter.setNotInOrganizations(orgs);
							allOrgs.removeAll(orgs);
							securityFilter.setAuthorizedOrganizations(allOrgs);
						}else{
							securityFilter.setNotInOrganizations(null);
							//securityFilter.setAuthorizedOrganizations(allOrgs);
						}
						}
				}
					
			}else{
				List<Long> gradeMulti = securityProfileFinder.selectGradeMultiCompanyByResponsibilityId(security.getResponsibilityId());
				if(!gradeMulti.isEmpty())
					securityFilter.setGradeExclusions(gradeMulti);
				else
					securityFilter.setGradeExclusions(null);
				
				List<Long> allOrgs = hierarchyServiceImpl.findAllOrgainzations(profile.getWorkspaceCompanyId());
				if(security.getOrgFilter().equals(FilterType.INCLUDE.toString())){
					securityFilter.setInOrganizations(securityProfileFinder.selectOrganizationMultiCompanyByResponsibilityId(security.getResponsibilityId()));
					securityFilter.setAuthorizedOrganizations(securityProfileFinder.selectOrganizationMultiCompanyByResponsibilityId(security.getResponsibilityId()));
				}else{
					List<Long> orgs = securityProfileFinder.selectOrganizationMultiCompanyByResponsibilityId(security.getResponsibilityId());
					if(orgs != null && orgs.size() != 0){
						securityFilter.setNotInOrganizations(orgs);
						allOrgs.removeAll(orgs);
						securityFilter.setAuthorizedOrganizations(allOrgs);
					}else{
						securityFilter.setNotInOrganizations(null);							
						//securityFilter.setAuthorizedOrganizations(allOrgs);
					}
				}
			}
				
		}
		return securityFilter;
	}

	@Override
	public int countActiveUserByUsername(String userName) {
		int count = securityProfileFinder.countActiveUserByUsername(userName);
		return count;
			
	}

	@Override
	public DefaultTreeModel<TreeInfo> getAuthorizedTreeModelMenuByRootMenuId(Long menuId, List<Long> menuExclusions, List<Long> functionExclusions) {
		SimpleTreeNodeCollection<TreeInfo> children = buildNode(menuId, menuExclusions, functionExclusions);
    	SimpleTreeNode<TreeInfo> treeNode = new SimpleTreeNode<TreeInfo>(new TreeInfo(null, null), children, true);
    	return new DefaultTreeModel<TreeInfo>(treeNode);
	}
	
	private SimpleTreeNodeCollection<TreeInfo> buildNode(Long rootMenuId, List<Long> menuExclusions, List<Long> functionExclusions){
		SimpleTreeNodeCollection<TreeInfo> child =  new SimpleTreeNodeCollection<>();
		child.addAll(populateMenu(rootMenuId, menuExclusions, functionExclusions));
		return child;
	}
	
	private SimpleTreeNodeCollection<TreeInfo> populateMenu(Long rootMenuId, List<Long> menuExclusions, List<Long> functionExclusions){
		SimpleTreeNodeCollection<TreeInfo> ch = new SimpleTreeNodeCollection<>();
    	
    	List<AuthorizedMenuDTO> menus = securityProfileFinder.selectAutohrizedMenu(rootMenuId, menuExclusions, functionExclusions);
    	
    	for (AuthorizedMenuDTO m : menus) {
    		String type = m.getMenuItemType();
    		if(type.equals(MenuItemType.SUBMENU.toString())){
    			SimpleTreeNodeCollection<TreeInfo> child =  buildNode(m.getActionId(), menuExclusions, functionExclusions);
        		SimpleTreeNode<TreeInfo> node = new SimpleTreeNode<TreeInfo>(new TreeInfo(m.getDisplayName(), MenuItemType.SUBMENU.toString(), m), child, true);
    			ch.add(node);
    		}else{
    			SimpleTreeNode<TreeInfo> node = new SimpleTreeNode<TreeInfo>(new TreeInfo(m.getDisplayName(), MenuItemType.FUNCTION.toString(), m));
    			ch.add(node);
    		}
    		
		}
    	
    	return ch;
	}

	@Override
	public FunctionPermission getFunctionPermissionByFunctionId(Long functionId) {
		FunctionPermission function = null;
		for (FunctionPermission functionPermission : getSecurityProfile().getSecurity().getFunctionPermissions()) {
			if(functionPermission.getFunctionId().equals(functionId)){
				function = functionPermission;
			}
		}
		return function;
	}

	@Override
	public boolean isEmployee(String userName) {
		return securityProfileFinder.isEmployee(userName) == 0 ? false : true;
	}
	
	/**
	 * Getting list of company. It only select company that allow other user to access based on Responsibility 
	 */
	@Override
	public List<KeyValue> getNonEmployeeAuthorizedCompaniesByUserName(String userName) {
		return securityProfileFinder.getNonEmployeeAuthorizedCompaniesByUserName(userName);
	}

	@Override
	public int isBusinessGroupAdmin(String userName) {
		return securityProfileFinder.countBusinessGroupAdminByUsername(userName);
	}

	@Override
	public List<KeyValue> getBusinessGroup() {
		return securityProfileFinder.getBusinessGroup();
	}

	@Override
	public List<KeyValue> getSuperUserResponsibility() {
		return securityProfileFinder.getSuperUserResponsibility();
	}

	@Override
	public Security getSecurityByResponsibilityId(Long respId) {
		return securityProfileFinder.getResponsibilityById(respId);
	}

	@Override
	public Long getUserIdByUsername(String username) {
		return securityProfileFinder.getUserIdByUsername(username);
	}

	@Override
	public List<KeyValue> getCwkAuthorizedCompanies(String username) {
		return securityProfileFinder.selectCwkAuthorizedCompanies(username);
	}

}
