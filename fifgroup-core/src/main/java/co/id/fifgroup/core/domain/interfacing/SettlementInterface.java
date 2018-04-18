package co.id.fifgroup.core.domain.interfacing;

import java.math.BigDecimal;
import java.util.List;

public class SettlementInterface extends InvoiceInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String claimNumber;
	private BigDecimal prepaymentAmount;
	private BigDecimal settlementAmount;
	private String prepaymentInvoiceNumber;
	private String settlementInvoiceNumber;
	private String rvNumber;
	private String branchDestinationOld;
	private String companyGlCodeOld;
	private String costCenterOld;
	private List<InvoiceDetail> invoiceDetails;
	
	
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public BigDecimal getPrepaymentAmount() {
		return prepaymentAmount;
	}
	public void setPrepaymentAmount(BigDecimal prepaymentAmount) {
		this.prepaymentAmount = prepaymentAmount;
	}
	public String getPrepaymentInvoiceNumber() {
		return prepaymentInvoiceNumber;
	}
	public void setPrepaymentInvoiceNumber(String prepaymentInvoiceNumber) {
		this.prepaymentInvoiceNumber = prepaymentInvoiceNumber;
	}
	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getSettlementInvoiceNumber() {
		return settlementInvoiceNumber;
	}
	public void setSettlementInvoiceNumber(String settlementInvoiceNumber) {
		this.settlementInvoiceNumber = settlementInvoiceNumber;
	}
	public String getRvNumber() {
		return rvNumber;
	}
	public void setRvNumber(String rvNumber) {
		this.rvNumber = rvNumber;
	}
	public List<InvoiceDetail> getInvoiceDetails() {
		return invoiceDetails;
	}
	public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
	public String getCompanyGlCodeOld() {
		return companyGlCodeOld;
	}
	public void setCompanyGlCodeOld(String companyGlCodeOld) {
		this.companyGlCodeOld = companyGlCodeOld;
	}
	public String getBranchDestinationOld() {
		return branchDestinationOld;
	}
	public void setBranchDestinationOld(String branchDestinationOld) {
		this.branchDestinationOld = branchDestinationOld;
	}
	@Override
	public String toString() {
		return "SettlementInterface [claimNumber=" + claimNumber
				+ ", prepaymentAmount=" + prepaymentAmount
				+ ", settlementAmount=" + settlementAmount
				+ ", prepaymentInvoiceNumber=" + prepaymentInvoiceNumber
				+ ", settlementInvoiceNumber=" + settlementInvoiceNumber
				+ ", rvNumber=" + rvNumber + ", branchDestinationOld="
				+ branchDestinationOld + ", companyGlCodeOld="
				+ companyGlCodeOld + ", invoiceDetails=" + invoiceDetails
				+ ", getAmount()=" + getAmount() + ", getAutoPayment()="
				+ getAutoPayment() + ", getBranchCode()=" + getBranchCode()
				+ ", getBranchDestination()=" + getBranchDestination()
				+ ", getClassCode()=" + getClassCode() + ", getCompanyCode()="
				+ getCompanyCode() + ", getCostCenter()=" + getCostCenter()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCurrencyCode()="
				+ getCurrencyCode() + ", getDebetCredit()=" + getDebetCredit()
				+ ", getDescription()=" + getDescription()
				+ ", getDescriptionDetail()=" + getDescriptionDetail()
				+ ", getDivision()=" + getDivision() + ", getDueDate()="
				+ getDueDate() + ", getEmployeeName()=" + getEmployeeName()
				+ ", getEmployeeNumber()=" + getEmployeeNumber()
				+ ", getExchangeRate()=" + getExchangeRate()
				+ ", getFakturPajakDate()=" + getFakturPajakDate()
				+ ", getFakturPajakNumber()=" + getFakturPajakNumber()
				+ ", getIncludePpn()=" + getIncludePpn()
				+ ", getInvoiceDate()=" + getInvoiceDate()
				+ ", getInvoiceNumber()=" + getInvoiceNumber()
				+ ", getInvoiceType()=" + getInvoiceType()
				+ ", getJournalType()=" + getJournalType() + ", getModuleId()="
				+ getModuleId() + ", getPaidByHo()=" + getPaidByHo()
				+ ", getPaymentBankAccount()=" + getPaymentBankAccount()
				+ ", getPaymentBankCode()=" + getPaymentBankCode()
				+ ", getPaymentViaBank()=" + getPaymentViaBank()
				+ ", getPersonId()=" + getPersonId() + ", getPpnTaxCode()="
				+ getPpnTaxCode() + ", getPpnType()=" + getPpnType()
				+ ", getPriority()=" + getPriority() + ", getProcName()="
				+ getProcName() + ", getProductCode()=" + getProductCode()
				+ ", getReferenceNumber()=" + getReferenceNumber()
				+ ", getScheduleDate()=" + getScheduleDate()
				+ ", getSegmentBank()=" + getSegmentBank()
				+ ", getSegmentFuture()=" + getSegmentFuture()
				+ ", getSegmentIb()=" + getSegmentIb()
				+ ", getSequenceNumber()=" + getSequenceNumber()
				+ ", getSupplierAccountName()=" + getSupplierAccountName()
				+ ", getSupplierAccountNumber()=" + getSupplierAccountNumber()
				+ ", getSupplierBankCode()=" + getSupplierBankCode()
				+ ", getSupplierCode()=" + getSupplierCode()
				+ ", getTaxAmount()=" + getTaxAmount() + ", getTaxCode()="
				+ getTaxCode() + ", getTransactionType()="
				+ getTransactionType() + ", getUsedPpn()=" + getUsedPpn()
				+ ", getSupplierType()=" + getSupplierType()
				+ ", getSupplierSubType()=" + getSupplierSubType()
				+ ", getReturnValue()=" + getReturnValue()
				+ ", getCompanyGlCode()=" + getCompanyGlCode()
				+ ", getSpsPrepayment()=" + getSpsPrepayment()
				+ ", getForceApprover()=" + getForceApprover()
				+ ", getActivityCode()=" + getActivityCode() + "]";
	}
	public String getCostCenterOld() {
		return costCenterOld;
	}
	public void setCostCenterOld(String costCenterOld) {
		this.costCenterOld = costCenterOld;
	}
	
}
