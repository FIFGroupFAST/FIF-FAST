package co.id.fifgroup.systemadmin.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;

import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.systemadmin.dao.MenuDetailMapper;
import co.id.fifgroup.systemadmin.dao.MenuMapper;
import co.id.fifgroup.systemadmin.domain.Menu;
import co.id.fifgroup.systemadmin.domain.MenuDetail;
import co.id.fifgroup.systemadmin.domain.MenuDetailExample;
import co.id.fifgroup.systemadmin.domain.MenuExample;
import co.id.fifgroup.systemadmin.domain.MenuItem;
import co.id.fifgroup.systemadmin.dto.MenuDetailDto;
import co.id.fifgroup.systemadmin.dto.MenuDto;
import co.id.fifgroup.systemadmin.finder.MenuFinder;
import co.id.fifgroup.systemadmin.service.MenuService;

@Transactional
@Service("menuService")
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private MenuDetailMapper menuDetailMapper;
	
	@Autowired
	private MenuFinder menuFinder;
	
	@Override
	@Transactional
	public void save(MenuDto menuDto) throws Exception{
		if(menuDto.getId() == null) {
			menuMapper.insert(menuDto);
			for (MenuDetailDto menuDetailDto : menuDto.getMenuDetailDto()) {
				menuDetailDto.setMenuId(menuDto.getId());
				menuDetailDto.setCreatedBy(menuDto.getCreatedBy());
				menuDetailDto.setCreationDate(menuDto.getCreationDate());
				menuDetailDto.setLastUpdateDate(menuDto.getLastUpdateDate());
				menuDetailDto.setLastUpdatedBy(menuDto.getLastUpdatedBy());
				if(menuDetailDto.getDateTo() == null) {
					menuDetailDto.setDateTo(DateUtil.MAX_DATE);
				}
				
				menuDetailMapper.insert(menuDetailDto);
			}
		} else {
			menuMapper.updateByPrimaryKeySelective(menuDto);
			for (MenuDetailDto menuDetailDto : menuDto.getMenuDetailDto()) {
				menuDetailDto.setMenuId(menuDto.getId());
				menuDetailDto.setCreatedBy(menuDto.getCreatedBy());
				menuDetailDto.setCreationDate(menuDto.getCreationDate());
				menuDetailDto.setLastUpdateDate(menuDto.getLastUpdateDate());
				menuDetailDto.setLastUpdatedBy(menuDto.getLastUpdatedBy());
				if(menuDetailDto.getDateTo() == null) {
					menuDetailDto.setDateTo(DateUtil.MAX_DATE);
				}
				
				if(menuDetailDto.getId() != null) {					
					menuDetailMapper.updateByPrimaryKeySelective(menuDetailDto);
				} else {
					menuDetailMapper.insert(menuDetailDto);
				}
			}
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Menu> getMenuByExample(MenuExample example) {
		example.setOrderByClause("MENU_NAME");
		return menuMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Menu> selectByExample(MenuExample example, int limit, int offset) {
		return menuMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly=true)
	public MenuDto getMenuById(Long id) {	
		return menuFinder.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<MenuItem> getMenuItemByExample(MenuItem example, MenuItemType type) {
		if(type.equals(MenuItemType.FUNCTION)) {
			return menuFinder.selectFunctionByExample(example);
		} else {
			return menuFinder.selectMenuByExample(example);
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public int countByExample(MenuExample example) {
		return menuMapper.countByExample(example);
	}


	@Override
	@Transactional(readOnly=true)
	public List<MenuDto> selectMenuByMenu(Menu menu, int limit, int offset) {
		return menuFinder.selectMenuByMenu(menu, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public List<Menu> getMenuName() {
		return menuFinder.getMenuName();
	}

	@Override
	public List<MenuDetail> getMenuDetailByMenuId(Long menuId) {
		MenuDetailExample example = new MenuDetailExample();
		example.createCriteria().andMenuIdEqualTo(menuId);
		return menuDetailMapper.selectByExample(example);
	}

	@Override
	public List<MenuItem> getMenuItemByExample(MenuItem example, MenuItemType type, int limit, int offset) {
		if(type.equals(MenuItemType.FUNCTION)) {
			return menuFinder.selectFunctionByExample(example, new RowBounds(offset, limit));
		} else {
			return menuFinder.selectMenuByExample(example, new RowBounds(offset, limit));
		}
	}

	@Override
	public int countMenuItemByExample(MenuItem example, MenuItemType type) {
		if(type.equals(MenuItemType.FUNCTION)) {
			return menuFinder.countFunctionByExample(example);
		} else {
			return menuFinder.countMenuByExample(example);
		}
	}

	@Override
	public List<MenuDto> selectMenuByMenu(Menu menu) {
		return menuFinder.selectMenuByMenu(menu);
	}
}
