package co.id.fifgroup.systemadmin.domain;

import java.io.Serializable;

public class MenuItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long actionId;
	private String itemName;
	private String itemType;
	
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}	
}
