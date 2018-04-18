package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.Date;

public class AssessmentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long assessmentId;
	private String resultHrd;
	private String resultAssessment;
	private Date testDate;
	private String filePath;
	private String strength;
	private String developmentPlan;
	private String supplierCode;
	private String supplierOpexName;
	private String supplierBankCode;
	private String supplierBankAccountNumber;
	private Long entryIcpId;
	private Long personId;
	private String employeeNumber;
	private String lastName;
	private String creatorLastName;
	private Long jobId;
	private String job;
	private String grade;
	private Long jobDestinationId;
	private String jobDestination;
	private String organizationName;
	private String organizationBranchName;
	private Long organizationBranchId;
	private String positionName;
	private Date actualTerminationDate;
	private Date startDate;
	private Long competencyHdrId;
	private String selfGuideLearning;

	public Long getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}

	public String getResultHrd() {
		return resultHrd;
	}

	public void setResultHrd(String resultHrd) {
		this.resultHrd = resultHrd;
	}

	public String getResultAssessment() {
		return resultAssessment;
	}

	public void setResultAssessment(String resultAssessment) {
		this.resultAssessment = resultAssessment;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getDevelopmentPlan() {
		return developmentPlan;
	}

	public void setDevelopmentPlan(String developmentPlan) {
		this.developmentPlan = developmentPlan;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierOpexName() {
		return supplierOpexName;
	}

	public void setSupplierOpexName(String supplierOpexName) {
		this.supplierOpexName = supplierOpexName;
	}

	public String getSupplierBankCode() {
		return supplierBankCode;
	}

	public void setSupplierBankCode(String supplierBankCode) {
		this.supplierBankCode = supplierBankCode;
	}

	public String getSupplierBankAccountNumber() {
		return supplierBankAccountNumber;
	}

	public void setSupplierBankAccountNumber(String supplierBankAccountNumber) {
		this.supplierBankAccountNumber = supplierBankAccountNumber;
	}

	public Long getEntryIcpId() {
		return entryIcpId;
	}

	public void setEntryIcpId(Long entryIcpId) {
		this.entryIcpId = entryIcpId;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCreatorLastName() {
		return creatorLastName;
	}

	public void setCreatorLastName(String creatorLastName) {
		this.creatorLastName = creatorLastName;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Long getJobDestinationId() {
		return jobDestinationId;
	}

	public void setJobDestinationId(Long jobDestinationId) {
		this.jobDestinationId = jobDestinationId;
	}

	public String getJobDestination() {
		return jobDestination;
	}

	public void setJobDestination(String jobDestination) {
		this.jobDestination = jobDestination;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationBranchName() {
		return organizationBranchName;
	}

	public void setOrganizationBranchName(String organizationBranchName) {
		this.organizationBranchName = organizationBranchName;
	}

	public Long getOrganizationBranchId() {
		return organizationBranchId;
	}

	public void setOrganizationBranchId(Long organizationBranchId) {
		this.organizationBranchId = organizationBranchId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Date getActualTerminationDate() {
		return actualTerminationDate;
	}

	public void setActualTerminationDate(Date actualTerminationDate) {
		this.actualTerminationDate = actualTerminationDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getCompetencyHdrId() {
		return competencyHdrId;
	}

	public void setCompetencyHdrId(Long competencyHdrId) {
		this.competencyHdrId = competencyHdrId;
	}

	public String getSelfGuideLearning() {
		return selfGuideLearning;
	}

	public void setSelfGuideLearning(String selfGuideLearning) {
		this.selfGuideLearning = selfGuideLearning;
	}

}
