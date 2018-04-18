package co.id.fifgroup.systemadmin.service;

import java.util.List;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface ReportSecurityService {
	
	public List<KeyValue> getCompany(Long responsibilityId, Long currentCompanyId, String companyName, int limit, int offset);
	
	public List<KeyValue> getBranch(Long selectedCompanyId, Long responsibilityId, String branchCode, String branchName, int limit, int offset);
	
	public int countCompany(Long responsibilityId, Long currentCompanyId, String companyName);
	
	public int countBranch(Long selectedCompanyId, Long responsibilityId, String branchCode, String branchName);
	
	public List<KeyValue> getOrganization(Long selectedCompanyId, Long selectedBranchId, Long locationId, String organizationCode, String organizationName, int limit, int offset);
	
	public int countOrganization(Long selectedCompanyId, Long selectedBranchId, Long locationId,String organizationCode, String organizationName);
	
	public List<KeyValue> getLocation(Long responsibilityId, Long selectedCompanyId, Long seletedBranchId, String locationCode, String locationName, int limit, int offset);
	
	public int countLocation(Long responsibilityId, Long selectedCompanyId, Long seletedBranchId, String locationCode, String locationName);
	
	public List<KeyValue> getEmployee(Long companyId, Long branchId, Long locationId, Long organizationId, String employeeNumber, String fullName, int limit, int offset);
	
	public int countEmployee(Long companyId, Long branchId, Long locationId, Long organizationId, String employeeNumber, String fullName);
	
	public List<KeyValue> getJob(Long companyId, Long responsibilityId, String jobCode, String jobName, int limit, int offset);
	
	public int countJob(Long companyId, Long responsibilityId, String jobCode, String jobName);
	
	public List<KeyValue> getEmployeeCWK(Long companyId, Long branchId, Long locationId, Long organizationId, String employeeNumber, String fullName, int limit, int offset);
	
	public int countEmployeeCWK(Long companyId, Long branchId, Long locationId, Long organizationId, String employeeNumber, String fullName);
}
