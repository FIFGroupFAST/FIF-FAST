package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.systemadmin.domain.IncludedOrganization;

public class IncludedOrganizationDTO extends IncludedOrganization {

	private static final long serialVersionUID = 1L;
	private String organizationName;
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
