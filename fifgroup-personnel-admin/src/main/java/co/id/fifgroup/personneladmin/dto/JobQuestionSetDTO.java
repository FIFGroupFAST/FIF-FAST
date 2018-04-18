package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.personneladmin.domain.JobQuestionSet;

public class JobQuestionSetDTO extends JobQuestionSet {
	
	private static final long serialVersionUID = 1L;
	private String questionSetName;
	private String jobCode;
	private String jobName;
	private boolean isKeyJob;
	private String userName;
	
	public String getQuestionSetName() {
		return questionSetName;
	}
	public void setQuestionSetName(String questionSetName) {
		this.questionSetName = questionSetName;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public boolean isKeyJob() {
		return isKeyJob;
	}
	public void setKeyJob(boolean isKeyJob) {
		this.isKeyJob = isKeyJob;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
