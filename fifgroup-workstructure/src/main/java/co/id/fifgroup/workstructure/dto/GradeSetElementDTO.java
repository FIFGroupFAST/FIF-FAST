package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;

import co.id.fifgroup.workstructure.domain.GradeSetElement;

public class GradeSetElementDTO extends GradeSetElement implements Serializable, Cloneable{

	private static final long serialVersionUID = 1L;
	
	private String gradeName;
	private String grade;
	private String subGrade;
	private String gradeCode;

	public String getGradeName() {
		if(gradeName != null) {
			return gradeName;
		} else if(grade != null && subGrade != null) {
			return grade.concat("-").concat(subGrade);
		}
		return null;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubGrade() {
		return subGrade;
	}

	public void setSubGrade(String subGrade) {
		this.subGrade = subGrade;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	@Override
	public String toString() {
		return "GradeSetElementDTO [gradeName=" + getGradeName() + ", grade="
				+ grade + ", subGrade=" + subGrade + ", gradeCode=" + gradeCode
				+ ", getId()=" + getId() + ", getGradeSequence()="
				+ getGradeSequence() + ", getGradeId()=" + getGradeId()
				+ ", getConcordanceLevel()=" + getConcordanceLevel() + "]";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
}
