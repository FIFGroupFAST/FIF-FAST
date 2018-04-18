package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.Date;

public class HavDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long personId;
	private Long peopleHavId;
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	private String meaning;

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getPeopleHavId() {
		return peopleHavId;
	}

	public void setPeopleHavId(Long peopleHavId) {
		this.peopleHavId = peopleHavId;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

}
