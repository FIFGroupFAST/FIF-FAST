package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class GlPersonInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long personId;
	private Long companyCodeGl;
	private Long locationId;
	private String locationCode;
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getCompanyCodeGl() {
		return companyCodeGl;
	}
	public void setCompanyCodeGl(Long companyCodeGl) {
		this.companyCodeGl = companyCodeGl;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	

}
