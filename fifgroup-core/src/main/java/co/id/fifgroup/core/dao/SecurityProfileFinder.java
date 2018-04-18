package co.id.fifgroup.core.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.security.Security;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.ui.AuthorizedMenuDTO;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface SecurityProfileFinder {
	
	List<KeyValue> selectEmployeeAuthorizedCompanies(String username);
	
	List<KeyValue> selectEmployeeResponsibilityByCompany(@Param("username") String username, @Param("companyId") Long companyId);
	
	List<KeyValue> selectNonEmployeeResponsibilitiesByCompany(@Param("username") String username, @Param("companyId") Long companyId);
	
	List<AuthorizedMenuDTO> selectAutohrizedMenu(@Param("rootMenuId") Long rootMenuId, @Param("menuExclusions") List<Long> menuExclusions, @Param("functionExclusions") List<Long> functionExclusions);
	
	SecurityProfile getSecurityProfile(@Param("username") String username, @Param("companyId") Long companyId);
	
	List<String> getRolesByPersonIdAndJobId(@Param("personId") Long personId, @Param("jobId") Long jobId);

	Security selectSecurityByAssignment(@Param("respId") Long respId, @Param("companyId") Long companyId);
	
	Security selectSecurityMultiCompany(@Param("respId") Long respId, @Param("companyId") Long companyId);

	int countActiveUserByUsername(String userName);
	
	List<String> selectFieldPermissionByResponsibilityId(Long respId);
	
	List<Long> selectMenuItemExclusionByResponsibilityId(Long respId);
	
	List<Long> selectFunctionItemExclusionByResponsibilityId(Long respId);
	
	List<Long> selectGradeAssignmentByResponsibilityId(Long id);
	
	List<Long> selectGradeMultiCompanyByResponsibilityId(Long id);
	
	List<Long> selectOrganizationAssignmentByResponsibilityId(Long id);
	
	List<Long> selectOrganizationMultiCompanyByResponsibilityId(Long id);
	
	List<FunctionPermission> selectFunctionPermissionsByResponsibilityId(Long id);
	
	int isEmployee(@Param("username") String userName);
	
	List<KeyValue> getNonEmployeeAuthorizedCompaniesByUserName(@Param("username") String userName);
	
	void updateUserLastLoggedIn(@Param("username") String username, @Param("date")Date date);
	
	int countBusinessGroupAdminByUsername(String userName);
	
	List<KeyValue> getBusinessGroup();
	
	List<KeyValue> getSuperUserResponsibility();
	
	Security getResponsibilityById(Long id);
	
	Long getUserIdByUsername(String username);
	
	SecurityProfile getSecurityProfileIsEmployee(@Param("username") String userName);
	
	List<Long> selectOrgIdByOrgSvpId(@Param("orgId")Long orgId, @Param("companyId")Long companyId);
	
	List<KeyValue> selectCwkAuthorizedCompanies(String username);
	
	SecurityProfile getSecurityProfileIsCwk(@Param("username") String userName);
	
	List<KeyValue> selectCwkResponsibilityByCompany(@Param("username") String username, @Param("companyId") Long companyId);

	Long getUserIdByPersonId(@Param("personId") Long personId);
	
}
