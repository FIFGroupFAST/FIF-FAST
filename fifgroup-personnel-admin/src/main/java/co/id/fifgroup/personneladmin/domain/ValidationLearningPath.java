package co.id.fifgroup.personneladmin.domain;

import java.io.Serializable;

public class ValidationLearningPath implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String returnValue;
	private Long personId;
	private Long jobId;

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

}
