package co.id.fifgroup.personneladmin.dto;

import java.util.Date;

import co.id.fifgroup.core.domain.UploadStage;

public class WorkEquipmentStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;

	private String employeeNumber;
	private Long equipmentType;
	private Date dateAssigned;
	private Date returnDate;
	private String assetNumber;
	private String reason;

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Long getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(Long equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Date getDateAssigned() {
		return dateAssigned;
	}

	public void setDateAssigned(Date dateAssigned) {
		this.dateAssigned = dateAssigned;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
