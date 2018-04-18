package co.id.fifgroup.workstructure.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.workstructure.domain.Location;
import co.id.fifgroup.workstructure.domain.LocationExample;
import co.id.fifgroup.workstructure.service.LocationSetupService;

import co.id.fifgroup.core.domain.City;
import co.id.fifgroup.core.domain.CityExample;
import co.id.fifgroup.core.domain.Province;
import co.id.fifgroup.core.domain.ProvinceExample;
import co.id.fifgroup.core.domain.CityExample.Criteria;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.CityService;
import co.id.fifgroup.core.service.ProvinceService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.LookupCriteria;
import co.id.fifgroup.core.ui.lookup.LookupWindow;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.workstructure.dto.LocationDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class LocationSetupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(LocationSetupInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;
	private final String countryLookup = "MST_COUNTRY";
	private final String zoneLookup = "MST_ZONE";
	private LocationDTO locationDTO;
	private final String location = "location";
	private final String bandboxWidth = "500px";
	private FunctionPermission functionPermission;
	
	@WireVariable(rewireOnActivate=true)
	private transient LocationSetupService locationSetupServiceImpl;	
	@WireVariable(rewireOnActivate=true)
	private transient ProvinceService provinceServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CityService cityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	@Wire
	private Listbox lstLocation;
	@Wire
	private CommonPopupDoubleBandbox bnbLocation;
	@Wire
	private LookupWindow bnbCountry;
	@Wire
	private CommonPopupBandbox bnbProvince;
	@Wire
	private CommonPopupBandbox bnbCity;
	@Wire
	private LookupWindow bnbZone;
	@Wire
	private Datebox dtbEffectiveOnDate;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		bnbLocation.setWidth(bandboxWidth);
		bnbCountry.setWidth(bandboxWidth);
		bnbProvince.setWidth(bandboxWidth);
		bnbCity.setWidth(bandboxWidth);
		bnbZone.setWidth(bandboxWidth);
		lstLocation.setMold("paging");
		lstLocation.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbEffectiveOnDate.setValue(new Date());
		populateLocation();
		populateCountry();
		populateProvince();
		populateCity();
		populateZone();
	}
	
	@Listen("onDownloadUserManual = #winLocationSetupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void populateCity() {
		bnbCity.setSearchDelegate(new SerializableSearchDelegate<City>() {

			private static final long serialVersionUID = 1701578432617248999L;

			@Override
			public List<City> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				KeyValue keyValueProvince = bnbProvince.getKeyValue();
				CityExample cityExample = new CityExample();
				Criteria criteriaCity = cityExample.createCriteria();
				criteriaCity.andCityLikeInsensitive(searchCriteria);
				if(keyValueProvince != null && keyValueProvince.getKey() != null) {
					criteriaCity.andProvCodeEqualTo(keyValueProvince.getKey().toString());
				}
				return cityServiceImpl.getCitiesByExample(cityExample, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				KeyValue keyValueProvince = bnbProvince.getKeyValue();
				CityExample cityExample = new CityExample();
				Criteria criteriaCity = cityExample.createCriteria();
				criteriaCity.andCityLikeInsensitive(searchCriteria);
				if(keyValueProvince != null && keyValueProvince.getKey() != null) {
					criteriaCity.andProvCodeEqualTo(keyValueProvince.getKey().toString());
				}
				return cityServiceImpl.getCountCityByExample(cityExample);
			}

			@Override
			public void mapper(KeyValue keyValue, City data) {
				keyValue.setKey(data.getCityCode());
				keyValue.setValue(data.getCity());
				keyValue.setDescription(data.getCity());
			}
		});
	}
	
	public void populateProvince() {
		bnbProvince.setSearchDelegate(new SerializableSearchDelegate<Province>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public List<Province> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				ProvinceExample provinceExample = new ProvinceExample();
				provinceExample.createCriteria().andProvinsiLikeInsensitive(searchCriteria);
				return provinceServiceImpl.getProvincesByExample(provinceExample, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				ProvinceExample provinceExample = new ProvinceExample();
				provinceExample.createCriteria().andProvinsiLikeInsensitive(searchCriteria);
				return provinceServiceImpl.getCountProvinceByExample(provinceExample);
			}

			@Override
			public void mapper(KeyValue keyValue, Province data) {
				keyValue.setKey(data.getProvinsiCode());
				keyValue.setValue(data.getProvinsi());
				keyValue.setDescription(data.getProvinsi());
			}
		});
	}
	
	private void populateLocation() {
		bnbLocation.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Location>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Location> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				LocationExample example = new LocationExample();
				example.createCriteria().andLocationCodeLikeInsensitive(searchCriteria1)
					.andLocationNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return locationSetupServiceImpl.findByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				LocationExample example = new LocationExample();
				example.createCriteria().andLocationCodeLikeInsensitive(searchCriteria1)
					.andLocationNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return locationSetupServiceImpl.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, Location data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getLocationCode());
				keyValue.setDescription(data.getLocationName());
			}		
		});
	}
	
	private void populateCountry() {
		bnbCountry.setLookupCriteria(new LookupCriteria() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getParentDetailCode() {
				return null;
			}
			
			@Override
			public String getLookupName() {
				return countryLookup;
			}
		});
	}
	
	private void populateZone() {
		bnbZone.setLookupCriteria(new LookupCriteria() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getParentDetailCode() {
				return null;
			}
			
			@Override
			public String getLookupName() {
				return zoneLookup;
			}
		});
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		populate();
		if(locationDTO.getLocationName() == null 
				&& locationDTO.getCountryCode() == null
				&& locationDTO.getProvinceCode() == null
				&& locationDTO.getCityCode() == null
				&& locationDTO.getZoneCode() == null
				&& locationDTO.getEffectiveDate() == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
					}
				}				
			});
		} else {
			search();
		}		
	}
	
	private void search() {
		List<LocationDTO> currentLocations = locationSetupServiceImpl.findByInquiry(locationDTO);
		ListModelList<LocationDTO> model = new ListModelList<>(currentLocations);
		lstLocation.setModel(model);
	}
	
	private void populate() {
		locationDTO = new LocationDTO();
		
		locationDTO.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		locationDTO.setLocationName(bnbLocation.getKeyValue() != null ? (String)bnbLocation.getKeyValue().getDescription() : null);
		locationDTO.setCountryCode(bnbCountry.getKeyValue() != null ? (String)bnbCountry.getKeyValue().getKey() : null);
		locationDTO.setCountry(bnbCountry.getKeyValue() != null ? (String)bnbCountry.getKeyValue().getDescription() : null);
		locationDTO.setProvinceCode(bnbProvince.getKeyValue() != null ? (String)bnbProvince.getKeyValue().getKey() : null);
		locationDTO.setProvince(bnbProvince.getKeyValue() != null ? (String)bnbProvince.getKeyValue().getDescription() : null);
		locationDTO.setCityCode(bnbCity.getKeyValue() != null ? (String)bnbCity.getKeyValue().getKey() : null);
		locationDTO.setCity(bnbCity.getKeyValue() != null ? (String)bnbCity.getKeyValue().getDescription() : null);
		locationDTO.setZoneCode(bnbZone.getKeyValue() != null ? (String)bnbZone.getKeyValue().getKey() : null);
		locationDTO.setZoneCode(bnbZone.getKeyValue() != null ? (String)bnbZone.getKeyValue().getDescription() : null);
		locationDTO.setEffectiveDate(dtbEffectiveOnDate.getValue() != null ? dtbEffectiveOnDate.getValue() : DateUtil.truncate(new Date()));
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Executions.createComponents("~./hcms/workstructure/location_setup.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnUpload")
	public void onUpload() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(location, true);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/upload_location.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDetail= #winLocationSetupInquiry")
	public void onDetail(ForwardEvent event){
		locationDTO = (LocationDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(LocationDTO.class.getName(), locationDTO);
		Executions.createComponents("~./hcms/workstructure/location_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
}
