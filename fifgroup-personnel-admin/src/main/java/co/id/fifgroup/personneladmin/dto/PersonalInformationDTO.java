package co.id.fifgroup.personneladmin.dto;

import java.util.List;

import co.id.fifgroup.basicsetup.common.History;
import co.id.fifgroup.personneladmin.domain.PersonalInformation;

public class PersonalInformationDTO extends PersonalInformation implements History {

	private static final long serialVersionUID = 1L;

	private List<EquipmentAssignmentDTO> assignedList;

	public List<EquipmentAssignmentDTO> getAssignedList() {
		return assignedList;
	}

	public void setAssignedList(List<EquipmentAssignmentDTO> assignedList) {
		this.assignedList = assignedList;
	}


	
}
