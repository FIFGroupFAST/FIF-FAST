package co.id.fifgroup.basicsetup.dto;

import java.io.Serializable;

import co.id.fifgroup.basicsetup.domain.GlobalSetting;

public class GlobalSettingDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -658766461176826055L;
	private GlobalSetting globalSetting;
	private String userName;
	
	public GlobalSetting getGlobalSetting() {
		return globalSetting;
	}
	
	public void setGlobalSetting(GlobalSetting globalSetting) {
		this.globalSetting = globalSetting;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
