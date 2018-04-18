package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.version.Version;

public class OrgLevelHierDTO extends Version implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long companyId;
    private String levelHierName;
    private String description;
    private Date effectiveOnDate;
    private List<OrgLevelHierElementDTO> hierElements;
    
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getLevelHierName() {
		return levelHierName;
	}
	public void setLevelHierName(String levelHierName) {
		this.levelHierName = levelHierName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}
	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}	
	public List<OrgLevelHierElementDTO> getHierElements() {
		return hierElements;
	}
	public void setHierElements(List<OrgLevelHierElementDTO> hierElements) {
		this.hierElements = hierElements;
	}
	@Override
	public String toString() {
		return "OrgLevelHierarchyDTO [levelHierName=" + levelHierName
				+ ", hierElements=" + hierElements + ", getId()=" + getId()
				+ ", getVersionId()=" + getVersionId() + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((levelHierName == null) ? 0 : levelHierName.hashCode());
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
		OrgLevelHierDTO other = (OrgLevelHierDTO) obj;
		if (levelHierName == null) {
			if (other.levelHierName != null)
				return false;
		} else if (!levelHierName.equals(other.levelHierName))
			return false;
		return true;
	}    
}
