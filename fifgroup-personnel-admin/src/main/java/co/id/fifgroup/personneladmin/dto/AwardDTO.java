package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.personneladmin.domain.Award;

public class AwardDTO extends Award {

	private static final long serialVersionUID = 1L;

	private String jobName;
	private String jobCode;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

}
