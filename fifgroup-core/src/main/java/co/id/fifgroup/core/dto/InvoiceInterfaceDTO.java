package co.id.fifgroup.core.dto;

import java.io.Serializable;

import co.id.fifgroup.core.domain.interfacing.InvoiceInterface;

public class InvoiceInterfaceDTO extends InvoiceInterface implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String supplierName;
	private String invoiceDateMmYyyy;
	private String feeCode;
	private Long branchId;
	private Long companyId;
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getInvoiceDateMmYyyy() {
		return invoiceDateMmYyyy;
	}
	public void setInvoiceDateMmYyyy(String invoiceDateMmYyyy) {
		this.invoiceDateMmYyyy = invoiceDateMmYyyy;
	}
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
}
