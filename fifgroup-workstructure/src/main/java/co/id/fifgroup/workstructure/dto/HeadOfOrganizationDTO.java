package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.util.Date;

public class HeadOfOrganizationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer versionNumber;
	private Date dateFrom;
	private Date dateTo;
	private String levelCode;
	private String levelName;
	private Long jobId;
	private String jobName;
	private Long personId;
	private String employeeNumber;
	private String fullName;
	private String photoFilePath;
	
	public Integer getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhotoFilePath() {
		return photoFilePath;
	}
	public void setPhotoFilePath(String photoFilePath) {
		this.photoFilePath = photoFilePath;
	}
	@Override
	public String toString() {
		return "HeadOfOrganizationDTO [versionNumber=" + versionNumber
				+ ", dateFrom=" + dateFrom + ", dateTo=" + dateTo
				+ ", levelCode=" + levelCode + ", jobId=" + jobId + ", personId="
				+ personId + "]";
	}
	
	
}
