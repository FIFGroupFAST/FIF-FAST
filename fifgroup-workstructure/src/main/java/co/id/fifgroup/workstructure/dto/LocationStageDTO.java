package co.id.fifgroup.workstructure.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.domain.UploadStage;

public class LocationStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;
	
	private Date dateFrom;
	private String code;
	private String name;
	private String description;
	private String countryCode;
	private String provinceCode;
	private String cityCode;
	private String zoneCode;
	private Date dateTo;
	private String workingSchedule;
	private Long workingScheduleId;
	private String branchOwnerCode;
	private Long branchOwnerId;
	private Long companyId;
	private List<String> otherInfoStrings = new ArrayList<>();
	public List<String> getOtherInfoStrings() {
		return otherInfoStrings;
	}
	public void setOtherInfoStrings(List<String> otherInfoStrings) {
		this.otherInfoStrings = otherInfoStrings;
	}
	public String getWorkingSchedule() {
		return workingSchedule;
	}
	public void setWorkingSchedule(String workingSchedule) {
		this.workingSchedule = workingSchedule;
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
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getBranchOwnerCode() {
		return branchOwnerCode;
	}
	public void setBranchOwnerCode(String branchOwnerCode) {
		this.branchOwnerCode = branchOwnerCode;
	}
	public Long getBranchOwnerId() {
		return branchOwnerId;
	}
	public void setBranchOwnerId(Long branchOwnerId) {
		this.branchOwnerId = branchOwnerId;
	}
}
