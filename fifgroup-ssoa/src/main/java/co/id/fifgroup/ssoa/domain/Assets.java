package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class Assets {

	private Long id;
	private Long ebsAssetId;
	private String assetNumber;
	private String tagNumber;
	private String serialNumber;
	private String description;
	private Long categoryId;
	private String itemCategory;
	private Long companyId;
	private Long branchId;
	private String branchCode;
	private String branchName;
	private Long locationId;
	private String locationCode;
	private String locationName;
	private String position;
	private String assetType;
	private String categoryName;

	private Date datePlacedInService;
	private Long lifeInMonths;

	private Long originalCost;
	private Long unrevaluedCost;
	private String bookTypeCode;

	private Date lastPrintedDate;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;

	private String stockOpnameResult;
	private String stockOpnameSugest;
	private String stockOpnameHoSugest;
	private Long stockOpnameDtlId;
	private Date startDate;
	private Date endDate;
	private Long rowNumber;
	private Long printedCount;
	private Long assetTypeId;
	private String assetTypeCode;

	private String retiredFlag;
	private Long initialUnits;
	private Long netBookValue;

	public Assets() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getDatePlacedInService() {
		return datePlacedInService;
	}

	public void setDatePlacedInService(Date datePlacedInService) {
		this.datePlacedInService = datePlacedInService;
	}

	public Long getLifeInMonths() {
		return lifeInMonths;
	}

	public void setLifeInMonths(Long lifeInMonths) {
		this.lifeInMonths = lifeInMonths;
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

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getStockOpnameResult() {
		return stockOpnameResult;
	}

	public void setStockOpnameResult(String stockOpnameResult) {
		this.stockOpnameResult = stockOpnameResult;
	}

	public String getStockOpnameSugest() {
		return stockOpnameSugest;
	}

	public void setStockOpnameSugest(String stockOpnameSugest) {
		this.stockOpnameSugest = stockOpnameSugest;
	}

	public String getStockOpnameHoSugest() {
		return stockOpnameHoSugest;
	}

	public void setStockOpnameHoSugest(String stockOpnameHoSugest) {
		this.stockOpnameHoSugest = stockOpnameHoSugest;
	}

	public Long getStockOpnameDtlId() {
		return stockOpnameDtlId;
	}

	public void setStockOpnameDtlId(Long stockOpnameDtlId) {
		this.stockOpnameDtlId = stockOpnameDtlId;
	}

	public Long getEbsAssetId() {
		return ebsAssetId;
	}

	public void setEbsAssetId(Long ebsAssetId) {
		this.ebsAssetId = ebsAssetId;
	}

	public Date getLastPrintedDate() {
		return lastPrintedDate;
	}

	public void setLastPrintedDate(Date lastPrintedDate) {
		this.lastPrintedDate = lastPrintedDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Long getPrintedCount() {
		return printedCount;
	}

	public void setPrintedCount(Long printedCount) {
		this.printedCount = printedCount;
	}

	public Long getInitialUnits() {
		return initialUnits;
	}

	public void setInitialUnits(Long initialUnits) {
		this.initialUnits = initialUnits;
	}

	public String getRetiredFlag() {
		return retiredFlag;
	}

	public void setRetiredFlag(String retiredFlag) {
		this.retiredFlag = retiredFlag;
	}

	public Long getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(Long assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public String getAssetTypeCode() {
		return assetTypeCode;
	}

	public void setAssetTypeCode(String assetTypeCode) {
		this.assetTypeCode = assetTypeCode;
	}

	
	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getNetBookValue() {
		return netBookValue;
	}

	public void setNetBookValue(Long netBookValue) {
		this.netBookValue = netBookValue;
	}
	
	

}