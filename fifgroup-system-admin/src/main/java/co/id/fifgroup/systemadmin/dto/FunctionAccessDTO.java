package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.systemadmin.domain.FunctionAccess;

public class FunctionAccessDTO extends FunctionAccess {
	
	private static final long serialVersionUID = 1L;
	private Long itemId;
	private String itemType;
	private String itemName;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
