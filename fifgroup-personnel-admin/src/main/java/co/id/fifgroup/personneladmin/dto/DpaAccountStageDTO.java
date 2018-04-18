package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.core.domain.UploadStage;

public class DpaAccountStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private String employeeNumber;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

}
