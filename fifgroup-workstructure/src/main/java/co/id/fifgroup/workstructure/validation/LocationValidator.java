package co.id.fifgroup.workstructure.validation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.domain.LocationExample;
import co.id.fifgroup.workstructure.service.LocationSetupService;

import co.id.fifgroup.basicsetup.service.OtherInfoComponentService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.upload.UploadUtil;
import co.id.fifgroup.workstructure.dao.LocationMapper;
import co.id.fifgroup.workstructure.dto.LocationDTO;
import co.id.fifgroup.workstructure.finder.LocationFinder;

@Component
public class LocationValidator implements Validator<LocationDTO>{
	
	
	@Autowired
	private LocationMapper locationMapper;
	@Autowired
	private LocationFinder locationFinder;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private LocationSetupService locationSetupServiceImpl;
	@Autowired
	private OtherInfoComponentService otherInfoComponentServiceImpl;
	
	public static final String UNIQUE_FIELD = "uniqueField";
	public static final String LOCATION_CODE = " code";
	public static final String LOCATION_NAME = " name";
	public static final String COUNTRY = " country";
	public static final String PROVINCE = " province";
	public static final String CITY = " city";
	public static final String BRANCH_OWNER = "branch owner";
	
	public void validate(LocationDTO subject) throws ValidationException {
		String locationCode = subject.getLocationCode();
		String locationName = subject.getLocationName();
		String countryCode = subject.getCountryCode();
		String provinceCode = subject.getProvinceCode();
		String cityCode = subject.getCityCode();
		int countCode = 0;
		int countName = 0;
		
		if(subject.getId() == null) {
			LocationExample locationExample = new LocationExample();
			locationExample.createCriteria().andLocationCodeLikeInsensitive(locationCode).
			andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			countCode = locationMapper.countByExample(locationExample);
			
			locationExample.clear();
			locationExample.createCriteria().andLocationNameLikeInsensitive(locationName).
			andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			countName = locationMapper.countByExample(locationExample);
		}
		
		Map<String, String> mapValidate = new HashMap<>();

		if(subject.isUpload()) {
			if(countCode > 0 && countName > 0 ) {
				LocationDTO locationDTO = locationFinder.findLastVersionByCode(subject.getLocationCode(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				if(locationDTO != null) {
					if(locationDTO.isFuture())
						mapValidate.put(UNIQUE_FIELD, Labels.getLabel("common.cannotBeUploadedThereIsFutureVersionFor", new Object[] { subject.getLocationCode()+"-"+subject.getLocationName() }));
					else if(locationDTO.isCurrent()) {
						Calendar dateFromFuture = Calendar.getInstance();
						dateFromFuture.setTime(subject.getDateFrom());
						dateFromFuture.add(Calendar.DAY_OF_MONTH, -1);
						mapValidate.put(UploadUtil.HEADER_ID, locationDTO.getId().toString());
						mapValidate.put(UploadUtil.VERSION_NUMBER, locationDTO.getVersionNumber().toString());
						locationDTO.setDateTo(dateFromFuture.getTime());
						locationSetupServiceImpl.updateDetail(locationDTO);
					}				
				}
			}
		} else {
			if (subject.getBranchOwnerId() == null) {
				mapValidate.put(BRANCH_OWNER, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.branchOwner") }));
			}
		}
		if(locationName == null || locationName.equals("")) {
			mapValidate.put(LOCATION_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.locationName") }));
		}
		else if(subject.getId() == null) {
			if(countName > 0) {
				mapValidate.put(LOCATION_NAME, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.locationName") }));
			}
		}
		if(locationCode == null || locationCode.equals("")) {
			mapValidate.put(LOCATION_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.locationCode") }));
		}
		else if(!ValidationRule.isAlphaNumeric(locationCode)) {
			mapValidate.put(LOCATION_CODE, Labels.getLabel("wos.cannotContainSpaceOrCharacter", new Object[] { Labels.getLabel("wos.locationCode") }));
		}
		else if(subject.getId() == null) {
			if(countCode > 0) {
				mapValidate.put(LOCATION_CODE, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.locationCode") }));
			}
		}			
		if(countryCode == null || countryCode.equals("")) {
			mapValidate.put(COUNTRY, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.country") }));
		}
		if(provinceCode == null || provinceCode.equals("")) {
			mapValidate.put(PROVINCE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.province") }));
		}
		if(cityCode == null || cityCode.equals("")) {
			mapValidate.put(CITY, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("address.city") }));
		}
		if(subject.getOtherInfoComponent()!=null){
			if(!otherInfoComponentServiceImpl.validate(subject.getOtherInfoComponent()))
				mapValidate.put("", "");
		}
		
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}		
	}

}
