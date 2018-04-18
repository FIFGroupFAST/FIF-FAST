package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.domain.ResponsibilityExample;
import co.id.fifgroup.systemadmin.dto.FunctionAccessDTO;
import co.id.fifgroup.systemadmin.dto.GradeExclusionDTO;
import co.id.fifgroup.systemadmin.dto.IncludedOrganizationDTO;
import co.id.fifgroup.systemadmin.dto.ItemExclusionDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityCompanyDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityDTO;

public interface ResponsibilityFinder {
	
	public List<ResponsibilityDTO> selectResponsibilityAndMenuByParameter(ResponsibilityDTO responsibilityDto);
	
	public List<ResponsibilityDTO> selectResponsibilityAndMenuByParameterWithRowbounds(ResponsibilityDTO responsibilityDto, RowBounds rowBounds);
	
	public int countResponsibilityAndMenuByParameter(ResponsibilityDTO responsibilityDto);
	
	public List<ItemExclusionDTO> findFunctionByRootMenuId(@Param("menuId") Long menuId, @Param("itemName") String itemName);
	
	public List<ItemExclusionDTO> findSubmenuByRootMenuId(@Param("menuId") Long menuId, @Param("itemName") String itemName);
	
	public List<ResponsibilityCompanyDTO> getResponsibilityCompanyByResponsibilityId(Long id);
	
	public List<ItemExclusionDTO> getItemExclusionAndItemName(Long id);
	
	public List<FunctionAccessDTO> findFunctionAndSubmenuByNotInActionIdUseItemExclusion(ResponsibilityDTO responsibilityDto);
	
	public List<FunctionAccessDTO> findFunctionAndSubmenuByNotInActionIdNotUseItemExclusion(ResponsibilityDTO responsibilityDto);
	
	public List<KeyValue> findResponsibilityKeyValue();
	
	public KeyValue findResponsibilityKeyValueById(Long id);
	
	public List<GradeExclusionDTO> getGradeExclusionByAssignment(ResponsibilityCompanyDTO responsibilityCompanyDTO);
	
	public List<GradeExclusionDTO> getGradeExclusionByMultiCompany(ResponsibilityCompanyDTO responsibilityCompanyDTO);
	
	public List<IncludedOrganizationDTO> getIncludedOrganizationByAssignment(ResponsibilityCompanyDTO responsibilityCompanyDTO);
	
	public List<IncludedOrganizationDTO> getIncludedOrganizationByMultiCompany(ResponsibilityCompanyDTO responsibilityCompanyDTO);
	
	public void deleteGradeExclusionAssignmentByRespCompanyId(Long Id);
	
	public void deleteIncludedOrganizationAssignmentByRespCompanyId(Long Id);
	
	public void deleteGradeExclusionMultiCompanyByRespCompanyId(Long Id);
	
	public void deleteIncludedOrganizationMultiCompanyByRespCompanyId(Long Id);	
	
	public List<Responsibility> getResponsibilityName();
	
	public void deleteItemExclusionByResponsibilityId(@Param("responsibilityId") Long responsibilityId);
	
	public void deleteFieldPermissionByResponsibilityId(@Param("responsibilityId") Long responsibilityId);
	
	public void deleteFunctionAccessByResponsibilityId(@Param("responsibilityId") Long responsibilityId);
	
	public int countFunctionByRootMenuId(@Param("menuId") Long menuId, @Param("itemName") String itemName);
	
	public int countSubmenuByRootMenuId(@Param("menuId") Long menuId, @Param("itemName") String itemName);
	
	public void deleteIncludedOrganizationByResponsibilityId(Long respId);
	
	public void deleteGradeExclusionByREsponsibilityId(Long respId);
	
	public List<FunctionAccessDTO> getFunctionAccessByMenuAndRespId(@Param("menuId")Long menuId, @Param("respId")Long respId);
	
	public List<FunctionAccessDTO> getFunctionAccessByMenuId(Long menuId);
	
}
