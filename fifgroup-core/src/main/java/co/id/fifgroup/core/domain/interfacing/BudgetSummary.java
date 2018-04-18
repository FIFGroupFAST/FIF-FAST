package co.id.fifgroup.core.domain.interfacing;

import java.math.BigDecimal;
import java.util.Date;

public class BudgetSummary extends Budget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal budgetAmount;
	private BigDecimal reservedAmount;
	private BigDecimal usedAmount;
	private BigDecimal releasedAmount;
	private BigDecimal correctionAmmount;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	public BigDecimal getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(BigDecimal budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public BigDecimal getReservedAmount() {
		return reservedAmount;
	}

	public void setReservedAmount(BigDecimal reservedAmount) {
		this.reservedAmount = reservedAmount;
	}

	public BigDecimal getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}

	public BigDecimal getReleasedAmount() {
		return releasedAmount;
	}

	public void setReleasedAmount(BigDecimal releasedAmount) {
		this.releasedAmount = releasedAmount;
	}

	public BigDecimal getCorrectionAmmount() {
		return correctionAmmount;
	}

	public void setCorrectionAmmount(BigDecimal correctionAmmount) {
		this.correctionAmmount = correctionAmmount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
