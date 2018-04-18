package co.id.fifgroup.core.domain.transaction;

import java.util.UUID;

public abstract class AbstractCommonTrx implements CommonTrx {

	private static final long serialVersionUID = 1L;

	protected Long branchId;
	protected Long branchType;
	protected Long jobId;
	protected Long gradeId;
	protected Long organizationId;
	protected Long locationId;
	protected String jobGroup;
	protected Long companyId;
	protected Long businessGroupId;
	protected Long keyJob;
	protected String employmentStatus;
	protected UUID objectEmployeeUUID;
	protected boolean basedLineRequestor;
	protected Long transactionType;
	protected int transactionApprovalStatus;
	protected Long organizationDestinationId;

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getBranchType() {
		return branchType;
	}

	public void setBranchType(Long branchType) {
		this.branchType = branchType;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getBusinessGroupId() {
		return businessGroupId;
	}

	public void setBusinessGroupId(Long businessGroupId) {
		this.businessGroupId = businessGroupId;
	}

	public Long getKeyJob() {
		return keyJob;
	}

	public void setKeyJob(Long keyJob) {
		this.keyJob = keyJob;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public UUID getObjectEmployeeUUID() {
		return objectEmployeeUUID;
	}

	public void setObjectEmployeeUUID(UUID objectEmployeeUUID) {
		this.objectEmployeeUUID = objectEmployeeUUID;
	}

	public boolean isBasedLineRequestor() {
		return basedLineRequestor;
	}

	public void setBasedLineRequestor(boolean basedLineRequestor) {
		this.basedLineRequestor = basedLineRequestor;
	}

	public Long getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Long transactionType) {
		this.transactionType = transactionType;
	}

	public int getTransactionApprovalStatus() {
		return transactionApprovalStatus;
	}

	public void setTransactionApprovalStatus(int transactionApprovalStatus) {
		this.transactionApprovalStatus = transactionApprovalStatus;
	}

	public Long getOrganizationDestinationId() {
		return organizationDestinationId;
	}

	public void setOrganizationDestinationId(Long organizationDestinationId) {
		this.organizationDestinationId = organizationDestinationId;
	}

	
}
