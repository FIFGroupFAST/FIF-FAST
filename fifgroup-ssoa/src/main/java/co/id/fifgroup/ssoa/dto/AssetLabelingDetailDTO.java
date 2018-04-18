package co.id.fifgroup.ssoa.dto;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import co.id.fifgroup.ssoa.domain.AssetLabeling;
//import co.id.fifgroup.ssoa.domain.LookupHeader;
import co.id.fifgroup.ssoa.domain.AssetLabelingDetail;
//import co.id.fifgroup.ssoa.domain.assetRetirementHeader;

public class AssetLabelingDetailDTO extends AssetLabelingDetail {

	private static final long serialVersionUID = 9207558486244871296L;
    private String itemName;
    
	private AssetLabeling mainTask;
	private AssetLabeling successTask;
	private AssetLabeling errorTask;
	private Date dateOfService;
	private String assetNumber;
	private String ebsAssetId;
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
	private Date datePlacedInService;
	private Long lifeInMonths;
	private Long originalCost;
	private Long unrevaluedCost;
	private String bookTypeCode;
	private Long printedCount;
	private Date lastPrintedDate;
	private String stockOpnameResult;
	private String stockOpnameSugest;
	private String stockOpnameHoSugest;
	private String lookupDesc;
	private String assetTypeName;
	private String categoryName;
  //  private List<AssetLabelingDetailDTO> assetLabelingDetailDTO;
    /*
		@Override
	    public Long getLabelingDetailId() {
	        return getLabelingDetailId();
	    }
*/
		
		public String getItemName() {
			return itemName;
		}
		public String getLookupDesc() {
			return lookupDesc;
		}
		public void setLookupDesc(String lookupDesc) {
			this.lookupDesc = lookupDesc;
		}
		public Date getLastPrintedDate() {
			return lastPrintedDate;
		}
		public void setLastPrintedDate(Date lastPrintedDate) {
			this.lastPrintedDate = lastPrintedDate;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}


		public AssetLabeling getMainTask() {
			return mainTask;
		}


		public void setMainTask(AssetLabeling mainTask) {
			this.mainTask = mainTask;
		}


		public AssetLabeling getSuccessTask() {
			return successTask;
		}


		public void setSuccessTask(AssetLabeling successTask) {
			this.successTask = successTask;
		}


		public AssetLabeling getErrorTask() {
			return errorTask;
		}


		public void setErrorTask(AssetLabeling errorTask) {
			this.errorTask = errorTask;
		}
		public String getAssetNumber() {
			return assetNumber;
		}
		public void setAssetNumber(String assetNumber) {
			this.assetNumber = assetNumber;
		}
		public String getEbsAssetId() {
			return ebsAssetId;
		}
		public void setEbsAssetId(String ebsAssetId) {
			this.ebsAssetId = ebsAssetId;
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
		public Long getPrintedCount() {
			return printedCount;
		}
		public void setPrintedCount(Long printedCount) {
			this.printedCount = printedCount;
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
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public Date getDateOfService() {
			return dateOfService;
		}
		public void setDateOfService(Date dateOfService) {
			this.dateOfService = dateOfService;
		}
		public String getAssetTypeName() {
			return assetTypeName;
		}
		public void setAssetTypeName(String assetTypeName) {
			this.assetTypeName = assetTypeName;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
		
		
	}

