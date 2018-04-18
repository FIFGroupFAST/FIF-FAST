package co.id.fifgroup.workstructure.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.AbstractComponent;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.VersionValidator;
import co.id.fifgroup.workstructure.constant.HierarchyType;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxFrom;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.workstructure.dto.OrgHierElementDTO;
import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.validation.OrganizationHierarchyValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class OrganizationHierarchySetupComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(OrganizationHierarchySetupComposer.class);
	
	@Wire
	private Listbox lstType;
	@Wire
	private Checkbox chkDeptOwn;
	@Wire
	private Listbox lstVersion;
	@Wire
	private Textbox txtHierName;
	@Wire
	private Textbox txtHierDesc;
	@Wire
	private DateBoxFrom dtbFrom;
	@Wire
	private DateBoxMax dtbTo;
	@Wire
	private CommonPopupDoubleBandbox bnbOrgParent;
	@Wire
	private Button btnUp;
	@Wire
	private Label lblNumOfSubordinates;
	@Wire
	private TabularEntry<OrgHierElementDTO> lstElement;
	@Wire
	private Button btnAddRowElement;
	@Wire
	private Button btnDeleteElement;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnDelete;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationHierarchySetupService organizationHierarchySetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private OrgHierarchyDTO selectedHier;
	private OrgHierElementDTO rootElement;
	private ListModelList<OrgHierElementDTO> model;
	private List<OrgHierElementDTO> hierElements;
	private List<OrgHierElementDTO> elementsTemp;
	private ListModelList<HierarchyType> modelHierarchyType;
	private Long parentId;
	private boolean isParentHeadOffice;
	private Integer subordinateRoot = 0;
	private List<Integer> versionList;
	private FunctionPermission functionPermission;
	private ListModelList<String> modelVersion;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		chkDeptOwn.setDisabled(true);
		elementsTemp = new ArrayList<OrgHierElementDTO>();
		selectedHier = (OrgHierarchyDTO) arg.get(OrgHierarchyDTO.class.getName());
		if(selectedHier != null)
			selectedHier = organizationHierarchySetupServiceImpl.findByIdAndVersionNumber(selectedHier.getId(), selectedHier.getVersionNumber());
		populateHierType();
		buildTabularElement();
		readParameter(selectedHier);
		populateOrgParent(bnbOrgParent);
		bnbOrgParent.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				KeyValue keyValue = (KeyValue) event.getData();
				if(keyValue != null) {
					OrganizationDTO selectedOrg = organizationSetupServiceImpl.findById((Long) keyValue.getKey());
					hierElements.clear();
					elementsTemp.clear();
					parentId = (Long) keyValue.getKey();
					rootElement = new OrgHierElementDTO();
					rootElement.setOrganizationId(parentId);
					rootElement.setParentId(parentId);
					isParentHeadOffice = selectedOrg.getIsHeadOffice();
					btnUp.setDisabled(true);
					lblNumOfSubordinates.setValue(String.valueOf(subordinateRoot));
					lstElement.setModel(getModel());
				}
			}
		});
		
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			lstVersion.setDisabled(true);
			dtbFrom.setDisabled(true);
			disabledComponent();
		}
		if(!functionPermission.isDeletable()){
			btnDelete.setDisabled(true);
		}
			
	}
	
	private void readParameter(OrgHierarchyDTO selectedHier) {
		if(selectedHier != null) {
			populateVersion();
			renderHierarchy(selectedHier);
			disabledHeaderComponent();
			checkDisabledComponent(selectedHier);
		}			
		else {
			List<String> versions = new ArrayList<>();
			versions.add(Labels.getLabel("common.create"));
			ListModelList<String> model = new ListModelList<>(versions);
			lstVersion.setModel(model);
			lstVersion.setSelectedIndex(model.indexOf(Labels.getLabel("common.create")));
			btnSave.setDisabled(false);
			btnDelete.setDisabled(true);
			dtbTo.setValue(DateUtil.MAX_DATE);
			lblNumOfSubordinates.setValue("0");
			selectedHier = new OrgHierarchyDTO();
		}
	}
	
	private void checkDisabledComponent(OrgHierarchyDTO selectedHier) {
		if(selectedHier.isFuture()) {
			enabledComponent();
			if(selectedHier.getOrgHierType().equals(HierarchyType.STRUCTURAL.toString()))
				dtbTo.setDisabled(true);
		} else if(selectedHier.isCurrent()) {
			if(organizationHierarchySetupServiceImpl.isHaveFuture(selectedHier.getId())) {
				disabledComponent();
				if(selectedHier.getOrgHierType().equals(HierarchyType.STRUCTURAL.toString()))
					dtbTo.setDisabled(true);
			}
			else {
				disabledComponent();
				btnSave.setDisabled(false);
				dtbTo.setDisabled(false);
				if(selectedHier.getOrgHierType().equals(HierarchyType.STRUCTURAL.toString()))
					dtbTo.setDisabled(true);
			}
		} else if(DateUtils.truncate(selectedHier.getDateTo(), Calendar.DATE).compareTo(DateUtils.truncate(new Date(), Calendar.DATE)) < 0) {
			disabledComponent();
		}
	}
	
	private void renderHierarchy(OrgHierarchyDTO selectedHier) {
		txtHierName.setValue(selectedHier.getOrgHierName());
		txtHierDesc.setValue(selectedHier.getDescription());
		dtbFrom.setValue(selectedHier.getDateFrom());
		dtbTo.setValue(selectedHier.getDateTo());
		if(selectedHier.getIsDeptOwner() != null)
			chkDeptOwn.setChecked(selectedHier.getIsDeptOwner());
		for(HierarchyType hierarchyType : modelHierarchyType) {
			if(selectedHier.getOrgHierType().equals(hierarchyType.toString())) {
				modelHierarchyType.addToSelection(hierarchyType);
				break;
			}
		}
		if(!selectedHier.getElements().isEmpty()) {
			Organization org;
			for(OrgHierElementDTO element : selectedHier.getElements()) {
				if(element.getParentId() != null && element.getOrganizationId() != null) {
					if(element.getParentId().equals(element.getOrganizationId())) {
						org = organizationSetupServiceImpl.findOrganizationById(element.getOrganizationId());
						if(org != null) {
							bnbOrgParent.setRawValue(new KeyValue(element.getOrganizationId(), org.getOrganizationName(), org.getOrganizationName()));
						}
						rootElement = element;
						parentId = element.getParentId();
						break;
					}
				}
			}
			hierElements.clear();
			renderSubOrdinate(selectedHier.getElements(), true);
			elementsTemp.addAll(selectedHier.getElements());
		}
	}
	
	private void populateVersion() {
		versionList = organizationHierarchySetupServiceImpl.findVersionsById(selectedHier.getId());
		List<String> versions = new ArrayList<>();
		for(Integer version : versionList)
			versions.add(String.valueOf(version));
		versions.add(Labels.getLabel("common.create"));
		
		modelVersion = new ListModelList<>(versions);
		lstVersion.setModel(modelVersion);
		lstVersion.setSelectedIndex(versionList.indexOf(selectedHier.getVersionNumber()));
		
		if(versionList.size() <= selectedHier.getVersionNumber()) {
			dtbTo.setDisabled(false);
			btnSave.setDisabled(false);
		}
	}
	
	@Listen("onSelect = #lstVersion")
	public void onVersionChange() {
		String selectedVersion = (String)lstVersion.getSelectedItem().getValue();
		clearErrorMessage();
		if(Labels.getLabel("common.create").equals(selectedVersion)) {
			if(!organizationHierarchySetupServiceImpl.isFutureExist(selectedHier.getId())) {
				Integer latestVersion = versionList.get(versionList.size() - 1);
				selectedHier = organizationHierarchySetupServiceImpl.findByIdAndVersionNumber(selectedHier.getId(), latestVersion);
				selectedHier.setVersionId(null);
				enabledComponent();
				elementsTemp.clear();
				renderHierarchy(selectedHier);
				dtbFrom.setValue(DateUtil.add(new Date(), Calendar.DATE, 1));
				dtbTo.setValue(DateUtil.MAX_DATE);
				if(selectedHier.getOrgHierType().equals(HierarchyType.STRUCTURAL.toString()))
					dtbTo.setDisabled(true);
				btnDelete.setDisabled(true);
			} else {
				Messagebox.show(Labels.getLabel("common.cannotCreateFutureExist"));
				modelVersion.addToSelection(selectedHier.getVersionNumber().toString());
			}
			
		} else {
			selectedHier = organizationHierarchySetupServiceImpl.findByIdAndVersionNumber(
					selectedHier.getId(), Integer.valueOf(selectedVersion));
			checkDisabledComponent(selectedHier);
			elementsTemp.clear();
			renderHierarchy(selectedHier);
		}
	}
	
	private void buildTabularElement() {
		lstElement.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstElement.setDataType(OrgHierElementDTO.class);
		lstElement.setModel(getModel());
		lstElement.setItemRenderer(getListItemRenderer());
		lstElement.setValidationRule(getValidationRule());
	}
	
	private ListModelList<OrgHierElementDTO> getModel() {
		if(hierElements == null)
			hierElements = new ArrayList<OrgHierElementDTO>();
		
		Collections.sort(hierElements, new Comparator<OrgHierElementDTO>() {

			@Override
			public int compare(OrgHierElementDTO o1, OrgHierElementDTO o2) {
				return String.valueOf(o1.getOrgName()).compareTo(String.valueOf(o2.getOrgName()));
			}
		});
		
		model = new ListModelList<OrgHierElementDTO>(hierElements);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<OrgHierElementDTO> getListItemRenderer() {
		return new SerializableListItemRenderer<OrgHierElementDTO>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void render(Listitem item, final OrgHierElementDTO data, int index) throws Exception {
				new Listcell().setParent(item);
				final Label lblSub = new Label();
				
				Listcell listcellOrg = new Listcell();
				final CommonPopupDoubleBandbox bnbOrg = new CommonPopupDoubleBandbox();
				bnbOrg.setWidth("95%");
				populateOrgElement(bnbOrg);
				bnbOrg.setHeaderLabel1(Labels.getLabel("common.organizationCode"));
				bnbOrg.setHeaderLabel2(Labels.getLabel("common.organizationName"));
				bnbOrg.setSearchText1(Labels.getLabel("common.organizationCode"));
				bnbOrg.setSearchText2(Labels.getLabel("common.organizationName"));
				bnbOrg.setTitle(Labels.getLabel("common.listOfOrganization"));
				bnbOrg.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -5788144121529892249L;

					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue keyValue = (KeyValue) event.getData();
						if(keyValue != null) {
							if(elementsTemp.contains(data))
								elementsTemp.remove(data);
							data.setOrganizationId((Long) keyValue.getKey());
							data.setOrgName((String) keyValue.getDescription());
							data.setParentId(parentId);
							data.setSubordinate(0);
							if(validateOrganization(data))
								elementsTemp.add(data);
							else {
								Messagebox.show(Labels.getLabel("wos.thisOrganizationIsUsed"), Labels.getLabel("title.information"), Messagebox.OK, Messagebox.INFORMATION);
								bnbOrg.setRawValue(null);
							}
							lblSub.setValue("0");
						} else {
							data.setSubordinate(0);
							lblSub.setValue("0");
						}
					}
				});
				bnbOrg.setValue(data.getOrgName());
				String selectedVersion = (String)lstVersion.getSelectedItem().getValue();
				if(selectedHier != null) {
					if(selectedHier.isFuture() || Labels.getLabel("common.create").equals(selectedVersion)){
						bnbOrg.setDisabled(false);
					}else{
						bnbOrg.setDisabled(true);
					}
				}
				listcellOrg.appendChild(bnbOrg);
				listcellOrg.setParent(item);
				
				Listcell lstcellSub = new Listcell();
				lblSub.setValue(data.getSubordinate() != null ? String.valueOf(data.getSubordinate()) : "0");
				lstcellSub.appendChild(lblSub);
				lstcellSub.setParent(item);
				
				Listcell listcellButton = new Listcell();
				Button btnDown = new Button("down");
				listcellButton.appendChild(btnDown);
				listcellButton.setParent(item);
				btnDown.addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						if(data.getOrganizationId() != null)
							onDown(data);
					}
					
				});
				
				//Start WWN ADD 14061810344788_Perbaikan org hier tdk ada jika is head office
				if(data != null && data.getParentId() != null)
					isParentHeadOffice = organizationSetupServiceImpl.isHeadOffice(data.getParentId(), dtbFrom.getValue() == null ? new Date() : dtbFrom.getValue());
				//END WWN ADD 14061810344788_Perbaikan org hier tdk ada jika is head office
			}
		};
	}
	
	private boolean validateOrganization(OrgHierElementDTO data) {
		if(data.getOrganizationId().equals(bnbOrgParent.getKeyValue().getKey()))
			return false;

		for(OrgHierElementDTO element : elementsTemp) {
			if(element.getOrganizationId().equals(data.getOrganizationId()))
				return false;
		}				
		return true;
	}
	
	private void onDown(OrgHierElementDTO data) {
		if(bnbOrgParent.getKeyValue() != null) {
			if(data.getOrganizationId().equals(rootElement.getOrganizationId()))
				btnUp.setDisabled(true);
			else 
				btnUp.setDisabled(false);
			
			if(selectedHier != null)
				getNewChildrens(data.getParentId());
			
			KeyValue keyValue = new KeyValue(data.getOrganizationId(), data.getOrgName(), data.getOrgName());
			bnbOrgParent.setRawValue(keyValue);
			hierElements.clear();
			renderSubOrdinate(elementsTemp, true);
			parentId = data.getOrganizationId();
			isParentHeadOffice = organizationSetupServiceImpl.isHeadOffice(data.getOrganizationId(), dtbFrom.getValue() == null ? new Date() : dtbFrom.getValue());			
		}
	}
	
	private void getNewChildrens(Long parentId) {
		List<OrgHierElementDTO> toRemove = new ArrayList<OrgHierElementDTO>();
		for(OrgHierElementDTO element : elementsTemp) {
			if(element.getParentId() != null && parentId != null && element.getOrganizationId() != null) {
				if(element.getParentId().equals(parentId) && !element.getOrganizationId().equals(parentId)) {
					toRemove.add(element);
				}
			}
		}
		elementsTemp.removeAll(toRemove);
		elementsTemp.addAll(lstElement.getValue());
	}
	
	@Listen("onClick = #btnUp")
	public void onUp() {
		if(selectedHier != null)
			getNewChildrens((Long)bnbOrgParent.getKeyValue().getKey());
		
		hierElements.clear();
		for(OrgHierElementDTO element : elementsTemp) {
			if(element.getOrganizationId().equals(bnbOrgParent.getKeyValue().getKey())) {
				if (!element.getParentId().equals(element.getOrganizationId())) {
					parentId = element.getParentId();
					Organization org = organizationSetupServiceImpl.findNameById(parentId);
					KeyValue keyValue = new KeyValue(element.getParentId(), org.getOrganizationName(), org.getOrganizationName());
					bnbOrgParent.setRawValue(keyValue);
					isParentHeadOffice = organizationSetupServiceImpl.isHeadOffice(element.getParentId(), dtbFrom.getValue() == null ? new Date() : dtbFrom.getValue());
				}
			}
		}
		renderSubOrdinate(elementsTemp, true);
	}
	
	private void renderSubOrdinate(List<OrgHierElementDTO> elements, Boolean isAddToElements) {
		int parentSubOrdinate = 0;
		for(OrgHierElementDTO element : elements) {
			if(element.getParentId() != null && element.getOrganizationId() != null && bnbOrgParent.getKeyValue() != null) {
				if(element.getParentId().equals((Long) bnbOrgParent.getKeyValue().getKey()) && !element.getParentId().equals(element.getOrganizationId())) {
					Organization org = organizationSetupServiceImpl.findNameById(element.getOrganizationId());
					element.setOrgName(org.getOrganizationName());
					int subOrdinate = 0;
					for(OrgHierElementDTO nextElement : elements) {
						if(element.getOrganizationId().equals(nextElement.getParentId())) {
							subOrdinate++;
							for(OrgHierElementDTO subElement : elements) {
								if(nextElement.getOrganizationId().equals(subElement.getParentId())) {
									subOrdinate++;
								}
							}
						}
					}
					element.setSubordinate(subOrdinate);
					if(isAddToElements)
						hierElements.add(element);
					parentSubOrdinate = parentSubOrdinate + subOrdinate;
				}
			}
		}
		parentSubOrdinate = parentSubOrdinate + hierElements.size();
		lstElement.setModel(getModel());
		lblNumOfSubordinates.setValue(String.valueOf(parentSubOrdinate));
	}
	
	private TabularValidationRule<OrgHierElementDTO> getValidationRule() {
		return new TabularValidationRule<OrgHierElementDTO>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean validate(OrgHierElementDTO data, List<String> errorRow) {
				
				if(errorRow.size() > 0)	
					return false;
				return true;
			}			
		};
	}
	
	private void populateOrgElement(CommonPopupDoubleBandbox bnb) {
		bnb.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<OrganizationDTO>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<OrganizationDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				
				if(!lstType.getSelectedItem().getValue().equals(HierarchyType.SELECT)) {
					if(bnbOrgParent.getKeyValue() == null) {
						return null;
					}
					else if(lstType.getSelectedItem().getValue().equals(HierarchyType.FUNCTIONAL)) {
						OrganizationDTO org = new OrganizationDTO();
						org.setCode(searchCriteria1);
						org.setName(searchCriteria2);
						org.setEffectiveOnDate(dtbFrom.getValue() != null ? dtbFrom.getValue() : new Date());
						return organizationSetupServiceImpl.findByExample(org, limit, offset);
					}
					else if(lstType.getSelectedItem().getValue().equals(HierarchyType.STRUCTURAL)) {
						OrganizationDTO org = new OrganizationDTO();
						org.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
						org.setCode(searchCriteria1);
						org.setName(searchCriteria2);
						org.setEffectiveOnDate(dtbFrom.getValue() != null ? dtbFrom.getValue() : new Date());
						org.setParentId(parentId);
						org.setIsHeadOffice(isParentHeadOffice);
						return organizationSetupServiceImpl.findOrganizationBySequenceLevel(org, limit, offset);
					}
				}
				return null;
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				if(!lstType.getSelectedItem().getValue().equals(HierarchyType.SELECT)) {
					if(bnbOrgParent.getKeyValue() == null) {
						return 0;
					}
					else if(lstType.getSelectedItem().getValue().equals(HierarchyType.FUNCTIONAL)) {
						OrganizationDTO org = new OrganizationDTO();
						org.setCode(searchCriteria1);
						org.setName(searchCriteria2);
						org.setEffectiveOnDate(dtbFrom.getValue() != null ? dtbFrom.getValue() : new Date());
						return organizationSetupServiceImpl.countByExample(org);
					}
					else if(lstType.getSelectedItem().getValue().equals(HierarchyType.STRUCTURAL)) {
						OrganizationDTO org = new OrganizationDTO();
						org.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
						org.setCode(searchCriteria1);
						org.setName(searchCriteria2);
						org.setEffectiveOnDate(dtbFrom.getValue() != null ? dtbFrom.getValue() : new Date());
						org.setParentId(parentId);
						org.setIsHeadOffice(isParentHeadOffice);
						return organizationSetupServiceImpl.countOrganizationBySequenceLevel(org);
					}
				}
				return 0;
			}

			@Override
			public void mapper(KeyValue keyValue, OrganizationDTO data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getCode());
				keyValue.setDescription(data.getName());
			}
		});
	}
	
	private void populateOrgParent(CommonPopupDoubleBandbox bnb) {
		bnb.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<OrganizationDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				OrganizationDTO org = new OrganizationDTO();
				org.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				org.setCode(searchCriteria1);
				org.setName(searchCriteria2);
				org.setEffectiveOnDate(new Date());
				return organizationSetupServiceImpl.findByExample(org, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				OrganizationDTO org = new OrganizationDTO();
				org.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				org.setCode(searchCriteria1);
				org.setName(searchCriteria2);
				org.setEffectiveOnDate(new Date());
				return organizationSetupServiceImpl.countByExample(org);
			}

			@Override
			public void mapper(KeyValue keyValue, OrganizationDTO data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getCode());
				keyValue.setDescription(data.getName());
			}
		});
	}
	
	@Listen("onSelect = #lstType")
	public void onSelectHierarchyType() {
		if(lstType.getSelectedItem().getValue().equals(HierarchyType.STRUCTURAL)) {
			chkDeptOwn.setChecked(false);
			chkDeptOwn.setDisabled(true);
			dtbTo.setValue(DateUtil.MAX_DATE);
			dtbTo.setDisabled(true);
		} else if(lstType.getSelectedItem().getValue().equals(HierarchyType.FUNCTIONAL)) {
			chkDeptOwn.setChecked(true);
			chkDeptOwn.setDisabled(false);
			dtbTo.setDisabled(false);
		} else {
			chkDeptOwn.setChecked(false);
			chkDeptOwn.setDisabled(true);
		}
	}
	
	private void populateHierType() {
		modelHierarchyType = new ListModelList<HierarchyType>(HierarchyType.values());
		lstType.setModel(modelHierarchyType);
		lstType.renderAll();
		modelHierarchyType.addToSelection(HierarchyType.SELECT);
		
		lstType.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				bnbOrgParent.setRawValue(null);
				hierElements.clear();
				elementsTemp.clear();
				lstElement.setModel(getModel());
			}
			
		});
	}
	
	@Listen("onClick = #btnAddRowElement")
	public void onAddRowElement() {
		lstElement.addRow();
	}
	
	@Listen("onClick = #btnDeleteElement")
	public void onDeleteRowElement() {
		Set<OrgHierElementDTO> removes = model.getSelection();
		List<OrgHierElementDTO> children = new ArrayList<>();
		for(OrgHierElementDTO element : removes) {
			elementsTemp.remove(element);
			children.addAll(elementsTemp);
			for(OrgHierElementDTO child : children) {
				if(element.getOrganizationId() != null && element.getOrganizationId().equals(child.getParentId()))
					elementsTemp.remove(child);
			}
			children.clear();
		}
		lstElement.deleteRow();
	}
	
	@Listen("onClick = button#btnSave")
	public void onSave() {
		populateHierarchy();
		String confirmationMsg = "";
		if(selectedHier.getId() == null)
			confirmationMsg = Labels.getLabel("common.confirmationMessage");
		else 
			confirmationMsg = Labels.getLabel("common.confirmationSaveVersion");
		
		Messagebox.show(confirmationMsg, Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						clearErrorMessage();
						organizationHierarchySetupServiceImpl.save(selectedHier);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						doBack();
					} catch (ValidationException ve) {
						logger.error(ve.getAllMessages());
						showErrorMessage(ve.getConstraintViolations());
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(e.getMessage());
					}	
				}
			}			
		});		
	}
	
	private void populateHierarchy() {
		if(selectedHier == null)
			selectedHier = new OrgHierarchyDTO();
		
		if(selectedHier.getId() == null) {
			if(rootElement != null) {
				if(elementsTemp == null)
					elementsTemp = new ArrayList<>();
				elementsTemp.add(rootElement);
			}
		}
		selectedHier.setIsDeptOwner(chkDeptOwn.isChecked());
		selectedHier.setOrgHierName(txtHierName.getValue().toUpperCase());
		selectedHier.setDescription(txtHierDesc.getValue());
		selectedHier.setDateFrom(dtbFrom.getValue());
		selectedHier.setDateTo(dtbTo.getValue());
		selectedHier.setOrgHierType(((HierarchyType) lstType.getSelectedItem().getValue()).equals(HierarchyType.SELECT) ? null : ((HierarchyType) lstType.getSelectedItem().getValue()).toString().toUpperCase());
		selectedHier.setElements(elementsTemp);
		selectedHier.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		selectedHier.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedHier.setCreationDate(new Date());
		selectedHier.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedHier.setLastUpdateDate(new Date());
	}
	
	private void showErrorMessage(Map<String, String> maps) {
		if(maps.get(OrganizationHierarchyValidator.HIERARCHY_NAME) != null) {
			ErrorMessageUtil.setErrorMessage(txtHierName, 
				maps.get(OrganizationHierarchyValidator.HIERARCHY_NAME));
		}
		if(maps.get(OrganizationHierarchyValidator.HIERARCHY_TYPE) != null) {
			ErrorMessageUtil.setErrorMessage(lstType, 
				maps.get(OrganizationHierarchyValidator.HIERARCHY_TYPE));
		}
		if(maps.get(VersionValidator.DATE_FROM) != null) {
			ErrorMessageUtil.setErrorMessage(dtbFrom, 
				maps.get(VersionValidator.DATE_FROM));
		}
		if(maps.get(OrganizationHierarchyValidator.ROOT) != null) {
			ErrorMessageUtil.setErrorMessage(btnUp, 
				maps.get(OrganizationHierarchyValidator.ROOT));
		}
		if(maps.get(OrganizationHierarchyValidator.ELEMENT) != null) {
			if(maps.containsKey(OrganizationHierarchyValidator.ELEMENT)) {
				Label lblErrorMsg = new Label(maps.get(OrganizationHierarchyValidator.ELEMENT));
				lblErrorMsg.setStyle("color:red");
				Hlayout hlayout = new Hlayout();
				hlayout.appendChild(lblErrorMsg);
				hlayout.appendChild(new Separator());
				lstElement.getParent().insertBefore(hlayout, lstElement);
			}
		}
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtHierName);
		ErrorMessageUtil.clearErrorMessage(lstType);
		ErrorMessageUtil.clearErrorMessage(dtbFrom);
		ErrorMessageUtil.clearErrorMessage(btnUp);
		if(lstElement.getPreviousSibling() instanceof Hlayout) 
			lstElement.getParent().removeChild(lstElement.getPreviousSibling());
	}
	
	private void disabledHeaderComponent() {
		lstType.setDisabled(true);
		chkDeptOwn.setDisabled(true);
		txtHierName.setDisabled(true);
		dtbFrom.setDisabled(true);
		btnUp.setDisabled(true);
	}
	
	private void enabledComponent() {
		txtHierDesc.setDisabled(false);
		dtbFrom.setDisabled(false);
		dtbTo.setDisabled(false);
		bnbOrgParent.setDisabled(false);
		btnAddRowElement.setDisabled(false);
		btnDeleteElement.setDisabled(false);
		btnSave.setDisabled(false);
		btnDelete.setDisabled(false);
		disabledElement(false);
		lstElement.setItemRenderer(getListItemRenderer());
		//TODO enabled lov org
	}
	
	private void disabledComponent() {
		txtHierDesc.setDisabled(true);
		dtbFrom.setDisabled(true);
		dtbTo.setDisabled(true);
		bnbOrgParent.setDisabled(true);
		btnAddRowElement.setDisabled(true);
		btnDeleteElement.setDisabled(true);
		btnSave.setDisabled(true);
		btnDelete.setDisabled(true);
		disabledElement(true);
		lstElement.setItemRenderer(getListItemRenderer());
		//TODO disabled lov org
	}
	
	private void disabledElement(Boolean isDisabled) {
		for(Listitem listitem : lstElement.getItems()) {
			for(Component component : listitem.getChildren()) {
				if(component instanceof Listcell) {
					for(Component inputElement : component.getChildren()) {
						if(!(inputElement instanceof Label)) {
							if(inputElement instanceof CommonPopupDoubleBandbox) {
									AbstractComponent ac = (AbstractComponent) inputElement;
									try {
										Method m = ac.getClass().getMethod("setDisabled", Boolean.TYPE);
										try {
											m.invoke(ac, Boolean.valueOf(isDisabled));
										} catch (IllegalAccessException
												| IllegalArgumentException
												| InvocationTargetException e) {
										}
									} catch (NoSuchMethodException e) {
									} catch (SecurityException e) {
									}
								
							} else {
								AbstractComponent ac = (AbstractComponent) inputElement;
								try {
									Method m = ac.getClass().getMethod("setDisabled", Boolean.TYPE);
									try {
										m.invoke(ac, Boolean.valueOf(isDisabled));
									} catch (IllegalAccessException
											| IllegalArgumentException
											| InvocationTargetException e) {
									}
								} catch (NoSuchMethodException e) {
								} catch (SecurityException e) {
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Listen("onClick = button#btnDelete")
	public void onDelete() {
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					organizationHierarchySetupServiceImpl.delete(selectedHier);
					Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
					doBack();
				}
			}
		});
	}
	
	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					doBack();
				}
			}
		});
	}
	
	public void doBack() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/organization_hierarchy_setup_inquiry.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

}
