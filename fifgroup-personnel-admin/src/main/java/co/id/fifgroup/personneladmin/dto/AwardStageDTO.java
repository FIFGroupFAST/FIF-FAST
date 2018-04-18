package co.id.fifgroup.personneladmin.dto;

import java.math.BigDecimal;
import java.util.Date;

import co.id.fifgroup.core.domain.UploadStage;

public class AwardStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;

	private String employeeNumber;
	private Date awardDate;
	private String jobCode;
	private String awardType;
	private String awardPrize;
	private BigDecimal amount;

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Date getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getAwardPrize() {
		return awardPrize;
	}

	public void setAwardPrize(String awardPrize) {
		this.awardPrize = awardPrize;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
