package co.id.fifgroup.systemadmin.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.id.fifgroup.core.security.SecurityFilter;
import co.id.fifgroup.core.service.OrganizationHierarchyServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;

@Component
public class ReportSecurityResolver {
	
	@Autowired
	private OrganizationHierarchyServiceAdapter hierarchyServiceImpl;
	
	@Autowired
	private SecurityService securityServiceImpl;
	
	public List<Long> getAuthorizedOrganization(Long branchId, Long orgId, Boolean isWithoutPOS, SecurityFilter securityFilter, Long workspaceCoyId) {
		List<Long> organizations = new ArrayList<Long>();
		
//		if(branchId != null){
//			
//			if(orgId != null){
//				
//				if(isWithoutPOS){
//					//Get organization by organization root id and without POS level
//					organizations = hierarchyServiceImpl.findOrganizationByParent(orgId, workspaceCoyId);
//				}else{
//					//Get organization by organization root id\
//					organizations = hierarchyServiceImpl.findOrganizationByParent(orgId, workspaceCoyId);
//				}
//			}else{
//				if(isWithoutPOS){
//					//Get organization by branch id and without POS level
//					organizations = hierarchyServiceImpl.findBranchWithoutPosByBranchId(branchId, workspaceCoyId);
//				}else{
//					//Get organization by branch id
//					organizations = hierarchyServiceImpl.findBranchOrganizations(branchId, workspaceCoyId);
//				}
//			}
//			
//		}else{
//			if(orgId != null){
//				
//				if(isWithoutPOS){
//					//Get organization by organization root id and without POS level
//					organizations = hierarchyServiceImpl.findOrganizationByParent(orgId, workspaceCoyId);
//				}else{
//					//Get organization by organization root id
//					organizations = hierarchyServiceImpl.findOrganizationByParent(orgId, workspaceCoyId);
//				}
//			}else{
//				if(isWithoutPOS){
//					//Get all organization without POS level
//					//organizations = hierarchyServiceImpl.findOrganizationByParent(orgId, workspaceCoyId);
//				}else{
//					//Get organization by branch id
//					//organizations = hierarchyServiceImpl.findAllOrgainzations(workspaceCoyId);
//				}
//			}
//			
//		}
		
		organizations = hierarchyServiceImpl.findAuthorizedOrganizations(orgId, branchId, isWithoutPOS, securityFilter.getInOrganizations(), securityFilter.getNotInOrganizations(), workspaceCoyId);
		
		// if authorized organization not found
		if (organizations.size() < 1) {
			organizations.add(-1L);
		}
		return organizations;
	}

	public List<Long> getAuthorizedOrganization(SecurityFilter securityFilter, Long workspaceCoyId) {
		List<Long> organizations = securityFilter.getAuthorizedOrganizations();
		return null != organizations ? organizations : new ArrayList<Long>();
	}
}
