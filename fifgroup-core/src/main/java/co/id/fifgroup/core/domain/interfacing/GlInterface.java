package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GlInterface implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private Long setOfBookId;
	private Date accountingDate;
	private Date transactionDate;
	private String periodName;
	private String currencyCode;
	private Date dateCreated;
	private String createdBy;
	private String actualFlag;
	private String userJeCategoryName;
	private String userJeSourceName;
	private String segment1;
	private String segment2;
	private String segment3;
	private String segment4;
	private String segment5;
	private String segment6;
	private String segment7;
	private String segment8;
	private BigDecimal enteredDr;
	private BigDecimal enteredCr;
	private BigDecimal accountedDr;
	private BigDecimal accountedCr;
	private String reference1;
	private String reference4;
	private String reference10;
	private String attribute4;
	private String attribute5;
	
	private String enteredDrStr;
	private String enteredCrStr;
	private String accountedDrStr;
	private String accountedCrStr;
	
	
	
	public String getEnteredDrStr() {
		return enteredDrStr;
	}
	public void setEnteredDrStr(String enteredDrStr) {
		this.enteredDrStr = enteredDrStr;
	}
	public String getEnteredCrStr() {
		return enteredCrStr;
	}
	public void setEnteredCrStr(String enteredCrStr) {
		this.enteredCrStr = enteredCrStr;
	}
	public String getAccountedDrStr() {
		return accountedDrStr;
	}
	public void setAccountedDrStr(String accountedDrStr) {
		this.accountedDrStr = accountedDrStr;
	}
	public String getAccountedCrStr() {
		return accountedCrStr;
	}
	public void setAccountedCrStr(String accountedCrStr) {
		this.accountedCrStr = accountedCrStr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getSetOfBookId() {
		return setOfBookId;
	}
	public void setSetOfBookId(Long setOfBookId) {
		this.setOfBookId = setOfBookId;
	}
	public Date getAccountingDate() {
		return accountingDate;
	}
	public void setAccountingDate(Date accountingDate) {
		this.accountingDate = accountingDate;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getActualFlag() {
		return actualFlag;
	}
	public void setActualFlag(String actualFlag) {
		this.actualFlag = actualFlag;
	}
	public String getUserJeCategoryName() {
		return userJeCategoryName;
	}
	public void setUserJeCategoryName(String userJeCategoryName) {
		this.userJeCategoryName = userJeCategoryName;
	}
	public String getUserJeSourceName() {
		return userJeSourceName;
	}
	public void setUserJeSourceName(String userJeSourceName) {
		this.userJeSourceName = userJeSourceName;
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
	public BigDecimal getEnteredDr() {
		return enteredDr;
	}
	public void setEnteredDr(BigDecimal enteredDr) {
		this.enteredDr = enteredDr;
	}
	public BigDecimal getEnteredCr() {
		return enteredCr;
	}
	public void setEnteredCr(BigDecimal enteredCr) {
		this.enteredCr = enteredCr;
	}
	public BigDecimal getAccountedDr() {
		return accountedDr;
	}
	public void setAccountedDr(BigDecimal accountedDr) {
		this.accountedDr = accountedDr;
	}
	public BigDecimal getAccountedCr() {
		return accountedCr;
	}
	public void setAccountedCr(BigDecimal accountedCr) {
		this.accountedCr = accountedCr;
	}
	public String getReference1() {
		return reference1;
	}
	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}
	public String getReference4() {
		return reference4;
	}
	public void setReference4(String reference4) {
		this.reference4 = reference4;
	}
	public String getReference10() {
		return reference10;
	}
	public void setReference10(String reference10) {
		this.reference10 = reference10;
	}
	public String getAttribute4() {
		return attribute4;
	}
	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}
	public String getAttribute5() {
		return attribute5;
	}
	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	

}
