package co.id.fifgroup.core.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class SecurityFilter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long personId;
	private List<Long> inOrganizations = new ArrayList<>();
	private List<Long> notInOrganizations = new ArrayList<>();
	private List<Long> gradeExclusions = new ArrayList<>();
	private List<Long> authorizedOrganizations = new ArrayList<>();
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public List<Long> getInOrganizations() {
		return inOrganizations == null ? new ArrayList<Long>() : Collections.unmodifiableList(inOrganizations);
	}
	public void setInOrganizations(List<Long> inOrganizations) {
		this.inOrganizations = inOrganizations;
	}
	public List<Long> getNotInOrganizations() {
		return notInOrganizations;
	}
	public void setNotInOrganizations(List<Long> notInOrganizations) {
		this.notInOrganizations = notInOrganizations;
	}
	public List<Long> getGradeExclusions() {
		return gradeExclusions;
	}
	public void setGradeExclusions(List<Long> gradeExclusions) {
		this.gradeExclusions = gradeExclusions;
	}
	@Override
	public String toString() {
		return "SecurityFilter [personId=" + personId + ", inOrganizations="
				+ inOrganizations + ", notInOrganizations="
				+ notInOrganizations + ", gradeExclusions=" + gradeExclusions
				+ "]";
	}
	public List<Long> getAuthorizedOrganizations() {
		return authorizedOrganizations;
	}
	public void setAuthorizedOrganizations(List<Long> authorizedOrganizations) {
		this.authorizedOrganizations = authorizedOrganizations;
	}	
}
