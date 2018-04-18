package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String productCode;
	private BigDecimal amount;
	private String taxCode;
	private BigDecimal taxAmount;
	private String invoiceNumber;
	private String branchDestination;
	private String refferenceNumber;
	private String branchCode;
	
	
	
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getBranchDestination() {
		return branchDestination;
	}
	public void setBranchDestination(String branchDestination) {
		this.branchDestination = branchDestination;
	}
	public String getRefferenceNumber() {
		return refferenceNumber;
	}
	public void setRefferenceNumber(String refferenceNumber) {
		this.refferenceNumber = refferenceNumber;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	@Override
	public String toString() {
		return "InvoiceDetail [productCode=" + productCode + ", amount="
				+ amount + ", invoiceNumber=" + invoiceNumber
				+ ", branchDestination=" + branchDestination
				+ ", refferenceNumber=" + refferenceNumber + ", branchCode="
				+ branchCode + "]";
	}

}
