package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class PrepaymentSettlement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String invoicePrepaymentNumber;
	private int prepaymentSequenceNumber;
	private String invoiceNumber;
	private String createdBy;
	private String createRv;
	private String rvNumber;
	private String outMessage;
	private Integer returnValue;
	
	public String getInvoicePrepaymentNumber() {
		return invoicePrepaymentNumber;
	}
	public void setInvoicePrepaymentNumber(String invoicePrepaymentNumber) {
		this.invoicePrepaymentNumber = invoicePrepaymentNumber;
	}
	public int getPrepaymentSequenceNumber() {
		return prepaymentSequenceNumber;
	}
	public void setPrepaymentSequenceNumber(int prepaymentSequenceNumber) {
		this.prepaymentSequenceNumber = prepaymentSequenceNumber;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreateRv() {
		return createRv;
	}
	public void setCreateRv(String createRv) {
		this.createRv = createRv;
	}
	public String getRvNumber() {
		return rvNumber;
	}
	public void setRvNumber(String rvNumber) {
		this.rvNumber = rvNumber;
	}
	public String getOutMessage() {
		return outMessage;
	}
	public void setOutMessage(String outMessage) {
		this.outMessage = outMessage;
	}
	public Integer getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Integer returnValue) {
		this.returnValue = returnValue;
	}
	
	

}
