package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class IcpDTO implements Serializable{

	private static final long serialVersionUID = -3264512998222023644L;
	
	private Long submissionId;
    private Long personId;
    private Long companyId;
    private Long jobDestinationId;
    private String mobility;
    private Date requestDate;
    private Long requestedBy;
    private Date projectedDate;
    private String remark;
    private String status;
    private String approvalStatus;
    private Long createdBy;
    private Date creationDate;
    private Long lastUpdatedBy;
    private Date lastUpdateDate;
    private UUID avmTrxId;
	private Long branchId;
	private String branchName;
	private Long organizationId;
	private String organizationName;
	private String gradePerson;
	private String employeeNumber;
	private String fullName;
	private Long jobId;
	private String jobName;
	private String companyName;
	private List<Long> inOrganizationId;
	private List<Long> notInOrganizationId;
	private Date dateFrom;
	private Date dateTo;
	private String jobDestinationCode;
	private String jobDestinationName;
	private String userName;
	private String requestByName;
	private UUID personUUID;
	private Long isKeyJobDestination;
	private Long branchSource;
	private Long organizationDestinationId;
	private Long organizationDestinationName;
	
	public Long getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getJobDestinationId() {
		return jobDestinationId;
	}
	public void setJobDestinationId(Long jobDestinationId) {
		this.jobDestinationId = jobDestinationId;
	}
	public String getMobility() {
		return mobility;
	}
	public void setMobility(String mobility) {
		this.mobility = mobility;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Long getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(Long requestedBy) {
		this.requestedBy = requestedBy;
	}
	public Date getProjectedDate() {
		return projectedDate;
	}
	public void setProjectedDate(Date projectedDate) {
		this.projectedDate = projectedDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public UUID getAvmTrxId() {
		return avmTrxId;
	}
	public void setAvmTrxId(UUID avmTrxId) {
		this.avmTrxId = avmTrxId;
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
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getGradePerson() {
		return gradePerson;
	}
	public void setGradePerson(String gradePerson) {
		this.gradePerson = gradePerson;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<Long> getInOrganizationId() {
		return inOrganizationId;
	}
	public void setInOrganizationId(List<Long> inOrganizationId) {
		this.inOrganizationId = inOrganizationId;
	}
	public List<Long> getNotInOrganizationId() {
		return notInOrganizationId;
	}
	public void setNotInOrganizationId(List<Long> notInOrganizationId) {
		this.notInOrganizationId = notInOrganizationId;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getJobDestinationCode() {
		return jobDestinationCode;
	}
	public void setJobDestinationCode(String jobDestinationCode) {
		this.jobDestinationCode = jobDestinationCode;
	}
	public String getJobDestinationName() {
		return jobDestinationName;
	}
	public void setJobDestinationName(String jobDestinationName) {
		this.jobDestinationName = jobDestinationName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRequestByName() {
		return requestByName;
	}
	public void setRequestByName(String requestByName) {
		this.requestByName = requestByName;
	}
	public UUID getPersonUUID() {
		return personUUID;
	}
	public void setPersonUUID(UUID personUUID) {
		this.personUUID = personUUID;
	}
	public Long getIsKeyJobDestination() {
		return isKeyJobDestination;
	}
	public void setIsKeyJobDestination(Long isKeyJobDestination) {
		this.isKeyJobDestination = isKeyJobDestination;
	}
	public Long getBranchSource() {
		return branchSource;
	}
	public void setBranchSource(Long branchSource) {
		this.branchSource = branchSource;
	}
	public Long getOrganizationDestinationId() {
		return organizationDestinationId;
	}
	public void setOrganizationDestinationId(Long organizationDestinationId) {
		this.organizationDestinationId = organizationDestinationId;
	}
	public Long getOrganizationDestinationName() {
		return organizationDestinationName;
	}
	public void setOrganizationDestinationName(Long organizationDestinationName) {
		this.organizationDestinationName = organizationDestinationName;
	}
}
