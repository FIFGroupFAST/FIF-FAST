package co.id.fifgroup.workstructure.dto;

import java.util.Date;

import co.id.fifgroup.workstructure.domain.OrganizationContact;

public class OrganizationContactDTO extends OrganizationContact {

	private static final long serialVersionUID = 1L;
	
	private String type;
	
	public OrganizationContactDTO() { }
	
	public OrganizationContactDTO(String type, String information,
			Date startDate, Date endDate) {
		super();
		this.type = type;
		this.setInformation(information);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "OrganizationContactDto [type=" + type + ", information="
				+ getInformation() + "]";
	}
	
	
	
	
}
