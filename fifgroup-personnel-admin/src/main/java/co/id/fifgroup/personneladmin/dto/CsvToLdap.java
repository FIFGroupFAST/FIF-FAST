package co.id.fifgroup.personneladmin.dto;

import java.util.Date;

public class CsvToLdap {

	private String employeeNumber;
	private String firstName;
	private String lastName;
	private String jobName;
	private String branchName;
	private String locationCode;
	private String phoneNumber;
	private Date hireDate;
	private Date terminationDate;
	private String employeeStatus;
	private String branchCode;
	private Date birthDate;
	private String employeeNumberSpv;
	private String fullNameSpv;
	private String isNew;
	private String jobCodeHrms;

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public String getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmployeeNumberSpv() {
		return employeeNumberSpv;
	}

	public void setEmployeeNumberSpv(String employeeNumberSpv) {
		this.employeeNumberSpv = employeeNumberSpv;
	}

	public String getFullNameSpv() {
		return fullNameSpv;
	}

	public void setFullNameSpv(String fullNameSpv) {
		this.fullNameSpv = fullNameSpv;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getJobCodeHrms() {
		return jobCodeHrms;
	}

	public void setJobCodeHrms(String jobCodeHrms) {
		this.jobCodeHrms = jobCodeHrms;
	}
}
