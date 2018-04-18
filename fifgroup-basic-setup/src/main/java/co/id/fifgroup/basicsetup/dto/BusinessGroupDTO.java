package co.id.fifgroup.basicsetup.dto;

import java.io.Serializable;

import co.id.fifgroup.basicsetup.domain.BusinessGroup;

public class BusinessGroupDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2359093678515544776L;
	private BusinessGroup businessGroup;
	private String userName;
	
	public BusinessGroup getBusinessGroup() {
		return businessGroup;
	}
	public void setBusinessGroup(BusinessGroup businessGroup) {
		this.businessGroup = businessGroup;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
