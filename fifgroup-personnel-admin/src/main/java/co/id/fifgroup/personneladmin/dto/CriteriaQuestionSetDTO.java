package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;

public class CriteriaQuestionSetDTO implements Serializable {

	private static final long serialVersionUID = 543313942939593826L;

	private Long jobId;
	private String jobForCode;
	private String jobTypeCode;
	private String peopleCategoryCode;
	private Boolean keyJob;
	private Long companyId;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobForCode() {
		return jobForCode;
	}

	public void setJobForCode(String jobForCode) {
		this.jobForCode = jobForCode;
	}

	public String getJobTypeCode() {
		return jobTypeCode;
	}

	public void setJobTypeCode(String jobTypeCode) {
		this.jobTypeCode = jobTypeCode;
	}

	public String getPeopleCategoryCode() {
		return peopleCategoryCode;
	}

	public void setPeopleCategoryCode(String peopleCategoryCode) {
		this.peopleCategoryCode = peopleCategoryCode;
	}

	public Boolean getKeyJob() {
		return keyJob;
	}

	public void setKeyJob(Boolean keyJob) {
		this.keyJob = keyJob;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

}
