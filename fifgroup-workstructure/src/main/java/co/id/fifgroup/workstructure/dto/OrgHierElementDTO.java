package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;

import co.id.fifgroup.workstructure.domain.OrgHierarchyElement;

public class OrgHierElementDTO extends OrgHierarchyElement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String orgName;
	private Integer subordinate;
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(Integer subordinate) {
		this.subordinate = subordinate;
	}
	
	@Override
	public String toString() {
		return "OrgHierElementDTO [orgName=" + orgName + ", getVersionId()="
				+ getVersionId() + ", getParentId()=" + getParentId()
				+ ", getOrganizationId()=" + getOrganizationId() + "]";
	}
}
