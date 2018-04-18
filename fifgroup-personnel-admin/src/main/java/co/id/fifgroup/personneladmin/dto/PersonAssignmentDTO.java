package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.personneladmin.domain.PersonalInformation;

public class PersonAssignmentDTO extends PersonalInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID personUUID;
	private String peopleType;
	private PrimaryAssignmentDTO primaryAssignmentDTO;
	private List<String> roles;

	public PrimaryAssignmentDTO getPrimaryAssignmentDTO() {
		return primaryAssignmentDTO;
	}

	public void setPrimaryAssignmentDTO(PrimaryAssignmentDTO primaryAssignmentDTO) {
		this.primaryAssignmentDTO = primaryAssignmentDTO;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public UUID getPersonUUID() {
		return personUUID;
	}

	public void setPersonUUID(UUID personUUID) {
		this.personUUID = personUUID;
	}

	public String getPeopleType() {
		return peopleType;
	}

	public void setPeopleType(String peopleType) {
		this.peopleType = peopleType;
	}
}
