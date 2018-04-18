package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class OcopRetiredIterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String employeeNumber;
	private String itemTypeCode;
	private Long rentalTypeId;
	private String poNumber;
	private String lastApproverNumber;
	private String remark;
	
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	public Long getRentalTypeId() {
		return rentalTypeId;
	}
	public void setRentalTypeId(Long rentalTypeId) {
		this.rentalTypeId = rentalTypeId;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getLastApproverNumber() {
		return lastApproverNumber;
	}
	public void setLastApproverNumber(String lastApproverNumber) {
		this.lastApproverNumber = lastApproverNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
