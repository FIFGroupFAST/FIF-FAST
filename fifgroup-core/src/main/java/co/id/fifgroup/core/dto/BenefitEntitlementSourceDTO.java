package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;

public class BenefitEntitlementSourceDTO implements Serializable {

	private static final long serialVersionUID = 6851127810708245561L;

	private Long entitlementSourceId;
	private String source;
	private String reason;
	private Long personId;
	private Long assignmentId;
	private Date effectiveStartDate;
	private boolean processedFlag;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private Long companyId;

	public Long getEntitlementSourceId() {
		return entitlementSourceId;
	}

	public void setEntitlementSourceId(Long entitlementSourceId) {
		this.entitlementSourceId = entitlementSourceId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public boolean isProcessedFlag() {
		return processedFlag;
	}

	public void setProcessedFlag(boolean processedFlag) {
		this.processedFlag = processedFlag;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

}
