package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.version.Version;

import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;

public class LocationDTO extends Version implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long locationId;
	private String locationName;
	private String locationCode;
	private String description;
	private String countryCode;
	private String country;
	private String provinceCode;
	private String province;
	private String cityCode;
	private String city;
	private String zoneCode;
	private String zone;
	private Long workingScheduleId;
	private Long companyId;
	private Date effectiveDate;
	private String userName;
	private List<OtherInfoDTO> otherInfos;
	private OtherInfoComponent otherInfoComponent;
	private boolean isUpload = true;
	private boolean isValidOtherInfo = true;
	private Long branchOwnerId;
	
	public LocationDTO() { }
	
	public LocationDTO(Long locationId,String locationName, String locationCode, String description,
			String countryCode, String provinceCode, String cityCode,
			String zoneCode, String zone, Long workingScheduleId, Long companyId,
			Date effectiveDate) {
		super();
		this.locationId=locationId;
		this.locationName = locationName;
		this.locationCode = locationCode;
		this.description = description;
		this.countryCode = countryCode;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.zoneCode = zoneCode;
		this.zone = zone;
		this.workingScheduleId = workingScheduleId;
		this.companyId = companyId;
		this.effectiveDate = effectiveDate;
	}
	
	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getWorkingScheduleId() {
		return workingScheduleId;
	}

	public void setWorkingScheduleId(Long workingScheduleId) {
		this.workingScheduleId = workingScheduleId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<OtherInfoDTO> getOtherInfos() {
		return otherInfos;
	}

	public void setOtherInfos(List<OtherInfoDTO> otherInfos) {
		this.otherInfos = otherInfos;
	}

	public OtherInfoComponent getOtherInfoComponent() {
		return otherInfoComponent;
	}

	public void setOtherInfoComponent(OtherInfoComponent oInfoComponent) {
		this.otherInfoComponent = oInfoComponent;
	}

	public boolean isUpload() {
		return isUpload;
	}

	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}

	@Override
	public String toString() {
		return "LocationDto [locationId=" + locationId +"locationName=" + locationName + ", locationCode="
				+ locationCode + ", description=" + description
				+ ", countryCode=" + countryCode + ", provinceCode="
				+ provinceCode + ", cityCode=" + cityCode + ", zipCode="
				+ zoneCode + ", workingScheduleId=" + workingScheduleId
				+ ", companyId=" + companyId + ", effectiveDate="
				+ effectiveDate + "]";
	}

	public boolean isValidOtherInfo() {
		return isValidOtherInfo;
	}

	public void setValidOtherInfo(boolean isValidOtherInfo) {
		this.isValidOtherInfo = isValidOtherInfo;
	}

	public Long getBranchOwnerId() {
		return branchOwnerId;
	}

	public void setBranchOwnerId(Long branchOwnerId) {
		this.branchOwnerId = branchOwnerId;
	}
	
}
