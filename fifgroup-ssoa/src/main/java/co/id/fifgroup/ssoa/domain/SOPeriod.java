package co.id.fifgroup.ssoa.domain;

import java.util.Date;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.AbstractCommonTrx;

public class SOPeriod extends AbstractCommonTrx {
	private Long soPeriodId;
	private Long companyId;
	private String description;
	private Date startDate;
	private Date endDate;
	private String startDateTostring;
	private String endDateTostring;
	private Integer involveBranch;
	private Integer notStartedBranch;
	private Integer inProcessBranch;
	private Integer submitBranch;
	private Integer approveBranch;
	private Integer closedBranch;
	private Long statusId;
	private String statusCode;
	private String statusName;
	private String fullName;
	private Date lastNotificationDate;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	private String sumbitBranchCode;
	private String approvedBranchCode;
	private String periodDate;
	private String closedBranchCode;
	private String inprocessBranchCode;
	private String notStartedBranchCode;
	
	private String avmTrxIdString;
	private UUID avmTrxId;

	public SOPeriod() {}

	
	public UUID getAvmTrxId() {
		return avmTrxId;
	}

	
	public String getStartDateTostring() {
		return startDateTostring;
	}


	public void setStartDateTostring(String startDateTostring) {
		this.startDateTostring = startDateTostring;
	}


	public String getEndDateTostring() {
		return endDateTostring;
	}


	public void setEndDateTostring(String endDateTostring) {
		this.endDateTostring = endDateTostring;
	}


	public void setAvmTrxId(UUID avmTrxId) {
		this.avmTrxId = avmTrxId;
	}
	
	
	
	public String getPeriodDate() {
		return periodDate;
	}


	public void setPeriodDate(String periodDate) {
		this.periodDate = periodDate;
	}


	public String getAvmTrxIdString() {
		return avmTrxIdString;
	}
	
	public void setAvmTrxIdString(String avmTrxIdString) {
		this.avmTrxIdString = avmTrxIdString;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public Long getSoPeriodId() {
		return soPeriodId;
	}

	public void setSoPeriodId(Long soPeriodId) {
		this.soPeriodId = soPeriodId;
	}

	public String getDescription() {
		return description;
	}
	
	public Long getCompanyId(){
		return companyId;
		
	}
	public void setCompanyId(Long companyId){
		this.companyId= companyId;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getInvolveBranch() {
		return involveBranch;
	}

	public void setInvolveBranch(Integer involveBranch) {
		this.involveBranch = involveBranch;
	}

	public Integer getNotStartedBranch() {
		return notStartedBranch;
	}

	public void setNotStartedBranch(Integer notStartedBranch) {
		this.notStartedBranch = notStartedBranch;
	}

	public Integer getInProcessBranch() {
		return inProcessBranch;
	}

	public void setInProcessBranch(Integer inProcessBranch) {
		this.inProcessBranch = inProcessBranch;
	}

	public Integer getSubmitBranch() {
		return submitBranch;
	}

	public void setSubmitBranch(Integer submitBranch) {
		this.submitBranch = submitBranch;
	}

	public Integer getApproveBranch() {
		return approveBranch;
	}

	public void setApproveBranch(Integer approveBranch) {
		this.approveBranch = approveBranch;
	}

	public Integer getClosedBranch() {
		return closedBranch;
	}

	public void setClosedBranch(Integer closedBranch) {
		this.closedBranch = closedBranch;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	
	public Date getLastNotificationDate() {
		return lastNotificationDate;
	}

	public void setLastNotificationDate(Date lastNotificationDate) {
		this.lastNotificationDate = lastNotificationDate;
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

	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}


	public String getSumbitBranchCode() {
		return sumbitBranchCode;
	}


	public void setSumbitBranchCode(String sumbitBranchCode) {
		this.sumbitBranchCode = sumbitBranchCode;
	}


	public String getApprovedBranchCode() {
		return approvedBranchCode;
	}


	public void setApprovedBranchCode(String approvedBranchCode) {
		this.approvedBranchCode = approvedBranchCode;
	}


	public String getClosedBranchCode() {
		return closedBranchCode;
	}


	public void setClosedBranchCode(String closedBranchCode) {
		this.closedBranchCode = closedBranchCode;
	}


	public String getInprocessBranchCode() {
		return inprocessBranchCode;
	}


	public void setInprocessBranchCode(String inprocessBranchCode) {
		this.inprocessBranchCode = inprocessBranchCode;
	}


	public String getNotStartedBranchCode() {
		return notStartedBranchCode;
	}
	
	
}