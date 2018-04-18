package co.id.fifgroup.personneladmin.dto;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.basicsetup.common.History;
import co.id.fifgroup.personneladmin.domain.AssignmentOtherInfo;
import co.id.fifgroup.personneladmin.domain.PrimaryAssignment;

public class PrimaryAssignmentDTO extends PrimaryAssignment implements History {

	private static final long serialVersionUID = 3492213585885994919L;

	private String organizationName;
	private Long branchId;
	private String branchCode;
	private String branchName;
	private Long jobId;
	private String jobName;
	private String jobGroupCode;
	private String jobGroup;
	private String personalGradeCode;
	private String personalGrade;
	private Long locationId;
	private String locationName;
	private String gradeName;
	private String grade;
	private String subGrade;
	private String employeeNumber;
	private String companyName;
	private List<AssignmentOtherInfo> assignmentOtherInfos;
	private boolean otherInfoValidate;

	/*
	 * Added 29 Juli 2015
	 */
	private Long appraisalEmployeeId;
	private Short isPromoted;
	private String nextGrade;
	private Date lastJobDate;
	private Date lastGradeDate;
	
	//add by cahyo for ITSM 16030817095479
	private Long createdBy;
	private Long lastUpdatedBy;
	//end of add
	
	public Long getAppraisalEmployeeId() {
		return appraisalEmployeeId;
	}

	public void setAppraisalEmployeeId(Long appraisalEmployeeId) {
		this.appraisalEmployeeId = appraisalEmployeeId;
	}

	public Short getIsPromoted() {
		return isPromoted;
	}

	public void setIsPromoted(Short isPromoted) {
		this.isPromoted = isPromoted;
	}

	public String getNextGrade() {
		return nextGrade;
	}

	public void setNextGrade(String nextGrade) {
		this.nextGrade = nextGrade;
	}

	public Date getLastJobDate() {
		return lastJobDate;
	}

	public void setLastJobDate(Date lastJobDate) {
		this.lastJobDate = lastJobDate;
	}

	public Date getLastGradeDate() {
		return lastGradeDate;
	}

	public void setLastGradeDate(Date lastGradeDate) {
		this.lastGradeDate = lastGradeDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<AssignmentOtherInfo> getAssignmentOtherInfos() {
		return assignmentOtherInfos;
	}

	public void setAssignmentOtherInfos(
			List<AssignmentOtherInfo> assignmentOtherInfos) {
		this.assignmentOtherInfos = assignmentOtherInfos;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobGroupCode() {
		return jobGroupCode;
	}

	public void setJobGroupCode(String jobGroupCode) {
		this.jobGroupCode = jobGroupCode;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getPersonalGradeCode() {
		return personalGradeCode;
	}

	public void setPersonalGradeCode(String personalGradeCode) {
		this.personalGradeCode = personalGradeCode;
	}

	public String getPersonalGrade() {
		return personalGrade;
	}

	public void setPersonalGrade(String personalGrade) {
		this.personalGrade = personalGrade;
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

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public boolean isOtherInfoValidate() {
		return otherInfoValidate;
	}

	public void setOtherInfoValidate(boolean otherInfoValidate) {
		this.otherInfoValidate = otherInfoValidate;
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

	//add by cahyo for ITSM 16030817095479
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	//end of add
	
	
	

}
