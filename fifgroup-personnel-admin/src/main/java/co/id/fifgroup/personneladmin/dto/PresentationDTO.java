package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.Date;

public class PresentationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long entryPresenId;
	private String typePresentasi;
	private String filePath;
	private String note;
	private Date presentationDate;
	private String finalResult;
	private Long entryIcpId;
	private Long entryIdpHdrId;
	private Long personId;
	private String employeeNumber;
	private String lastName;
	private Long jobId;
	private String job;
	private String jobDestination;
	private String grade;
	private String organizationName;
	private String organizationBranchName;
	private Long organizationBranchId;
	private String positionName;
	private Date actualTerminationDate;
	private Date dateStart;

	public Long getEntryPresenId() {
		return entryPresenId;
	}

	public void setEntryPresenId(Long entryPresenId) {
		this.entryPresenId = entryPresenId;
	}

	public String getTypePresentasi() {
		return typePresentasi;
	}

	public void setTypePresentasi(String typePresentasi) {
		this.typePresentasi = typePresentasi;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getPresentationDate() {
		return presentationDate;
	}

	public void setPresentationDate(Date presentationDate) {
		this.presentationDate = presentationDate;
	}

	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}

	public Long getEntryIcpId() {
		return entryIcpId;
	}

	public void setEntryIcpId(Long entryIcpId) {
		this.entryIcpId = entryIcpId;
	}

	public Long getEntryIdpHdrId() {
		return entryIdpHdrId;
	}

	public void setEntryIdpHdrId(Long entryIdpHdrId) {
		this.entryIdpHdrId = entryIdpHdrId;
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

	public String getJobDestination() {
		return jobDestination;
	}

	public void setJobDestination(String jobDestination) {
		this.jobDestination = jobDestination;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

}
