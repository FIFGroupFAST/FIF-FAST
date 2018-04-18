package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class InvoiceNumber implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String invoiceNumber;
	private String branchCode;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

}
