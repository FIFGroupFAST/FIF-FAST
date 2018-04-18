package co.id.fifgroup.core.dto;
import java.io.Serializable;
import java.util.Date;

public class CareerAndTalentDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long havDetailId;
	private String havName;
	private Long employeeNumber;
	private Integer period;
	private Long entryAssesmentId;
	private Long personId;
	private Long companyId;
	private Date assesmentDate;
	private String bureauPsychology;
	private String assesmentResult;
	private String hcResult;
	private Long entryBy;
	private Long jobAssessmentId;
	private Long branchId;
	private String urlPath;
	private String fullName;
	private String originJob;
	private String targetJob;
	private String grade;
	private String remark;
	private String actingDecision;
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getActingDecision() {
		return actingDecision;
	}
	public void setActingDecision(String actingDecision) {
		this.actingDecision = actingDecision;
	}
	public Long getEntryBy() {
		return entryBy;
	}
	public void setEntryBy(Long entryBy) {
		this.entryBy = entryBy;
	}
	public String getOriginJob() {
		return originJob;
	}
	public void setOriginJob(String originJob) {
		this.originJob = originJob;
	}
	public String getTargetJob() {
		return targetJob;
	}
	public void setTargetJob(String targetJob) {
		this.targetJob = targetJob;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(Long employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public Long getEntryAssesmentId() {
		return entryAssesmentId;
	}
	public void setEntryAssesmentId(Long entryAssesmentId) {
		this.entryAssesmentId = entryAssesmentId;
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
	public Date getAssesmentDate() {
		return assesmentDate;
	}
	public void setAssesmentDate(Date assesmentDate) {
		this.assesmentDate = assesmentDate;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getBureauPsychology() {
		return bureauPsychology;
	}
	public void setBureauPsychology(String bureauPsychology) {
		this.bureauPsychology = bureauPsychology;
	}
	public String getAssesmentResult() {
		return assesmentResult;
	}
	public void setAssesmentResult(String assesmentResult) {
		this.assesmentResult = assesmentResult;
	}
	public String getHcResult() {
		return hcResult;
	}
	public void setHcResult(String hcResult) {
		this.hcResult = hcResult;
	}

	public Long getJobAssessmentId() {
		return jobAssessmentId;
	}
	public void setJobAssessmentId(Long jobAssessmentId) {
		this.jobAssessmentId = jobAssessmentId;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getHavDetailId() {
		return havDetailId;
	}
	public void setHavDetailId(Long havDetailId) {
		this.havDetailId = havDetailId;
	}
	public String getHavName() {
		return havName;
	}
	public void setHavName(String havName) {
		this.havName = havName;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	

}
