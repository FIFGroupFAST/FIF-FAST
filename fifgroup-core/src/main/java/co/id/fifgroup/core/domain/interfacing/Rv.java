package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class Rv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String branchCode;
	private String createdBy;
	private String returnValue;
	private Prepayment prepayment;
	private String rvNumber;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getRvNumber() {
		return rvNumber;
	}

	public void setRvNumber(String rvNumber) {
		this.rvNumber = rvNumber;
	}

	public Prepayment getPrepayment() {
		return prepayment;
	}

	public void setPrepayment(Prepayment prepayment) {
		this.prepayment = prepayment;
	}

}
