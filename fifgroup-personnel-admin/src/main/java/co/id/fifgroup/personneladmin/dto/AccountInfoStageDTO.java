package co.id.fifgroup.personneladmin.dto;

import java.util.Date;

import co.id.fifgroup.core.domain.UploadStage;

public class AccountInfoStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;

	private String accountType;
	private String employeeNumber;
	private String relationshipCode;
	private String insuranceNumber;
	private String dpaNumber;
	private Date dpaJoinDate;
	private String kpjNumber;
	private Date effectiveStartDate;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getRelationshipCode() {
		return relationshipCode;
	}

	public void setRelationshipCode(String relationshipCode) {
		this.relationshipCode = relationshipCode;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public String getDpaNumber() {
		return dpaNumber;
	}

	public void setDpaNumber(String dpaNumber) {
		this.dpaNumber = dpaNumber;
	}

	public Date getDpaJoinDate() {
		return dpaJoinDate;
	}

	public void setDpaJoinDate(Date dpaJoinDate) {
		this.dpaJoinDate = dpaJoinDate;
	}

	public String getKpjNumber() {
		return kpjNumber;
	}

	public void setKpjNumber(String kpjNumber) {
		this.kpjNumber = kpjNumber;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((employeeNumber == null) ? 0 : employeeNumber.hashCode());
		result = prime
				* result
				+ ((relationshipCode == null) ? 0 : relationshipCode.hashCode());
		return result;
	}
}
