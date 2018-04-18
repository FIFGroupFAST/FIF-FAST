package co.id.fifgroup.systemworkflow.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import co.id.fifgroup.systemworkflow.constants.TrxType;

import co.id.fifgroup.core.domain.transaction.CommonPromotionTrx;

public class PromotionTrx implements CommonPromotionTrx, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// need for approval process
	private Long branchType;
	private Long job;
	private Long grade;
	private Long organization;
	private Long location;
	private String jobGroup;
	private Long company;
	private Long businessGroup;
	private Long keyJob;
	private boolean isBasedLineRequestor;
	private UUID objectEmployeeUUID;
	private String employmentStatus;
	private Long icpStatus;
	private String assignmentStatus;
	private int transactionApprovalStatus;
	
	private String empNumber;
	private String empName;
	private String destinationBranch;
	private String destinationOrganization;
	private String destinationJob;
	private String destinationGrade;
	private Date effectiveDateFrom;
	private Date effectiveDateTo;
	private String remark;

	public void setBranchType(Long branchType) {
		this.branchType = branchType;
	}

	public void setJob(Long job) {
		this.job = job;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	public void setOrganization(Long organization) {
		this.organization = organization;
	}

	public void setLocation(Long location) {
		this.location = location;
	}

	public void setGroupJob(String groupJob) {
		this.jobGroup = groupJob;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public void setGroup(Long group) {
		this.businessGroup = group;
	}

	public void setKeyJob(Long keyJob) {
		this.keyJob = keyJob;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public void setBasedLineRequestor(boolean isBasedLineRequestor) {
		this.isBasedLineRequestor = isBasedLineRequestor;
	}
	
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public void setBusinessGroup(Long businessGroup) {
		this.businessGroup = businessGroup;
	}

	public void setIcpStatus(Long icpStatus) {
		this.icpStatus = icpStatus;
	}

	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	public void setObjectEmployeeUUID(UUID objectEmployeeUUID) {
		this.objectEmployeeUUID = objectEmployeeUUID;
	}
	
	public void setTransactionApprovalStatus(int transactionApprovalStatus) {
		this.transactionApprovalStatus = transactionApprovalStatus;
	}

	@Override
	public Long getBranchType() {
		return branchType;
	}

	@Override
	public Long getJobId() {
		return job;
	}

	@Override
	public Long getGradeId() {
		return grade;
	}

	@Override
	public Long getOrganizationId() {
		return organization;
	}

	@Override
	public Long getLocationId() {
		return location;
	}

	@Override
	public String getJobGroup() {
		return jobGroup;
	}

	@Override
	public Long getCompanyId() {
		return company;
	}

	@Override
	public Long getBusinessGroupId() {
		return businessGroup;
	}

	@Override
	public Long getKeyJob() {
		return keyJob;
	}

	@Override
	public String getEmploymentStatus() {
		return employmentStatus;
	}

	@Override
	public boolean isBasedLineRequestor() {
		return isBasedLineRequestor;
	}

	@Override
	public Long getIcpStatus() {
		return icpStatus;
	}

	@Override
	public String getAssignmentStatus() {
		return assignmentStatus;
	}

	@Override
	public UUID getObjectEmployeeUUID() {
		return objectEmployeeUUID;
	}

	public String getEmpNumber() {
		return empNumber;
	}

	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDestinationBranch() {
		return destinationBranch;
	}

	public void setDestinationBranch(String destinationBranch) {
		this.destinationBranch = destinationBranch;
	}

	public String getDestinationOrganization() {
		return destinationOrganization;
	}

	public void setDestinationOrganization(String destinationOrganization) {
		this.destinationOrganization = destinationOrganization;
	}

	public String getDestinationJob() {
		return destinationJob;
	}

	public void setDestinationJob(String destinationJob) {
		this.destinationJob = destinationJob;
	}

	public String getDestinationGrade() {
		return destinationGrade;
	}

	public void setDestinationGrade(String destinationGrade) {
		this.destinationGrade = destinationGrade;
	}

	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	public void setEffectiveDateFrom(Date effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}

	public Date getEffectiveDateTo() {
		return effectiveDateTo;
	}

	public void setEffectiveDateTo(Date effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public Long getTransactionType() {
		return TrxType.PRO_IRREGULAR_REQUEST.getCode();
	}

	@Override
	public int getTransactionApprovalStatus() {
		return transactionApprovalStatus;
	}

	@Override
	public Long getOrganizationDestinationId() {
		return null;
	}

	@Override
	public Boolean getCanBeActing() {
		return null;
	}	
}
