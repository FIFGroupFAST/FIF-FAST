package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DepositInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String depositNumber;
	private int sequence;
	private String contractNumber;
	private String customerId;
	private String refferenceNumber;
	private String currency;
	private BigDecimal amountCredit;
	private BigDecimal amountDebet;
	private String segment1;
	private String segment2;
	private String segment3;
	private String segment4;
	private String segment5;
	private String segment6;
	private String segment7;
	private String segment8;
	private String segment9;
	private String segment10;
	private String companyCode;
	private String status;
	private String sourceCode;
	private String description;
	private int lastHistSeq;
	private String createdBy;
	private Date createdTimestamp;
	private String lastUpdateBy;
	private Date lastUpdateTimestamp;
	private int sequenceHistory;
	
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
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getRefferenceNumber() {
		return refferenceNumber;
	}
	public void setRefferenceNumber(String refferenceNumber) {
		this.refferenceNumber = refferenceNumber;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
	public String getSegment1() {
		return segment1;
	}
	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}
	public String getSegment2() {
		return segment2;
	}
	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}
	public String getSegment3() {
		return segment3;
	}
	public void setSegment3(String segment3) {
		this.segment3 = segment3;
	}
	public String getSegment4() {
		return segment4;
	}
	public void setSegment4(String segment4) {
		this.segment4 = segment4;
	}
	public String getSegment5() {
		return segment5;
	}
	public void setSegment5(String segment5) {
		this.segment5 = segment5;
	}
	public String getSegment6() {
		return segment6;
	}
	public void setSegment6(String segment6) {
		this.segment6 = segment6;
	}
	public String getSegment7() {
		return segment7;
	}
	public void setSegment7(String segment7) {
		this.segment7 = segment7;
	}
	public String getSegment8() {
		return segment8;
	}
	public void setSegment8(String segment8) {
		this.segment8 = segment8;
	}
	public String getSegment9() {
		return segment9;
	}
	public void setSegment9(String segment9) {
		this.segment9 = segment9;
	}
	public String getSegment10() {
		return segment10;
	}
	public void setSegment10(String segment10) {
		this.segment10 = segment10;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getLastHistSeq() {
		return lastHistSeq;
	}
	public void setLastHistSeq(int lastHistSeq) {
		this.lastHistSeq = lastHistSeq;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}
	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public int getSequenceHistory() {
		return sequenceHistory;
	}
	public void setSequenceHistory(int sequenceHistory) {
		this.sequenceHistory = sequenceHistory;
	}
	
	

}
