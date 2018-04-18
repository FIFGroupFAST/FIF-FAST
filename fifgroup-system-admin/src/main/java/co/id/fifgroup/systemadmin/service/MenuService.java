package co.id.fifgroup.systemadmin.service;

import java.util.List;

import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.systemadmin.domain.Menu;
import co.id.fifgroup.systemadmin.domain.MenuDetail;
import co.id.fifgroup.systemadmin.domain.MenuExample;
import co.id.fifgroup.systemadmin.domain.MenuItem;
import co.id.fifgroup.systemadmin.dto.MenuDto;

public interface MenuService {
	
	public void save(MenuDto menuDto) throws Exception;
	
	public List<Menu> getMenuByExample(MenuExample example);
	
	public List<Menu> getMenuName();
	
	public List<Menu> selectByExample(MenuExample example, int limit, int offset);
	
	public List<MenuDto> selectMenuByMenu(Menu menu, int limit, int offset);
	
	public List<MenuDto> selectMenuByMenu(Menu menu);
	
	public MenuDto getMenuById(Long id);
	
	public List<MenuItem> getMenuItemByExample(MenuItem example, MenuItemType type);
	
	public List<MenuItem> getMenuItemByExample(MenuItem example, MenuItemType type, int limit, int offset);
	
	public int countMenuItemByExample(MenuItem example, MenuItemType type);
	
	public int countByExample(MenuExample example);
	
	public List<MenuDetail> getMenuDetailByMenuId(Long menuId);
	
}
