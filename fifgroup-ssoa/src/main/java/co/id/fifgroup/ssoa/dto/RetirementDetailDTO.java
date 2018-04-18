package co.id.fifgroup.ssoa.dto;


import java.util.Date;
import java.util.List;

import co.id.fifgroup.ssoa.domain.RetirementDetail;

public class RetirementDetailDTO extends RetirementDetail  {

	private static final long serialVersionUID = 9207558486244871296L;
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
	private String position;
	private Date datePlacedInService;
	private Long lifeInMonths;
	private Long originalCost;
	private Long unrevaluedCost;
	private String bookTypeCode;
	private String stockOpnameResult;
	private String stockOpnameSugest;
	private String stockOpnameHoSugest;
	private String assetType;
	private String accountCode;
	private Double price;
	
	
	
	private List<RetirementImgDTO> retirementImg;
	public List<RetirementImgDTO> getRetirementImg() {
		return retirementImg;
	}
	public void setRetirementImg(List<RetirementImgDTO> retirementImg) {
		this.retirementImg = retirementImg;
	}
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
	
	
	
}