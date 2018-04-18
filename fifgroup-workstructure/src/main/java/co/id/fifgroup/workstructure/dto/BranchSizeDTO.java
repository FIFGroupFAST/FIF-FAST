package co.id.fifgroup.workstructure.dto;

import java.util.Date;

import co.id.fifgroup.workstructure.domain.BranchSize;

import co.id.fifgroup.basicsetup.common.History;

public class BranchSizeDTO extends BranchSize implements History {

	private static final long serialVersionUID = 1L;
	
	private Long branchId;
	private String organizationCode;
	private String organizationName;
	private String organizationLevel;
	private Date effectiveOnDate;
	private String userName;
	private String size;
	private Boolean isUpload = false;
	private Long companyId;
	
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
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
	public String getOrganizationLevel() {
		return organizationLevel;
	}
	public void setOrganizationLevel(String organizationLevel) {
		this.organizationLevel = organizationLevel;
	}
	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}
	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean isUpload() {
		return isUpload;
	}
	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "BranchSizeDTO [organizationName="
				+ organizationName + ", organizationLevel=" + organizationLevel
				+ ", getId()=" + getId() + "]";
	}
	
}
