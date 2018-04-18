package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class SOPeriodDetail {
	private Long soPeriodDtlId;
	private Long soPeriodId;
	private Long branchId;
	private Branch branch;
	private Long statusId;
	private String statusCode;
	private String statusName;
	private Long notificationStatusId;
	private String notificationStatusCode;
	private String notificationStatusName;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	private SOPeriod soPeriod;
	
	private String branchCode;
	private String branchName;
	private Long lookUpId;
	private String avmTrxIdSOString;
	private String reportStatusCode;
	private String reportStatusName;
	
	
	public SOPeriodDetail() {}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getNotificationStatusName() {
		return notificationStatusName;
	}

	public void setNotificationStatusName(String notificationStatusName) {
		this.notificationStatusName = notificationStatusName;
	}
	
	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	
	public Long getSoPeriodDtlId() {
		return soPeriodDtlId;
	}

	public void setSoPeriodDtlId(Long soPeriodDtlId) {
		this.soPeriodDtlId = soPeriodDtlId;
	}

	public Long getSoPeriodId() {
		return soPeriodId;
	}
	public void setSoPeriodId(Long soPeriodId) {
		this.soPeriodId = soPeriodId;
	}

	public void setNotificationStatusCode(String notificationStatusCode){
		this.notificationStatusCode= notificationStatusCode;
	}
	
	public String getNotificationStatusCode(){
		return notificationStatusCode;
	}
	
	public void setNotificationStatusId(Long notificationStatusId){
		this.notificationStatusId= notificationStatusId;
	}
	
	public Long getNotificationStatusId(){
		return notificationStatusId;
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

	public SOPeriod getSoPeriod() {
		return soPeriod;
	}

	public void setSoPeriod(SOPeriod soPeriod) {
		this.soPeriod = soPeriod;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getLookUpId() {
		return lookUpId;
	}

	public void setLookUpId(Long lookUpId) {
		this.lookUpId = lookUpId;
	}

	public String getAvmTrxIdSOString() {
		return avmTrxIdSOString;
	}

	public void setAvmTrxIdSOString(String avmTrxIdSOString) {
		this.avmTrxIdSOString = avmTrxIdSOString;
	}

	public String getReportStatusCode() {
		return reportStatusCode;
	}

	public void setReportStatusCode(String reportStatusCode) {
		this.reportStatusCode = reportStatusCode;
	}

	public String getReportStatusName() {
		return reportStatusName;
	}

	public void setReportStatusName(String reportStatusName) {
		this.reportStatusName = reportStatusName;
	}
	
	
}