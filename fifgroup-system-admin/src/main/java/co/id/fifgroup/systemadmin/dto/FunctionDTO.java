package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.systemadmin.domain.Function;

public class FunctionDTO extends Function {
	
	private static final long serialVersionUID = 1L;
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
