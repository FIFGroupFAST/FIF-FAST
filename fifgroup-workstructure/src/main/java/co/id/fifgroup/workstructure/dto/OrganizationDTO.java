
package co.id.fifgroup.workstructure.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.version.Version;

import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;

public class OrganizationDTO extends Version {

	private static final long serialVersionUID = 1L;

	private Long companyId;
	private String companyName;
	private Long groupId;
	private UUID organizationUuid;
	private String code;
	private String name;
	private String description;
	private String levelCode;
	private String levelName;
	private String npwp;
	private String kppCode;
	private String kppName;
	private LocationDTO location;
	private Long organizationHeadId;
	private String organizationHead;
	private String organizationHeadCode;
	private Long picId;
	private Boolean isHeadOffice;
	private Long organizationSpvId;
	private String organizationSpv;
	private String branchCode;
	private String costCenterCode;
	private String costCenterName;
	private String address;
	private String kelurahanCode;
	private String kecamatanCode;
	private String zipCode;
	private String subZipCode;
	private Long workingScheduleId;
	private Date effectiveOnDate;
	private Long parentId;
	private String userName;
	private String color;
	private boolean isUpload = true;
	private boolean isParent;
	private Long versionId;
	private Long originLocationId;
	
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

    private Long createdBy;
    private Date creationDate;
    private Long lastUpdatedBy;
    private Date lastUpdateDate;
	
	/*add by RIM fix by RAH Long to String*/
	private String branchOwner;
	
	public String getBranchOwner() {
		return branchOwner;
	}
	public void setBranchOwner(String branchOwner) {
		this.branchOwner = branchOwner;
	}
	/*end add by RIM fix by RAH Long to String*/

	private List<OrganizationContactDTO> contacts = new ArrayList<>();
	private List<OtherInfoDTO> organizationInfos = new ArrayList<>();
	private OtherInfoComponent otherInfoComponent;
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}
	public String getOrganizationSpv() {
		return organizationSpv;
	}
	
	public UUID getOrganizationUuid() {
		return organizationUuid;
	}
	public void setOrganizationUuid(UUID organizationUuid) {
		this.organizationUuid = organizationUuid;
	}
	public void setOrganizationSpv(String organizationSpv) {
		this.organizationSpv = organizationSpv;
	}
	public LocationDTO getLocation() {
		return location;
	}
	public void setLocation(LocationDTO location) {
		this.location = location;
	}
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
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
	public String getKppName() {
		return kppName;
	}
	public void setKppName(String kppName) {
		this.kppName = kppName;
	}
	
	public Long getOrganizationHeadId() {
		return organizationHeadId;
	}

	public void setOrganizationHeadId(Long organizationHeadId) {
		this.organizationHeadId = organizationHeadId;
	}

	public String getOrganizationHeadCode() {
		return organizationHeadCode;
	}
	public void setOrganizationHeadCode(String organizationHeadCode) {
		this.organizationHeadCode = organizationHeadCode;
	}
	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public Boolean getIsHeadOffice() {
		return isHeadOffice;
	}

	public void setIsHeadOffice(Boolean isHeadOffice) {
		this.isHeadOffice = isHeadOffice;
	}

	public Long getOrganizationSpvId() {
		return organizationSpvId;
	}

	public void setOrganizationSpvId(Long organizationSpvId) {
		this.organizationSpvId = organizationSpvId;
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
	
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getKelurahanCode() {
		return kelurahanCode;
	}

	public void setKelurahanCode(String kelurahanCode) {
		this.kelurahanCode = kelurahanCode;
	}

	public String getKecamatanCode() {
		return kecamatanCode;
	}

	public void setKecamatanCode(String kecamatanCode) {
		this.kecamatanCode = kecamatanCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getWorkingScheduleId() {
		return workingScheduleId;
	}

	public void setWorkingScheduleId(Long workingScheduleId) {
		this.workingScheduleId = workingScheduleId;
	}

	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}
	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}
	public String getOrganizationHead() {
		return organizationHead;
	}
	public void setOrganizationHead(String organizationHead) {
		this.organizationHead = organizationHead;
	}
	public String getSubZipCode() {
		return subZipCode;
	}
	public void setSubZipCode(String subZipCode) {
		this.subZipCode = subZipCode;
	}
	public List<OrganizationContactDTO> getContacts() {
		return contacts;
	}

	public void setContacts(List<OrganizationContactDTO> contacts) {
		this.contacts = contacts;
	}

	public List<OtherInfoDTO> getOrganizationInfos() {
		return organizationInfos;
	}

	public void setOrganizationInfos(List<OtherInfoDTO> organizationInfos) {
		this.organizationInfos = organizationInfos;
	}

	public OtherInfoComponent getOtherInfoComponent() {
		return otherInfoComponent;
	}
	public void setOtherInfoComponent(OtherInfoComponent otherInfoComponent) {
		this.otherInfoComponent = otherInfoComponent;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public boolean isUpload() {
		return isUpload;
	}
	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "OrganizationDto [companyId=" + companyId + ", id=" + getId() + ", code=" + code
				+ ", name=" + name + ", description=" + description
				+ ", levelCode=" + levelCode + ", npwp=" + npwp + ", kppCode="
				+ kppCode + ", "
				+ ", organizationHeadId=" + organizationHeadId
				+ ", picId=" + picId
				+ ", isHeadOffice=" + isHeadOffice + ", organizationSpvId="
				+ organizationSpvId + ", branchCode=" + branchCode
				+ ", costCenterCode=" + costCenterCode + ", address=" + address
				+ ", kelurahanCode=" + kelurahanCode + ", kecamatanCode="
				+ kecamatanCode + ", zipCode=" + zipCode
				+ ", workingScheduleId=" + workingScheduleId + ", contacts="
				+ contacts + ", organizationInfos=" + organizationInfos+ "]";
	}
	public boolean isParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public Long getVersionId() {
		return versionId;
	}
	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}
	public Long getOriginLocationId() {
		return originLocationId;
	}
	public void setOriginLocationId(Long originLocationId) {
		this.originLocationId = originLocationId;
	}
}
