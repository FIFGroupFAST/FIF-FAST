package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvoiceIndividualHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String invoiceNumber;
	private Date invoiceDate;
	private int splitSequence;
	private String transType;
	private String supplierCode;
	private String supplierName;
	private String supplierBankAcc;
	private String supplierBankAccName;
	private BigDecimal totalAmount;
	private BigDecimal amountDpp;
	private BigDecimal amountKumulatif;
	private BigDecimal splitAmount;
	private int pctRate;
	private BigDecimal pphAmt;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public int getSplitSequence() {
		return splitSequence;
	}
	public void setSplitSequence(int splitSequence) {
		this.splitSequence = splitSequence;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierBankAcc() {
		return supplierBankAcc;
	}
	public void setSupplierBankAcc(String supplierBankAcc) {
		this.supplierBankAcc = supplierBankAcc;
	}
	public String getSupplierBankAccName() {
		return supplierBankAccName;
	}
	public void setSupplierBankAccName(String supplierBankAccName) {
		this.supplierBankAccName = supplierBankAccName;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getAmountDpp() {
		return amountDpp;
	}
	public void setAmountDpp(BigDecimal amountDpp) {
		this.amountDpp = amountDpp;
	}
	public BigDecimal getAmountKumulatif() {
		return amountKumulatif;
	}
	public void setAmountKumulatif(BigDecimal amountKumulatif) {
		this.amountKumulatif = amountKumulatif;
	}
	public BigDecimal getSplitAmount() {
		return splitAmount;
	}
	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}
	public int getPctRate() {
		return pctRate;
	}
	public void setPctRate(int pctRate) {
		this.pctRate = pctRate;
	}
	public BigDecimal getPphAmt() {
		return pphAmt;
	}
	public void setPphAmt(BigDecimal pphAmt) {
		this.pphAmt = pphAmt;
	}
}
