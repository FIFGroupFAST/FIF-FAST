package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class PreInvoiceInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String invoiceNumber;
	private String spsPrepayment;
	private String forceApprover;
	private String returnValue;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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
	public String getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	
	

}
