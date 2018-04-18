package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TaxInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String branchCode;
	private String refferenceNo;
	private String invoiceNumber;
	private String invoiceType;
	private String transactionType;
	private Date invoiceDate;
	private String supplierCode;
	private String supplierType;
	private String supplierName;
	private String currency;
	private Integer exchangeRate;
	private Integer sequence;
	private String branchDestination;
	private Date scheduleDueDate;
	private String classCode;
	private String debitCredit;
	private BigDecimal amount;
	private String taxCode;
	private String taxClassCode;
	private String taxAmount;
	private String taxAmountInRup;
	private String status;
	private String companyCode;
	private String description;
	
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getRefferenceNo() {
		return refferenceNo;
	}
	public void setRefferenceNo(String refferenceNo) {
		this.refferenceNo = refferenceNo;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Integer exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getBranchDestination() {
		return branchDestination;
	}
	public void setBranchDestination(String branchDestination) {
		this.branchDestination = branchDestination;
	}
	public Date getScheduleDueDate() {
		return scheduleDueDate;
	}
	public void setScheduleDueDate(Date scheduleDueDate) {
		this.scheduleDueDate = scheduleDueDate;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getDebitCredit() {
		return debitCredit;
	}
	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getTaxClassCode() {
		return taxClassCode;
	}
	public void setTaxClassCode(String taxClassCode) {
		this.taxClassCode = taxClassCode;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getTaxAmountInRup() {
		return taxAmountInRup;
	}
	public void setTaxAmountInRup(String taxAmountInRup) {
		this.taxAmountInRup = taxAmountInRup;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
