package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.Menu;

public class MenuDto extends Menu implements Traversable {

	private static final long serialVersionUID = 1L;
	private List<MenuDetailDto> menuDetailDto;
	private String userName;
	
	@Override
	public Long getId() {
		return super.getId();
	}

	public List<MenuDetailDto> getMenuDetailDto() {
		return menuDetailDto;
	}

	public void setMenuDetailDto(List<MenuDetailDto> menuDetailDto) {
		this.menuDetailDto = menuDetailDto;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
