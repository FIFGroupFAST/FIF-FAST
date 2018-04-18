package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.util.Date;

public class Approver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String branchCode;
	private String invoiceNumber;
	private int sequenceApproverNumber;
	private String approver;
	private Date approveDate;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public int getSequenceApproverNumber() {
		return sequenceApproverNumber;
	}

	public void setSequenceApproverNumber(int sequenceApproverNumber) {
		this.sequenceApproverNumber = sequenceApproverNumber;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

}
