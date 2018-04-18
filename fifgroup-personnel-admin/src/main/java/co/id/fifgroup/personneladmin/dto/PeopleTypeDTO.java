package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.basicsetup.common.History;
import co.id.fifgroup.personneladmin.domain.PeopleType;

public class PeopleTypeDTO extends PeopleType implements History {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String employeeNumber;
	private String applicantNo;

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getApplicantNo() {
		return applicantNo;
	}

	public void setApplicantNo(String applicantNo) {
		this.applicantNo = applicantNo;
	}

}
