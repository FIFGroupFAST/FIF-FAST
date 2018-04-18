package co.id.fifgroup.basicsetup.dto;

import co.id.fifgroup.basicsetup.domain.FormulaParameter;

public class FormulaParameterDTO extends FormulaParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 131436954726310294L;
	
	String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
