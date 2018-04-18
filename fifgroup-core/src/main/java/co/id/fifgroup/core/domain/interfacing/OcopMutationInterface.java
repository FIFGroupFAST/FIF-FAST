package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.util.Date;

public class OcopMutationInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String employeeNumber;
	private Long rentalTypeId;
	private String prevLocationCode;
	private String prevLocationName;
	private String locationCode;
	private String locationName;
	private Date processDate;
	private String itemTypeCode;
	private String poNumber;
	private String prevBranchCode;
	private String branchCode;
	private String prevCostCenter;
	private String costCenter;
	private String branchHeadeEmployeeNumber;
	
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public Long getRentalTypeId() {
		return rentalTypeId;
	}
	public void setRentalTypeId(Long rentalTypeId) {
		this.rentalTypeId = rentalTypeId;
	}
	public String getPrevLocationCode() {
		return prevLocationCode;
	}
	public void setPrevLocationCode(String prevLocationCode) {
		this.prevLocationCode = prevLocationCode;
	}
	public String getPrevLocationName() {
		return prevLocationName;
	}
	public void setPrevLocationName(String prevLocationName) {
		this.prevLocationName = prevLocationName;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getPrevBranchCode() {
		return prevBranchCode;
	}
	public void setPrevBranchCode(String prevBranchCode) {
		this.prevBranchCode = prevBranchCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchHeadeEmployeeNumber() {
		return branchHeadeEmployeeNumber;
	}
	public void setBranchHeadeEmployeeNumber(String branchHeadeEmployeeNumber) {
		this.branchHeadeEmployeeNumber = branchHeadeEmployeeNumber;
	}
	public String getPrevCostCenter() {
		return prevCostCenter;
	}
	public void setPrevCostCenter(String prevCostCenter) {
		this.prevCostCenter = prevCostCenter;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	
	
}
