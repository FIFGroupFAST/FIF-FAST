package co.id.fifgroup.workstructure.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.LocationSetupService;
import co.id.fifgroup.workstructure.service.OrganizationLevelSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import com.google.common.base.Strings;

import co.id.fifgroup.basicsetup.common.OtherInfoComponentValue;
import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.basicsetup.service.OtherInfoComponentService;
import co.id.fifgroup.core.domain.Kecamatan;
import co.id.fifgroup.core.domain.KecamatanExample;
import co.id.fifgroup.core.domain.Kelurahan;
import co.id.fifgroup.core.domain.KelurahanExample;
import co.id.fifgroup.core.domain.ZipCode;
import co.id.fifgroup.core.domain.KecamatanExample.Criteria;
import co.id.fifgroup.core.service.CityService;
import co.id.fifgroup.core.service.KecamatanService;
import co.id.fifgroup.core.service.KelurahanService;
import co.id.fifgroup.core.service.ProvinceService;
import co.id.fifgroup.core.service.SecurityService;
//import co.id.fifgroup.core.service.WorkingScheduleService;
import co.id.fifgroup.core.ui.CommonPopupZipCodeBandbox;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.LookupCriteria;
import co.id.fifgroup.core.ui.lookup.LookupWindow;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.lookup.ZipCodeSearchCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.LocationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationContactDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;
import co.id.fifgroup.workstructure.dto.OtherInfoDTO;
import co.id.fifgroup.workstructure.renderer.OrganizationContactItemRenderer;
import co.id.fifgroup.workstructure.validation.OrganizationContactValidator;
import co.id.fifgroup.workstructure.validation.OrganizationValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class BranchSplitDetailComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String KPP_LOOKUP = "MST_KPP";
	private static final String COUNTRY_LOOKUP = "MST_COUNTRY";
	private static final String ORGANIZATION_OTHER_INFO = "ORGANIZATION";
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient ProvinceService provinceServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CityService cityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient KecamatanService kecamatanServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient KelurahanService kelurahanServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OtherInfoComponentService otherInfoComponentServiceImpl;
	/*@WireVariable(rewireOnActivate=true)
	private transient WorkingScheduleService workingScheduleServiceImpl;*/
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationLevelSetupService organizationLevelSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LocationSetupService locationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient JobSetupService jobSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationValidator organizationValidator;
	
	
	@Wire private Textbox txtOrganizationCode;
	@Wire private Textbox txtOrganizationName;
	@Wire private Textbox txtDesc;
	@Wire private CommonPopupBandbox bnbOrgLevel;
	@Wire private Textbox txtNpwp;
	@Wire private LookupWindow bnbKpp;
	@Wire private CommonPopupDoubleBandbox bnbLocation;
	@Wire private CommonPopupDoubleBandbox bnbHeadOrg;
	@Wire private CommonPopupDoubleBandbox bnbOrgSpv;
	@Wire private Checkbox chkHO;
	@Wire private CommonPopupBandbox bnbBranchCode;
	@Wire private CommonPopupBandbox bnbCostCenter;
	@Wire private Label lblCountry;
	@Wire private Label lblProvince;
	@Wire private Label lblCity;
	@Wire private CommonPopupBandbox bnbKecamatan;
	@Wire private CommonPopupBandbox bnbKelurahan;
	@Wire private CommonPopupZipCodeBandbox bnbZipCode;
	@Wire private Textbox txtAddress;
	@Wire private TabularEntry<OrganizationContactDTO> lstContact;
	@Wire private Button btnAddContact;
	@Wire private Button btnDeleteContact;
	@Wire private Tabpanel tpOtherInfo;
	@Wire private Listbox lstWorkingSchedule;
	
	
	private OrganizationDTO selectedOrg;
	private String cityCode;
	private ListModelList<KeyValue> modelWorkingSchedule;
	private OtherInfoComponent otherInfoComponent;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		if(arg.containsKey(OrganizationDTO.class.getName())){
			selectedOrg = (OrganizationDTO) arg.get(OrganizationDTO.class.getName());
			loadContactTabular();
			loadOtherInfo();
			populateLookup();
			populateValue();
		}
		
	}
	
	private void populateLookup() {
		populateOrgLevel();
		populateKpp();
		populateLocation();
		populateJob();
		populateOrganizationByCompany();
		populateBranchCode();
		populateCostCentre();
		populateKecamatan();
		populateKelurahanByKecamatan();
		populateZipCode();
		populateWorkingSchedule();
	}
	
	private void populateValue(){
		txtOrganizationCode.setValue(selectedOrg.getCode());
		txtOrganizationName.setValue(selectedOrg.getName());
		txtDesc.setValue(selectedOrg.getDescription());
		
		setSelectedLookup(bnbLocation, selectedOrg.getLocation().getId() != null ? String.valueOf(selectedOrg.getLocation().getId()) : null, selectedOrg.getLocation().getLocationName());
		bnbKpp.setValueByCode(KPP_LOOKUP, selectedOrg.getKppCode());
		txtNpwp.setValue(selectedOrg.getNpwp());
		setSelectedLookup(bnbOrgLevel, selectedOrg.getLevelCode(), selectedOrg.getLevelName());
		setSelectedLookup(bnbHeadOrg, selectedOrg.getOrganizationHeadId() != null ? String.valueOf(selectedOrg.getOrganizationHeadId()) : null, selectedOrg.getOrganizationHead());
		setSelectedLookup(bnbOrgSpv, selectedOrg.getOrganizationSpvId() != null ? String.valueOf(selectedOrg.getOrganizationSpvId()) : null, selectedOrg.getOrganizationSpv());
		chkHO.setChecked(selectedOrg.getIsHeadOffice() != null ? selectedOrg.getIsHeadOffice() : false);
		
		List<KeyValue> glCodes = organizationSetupServiceImpl.getGlCodes(selectedOrg.getBranchCode(), Integer.MAX_VALUE, 0);
		if(!glCodes.isEmpty()) {
			KeyValue kv = new KeyValue(glCodes.get(0).getKey(), glCodes.get(0).getDescription(), 
					glCodes.get(0).getKey().toString().concat(" - ").concat(glCodes.get(0).getDescription().toString()));
			bnbBranchCode.setRawValue(kv);
		}else{
			KeyValue kv = new KeyValue(selectedOrg.getBranchCode(), "Other", selectedOrg.getBranchCode()+"-"+"Other");
			bnbBranchCode.setRawValue(kv);
		}
			
		
		List<KeyValue> costCenter = organizationSetupServiceImpl.getCostCenter(selectedOrg.getCostCenterCode(), Integer.MAX_VALUE, 0);
		if(!costCenter.isEmpty()) {
			KeyValue kv = new KeyValue(costCenter.get(0).getKey(), costCenter.get(0).getDescription(), 
					costCenter.get(0).getKey().toString().concat(" - ").concat(costCenter.get(0).getDescription().toString()));
			bnbCostCenter.setRawValue(kv);
		}
		lblCountry.setValue(getCountry(selectedOrg.getLocation().getCountryCode()));
		lblProvince.setValue(provinceServiceImpl.getProvinceValue(selectedOrg.getLocation().getProvinceCode()));
		lblCity.setValue(cityServiceImpl.getCityValue(selectedOrg.getLocation().getCityCode()));
		cityCode = selectedOrg.getLocation().getCityCode();	
		setSelectedLookup(bnbKecamatan, selectedOrg.getKecamatanCode(), getKecValue(selectedOrg.getKecamatanCode()));
		setSelectedLookup(bnbKelurahan, selectedOrg.getKelurahanCode(), getKelValue(selectedOrg.getKelurahanCode()));
		ZipCode zipCode = new ZipCode();
		zipCode.setZipCode(selectedOrg.getZipCode() != null ? selectedOrg.getZipCode() : "");
		zipCode.setSubZipCode(selectedOrg.getSubZipCode() != null ? selectedOrg.getSubZipCode() : "");
		bnbZipCode.setRawValue(zipCode);
		if(selectedOrg.getWorkingScheduleId() != null) {
			for(KeyValue kv : modelWorkingSchedule) {
				Long wsId = (Long)kv.getKey();
				if(wsId != null && wsId.equals(selectedOrg.getWorkingScheduleId())) {
					modelWorkingSchedule.addToSelection(kv);
					break;
				}
			}
		}
		txtAddress.setValue(selectedOrg.getAddress());
//		lstContact.setModel(new ListModelList<>(organization.getContacts()));
		if(selectedOrg.getOrganizationInfos() != null) {
			for(OtherInfoDTO orgInfo : selectedOrg.getOrganizationInfos()) {
				otherInfoComponentServiceImpl.setOtherInfoDetailValueToComponent(otherInfoComponent, orgInfo.getInfoId(), orgInfo.getInfoValue());
			}
			selectedOrg.setOrganizationInfos(selectedOrg.getOrganizationInfos());
		}
	}
	
	private String getKecValue(String kecCode) {
		if(kecCode == null) kecCode = "";
		KecamatanExample example = new KecamatanExample();
		example.createCriteria().andKecamatanCodeLikeInsensitive(kecCode);
		if(!kecCode.equals(""))
			return kecamatanServiceImpl.getKecamatansByExample(example).get(0).getKecamatan();
		return "";
	}
	
	private String getKelValue(String kelCode) {
		if(kelCode == null) kelCode = "";
		KelurahanExample example = new KelurahanExample();
		example.createCriteria().andKelurahanCodeLikeInsensitive(kelCode);
		if(!kelCode.equals(""))
			return kelurahanServiceImpl.getKelurahansByExample(example).get(0).getKelurahan();
		return "";
	}
	private String getCountry(String countryCode) {
		KeyValue keyValue = lookupServiceImpl.getLookup(COUNTRY_LOOKUP, countryCode, securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(keyValue != null)
			return (String) keyValue.getDescription();
		return "";
	}
	
	private void setSelectedLookup(Component component, String code, String name) {
		if(code == null && name == null || code == null && name.equals("")) {
			if(component instanceof CommonPopupBandbox)
				((CommonPopupBandbox) component).setRawValue(null);
			else if(component instanceof LookupWindow)
				((LookupWindow) component).setRawValue(null);
			else if(component instanceof CommonPopupDoubleBandbox)
				((CommonPopupDoubleBandbox) component).setRawValue(null);					
		} else {
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(code);
			keyValue.setValue(name);
			keyValue.setDescription(name);
			if(component instanceof CommonPopupBandbox)
				((CommonPopupBandbox) component).setRawValue(keyValue);
			else if(component instanceof LookupWindow)
				((LookupWindow) component).setRawValue(keyValue);
			else if(component instanceof CommonPopupDoubleBandbox)
				((CommonPopupDoubleBandbox) component).setRawValue(keyValue);
		}
	}
	
	@Listen("onClick = button#btnOk")
	public void onOk(){
		clearErrorMessage();
		setValue();
		if(lstContact.validate()) {
			try {
				organizationValidator.validate(selectedOrg);
				BranchSplitGeneratorComposer prevComposer = (BranchSplitGeneratorComposer)arg.get(BranchSplitGeneratorComposer.class.getName()); 
				prevComposer.setSelectedOrg(selectedOrg);
				getSelf().detach();
			} catch (ValidationException vex) {
				showErrorMessage(vex.getConstraintViolations());
			} catch (Exception e) {
				Messagebox.show(e.getMessage());
			}
		}
		
	}
	
	private void showErrorMessage(Map<String, String> maps) {
		if(maps.get(OrganizationValidator.ORGANIZATION_CODE) != null) {
			ErrorMessageUtil.setErrorMessage(txtOrganizationCode, 
				maps.get(OrganizationValidator.ORGANIZATION_CODE));
		}
		if(maps.get(OrganizationValidator.ORGANIZATION_NAME) != null) {
			ErrorMessageUtil.setErrorMessage(txtOrganizationName, 
				maps.get(OrganizationValidator.ORGANIZATION_NAME));
		}
		if(maps.get(OrganizationValidator.ORGANIZATION_LEVEL) != null) {
			ErrorMessageUtil.setErrorMessage(bnbOrgLevel, 
				maps.get(OrganizationValidator.ORGANIZATION_LEVEL));
		}
		if(maps.get(OrganizationValidator.ADDRESS) != null) {
			ErrorMessageUtil.setErrorMessage(txtAddress, 
					maps.get(OrganizationValidator.ADDRESS));
		}
		if(maps.get(OrganizationValidator.BRANCH_CODE) != null) {
			ErrorMessageUtil.setErrorMessage(bnbBranchCode, 
				maps.get(OrganizationValidator.BRANCH_CODE));
		}
		if(maps.get(OrganizationValidator.COST_CENTRE_CODE) != null) {
			ErrorMessageUtil.setErrorMessage(bnbCostCenter, 
				maps.get(OrganizationValidator.COST_CENTRE_CODE));
		}
		if(maps.get(OrganizationValidator.HEAD_OF_ORGANIZATION) != null) {
			ErrorMessageUtil.setErrorMessage(bnbHeadOrg, 
				maps.get(OrganizationValidator.HEAD_OF_ORGANIZATION));
		}
		if(maps.get(OrganizationValidator.KPP_CODE) != null) {
			ErrorMessageUtil.setErrorMessage(bnbKpp, 
				maps.get(OrganizationValidator.KPP_CODE));
		}
		if(maps.get(OrganizationValidator.LOCATION) != null) {
			ErrorMessageUtil.setErrorMessage(bnbLocation, 
				maps.get(OrganizationValidator.LOCATION));
		}
		if(maps.get(OrganizationValidator.ZIP_CODE) != null) {
			ErrorMessageUtil.setErrorMessage(bnbZipCode, 
				maps.get(OrganizationValidator.ZIP_CODE));
		}
		if(maps.get(OrganizationValidator.NPWP) != null) {
			ErrorMessageUtil.setErrorMessage(txtNpwp, 
				maps.get(OrganizationValidator.NPWP));
		}
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtOrganizationCode);
		ErrorMessageUtil.clearErrorMessage(txtOrganizationName);
		ErrorMessageUtil.clearErrorMessage(txtAddress);
		ErrorMessageUtil.clearErrorMessage(bnbBranchCode);
		ErrorMessageUtil.clearErrorMessage(bnbCostCenter);
		ErrorMessageUtil.clearErrorMessage(bnbHeadOrg);
		ErrorMessageUtil.clearErrorMessage(bnbKpp);
		ErrorMessageUtil.clearErrorMessage(bnbLocation);
		ErrorMessageUtil.clearErrorMessage(bnbOrgLevel);
		ErrorMessageUtil.clearErrorMessage(bnbOrgSpv);
		ErrorMessageUtil.clearErrorMessage(bnbZipCode);
		ErrorMessageUtil.clearErrorMessage(txtNpwp);
	}
	
	@Listen("onClick = #btnAddContact") 
	public void addContact() {
		lstContact.addRow();
	}
	
	@Listen("onClick = #btnDeleteContact") 
	public void deleteContact() {
		lstContact.deleteRow();
	}
	
	private void loadContactTabular(){
		lstContact.setDataType(OrganizationContactDTO.class);
		lstContact.setModel(new ListModelList<>(selectedOrg.getContacts()));
		lstContact.setItemRenderer(new OrganizationContactItemRenderer(selectedOrg));
		lstContact.setValidationRule(new OrganizationContactValidator());
	}
	
	private void populateWorkingSchedule() {
		/*lstWorkingSchedule.setItemRenderer(new SerializableListItemRenderer<KeyValue>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, KeyValue data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(String.valueOf(data.getValue())));
			}
			
		});
		KeyValue select = new KeyValue(null, Labels.getLabel("common.select"), Labels.getLabel("common.select"));
		List<KeyValue> workingSchedules = workingScheduleServiceImpl.getWorkingScheduleByCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		modelWorkingSchedule = new ListModelList<>();
		modelWorkingSchedule.add(select);
		modelWorkingSchedule.addAll(workingSchedules);
		lstWorkingSchedule.setModel(modelWorkingSchedule);
		lstWorkingSchedule.renderAll();
		modelWorkingSchedule.addToSelection(select);*/
	}
	
	private void populateOrgLevel() {
		bnbOrgLevel.setSearchDelegate(new SerializableSearchDelegate<OrganizationLevelDto>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationLevelDto> findBySearchCriteria(
					String searchCriteria, int limit, int offset) {
					OrganizationLevelDto levelDto = new OrganizationLevelDto();
					levelDto.setName(searchCriteria);
					levelDto.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					levelDto.setEffectiveOnDate(DateUtil.truncate(new Date()));
					return organizationLevelSetupServiceImpl.findByExample(levelDto);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
					OrganizationLevelDto levelDto = new OrganizationLevelDto();
					levelDto.setName(searchCriteria);
					levelDto.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					levelDto.setEffectiveOnDate(DateUtil.truncate(new Date()));
					return organizationLevelSetupServiceImpl.countByExample(levelDto);
			}

			@Override
			public void mapper(KeyValue keyValue, OrganizationLevelDto data) {
				keyValue.setKey(data.getCode());
				keyValue.setValue(data.getName());
				keyValue.setDescription(data.getName());
			}
		});
	}
	
	private void populateLocation() {
		bnbLocation.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<LocationDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<LocationDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				LocationDTO location = new LocationDTO();
				location.setLocationCode(searchCriteria1);
				location.setLocationName(searchCriteria2);
				location.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				location.setEffectiveDate(DateUtil.truncate(selectedOrg.getEffectiveOnDate()));
				return locationSetupServiceImpl.findActiveLocationForLov(location, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				LocationDTO location = new LocationDTO();
				location.setLocationCode(searchCriteria1);
				location.setLocationName(searchCriteria2);
				location.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				location.setEffectiveDate(DateUtil.truncate(selectedOrg.getEffectiveOnDate()));
				return locationSetupServiceImpl.countActiveLocationForLov(location);
			}

			@Override
			public void mapper(KeyValue keyValue, LocationDTO data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getLocationCode());
				keyValue.setDescription(data.getLocationName());
			}		
		});
		
		bnbLocation.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				KeyValue keyValue = (KeyValue) event.getData();
				if(keyValue != null) {
					LocationDTO location = locationSetupServiceImpl.findById((Long) keyValue.getKey());
					if(location != null) {
						lblCountry.setValue(getCountry(location.getCountryCode()));
						lblProvince.setValue(provinceServiceImpl.getProvinceValue(location.getProvinceCode()));
						lblCity.setValue(cityServiceImpl.getCityValue(location.getCityCode()));
						cityCode = location.getCityCode();
					}
				}
			}
			
		});
	}
	
	
	private void populateKpp() {
		bnbKpp.setTitle(Labels.getLabel("wos.listOfKPP"));
		bnbKpp.setHeaderLabel(Labels.getLabel("wos.kpp"));
		bnbKpp.setSearchText(Labels.getLabel("wos.kpp"));
		bnbKpp.setLookupCriteria(new LookupCriteria() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getParentDetailCode() {
				return null;
			}
			
			@Override
			public String getLookupName() {
				return KPP_LOOKUP;
			}
		});
	}
	
	private void populateBranchCode() {
		bnbBranchCode.setSearchDelegate(new SerializableSearchDelegate<KeyValue>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				return  organizationSetupServiceImpl.getGlCodes(searchCriteria, limit, offset);
			}

			
			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return organizationSetupServiceImpl.countGlCodes(searchCriteria);
			}

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				keyValue.setKey(data.getKey());
				keyValue.setValue(data.getValue());
				keyValue.setDescription(data.getKey().toString().concat(" - ").concat(data.getDescription().toString()));
			}
			
		});
	}
	
	private void populateCostCentre() {
		bnbCostCenter.setSearchDelegate(new SerializableSearchDelegate<KeyValue>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				return  organizationSetupServiceImpl.getCostCenter(searchCriteria, limit, offset);
			}

			
			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return organizationSetupServiceImpl.countCostCenter(searchCriteria);
			}

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				keyValue.setKey(data.getKey());
				keyValue.setValue(data.getValue());
				keyValue.setDescription(data.getKey().toString().concat(" - ").concat(data.getDescription().toString()));
			}
			
		});
	}
	
	private void populateOrganizationByCompany() {
		bnbOrgSpv.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<OrganizationDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				OrganizationDTO org = new OrganizationDTO();
				org.setCompanyName(searchCriteria1);
				org.setName(searchCriteria2);
				org.setGroupId(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
				org.setEffectiveOnDate(new Date());
				return organizationSetupServiceImpl.findOrganizationByCompany(org, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				OrganizationDTO org = new OrganizationDTO();
				org.setCompanyName(searchCriteria1);
				org.setName(searchCriteria2);
				org.setGroupId(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
				org.setEffectiveOnDate(new Date());
				return organizationSetupServiceImpl.countOrganizationByCompany(org);
			}
			
			@Override
			public void mapper(KeyValue keyValue, OrganizationDTO data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getCompanyName());
				keyValue.setDescription(data.getName());
			}
		});
	}
	
	private void populateZipCode() {
		bnbZipCode.setZipCodeSearchCriteria(new ZipCodeSearchCriteria() {
			
			@Override
			public String getProvinceCode() {
				LocationDTO location = selectedOrg.getLocation();
				if(location != null && location.getProvinceCode() != null) {
					return location.getProvinceCode();
				}
				return null;
			}
			
			@Override
			public String getKelurahanCode() {
				KeyValue keyValueKelurahan = bnbKelurahan.getKeyValue();
				if(keyValueKelurahan != null && keyValueKelurahan.getKey() != null) {
					return keyValueKelurahan.getKey().toString();
				}
				return null;
			}
			
			@Override
			public String getKecamatanCode() {
				KeyValue keyValueKecamatan = bnbKecamatan.getKeyValue();
				if(keyValueKecamatan != null && keyValueKecamatan.getKey() != null) {
					return keyValueKecamatan.getKey().toString();
				}
				return null;
			}
			
			@Override
			public String getCityCode() {
				LocationDTO location = selectedOrg.getLocation();
				if(location != null && location.getCityCode() != null) {
					return location.getCityCode();
				}
				return null;
			}
		});
	}
	
	private void populateKelurahanByKecamatan() {
		bnbKelurahan.setSearchDelegate(new SerializableSearchDelegate<Kelurahan>() {

			private static final long serialVersionUID = 1701578432617248999L;

			@Override
			public List<Kelurahan> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				KelurahanExample kelurahanExample = new KelurahanExample();
				co.id.fifgroup.core.domain.KelurahanExample.Criteria kelurahanCriteria = kelurahanExample.createCriteria();
				kelurahanCriteria.andKelurahanLikeInsensitive(searchCriteria);
				
				if(bnbKecamatan.getKeyValue() != null) {
					kelurahanCriteria.andKecCodeEqualTo((String)bnbKecamatan.getKeyValue().getKey());
				}
				return kelurahanServiceImpl.getKelurahansByExample(kelurahanExample, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				KelurahanExample kelurahanExample = new KelurahanExample();
				co.id.fifgroup.core.domain.KelurahanExample.Criteria kelurahanCriteria = kelurahanExample.createCriteria();
				kelurahanCriteria.andKelurahanLikeInsensitive(searchCriteria);
				
				if(bnbKecamatan.getKeyValue() != null) {
					kelurahanCriteria.andKecCodeEqualTo((String)bnbKecamatan.getKeyValue().getKey());
				}
				return kelurahanServiceImpl.getCountKelurahanByExample(kelurahanExample);
			}

			@Override
			public void mapper(KeyValue keyValue, Kelurahan data) {
				keyValue.setKey(data.getKelurahanCode());
				keyValue.setValue(data.getKelurahan());
				keyValue.setDescription(data.getKelurahan());
			}			
		});
		
		bnbKelurahan.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				bnbZipCode.setRawValue(new ZipCode());
			}
		});
	}
	
	private void populateKecamatan() {
		bnbKecamatan.setSearchDelegate(new SerializableSearchDelegate<Kecamatan>() {

			private static final long serialVersionUID = 1701578432617248999L;

			@Override
			public List<Kecamatan> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				KecamatanExample kecamatanExample = new KecamatanExample();
				Criteria kecamatanCriteria = kecamatanExample.createCriteria();
				kecamatanCriteria.andKecamatanLikeInsensitive(searchCriteria);
				if(cityCode != null)
					kecamatanCriteria.andCityCodeEqualTo(cityCode);
				
				return kecamatanServiceImpl.getKecamatansByExample(kecamatanExample, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				KecamatanExample kecamatanExample = new KecamatanExample();
				Criteria kecamatanCriteria = kecamatanExample.createCriteria();
				kecamatanCriteria.andKecamatanLikeInsensitive(searchCriteria);
				if(cityCode != null)
					kecamatanCriteria.andCityCodeEqualTo(cityCode);
				
				return kecamatanServiceImpl.getCountKecamatanByExample(kecamatanExample);
			}

			@Override
			public void mapper(KeyValue keyValue, Kecamatan data) {
				keyValue.setKey(data.getKecamatanCode());
				keyValue.setValue(data.getKecamatan());
				keyValue.setDescription(data.getKecamatan());
			}
			
		});
		
		bnbKecamatan.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				bnbKelurahan.setRawValue(new KeyValue());
				bnbZipCode.setRawValue(new ZipCode());
			}
			
		});
	}
	
	private void populateJob() {
		bnbHeadOrg.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<JobDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<JobDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				JobDTO job = new JobDTO();
				job.setJobCode(searchCriteria1);
				job.setJobName(searchCriteria2);
				job.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				job.setEffectiveDate(DateUtil.truncate(new Date()));
				return jobSetupServiceImpl.getActiveJob(job, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				JobDTO job = new JobDTO();
				job.setJobCode(searchCriteria1);
				job.setJobName(searchCriteria2);
				job.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				job.setEffectiveDate(DateUtil.truncate(new Date()));
				return jobSetupServiceImpl.countActiveJob(job);
			}
			
			@Override
			public void mapper(KeyValue keyValue, JobDTO data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getJobCode());
				keyValue.setDescription(data.getJobName());
			}
		});
	}
	
	private void loadOtherInfo() {
		otherInfoComponent = otherInfoComponentServiceImpl.getOtherInfoComponent(ORGANIZATION_OTHER_INFO, securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(otherInfoComponent != null) {
			tpOtherInfo.appendChild(otherInfoComponent.getComponent());			
		}
	}
	
	private void setValue() {
		selectedOrg.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		selectedOrg.setOrganizationUuid(UUID.randomUUID());
		selectedOrg.setCode(txtOrganizationCode.getValue().toUpperCase());
		selectedOrg.setName(txtOrganizationName.getValue());
		selectedOrg.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedOrg.setCreationDate(new Date());
		selectedOrg.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		selectedOrg.setLastUpdateDate(new Date());
		selectedOrg.setDescription(txtDesc.getValue());
		selectedOrg.setDateFrom(selectedOrg.getEffectiveOnDate());
		selectedOrg.setDateTo(DateUtil.MAX_DATE);
		selectedOrg.setLevelCode(bnbOrgLevel.getKeyValue() != null ? (String) bnbOrgLevel.getKeyValue().getKey() : "");
		selectedOrg.setLevelName(bnbOrgLevel.getKeyValue() != null ? (String) bnbOrgLevel.getKeyValue().getDescription() : "");
		selectedOrg.setNpwp(Strings.isNullOrEmpty(txtNpwp.getValue()) ? null : txtNpwp.getValue());
		selectedOrg.setKppCode(bnbKpp.getKeyValue() != null ? String.valueOf(bnbKpp.getKeyValue().getKey()) : "");
		selectedOrg.setLocation(getLocation());
		selectedOrg.setOrganizationHeadId(bnbHeadOrg.getKeyValue() != null ? Long.parseLong(bnbHeadOrg.getKeyValue().getKey().toString()) : null);
		selectedOrg.setOrganizationHead(bnbHeadOrg.getKeyValue() != null ? bnbHeadOrg.getKeyValue().getDescription().toString() : null);
		selectedOrg.setOrganizationHeadCode(bnbHeadOrg.getKeyValue() != null ? bnbHeadOrg.getKeyValue().getValue().toString() : null);
		selectedOrg.setOrganizationSpvId(bnbOrgSpv.getKeyValue() != null ? Long.parseLong(bnbOrgSpv.getKeyValue().getKey().toString()) : null);
		selectedOrg.setOrganizationSpv(bnbOrgSpv.getKeyValue() != null ? String.valueOf(bnbOrgSpv.getKeyValue().getDescription()) : null);
		selectedOrg.setIsHeadOffice(chkHO.isChecked());
		selectedOrg.setBranchCode(bnbBranchCode.getKeyValue() != null ? String.valueOf(bnbBranchCode.getKeyValue().getKey()) : "");
		selectedOrg.setCostCenterCode(bnbCostCenter.getKeyValue() != null ? String.valueOf(bnbCostCenter.getKeyValue().getKey()) : "");
		selectedOrg.setCostCenterName(bnbCostCenter.getKeyValue() != null ? String.valueOf(bnbCostCenter.getKeyValue().getDescription()) : "");
		selectedOrg.setKecamatanCode(bnbKecamatan.getKeyValue() != null ? (String) bnbKecamatan.getKeyValue().getKey() : "");
		selectedOrg.setKelurahanCode(bnbKelurahan.getKeyValue() != null ? (String) bnbKelurahan.getKeyValue().getKey() : "");
		selectedOrg.setZipCode(bnbZipCode.getValueZipcode() != null ? (String) bnbZipCode.getValueZipcode().getZipCode() : "");
		selectedOrg.setSubZipCode(bnbZipCode.getValueZipcode() != null ? (String) bnbZipCode.getValueZipcode().getSubZipCode() : "");
		selectedOrg.setAddress(txtAddress.getValue());
		selectedOrg.setWorkingScheduleId((Long)((KeyValue)lstWorkingSchedule.getSelectedItem().getValue()).getKey() != null ? (Long)((KeyValue)lstWorkingSchedule.getSelectedItem().getValue()).getKey() : null);
		selectedOrg.setContacts(lstContact.getValue());
		selectedOrg.setUpload(false);
		
		if(otherInfoComponent != null) {
			final List<OtherInfoDTO> orgOtherInfos = new ArrayList<OtherInfoDTO>();
			otherInfoComponentServiceImpl.setOtherInfoDetailValue(otherInfoComponent, new OtherInfoComponentValue() {
				
				private static final long serialVersionUID = 4891725512579095929L;
				
				@Override
				public void setValue(Long infoId, String value) {
					OtherInfoDTO orgInfo = new OtherInfoDTO();
					orgInfo.setInfoId(infoId);
					orgInfo.setInfoValue(value);
					orgInfo.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
					orgInfo.setCreationDate(new Date());
					orgInfo.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
					orgInfo.setLastUpdateDate(new Date());
					orgOtherInfos.add(orgInfo);
				}
			});
			selectedOrg.setOrganizationInfos(orgOtherInfos);
			selectedOrg.setOtherInfoComponent(otherInfoComponent);
		}
	}
	
	private LocationDTO getLocation() {
		
		Long locationId = null;
		if((bnbLocation.getKeyValue().getKey() instanceof String))
			locationId = Long.valueOf((String)bnbLocation.getKeyValue().getKey());
		else
			locationId = (Long)bnbLocation.getKeyValue().getKey();
		
		return locationSetupServiceImpl.findByIdAndDate(locationId, selectedOrg.getEffectiveOnDate());
	}
}
