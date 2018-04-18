package co.id.fifgroup.systemworkflow.dto;

import co.id.fifgroup.systemworkflow.domain.TemplateMessageMapping;

public class TemplateMessageMappingDTO extends TemplateMessageMapping {

	private static final long serialVersionUID = 1L;
	
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}	

}
