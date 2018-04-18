package co.id.fifgroup.ssoa.dto;

import co.id.fifgroup.ssoa.domain.SystemProperty;

public class SystemPropertyDTO extends SystemProperty {

	private static final long serialVersionUID = -6760769892369076382L;
	private String lastUpdateUserName;

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}
}