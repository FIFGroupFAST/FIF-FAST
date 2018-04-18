package co.id.fifgroup.core.dto;

import java.util.Date;

public class MppJournalTransactionDTO {

	private Long sourceTrxType;
	private Long sourceTrnId;
    private Date mppTrnDate;
    private Long periodSetId;
    private Long periodId;
    private Long organizationId;
    private Long jobId;
    private Long personId;
    private Long reserved;
    private Long existing;
    private String source;
    private String actionType;
    private Long mppTrnId;
    
    /**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	private String remark;
	/**
	 * @return the sourceTrxType
	 */
	public Long getSourceTrxType() {
		return sourceTrxType;
	}
	/**
	 * @param sourceTrxType the sourceTrxType to set
	 */
	public void setSourceTrxType(Long sourceTrxType) {
		this.sourceTrxType = sourceTrxType;
	}
	/**
	 * @return the sourceTrnId
	 */
	public Long getSourceTrnId() {
		return sourceTrnId;
	}
	/**
	 * @param sourceTrnId the sourceTrnId to set
	 */
	public void setSourceTrnId(Long sourceTrnId) {
		this.sourceTrnId = sourceTrnId;
	}
	/**
	 * @return the mppTrnDate
	 */
	public Date getMppTrnDate() {
		return mppTrnDate;
	}
	/**
	 * @param mppTrnDate the mppTrnDate to set
	 */
	public void setMppTrnDate(Date mppTrnDate) {
		this.mppTrnDate = mppTrnDate;
	}
	/**
	 * @return the periodSetId
	 */
	public Long getPeriodSetId() {
		return periodSetId;
	}
	/**
	 * @param periodSetId the periodSetId to set
	 */
	public void setPeriodSetId(Long periodSetId) {
		this.periodSetId = periodSetId;
	}
	/**
	 * @return the periodId
	 */
	public Long getPeriodId() {
		return periodId;
	}
	/**
	 * @param periodId the periodId to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	/**
	 * @return the organizationId
	 */
	public Long getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the jobId
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	/**
	 * @return the reserved
	 */
	public Long getReserved() {
		return reserved;
	}
	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(Long reserved) {
		this.reserved = reserved;
	}
	/**
	 * @return the existing
	 */
	public Long getExisting() {
		return existing;
	}
	/**
	 * @param existing the existing to set
	 */
	public void setExisting(Long existing) {
		this.existing = existing;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getMppTrnId() {
		return mppTrnId;
	}
	
	public void setMppTrnId(Long mppTrnId) {
		this.mppTrnId = mppTrnId;
	}
	
	

}
