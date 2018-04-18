package co.id.fifgroup.personneladmin.domain;

import java.io.Serializable;

import co.id.fifgroup.personneladmin.domain.PrimaryAssignment;

/**
 * 14040808522990_CR HCMS – Personal Admin_RAH
 * 
 */
public class AssignmentTrackHistory extends PrimaryAssignment implements
		Serializable {

	private static final long serialVersionUID = 6741076430701967259L;

	private String companyName;
	private String dateTrackChange;
	private String grade;
	private String isEffective;
	private Long jobGroupCode;
	private String subGrade;

	public String getCompanyName() {
		return companyName;
	}

	public String getDateTrackChange() {
		return dateTrackChange;
	}

	public String getGrade() {
		return grade;
	}

	public String getIsEffective() {
		return isEffective;
	}

	public Long getJobGroupCode() {
		return jobGroupCode;
	}

	public String getSubGrade() {
		return subGrade;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setDateTrackChange(String dateTrackChange) {
		this.dateTrackChange = dateTrackChange;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setIsEffective(String isEffective) {
		this.isEffective = isEffective;
	}

	public void setJobGroupCode(Long jobGroupCode) {
		this.jobGroupCode = jobGroupCode;
	}

	public void setSubGrade(String subGrade) {
		this.subGrade = subGrade;
	}
}
