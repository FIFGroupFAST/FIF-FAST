package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;

import co.id.fifgroup.workstructure.domain.JobValidGrade;

public class JobValidGradeDTO extends JobValidGrade implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long index;
	private Long gradeSetId;
	private String gradeName;
	private String grade;
	private String subGrade;
	
	private String gradeCode;

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Long getGradeSetId() {
		return gradeSetId;
	}

	public void setGradeSetId(Long gradeSetId) {
		this.gradeSetId = gradeSetId;
	}

	public String getGradeName() {
		if (gradeName != null) {
			return gradeName;
		} else if (grade != null && subGrade != null) {
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

	
	@Override
	public String toString() {
		return "JobValidGradeDto [gradeId=" + getGradeId() + ", gradeName="
				+ gradeName + "]";
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

}
