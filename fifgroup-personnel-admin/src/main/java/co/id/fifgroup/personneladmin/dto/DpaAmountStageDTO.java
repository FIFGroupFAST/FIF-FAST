package co.id.fifgroup.personneladmin.dto;

import java.math.BigDecimal;

import co.id.fifgroup.core.domain.UploadStage;

public class DpaAmountStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;

	private String employeeNumber;
	private BigDecimal dpaByCompany;
	private BigDecimal dpaByEmployee;

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public BigDecimal getDpaByCompany() {
		return dpaByCompany;
	}

	public void setDpaByCompany(BigDecimal dpaByCompany) {
		this.dpaByCompany = dpaByCompany;
	}

	public BigDecimal getDpaByEmployee() {
		return dpaByEmployee;
	}

	public void setDpaByEmployee(BigDecimal dpaByEmployee) {
		this.dpaByEmployee = dpaByEmployee;
	}

}
