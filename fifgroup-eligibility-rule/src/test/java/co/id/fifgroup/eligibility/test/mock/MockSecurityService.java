package co.id.fifgroup.eligibility.test.mock;

import java.util.List;

import org.zkoss.zul.DefaultTreeModel;

import co.id.fifgroup.core.constant.LoginStatus;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.security.Security;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.TreeInfo;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public class MockSecurityService implements SecurityService{

	@Override
	public SecurityProfile getSecurityProfile() {
		SecurityProfile profile = new SecurityProfile();
		profile.setWorkspaceBusinessGroupId(1L);
		profile.setCompanyId(1L);
		profile.setPersonId(15L);
		profile.setUserId(22L);
		profile.setWorkspaceBusinessGroupId(1L);
		return profile;
	}

	@Override
	public SecurityProfile getSecurityProfile(String userName, Long companyId) {
		SecurityProfile profile = new SecurityProfile();
		profile.setWorkspaceBusinessGroupId(1L);
		profile.setWorkspaceBusinessGroupId(1L);
		profile.setWorkspaceCompanyId(companyId);
		profile.setCompanyId(companyId);
		profile.setPersonId(15L);
		profile.setUserId(22L);
		return profile;
	}

	@Override
	public List<KeyValue> getEmployeeAuthorizedCompanies(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyValue> getResponsibilityByCompany(String username,
			Long companyId, LoginStatus loginStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Security getSecurityByResponsibilityId(Long respId, Long companyId,
			boolean isAssignment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countActiveUserByUsername(String userName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DefaultTreeModel<TreeInfo> getAuthorizedTreeModelMenuByRootMenuId(
			Long menuId, List<Long> menuExclusions,
			List<Long> functionExclusions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FunctionPermission getFunctionPermissionByFunctionId(Long functionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmployee(String userName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<KeyValue> getNonEmployeeAuthorizedCompaniesByUserName(
			String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int isBusinessGroupAdmin(String userName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<KeyValue> getBusinessGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyValue> getSuperUserResponsibility() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Security getSecurityByResponsibilityId(Long respId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUserIdByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyValue> getCwkAuthorizedCompanies(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
