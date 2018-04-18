package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.FieldPermission;
import co.id.fifgroup.systemadmin.domain.FunctionAccess;
import co.id.fifgroup.systemadmin.domain.Menu;
import co.id.fifgroup.systemadmin.domain.Responsibility;

public class ResponsibilityDTO extends Responsibility implements Traversable{
	
	private static final long serialVersionUID = 1L;
	private Menu menu;
	private List<ItemExclusionDTO> itemExclusions;
	private List<FunctionAccess> customFunctionAccesss;
	private List<FieldPermission> fieldPermissions;
	private List<ResponsibilityCompanyDTO> responsibilityCompanyDto;
	private String userName;
	private String taskGroupName;
	
	@Override
	public Long getId() {
		return super.getId();
	}
	
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public List<ItemExclusionDTO> getItemExclusions() {
		return itemExclusions;
	}
	public void setItemExclusions(List<ItemExclusionDTO> itemExclusions) {
		this.itemExclusions = itemExclusions;
	}
	public List<FunctionAccess> getCustomFunctionAccesss() {
		return customFunctionAccesss;
	}
	public void setCustomFunctionAccesss(List<FunctionAccess> customFunctionAccesss) {
		this.customFunctionAccesss = customFunctionAccesss;
	}
	public List<FieldPermission> getFieldPermissions() {
		return fieldPermissions;
	}
	public void setFieldPermissions(List<FieldPermission> fieldPermissions) {
		this.fieldPermissions = fieldPermissions;
	}
	public List<ResponsibilityCompanyDTO> getResponsibilityCompanyDto() {
		return responsibilityCompanyDto;
	}
	public void setResponsibilityCompanyDto(List<ResponsibilityCompanyDTO> responsibilityCompanyDto) {
		this.responsibilityCompanyDto = responsibilityCompanyDto;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTaskGroupName() {
		return taskGroupName;
	}
	public void setTaskGroupName(String taskGroupName) {
		this.taskGroupName = taskGroupName;
	}
}
