package co.id.fifgroup.workstructure.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.workstructure.service.BranchSplitGeneratorService;
import co.id.fifgroup.workstructure.service.LocationSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.basicsetup.common.OtherInfoComponentValue;
import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;
import co.id.fifgroup.basicsetup.service.OtherInfoComponentService;
import co.id.fifgroup.core.domain.Kecamatan;
import co.id.fifgroup.core.domain.KecamatanExample;
import co.id.fifgroup.core.domain.Kelurahan;
import co.id.fifgroup.core.domain.KelurahanExample;
import co.id.fifgroup.core.domain.ZipCode;
import co.id.fifgroup.core.domain.KecamatanExample.Criteria;
import co.id.fifgroup.core.service.KecamatanService;
import co.id.fifgroup.core.service.KelurahanService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.CommonPopupZipCodeBandbox;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.LookupCriteria;
import co.id.fifgroup.core.ui.lookup.LookupWindow;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.lookup.ZipCodeSearchCriteria;
import co.id.fifgroup.workstructure.dto.BranchSplitMasterDTO;
import co.id.fifgroup.workstructure.dto.BranchSplitOrganizationDetailDTO;
import co.id.fifgroup.workstructure.dto.LocationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.dto.OtherInfoDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class BranchSplitGeneratorComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(BranchSplitGeneratorComposer.class);
	private static String ORGANIZATION = "ORGANIZATION";
	private static String KPP_LOOKUP_NAME = "MST_KPP";
	public static String OTHER_BRANCH_CODE = "00000";
	
	@WireVariable("arg")
	private Map<String, Object> param;
	@WireVariable(rewireOnActivate=true)
	private transient BranchSplitGeneratorService branchSplitGeneratorServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LocationSetupService locationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OtherInfoComponentService otherInfoComponentServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient KecamatanService kecamatanServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient KelurahanService kelurahanServiceImpl;
	
	
	@Wire
	private Listbox lsType;
	@Wire
	private Datebox dbEffectiveStartDate;
	@Wire
	private CommonPopupDoubleBandbox bnbLocation;
	@Wire
	private CommonPopupDoubleBandbox bnbParentOrg;
	@Wire
	private CommonPopupBandbox bnbBranchCode;
	@Wire
	private CommonPopupZipCodeBandbox bnbZipCode;
	@Wire
	private Hbox hboxOtherinfo;
	@Wire
	private LookupWindow bnbKpp;
	@Wire
	private CommonPopupBandbox bnbKecamatan;
	@Wire
	private CommonPopupBandbox bnbKelurahan;
	@Wire
	private Textbox txtOther;
	@Wire
	private Textbox txtAddress;
	@Wire
	private Textbox txtNpwp;
	@Wire
	private Textbox txtName;
	@Wire
	private Listbox lstOrganizationDetail;
	@Wire
	private Button btnProcess;
	
	private OrganizationDTO organizationHeader;
	private String cityCode;
	private String provinceCode;
	private OtherInfoComponent otherInfoComponent;
	private OrganizationDTO selectedOrg;
	List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		dbEffectiveStartDate.setFormat(GlobalVariable.getDateFormat());
		txtOther.setDisabled(true);
		
		loadType();
		loadLocation();
		loadParentOrganization();
		loadBranchCode();
		loadZipCode();
		loadOtherInfo();
		loadKpp();
		loadKecamatan();
		loadKeluarhan();
	}
	
	private void loadKeluarhan(){
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
	
	
	private void loadKecamatan(){
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
	
	private void loadKpp(){
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
				return KPP_LOOKUP_NAME;
			}
		});
	}
	
	private void loadOtherInfo(){
		otherInfoComponent = otherInfoComponentServiceImpl.getOtherInfoComponent(ORGANIZATION, securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(otherInfoComponent != null) {
			hboxOtherinfo.appendChild(otherInfoComponent.getComponent());			
		}
	}
	
	private void loadZipCode(){
		bnbZipCode.setZipCodeSearchCriteria(new ZipCodeSearchCriteria() {
			
			@Override
			public String getProvinceCode() {
				return provinceCode;
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
				return cityCode;
			}
		});
	}
	
	private void loadBranchCode(){
		bnbBranchCode.setSearchDelegate(new SerializableSearchDelegate<KeyValue>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				KeyValue otherBranchCode = new KeyValue(OTHER_BRANCH_CODE, "OTHER", "Other");
				List<KeyValue> keyValues = branchSplitGeneratorServiceImpl.getGlCodes(searchCriteria, limit, offset);
				keyValues.add(0, otherBranchCode);
				return keyValues; 
			}

			
			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return branchSplitGeneratorServiceImpl.countGlCodes(searchCriteria);
			}

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				keyValue.setKey(data.getKey());
				keyValue.setValue(data.getValue());
				keyValue.setDescription(data.getKey().toString().concat(" - ").concat(data.getDescription().toString()));
			}
			
		});
		
		bnbBranchCode.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if(bnbBranchCode.getKeyValue() != null){
					if(bnbBranchCode.getKeyValue().getKey().equals(OTHER_BRANCH_CODE))
						txtOther.setDisabled(false);
					else{
						txtOther.setDisabled(true);
						txtOther.setValue("");
					}
				}
				
			}
		});
	}
	
	private void loadParentOrganization(){
		bnbParentOrg.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<OrganizationDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				OrganizationDTO org = new OrganizationDTO();
				org.setCompanyName(searchCriteria1);
				org.setName(searchCriteria2);
				org.setGroupId(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
				return organizationSetupServiceImpl.findOrganizationByCompany(org, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				OrganizationDTO org = new OrganizationDTO();
				org.setCompanyName(searchCriteria1);
				org.setName(searchCriteria2);
				org.setGroupId(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
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
	
	private void loadLocation(){
		bnbLocation.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<LocationDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<LocationDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				LocationDTO location = new LocationDTO();
				location.setLocationCode(searchCriteria1);
				location.setLocationName(searchCriteria2);
				location.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return locationSetupServiceImpl.findActiveLocationForLov(location, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				LocationDTO location = new LocationDTO();
				location.setLocationCode(searchCriteria1);
				location.setLocationName(searchCriteria2);
				location.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
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
						/*lblCountry.setValue(getCountry(location.getCountryCode()));
						lblProvince.setValue(provinceServiceImpl.getProvinceValue(location.getProvinceCode()));
						lblCity.setValue(cityServiceImpl.getCityValue(location.getCityCode()));*/
						cityCode = location.getCityCode();
						provinceCode = location.getProvinceCode();
					}
				}
			}
			
		});
	}
	
	private void loadType(){
		List<KeyValue> types = new ArrayList<>();
		
		try {
			types = branchSplitGeneratorServiceImpl.getOrganizationGeneratorType(securityServiceImpl.getSecurityProfile().getCompanyId());	
		} catch (RuntimeException e) {
			Messagebox.show(e.getMessage());
		}
		
		types.add(0, new KeyValue(null, "-Select-","-Select-"));
		
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(types);
		lsType.setModel(model);
		lsType.renderAll();
		lsType.setSelectedIndex(0);
	}
	
	
	@Listen("onAdvanced= #winBranchSplitGenerator")
	public void onDetail(ForwardEvent event) {
		selectedOrg = (OrganizationDTO) event.getData();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(OrganizationDTO.class.getName(), selectedOrg);
		map.put(BranchSplitGeneratorComposer.class.getName(), this);
		Window win = (Window)Executions.createComponents("~./hcms/workstructure/branch_split_detail.zul", getSelf().getParent(), map);
		win.doModal();
	}
	
	public void setSelectedOrg(OrganizationDTO selectedOrg){
		this.selectedOrg = selectedOrg;
		ListModelList<OrganizationDTO> model = new ListModelList<OrganizationDTO>(organizationDTOs);
		lstOrganizationDetail.setModel(model);
		lstOrganizationDetail.renderAll();
	}
	
	@Listen("onClick=button#btnGenerate")
	public void generate(){
		
		Messagebox.show("Are sure want to generate the organization data?", "Confirmation", Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				String type = null;
				organizationHeader = setOrganizationHeader(organizationHeader);
				
				try {
					clearError();
					validate(organizationHeader);
					
					if(lsType.getSelectedItem() != null){
						KeyValue typeKv = (KeyValue)lsType.getSelectedItem().getValue();
						type = (String) typeKv.getKey();
					}
					
					BranchSplitMasterDTO template = branchSplitGeneratorServiceImpl.getTemplateByType(type, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					
					if(template != null){
						organizationDTOs = new ArrayList<OrganizationDTO>();
						
						for (BranchSplitOrganizationDetailDTO orgDetail : template.getOrganizationDetails()) {
							OrganizationDTO organizationDTO = new OrganizationDTO();
							
							organizationDTO.setCode(generateOrgCode(orgDetail.getPrefixCode(), organizationHeader.getBranchCode()));
							organizationDTO.setName(generateOrganizationName(txtName.getValue(), orgDetail.getOrgName()));
							organizationDTO.setDescription(generateOrganizationName(txtName.getValue(), orgDetail.getOrgName()));
							organizationDTO.setLevelCode(orgDetail.getLevelCode());
							organizationDTO.setLevelName(orgDetail.getLevelName());
							organizationDTO.setNpwp(txtNpwp.getValue());
							organizationDTO.setKppCode(organizationHeader.getKppCode());
							organizationDTO.setLocation(organizationHeader.getLocation());
							organizationDTO.setOrganizationHeadId(orgDetail.getOrgHeadId());
							organizationDTO.setOrganizationHead(orgDetail.getOrgHeadName());
							organizationDTO.setOrganizationHeadCode(orgDetail.getOrgHeadCode());
							organizationDTO.setBranchCode(organizationHeader.getBranchCode());
							organizationDTO.setCostCenterCode(orgDetail.getCostCenterCode());
							organizationDTO.setCostCenterName(orgDetail.getCostCenterName());
							organizationDTO.setKecamatanCode(organizationHeader.getKecamatanCode());
							organizationDTO.setKelurahanCode(organizationHeader.getKelurahanCode());
							organizationDTO.setZipCode(organizationHeader.getZipCode());
							organizationDTO.setAddress(organizationHeader.getAddress());
							organizationDTO.setEffectiveOnDate(organizationHeader.getEffectiveOnDate());
							organizationDTO.setOtherInfoComponent(organizationHeader.getOtherInfoComponent());
							organizationDTO.setOrganizationInfos(organizationHeader.getOrganizationInfos());
							organizationDTO.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
							organizationDTO.setDateFrom(dbEffectiveStartDate.getValue());
							organizationDTO.setDateTo(DateUtil.MAX_DATE);
							organizationDTO.setIsHeadOffice(false);
							organizationDTO.setParent(orgDetail.isIsParent());
							
							organizationDTOs.add(organizationDTO);
						}
						
						ListModelList<OrganizationDTO> model = new ListModelList<OrganizationDTO>(organizationDTOs);
						lstOrganizationDetail.setModel(model);
						lstOrganizationDetail.renderAll();
						Messagebox.show("Data has been finalized");
					}else{
						Messagebox.show("No template detail found");
					}
					
					
					
				} catch (ValidationException e) {
					showError(e);
					
					ListModelList<OrganizationDTO> model = new ListModelList<OrganizationDTO>(new ArrayList<OrganizationDTO>());
					lstOrganizationDetail.setModel(model);
					lstOrganizationDetail.renderAll();
				}
			}
		});
	}
	
	private void clearError(){
		ErrorMessageUtil.clearErrorMessage(lsType);
		ErrorMessageUtil.clearErrorMessage(dbEffectiveStartDate);
		ErrorMessageUtil.clearErrorMessage(txtName);
		ErrorMessageUtil.clearErrorMessage(bnbLocation);
		ErrorMessageUtil.clearErrorMessage(bnbParentOrg);
		ErrorMessageUtil.clearErrorMessage(txtOther);
		ErrorMessageUtil.clearErrorMessage(bnbKecamatan);
		ErrorMessageUtil.clearErrorMessage(bnbKelurahan);
		ErrorMessageUtil.clearErrorMessage(bnbZipCode);
		ErrorMessageUtil.clearErrorMessage(txtAddress);
		ErrorMessageUtil.clearErrorMessage(txtNpwp);
		ErrorMessageUtil.clearErrorMessage(bnbKpp);
	}
	
	private void showError(ValidationException e){
		ErrorMessageUtil.setErrorMessage(lsType, e.getMessage(lsType.getId()));
		ErrorMessageUtil.setErrorMessage(dbEffectiveStartDate, e.getMessage(dbEffectiveStartDate.getId()));
		ErrorMessageUtil.setErrorMessage(txtName, e.getMessage(txtName.getId()));
		ErrorMessageUtil.setErrorMessage(bnbLocation, e.getMessage(bnbLocation.getId()));
		ErrorMessageUtil.setErrorMessage(bnbParentOrg, e.getMessage(bnbParentOrg.getId()));
		ErrorMessageUtil.setErrorMessage(txtOther, e.getMessage(bnbBranchCode.getId()));
		ErrorMessageUtil.setErrorMessage(bnbKecamatan, e.getMessage(bnbKecamatan.getId()));
		ErrorMessageUtil.setErrorMessage(bnbKelurahan, e.getMessage(bnbKelurahan.getId()));
		ErrorMessageUtil.setErrorMessage(bnbZipCode, e.getMessage(bnbZipCode.getId()));
		ErrorMessageUtil.setErrorMessage(txtAddress, e.getMessage(txtAddress.getId()));
		ErrorMessageUtil.setErrorMessage(txtNpwp, e.getMessage(txtNpwp.getId()));
		ErrorMessageUtil.setErrorMessage(bnbKpp, e.getMessage(bnbKpp.getId()));
	}
	
	private OrganizationDTO setOrganizationHeader(OrganizationDTO subject){
		
		if(subject == null)
			subject = new OrganizationDTO();
		
		subject.setEffectiveOnDate(dbEffectiveStartDate.getValue());
		
		if(bnbLocation.getKeyValue() != null){
			LocationDTO location = locationSetupServiceImpl.findByIdAndDate((Long)bnbLocation.getKeyValue().getKey(), dbEffectiveStartDate.getValue());
			subject.setLocation(location);
		}
		
		if(bnbBranchCode.getKeyValue() != null){
			if(!bnbBranchCode.getKeyValue().getKey().equals(OTHER_BRANCH_CODE))
				subject.setBranchCode((String)bnbBranchCode.getKeyValue().getKey());
			else{
				if(!txtOther.getValue().equals(""))
					subject.setBranchCode(txtOther.getValue());
			}
				
		}
		
		if(bnbKecamatan.getKeyValue() != null)
			subject.setKecamatanCode((String)bnbKecamatan.getKeyValue().getKey());
		
		if(bnbKelurahan.getKeyValue() != null)
			subject.setKelurahanCode((String)bnbKelurahan.getKeyValue().getKey());
		
		if(bnbZipCode.getValueZipcode() != null)
			subject.setZipCode(bnbZipCode.getValueZipcode().getZipCode());
		
		if(txtAddress.getValue() != null)
			subject.setAddress(txtAddress.getValue());
		
		if(txtNpwp.getValue() != null){
			subject.setNpwp(txtNpwp.getValue());
		}
		if(bnbKpp.getKeyValue() != null)
			subject.setKppCode((String)bnbKpp.getKeyValue().getKey());
		
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
			subject.setOrganizationInfos(orgOtherInfos);
			subject.setOtherInfoComponent(otherInfoComponent);
		}
		
		
		return subject;
	}
	
	private void validate(OrganizationDTO subject) throws ValidationException{
		
		Map<String, String> error = new HashMap<String, String>();
		
		if(((KeyValue)lsType.getSelectedItem().getValue()).getKey() == null)
			error.put(lsType.getId(), "Please fill Type");
		
		if(subject.getEffectiveOnDate() == null)
			error.put(dbEffectiveStartDate.getId(), "Please fill Effective Start Date");
		
		if(txtName.getValue().equals(""))
			error.put(txtName.getId(), "Please fill Name");
		
		/*if(bnbParentOrg.getKeyValue() == null)
			error.put(bnbParentOrg.getId(), "Please fill Parent Organization");*/
		
		if(subject.getLocation() == null)
			error.put(bnbLocation.getId(), "Please fill location");
		
		if(subject.getBranchCode() == null)
			error.put(bnbBranchCode.getId(), "Pleases fill Branch Code");
		
		if(subject.getZipCode() == null)
			error.put(bnbZipCode.getId(), "Please fill Zip Code");
		
		if(subject.getAddress().equals(""))
			error.put(txtAddress.getId(), "Please fill Address");
		
		if(subject.getKppCode() == null)
			error.put(bnbKpp.getId(), "Please fill KPP");
		
		if(!subject.getNpwp().equals("")){
			if (!ValidationRule.isNPWP(subject.getNpwp())) {
				error.put(txtNpwp.getId(), Labels.getLabel("wos.npwpFormat", new Object[] { "xx.xxx.xxx.x-xxx.xxx" }));
			}
		}
		
		if(subject.getOtherInfoComponent() != null){
			if(!otherInfoComponentServiceImpl.validate(otherInfoComponent))
				error.put("", "-");
		}
		
		if(error.size() > 0) throw new ValidationException(error);
	}
	
	private String generateOrgCode(String prefix, String branchCode){
		String code = null;
		code = prefix+""+branchCode;
		return code;
	}
	
	private String generateOrganizationName(String name, String tempalteName){
		String orgName = null;
		orgName = name+" "+tempalteName;
		return orgName;
	}
	
	@Listen("onClick= button#btnDownload")
	public void download(){
		branchSplitGeneratorServiceImpl.download(organizationDTOs);
	}
	
	@Listen("onClick= button#btnProcess")
	public void process(){
		
		Messagebox.show("Are sure want to process the organization data?", "Confirmation", Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					try {
						branchSplitGeneratorServiceImpl.process(organizationDTOs, organizationHeader.getEffectiveOnDate(), bnbParentOrg.getKeyValue() == null ? null : (Long)bnbParentOrg.getKeyValue().getKey());
						Messagebox.show("Data has been finalized");
						btnProcess.setDisabled(true);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						Messagebox.show(e.getMessage());
					}
				}
			}
		});	
		
		
	}
	
	@Listen("onClick= button#btnCancel")
	public void cancel(){
		Tabbox tabbox = (Tabbox)param.get("Tabbox");
		Tabs tabs = (Tabs)tabbox.getFellow("tabs");
		Tab selectedTab = null;
		for (Component com : tabs.getChildren()) {
			Tab tab = (Tab)com;
			if(tab.isSelected())
				selectedTab = tab;
		}
		
		selectedTab.close();
	}

}
