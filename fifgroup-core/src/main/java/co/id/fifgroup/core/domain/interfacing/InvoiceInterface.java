package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvoiceInterface implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal amount;
	private String autoPayment;
	private String branchCode;
	private String branchDestination;
	private String classCode;
	private String companyCode;
	private String costCenter;
	private String createdBy;
	private String currencyCode;
	private String debetCredit;
	private String description;
	private String descriptionDetail;
	private String division;
	private Date dueDate;
	private String employeeName;
	private String employeeNumber;
	private Long exchangeRate;
	private Date fakturPajakDate;
	private String fakturPajakNumber;
	private String includePpn;
	private Date invoiceDate;
	private String invoiceNumber;
	private String invoiceType;
	private String journalType;
	private Long moduleId;
	private String paidByHo;
	private String paymentBankAccount;
	private String paymentBankCode;
	private String paymentViaBank;
	private Long personId;
	private String ppnTaxCode;
	private String ppnType;
	private int priority;
	private String procName;
	private String productCode;
	private String referenceNumber;
	private Date scheduleDate;
	private String segmentBank;
	private String segmentFuture;
	private String segmentIb;
	private int sequenceNumber;
	private String supplierAccountName;
	private String supplierAccountNumber;
	private String supplierBankCode;
	private String supplierCode;
	private String supplierSubType;
	private String supplierType;
	private BigDecimal taxAmount;
	private String taxCode;
	private String transactionType;
	private String usedPpn;
	private String companyGlCode;
	private String spsPrepayment;
	private String forceApprover;
	private String activityCode;
	
	private String returnValue;

	/*
	 * //Settlement private String oldCompanyCode; private String oldBranchCode;
	 * private Date transferDate; private String prepaymentClaimNumber; private
	 * BigDecimal totalAmount; private BigDecimal prepaymentAmount; private
	 * boolean isCreateRv;
	 * 
	 * //rv private String prepaymentClaimId;
	 */

	public BigDecimal getAmount() {
		return amount;
	}

	public String getAutoPayment() {
		return autoPayment;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getBranchDestination() {
		return branchDestination;
	}

	public String getClassCode() {
		return classCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getDebetCredit() {
		return debetCredit;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionDetail() {
		return descriptionDetail;
	}

	public String getDivision() {
		return division;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public Long getExchangeRate() {
		return exchangeRate;
	}

	public Date getFakturPajakDate() {
		return fakturPajakDate;
	}

	public String getFakturPajakNumber() {
		return fakturPajakNumber;
	}

	public String getIncludePpn() {
		return includePpn;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public String getJournalType() {
		return journalType;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public String getPaidByHo() {
		return paidByHo;
	}

	public String getPaymentBankAccount() {
		return paymentBankAccount;
	}

	public String getPaymentBankCode() {
		return paymentBankCode;
	}

	public String getPaymentViaBank() {
		return paymentViaBank;
	}

	public Long getPersonId() {
		return personId;
	}

	public String getPpnTaxCode() {
		return ppnTaxCode;
	}

	public String getPpnType() {
		return ppnType;
	}

	public int getPriority() {
		return priority;
	}

	public String getProcName() {
		return procName;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public String getSegmentBank() {
		return segmentBank;
	}

	public String getSegmentFuture() {
		return segmentFuture;
	}

	public String getSegmentIb() {
		return segmentIb;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public String getSupplierAccountName() {
		return supplierAccountName;
	}

	public String getSupplierAccountNumber() {
		return supplierAccountNumber;
	}

	public String getSupplierBankCode() {
		return supplierBankCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getUsedPpn() {
		return usedPpn;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setAutoPayment(String autoPayment) {
		this.autoPayment = autoPayment;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setBranchDestination(String branchDestination) {
		this.branchDestination = branchDestination;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setDebetCredit(String debetCredit) {
		this.debetCredit = debetCredit;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescriptionDetail(String descriptionDetail) {
		this.descriptionDetail = descriptionDetail;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public void setExchangeRate(Long exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setFakturPajakDate(Date fakturPajakDate) {
		this.fakturPajakDate = fakturPajakDate;
	}

	public void setFakturPajakNumber(String fakturPajakNumber) {
		this.fakturPajakNumber = fakturPajakNumber;
	}

	public void setIncludePpn(String includePpn) {
		this.includePpn = includePpn;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public void setJournalType(String journalType) {
		this.journalType = journalType;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public void setPaidByHo(String paidByHo) {
		this.paidByHo = paidByHo;
	}

	public void setPaymentBankAccount(String paymentBankAccount) {
		this.paymentBankAccount = paymentBankAccount;
	}

	public void setPaymentBankCode(String paymentBankCode) {
		this.paymentBankCode = paymentBankCode;
	}

	public void setPaymentViaBank(String paymentViaBank) {
		this.paymentViaBank = paymentViaBank;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public void setPpnTaxCode(String ppnTaxCode) {
		this.ppnTaxCode = ppnTaxCode;
	}

	public void setPpnType(String ppnType) {
		this.ppnType = ppnType;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public void setSegmentBank(String segmentBank) {
		this.segmentBank = segmentBank;
	}

	public void setSegmentFuture(String segmentFuture) {
		this.segmentFuture = segmentFuture;
	}

	public void setSegmentIb(String segmentIb) {
		this.segmentIb = segmentIb;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public void setSupplierAccountName(String supplierAccountName) {
		this.supplierAccountName = supplierAccountName;
	}

	public void setSupplierAccountNumber(String supplierAccountNumber) {
		this.supplierAccountNumber = supplierAccountNumber;
	}

	public void setSupplierBankCode(String supplierBankCode) {
		this.supplierBankCode = supplierBankCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setUsedPpn(String usedPpn) {
		this.usedPpn = usedPpn;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getSupplierSubType() {
		return supplierSubType;
	}

	public void setSupplierSubType(String supplierSubType) {
		this.supplierSubType = supplierSubType;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getCompanyGlCode() {
		return companyGlCode;
	}

	public void setCompanyGlCode(String companyGlCode) {
		this.companyGlCode = companyGlCode;
	}

	public String getSpsPrepayment() {
		return spsPrepayment;
	}

	public void setSpsPrepayment(String spsPrepayment) {
		this.spsPrepayment = spsPrepayment;
	}

	public String getForceApprover() {
		return forceApprover;
	}

	public void setForceApprover(String forceApprover) {
		this.forceApprover = forceApprover;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	@Override
	public String toString() {
		return "InvoiceInterface [amount=" + amount + ", autoPayment="
				+ autoPayment + ", branchCode=" + branchCode
				+ ", branchDestination=" + branchDestination + ", classCode="
				+ classCode + ", companyCode=" + companyCode + ", costCenter="
				+ costCenter + ", createdBy=" + createdBy + ", currencyCode="
				+ currencyCode + ", debetCredit=" + debetCredit
				+ ", description=" + description + ", descriptionDetail="
				+ descriptionDetail + ", division=" + division + ", dueDate="
				+ dueDate + ", employeeName=" + employeeName
				+ ", employeeNumber=" + employeeNumber + ", exchangeRate="
				+ exchangeRate + ", fakturPajakDate=" + fakturPajakDate
				+ ", fakturPajakNumber=" + fakturPajakNumber + ", includePpn="
				+ includePpn + ", invoiceDate=" + invoiceDate
				+ ", invoiceNumber=" + invoiceNumber + ", invoiceType="
				+ invoiceType + ", journalType=" + journalType + ", moduleId="
				+ moduleId + ", paidByHo=" + paidByHo + ", paymentBankAccount="
				+ paymentBankAccount + ", paymentBankCode=" + paymentBankCode
				+ ", paymentViaBank=" + paymentViaBank + ", personId="
				+ personId + ", ppnTaxCode=" + ppnTaxCode + ", ppnType="
				+ ppnType + ", priority=" + priority + ", procName=" + procName
				+ ", productCode=" + productCode + ", referenceNumber="
				+ referenceNumber + ", scheduleDate=" + scheduleDate
				+ ", segmentBank=" + segmentBank + ", segmentFuture="
				+ segmentFuture + ", segmentIb=" + segmentIb
				+ ", sequenceNumber=" + sequenceNumber
				+ ", supplierAccountName=" + supplierAccountName
				+ ", supplierAccountNumber=" + supplierAccountNumber
				+ ", supplierBankCode=" + supplierBankCode + ", supplierCode="
				+ supplierCode + ", supplierSubType=" + supplierSubType
				+ ", supplierType=" + supplierType + ", taxAmount=" + taxAmount
				+ ", taxCode=" + taxCode + ", transactionType="
				+ transactionType + ", usedPpn=" + usedPpn + ", companyGlCode="
				+ companyGlCode + ", spsPrepayment=" + spsPrepayment
				+ ", forceApprover=" + forceApprover + ", activityCode="
				+ activityCode + ", returnValue=" + returnValue + "]";
	}

}