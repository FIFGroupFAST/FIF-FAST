package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RvInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String referenceNumber;
	private int SequenceNumber;
	private String transType;
	private String fromId;
	private String branchId;
	private Date transDate;
	private String documentNumber;
	private String description;
	private String receivedMethod;
	private String receivedBankId;
	private String receivedBankAccount;
	private String currency;
	private int exchangeRate;
	private BigDecimal receivedAmount;
	private BigDecimal fee;
	private String segment1;
	private String segment2;
	private String segment3;
	private String segment4;
	private String segment5;
	private String procedureName;
	private String createdBy;
	private Date createDate;
	private String segment6;
	private String segment7;
	private String segment8;
	private String segment9;
	private String segment10;
	private String bankId;
	private String companyId;
	private String contractNumber;
	private String rvNumber;
	private int roundRcv;
	private String source;
	private String referenceDetail;
	private String returnValue;
	
	
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public int getSequenceNumber() {
		return SequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		SequenceNumber = sequenceNumber;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReceivedMethod() {
		return receivedMethod;
	}
	public void setReceivedMethod(String receivedMethod) {
		this.receivedMethod = receivedMethod;
	}
	public String getReceivedBankId() {
		return receivedBankId;
	}
	public void setReceivedBankId(String receivedBankId) {
		this.receivedBankId = receivedBankId;
	}
	public String getReceivedBankAccount() {
		return receivedBankAccount;
	}
	public void setReceivedBankAccount(String receivedBankAccount) {
		this.receivedBankAccount = receivedBankAccount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(int exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
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
	public String getProcedureName() {
		return procedureName;
	}
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getRvNumber() {
		return rvNumber;
	}
	public void setRvNumber(String rvNumber) {
		this.rvNumber = rvNumber;
	}
	public int getRoundRcv() {
		return roundRcv;
	}
	public void setRoundRcv(int roundRcv) {
		this.roundRcv = roundRcv;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getReferenceDetail() {
		return referenceDetail;
	}
	public void setReferenceDetail(String referenceDetail) {
		this.referenceDetail = referenceDetail;
	}
	public String getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	
	
	

}
