package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;

public class ContactDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String typeCode;
	private String type;
	private String information;
	private Date startDate;
	private Date endDate;
	private Long communicationId;
	
	public ContactDTO() { }
	
	public ContactDTO(String type, String information,
			Date startDate, Date endDate, Long communicationId) {
		super();
		this.type = type;
		this.information = information;
		this.startDate = startDate;
		this.endDate = endDate;
		this.communicationId = communicationId;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "OrganizationContactDto [type=" + type + ", information="
				+ information + "]";
	}

	public Long getCommunicationId() {
		return communicationId;
	}

	public void setCommunicationId(Long communicationId) {
		this.communicationId = communicationId;
	}
	
}
