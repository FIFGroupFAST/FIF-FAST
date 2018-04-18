package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Admin
 *
 */
public class DevelopmentHistoryDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String developmentOrganizer;
	private String developmentCategory;
	private String developmentType;
	private String developmentName;
	private String coeName;
	private Date developmentStartDate;
	private Date developmentEndDate;
	private Long totalAttendees;
	private String preActivityStatus;
	private Long inClassDevelopmentScore;
	private Long remedialInClassDevelopmentScore;
	private String postActivityStatus;
	private String finalResult;
	private String devResultRemark;
	
	/*
	 * start add for Development History Dashboard
	 */
	private String employeeName; //PEA personal information
	private String jobName; //wos_jobs
	private String employeeNumber;  //PEA personal information
	private String developmentProposalID;  
	
	public String getEmployeeName() {
		return employeeName;
	}
	public String getJobName() {
		return jobName;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public String getDevelopmentProposalID() {
		return developmentProposalID;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public void setDevelopmentProposalID(String developmentProposalID) {
		this.developmentProposalID = developmentProposalID;
	}
	
	
	/*
	 * end add for Development History Dashboard
	 */
	
	public String getDevelopmentOrganizer() {
		return developmentOrganizer;
	}
	public void setDevelopmentOrganizer(String developmentOrganizer) {
		this.developmentOrganizer = developmentOrganizer;
	}
	public String getDevelopmentCategory() {
		return developmentCategory;
	}
	public void setDevelopmentCategory(String developmentCategory) {
		this.developmentCategory = developmentCategory;
	}
	public String getDevelopmentType() {
		return developmentType;
	}
	public void setDevelopmentType(String developmentType) {
		this.developmentType = developmentType;
	}
	public String getDevelopmentName() {
		return developmentName;
	}
	public void setDevelopmentName(String developmentName) {
		this.developmentName = developmentName;
	}
	public String getCoeName() {
		return coeName;
	}
	public void setCoeName(String coeName) {
		this.coeName = coeName;
	}
	public Date getDevelopmentStartDate() {
		return developmentStartDate;
	}
	public void setDevelopmentStartDate(Date developmentStartDate) {
		this.developmentStartDate = developmentStartDate;
	}
	public Date getDevelopmentEndDate() {
		return developmentEndDate;
	}
	public void setDevelopmentEndDate(Date developmentEndDate) {
		this.developmentEndDate = developmentEndDate;
	}
	public Long getTotalAttendees() {
		return totalAttendees;
	}
	public void setTotalAttendees(Long totalAttendees) {
		this.totalAttendees = totalAttendees;
	}
	public String getPreActivityStatus() {
		return preActivityStatus;
	}
	public void setPreActivityStatus(String preActivityStatus) {
		this.preActivityStatus = preActivityStatus;
	}
	public Long getInClassDevelopmentScore() {
		return inClassDevelopmentScore;
	}
	public void setInClassDevelopmentScore(Long inClassDevelopmentScore) {
		this.inClassDevelopmentScore = inClassDevelopmentScore;
	}
	public Long getRemedialInClassDevelopmentScore() {
		return remedialInClassDevelopmentScore;
	}
	public void setRemedialInClassDevelopmentScore(
			Long remedialInClassDevelopmentScore) {
		this.remedialInClassDevelopmentScore = remedialInClassDevelopmentScore;
	}
	public String getPostActivityStatus() {
		return postActivityStatus;
	}
	public void setPostActivityStatus(String postActivityStatus) {
		this.postActivityStatus = postActivityStatus;
	}
	public String getFinalResult() {
		return finalResult;
	}
	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}
	public String getDevResultRemark() {
		return devResultRemark;
	}
	public void setDevResultRemark(String devResultRemark) {
		this.devResultRemark = devResultRemark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
