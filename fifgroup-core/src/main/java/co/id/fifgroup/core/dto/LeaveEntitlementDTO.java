package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;

public class LeaveEntitlementDTO implements Serializable {

	private static final long serialVersionUID = -8697951008685034910L;

	private Long entitlementId;
	private Long leaveTypeId;
	private String leaveType;
	private String description;
	private Long personId;
	private Date startActiveDate;
	private Date endActiveDate;
	private Integer entitlement;
	private Integer cfEntitlement;
	private Date cfEndActiveDate;
	private Long entitlementSourceId;
	private Short payrollProcessFlag;
	private Integer adjustedEntitlement;
	private String adjustmentReason;
	private Short priorPeriodAdvanceNum;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private Long companyId;
	private Integer used;
	private Integer balance;

	public Long getEntitlementId() {
		return entitlementId;
	}

	public void setEntitlementId(Long entitlementId) {
		this.entitlementId = entitlementId;
	}

	public Long getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(Long leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Date getStartActiveDate() {
		return startActiveDate;
	}

	public void setStartActiveDate(Date startActiveDate) {
		this.startActiveDate = startActiveDate;
	}

	public Date getEndActiveDate() {
		return endActiveDate;
	}

	public void setEndActiveDate(Date endActiveDate) {
		this.endActiveDate = endActiveDate;
	}

	public Integer getEntitlement() {
		return entitlement;
	}

	public void setEntitlement(Integer entitlement) {
		this.entitlement = entitlement;
	}

	public Integer getCfEntitlement() {
		return cfEntitlement;
	}

	public void setCfEntitlement(Integer cfEntitlement) {
		this.cfEntitlement = cfEntitlement;
	}

	public Date getCfEndActiveDate() {
		return cfEndActiveDate;
	}

	public void setCfEndActiveDate(Date cfEndActiveDate) {
		this.cfEndActiveDate = cfEndActiveDate;
	}

	public Long getEntitlementSourceId() {
		return entitlementSourceId;
	}

	public void setEntitlementSourceId(Long entitlementSourceId) {
		this.entitlementSourceId = entitlementSourceId;
	}

	public Short getPayrollProcessFlag() {
		return payrollProcessFlag;
	}

	public void setPayrollProcessFlag(Short payrollProcessFlag) {
		this.payrollProcessFlag = payrollProcessFlag;
	}

	public Integer getAdjustedEntitlement() {
		return adjustedEntitlement;
	}

	public void setAdjustedEntitlement(Integer adjustedEntitlement) {
		this.adjustedEntitlement = adjustedEntitlement;
	}

	public String getAdjustmentReason() {
		return adjustmentReason;
	}

	public void setAdjustmentReason(String adjustmentReason) {
		this.adjustmentReason = adjustmentReason;
	}

	public Short getPriorPeriodAdvanceNum() {
		return priorPeriodAdvanceNum;
	}

	public void setPriorPeriodAdvanceNum(Short priorPeriodAdvanceNum) {
		this.priorPeriodAdvanceNum = priorPeriodAdvanceNum;
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

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

}
