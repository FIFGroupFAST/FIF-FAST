package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.version.Version;

public class OrgHierarchyDTO extends Version implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long companyId;
    private String orgHierType;
    private Boolean isDeptOwner;
    private String orgHierName;
    private String description;
    private Long createdBy;
    private Date creationDate;
    private Long lastUpdatedBy;
    private Date lastUpdateDate;
    private Date effectiveOnDate;
    private String userName;
	private List<OrgHierElementDTO> elements;
	
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getOrgHierType() {
		return orgHierType;
	}
	public void setOrgHierType(String orgHierType) {
		this.orgHierType = orgHierType;
	}
	public String getOrgHierName() {
		return orgHierName;
	}
	public Boolean getIsDeptOwner() {
		return isDeptOwner;
	}
	public void setIsDeptOwner(Boolean isDeptOwner) {
		this.isDeptOwner = isDeptOwner;
	}
	public void setOrgHierName(String orgHierName) {
		this.orgHierName = orgHierName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}
	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<OrgHierElementDTO> getElements() {
		return elements;
	}
	public void setElements(List<OrgHierElementDTO> elements) {
		this.elements = elements;
	}
	@Override
	public String toString() {
		return "OrgHierarchyDTO [orgHierType=" + orgHierType + ", isDeptOwner="
				+ isDeptOwner + ", orgHierName=" + orgHierName
				+ ", elements=" + elements
				+ ", getId()=" + getId() + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((orgHierName == null) ? 0 : orgHierName.hashCode());
		result = prime * result
				+ ((orgHierType == null) ? 0 : orgHierType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrgHierarchyDTO other = (OrgHierarchyDTO) obj;
		if (orgHierName == null) {
			if (other.orgHierName != null)
				return false;
		} else if (!orgHierName.equals(other.orgHierName))
			return false;
		if (orgHierType == null) {
			if (other.orgHierType != null)
				return false;
		} else if (!orgHierType.equals(other.orgHierType))
			return false;
		return true;
	}
	
	
	
}
