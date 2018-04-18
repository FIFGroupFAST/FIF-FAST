package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;

import co.id.fifgroup.workstructure.domain.JobSpecification;

public class JobSpecificationDTO extends JobSpecification implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String qualification;
	private String condition;
	private int index;
	
	public String getQualification() {
		return qualification;
	}
	
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "JobSpecificationDTO [qualification=" + qualification
				+ ", condition=" + condition + "]";
	}
}
