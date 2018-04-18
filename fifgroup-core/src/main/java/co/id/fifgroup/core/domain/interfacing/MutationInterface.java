package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MutationInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long personId;
	private Date transferDate;
	private String locationIdOldCode;
	private String locationIdNewCode;
	private String companyCodeOld;
	private String companyCodeNew;
	private String transactionType;
	private BigDecimal prepaymentAmount;
	private String claimId;
	private String claimNumber;
	private String classCode;
	private String employeeNumber;
	private String employeeName;
	private Long userId;
	private String categoryName;
	private String categoryNameNew;
	private String currencyCode;
	private String costCenterNew;
	private String costCenterOld;
	private String supplierBankCode;
	private String productCode;
	private String branchDestination;
	private String segmentFuture;
	private Long companyId;
	private String invoiceNumber;
	private String segmentIb;
	
	/* BEGIN - added by MRK: 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer */
	private String glCodeOld;
	private String glCodeNew;
	public String getGlCodeOld() {
		return glCodeOld;
	}
	public void setGlCodeOld(String glCodeOld) {
		this.glCodeOld = glCodeOld;
	}
	public String getGlCodeNew() {
		return glCodeNew;
	}
	public void setGlCodeNew(String glCodeNew) {
		this.glCodeNew = glCodeNew;
	}
	/* END - added by MRK: 14091116521937 - [Bug Fixing] Perbaikan Journal mutation Promotion Transfer */
	
	
	public String getSupplierBankCode() {
		return supplierBankCode;
	}
	public void setSupplierBankCode(String supplierBankCode) {
		this.supplierBankCode = supplierBankCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getBranchDestination() {
		return branchDestination;
	}
	public void setBranchDestination(String branchDestination) {
		this.branchDestination = branchDestination;
	}
	public String getSegmentFuture() {
		return segmentFuture;
	}
	public void setSegmentFuture(String segmentFuture) {
		this.segmentFuture = segmentFuture;
	}
	public String getCostCenterNew() {
		return costCenterNew;
	}
	public void setCostCenterNew(String costCenterNew) {
		this.costCenterNew = costCenterNew;
	}
	public String getCostCenterOld() {
		return costCenterOld;
	}
	public void setCostCenterOld(String costCenterOld) {
		this.costCenterOld = costCenterOld;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryNameNew() {
		return categoryNameNew;
	}
	public void setCategoryNameNew(String categoryNameNew) {
		this.categoryNameNew = categoryNameNew;
	}
	public String getLocationIdOldCode() {
		return locationIdOldCode;
	}
	public void setLocationIdOldCode(String locationIdOldCode) {
		this.locationIdOldCode = locationIdOldCode;
	}
	public String getLocationIdNewCode() {
		return locationIdNewCode;
	}
	public void setLocationIdNewCode(String locationIdNewCode) {
		this.locationIdNewCode = locationIdNewCode;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Date getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	public String getCompanyCodeOld() {
		return companyCodeOld;
	}
	public void setCompanyCodeOld(String companyCodeOld) {
		this.companyCodeOld = companyCodeOld;
	}
	public String getCompanyCodeNew() {
		return companyCodeNew;
	}
	public void setCompanyCodeNew(String companyCodeNew) {
		this.companyCodeNew = companyCodeNew;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public BigDecimal getPrepaymentAmount() {
		return prepaymentAmount;
	}
	public void setPrepaymentAmount(BigDecimal prepaymentAmount) {
		this.prepaymentAmount = prepaymentAmount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getSegmentIb() {
		return segmentIb;
	}
	public void setSegmentIb(String segmentIb) {
		this.segmentIb = segmentIb;
	}
	
	

}
