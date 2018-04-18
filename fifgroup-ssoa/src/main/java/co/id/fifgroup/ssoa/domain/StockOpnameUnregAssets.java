package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class StockOpnameUnregAssets {
	private Long id;
	private Long stockOpnameId;
	private Long assetId;
	private String tagNumber;
	private String serialNumber;
	private String description;
	private Long categoryId;
	private Long companyId;
	private Long branchId;
	private Long locationId;

	private Date datePlacedInService;
	private Long lifeInMonths;
	
	private Long originalCost;
	private Long unrevaluedCost;
	private String bookTypeCode;
	
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	
	private Long opnameResultId;
	private String opnameResultCode;
	private Long opnameSugestId;
	private String opnameSugestCode;
	private Long opnameHOSugestId;
	private String opnameHOSugestCode;
	private String image;
	private Long opnameActionStatusId;
	private String opnameActionStatusCode;
	private Long assetTypeId;
	private String assetTypeCode;
	private String position;
	
	

	public StockOpnameUnregAssets() {
		
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getStockOpnameId() {
		return stockOpnameId;
	}



	public void setStockOpnameId(Long stockOpnameId) {
		this.stockOpnameId = stockOpnameId;
	}



	public Long getAssetId() {
		return assetId;
	}



	public void setAssetId(Long assetId) {
		this.assetId = assetId;
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



	public Long getLocationId() {
		return locationId;
	}



	public void setLocationId(Long locationId) {
		this.locationId = locationId;
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



	public Long getOpnameResultId() {
		return opnameResultId;
	}



	public void setOpnameResultId(Long opnameResultId) {
		this.opnameResultId = opnameResultId;
	}



	public String getOpnameResultCode() {
		return opnameResultCode;
	}



	public void setOpnameResultCode(String opnameResultCode) {
		this.opnameResultCode = opnameResultCode;
	}



	public Long getOpnameSugestId() {
		return opnameSugestId;
	}



	public void setOpnameSugestId(Long opnameSugestId) {
		this.opnameSugestId = opnameSugestId;
	}



	public String getOpnameSugestCode() {
		return opnameSugestCode;
	}



	public void setOpnameSugestCode(String opnameSugestCode) {
		this.opnameSugestCode = opnameSugestCode;
	}



	public Long getOpnameHOSugestId() {
		return opnameHOSugestId;
	}



	public void setOpnameHOSugestId(Long opnameHOSugestId) {
		this.opnameHOSugestId = opnameHOSugestId;
	}



	public String getOpnameHOSugestCode() {
		return opnameHOSugestCode;
	}



	public void setOpnameHOSugestCode(String opnameHOSugestCode) {
		this.opnameHOSugestCode = opnameHOSugestCode;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}


	



	public Long getOpnameActionStatusId() {
		return opnameActionStatusId;
	}



	public void setOpnameActionStatusId(Long opnameActionStatusId) {
		this.opnameActionStatusId = opnameActionStatusId;
	}



	public String getOpnameActionStatusCode() {
		return opnameActionStatusCode;
	}



	public void setOpnameActionStatusCode(String opnameActionStatusCode) {
		this.opnameActionStatusCode = opnameActionStatusCode;
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



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
	}


	
	


	
	


	

	
}