package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.Date;

public class HousEmployeeDTO implements Serializable {

	private static final long serialVersionUID = -5978080169324772957L;

	private String employeeNumber;
	private String branchCode;
	private String posCode;
	private String employeeName;
	private String jobCode;
	private String emplStatus;
	private String emplArea;
	private String emplUserid;
	private Date assignmentEffectiveDate;
	private String employeeJobCode;
	private String employeeFlag;
	private String employeeHp;
	private String organizationMutationCode;
	private String branchMutationCode;
	private String jobMutationCode;
	private String employeeSmsFlag;
	private Date employeeUpdatedDate;
	private String employeeUpdatedBy;
	private String organization;

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getEmplStatus() {
		return emplStatus;
	}

	public void setEmplStatus(String emplStatus) {
		this.emplStatus = emplStatus;
	}

	public String getEmplArea() {
		return emplArea;
	}

	public void setEmplArea(String emplArea) {
		this.emplArea = emplArea;
	}

	public String getEmplUserid() {
		return emplUserid;
	}

	public void setEmplUserid(String emplUserid) {
		this.emplUserid = emplUserid;
	}

	public Date getAssignmentEffectiveDate() {
		return assignmentEffectiveDate;
	}

	public void setAssignmentEffectiveDate(Date assignmentEffectiveDate) {
		this.assignmentEffectiveDate = assignmentEffectiveDate;
	}

	public String getEmployeeJobCode() {
		return employeeJobCode;
	}

	public void setEmployeeJobCode(String employeeJobCode) {
		this.employeeJobCode = employeeJobCode;
	}

	public String getEmployeeFlag() {
		return employeeFlag;
	}

	public void setEmployeeFlag(String employeeFlag) {
		this.employeeFlag = employeeFlag;
	}

	public String getEmployeeHp() {
		return employeeHp;
	}

	public void setEmployeeHp(String employeeHp) {
		this.employeeHp = employeeHp;
	}

	public String getOrganizationMutationCode() {
		return organizationMutationCode;
	}

	public void setOrganizationMutationCode(String organizationMutationCode) {
		this.organizationMutationCode = organizationMutationCode;
	}

	public String getBranchMutationCode() {
		return branchMutationCode;
	}

	public void setBranchMutationCode(String branchMutationCode) {
		this.branchMutationCode = branchMutationCode;
	}

	public String getJobMutationCode() {
		return jobMutationCode;
	}

	public void setJobMutationCode(String jobMutationCode) {
		this.jobMutationCode = jobMutationCode;
	}

	public String getEmployeeSmsFlag() {
		return employeeSmsFlag;
	}

	public void setEmployeeSmsFlag(String employeeSmsFlag) {
		this.employeeSmsFlag = employeeSmsFlag;
	}

	public Date getEmployeeUpdatedDate() {
		return employeeUpdatedDate;
	}

	public void setEmployeeUpdatedDate(Date employeeUpdatedDate) {
		this.employeeUpdatedDate = employeeUpdatedDate;
	}

	public String getEmployeeUpdatedBy() {
		return employeeUpdatedBy;
	}

	public void setEmployeeUpdatedBy(String employeeUpdatedBy) {
		this.employeeUpdatedBy = employeeUpdatedBy;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

}
