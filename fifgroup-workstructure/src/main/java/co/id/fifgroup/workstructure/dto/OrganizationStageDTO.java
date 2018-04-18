package co.id.fifgroup.workstructure.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.domain.UploadStage;

public class OrganizationStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;
	
	private Date dateFrom;
	private String organizationCode;
	private String organizationName;
	private String description;
	private String levelCode;
	private String npwp;
	private String kppCode;
	private String locationCode;
	private String jobCode;
	private String branchCode;
	private String costCenterCode;
	private String workingSchedule;
	private Long locationId;
	private Long headOfOrganization;
	private Date dateTo;
	private String address;
	private String zipCode;
	private Long workingScheduleId;
	private Long companyId;
	private List<String> otherInfoStrings = new ArrayList<>();
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public List<String> getOtherInfoStrings() {
		return otherInfoStrings;
	}
	public void setOtherInfoStrings(List<String> otherInfoStrings) {
		this.otherInfoStrings = otherInfoStrings;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	public String getNpwp() {
		return npwp;
	}
	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}
	public String getKppCode() {
		return kppCode;
	}
	public void setKppCode(String kppCode) {
		this.kppCode = kppCode;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getCostCenterCode() {
		return costCenterCode;
	}
	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getHeadOfOrganization() {
		return headOfOrganization;
	}
	public void setHeadOfOrganization(Long headOfOrganization) {
		this.headOfOrganization = headOfOrganization;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public Long getWorkingScheduleId() {
		return workingScheduleId;
	}
	public void setWorkingScheduleId(Long workingScheduleId) {
		this.workingScheduleId = workingScheduleId;
	}
	public String getWorkingSchedule() {
		return workingSchedule;
	}
	public void setWorkingSchedule(String workingSchedule) {
		this.workingSchedule = workingSchedule;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}	
}
