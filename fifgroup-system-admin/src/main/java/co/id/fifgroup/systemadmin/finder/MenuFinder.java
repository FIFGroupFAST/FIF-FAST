package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.Menu;
import co.id.fifgroup.systemadmin.domain.MenuItem;
import co.id.fifgroup.systemadmin.dto.MenuDto;

public interface MenuFinder {
	
	MenuDto selectByPrimaryKey(Long id);
	
	List<MenuItem> selectFunctionByExample(MenuItem example);
	
	int countFunctionByExample(MenuItem example);
	
	List<MenuItem> selectFunctionByExample(MenuItem example, RowBounds rowBounds);
	
	List<MenuItem> selectMenuByExample(MenuItem example);
	
	int countMenuByExample(MenuItem example);
	
	List<MenuItem> selectMenuByExample(MenuItem example, RowBounds rowBounds);
	
	List<MenuDto> selectMenuByMenu(Menu menu, RowBounds rowBounds);
	
	List<MenuDto> selectMenuByMenu(Menu menu);
	
	List<Menu> getMenuName();
	
}
