package co.id.fifgroup.core.domain.interfacing;

public class PrepaymentInterface extends InvoiceInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String claimNumber;

	public String getClaimNumber() {
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	@Override
	public String toString() {
		return "PrepaymentInterface [claimNumber=" + claimNumber
				+ ", getClaimNumber()=" + getClaimNumber() + ", getAmount()="
				+ getAmount() + ", getAutoPayment()=" + getAutoPayment()
				+ ", getBranchCode()=" + getBranchCode()
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
	
}
