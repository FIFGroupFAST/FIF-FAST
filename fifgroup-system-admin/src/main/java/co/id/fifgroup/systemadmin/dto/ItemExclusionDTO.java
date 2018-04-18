package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.ItemExclusion;
import co.id.fifgroup.systemadmin.domain.MenuItem;

public class ItemExclusionDTO extends ItemExclusion implements Traversable {
	
	private static final long serialVersionUID = 1L;	
	private MenuItem menuItem;
	
	@Override
	public Long getId() {
		return super.getId();
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
}
