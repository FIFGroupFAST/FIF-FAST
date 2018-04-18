package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DepositHistoryInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String depositNumber;
	private int sequence;
	private int sequenceHistory;
	private BigDecimal amountCredit;
	private BigDecimal amountDebet;
	private BigDecimal openingBalance;
	private BigDecimal closingBalance;
	private String createdBy;
	private Date createdTimestamp;
	private String description;
	private String lastUpdateBy;
	private Date lastUpdateDate;
	private String refferenceNumber;
	
	public String getDepositNumber() {
		return depositNumber;
	}
	public void setDepositNumber(String depositNumber) {
		this.depositNumber = depositNumber;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getSequenceHistory() {
		return sequenceHistory;
	}
	public void setSequenceHistory(int sequenceHistory) {
		this.sequenceHistory = sequenceHistory;
	}
	public BigDecimal getAmountCredit() {
		return amountCredit;
	}
	public void setAmountCredit(BigDecimal amountCredit) {
		this.amountCredit = amountCredit;
	}
	public BigDecimal getAmountDebet() {
		return amountDebet;
	}
	public void setAmountDebet(BigDecimal amountDebet) {
		this.amountDebet = amountDebet;
	}
	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}
	public BigDecimal getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getRefferenceNumber() {
		return refferenceNumber;
	}
	public void setRefferenceNumber(String refferenceNumber) {
		this.refferenceNumber = refferenceNumber;
	}
	
	
	

}
