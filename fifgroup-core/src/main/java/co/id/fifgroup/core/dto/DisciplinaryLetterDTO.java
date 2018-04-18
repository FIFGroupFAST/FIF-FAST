package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;

public class DisciplinaryLetterDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String branchName;
	private String organizationName;
	private String jobName;
	private String spNumber;
	private String disciplinaryType;
	private String reasonName;
	private String disciplinaryLetterLevel;
	private String levelSource;
	private Date startDate;
	private Date endDate;
	private String cronologies;
	private Integer target;
	private Integer achievement;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSpNumber() {
		return spNumber;
	}

	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}

	public String getDisciplinaryType() {
		return disciplinaryType;
	}

	public void setDisciplinaryType(String disciplinaryType) {
		this.disciplinaryType = disciplinaryType;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getDisciplinaryLetterLevel() {
		return disciplinaryLetterLevel;
	}

	public void setDisciplinaryLetterLevel(String disciplinaryLetterLevel) {
		this.disciplinaryLetterLevel = disciplinaryLetterLevel;
	}

	public String getLevelSource() {
		return levelSource;
	}

	public void setLevelSource(String levelSource) {
		this.levelSource = levelSource;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCronologies() {
		return cronologies;
	}

	public void setCronologies(String cronologies) {
		this.cronologies = cronologies;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public Integer getAchievement() {
		return achievement;
	}

	public void setAchievement(Integer achievement) {
		this.achievement = achievement;
	}

}
