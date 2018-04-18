package co.id.fifgroup.systemworkflow.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class VacationRuleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long vacationRuleId;
	private UUID approverId;
	private String approverFullName;
	private UUID substituteId;
	private String substituteFullName;
	private Date effectiveDateFrom;
	private Date effectiveDateTo;
	private Long createdBy;
	private Date createdDate;
	private Long lastUpdatedBy;
	private Date lastUpdatedDate;
	private String userName;

	public Long getVacationRuleId() {
		return vacationRuleId;
	}

	public void setVacationRuleId(Long vacationRuleId) {
		this.vacationRuleId = vacationRuleId;
	}

	public UUID getApproverId() {
		return approverId;
	}

	public void setApproverId(UUID approverId) {
		this.approverId = approverId;
	}

	public String getApproverFullName() {
		return approverFullName;
	}

	public void setApproverFullName(String approverFullName) {
		this.approverFullName = approverFullName;
	}

	public UUID getSubstituteId() {
		return substituteId;
	}

	public void setSubstituteId(UUID substituteId) {
		this.substituteId = substituteId;
	}

	public String getSubstituteFullName() {
		return substituteFullName;
	}

	public void setSubstituteFullName(String substituteFullName) {
		this.substituteFullName = substituteFullName;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
