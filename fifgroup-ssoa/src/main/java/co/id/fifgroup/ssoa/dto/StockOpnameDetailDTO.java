package co.id.fifgroup.ssoa.dto;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.StockOpnameImg;

public class StockOpnameDetailDTO extends StockOpnameDetail {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3283726987209046312L;
	
	
	private String userName;
	
	private List<StockOpnameImgDTO> stockOpnameImg;
	
	private String opnameResult;
	private String opnameSugest;
	private String opnameHOSugest;
	private String description;
	private String ownType;
	private Long branchId;
	private String branchCode;
	private String branchName;
	private Long locationId;
	private String locationCode;
	private String locationName;
	private String itemCategory;
	private Date dateOfService;
	private String lifeOfService;
	private String serialNumber;
	private String stockOpnameBy;
	private Date stockOpnameDate;
	private String image;
	private String bookTypeCode;
	private Integer no;
	private String assetType;
	private String categoryName;
	private String createdByName;
	private String lastUpdateByName;



	public String getAssetType() {
		return assetType;
	}



	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}



	public String getLocationCode() {
		return locationCode;
	}



	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public List<StockOpnameImgDTO> getStockOpnameImg() {
		return stockOpnameImg;
	}



	public void setStockOpnameImg(List<StockOpnameImgDTO> stockOpnameImg) {
		this.stockOpnameImg = stockOpnameImg;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getOwnType() {
		return ownType;
	}



	public void setOwnType(String ownType) {
		this.ownType = ownType;
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



	public String getLifeOfService() {
		return lifeOfService;
	}



	public void setLifeOfService(String lifeOfService) {
		this.lifeOfService = lifeOfService;
	}



	public String getSerialNumber() {
		return serialNumber;
	}



	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}



	public String getStockOpnameBy() {
		return stockOpnameBy;
	}



	public void setStockOpnameBy(String stockOpnameBy) {
		this.stockOpnameBy = stockOpnameBy;
	}



	public Date getStockOpnameDate() {
		return stockOpnameDate;
	}



	public void setStockOpnameDate(Date stockOpnameDate) {
		this.stockOpnameDate = stockOpnameDate;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public String getBookTypeCode() {
		return bookTypeCode;
	}



	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}



	public Integer getNo() {
		return no;
	}



	public void setNo(Integer no) {
		this.no = no;
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

	
	
	
	
	
}
