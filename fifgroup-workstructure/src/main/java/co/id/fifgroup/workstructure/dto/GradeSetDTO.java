package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.workstructure.domain.GradeSet;

public class GradeSetDTO extends GradeSet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<GradeSetElementDTO> gradeSetElements;
	private Date effectiveOnDate;
	private String userName;
	private Boolean isFuture = false;

	public List<GradeSetElementDTO> getGradeSetElements() {
		return gradeSetElements;
	}

	public void setGradeSetElements(List<GradeSetElementDTO> gradeSetElements) {
		this.gradeSetElements = gradeSetElements;
	}

	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}

	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsFuture() {
		return isFuture;
	}

	public void setIsFuture(Boolean isFuture) {
		this.isFuture = isFuture;
	}

	@Override
	public String toString() {
		return "GradeSetDTO [gradeSetElements=" + gradeSetElements
				+ ", getId()=" + getId() + ", getGradeSetName()="
				+ getGradeSetName() + ", getStartDate()=" + getStartDate()
				+ ", getEndDate()=" + getEndDate() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((gradeSetElements == null) ? 0 : gradeSetElements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GradeSetDTO other = (GradeSetDTO) obj;
		if (gradeSetElements == null) {
			if (other.gradeSetElements != null)
				return false;
		} else if (!gradeSetElements.equals(other.gradeSetElements))
			return false;
		return true;
	}
}
