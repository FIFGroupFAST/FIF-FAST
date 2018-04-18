package co.id.fifgroup.core.ui;

import java.io.Serializable;

public class AuthorizedMenuDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long actionId;
	private String actionUrl;
	private String menuItemType;
	private int sequence;
	private String displayName;
	private String itemName;
	private Long moduleId;
	private String userManual;
	private String moduleName;
	
	public AuthorizedMenuDTO() {};
	
	public AuthorizedMenuDTO(Long actionId, String actionUrl,
			String menuItemType, int sequence, String displayName,
			String itemName, Long moduleId, String userManual, String moduleName) {
		super();
		this.actionId = actionId;
		this.actionUrl = actionUrl;
		this.menuItemType = menuItemType;
		this.sequence = sequence;
		this.displayName = displayName;
		this.itemName = itemName;
		this.moduleId = moduleId;
		this.userManual = userManual;
		this.moduleName = moduleName;
	}
	
	public String getMenuItemType() {
		return menuItemType;
	}
	public void setMenuItemType(String menuItemType) {
		this.menuItemType = menuItemType;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public String getUserManual() {
		return userManual;
	}
	public void setUserManual(String userManual) {
		this.userManual = userManual;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	
	
}
