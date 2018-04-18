package co.id.fifgroup.workstructure.domain;

import java.util.ArrayList;
import java.util.List;

import co.id.fifgroup.workstructure.dto.OrganizationDTO;


public class OrganizationNode {
	
	private List<OrganizationNode> children = new ArrayList<>();
	private OrganizationDTO data;
	private OrganizationNode parent;
	private boolean isOpen = true;
	
	public List<OrganizationNode> getChildren() {
		return children;
	}
	public void setChildren(List<OrganizationNode> children) {
		this.children = children;
	}
	public OrganizationDTO getData() {
		return data;
	}
	public void setData(OrganizationDTO data) {
		this.data = data;
	}
	public OrganizationNode getParent() {
		return parent;
	}
	public void setParent(OrganizationNode parent) {
		this.parent = parent;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public boolean isLeaf() {
		if(children.size() == 0)
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Organization Node [")
			.append(getData().getId()).append(", ")
			.append(getData().getName())
			.append("]\n");
		for (OrganizationNode child : getChildren()) {
			sb.append(child.toString());
		}		
		return sb.toString();
	}
	
	
	
}
