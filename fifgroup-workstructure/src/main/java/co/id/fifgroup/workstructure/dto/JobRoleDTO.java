package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;

import co.id.fifgroup.workstructure.domain.JobRole;

public class JobRoleDTO extends JobRole implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String managementType;
	private int index;
	
	
	public String getManagementType() {
		return managementType;
	}
	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "JobRoleDto [managementTypeCode=" + getManagementTypeCode()
				+ ", managementType=" + managementType + "]";
	}
	
}
