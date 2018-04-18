package co.id.fifgroup.ssoa.dto;

import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.ssoa.domain.SOPeriod;

import java.util.List;
import java.util.UUID;

public class SOPeriodDTO extends SOPeriod implements CommonTrx {
	
	private static final long serialVersionUID = 5749051688120495054L;
	
	private List<SOPeriodDetailDTO> soPeriodDetailDto;
	private String userName;
	
	public List<SOPeriodDetailDTO> getSOPeriodDetailDto() {
		return soPeriodDetailDto;
	}
	public void setSOPeriodDetailDto(List<SOPeriodDetailDTO> soPeriodDetailDto) {
		this.soPeriodDetailDto = soPeriodDetailDto;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
