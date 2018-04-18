package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface ReportSecurityFinder {
	
	public List<KeyValue> getCompany(@Param("responsibilityId")Long responsibilityId, @Param("currentCompanyId")Long currentCompanyId, @Param("companyName")String companyName, RowBounds rowBounds);
	
	public String getSecurityTypeByAssignment(@Param("responsibilityId")Long responsibilityId, @Param("companyId")Long companyId);
	
	public String getSecurityTypeOtherCompany(@Param("responsibilityId")Long responsibilityId, @Param("companyId")Long companyId);
	
	public List<KeyValue> getBranches(@Param("companyId")Long companyId, @Param("branchCode")String branchCode,@Param("branchName")String branchName, RowBounds rowBounds);
	
	public List<KeyValue> getBranchByResponsibility(@Param("responsibilityId")Long responsibilityId, @Param("personId")Long personId, @Param("branchCode")String branchCode, @Param("branchName")String branchName, RowBounds rowBounds);

	public int countCompany(@Param("responsibilityId")Long responsibilityId, @Param("currentCompanyId")Long currentCompanyId, @Param("companyName")String companyName);

	public int countBranches(@Param("companyId")Long companyId, @Param("branchCode")String branchCode, @Param("branchName")String branchName);
	
	public int countBranchByResponsibility(@Param("responsibilityId")Long responsibilityId, @Param("personId")Long personId, @Param("branchCode")String branchCode, @Param("branchName")String branchName);
	
	public List<KeyValue> getOrganizationByBranch(@Param("companyId")Long companyId, @Param("branchId")Long branchId, @Param("locationId")Long locationId, @Param("organizationCode") String organizationCode, @Param("organizationName")String organizationName, @Param("inOrgId")List<Long> inOrgId, @Param("notInOrgId")List<Long> notInOrgId, RowBounds rouBounds);
	
	public int countOrganizationByBranch(@Param("companyId")Long companyId, @Param("branchId")Long branchId, @Param("locationId")Long locationId, @Param("organizationCode") String organizationCode, @Param("organizationName")String organizationName, @Param("inOrgId")List<Long> inOrgId, @Param("notInOrgId")List<Long> notInOrgId);
	
	public KeyValue getCurrentLocation(@Param("organizationId")Long organizationId);
	
	public List<KeyValue> getLocation(@Param("companyId")Long companyId, @Param("branchOwnerId")Long branchOwnerId, @Param("locationCode")String locationCode, @Param("locationName")String locationName, RowBounds rowBounds);
	
	public int countLocation(@Param("companyId")Long companyId, @Param("branchOwnerId")Long branchOwnerId, @Param("locationCode")String locationCode, @Param("locationName")String locationName);
	
	public List<KeyValue> getEmployee(@Param("companyId")Long companyId,
									@Param("branchId")Long branchId,
									@Param("locationId")Long locationId,
									@Param("organizationId")Long organizationId,
									@Param("inOrgId")List<Long> inOrgId,
									@Param("notInOrgId")List<Long> notInOrgId,
									@Param("employeeNumber")String employeeNumber,
									@Param("fullName")String fullName,
									RowBounds rowBounds);
	
	public int countEmployee(@Param("companyId")Long companyId,
									@Param("branchId")Long branchId,
									@Param("locationId")Long locationId,
									@Param("organizationId")Long organizationId,
									@Param("inOrgId")List<Long> inOrgId,
									@Param("notInOrgId")List<Long> notInOrgId,
									@Param("employeeNumber")String employeeNumber,
									@Param("fullName")String fullName);
	
	public List<KeyValue> getJob(@Param("companyId")Long companyId, @Param("responsibilityId")Long responsibilityId, @Param("personId")Long personId, @Param("jobCode")String jobCode, @Param("jobName")String jobName, RowBounds rowbounds);
	
	public int countJob(@Param("companyId")Long companyId, @Param("responsibilityId")Long responsibilityId, @Param("personId")Long personId, @Param("jobCode")String jobCode, @Param("jobName")String jobName);
	
	public List<KeyValue> getEmployeeCWK(@Param("companyId")Long companyId,
			@Param("branchId")Long branchId,
			@Param("locationId")Long locationId,
			@Param("organizationId")Long organizationId,
			@Param("inOrgId")List<Long> inOrgId,
			@Param("notInOrgId")List<Long> notInOrgId,
			@Param("employeeNumber")String employeeNumber,
			@Param("fullName")String fullName,
			RowBounds rowBounds);
	
	public int countEmployeeCWK(@Param("companyId")Long companyId,
			@Param("branchId")Long branchId,
			@Param("locationId")Long locationId,
			@Param("organizationId")Long organizationId,
			@Param("inOrgId")List<Long> inOrgId,
			@Param("notInOrgId")List<Long> notInOrgId,
			@Param("employeeNumber")String employeeNumber,
			@Param("fullName")String fullName);
}
