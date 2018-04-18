package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.Date;

import co.id.fifgroup.core.util.DateUtil;

public class AssignmentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long assignmentId;
	private Long personId;
	private Long companyId;
	private Date startDate;
	private Date endDate;
	private String assignmentType;
	private String secondaryType;
	private Long organizationId;
	private String organizationName;
	private Long locationId;
	private String locationName;
	private Long jobId;
	private String jobName;
	private boolean fifappsAccess;
	private boolean internship;
	private String grade;
	private String subGrade;
	private String fullGrade;

	public int getSortAccess() {
		return isFifappsAccess() ? 1 : DateUtil.truncate(getEndDate()).equals(
				DateUtil.MAX_DATE) ? 1 : 0;
	}

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public String getSecondaryType() {
		return secondaryType;
	}

	public void setSecondaryType(String secondaryType) {
		this.secondaryType = secondaryType;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public boolean isFifappsAccess() {
		return fifappsAccess;
	}

	public void setFifappsAccess(boolean fifappsAccess) {
		this.fifappsAccess = fifappsAccess;
	}

	public boolean isInternship() {
		return internship;
	}

	public void setInternship(boolean internship) {
		this.internship = internship;
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

	public String getFullGrade() {
		if (grade != null && subGrade != null) {
			fullGrade = grade.concat(" - ").concat(subGrade);
		} else {
			if (grade != null && subGrade == null) {
				fullGrade = grade.toString();
			} else {
				fullGrade = null;
			}
		}

		return fullGrade;
	}

	public void setFullGrade(String fullGrade) {
		this.fullGrade = fullGrade;
	}

}
