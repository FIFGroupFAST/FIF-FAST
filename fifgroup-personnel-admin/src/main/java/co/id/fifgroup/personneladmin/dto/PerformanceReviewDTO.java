package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.Date;

public class PerformanceReviewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long personId;
	private String employeeNumber;
	private Date reviewDate;
	private String performanceRating;
	private String meaning;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private Long lastUpdateLogin;
	private Long createdBy;
	private Date creationDate;
	private Long companyId;
	

	private Long period;
	private String performanceGrade;
	private Double multiplierAmount;
	private Long bonusAmount;
	private Long sequence;
	
	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public String getPerformanceGrade() {
		return performanceGrade;
	}

	public void setPerformanceGrade(String performanceGrade) {
		this.performanceGrade = performanceGrade;
	}

	public Double getMultiplierAmount() {
		return multiplierAmount;
	}

	public void setMultiplierAmount(Double multiplierAmount) {
		this.multiplierAmount = multiplierAmount;
	}

	public Long getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Long bonusAmount) {
		this.bonusAmount = bonusAmount;
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

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getPerformanceRating() {
		return performanceRating;
	}

	public void setPerformanceRating(String performanceRating) {
		this.performanceRating = performanceRating;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Long lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

}
