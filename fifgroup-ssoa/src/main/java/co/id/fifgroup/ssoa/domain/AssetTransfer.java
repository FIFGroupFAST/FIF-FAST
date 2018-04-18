package co.id.fifgroup.ssoa.domain;

import java.util.Date;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.AbstractCommonTrx;

public class AssetTransfer extends AbstractCommonTrx {
	
	private Long transferId;
	private Long companyOwner;	
	private Long branchId;
	private Long organitationDestinationId;
	private String requestNo;
	private String ebsFlag;
	private String bastDocNo;	
	private Date requestDate;	
	private Long branchOrigin;	
	private Long branchDestination;
	private String branchNameOrigin;
	private String branchCodeOrigin;
	private String branchNameDestination;
	private String branchCodeDestination;
	private String reason;	
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
	
	public AssetTransfer() {
		
	}
	public Long getTransferId() {
		return transferId;
	}
	public void setMutationId(Long transferId) {
		this.transferId = transferId;
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
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Long getBranchOrigin() {
		return branchOrigin;
	}
	public void setBranchOrigin(Long branchOrigin) {
		this.branchOrigin = branchOrigin;
	}
	public Long getBranchDestination() {
		return branchDestination;
	}
	public void setBranchDestination(Long branchDestination) {
		this.branchDestination = branchDestination;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	
	public String getBranchNameOrigin() {
		return branchNameOrigin;
	}

	public void setBranchNameOrigin(String branchNameOrigin) {
		this.branchNameOrigin = branchNameOrigin;
	}

	public String getBranchNameDestination() {
		return branchNameDestination;
	}

	public void setBranchNameDestination(String branchNameDestination) {
		this.branchNameDestination = branchNameDestination;
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
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public String getBranchCodeOrigin() {
		return branchCodeOrigin;
	}
	public void setBranchCodeOrigin(String branchCodeOrigin) {
		this.branchCodeOrigin = branchCodeOrigin;
	}
	public String getBranchCodeDestination() {
		return branchCodeDestination;
	}
	public void setBranchCodeDestination(String branchCodeDestination) {
		this.branchCodeDestination = branchCodeDestination;
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
	public String getBastDocNo() {
		return bastDocNo;
	}
	public void setBastDocNo(String bastDocNo) {
		this.bastDocNo = bastDocNo;
	}
	public Long getOrganitationDestinationId() {
		return organitationDestinationId;
	}
	public void setOrganitationDestinationId(Long organitationDestinationId) {
		this.organitationDestinationId = organitationDestinationId;
	}
	public String getEbsFlag() {
		return ebsFlag;
	}
	public void setEbsFlag(String ebsFlag) {
		this.ebsFlag = ebsFlag;
	}
	
	
}
