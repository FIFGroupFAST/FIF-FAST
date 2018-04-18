package co.id.fifgroup.core.security;

import java.io.Serializable;
import java.util.List;

public class Security implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long responsibilityId;
	private String responsibilityName;
	private Long menuId;
	private Long businessGroupId;
	private Long taskGroupId;
	private String functionDefaultAccess;
	private String securityType;
	private String orgFilter;
	private boolean recursive;
	private Long organizationLevelId;
	private SecurityFilter securityFilter;
	private List<String> fieldPermissions;
	private List<Long> menuItemExclusions;
	private List<Long> functionItemExclusions;
	private List<FunctionPermission> functionPermissions;
	
	public Long getResponsibilityId() {
		return responsibilityId;
	}
	public void setResponsibilityId(Long responsibilityId) {
		this.responsibilityId = responsibilityId;
	}
	public String getResponsibilityName() {
		return responsibilityName;
	}
	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getBusinessGroupId() {
		return businessGroupId;
	}
	public void setBusinessGroupId(Long businessGroupId) {
		this.businessGroupId = businessGroupId;
	}
	public String getFunctionDefaultAccess() {
		return functionDefaultAccess;
	}
	public void setFunctionDefaultAccess(String functionDefaultAccess) {
		this.functionDefaultAccess = functionDefaultAccess;
	}
	public String getSecurityType() {
		return securityType;
	}
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}
	public boolean isRecursive() {
		return recursive;
	}
	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}
	public Long getOrganizationLevelId() {
		return organizationLevelId;
	}
	public void setOrganizationLevelId(Long organizationLevelId) {
		this.organizationLevelId = organizationLevelId;
	}
	public Long getTaskGroupId() {
		return taskGroupId;
	}
	public void setTaskGroupId(Long taskGroupId) {
		this.taskGroupId = taskGroupId;
	}
	public List<String> getFieldPermissions() {
		return fieldPermissions;
	}
	public void setFieldPermissions(List<String> fieldPermissions) {
		this.fieldPermissions = fieldPermissions;
	}

	public List<Long> getMenuItemExclusions() {
		return menuItemExclusions;
	}
	public void setMenuItemExclusions(List<Long> menuItemExclusions) {
		this.menuItemExclusions = menuItemExclusions;
	}
	public List<Long> getFunctionItemExclusions() {
		return functionItemExclusions;
	}
	public void setFunctionItemExclusions(List<Long> functionItemExclusions) {
		this.functionItemExclusions = functionItemExclusions;
	}
	public String getOrgFilter() {
		return orgFilter;
	}
	public void setOrgFilter(String orgFilter) {
		this.orgFilter = orgFilter;
	}
	public SecurityFilter getSecurityFilter() {
		return securityFilter;
	}
	public void setSecurityFilter(SecurityFilter securityFilter) {
		this.securityFilter = securityFilter;
	}
	public List<FunctionPermission> getFunctionPermissions() {
		return functionPermissions;
	}
	public void setFunctionPermissions(List<FunctionPermission> functionPermissions) {
		this.functionPermissions = functionPermissions;
	}
	
	public boolean hasPermission(String permissionName) {
		return fieldPermissions.contains(permissionName);
	}
	
	@Override
	public String toString() {
		return "Security [responsibilityId=" + responsibilityId
				+ ", responsibilityName=" + responsibilityName + ", menuId="
				+ menuId + ", businessGroupId=" + businessGroupId
				+ ", taskGroupId=" + taskGroupId + ", functionDefaultAccess="
				+ functionDefaultAccess + ", securityType=" + securityType
				+ ", orgFilter=" + orgFilter + ", recursive=" + recursive
				+ ", organizationLevelId=" + organizationLevelId
				+ ", securityFilter=" + securityFilter + ", fieldPermissions="
				+ fieldPermissions + ", menuItemExclusions="
				+ menuItemExclusions + ", functionItemExclusions="
				+ functionItemExclusions + ", functionPermissions="
				+ functionPermissions + "]";
	}
}
