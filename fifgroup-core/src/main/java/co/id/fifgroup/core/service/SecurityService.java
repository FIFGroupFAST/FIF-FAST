package co.id.fifgroup.core.service;

import java.util.List;

import org.zkoss.zul.DefaultTreeModel;

import co.id.fifgroup.core.constant.LoginStatus;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.security.Security;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.ui.TreeInfo;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface SecurityService {
	
	public SecurityProfile getSecurityProfile();
	
	public SecurityProfile getSecurityProfile(String userName, Long companyId);
	
	public List<KeyValue> getEmployeeAuthorizedCompanies(String username);
	
	public List<KeyValue> getResponsibilityByCompany(String username, Long companyId, LoginStatus loginStatus);
	
	public Security getSecurityByResponsibilityId(Long respId, Long companyId, boolean isAssignment);
	
	public int countActiveUserByUsername(String userName);
	
	public DefaultTreeModel<TreeInfo> getAuthorizedTreeModelMenuByRootMenuId(Long menuId, List<Long> menuExclusions, List<Long> functionExclusions);
	
	public FunctionPermission getFunctionPermissionByFunctionId(Long functionId);
	
	public boolean isEmployee(String userName);
	
	public List<KeyValue> getNonEmployeeAuthorizedCompaniesByUserName(String userName);
	
	public int isBusinessGroupAdmin(String userName);
	
	public List<KeyValue> getBusinessGroup();
	
	public List<KeyValue> getSuperUserResponsibility();
	
	public Security getSecurityByResponsibilityId(Long respId);
	
	public Long getUserIdByUsername(String username);
	
	public List<KeyValue> getCwkAuthorizedCompanies(String username);
	
}
