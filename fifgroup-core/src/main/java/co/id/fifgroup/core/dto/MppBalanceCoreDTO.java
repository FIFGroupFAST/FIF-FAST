package co.id.fifgroup.core.dto;

import java.util.Date;

public class MppBalanceCoreDTO {

	private Long periodSetId;
	private String periodSetName;
	private Date periodSetStartDate;
	private Date periodSetEndDate;
	
	private Long periodId;
	private String periodName;
	private Date periodFrom;
	private Date periodTo;
	private Long companyId;
	
	private Long branchId;
	private Long existing;
	private Long reserved;
	private Long headcount;
	private Long availableHeadcount;

	private String deptOwnerCode;
	private String deptOwnerName;
	private String branchName;
	private String organizationName;
	private String jobName;
	private String remark;
	
	private Date updateStartDate;
	private Date updateEndDate;
	
	private Date asOfDate;
	private Date balanceDateFrom;
	private Date balanceDateTo;
	private Long balanceId;

    private Long organizationId;
    private Long jobId;
    private String jobForCode;
    private Date dateFrom;
    private Date dateTo;
    private Long optimumBalance;
    private Long createdBy;
    private Date creationDate;
    private Long lastUpdatedBy;
    private Date lastUpdateDate;
	public Long getPeriodSetId() {
		return periodSetId;
	}
	public void setPeriodSetId(Long periodSetId) {
		this.periodSetId = periodSetId;
	}
	public String getPeriodSetName() {
		return periodSetName;
	}
	public void setPeriodSetName(String periodSetName) {
		this.periodSetName = periodSetName;
	}
	public Date getPeriodSetStartDate() {
		return periodSetStartDate;
	}
	public void setPeriodSetStartDate(Date periodSetStartDate) {
		this.periodSetStartDate = periodSetStartDate;
	}
	public Date getPeriodSetEndDate() {
		return periodSetEndDate;
	}
	public void setPeriodSetEndDate(Date periodSetEndDate) {
		this.periodSetEndDate = periodSetEndDate;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public Date getPeriodFrom() {
		return periodFrom;
	}
	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}
	public Date getPeriodTo() {
		return periodTo;
	}
	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getExisting() {
		return existing;
	}
	public void setExisting(Long existing) {
		this.existing = existing;
	}
	public Long getReserved() {
		return reserved;
	}
	public void setReserved(Long reserved) {
		this.reserved = reserved;
	}
	public Long getHeadcount() {
		return headcount;
	}
	public void setHeadcount(Long headcount) {
		this.headcount = headcount;
	}
	public Long getAvailableHeadcount() {
		return availableHeadcount;
	}
	public void setAvailableHeadcount(Long availableHeadcount) {
		this.availableHeadcount = availableHeadcount;
	}
	public String getDeptOwnerCode() {
		return deptOwnerCode;
	}
	public void setDeptOwnerCode(String deptOwnerCode) {
		this.deptOwnerCode = deptOwnerCode;
	}
	public String getDeptOwnerName() {
		return deptOwnerName;
	}
	public void setDeptOwnerName(String deptOwnerName) {
		this.deptOwnerName = deptOwnerName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdateStartDate() {
		return updateStartDate;
	}
	public void setUpdateStartDate(Date updateStartDate) {
		this.updateStartDate = updateStartDate;
	}
	public Date getUpdateEndDate() {
		return updateEndDate;
	}
	public void setUpdateEndDate(Date updateEndDate) {
		this.updateEndDate = updateEndDate;
	}
	public Date getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(Date asOfDate) {
		this.asOfDate = asOfDate;
	}
	public Date getBalanceDateFrom() {
		return balanceDateFrom;
	}
	public void setBalanceDateFrom(Date balanceDateFrom) {
		this.balanceDateFrom = balanceDateFrom;
	}
	public Date getBalanceDateTo() {
		return balanceDateTo;
	}
	public void setBalanceDateTo(Date balanceDateTo) {
		this.balanceDateTo = balanceDateTo;
	}
	public Long getBalanceId() {
		return balanceId;
	}
	public void setBalanceId(Long balanceId) {
		this.balanceId = balanceId;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getJobForCode() {
		return jobForCode;
	}
	public void setJobForCode(String jobForCode) {
		this.jobForCode = jobForCode;
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
	public Long getOptimumBalance() {
		return optimumBalance;
	}
	public void setOptimumBalance(Long optimumBalance) {
		this.optimumBalance = optimumBalance;
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
    
    
}
