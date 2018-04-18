package co.id.fifgroup.ssoa.dto;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.ssoa.domain.StockOpnameUnregAssets;


public class StockOpnameUnregAssetsDTO extends StockOpnameUnregAssets{
	
	private static final long serialVersionUID = 8153696528893553702L;
	
	private List<StockOpnameUnregAssetImgDTO> stockOpnameUnregAssetImg;
	private String userName;
	private String action;
	private String locationCode;
	private String branchCode;
	private String branchName;
	private String locationName;
	private String assetNo;
	private String itemCategory;
	private Date dateOfService;
	private String opnameResult;
	private String opnameSugest;
	private String opnameHOSugest;
	private String assetType;
	private String opnameActionStatus;
	private String categoryName;
	private String createdByName;
	private String lastUpdateByName;
	private Long netBookValue;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<StockOpnameUnregAssetImgDTO> getStockOpnameUnregAssetImg() {
		return stockOpnameUnregAssetImg;
	}
	public void setStockOpnameUnregAssetImg(List<StockOpnameUnregAssetImgDTO> stockOpnameUnregAssetImg) {
		this.stockOpnameUnregAssetImg = stockOpnameUnregAssetImg;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	public Date getDateOfService() {
		return dateOfService;
	}
	public void setDateOfService(Date dateOfService) {
		this.dateOfService = dateOfService;
	}
	public String getOpnameResult() {
		return opnameResult;
	}
	public void setOpnameResult(String opnameResult) {
		this.opnameResult = opnameResult;
	}
	public String getOpnameSugest() {
		return opnameSugest;
	}
	public void setOpnameSugest(String opnameSugest) {
		this.opnameSugest = opnameSugest;
	}
	public String getOpnameHOSugest() {
		return opnameHOSugest;
	}
	public void setOpnameHOSugest(String opnameHOSugest) {
		this.opnameHOSugest = opnameHOSugest;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getOpnameActionStatus() {
		return opnameActionStatus;
	}
	public void setOpnameActionStatus(String opnameActionStatus) {
		this.opnameActionStatus = opnameActionStatus;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getLastUpdateByName() {
		return lastUpdateByName;
	}
	public void setLastUpdateByName(String lastUpdateByName) {
		this.lastUpdateByName = lastUpdateByName;
	}
	public Long getNetBookValue() {
		return netBookValue;
	}
	public void setNetBookValue(Long netBookValue) {
		this.netBookValue = netBookValue;
	}
	
	
	
	
	
	
	
}
