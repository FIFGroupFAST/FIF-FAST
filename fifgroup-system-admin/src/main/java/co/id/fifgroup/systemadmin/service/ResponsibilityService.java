package co.id.fifgroup.systemadmin.service;

import java.util.List;

import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.systemadmin.domain.FieldPermission;
import co.id.fifgroup.systemadmin.domain.FieldPermissionExample;
import co.id.fifgroup.systemadmin.domain.ItemExclusionExample;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.domain.ResponsibilityCompany;
import co.id.fifgroup.systemadmin.domain.ResponsibilityCompanyExample;
import co.id.fifgroup.systemadmin.domain.ResponsibilityExample;
import co.id.fifgroup.systemadmin.domain.SpecialPermission;
import co.id.fifgroup.systemadmin.domain.SpecialPermissionExample;
import co.id.fifgroup.systemadmin.dto.FunctionAccessDTO;
import co.id.fifgroup.systemadmin.dto.ItemExclusionDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityDTO;

public interface ResponsibilityService {

	public int countResponsibilityByExample(ResponsibilityExample example);
	
	public int countResponsibilityAndMenuByParameter(ResponsibilityDTO responsibilityDto);
	
	public List<Responsibility> getResponsibilityByExample(ResponsibilityExample example);
	
	public List<Responsibility> getResponsibilityName();
	
	public List<Responsibility> selectByExampleWithRowbounds(ResponsibilityExample example, int limit, int offset);
	
	public ResponsibilityDTO getResponsibilityByResponsibilityId(Long id);
	
	public void save(ResponsibilityDTO responsibilityDto) throws Exception;
	
	public boolean validateResponsibility(Responsibility responsibility);
	
	public List<ResponsibilityDTO> selectResponsibilityAndMenuByParameter(ResponsibilityDTO responsibilityDto);
	
	public List<ResponsibilityDTO> selectResponsibilityAndMenuByParameterWithRowbounds(ResponsibilityDTO responsibilityDto, int limit, int offset);
	
	public List<ItemExclusionDTO> findFunctionAndSubmenuByRootMenuId(Long menuId, String itemName, MenuItemType type);
	
	public int countItemExclusionByExample(ItemExclusionExample example);
	
	public int countResponsibilityCompanyByExample(ResponsibilityCompanyExample example);
	
	public int countFieldPermissionByExample(FieldPermissionExample example);
	
	public List<FieldPermission> getFieldPermissionByExample(FieldPermissionExample example);
	
	public FieldPermission getFieldPermissionById(Long id);
	
	public List<SpecialPermission> getSpecialPermissionByExample(SpecialPermissionExample example, int limit, int offset);
	
	public List<FunctionAccessDTO> findFunctionAndSubmenuByNotInActionIdUseItemExclusion(ResponsibilityDTO responsibilityDto);
	
	public List<FunctionAccessDTO> findFunctionAndSubmenuByNotInActionIdNotUseItemExclusion(ResponsibilityDTO responsibilityDto);
	
	public void validateResponsibility(ResponsibilityDTO responsibilityDto) throws ValidationException;
	
	public Responsibility selectByPrimaryKey(Long responsibilityId);
	
	public ResponsibilityCompany selectResponsibilityCompanyByPrimaryKey(Long id);
	
	public int countFunctionAndSubmenuByRootMenuId(Long menuId, String itemName, MenuItemType type);
	
	public List<FunctionAccessDTO> getFunctionsAccessByMenuId(Long menuId, Long respId, String type);
	
	public int countSpecialPermissionByExample(SpecialPermissionExample example);
}
