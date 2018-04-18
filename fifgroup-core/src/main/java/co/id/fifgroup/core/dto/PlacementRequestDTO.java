package co.id.fifgroup.core.dto;

import java.util.Date;


public class PlacementRequestDTO {
	private String contractNumber;
	private String houseType;
	private String houseBranchName;
	private String houseLocation;
	private String houseAddress;
	private Date placementDate;
	private Date closedDate;
	private String placementStatus;
	
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getHouseBranch() {
		return houseBranchName;
	}
	public void setHouseBranch(String houseBranch) {
		this.houseBranchName = houseBranch;
	}
	public String getHouseLocation() {
		return houseLocation;
	}
	public void setHouseLocation(String houseLocation) {
		this.houseLocation = houseLocation;
	}
	public String getHouseAddress() {
		return houseAddress;
	}
	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	public Date getPlacementDate() {
		return placementDate;
	}
	public void setPlacementDate(Date placementDate) {
		this.placementDate = placementDate;
	}
	public Date getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	public String getHouseBranchName() {
		return houseBranchName;
	}
	public void setHouseBranchName(String houseBranchName) {
		this.houseBranchName = houseBranchName;
	}
	public String getPlacementStatus() {
		return placementStatus;
	}
	public void setPlacementStatus(String placementStatus) {
		this.placementStatus = placementStatus;
	}
	
}
