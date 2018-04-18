package co.id.fifgroup.personneladmin.dto;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.domain.TerminationDocument;
import co.id.fifgroup.personneladmin.domain.ExitClearance;
import co.id.fifgroup.personneladmin.domain.WorkingEquipment;

public class ExitClearanceDTO extends ExitClearance {

	private static final long serialVersionUID = 1L;
	private String employeeNumber;
	private String fullName;
	private Long branchId;
	private String branchName;
	private Long organizationId;
	private String organizationName;
	private Long jobId;
	private String jobName;
	private String userName;
	private Date terminationDate;
	private Long terminationTypeId;
	private String terminationReason;

	private Date terminationDateFrom;
	private Date terminationDateTo;
	private List<WorkingEquipment> listWorkingEquipment;
	private MediaDTO media;
	private List<TerminationDocument> terminationDocuments;
	private boolean exitInterviewCompleted;
	private List<Long> inOrganizations;
	private List<Long> notInOrganizations;
	
	private int number = 0;
	
	public int getNumber() {
		return this.number;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public Long getTerminationTypeId() {
		return terminationTypeId;
	}

	public void setTerminationTypeId(Long terminationTypeId) {
		this.terminationTypeId = terminationTypeId;
	}

	public String getTerminationReason() {
		return terminationReason;
	}

	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}

	public Date getTerminationDateFrom() {
		return terminationDateFrom;
	}

	public void setTerminationDateFrom(Date terminationDateFrom) {
		this.terminationDateFrom = terminationDateFrom;
	}

	public Date getTerminationDateTo() {
		return terminationDateTo;
	}

	public void setTerminationDateTo(Date terminationDateTo) {
		this.terminationDateTo = terminationDateTo;
	}

	public List<WorkingEquipment> getListWorkingEquipment() {
		return listWorkingEquipment;
	}

	public void setListWorkingEquipment(
			List<WorkingEquipment> listWorkingEquipment) {
		this.listWorkingEquipment = listWorkingEquipment;
	}

	public MediaDTO getMedia() {
		return media;
	}

	public void setMedia(MediaDTO media) {
		this.media = media;
	}

	public List<TerminationDocument> getTerminationDocuments() {
		return terminationDocuments;
	}

	public void setTerminationDocuments(
			List<TerminationDocument> terminationDocuments) {
		this.terminationDocuments = terminationDocuments;
	}

	public boolean isExitInterviewCompleted() {
		return exitInterviewCompleted;
	}

	public void setExitInterviewCompleted(boolean exitInterviewCompleted) {
		this.exitInterviewCompleted = exitInterviewCompleted;
	}

	public List<Long> getInOrganizations() {
		return inOrganizations;
	}

	public void setInOrganizations(List<Long> inOrganizations) {
		this.inOrganizations = inOrganizations;
	}

	public List<Long> getNotInOrganizations() {
		return notInOrganizations;
	}

	public void setNotInOrganizations(List<Long> notInOrganizations) {
		this.notInOrganizations = notInOrganizations;
	}

}
