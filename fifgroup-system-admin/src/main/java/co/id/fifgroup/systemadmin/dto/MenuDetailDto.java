package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.MenuDetail;

public class MenuDetailDto extends MenuDetail implements Traversable {
	
	private static final long serialVersionUID = 1L;
	private String itemName;
	
	@Override
	public Long getId() {
		return super.getId();
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
}
