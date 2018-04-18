package co.id.fifgroup.systemadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.validation.ValidationException;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.lowagie.text.pdf.AcroFields.Item;

import co.id.fifgroup.core.constant.FunctionAccessType;
import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.core.service.ResponsibilityServiceAdapter;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.systemadmin.constant.SecurityCompanyType;
import co.id.fifgroup.systemadmin.dao.FieldPermissionMapper;
import co.id.fifgroup.systemadmin.dao.FunctionAccessMapper;
import co.id.fifgroup.systemadmin.dao.GradeExclusionMapper;
import co.id.fifgroup.systemadmin.dao.IncludedOrganizationMapper;
import co.id.fifgroup.systemadmin.dao.ItemExclusionMapper;
import co.id.fifgroup.systemadmin.dao.MenuMapper;
import co.id.fifgroup.systemadmin.dao.ResponsibilityCompanyMapper;
import co.id.fifgroup.systemadmin.dao.ResponsibilityMapper;
import co.id.fifgroup.systemadmin.dao.SpecialPermissionMapper;
import co.id.fifgroup.systemadmin.dao.TaskGroupMapper;
import co.id.fifgroup.systemadmin.domain.FieldPermission;
import co.id.fifgroup.systemadmin.domain.FieldPermissionExample;
import co.id.fifgroup.systemadmin.domain.FunctionAccess;
import co.id.fifgroup.systemadmin.domain.ItemExclusionExample;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.domain.ResponsibilityCompany;
import co.id.fifgroup.systemadmin.domain.ResponsibilityCompanyExample;
import co.id.fifgroup.systemadmin.domain.ResponsibilityExample;
import co.id.fifgroup.systemadmin.domain.SpecialPermission;
import co.id.fifgroup.systemadmin.domain.SpecialPermissionExample;
import co.id.fifgroup.systemadmin.dto.FunctionAccessDTO;
import co.id.fifgroup.systemadmin.dto.GradeExclusionDTO;
import co.id.fifgroup.systemadmin.dto.IncludedOrganizationDTO;
import co.id.fifgroup.systemadmin.dto.ItemExclusionDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityCompanyDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityDTO;
import co.id.fifgroup.systemadmin.finder.ResponsibilityFinder;
import co.id.fifgroup.systemadmin.service.ResponsibilityService;
import co.id.fifgroup.systemadmin.validation.ResponsibilityValidator;

@Transactional
@Service("responsibilityService")
public class ResponsibilityServiceImpl implements ResponsibilityService, ResponsibilityServiceAdapter {
	
	//Responsibility entity
	@Autowired
	private ResponsibilityMapper responsibilityMapper;	
	@Autowired
	private ResponsibilityCompanyMapper responsibilityCompanyMapper;
	@Autowired
	private ItemExclusionMapper itemExclusionMapper;
	@Autowired
	private GradeExclusionMapper gradeExclusionMapper;
	@Autowired
	private IncludedOrganizationMapper includedOrganizationMapper;
	@Autowired
	private FieldPermissionMapper fieldPermissionMapper;
	@Autowired
	private SpecialPermissionMapper specialPermissionMapper;
	@Autowired
	private FunctionAccessMapper functionAccessMapper;
	@Autowired
	private TaskGroupMapper taskGroupMapper;
	
	//Responsibility Finder
	@Autowired
	private ResponsibilityFinder responsibilityFinder;
	
	//Other module entity
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private ModelMapper modelMapper;

	
	@Override
	@Transactional(readOnly=true)
	public int countResponsibilityByExample(ResponsibilityExample example) {
		return responsibilityMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int countResponsibilityAndMenuByParameter(ResponsibilityDTO responsibilityDto) {
		return responsibilityFinder.countResponsibilityAndMenuByParameter(responsibilityDto);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Responsibility> getResponsibilityByExample(ResponsibilityExample example) {
		return responsibilityMapper.selectByExample(example);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Responsibility> selectByExampleWithRowbounds(ResponsibilityExample example, int limit, int offset) {
		return responsibilityMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public ResponsibilityDTO getResponsibilityByResponsibilityId(Long id) {
		List<ResponsibilityCompanyDTO> companyList = new ArrayList<ResponsibilityCompanyDTO>();
		List<GradeExclusionDTO> gradeExclusionDTOAssignmentList = new ArrayList<GradeExclusionDTO>();
		List<IncludedOrganizationDTO> includedOrganizationDTOByAssignmentList = new ArrayList<IncludedOrganizationDTO>();
		List<GradeExclusionDTO> gradeExclusionDTOMultiCompanyList = new ArrayList<GradeExclusionDTO>();
		List<IncludedOrganizationDTO> includedOrganizationDTOByMultiCompanyList = new ArrayList<IncludedOrganizationDTO>();
				
		ResponsibilityDTO respDto = new ResponsibilityDTO();
		Responsibility resp = responsibilityMapper.selectByPrimaryKey(id);
		respDto = modelMapper.map(resp, ResponsibilityDTO.class);
		respDto.setId(resp.getId());
		if(resp != null) {
			respDto.setTaskGroupId(resp.getTaskGroupId());
			if(resp.getTaskGroupId() != null)
				respDto.setTaskGroupName(taskGroupMapper.selectByPrimaryKey(resp.getTaskGroupId()).getTaskGroupName());
			respDto.setMenu(menuMapper.selectByPrimaryKey(resp.getMenuId()));
		}
		respDto.setItemExclusions(responsibilityFinder.getItemExclusionAndItemName(id));
			
		companyList = responsibilityFinder.getResponsibilityCompanyByResponsibilityId(id);
		if(companyList != null) {
			for (ResponsibilityCompanyDTO responsibilityCompanyDTO : companyList) {
				responsibilityCompanyDTO.getId();
				gradeExclusionDTOAssignmentList = responsibilityFinder.getGradeExclusionByAssignment(responsibilityCompanyDTO);
				responsibilityCompanyDTO.setGradeExclusionByAssignments(gradeExclusionDTOAssignmentList);
				
				includedOrganizationDTOByAssignmentList = responsibilityFinder.getIncludedOrganizationByAssignment(responsibilityCompanyDTO);
				responsibilityCompanyDTO.setIncludedOrganizationByAssignments(includedOrganizationDTOByAssignmentList);
				
				includedOrganizationDTOByMultiCompanyList = responsibilityFinder.getIncludedOrganizationByMultiCompany(responsibilityCompanyDTO);
				responsibilityCompanyDTO.setIncludedOrganizationMultiCompanys(includedOrganizationDTOByMultiCompanyList);
				
				gradeExclusionDTOMultiCompanyList = responsibilityFinder.getGradeExclusionByMultiCompany(responsibilityCompanyDTO);
				responsibilityCompanyDTO.setGradeExclusionMultiCompanys(gradeExclusionDTOMultiCompanyList);
			}
			
			respDto.setResponsibilityCompanyDto(companyList);
		}	

		FieldPermissionExample fieldPermissionExample = new FieldPermissionExample();
		fieldPermissionExample.createCriteria().andResponsibilityIdEqualTo(id);
		List<FieldPermission> fieldPermission = fieldPermissionMapper.selectByExample(fieldPermissionExample);
		if(fieldPermission != null) {
			respDto.setFieldPermissions(fieldPermission);
		}
		
		return respDto;
	}

	@Override
	@Transactional
	public void save(ResponsibilityDTO responsibilityDto) throws Exception {
		
		if(responsibilityDto.getId() == null){
			//Insert
			responsibilityMapper.insert(responsibilityDto);
			insertResponsibilityDetail(responsibilityDto);

		}else{
			//Update
			responsibilityMapper.updateByPrimaryKey(responsibilityDto);
			
			responsibilityFinder.deleteItemExclusionByResponsibilityId(responsibilityDto.getId());
			responsibilityFinder.deleteFieldPermissionByResponsibilityId(responsibilityDto.getId());
			responsibilityFinder.deleteFunctionAccessByResponsibilityId(responsibilityDto.getId());
			
			//Delete Responsibility Company
			responsibilityFinder.deleteIncludedOrganizationByResponsibilityId(responsibilityDto.getId());
			responsibilityFinder.deleteGradeExclusionByREsponsibilityId(responsibilityDto.getId());
			for(ResponsibilityCompanyDTO responsibilityCompanyDto : responsibilityDto.getResponsibilityCompanyDto())
				responsibilityCompanyMapper.deleteByPrimaryKey(responsibilityCompanyDto.getId());
			
			//Insert Detail
			insertResponsibilityDetail(responsibilityDto);
			
		}
	}
	
	private void insertResponsibilityDetail(ResponsibilityDTO responsibilityDto){
		if(responsibilityDto.getItemExclusions() != null && responsibilityDto.getItemExclusions().size() > 0){
			for (ItemExclusionDTO itemExc : responsibilityDto.getItemExclusions()) {
				itemExc.setResponsibilityId(responsibilityDto.getId());
				itemExclusionMapper.insert(itemExc);
			}
		}
		
		if(responsibilityDto.getResponsibilityCompanyDto() != null && responsibilityDto.getResponsibilityCompanyDto().size() > 0){
			
			for (ResponsibilityCompanyDTO respCo : responsibilityDto.getResponsibilityCompanyDto()) {
				respCo.setResponsibilityId(responsibilityDto.getId());
				responsibilityCompanyMapper.insert(respCo);
				
				if(respCo.getGradeExclusionByAssignments() != null) {
					for(GradeExclusionDTO gradeExclusionDTOAssignment : respCo.getGradeExclusionByAssignments()) {
						gradeExclusionDTOAssignment.setRespCompanyId(respCo.getId());						
						gradeExclusionDTOAssignment.setSecurityCompanyType(SecurityCompanyType.ASSIGNMENT.toString());
						if(gradeExclusionDTOAssignment.getGradeId() != null) {
							gradeExclusionMapper.insert(gradeExclusionDTOAssignment);
						}
					}
				}
				
				if(respCo.getIncludedOrganizationByAssignments() != null) {
					for(IncludedOrganizationDTO includedOrganizationDTOAssignment : respCo.getIncludedOrganizationByAssignments()) {
						includedOrganizationDTOAssignment.setRespCompanyId(respCo.getId());
						includedOrganizationDTOAssignment.setSecurityCompanyType(SecurityCompanyType.ASSIGNMENT.toString());
						if(includedOrganizationDTOAssignment.getOrganizationId() != null) {
							includedOrganizationMapper.insert(includedOrganizationDTOAssignment);
						}
					}
				}
				
				if(respCo.getGradeExclusionMultiCompanys() != null) {
					for(GradeExclusionDTO gradeExclusionDTOMultiCompany : respCo.getGradeExclusionMultiCompanys()) {
						if(gradeExclusionDTOMultiCompany.getGradeId() != null) {
							gradeExclusionDTOMultiCompany.setRespCompanyId(respCo.getId());
							gradeExclusionDTOMultiCompany.setSecurityCompanyType(SecurityCompanyType.MULTICOY.toString());
							gradeExclusionMapper.insert(gradeExclusionDTOMultiCompany);
						}
					}
				}
				
				if(respCo.getIncludedOrganizationMultiCompanys() != null) {
					for(IncludedOrganizationDTO includedOrganizationDTOMultiCompany : respCo.getIncludedOrganizationMultiCompanys()) {
						if(includedOrganizationDTOMultiCompany.getOrganizationId() != null) {
							includedOrganizationDTOMultiCompany.setRespCompanyId(respCo.getId());
							includedOrganizationDTOMultiCompany.setSecurityCompanyType(SecurityCompanyType.MULTICOY.toString());
							includedOrganizationMapper.insert(includedOrganizationDTOMultiCompany);
						}
					}
				}
				
			}
			
		}
		
		if(responsibilityDto.getFieldPermissions() != null && responsibilityDto.getFieldPermissions().size() > 0){
			for(FieldPermission fieldPermission : responsibilityDto.getFieldPermissions()) {
				fieldPermission.setResponsibilityId(responsibilityDto.getId());
				if(!fieldPermission.getPermissionName().equals(""))
					fieldPermissionMapper.insert(fieldPermission);
			}
		}
		
		if(responsibilityDto.getFunctionDefaultAccess().equals(FunctionAccessType.CUSTOM.getValue())){
			if(responsibilityDto.getCustomFunctionAccesss() != null) {
				for(FunctionAccess functionAccess : responsibilityDto.getCustomFunctionAccesss()) {
					if(functionAccess.getFunctionId() != null) {
						functionAccess.setResponsibilityId(responsibilityDto.getId());
						functionAccessMapper.insert(functionAccess);
					}
				}
			}
		}	
	}

	@Override
	@Transactional(readOnly=true)
	public boolean validateResponsibility(Responsibility responsibility) {
		return false;
	}

	@Override
	@Transactional(readOnly=true)
	public List<ItemExclusionDTO> findFunctionAndSubmenuByRootMenuId(Long menuId, String itemName, MenuItemType type) {
		List<ItemExclusionDTO> menuItemData = null;
		if(type.toString().trim().equals(MenuItemType.FUNCTION.toString().trim())) {
			//menuItemData = responsibilityFinder.findFunctionByRootMenuId(menuId, itemName);
			List<ItemExclusionDTO> functionLists = new ArrayList<ItemExclusionDTO>();
			menuItemData = responsibilityFinder.findFunctionByRootMenuId(menuId, "%%");
			menuItemData.addAll(loadFnction(functionLists, menuId, "%%"));
		} else if(type.toString().trim().equals(MenuItemType.SUBMENU.toString().trim())) {
			//menuItemData = responsibilityFinder.findSubmenuByRootMenuId(menuId, itemName);		
			List<ItemExclusionDTO> submenuLists = new ArrayList<ItemExclusionDTO>();
			menuItemData = loadSubmenu(submenuLists, menuId, "%%");
		}
		if(itemName.equals("%%"))
			return menuItemData;
		else
			return filterByKey(menuItemData, itemName);
	}
	
	private List<ItemExclusionDTO> filterByKey(List<ItemExclusionDTO> items, final String key){
		final String key2 = key.replace("%", "");
		List<ItemExclusionDTO> filtered = Lists.newArrayList(Collections2.filter(items, new Predicate<ItemExclusionDTO>() {
			@Override
			public boolean apply(ItemExclusionDTO data){
				return data.getMenuItem().getItemName().toUpperCase().contains(key2);
			}
		}));
		return filtered;
	}
	
	private List<ItemExclusionDTO> loadSubmenu(List<ItemExclusionDTO> submenuLists, Long menuId, String itemName){
		List<ItemExclusionDTO> list = responsibilityFinder.findSubmenuByRootMenuId(menuId, itemName);	
		for (ItemExclusionDTO itemExclusionDTO : list) {
			loadSubmenu(submenuLists, itemExclusionDTO.getActionId(), itemName);
			submenuLists.add(itemExclusionDTO);
		}
		return submenuLists;
	}
	
	private List<ItemExclusionDTO> loadFnction(List<ItemExclusionDTO> functionLists, Long menuId, String itemName){
		List<ItemExclusionDTO> list = responsibilityFinder.findSubmenuByRootMenuId(menuId, itemName);	
		for (ItemExclusionDTO itemExclusionDTO : list) {
			loadFnction(functionLists, itemExclusionDTO.getActionId(), itemName);
			functionLists.addAll(responsibilityFinder.findFunctionByRootMenuId(itemExclusionDTO.getActionId(), itemName));
		}
		return functionLists;
	}
	
	@Override
	@Transactional(readOnly=true)
	public int countFieldPermissionByExample(FieldPermissionExample example) {
		return fieldPermissionMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<FieldPermission> getFieldPermissionByExample(FieldPermissionExample example) {
		return fieldPermissionMapper.selectByExample(example);
	}
	
	@Override
	@Transactional(readOnly=true)
	public FieldPermission getFieldPermissionById(Long id) {
		return fieldPermissionMapper.selectByPrimaryKey(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<SpecialPermission> getSpecialPermissionByExample(SpecialPermissionExample example, int limit, int offset) {
		return specialPermissionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public List<FunctionAccessDTO> findFunctionAndSubmenuByNotInActionIdUseItemExclusion(ResponsibilityDTO responsibilityDto) {
		return responsibilityFinder.findFunctionAndSubmenuByNotInActionIdUseItemExclusion(responsibilityDto);
	}

	@Override
	@Transactional(readOnly=true)
	public List<FunctionAccessDTO> findFunctionAndSubmenuByNotInActionIdNotUseItemExclusion(ResponsibilityDTO responsibilityDto) {
		return responsibilityFinder.findFunctionAndSubmenuByNotInActionIdNotUseItemExclusion(responsibilityDto);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ResponsibilityDTO> selectResponsibilityAndMenuByParameter(ResponsibilityDTO responsibilityDto) {
		return responsibilityFinder.selectResponsibilityAndMenuByParameter(responsibilityDto);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ResponsibilityDTO> selectResponsibilityAndMenuByParameterWithRowbounds(ResponsibilityDTO responsibilityDto, int limit, int offset) {
		return responsibilityFinder.selectResponsibilityAndMenuByParameterWithRowbounds(responsibilityDto, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int countItemExclusionByExample(ItemExclusionExample example) {
		return itemExclusionMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int countResponsibilityCompanyByExample(ResponsibilityCompanyExample example) {
		return responsibilityCompanyMapper.countByExample(example);
	}

	@Override
	public void validateResponsibility(ResponsibilityDTO responsibilityDto) throws ValidationException {
		ResponsibilityValidator responsibilityValidator = new ResponsibilityValidator();
		responsibilityValidator.validate(responsibilityDto);	
	}

	@Override
	@Transactional(readOnly=true)
	public List<KeyValue> getResponsibilityKeyValues() {
		return responsibilityFinder.findResponsibilityKeyValue();
	}

	@Override
	@Transactional(readOnly=true)
	public KeyValue getResponsibilityKeyValueById(Long id) {
		return responsibilityFinder.findResponsibilityKeyValueById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Responsibility> getResponsibilityName() {
		return responsibilityFinder.getResponsibilityName();
	}

	@Override
	public Responsibility selectByPrimaryKey(Long responsibilityId) {
		return responsibilityMapper.selectByPrimaryKey(responsibilityId);
	}

	@Override
	public ResponsibilityCompany selectResponsibilityCompanyByPrimaryKey(Long id) {
		return responsibilityCompanyMapper.selectByPrimaryKey(id);
	}

	@Override
	public int countFunctionAndSubmenuByRootMenuId(Long menuId,
			String itemName, MenuItemType type) {
		int result = 0;
		if(type.toString().trim().equals(MenuItemType.FUNCTION.toString().trim())) {
			result = responsibilityFinder.countFunctionByRootMenuId(menuId, itemName);
		} else if(type.toString().trim().equals(MenuItemType.SUBMENU.toString().trim())) {
			result = responsibilityFinder.countSubmenuByRootMenuId(menuId, itemName);		
		}
		return result;
		
	}

	@Override
	public List<FunctionAccessDTO> getFunctionsAccessByMenuId(Long menuId, Long respId, String type) {
		if(respId == null)
			return responsibilityFinder.getFunctionAccessByMenuId(menuId);
		else if(type.equals(FunctionAccessType.READ.toString()) || type.equals(FunctionAccessType.FULL.toString()))
			return responsibilityFinder.getFunctionAccessByMenuId(menuId);
		else
			return responsibilityFinder.getFunctionAccessByMenuAndRespId(menuId, respId);
	}

	@Override
	public int countSpecialPermissionByExample(SpecialPermissionExample example) {
		return specialPermissionMapper.countByExample(example);
	}
}
