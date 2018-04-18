package co.id.fifgroup.personneladmin.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.personneladmin.domain.ProbationReview;

public class ProbationReviewDTO extends ProbationReview {
	
	private static final long serialVersionUID = 1L;
	
	private Long organizationId;
	private String organizationCode;
	private String organizationName;
	private Long jobId;
	private String jobName;
	private Long gradeId;
	private String gradeName;
	private String employeeNumber;
	private String fullName;
	private Date probationEndDate;
	private Long branchId;
	private String branchName;
	private Long locationId;
	private String locationName;
	private String userName;
	private Date dateFrom;
	private Date dateTo;
	private Date effectiveOnDate;
	private UUID personUUID;
	private boolean isValid;
	
	private int number = 0;
	
	public int getNumber() {
		return this.number;
	}
	
//	private List<QuestionAnswer> questionAnswerProbation;
	
	private List<ProbationCompetencyReviewDTO> softCompetencyReviewDTOs;
	private List<ProbationCompetencyReviewDTO> technicalCompetencyReviewDTOs;
	
	public UUID getPersonUUID() {
		return personUUID;
	}

	public void setPersonUUID(UUID personUUID) {
		this.personUUID = personUUID;
	}
	
	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
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

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
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
		try {
			this.number =  Integer.parseInt(employeeNumber);
		} catch(NumberFormatException e) { }
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getProbationEndDate() {
		return probationEndDate;
	}

	public void setProbationEndDate(Date probationEndDate) {
		this.probationEndDate = probationEndDate;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}

	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}

	public List<ProbationCompetencyReviewDTO> getSoftCompetencyReviewDTOs() {
		return softCompetencyReviewDTOs;
	}

	public void setSoftCompetencyReviewDTOs(
			List<ProbationCompetencyReviewDTO> softCompetencyReviewDTOs) {
		this.softCompetencyReviewDTOs = softCompetencyReviewDTOs;
	}

	public List<ProbationCompetencyReviewDTO> getTechnicalCompetencyReviewDTOs() {
		return technicalCompetencyReviewDTOs;
	}

	public void setTechnicalCompetencyReviewDTOs(
			List<ProbationCompetencyReviewDTO> technicalCompetencyReviewDTOs) {
		this.technicalCompetencyReviewDTOs = technicalCompetencyReviewDTOs;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

//	public List<QuestionAnswer> getQuestionAnswerProbation() {
//		return questionAnswerProbation;
//	}
//
//	public void setQuestionAnswerProbation(
//			List<QuestionAnswer> questionAnswerProbation) {
//		this.questionAnswerProbation = questionAnswerProbation;
//	}
	
	
}
