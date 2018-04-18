package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RVUnsettledDTO implements Serializable {

	private static final long serialVersionUID = 4021891859169931479L;

	private String branchName;
	private String employeeNumber;
	private String fullName;
	private String transaction;
	private String rvNumber;
	private Date rvCreationDate;
	private String description;
	private BigDecimal rvAmount;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getRvNumber() {
		return rvNumber;
	}

	public void setRvNumber(String rvNumber) {
		this.rvNumber = rvNumber;
	}

	public Date getRvCreationDate() {
		return rvCreationDate;
	}

	public void setRvCreationDate(Date rvCreationDate) {
		this.rvCreationDate = rvCreationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getRvAmount() {
		return rvAmount;
	}

	public void setRvAmount(BigDecimal rvAmount) {
		this.rvAmount = rvAmount;
	}

}
