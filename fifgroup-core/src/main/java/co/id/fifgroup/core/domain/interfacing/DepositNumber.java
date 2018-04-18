package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class DepositNumber implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String depositNumber;
	private String branchCode;
	private Long moduleId;
	
	
	
	public DepositNumber(String branchCode, Long moduleId) {
		super();
		this.branchCode = branchCode;
		this.moduleId = moduleId;
	}
	
	
	
	public DepositNumber() {
		super();
	}

	public String getDepositNumber() {
		return depositNumber;
	}
	public void setDepositNumber(String depositNumber) {
		this.depositNumber = depositNumber;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	
	

}
