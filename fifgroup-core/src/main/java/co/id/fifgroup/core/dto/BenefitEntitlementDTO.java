package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import co.id.fifgroup.core.audit.Traversable;

public class BenefitEntitlementDTO implements Serializable, Traversable {

	private static final long serialVersionUID = -8697951008685034910L;

	private Long entitlementId;
	private Long companyId;
	private Long benefitTypeId;
	private String benefitType;
	private boolean prepayment;
	private String description;
	private Long personId;
	private Long contactId;
	private String beneficiary;
	private Date startActiveDate;
	private Date endActiveDate;
	private Long entitlementSourceId;
	private BigDecimal plafond;
	private BigDecimal adjustedPlafond;
	private String adjustmentReason;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private Integer used;
	private Integer balance;

	public Long getEntitlementId() {
		return entitlementId;
	}

	public void setEntitlementId(Long entitlementId) {
		this.entitlementId = entitlementId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getBenefitTypeId() {
		return benefitTypeId;
	}

	public void setBenefitTypeId(Long benefitTypeId) {
		this.benefitTypeId = benefitTypeId;
	}

	public String getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}

	public boolean isPrepayment() {
		return prepayment;
	}

	public void setPrepayment(boolean prepayment) {
		this.prepayment = prepayment;
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

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
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

	public Long getEntitlementSourceId() {
		return entitlementSourceId;
	}

	public void setEntitlementSourceId(Long entitlementSourceId) {
		this.entitlementSourceId = entitlementSourceId;
	}

	public BigDecimal getPlafond() {
		return plafond;
	}

	public void setPlafond(BigDecimal plafond) {
		this.plafond = plafond;
	}

	public BigDecimal getAdjustedPlafond() {
		return adjustedPlafond;
	}

	public void setAdjustedPlafond(BigDecimal adjustedPlafond) {
		this.adjustedPlafond = adjustedPlafond;
	}

	public String getAdjustmentReason() {
		return adjustmentReason;
	}

	public void setAdjustmentReason(String adjustmentReason) {
		this.adjustmentReason = adjustmentReason;
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

	@Override
	public Object getId() {
		return this.getEntitlementId();
	}

}
