package co.id.fifgroup.basicsetup.dto;

import co.id.fifgroup.basicsetup.domain.Module;

public class ModuleDTO extends Module {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6760769892369076382L;
	private String lastUpdateUserName;
	
	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}
	
	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}
}
