package co.id.fifgroup.ssoa.domain;

import java.util.Date;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.AbstractCommonTrx;

public class AssetRegistration extends AbstractCommonTrx {
	
	private Long registrationId;
	private Long companyOwner;	
	private Long branchId;
	private String branchName;
	private String branchCode;
	private Long organitationDestinationId;
	private String registrationNo;
	private Date registrationDate;
	private String remarks;		
	private Long statusId;
	private String statusCode;
	private String statusName;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	private String fullName;
	
	private Long assetId;
	private String avmTrxIdString;
	private String avmTrxIdBastString;
	
	private UUID avmTrxId;
	
	public AssetRegistration() {
		
	}
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getCompanyOwner() {
		return companyOwner;
	}
	public void setCompanyOwner(Long companyOwner) {
		this.companyOwner = companyOwner;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	
	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

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
	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public UUID getAvmTrxId() {
		return avmTrxId;
	}

	public void setAvmTrxId(UUID avmTrxId) {
		this.avmTrxId = avmTrxId;
	}
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	
	public String getAvmTrxIdString() {
		return avmTrxIdString;
	}
	public void setAvmTrxIdString(String avmTrxIdString) {
		this.avmTrxIdString = avmTrxIdString;
	}
	public String getAvmTrxIdBastString() {
		return avmTrxIdBastString;
	}
	public void setAvmTrxIdBastString(String avmTrxIdBastString) {
		this.avmTrxIdBastString = avmTrxIdBastString;
	}
	
	public Long getOrganitationDestinationId() {
		return organitationDestinationId;
	}
	public void setOrganitationDestinationId(Long organitationDestinationId) {
		this.organitationDestinationId = organitationDestinationId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	
}
