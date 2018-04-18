package co.id.fifgroup.workstructure.domain;

import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class HierarchyElement {
	
	private Long elementId;
	private Long parentId;
	private Long hierarchyVersionId;
	private OrganizationDTO self;
	
	public HierarchyElement(Long parentId, OrganizationDTO self) {
		super();
		this.parentId = parentId;
		this.self = self;
	}
	public HierarchyElement() {
		super();
	}
	public Long getElementId() {
		return elementId;
	}
	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public OrganizationDTO getSelf() {
		return self;
	}
	public void setSelf(OrganizationDTO self) {
		this.self = self;
	}	
	public Long getHierarchyVersionId() {
		return hierarchyVersionId;
	}
	public void setHierarchyVersionId(Long hierarchyVersionId) {
		this.hierarchyVersionId = hierarchyVersionId;
	}
	
	@Override
	public String toString() {
		return "HierarchyElementDTO [parentId=" + parentId + ", self=" + self.toString()
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((self == null) ? 0 : self.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HierarchyElement other = (HierarchyElement) obj;
		if (self == null) {
			if (other.self != null)
				return false;
		} else if (!self.equals(other.self))
			return false;
		return true;
	}
	
}
