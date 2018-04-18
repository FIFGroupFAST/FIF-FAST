package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.version.Version;
import co.id.fifgroup.workstructure.domain.JobDocument;

import co.id.fifgroup.basicsetup.domain.LookupDependent;
import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;

public class JobDTO extends Version implements Serializable {

	private static final long serialVersionUID = -2414432333412325181L;

	private Long companyId;
	private String jobCode;
	private String jobName;
	private UUID jobUuid;
	private String description;
	private String jobForCode;
	private String jobFor;
	private String jobTypeCode;
	private String jobType;
	private String peopleCategoryCode;
	private String peopleCategory;
	private String jobGroupCode;
	private String jobGroup;
	private Boolean isKeyJob;
	private Boolean isManager;
	private String mainResponsibility;
	private String jobFilePath;
	private Long gradeSetId;
	private String gradeSetName;
	private Long workingScheduleId;
	private List<OtherInfoDTO> jobInfos;
	private List<JobValidGradeDTO> jobValidGrades;
	private List<JobRoleDTO> jobRoles;
	private List<JobSpecificationDTO> jobSpecifications;
	private Date effectiveDate;
	private OtherInfoComponent otherInfoComponent;
	private Boolean isUpload = true;
	private String keyJob;
	private String manager;
	private String userName;
	private List<String> listJobFor;
	private List<LookupDependent> documentType;
	private List<JobDocument> jobDocuments;
	private boolean validJobsDocuments;

	
	public boolean isValidJobsDocuments() {
		return validJobsDocuments;
	}

	public void setValidJobsDocuments(boolean validJobsDocuments) {
		this.validJobsDocuments = validJobsDocuments;
	}

	public List<JobDocument> getJobDocuments() {
		return jobDocuments;
	}

	public void setJobDocuments(List<JobDocument> jobDocuments) {
		this.jobDocuments = jobDocuments;
	}

	public List<LookupDependent> getDocumentType() {
		return documentType;
	}

	public void setDocumentType(List<LookupDependent> list) {
		this.documentType = list;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public UUID getJobUuid() {
		return jobUuid;
	}

	public void setJobUuid(UUID jobUuid) {
		this.jobUuid = jobUuid;
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

	public String getMainResponsibility() {
		return mainResponsibility;
	}

	public void setMainResponsibility(String mainResponsibility) {
		this.mainResponsibility = mainResponsibility;
	}

	public String getJobFilePath() {
		return jobFilePath;
	}

	public void setJobFilePath(String jobFilePath) {
		this.jobFilePath = jobFilePath;
	}

	public Long getGradeSetId() {
		return gradeSetId;
	}

	public void setGradeSetId(Long gradeSetId) {
		this.gradeSetId = gradeSetId;
	}

	public String getGradeSetName() {
		return gradeSetName;
	}

	public void setGradeSetName(String gradeSetName) {
		this.gradeSetName = gradeSetName;
	}

	public Long getWorkingScheduleId() {
		return workingScheduleId;
	}

	public void setWorkingScheduleId(Long workingScheduleId) {
		this.workingScheduleId = workingScheduleId;
	}

	public List<OtherInfoDTO> getJobInfos() {
		return jobInfos;
	}

	public void setJobInfos(List<OtherInfoDTO> jobInfos) {
		this.jobInfos = jobInfos;
	}

	public List<JobValidGradeDTO> getJobValidGrades() {
		return jobValidGrades;
	}

	public void setJobValidGrades(List<JobValidGradeDTO> jobValidGrades) {
		this.jobValidGrades = jobValidGrades;
	}

	public List<JobRoleDTO> getJobRoles() {
		return jobRoles;
	}

	public void setJobRoles(List<JobRoleDTO> jobRoles) {
		this.jobRoles = jobRoles;
	}

	public List<JobSpecificationDTO> getJobSpecifications() {
		return jobSpecifications;
	}

	public void setJobSpecifications(List<JobSpecificationDTO> jobSpecifications) {
		this.jobSpecifications = jobSpecifications;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getJobFor() {
		return jobFor;
	}

	public void setJobFor(String jobFor) {
		this.jobFor = jobFor;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getPeopleCategory() {
		return peopleCategory;
	}

	public void setPeopleCategory(String peopleCategory) {
		this.peopleCategory = peopleCategory;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public OtherInfoComponent getOtherInfoComponent() {
		return otherInfoComponent;
	}

	public void setOtherInfoComponent(OtherInfoComponent otherInfoComponent) {
		this.otherInfoComponent = otherInfoComponent;
	}

	public Boolean isUpload() {
		return isUpload;
	}

	public void setUpload(Boolean isUpload) {
		this.isUpload = isUpload;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "JobDto [id=" + getId() + "jobCode=" + jobCode + ", jobName="
				+ jobName + ", description=" + description + ", jobForCode="
				+ jobForCode + ", jobTypeCode=" + jobTypeCode
				+ ", peopleCategoryCode=" + peopleCategoryCode
				+ ", jobGroupCode=" + jobGroupCode + ", jobInfos=" + jobInfos
				+ ", jobValidGrades=" + jobValidGrades + ", jobRoles="
				+ jobRoles + ", jobSpecifications=" + jobSpecifications
				+ ", gradeSetId=" + gradeSetId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((jobCode == null) ? 0 : jobCode.hashCode());
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobDTO other = (JobDTO) obj;
		if (jobCode == null) {
			if (other.jobCode != null)
				return false;
		} else if (!jobCode.equals(other.jobCode))
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		return true;
	}

	public List<String> getListJobFor() {
		return listJobFor;
	}

	public void setListJobFor(List<String> listJobFor) {
		this.listJobFor = listJobFor;
	}

}
