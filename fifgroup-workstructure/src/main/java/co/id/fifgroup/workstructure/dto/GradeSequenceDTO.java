package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;

import co.id.fifgroup.workstructure.constant.GradeSequence;

public class GradeSequenceDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Boolean valid = true;
	private GradeSequence gradeSequence = GradeSequence.SUB_GRADE;
	private Long validGradeId;
	
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public GradeSequence getGradeSequence() {
		return gradeSequence;
	}
	public void setGradeSequence(GradeSequence gradeSequence) {
		this.gradeSequence = gradeSequence;
	}
	public Long getValidGradeId() {
		return validGradeId;
	}
	public void setValidGradeId(Long validGradeId) {
		this.validGradeId = validGradeId;
	}
	
}
