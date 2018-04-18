package co.id.fifgroup.systemadmin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.GradeExample;
import co.id.fifgroup.workstructure.service.GradeSetupService;
import co.id.fifgroup.workstructure.service.OrganizationLevelSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import com.google.common.base.Strings;

import co.id.fifgroup.audit.objectcopy.DeepCopy;
import co.id.fifgroup.basicsetup.domain.BusinessGroup;
import co.id.fifgroup.basicsetup.domain.BusinessGroupExample;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.service.BusinessGroupService;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.constant.FilterType;
import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.systemadmin.domain.FieldPermission;
import co.id.fifgroup.systemadmin.domain.Menu;
import co.id.fifgroup.systemadmin.domain.MenuExample;
import co.id.fifgroup.systemadmin.domain.MenuItem;
import co.id.fifgroup.systemadmin.domain.SpecialPermission;
import co.id.fifgroup.systemadmin.domain.SpecialPermissionExample;
import co.id.fifgroup.systemadmin.domain.TaskGroup;
import co.id.fifgroup.systemadmin.domain.TaskGroupExample;
import co.id.fifgroup.systemadmin.dto.GradeExclusionDTO;
import co.id.fifgroup.systemadmin.dto.IncludedOrganizationDTO;
import co.id.fifgroup.systemadmin.dto.ItemExclusionDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityCompanyDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityDTO;
import co.id.fifgroup.systemadmin.service.MenuService;
import co.id.fifgroup.systemadmin.service.ResponsibilityService;
import co.id.fifgroup.systemadmin.service.TaskGroupService;
import co.id.fifgroup.systemadmin.validation.ResponsibilityValidator;
import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;



@VariableResolver(DelegatingVariableResolver.class)
public class ResponsibilitySetupComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Services
	@WireVariable(rewireOnActivate = true)
	private transient TaskGroupService taskGroupServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient MenuService menuService;
	@WireVariable(rewireOnActivate = true)
	private transient ResponsibilityService responsibilityService;
	@WireVariable(rewireOnActivate = true)
	private transient BusinessGroupService businessGroupServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient GradeSetupService gradeSetupServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient OrganizationLevelSetupService organizationLevelSetupServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient ResponsibilityValidator responsibilityValidator;
	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	
	//Components
	@Wire
	private Textbox txtRespName;
	@Wire
	private CommonPopupBandbox txtTaskGroup;
	@Wire
	private Datebox dtbDateFrom;
	@Wire
	private CommonPopupBandbox txtMenuName;
	@Wire
	private Datebox dtbDateTo;
	@Wire
	private TabularEntry<ItemExclusionDTO> tbnItemExclusions;
	@Wire
	private Listbox lstBusinessGroup;
	@Wire
	private TabularEntry<ResponsibilityCompanyDTO> tbnCompany;
	@Wire
	private Groupbox grbCompanyDetails;
	@Wire
	private Label lblErrorCompany;
	@Wire
	private Radiogroup rdogrpSecurityByAssignment;
	@Wire
	private Listbox lstLevelAssignment;
	@Wire
	private Checkbox chkRecursiveAssignment;
	@Wire
	private Listbox lstFilterAssignment;
	@Wire
	private TabularEntry<IncludedOrganizationDTO> tbnOrganizationAssignment;
	@Wire
	private TabularEntry<GradeExclusionDTO> tbnGradeExclusionAssignment;
	@Wire
	private Checkbox chkAllowOtherUserMultiCompany;
	@Wire
	private Radiogroup rdogrpViewAllOrganizationMultiCompany;
	@Wire
	private Listbox lstLevelDefaultMultiCompany;
	@Wire
	private Checkbox chkRecursiveMultiCompany;
	@Wire
	private TabularEntry<IncludedOrganizationDTO> tbnOrganizationMultiCompany;
	@Wire
	private TabularEntry<GradeExclusionDTO> tbnGradeMultiCompany;
	@Wire
	private Row rowLevelMultiCompany;
	@Wire
	private Row rowFilterMultiCompany;
	@Wire
	private Row rowOrganizationMultiCompany;
	@Wire
	private Row rowSecurityMultiCompany;
	@Wire
	private Row rowGradeExclusionMultiCompany;
	@Wire
	private Row rowLevelByAssingment;
	@Wire
	private Row rowFilterByAssingment;
	@Wire
	private Row rowOrganizationByAssignment;
	@Wire
	private Listbox lstFilterDefaultMultiCompany;
	@Wire
	private TabularEntry<FieldPermission> tbnPermission;
	@Wire
	private Vbox detailErrorMessage;
	@Wire
	private Label lblErrorMenu;
	@Wire
	private Label lblErrorName;
	@Wire
	private Button btnAddRowItemExclusions;
	@Wire
	private Button btnDeleteItemExclusions;
	@Wire
	private Button btnAddRowCompany;
	@Wire
	private Button btnDeleteRowCompany;
	@Wire
	private Button btnAddRowPermission;
	@Wire
	private Button btnDeletePermission;
	@Wire
	private Button btnAddGradeExclusionAssignment;
	@Wire
	private Button btnDeleteGradeExclusionAssignment;
	@Wire
	private Button btnAddGradeMultiCompany;
	@Wire
	private Button btnDeleteGradeMultiCompany;
	@Wire
	private Button btnAddOrganizationMultiCompany;
	@Wire
	private Button btnDeleteOrganizationMultiCompany;
	@Wire
	private Button btnAddOrganizationAssignment;
	@Wire
	private Button btnDeleteOrganizationAssignment;
	
	//Variables
	private List<ItemExclusionDTO> itemExclusionDTOList;
	private ResponsibilityDTO responsibilityDTO;
	private ResponsibilityDTO selectedResponsibilityDTO;
	private List<ResponsibilityCompanyDTO> responsibilityCompanyDTOList;
	private ResponsibilityCompanyDTO currentResponsibilityCompanyDTO;
	private List<GradeExclusionDTO> gradeExclusionByAssignmentDTOList;
	private List<IncludedOrganizationDTO> includedOrganizationByAssignmentDTOList;
	private List<GradeExclusionDTO> gradeExclusionByMultiCompanyDTOList;
	private List<IncludedOrganizationDTO> includedOrganizationByMultiCompanyDTOList;
	private List<FieldPermission> fieldPermissionDTOList;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private static Logger log = LoggerFactory.getLogger(ResponsibilitySetupComposer.class);
	private ResponsibilityDTO previousObject;
	FunctionPermission functionPermission;
	private boolean isDetailOpen = false;
	private CommonPopupBandbox activeCompanyBandbox;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		selectedResponsibilityDTO = (ResponsibilityDTO) arg.get("responsibility");
		
		ResponsibilityDTO responsibility = (ResponsibilityDTO) arg.get("responsibilityInquiry");
		if(responsibility != null){
			selectedResponsibilityDTO = responsibilityService.getResponsibilityByResponsibilityId(responsibility.getId());
			previousObject = (ResponsibilityDTO) DeepCopy.copy(selectedResponsibilityDTO);
		}
		if(selectedResponsibilityDTO != null){
			responsibilityDTO = selectedResponsibilityDTO;
			itemExclusionDTOList = selectedResponsibilityDTO.getItemExclusions();
			responsibilityCompanyDTOList = selectedResponsibilityDTO.getResponsibilityCompanyDto();
			fieldPermissionDTOList = selectedResponsibilityDTO.getFieldPermissions();
			loadInitData();
			buildItemExclusions();
			loadBusinessGroupData();
			buildResponsibilityCompany();
			bulidTbnFieldPermission();
		}else{
			buildItemExclusions();
			loadBusinessGroupData();
			buildResponsibilityCompany();
			bulidTbnFieldPermission();
		}
		
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
		dtbDateFrom.setFormat(FormatDateUI.getDateFormat());
		dtbDateTo.setFormat(FormatDateUI.getDateFormat());
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			txtRespName.setDisabled(true);
			dtbDateTo.setDisabled(true);
			txtTaskGroup.setDisabled(true);
			txtMenuName.setDisabled(true);
			btnAddRowItemExclusions.setDisabled(true);
			btnDeleteItemExclusions.setDisabled(true);
			btnAddRowCompany.setDisabled(true);
			btnDeleteRowCompany.setDisabled(true);
			btnAddRowPermission.setDisabled(true);
			btnDeletePermission.setDisabled(true);
			lstBusinessGroup.setDisabled(true);
			Vbox v1 =  (Vbox) rdogrpSecurityByAssignment.getChildren().get(0);
			for (Component radio : v1.getChildren()) {
				Radio rd = (Radio)radio;
				rd.setDisabled(true);
			}
			Vbox v2 = (Vbox) rdogrpViewAllOrganizationMultiCompany.getChildren().get(0);
			for (Component radio : v2.getChildren()) {
				Radio rd = (Radio)radio;
				rd.setDisabled(true);
			}
			lstLevelAssignment.setDisabled(true);
			lstLevelDefaultMultiCompany.setDisabled(true);
			lstFilterAssignment.setDisabled(true);
			lstFilterDefaultMultiCompany.setDisabled(true);
			chkAllowOtherUserMultiCompany.setDisabled(true);
			chkRecursiveAssignment.setDisabled(true);
			chkRecursiveMultiCompany.setDisabled(true);
			btnAddGradeExclusionAssignment.setDisabled(true);
			btnDeleteGradeExclusionAssignment.setDisabled(true);
			btnAddGradeMultiCompany.setDisabled(true);
			btnDeleteGradeMultiCompany.setDisabled(true);
			btnAddOrganizationMultiCompany.setDisabled(true);
			btnDeleteOrganizationMultiCompany.setDisabled(true);
			btnAddOrganizationAssignment.setDisabled(true);
			btnDeleteOrganizationAssignment.setDisabled(true);
		}
	}
	
	private void loadInitData(){
		txtRespName.setValue(selectedResponsibilityDTO.getResponsibilityName());
		txtRespName.setDisabled(true);
		txtMenuName.setValue(selectedResponsibilityDTO.getMenu().getMenuName());
		
		KeyValue menuKeyValue = new KeyValue();
		menuKeyValue.setKey(selectedResponsibilityDTO.getMenuId());
		menuKeyValue.setValue(selectedResponsibilityDTO.getMenu());
		menuKeyValue.setDescription(selectedResponsibilityDTO.getMenu().getMenuName());
		txtMenuName.setRawValue(menuKeyValue);
		
		KeyValue taskGroupKeyvalue = new KeyValue();
		taskGroupKeyvalue.setKey(selectedResponsibilityDTO.getTaskGroupId());
		taskGroupKeyvalue.setValue(selectedResponsibilityDTO.getTaskGroupName());
		taskGroupKeyvalue.setDescription(selectedResponsibilityDTO.getTaskGroupName());
		txtTaskGroup.setRawValue(taskGroupKeyvalue);
		
		dtbDateFrom.setValue(selectedResponsibilityDTO.getDateFrom());
		dtbDateTo.setValue(selectedResponsibilityDTO.getDateTo());
		dtbDateFrom.setDisabled(true);
	}
	
	@Listen("onCreate=#txtTaskGroup")
	public void loadTaskGroupData(){
		txtTaskGroup.setTitle("List of Task Group");
		txtTaskGroup.setSearchText("Task Group Name");
		txtTaskGroup.setHeaderLabel("Task Group");
		txtTaskGroup.setSearchDelegate(new SerializableSearchDelegate<TaskGroup>() {

			private static final long serialVersionUID = -9008913765332633929L;

			@Override
			public List<TaskGroup> findBySearchCriteria(String searchCriteria, int limit, int offset) {				
				TaskGroupExample taskGroupExample = new TaskGroupExample();
				taskGroupExample.createCriteria().andTaskGroupNameLikeInsensitive(searchCriteria);			
				return taskGroupServiceImpl.getTaskGroupByExample(taskGroupExample, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				TaskGroupExample example = new TaskGroupExample();
				example.createCriteria().andTaskGroupNameLikeInsensitive(searchCriteria);
				return taskGroupServiceImpl.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, TaskGroup data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getTaskGroupName());
				keyValue.setDescription(data.getTaskGroupName());
			}
		});
	}
	
	
	@Listen("onCreate=#txtMenuName")
	public void loadMenuData(){
		txtMenuName.setTitle("List of Menu");
		txtMenuName.setSearchText("Menu Name");
		txtMenuName.setHeaderLabel("Menu");
		txtMenuName.setSearchDelegate(new SerializableSearchDelegate<Menu>() {

			private static final long serialVersionUID = -9008913765332633929L;

			@Override
			public List<Menu> findBySearchCriteria(String searchCriteria, int limit, int offset) {		
				MenuExample example = new MenuExample();
				example.createCriteria().andMenuNameLikeInsensitive(searchCriteria)
					.andDateFromLessThanOrEqualTo(DateUtil.truncate(new Date()))
					.andDateToGreaterThanOrEqualTo(DateUtil.truncate(new Date()));	
				return menuService.getMenuByExample(example);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {			
				MenuExample example = new MenuExample();
				example.createCriteria().andMenuNameLikeInsensitive(searchCriteria);
				return menuService.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, Menu data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data);
				keyValue.setDescription(data.getMenuName());
			}
		});
	}
	
	private void loadBusinessGroupData(){
		List<BusinessGroup> businessGroupList = businessGroupServiceImpl.getBusinessGroupByExample(new BusinessGroupExample());
		ListModelList<BusinessGroup> model = new ListModelList<BusinessGroup>(businessGroupList);
		lstBusinessGroup.setModel(model);
		lstBusinessGroup.renderAll();
		if(selectedResponsibilityDTO != null) {
			if(selectedResponsibilityDTO.getBusinessGroupId() != null) {
				int idx = 0;
				for (BusinessGroup businessGroup : model) {
					if(businessGroup.getGroupId() == selectedResponsibilityDTO.getBusinessGroupId()) {
						lstBusinessGroup.setSelectedIndex(idx);
					}
					idx++;
				}
			}
		} else {
			lstBusinessGroup.setSelectedIndex(0);
		}
	}
	
	
	//Start Build Item Exclusion Tabular Entry	
	@Listen("onClick=#btnAddRowItemExclusions")
	public void addRowItemExclusions() {
		tbnItemExclusions.addRow();
	}
		
	@Listen("onClick=#btnDeleteItemExclusions")
	public void deleteRowItemExclusions() {
		tbnItemExclusions.deleteRow();
	}
			
	private ListModelList<ItemExclusionDTO> getItemExclusionModel() {
		if(itemExclusionDTOList == null) {
			itemExclusionDTOList = new ArrayList<ItemExclusionDTO>();
			itemExclusionDTOList.add(new ItemExclusionDTO());
		}
			
		ListModelList<ItemExclusionDTO> model = new ListModelList<ItemExclusionDTO>(itemExclusionDTOList);
		model.setMultiple(true);
		return model;
	}
		
	private SerializableListItemRenderer<ItemExclusionDTO> getItemExclusionRenderer() {
		return new SerializableListItemRenderer<ItemExclusionDTO>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final ItemExclusionDTO data, int index) throws Exception {		
				if(data.getMenuItem() == null) {
						data.setMenuItem(new MenuItem());
					}
					
					String menuItem = data.getItemType();
					new Listcell("").setParent(item);
					
					Listcell cellMenu = new Listcell();
					final Listbox type = createMenuItemType(menuItem);
					
					if(type.getSelectedItem() != null) {
						data.setItemType(type.getSelectedItem().getValue().toString());
					}
					
					type.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							data.setItemType(type.getSelectedItem().getValue().toString());
						}
					});

					cellMenu.appendChild(type);
					cellMenu.setParent(item);
									
					Listcell cellItemExclusion = new Listcell();
					CommonPopupBandbox commonPopupBandboxItemExclusion = new CommonPopupBandbox();
					commonPopupBandboxItemExclusion.setTitle("List of Menu Item");
					commonPopupBandboxItemExclusion.setSearchText("Menu Item Name");
					commonPopupBandboxItemExclusion.setHeaderLabel("Menu Item");
					commonPopupBandboxItemExclusion.setWidth("75%");
					commonPopupBandboxItemExclusion.setSearchDelegate(new SerializableSearchDelegate<ItemExclusionDTO>() {

						private static final long serialVersionUID = 1L;
		
						@Override
						public List<ItemExclusionDTO> findBySearchCriteria(String searchCriteria, int limit, int offset) {
							
							KeyValue selectedMenu = txtMenuName.getKeyValue();
							if(selectedMenu != null){
								List<ItemExclusionDTO> items = responsibilityService.findFunctionAndSubmenuByRootMenuId((Long)selectedMenu.getKey(), searchCriteria, (MenuItemType) type.getSelectedItem().getValue());
								if(!items.isEmpty())
									return items.subList(offset, items.size());
								else
									return new ArrayList<ItemExclusionDTO>();
							}else
								return new ArrayList<ItemExclusionDTO>();
						}
		
						@Override
						public int countBySearchCriteria(String searchCriteria) {
							KeyValue selectedMenu = txtMenuName.getKeyValue();
							if(selectedMenu != null)
								return responsibilityService.findFunctionAndSubmenuByRootMenuId((Long)selectedMenu.getKey(), searchCriteria, (MenuItemType) type.getSelectedItem().getValue()).size();
							else
								return 0;
						}
		
						@Override
						public void mapper(KeyValue keyValue, ItemExclusionDTO data) {
							keyValue.setKey(data.getActionId());
							keyValue.setValue(data.getMenuItem().getItemName());
							keyValue.setDescription(data.getMenuItem().getItemName());
						}
					});
					
					commonPopupBandboxItemExclusion.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
		
						private static final long serialVersionUID = 1L;
		
						@Override
						public void onEvent(Event event) throws Exception {
							KeyValue keyValue = (KeyValue) event.getData();
							if(keyValue != null) {
								data.setActionId((long) keyValue.getKey());
								data.getMenuItem().setItemName(keyValue.getValue().toString());
							} else {
								data.setActionId(null);
								data.getMenuItem().setItemName(null);
							}
						}
					});

					KeyValue setKeyValueItemExclusion = new KeyValue(data.getActionId(), data.getMenuItem().getItemName(), data.getMenuItem().getItemName());
					commonPopupBandboxItemExclusion.setRawValue(setKeyValueItemExclusion);
					commonPopupBandboxItemExclusion.setReadonly(true);
					cellItemExclusion.appendChild(commonPopupBandboxItemExclusion);
					cellItemExclusion.setParent(item);
				}
			};
		}
		
		private Listbox createMenuItemType(String selected) {
			Listbox type = new Listbox();
			type.setMold("select");
			ListModelList<MenuItemType> model = new ListModelList<MenuItemType>(MenuItemType.values());
			type.setModel(model);
			type.renderAll();
			
			if(selected != null) {
				if (MenuItemType.FUNCTION.toString().trim().equals(selected)) {
					type.setSelectedIndex(0);
				} else if (MenuItemType.SUBMENU.toString().trim().equals(selected.trim())) {
					type.setSelectedIndex(1);
				}
			} else {
				type.setSelectedIndex(0);
			}
			return type;
		}
		
		private TabularValidationRule<ItemExclusionDTO> itemExclusionValidationRule() {
			return new TabularValidationRule<ItemExclusionDTO>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validate(ItemExclusionDTO data, List<String> errorRow) {
					if(data.getActionId() == null) {
						errorRow.add("Item Exclusion can't be empty");
					}
					if(errorRow.size() > 0)	
						return false;
					return true;
				}
			};
		}
		
		private void buildItemExclusions() {
			tbnItemExclusions.setDataType(ItemExclusionDTO.class);
			tbnItemExclusions.setModel(getItemExclusionModel());
			tbnItemExclusions.setItemRenderer(getItemExclusionRenderer());
			tbnItemExclusions.setValidationRule(itemExclusionValidationRule());
		}
		
		//End Build Item Exclusion Tabular Entry

		//Start Build Company Tabular Entry
		@Listen("onClick=#btnAddRowCompany")
		public void addRowCompany() {
			tbnCompany.addRow();
		}
		
		@Listen("onClick=#btnDeleteRowCompany")
		public void deleteRowCompany() {
			tbnCompany.deleteRow();
		}
		
		private ListModelList<ResponsibilityCompanyDTO> getCompanyModel() {
			if(responsibilityCompanyDTOList == null) {
				responsibilityCompanyDTOList = new ArrayList<ResponsibilityCompanyDTO>();
				responsibilityCompanyDTOList.add(new ResponsibilityCompanyDTO());
			}
			
			ListModelList<ResponsibilityCompanyDTO> model = new ListModelList<ResponsibilityCompanyDTO>(responsibilityCompanyDTOList);
			model.setMultiple(true);
			return model;
		}
			
		private SerializableListItemRenderer<ResponsibilityCompanyDTO> getCompanyRenderer() {	
			return new SerializableListItemRenderer<ResponsibilityCompanyDTO>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void render(Listitem item, final ResponsibilityCompanyDTO data, int index) throws Exception {
					new Listcell("").setParent(item);
					
					currentResponsibilityCompanyDTO = data;
					if (Strings.isNullOrEmpty(currentResponsibilityCompanyDTO.getSecurityByAssign()))
						currentResponsibilityCompanyDTO.setSecurityByAssign(SecurityType.VIEW_OWN.toString());
					
					Listcell cellCompany = new Listcell();
					final CommonPopupBandbox commonPopupBandboxCompany = new CommonPopupBandbox();
					commonPopupBandboxCompany.setTitle("List of Company");
					commonPopupBandboxCompany.setSearchText("Company Name");
					commonPopupBandboxCompany.setHeaderLabel("Company");
					commonPopupBandboxCompany.setWidth("90%");
					commonPopupBandboxCompany.setSearchDelegate(new SerializableSearchDelegate<Company>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<Company> findBySearchCriteria(String searchCriteria, int limit, int offset) {
							CompanyExample companyExample = new CompanyExample();
							if(lstBusinessGroup.getSelectedItem() != null) {
								BusinessGroup businessGroup = lstBusinessGroup.getSelectedItem().getValue();
								if(businessGroup != null) {
									companyExample.createCriteria().andGroupIdEqualTo(businessGroup.getGroupId())
										.andCompanyNameLikeInsensitive(searchCriteria)
										.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
										.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
									
								}
							}
							
							return companyServiceImpl.getCompanyByExample(companyExample);
						}

						@Override
						public int countBySearchCriteria(String searchCriteria) {
							return companyServiceImpl.getCountCompaniesByEffectiveOnDateAndCompanyName(DateUtil.truncate(new Date()), searchCriteria);
						}

						@Override
						public void mapper(KeyValue keyValue, Company data) {
							keyValue.setKey(data.getCompanyId());
							keyValue.setValue(data.getCompanyName());
							keyValue.setDescription(data.getCompanyName());
						}
					});
					
					commonPopupBandboxCompany.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							KeyValue keyValue = (KeyValue) event.getData();
							currentResponsibilityCompanyDTO = new ResponsibilityCompanyDTO();
							currentResponsibilityCompanyDTO.setSecurityByAssign(SecurityType.VIEW_OWN.toString());
							if(keyValue != null) {
								data.setCompanyId((long) keyValue.getKey());
								data.setCompanyName(keyValue.getValue().toString());
								currentResponsibilityCompanyDTO.setCompanyId((long) keyValue.getKey());
								currentResponsibilityCompanyDTO.setCompanyName(keyValue.getValue().toString());
							} else {
								data.setCompanyId(null);
								data.setCompanyName(null);
							}
							if (tbnOrganizationAssignment.getModel() != null)
								selectOrganizationLevel();
							
							
							
						}
					});
					
					KeyValue setKeyValueCompany = new KeyValue(data.getCompanyId(), data.getCompanyName(), data.getCompanyName());
					commonPopupBandboxCompany.setRawValue(setKeyValueCompany);
					commonPopupBandboxCompany.setReadonly(true);
					cellCompany.appendChild(commonPopupBandboxCompany);
					cellCompany.setParent(item);
					
					Listcell cellSummary = new Listcell();
					Label labelSummary = new Label();
					
					//ResponsibilityCompany summary = responsibilityService.selectResponsibilityCompanyByPrimaryKey(data.getId());			
				//	if(summary != null) {
					labelSummary.setValue("Security type : " + currentResponsibilityCompanyDTO.getSecurityByAssign() + ", is Allow other user : " + currentResponsibilityCompanyDTO.isGrantAccessToOtherUser());
					//}
					labelSummary.setParent(cellSummary);
					cellSummary.setParent(item);
					
					Listcell cellA = new Listcell();
					final A ancorDetail = new A("Detail");
					ancorDetail.setStyle("color : blue");
					ancorDetail.addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							if(commonPopupBandboxCompany.getKeyValue() != null && commonPopupBandboxCompany.getKeyValue().getKey() != null) {
								grbCompanyDetails.setVisible(true);
								grbCompanyDetails.setTitle(data.getCompanyName());
								if(!data.getCompanyId().equals(currentResponsibilityCompanyDTO.getCompanyId()))
									currentResponsibilityCompanyDTO = data;
								lblErrorCompany.setVisible(false);
								isDetailOpen = true;
								renderSecurityCompanyDetail(commonPopupBandboxCompany);	
								activeCompanyBandbox = commonPopupBandboxCompany;
								disableCompanyTabularEntry(true);
							} else {
								lblErrorCompany.setVisible(true);
								lblErrorCompany.setValue("Choose Company first !");
								lblErrorCompany.setStyle("color : red");
								grbCompanyDetails.setVisible(false);
							}
						}
					});
					
					ancorDetail.setParent(cellA);
					cellA.setParent(item);
				}
			};
		}
		
		private void disableCompanyTabularEntry(boolean disabled){
			tbnCompany.getItems();
			for (Listitem item : tbnCompany.getItems()) {
				Component listCell = item.getChildren().get(1);
				Component comp = listCell.getChildren().get(0);
				CommonPopupBandbox bnb = (CommonPopupBandbox)comp;
				bnb.setDisabled(disabled);
			}
		}
			
		private TabularValidationRule<ResponsibilityCompanyDTO> companyValidationRule() {
			return new TabularValidationRule<ResponsibilityCompanyDTO>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validate(ResponsibilityCompanyDTO data, List<String> errorRow) {
					if(data.getCompanyId() == null) {
						errorRow.add("Company Name can't be empty");
					}
					if(errorRow.size() > 0)	
						return false;
					return true;
				}		
			};
		}
		
		private void buildResponsibilityCompany() {
			tbnCompany.setDataType(ResponsibilityCompanyDTO.class);
			tbnCompany.setModel(getCompanyModel());
			tbnCompany.setItemRenderer(getCompanyRenderer());
			tbnCompany.setValidationRule(companyValidationRule());
		}
		
		//End Build Company Tabular Entry

		//Start Build Grade Exclusion on Security By Assignment Tabular Entry
		@Listen("onClick=#btnAddGradeExclusionAssignment")
		public void addRowGradeExclusionAssignment() {
			tbnGradeExclusionAssignment.addRow();
		}
		
		@Listen("onClick=#btnDeleteGradeExclusionAssignment")
		public void deleteRowGradeExclusionAssignment() {
			tbnGradeExclusionAssignment.deleteRow();
		}
		
		private ListModelList<GradeExclusionDTO> getGradeExclusionAssignmentModel() {		
			gradeExclusionByAssignmentDTOList = new ArrayList<GradeExclusionDTO>();
			gradeExclusionByAssignmentDTOList.add(new GradeExclusionDTO());
			
			if(currentResponsibilityCompanyDTO.getGradeExclusionByAssignments() != null) {
				gradeExclusionByAssignmentDTOList = currentResponsibilityCompanyDTO.getGradeExclusionByAssignments();
			}
			
			ListModelList<GradeExclusionDTO> model = new ListModelList<GradeExclusionDTO>(gradeExclusionByAssignmentDTOList);
			model.setMultiple(true);
			return model;
		}
		
		private SerializableListItemRenderer<GradeExclusionDTO> getGradeExclusionAssignmentRenderer() {
			return new SerializableListItemRenderer<GradeExclusionDTO>() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void render(Listitem item, final GradeExclusionDTO data, int index) throws Exception {		
					new Listcell("").setParent(item);
					
					Listcell cellGradeExclusionAssignment = new Listcell();
					CommonPopupBandbox commonPopupBandboxGradeExclusionAssignment = new CommonPopupBandbox();	
					commonPopupBandboxGradeExclusionAssignment.setTitle("List of Grade");
					commonPopupBandboxGradeExclusionAssignment.setSearchText("Grade Name");
					commonPopupBandboxGradeExclusionAssignment.setHeaderLabel("Grade");
					commonPopupBandboxGradeExclusionAssignment.setSearchDelegate(new SerializableSearchDelegate<GradeDTO>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<GradeDTO> findBySearchCriteria(String searchCriteria, int limit, int offset) {
							GradeDTO criteria = new GradeDTO();
							criteria.setCompanyId(currentResponsibilityCompanyDTO.getCompanyId());
							criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
							criteria.setName(searchCriteria);
							return gradeSetupServiceImpl.findActiveGradeForLov(criteria, limit, offset);
						}
						
						@Override
						public int countBySearchCriteria(String searchCriteria) {
							GradeDTO criteria = new GradeDTO();
							criteria.setCompanyId(currentResponsibilityCompanyDTO.getCompanyId());
							criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
							criteria.setName(searchCriteria);
							return gradeSetupServiceImpl.countActiveGradeForLov(criteria);
						}

						@Override
						public void mapper(KeyValue keyValue, GradeDTO data) {
							keyValue.setKey(data.getId());
							keyValue.setValue(data.getCode()+" - "+data.getGrade() + " - " + data.getSubGrade());
							keyValue.setDescription(data.getCode()+" - "+data.getGrade() + " - " + data.getSubGrade());
						}
					});
					
					commonPopupBandboxGradeExclusionAssignment.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							KeyValue keyValue = (KeyValue) event.getData();
							if(keyValue != null) {
								data.setGradeId((long) keyValue.getKey());
								data.setGradeName(keyValue.getValue().toString());
							} else {
								data.setGradeId(null);
								data.setGradeName(null);
							}
						}
					});
					
					gradeExclusionByAssignmentDTOList.remove(data);
					gradeExclusionByAssignmentDTOList.add(data);
					currentResponsibilityCompanyDTO.setGradeExclusionByAssignments(gradeExclusionByAssignmentDTOList);
					commonPopupBandboxGradeExclusionAssignment.setValue(data.getGradeName());
					commonPopupBandboxGradeExclusionAssignment.setReadonly(true);
					cellGradeExclusionAssignment.appendChild(commonPopupBandboxGradeExclusionAssignment);
					cellGradeExclusionAssignment.setParent(item);
				}
			};
		}
		
		private TabularValidationRule<GradeExclusionDTO> gradeExclusionAssignmentValidationRule() {
			return new TabularValidationRule<GradeExclusionDTO>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validate(GradeExclusionDTO data, List<String> errorRow) {
					if(rdogrpSecurityByAssignment.getSelectedIndex() == 5) {
						if(data.getGradeId() == null) {
							errorRow.add("Grade name can't be empty");
						}
					}
					if(errorRow.size() > 0)	
						return false;
					return true;
				}		
			};
		}
		
		private void buildTbnGradeExclusionOnSecurityByAssignment() {
			tbnGradeExclusionAssignment.clearSelection();
			tbnGradeExclusionAssignment.setDataType(GradeExclusionDTO.class);
			tbnGradeExclusionAssignment.setModel(getGradeExclusionAssignmentModel());
			tbnGradeExclusionAssignment.setItemRenderer(getGradeExclusionAssignmentRenderer());
			tbnGradeExclusionAssignment.setValidationRule(gradeExclusionAssignmentValidationRule());
		}
		
		//Start Build Organization on Security By Assignment Tabular Entry
		@Listen("onClick=#btnAddOrganizationAssignment")
		public void addRowOrganizationAssignment() {
			tbnOrganizationAssignment.addRow();
		}
		
		@Listen("onClick=#btnDeleteOrganizationAssignment")
		public void deleteRowOrganizationAssignment() {
			tbnOrganizationAssignment.deleteRow();
		}
		
		private ListModelList<IncludedOrganizationDTO> getOrganizationAssignmentModel() {
			includedOrganizationByAssignmentDTOList = new ArrayList<IncludedOrganizationDTO>();
			includedOrganizationByAssignmentDTOList.add(new IncludedOrganizationDTO());
			
			if(currentResponsibilityCompanyDTO.getIncludedOrganizationByAssignments() != null) {
				includedOrganizationByAssignmentDTOList = currentResponsibilityCompanyDTO.getIncludedOrganizationByAssignments();
			}
					
			ListModelList<IncludedOrganizationDTO> model = new ListModelList<IncludedOrganizationDTO>(includedOrganizationByAssignmentDTOList);
			model.setMultiple(true);
			return model;
		}

		private SerializableListItemRenderer<IncludedOrganizationDTO> getOrganizationAssignmentRenderer() {
			return new SerializableListItemRenderer<IncludedOrganizationDTO>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void render(Listitem item, final IncludedOrganizationDTO data, int index) throws Exception {			
					new Listcell("").setParent(item);
					
					Listcell cellOrganizationAssignment = new Listcell();
					CommonPopupBandbox commonPopupBandboxOrganizationAssignment = new CommonPopupBandbox();
					commonPopupBandboxOrganizationAssignment.setTitle("List of Organization");
					commonPopupBandboxOrganizationAssignment.setSearchText("Organization Name");
					commonPopupBandboxOrganizationAssignment.setHeaderLabel("Organization");
					commonPopupBandboxOrganizationAssignment.setSearchDelegate(new SerializableSearchDelegate<OrganizationDTO>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<OrganizationDTO> findBySearchCriteria(String searchCriteria, int limit, int offset) {
							List<OrganizationDTO> listOrganizationDTO = new ArrayList<OrganizationDTO>();
							if(lstLevelAssignment.getSelectedIndex() > -1) {
								OrganizationLevelDto select = lstLevelAssignment.getSelectedItem().getValue();
								listOrganizationDTO = organizationSetupServiceImpl.findByLevelIdAndOrgName(select.getId(), searchCriteria, currentResponsibilityCompanyDTO.getCompanyId(), limit, offset);
							}
							return listOrganizationDTO;
						}

						@Override
						public int countBySearchCriteria(String searchCriteria) {
							if(lstLevelAssignment.getSelectedIndex() > -1) {
								OrganizationLevelDto select = lstLevelAssignment.getSelectedItem().getValue();
								return organizationSetupServiceImpl.countByLevelIdAndOrgName(select.getId(), searchCriteria, currentResponsibilityCompanyDTO.getCompanyId());
							} else {
								return 0;
							}
						}

						@Override
						public void mapper(KeyValue keyValue, OrganizationDTO data) {
							keyValue.setKey(data.getId());
							keyValue.setValue(data.getName());
							keyValue.setDescription(data.getName());
						}
					});
					
					commonPopupBandboxOrganizationAssignment.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							KeyValue keyValue = (KeyValue) event.getData();
							if(keyValue != null) {
								data.setOrganizationId((long) keyValue.getKey());
								data.setOrganizationName(keyValue.getValue().toString());
							} else {
								data.setOrganizationId(null);
								data.setOrganizationName(null);
							}
						}
					});
					
//					includedOrganizationByAssignmentDTOList.remove(data);
//					includedOrganizationByAssignmentDTOList.add(data);
//					currentResponsibilityCompanyDTO.setIncludedOrganizationByAssignments(includedOrganizationByAssignmentDTOList);
					commonPopupBandboxOrganizationAssignment.setValue(data.getOrganizationName());
					commonPopupBandboxOrganizationAssignment.setReadonly(true);
					cellOrganizationAssignment.appendChild(commonPopupBandboxOrganizationAssignment);
					cellOrganizationAssignment.setParent(item);
				}
			};
		}
		
		private TabularValidationRule<IncludedOrganizationDTO> organizationOnSecurityByAssignmentValidationRule() {
			return new TabularValidationRule<IncludedOrganizationDTO>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validate(IncludedOrganizationDTO data, List<String> errorRow) {
					if(rdogrpSecurityByAssignment.getSelectedIndex() == 5) {
						if(data.getOrganizationId() == null) {
							errorRow.add("Organization name can't be empty");
						}
					}
					if(errorRow.size() > 0)	
						return false;
					return true;
				}		
			};
		}
		
		private void buildTbnOrganizationOnSecurityByAssignment() {
			tbnOrganizationAssignment.clearSelection();
			tbnOrganizationAssignment.setDataType(IncludedOrganizationDTO.class);
			tbnOrganizationAssignment.setModel(getOrganizationAssignmentModel());
			tbnOrganizationAssignment.setItemRenderer(getOrganizationAssignmentRenderer());
			tbnOrganizationAssignment.setValidationRule(organizationOnSecurityByAssignmentValidationRule());
		}
		
		//End Build Organization on Security By Assignment Tabular Entry
		
		//Start Build Grade Exclusion on Multi Company Tabular Entry
		@Listen("onClick=#btnAddGradeMultiCompany")
		public void addRowGradeMultiCompany() {
			tbnGradeMultiCompany.addRow();
		}
		
		@Listen("onClick=#btnDeleteGradeMultiCompany")
		public void deleteRowGradeMultiCompany() {
			tbnGradeMultiCompany.deleteRow();
		}
		
		private ListModelList<GradeExclusionDTO> getGradeExclusionMultiCompanyModel() {
			gradeExclusionByMultiCompanyDTOList = new ArrayList<GradeExclusionDTO>();
			gradeExclusionByMultiCompanyDTOList.add(new GradeExclusionDTO());
			
			if(currentResponsibilityCompanyDTO.getGradeExclusionMultiCompanys() != null) {
				gradeExclusionByMultiCompanyDTOList = currentResponsibilityCompanyDTO.getGradeExclusionMultiCompanys();
			}
			
			ListModelList<GradeExclusionDTO> model = new ListModelList<GradeExclusionDTO>(gradeExclusionByMultiCompanyDTOList);
			model.setMultiple(true);
			return model;
		}
		
		private SerializableListItemRenderer<GradeExclusionDTO> getGradeExclusionMultiCompanyRenderer() {
			return new SerializableListItemRenderer<GradeExclusionDTO>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void render(Listitem item, final GradeExclusionDTO data, int index) throws Exception {			
					new Listcell("").setParent(item);
					
					Listcell cellGradeExclusionMultiCompany = new Listcell();
					CommonPopupBandbox commonPopupBandboxGradeExclusionMultiCompany = new CommonPopupBandbox();
					commonPopupBandboxGradeExclusionMultiCompany.setTitle("List of Grade");
					commonPopupBandboxGradeExclusionMultiCompany.setSearchText("Grade Name");
					commonPopupBandboxGradeExclusionMultiCompany.setHeaderLabel("Grade");
					commonPopupBandboxGradeExclusionMultiCompany.setSearchDelegate(new SerializableSearchDelegate<GradeDTO>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<GradeDTO> findBySearchCriteria(String searchCriteria, int limit, int offset) {
							GradeDTO criteria = new GradeDTO();
							criteria.setCompanyId(currentResponsibilityCompanyDTO.getCompanyId());
							criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
							criteria.setName(searchCriteria);
							return gradeSetupServiceImpl.findActiveGradeForLov(criteria, limit, offset);
						}
						
						@Override
						public int countBySearchCriteria(String searchCriteria) {
							GradeDTO criteria = new GradeDTO();
							criteria.setCompanyId(currentResponsibilityCompanyDTO.getCompanyId());
							criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
							criteria.setName(searchCriteria);
							return gradeSetupServiceImpl.countActiveGradeForLov(criteria);
						}

						@Override
						public void mapper(KeyValue keyValue, GradeDTO data) {
							keyValue.setKey(data.getId());
							keyValue.setValue(data.getCode()+" - "+data.getGrade() + " - " + data.getSubGrade());
							keyValue.setDescription(data.getCode()+" - "+data.getGrade() + " - " + data.getSubGrade());
						}
					});
					
					commonPopupBandboxGradeExclusionMultiCompany.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							KeyValue keyValue = (KeyValue) event.getData();
							if(keyValue != null) {
								data.setGradeId((long) keyValue.getKey());
								data.setGradeName(keyValue.getValue().toString());
							} else {
								data.setGradeId(null);
								data.setGradeName(null);
							}
						}
					});
					
//					gradeExclusionByMultiCompanyDTOList.remove(data);
//					gradeExclusionByMultiCompanyDTOList.add(data);
//					currentResponsibilityCompanyDTO.setGradeExclusionMultiCompanys(gradeExclusionByMultiCompanyDTOList);
					commonPopupBandboxGradeExclusionMultiCompany.setValue(data.getGradeName());
					commonPopupBandboxGradeExclusionMultiCompany.setReadonly(true);
					cellGradeExclusionMultiCompany.appendChild(commonPopupBandboxGradeExclusionMultiCompany);
					cellGradeExclusionMultiCompany.setParent(item);
				}
			};
		}
		
		private TabularValidationRule<GradeExclusionDTO> gradeExclusionOnMultiCompanyValidationRule() {
			return new TabularValidationRule<GradeExclusionDTO>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validate(GradeExclusionDTO data, List<String> errorRow) {
					if(chkAllowOtherUserMultiCompany.isChecked() == true) {
						if(data.getGradeId() == null) {
							errorRow.add("Grade name can't be empty");
						}
					}
					if(errorRow.size() > 0)	
						return false;
					return true;
				}		
			};
		}
		
		private void buildTbnGradeExclusionOnMultiCompany() {
			tbnGradeMultiCompany.setDataType(GradeExclusionDTO.class);
			tbnGradeMultiCompany.setModel(getGradeExclusionMultiCompanyModel());
			tbnGradeMultiCompany.setItemRenderer(getGradeExclusionMultiCompanyRenderer());
			tbnGradeMultiCompany.setValidationRule(gradeExclusionOnMultiCompanyValidationRule());
		}
		
		//End Build Grade Exclusion on Multi Company Tabular Entry
		
		//Start Build Organization on Multi Company Tabular Entry
		@Listen("onClick=#btnAddOrganizationMultiCompany")
		public void addRowOrganizationMultiCompany() {
			tbnOrganizationMultiCompany.addRow();
		}
		
		@Listen("onClick=#btnDeleteOrganizationMultiCompany")
		public void deleteRowOrganizationMultiCompany() {
			tbnOrganizationMultiCompany.deleteRow();
		}
		
		private ListModelList<IncludedOrganizationDTO> getOrganizationMultiCompanyModel() {
			includedOrganizationByMultiCompanyDTOList = new ArrayList<IncludedOrganizationDTO>();
			includedOrganizationByMultiCompanyDTOList.add(new IncludedOrganizationDTO());
			
			if(currentResponsibilityCompanyDTO.getIncludedOrganizationMultiCompanys() != null) {
				includedOrganizationByMultiCompanyDTOList = currentResponsibilityCompanyDTO.getIncludedOrganizationMultiCompanys();
			}
			
			ListModelList<IncludedOrganizationDTO> model = new ListModelList<IncludedOrganizationDTO>(includedOrganizationByMultiCompanyDTOList);
			model.setMultiple(true);
			return model;
		}
		
		private SerializableListItemRenderer<IncludedOrganizationDTO> getOrganizationMultiCompanyRenderer() {
			return new SerializableListItemRenderer<IncludedOrganizationDTO>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void render(Listitem item, final IncludedOrganizationDTO data, int index) throws Exception {			
					new Listcell("").setParent(item);
					
					Listcell cellOrganizationMultiCompany = new Listcell();
					CommonPopupBandbox commonPopupBandboxOrganizationMultiCompany = new CommonPopupBandbox();
					commonPopupBandboxOrganizationMultiCompany.setTitle("List of Organization");
					commonPopupBandboxOrganizationMultiCompany.setSearchText("Organization Name");
					commonPopupBandboxOrganizationMultiCompany.setHeaderLabel("Organization");
					commonPopupBandboxOrganizationMultiCompany.setSearchDelegate(new SerializableSearchDelegate<OrganizationDTO>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<OrganizationDTO> findBySearchCriteria(String searchCriteria, int limit, int offset) {
							List<OrganizationDTO> listOrganizationDTO = new ArrayList<OrganizationDTO>();
							if(lstLevelDefaultMultiCompany.getSelectedIndex() > -1) {
								OrganizationLevelDto select = lstLevelDefaultMultiCompany.getSelectedItem().getValue();
								listOrganizationDTO = organizationSetupServiceImpl.findByLevelIdAndOrgName(select.getId(), searchCriteria, currentResponsibilityCompanyDTO.getCompanyId(), limit, offset);
							}
							return listOrganizationDTO;
						}

						@Override
						public int countBySearchCriteria(String searchCriteria) {
							if(lstLevelAssignment.getSelectedIndex() > -1) {
								OrganizationLevelDto select = lstLevelAssignment.getSelectedItem().getValue();
								return organizationSetupServiceImpl.countByLevelIdAndOrgName(select.getId(), searchCriteria, currentResponsibilityCompanyDTO.getCompanyId());
							} else {
								return 0;
							}
						}

						@Override
						public void mapper(KeyValue keyValue, OrganizationDTO data) {
							keyValue.setKey(data.getId());
							keyValue.setValue(data.getName());
							keyValue.setDescription(data.getName());
						}

					});
					
					commonPopupBandboxOrganizationMultiCompany.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {
							KeyValue keyValue = (KeyValue) event.getData();
							if(keyValue != null) {
								data.setOrganizationId((long) keyValue.getKey());
								data.setOrganizationName(keyValue.getValue().toString());
							} else {
								data.setOrganizationId(null);
								data.setOrganizationName(null);
							}
						}
					});
					
//					includedOrganizationByMultiCompanyDTOList.remove(data);
//					includedOrganizationByMultiCompanyDTOList.add(data);
//					currentResponsibilityCompanyDTO.setIncludedOrganizationMultiCompanys(includedOrganizationByMultiCompanyDTOList);
					commonPopupBandboxOrganizationMultiCompany.setValue(data.getOrganizationName());
					commonPopupBandboxOrganizationMultiCompany.setReadonly(true);
					cellOrganizationMultiCompany.appendChild(commonPopupBandboxOrganizationMultiCompany);
					cellOrganizationMultiCompany.setParent(item);
				}
			};
		}
		
		private TabularValidationRule<IncludedOrganizationDTO> organizationOnMultiCompanyValidationRule() {
			return new TabularValidationRule<IncludedOrganizationDTO>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validate(IncludedOrganizationDTO data, List<String> errorRow) {
					if(chkAllowOtherUserMultiCompany.isChecked() == true) {
						if(data.getOrganizationId() == null) {
							errorRow.add("Organization name can't be empty");
						}
					}
					if(errorRow.size() > 0)	
						return false;
					return true;
				}		
			};
		}
		
		private void buildTbnOrganizationOnMultiCompany() {
			tbnOrganizationMultiCompany.setDataType(IncludedOrganizationDTO.class);
			tbnOrganizationMultiCompany.setModel(getOrganizationMultiCompanyModel());
			tbnOrganizationMultiCompany.setItemRenderer(getOrganizationMultiCompanyRenderer());
			tbnOrganizationMultiCompany.setValidationRule(organizationOnMultiCompanyValidationRule());
		}
		
		//End Build Organization on Multi Company Tabular Entry
		
		//Build FieldPermission Tabular Entry
		@Listen("onClick=#btnAddRowPermission")
		public void addRowPermission() {
			tbnPermission.addRow();
		}
		
		@Listen("onClick=#btnDeletePermission")
		public void deleteRowPermission() {
			tbnPermission.deleteRow();
		}
			
		private ListModelList<FieldPermission> getSpecialPermissionModel() {
			if(fieldPermissionDTOList == null) {
				fieldPermissionDTOList = new ArrayList<FieldPermission>();
				fieldPermissionDTOList.add(new FieldPermission());
			}
			
			ListModelList<FieldPermission> model = new ListModelList<FieldPermission>(fieldPermissionDTOList);
			model.setMultiple(true);
			return model;
		}
		
		private SerializableListItemRenderer<FieldPermission> getSpecialPermissionRenderer() {
			return new SerializableListItemRenderer<FieldPermission>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void render(Listitem item, final FieldPermission data, int index) throws Exception {
					new Listcell("").setParent(item);
					
					Listcell cellPermission = new Listcell();
					CommonPopupBandbox commonPopupBandboxPermission = new CommonPopupBandbox();
					commonPopupBandboxPermission.setTitle("List of Field Permission Key");
					commonPopupBandboxPermission.setSearchText("Key Name");
					commonPopupBandboxPermission.setHeaderLabel("Key");
					commonPopupBandboxPermission.setWidth("90%");
					commonPopupBandboxPermission.setSearchDelegate(new SerializableSearchDelegate<SpecialPermission>() {
						
						private static final long serialVersionUID = 1L;
						
						@Override
						public List<SpecialPermission> findBySearchCriteria(String searchCriteria, int limit, int offset) {
							SpecialPermissionExample specialPermissionExample = new SpecialPermissionExample();
							specialPermissionExample.createCriteria().andPermissionNameLikeInsensitive(searchCriteria);
							specialPermissionExample.setOrderByClause("PERMISSION_NAME asc");
							return responsibilityService.getSpecialPermissionByExample(specialPermissionExample, limit, offset);
						}
						
						@Override
						public int countBySearchCriteria(String searchCriteria) {
							SpecialPermissionExample specialPermissionExample = new SpecialPermissionExample();
							specialPermissionExample.createCriteria().andPermissionNameLikeInsensitive(searchCriteria);
							return responsibilityService.countSpecialPermissionByExample(specialPermissionExample);
						}
						
						@Override
						public void mapper(KeyValue keyValue, SpecialPermission data) {
							keyValue.setKey(data.getPermissionName());
							keyValue.setValue(data.getPermissionName());
							keyValue.setDescription(data.getPermissionName());
						}
					});
					
					commonPopupBandboxPermission.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
					
						private static final long serialVersionUID = 1L;
						
						@Override
						public void onEvent(Event event) throws Exception {
							KeyValue keyValue = (KeyValue) event.getData();
							if(keyValue != null) {
								data.setPermissionName(keyValue.getValue().toString());
							} else {
								data.setPermissionName(null);
							}
						}
					});
				
					KeyValue setKeyValuePermission = new KeyValue(data.getPermissionName(), data.getPermissionName(), data.getPermissionName());
					commonPopupBandboxPermission.setRawValue(setKeyValuePermission);
					commonPopupBandboxPermission.setReadonly(true);
					cellPermission.appendChild(commonPopupBandboxPermission);
					cellPermission.setParent(item);
				}
			};
		}
		
		private TabularValidationRule<FieldPermission> fieldPermissionValidationRule() {
			return new TabularValidationRule<FieldPermission>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean validate(FieldPermission data, List<String> errorRow) {
					if(data.getPermissionName() == null) {
						errorRow.add("Permission Name can't be empty");
					}
					if(errorRow.size() > 0)	
						return false;
					return true;
				}		
			};
		}
		
		private void bulidTbnFieldPermission() {
			tbnPermission.setDataType(FieldPermission.class);
			tbnPermission.setModel(getSpecialPermissionModel());
			tbnPermission.setItemRenderer(getSpecialPermissionRenderer());
			tbnPermission.setValidationRule(fieldPermissionValidationRule());
		}
		
		//End Build FieldPermission Tabular Entry
		
		private void loadCheckBoxAllowOtherUser(){
			chkAllowOtherUserMultiCompany.setChecked(currentResponsibilityCompanyDTO.isGrantAccessToOtherUser());
			if(chkAllowOtherUserMultiCompany.isChecked())
				enableOrDisableComponentMultiCoy(true);
			else
				enableOrDisableComponentMultiCoy(false);
			
			chkAllowOtherUserMultiCompany.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					if(chkAllowOtherUserMultiCompany.isChecked())
						enableOrDisableComponentMultiCoy(true);
					else
						enableOrDisableComponentMultiCoy(false);
				}
			});
		}
		
		private void enableOrDisableComponentMultiCoy(boolean operation){
			if(rdogrpViewAllOrganizationMultiCompany.getSelectedIndex() == 0 && operation){
				rowSecurityMultiCompany.setVisible(operation);
				rowGradeExclusionMultiCompany.setVisible(operation);
				rowFilterMultiCompany.setVisible(false);
				rowLevelMultiCompany.setVisible(false);
				rowOrganizationMultiCompany.setVisible(false);
			}else{
				rowSecurityMultiCompany.setVisible(operation);
				rowGradeExclusionMultiCompany.setVisible(operation);
				rowFilterMultiCompany.setVisible(operation);
				rowLevelMultiCompany.setVisible(operation);
				rowOrganizationMultiCompany.setVisible(operation);
			}
			
		}
		
		private void renderSecurityCompanyDetail(CommonPopupBandbox bandboxCompany) {
			//renderSecurityByAssignment
			loadRadioButtonSecurityByAssignment();
			buildTbnGradeExclusionOnSecurityByAssignment();
			loadLevelOrgByAssingment();
			loadListFilterSecurityByAssignment();
			loadIsRecursiveByAssignment();
			buildTbnOrganizationOnSecurityByAssignment();
			
			//renderSecurityMultiCompany
			loadCheckBoxAllowOtherUser();
			loadRadioButtonSecurityMultiCompany();
			buildTbnGradeExclusionOnMultiCompany();
			loadListLevelMultiCompany();
			loadListFilterMultiCompany();
			loadCheckBoxRecursiveMultiCompany();
			buildTbnOrganizationOnMultiCompany();
		}
		
		private void loadCheckBoxRecursiveMultiCompany(){
			chkRecursiveMultiCompany.setChecked(currentResponsibilityCompanyDTO.isRecursiveMultiCompany());
		}
		
		private void loadListFilterMultiCompany() {
			ListModelList<FilterType> model = new ListModelList<FilterType>(FilterType.values());
			lstFilterDefaultMultiCompany.setModel(model);
			lstFilterDefaultMultiCompany.renderAll();
			if(currentResponsibilityCompanyDTO.getFilterMultyCompany() != null) {
				lstFilterDefaultMultiCompany.setSelectedIndex(FilterType.getKey(currentResponsibilityCompanyDTO.getFilterMultyCompany()));
			} else {
				lstFilterDefaultMultiCompany.setSelectedIndex(0);
				currentResponsibilityCompanyDTO.setFilterMultyCompany(FilterType.getValue(lstFilterDefaultMultiCompany.getSelectedIndex()));
			}
		}
		
		private void loadListLevelMultiCompany() {
			List<OrganizationLevelDto> listModelLevelMultiCompany = organizationLevelSetupServiceImpl.findAll(currentResponsibilityCompanyDTO.getCompanyId());
			
			OrganizationLevelDto selectLevel = new OrganizationLevelDto();
			selectLevel.setId(-1L);
			selectLevel.setName("-Select-");
			listModelLevelMultiCompany.add(0, selectLevel);
			
			ListModelList<OrganizationLevelDto> model = new ListModelList<OrganizationLevelDto>(listModelLevelMultiCompany);
			lstLevelDefaultMultiCompany.setModel(model);
			lstLevelDefaultMultiCompany.renderAll();
			
			if(currentResponsibilityCompanyDTO.getOrganizationLevelIdMultiCompany() != null) {
				int idx = 0;
				for (OrganizationLevelDto organizationLevelDto : model) {
					if(organizationLevelDto.getId() == currentResponsibilityCompanyDTO.getOrganizationLevelIdMultiCompany()) {
						lstLevelDefaultMultiCompany.setSelectedIndex(idx);
					}
					idx++;
				}
			} else {
				lstLevelDefaultMultiCompany.setSelectedIndex(0);
				currentResponsibilityCompanyDTO.setOrganizationLevelIdMultiCompany(null);
			}
		}
		
		private void loadRadioButtonSecurityMultiCompany(){
			
			if(chkAllowOtherUserMultiCompany.isChecked()){
				rowSecurityMultiCompany.setVisible(true);
				rowGradeExclusionMultiCompany.setVisible(true);
				rowLevelMultiCompany.setVisible(false);
				rowFilterMultiCompany.setVisible(false);
				rowOrganizationMultiCompany.setVisible(false);
				
				if(currentResponsibilityCompanyDTO.getSecurityMultiCompany() != null){
					if(currentResponsibilityCompanyDTO.getSecurityMultiCompany().equals(SecurityType.VIEW_OWN.toString())){
						rdogrpViewAllOrganizationMultiCompany.setSelectedIndex(0);
						rowSecurityMultiCompany.setVisible(true);
						rowGradeExclusionMultiCompany.setVisible(true);
					} else if(currentResponsibilityCompanyDTO.getSecurityMultiCompany().equals(SecurityType.VIEW_HIERARCHY.toString())) {
						rdogrpViewAllOrganizationMultiCompany.setSelectedIndex(1);
						rowSecurityMultiCompany.setVisible(true);
						rowGradeExclusionMultiCompany.setVisible(true);
					} else if(currentResponsibilityCompanyDTO.getSecurityMultiCompany().equals(SecurityType.VIEW_BRANCH.toString())) {
						rdogrpViewAllOrganizationMultiCompany.setSelectedIndex(2);
						rowSecurityMultiCompany.setVisible(true);
						rowGradeExclusionMultiCompany.setVisible(true);
					} else if(currentResponsibilityCompanyDTO.getSecurityMultiCompany().equals(SecurityType.VIEW_FUNCTIONAL.toString())) {
						rdogrpViewAllOrganizationMultiCompany.setSelectedIndex(3);
						rowSecurityMultiCompany.setVisible(true);
						rowGradeExclusionMultiCompany.setVisible(true);
					} else if (currentResponsibilityCompanyDTO.getSecurityMultiCompany().equals(SecurityType.VIEW_ALL.toString())){
						rdogrpViewAllOrganizationMultiCompany.setSelectedIndex(4);
						rowSecurityMultiCompany.setVisible(true);
						rowGradeExclusionMultiCompany.setVisible(true);
						rowLevelMultiCompany.setVisible(true);
						rowFilterMultiCompany.setVisible(true);
						rowOrganizationMultiCompany.setVisible(true);
					}
						
				}
			}
			
			rdogrpViewAllOrganizationMultiCompany.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					if(rdogrpViewAllOrganizationMultiCompany.getSelectedIndex() == 4){
						rowSecurityMultiCompany.setVisible(true);
						rowGradeExclusionMultiCompany.setVisible(true);
						rowLevelMultiCompany.setVisible(true);
						rowFilterMultiCompany.setVisible(true);
						rowOrganizationMultiCompany.setVisible(true);
					}else{
						rowGradeExclusionMultiCompany.setVisible(true);
						rowLevelMultiCompany.setVisible(false);
						rowFilterMultiCompany.setVisible(false);
						rowOrganizationMultiCompany.setVisible(false);
					}
						
					
				}
			});
		}
		
		private void loadIsRecursiveByAssignment(){
			chkRecursiveAssignment.setChecked(currentResponsibilityCompanyDTO.isRecursiveByAssignment());
		}
		
		private void loadListFilterSecurityByAssignment() {		
			ListModelList<FilterType> model = new ListModelList<FilterType>(FilterType.values());
			lstFilterAssignment.setModel(model);
			lstFilterAssignment.renderAll();		
			if(currentResponsibilityCompanyDTO.getFilterByAssign() != null) {
				lstFilterAssignment.setSelectedIndex(FilterType.getKey(currentResponsibilityCompanyDTO.getFilterByAssign()));
			} else {
				lstFilterAssignment.setSelectedIndex(0);
				currentResponsibilityCompanyDTO.setFilterByAssign(FilterType.getValue(lstFilterAssignment.getSelectedIndex()));
			}
		}
		private void loadLevelOrgByAssingment(){
			List<OrganizationLevelDto> listLevelSecurityByAssignment = organizationLevelSetupServiceImpl.findAll(currentResponsibilityCompanyDTO.getCompanyId());
			OrganizationLevelDto selectLevel = new OrganizationLevelDto();
			selectLevel.setId(-1L);
			selectLevel.setName("-Select-");
			listLevelSecurityByAssignment.add(0, selectLevel);
			ListModelList<OrganizationLevelDto> model = new ListModelList<OrganizationLevelDto>(listLevelSecurityByAssignment);
			lstLevelAssignment.setModel(model);
			lstLevelAssignment.renderAll();
					
			if(currentResponsibilityCompanyDTO.getOrgLevelIdByAssign() != null) {
				int idx = 0;
				for (OrganizationLevelDto organizationLevelDto : model) {
					if(organizationLevelDto.getId().equals(currentResponsibilityCompanyDTO.getOrgLevelIdByAssign())) {
						lstLevelAssignment.setSelectedIndex(idx);
					}
					idx++;
				}
			} else {
				lstLevelAssignment.setSelectedIndex(0);
				currentResponsibilityCompanyDTO.setOrgLevelIdByAssign(((OrganizationLevelDto)lstLevelAssignment.getSelectedItem().getValue()).getId());
			}
		}
	
	private void loadRadioButtonSecurityByAssignment() {		
		rdogrpSecurityByAssignment.setSelectedIndex(SecurityType.getKey(currentResponsibilityCompanyDTO.getSecurityByAssign()));
		currentResponsibilityCompanyDTO.setSecurityByAssign(SecurityType.getValue(rdogrpSecurityByAssignment.getSelectedIndex()));
		if(rdogrpSecurityByAssignment.getSelectedIndex() == 5){ //view all
			disableOrEnabledComponenetNotViewAll(true);
		}else
			disableOrEnabledComponenetNotViewAll(false);
		rdogrpSecurityByAssignment.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				//currentResponsibilityCompanyDTO.setSecurityByAssign(SecurityType.getValue(rdogrpSecurityByAssignment.getSelectedIndex()));
				if(rdogrpSecurityByAssignment.getSelectedIndex() == 5){ //view all
					disableOrEnabledComponenetNotViewAll(true);
				}else
					disableOrEnabledComponenetNotViewAll(false);
		}
		});
	}
	
	private void disableOrEnabledComponenetNotViewAll(boolean operation){
		rowLevelByAssingment.setVisible(operation);
		rowFilterByAssingment.setVisible(operation);
		rowOrganizationByAssignment.setVisible(operation);
	}
	
	@Listen("onClick=#btnOkCompanyDetail")
	public void onOkCompanyDetail(){
		int idx = 0;
		currentResponsibilityCompanyDTO.setSecurityByAssign(SecurityType.getValue(rdogrpSecurityByAssignment.getSelectedIndex()));
		currentResponsibilityCompanyDTO.setGradeExclusionByAssignments(tbnGradeExclusionAssignment.getValue());
		if(lstLevelAssignment.getSelectedItem() != null)
			currentResponsibilityCompanyDTO.setOrgLevelIdByAssign(((OrganizationLevelDto)lstLevelAssignment.getSelectedItem().getValue()).getId());
		currentResponsibilityCompanyDTO.setRecursiveByAssignment(chkRecursiveAssignment.isChecked());
		if(lstFilterAssignment.getSelectedItem() != null)
			currentResponsibilityCompanyDTO.setFilterByAssign(FilterType.getValue(lstFilterAssignment.getSelectedIndex()));
		
		if(tbnOrganizationAssignment.getValue() != null)
			currentResponsibilityCompanyDTO.setIncludedOrganizationByAssignments(tbnOrganizationAssignment.getValue());
		
		currentResponsibilityCompanyDTO.setGrantAccessToOtherUser(chkAllowOtherUserMultiCompany.isChecked());
		if(rdogrpViewAllOrganizationMultiCompany.getSelectedItem() != null){
			if(rdogrpViewAllOrganizationMultiCompany.getSelectedIndex() == 0)
				currentResponsibilityCompanyDTO.setSecurityMultiCompany(SecurityType.VIEW_OWN.getValue());
			else if (rdogrpViewAllOrganizationMultiCompany.getSelectedIndex() == 1)
				currentResponsibilityCompanyDTO.setSecurityMultiCompany(SecurityType.VIEW_HIERARCHY.getValue());
			else if (rdogrpViewAllOrganizationMultiCompany.getSelectedIndex() == 2)
				currentResponsibilityCompanyDTO.setSecurityMultiCompany(SecurityType.VIEW_BRANCH.getValue());
			else if (rdogrpViewAllOrganizationMultiCompany.getSelectedIndex() == 3)
				currentResponsibilityCompanyDTO.setSecurityMultiCompany(SecurityType.VIEW_FUNCTIONAL.getValue());
			else
				currentResponsibilityCompanyDTO.setSecurityMultiCompany(SecurityType.VIEW_ALL.getValue());
		}
			
		if(chkAllowOtherUserMultiCompany.isChecked()){
			currentResponsibilityCompanyDTO.setGradeExclusionMultiCompanys(tbnGradeMultiCompany.getValue());
			if(lstLevelDefaultMultiCompany.getSelectedItem() != null)
				currentResponsibilityCompanyDTO.setOrganizationLevelIdMultiCompany(((OrganizationLevelDto)lstLevelDefaultMultiCompany.getSelectedItem().getValue()).getId());
			if(lstLevelDefaultMultiCompany.getSelectedItem() != null)
				currentResponsibilityCompanyDTO.setFilterMultyCompany(FilterType.getValue(lstFilterDefaultMultiCompany.getSelectedIndex()));
			currentResponsibilityCompanyDTO.setRecursiveMultiCompany(chkRecursiveMultiCompany.isChecked());
			currentResponsibilityCompanyDTO.setIncludedOrganizationMultiCompanys(tbnOrganizationMultiCompany.getValue());
		}
		
		List<ResponsibilityCompanyDTO> tmp = tbnCompany.getValue();
		for (ResponsibilityCompanyDTO res : tmp) {
			if(res.getCompanyId().equals(currentResponsibilityCompanyDTO.getCompanyId())){
				tmp.remove(idx);
				break;
			}
			idx++;
		}
		tmp.add(currentResponsibilityCompanyDTO);
		responsibilityCompanyDTOList = tmp;
		ListModelList<ResponsibilityCompanyDTO> model = new ListModelList<ResponsibilityCompanyDTO>(responsibilityCompanyDTOList);
		model.setMultiple(true);
		tbnCompany.setModel(model);
		tbnCompany.renderAll();
		grbCompanyDetails.setVisible(false);
		disableCompanyTabularEntry(false);
	}
	
	@Listen("onClick=#btnCancelCompanyDtail")
	public void onClickHideCompanyDetail(){
		grbCompanyDetails.setVisible(false);
		disableCompanyTabularEntry(false);
	}
		
	@Listen("onClick=#btnNext")
	public void nextPage(){
		
		if(responsibilityDTO == null){
			responsibilityDTO = new ResponsibilityDTO();
			responsibilityDTO.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			responsibilityDTO.setCreationDate(new Date());
		}
		
		responsibilityDTO.setResponsibilityName(txtRespName.getValue());
		
		if(txtMenuName.getKeyValue() != null){
			Menu menu = (Menu)((KeyValue)txtMenuName.getKeyValue()).getValue();
			responsibilityDTO.setMenu(menu);
			responsibilityDTO.setMenuId((Long)txtMenuName.getKeyValue().getKey());
		}
		if(txtTaskGroup.getKeyValue() != null){
			responsibilityDTO.setTaskGroupId((Long)txtTaskGroup.getKeyValue().getKey());
			responsibilityDTO.setTaskGroupName((String)txtTaskGroup.getKeyValue().getValue());
		}
		if(dtbDateFrom.getValue() != null)
			responsibilityDTO.setDateFrom(DateUtil.truncate(dtbDateFrom.getValue()));
		if(dtbDateTo.getValue() != null)
			responsibilityDTO.setDateTo(DateUtil.truncate(dtbDateTo.getValue()));
		if(lstBusinessGroup.getSelectedItem() != null){
			responsibilityDTO.setBusinessGroupId(((BusinessGroup)lstBusinessGroup.getSelectedItem().getValue()).getGroupId());
		}
		
		responsibilityDTO.setItemExclusions(tbnItemExclusions.getValue());
		responsibilityDTO.setResponsibilityCompanyDto(tbnCompany.getValue());
		responsibilityDTO.setFieldPermissions(tbnPermission.getValue());
		
		
		responsibilityDTO.setLastUpdateDate(new Date());
		responsibilityDTO.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		
		try {
			clearErrorMessage();
			
			responsibilityValidator.validateFirstPage(responsibilityDTO);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("responsibility", responsibilityDTO);
			param.put("previousObject", previousObject);
			param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
			Executions.createComponents("~./hcms/systemadmin/function_access_control.zul", getSelf().getParent(), param);
			getSelf().detach();
		} catch (ValidationException e) {
			showErrorMessage(e);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		
	}
	
	private void showErrorMessage(ValidationException e){
		lblErrorName.setValue(e.getMessage(ResponsibilityValidator.RESPONSIBILITY_NOT_EMPTY));
		if(!e.getMessage(ResponsibilityValidator.RESPONSIBILITY_NOT_UNIQUE).equals(""))
			lblErrorName.setValue(e.getMessage(ResponsibilityValidator.RESPONSIBILITY_NOT_UNIQUE));
		lblErrorMenu.setValue(e.getMessage(ResponsibilityValidator.MENU_NAME_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(dtbDateFrom, e.getMessage(ResponsibilityValidator.DATE_FROM_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(dtbDateTo, e.getMessage(ResponsibilityValidator.DATE_TO_NOT_SAME_DATE_FROM));
		if(!e.getMessage(ResponsibilityValidator.ITEM_EXCLUSIONS_NAME_CANNOT_BE_EMPTY).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.ITEM_EXCLUSIONS_NAME_CANNOT_BE_EMPTY)));
		if(!e.getMessage(ResponsibilityValidator.COMPANY_EMPTY).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.COMPANY_EMPTY)));
		if(!e.getMessage(ResponsibilityValidator.COMPANY_NAME_EMPTY).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.COMPANY_NAME_EMPTY)));
		if(!e.getMessage(ResponsibilityValidator.ITEM_EXCLUSIONS_DOUBLE).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.ITEM_EXCLUSIONS_DOUBLE)));
		if(!e.getMessage(ResponsibilityValidator.COMPANY_DOUBLE).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.COMPANY_DOUBLE)));
		if(!e.getMessage(ResponsibilityValidator.GRADE_MULTI_DOUBLE).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.GRADE_MULTI_DOUBLE)));
		if(!e.getMessage(ResponsibilityValidator.ORGANIZATION_MULTI_DOUBLE).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.ORGANIZATION_MULTI_DOUBLE)));
		if(!e.getMessage(ResponsibilityValidator.GRADE_BY_ASSIGNMENT_DOUBLE).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.GRADE_BY_ASSIGNMENT_DOUBLE)));
		if(!e.getMessage(ResponsibilityValidator.ORGANIZATION_BY_ASSIGN_DOUBLE).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.ORGANIZATION_BY_ASSIGN_DOUBLE)));
		if(!e.getMessage(ResponsibilityValidator.FIELD_PERMISSIONS_NAME_EMPTY).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.FIELD_PERMISSIONS_NAME_EMPTY)));
		if(!e.getMessage(ResponsibilityValidator.FIELD_PERMSSIONS_DOUBLE).equals(""))
			detailErrorMessage.appendChild(createlabel(e.getMessage(ResponsibilityValidator.FIELD_PERMSSIONS_DOUBLE)));
	}
	
	private void clearErrorMessage(){
		lblErrorName.setValue("");
		ErrorMessageUtil.clearErrorMessage(dtbDateFrom);
		ErrorMessageUtil.clearErrorMessage(dtbDateTo);
		lblErrorMenu.setValue("");
		detailErrorMessage.getChildren().clear();
	}
	
	private Label createlabel(String message){
		Label label = new Label(message);
		label.setStyle("color:red");
		return label;
	}
	
	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show("Do you want cancel?", "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/systemadmin/responsibility_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
	@Listen("onSelect=#lstLevelAssignment")
	public void selectOrganizationLevel() {
		((ListModelList<?>) tbnOrganizationAssignment.getModel()).clear();
	}
	
	@Listen("onSelect=#lstLevelDefaultMultiCompany")
	public void selectOrganizationLevelMultiCompany() {
		((ListModelList<?>) tbnOrganizationMultiCompany.getModel()).clear();
	}
	
	private void clearResponsibilityCompany(ResponsibilityCompanyDTO responsibilityCompany) {
		responsibilityCompany.setFilterByAssign(null);
		responsibilityCompany.setFilterMultyCompany(null);
		if (responsibilityCompany.getGradeExclusionByAssignments() != null)
			responsibilityCompany.getGradeExclusionByAssignments().clear();
		if (responsibilityCompany.getGradeExclusionMultiCompanys() != null)
			responsibilityCompany.getGradeExclusionMultiCompanys().clear();
		if (responsibilityCompany.getIncludedOrganizationByAssignments() != null)
			responsibilityCompany.getIncludedOrganizationByAssignments().clear();
		if (responsibilityCompany.getIncludedOrganizationMultiCompanys() != null)
			responsibilityCompany.getIncludedOrganizationMultiCompanys().clear();
		responsibilityCompany.setOrgLevelIdByAssign(null);
		responsibilityCompany.setOrganizationLevelIdMultiCompany(null);
		responsibilityCompany.setGrantAccessToOtherUser(false);
		responsibilityCompany.setRecursiveByAssignment(false);
		responsibilityCompany.setRecursiveMultiCompany(false);
	}
}
