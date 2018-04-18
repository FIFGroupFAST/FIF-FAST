package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class SupplierOpexInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String opexCode;
	private String opexBankCode;
	private String opexAccountNumber;
	private String opexAccountName;
	private String opexBankName;
	
	
	public String getOpexCode() {
		return opexCode;
	}
	public void setOpexCode(String opexCode) {
		this.opexCode = opexCode;
	}
	public String getOpexBankCode() {
		return opexBankCode;
	}
	public void setOpexBankCode(String opexBankCode) {
		this.opexBankCode = opexBankCode;
	}
	public String getOpexAccountNumber() {
		return opexAccountNumber;
	}
	public void setOpexAccountNumber(String opexAccountNumber) {
		this.opexAccountNumber = opexAccountNumber;
	}
	public String getOpexAccountName() {
		return opexAccountName;
	}
	public void setOpexAccountName(String opexAccountName) {
		this.opexAccountName = opexAccountName;
	}
	public String getOpexBankName() {
		return opexBankName;
	}
	public void setOpexBankName(String opexBankName) {
		this.opexBankName = opexBankName;
	}
	
	

}
