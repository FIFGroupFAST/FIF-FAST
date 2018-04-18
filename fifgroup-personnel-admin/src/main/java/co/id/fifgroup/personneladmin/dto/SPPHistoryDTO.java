package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SPPHistoryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jobName;
	private BigDecimal employeeNumber;
	private String fullName;
	private boolean isdevelopmentOrganizerHO;
	private String developmentCategory;
	private String developmentType;
	private String developmentName;
	private String coeName;
	private Date startDate;
	private Date endDate;
	private Long contractPeriod;
	private BigDecimal penaltyAmount;
	private String penaltyAmountFormat;
	private String faileddevelopment;
	private String documentSppUrl;
	private Date developmentStartDate;
	private Date developmentEndDate;
	
	
	public BigDecimal getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(BigDecimal employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String getPenaltyAmountFormat() {
		return penaltyAmountFormat;
	}
	public void setPenaltyAmountFormat(String penaltyAmountFormat) {
		this.penaltyAmountFormat = penaltyAmountFormat;
	}
	public String getDocumentSppUrl() {
		return documentSppUrl;
	}
	public void setDocumentSppUrl(String documentSppUrl) {
		this.documentSppUrl = documentSppUrl;
	}
	public boolean isIsdevelopmentOrganizerHO() {
		return isdevelopmentOrganizerHO;
	}
	public void setIsdevelopmentOrganizerHO(boolean isdevelopmentOrganizerHO) {
		this.isdevelopmentOrganizerHO = isdevelopmentOrganizerHO;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(Long contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public BigDecimal getPenaltyAmount() {
		return penaltyAmount;
	}
	public void setPenaltyAmount(BigDecimal penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}
	public String getFaileddevelopment() {
		return faileddevelopment;
	}
	public void setFaileddevelopment(String faileddevelopment) {
		this.faileddevelopment = faileddevelopment;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
}
