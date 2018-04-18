package co.id.fifgroup.core.security;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class SecurityProfile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String userName;
	private Long personId;
	private UUID personUUID;
	private String fullName;
	private String employeeNumber;
	private Long companyId;
	private Long workspaceCompanyId;
	private Long businessGroupId;
	private Long organizationId;
	private String organizationCode;
	private String organizationName;
	private Long jobId;
	private String jobCode;
	private String jobName;
	private Long gradeId;
	private String gradeCode;
	private Long branchId;
	private String branchName;
	private Long workspaceBusinessGroupId;

	private List<String> roles;
	private Security security;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public UUID getPersonUUID() {
		return personUUID;
	}
	public void setPersonUUID(UUID personUUID) {
		this.personUUID = personUUID;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public Long getWorkspaceCompanyId() {
		return workspaceCompanyId;
	}
	public void setWorkspaceCompanyId(Long workspaceCompanyId) {
		this.workspaceCompanyId = workspaceCompanyId;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public Long getWorkspaceBusinessGroupId() {
		return workspaceBusinessGroupId;
	}
	public void setWorkspaceBusinessGroupId(Long workspaceBusinessGroupId) {
		this.workspaceBusinessGroupId = workspaceBusinessGroupId;
	}
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

/*	@Override
	public String toString() {
		return "SecurityProfile [userId=" + userId + ", userName=" + userName
				+ ", personId=" + personId + ", personUUID=" + personUUID
				+ ", fullName=" + fullName + ", employeeNumber="
				+ employeeNumber + ", companyId=" + companyId
				+ ", workspaceCompanyId=" + workspaceCompanyId
				+ ", businessGroupId=" + businessGroupId + ", organizationId="
				+ organizationId + ", organizationCode=" + organizationCode
				+ ", organizationName=" + organizationName + ", jobId=" + jobId
				+ ", jobCode=" + jobCode + ", jobName=" + jobName
				+ ", gradeId=" + gradeId + ", gradeCode=" + gradeCode
				+ ", branchId=" + branchId + ", branchName=" + branchName
				+ ", workspaceBusinessGroupId=" + workspaceBusinessGroupId
				+ ", roles=" + roles + ", security=" + security + "]";
	}*/
	public Long getBusinessGroupId() {
		return businessGroupId;
	}
	public void setBusinessGroupId(Long businessGroupId) {
		this.businessGroupId = businessGroupId;
	}
	
	
	
	
	
}
