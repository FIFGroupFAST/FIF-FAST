package co.id.fifgroup.personneladmin.dto;

import java.util.Date;

public class EquipmentAssignmentDTO {

	private Long equipmentId;
	private String typeName;
	private String equipmentSize;
	private Date assignedDate;
	private String assignedDateStr;
	private Long typeId;
	
	
	public Long getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getEquipmentSize() {
		return equipmentSize;
	}
	public void setEquipmentSize(String equipmentSize) {
		this.equipmentSize = equipmentSize;
	}
	public Date getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}
	public String getAssignedDateStr() {
		return assignedDateStr;
	}
	public void setAssignedDateStr(String assignedDateStr) {
		this.assignedDateStr = assignedDateStr;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	
	
}
