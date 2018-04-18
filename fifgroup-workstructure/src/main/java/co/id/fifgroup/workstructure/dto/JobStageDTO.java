package co.id.fifgroup.workstructure.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.domain.UploadStage;

public class JobStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;
	
	private Date dateFrom;
	private String code;
	private String name;
	private String description;
	private String jobForCode;
	private String jobTypeCode;
	private String peopleCategoryCode;
	private String jobGroupCode;
	private String keyJob;
	private String manager;
	private String gradeSetName;
	private Long gradeSetId;
	private Boolean isKeyJob;
	private Boolean isManager;
	private Date dateTo;
	private String workingSchedule;
	private Long workingScheduleId;
	private Long companyId;
	private List<String> otherInfoStrings = new ArrayList<>();
	public List<String> getOtherInfoStrings() {
		return otherInfoStrings;
	}
	public void setOtherInfoStrings(List<String> otherInfoStrings) {
		this.otherInfoStrings = otherInfoStrings;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getJobGroupCode() {
		return jobGroupCode;
	}
	public void setJobGroupCode(String jobGroupCode) {
		this.jobGroupCode = jobGroupCode;
	}
	public String getKeyJob() {
		return keyJob;
	}
	public void setKeyJob(String keyJob) {
		this.keyJob = keyJob;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getGradeSetName() {
		return gradeSetName;
	}
	public void setGradeSetName(String gradeSetName) {
		this.gradeSetName = gradeSetName;
	}
	public Long getGradeSetId() {
		return gradeSetId;
	}
	public void setGradeSetId(Long gradeSetId) {
		this.gradeSetId = gradeSetId;
	}
	public Boolean getIsKeyJob() {
		return isKeyJob;
	}
	public void setIsKeyJob(Boolean isKeyJob) {
		this.isKeyJob = isKeyJob;
	}
	public Boolean getIsManager() {
		return isManager;
	}
	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getWorkingSchedule() {
		return workingSchedule;
	}
	public void setWorkingSchedule(String workingSchedule) {
		this.workingSchedule = workingSchedule;
	}
	public Long getWorkingScheduleId() {
		return workingScheduleId;
	}
	public void setWorkingScheduleId(Long workingScheduleId) {
		this.workingScheduleId = workingScheduleId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}	
}
