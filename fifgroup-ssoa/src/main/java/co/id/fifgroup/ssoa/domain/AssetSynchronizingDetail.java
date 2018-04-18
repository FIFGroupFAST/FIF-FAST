package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class AssetSynchronizingDetail {
	private Long id;
	private Long assetSyncId;
	private Long processTypeId;
	private String processTypeCode;
	// private String processType;
	private Long assetId;
	private Long stockOpnameUnregAssetId;

	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;

	private String assetNumber;
	private String tagNumber;
	private String serialNumber;
	private String description;
	private Long categoryId;
	private String categoryName;
	private String itemCategory;
	private Long companyId;

	private Long branchId;
	private String branchCode;
	private String branchName;
	private Long locationId;
	private String locationCode;
	private String locationName;

	private Date datePlacedInService;

	private Long originalCost;
	private Long unrevaluedCost;
	private String bookTypeCode;

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Date getDatePlacedInService() {
		return datePlacedInService;
	}

	public void setDatePlacedInService(Date datePlacedInService) {
		this.datePlacedInService = datePlacedInService;
	}

	public Long getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(Long originalCost) {
		this.originalCost = originalCost;
	}

	public Long getUnrevaluedCost() {
		return unrevaluedCost;
	}

	public void setUnrevaluedCost(Long unrevaluedCost) {
		this.unrevaluedCost = unrevaluedCost;
	}

	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public AssetSynchronizingDetail() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProcessTypeId() {
		return processTypeId;
	}

	public void setProcessTypeId(Long processTypeId) {
		this.processTypeId = processTypeId;
	}

	public String getProcessTypeCode() {
		return processTypeCode;
	}

	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
	}

	public Long getAssetSyncId() {
		return assetSyncId;
	}

	public void setAssetSyncId(Long assetSyncId) {
		this.assetSyncId = assetSyncId;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
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

	public Long getStockOpnameUnregAssetId() {
		return stockOpnameUnregAssetId;
	}

	public void setStockOpnameUnregAssetId(Long stockOpnameUnregAssetId) {
		this.stockOpnameUnregAssetId = stockOpnameUnregAssetId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}