package co.id.fifgroup.ssoa.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.ssoa.domain.AssetRegistration;


public class AssetRegistrationDTO extends AssetRegistration implements CommonTrx {
	
	private static final long serialVersionUID = 5749051688120495054L;
	
	private List<AssetRegistrationDetailDTO> assetRegistrationDetail;
	private List<AssetRegistrationDetailDTO> assetRegistrationAdd;
	private String userName;
	private String raBranchType;
	private Long id;
	private Long stockOpnameUnregAssetId;
	private Long stockOpnameId;
	private Long assetId;
	private String tagNumber;
	private String categoryName;
	private String branchCode;
	private String branchName;
	private String locationCode;
	private String locationName;
	private String serialNumber;
	private String description;
	private Long categoryId;
	private String position;
	private Long companyId;
	private Long branchId;
	private Long locationId;

	private Date datePlacedInService;
	private Date datePlaceInService;
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
	private String opnameResult;
	private String opnameSugest;
	private String opnameHOSugest;
	private String assetType;
	private String lastUpdateByName;
	private String createdByName;
	private Long opnameSugestId;
	private String opnameSugestCode;
	private Long opnameHOSugestId;
	private String opnameHOSugestCode;
	private String image;
	private Long opnameActionStatusId;
	private String opnameActionStatusCode;
	private String opnameActionStatus;
	private Long assetTypeId;
	private Long netBookValue;
	private String assetTypeCode;
	private String retiredFlag;
	
	private String ebsFlag;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDatePlaceInService() {
		return datePlaceInService;
	}
	public void setDatePlaceInService(Date datePlaceInService) {
		this.datePlaceInService = datePlaceInService;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public List<AssetRegistrationDetailDTO> getAssetRegistrationDetail() {
		return assetRegistrationDetail;
	}
	public void setAssetRegistrationDetail(List<AssetRegistrationDetailDTO> assetRegistrationDetail) {
		this.assetRegistrationDetail = assetRegistrationDetail;
	}
	public List<AssetRegistrationDetailDTO> getAssetRegistrationAdd() {
		return assetRegistrationAdd;
	}
	public void setAssetRegistrationAdd(List<AssetRegistrationDetailDTO> assetRegistrationAdd) {
		this.assetRegistrationAdd = assetRegistrationAdd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getBranchType() {
		return branchType;
	}
	public void setBranchType(Long branchType) {
		this.branchType = branchType;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getBusinessGroupId() {
		return businessGroupId;
	}
	public void setBusinessGroupId(Long businessGroupId) {
		this.businessGroupId = businessGroupId;
	}
	public Long getKeyJob() {
		return keyJob;
	}
	public void setKeyJob(Long keyJob) {
		this.keyJob = keyJob;
	}
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	public UUID getObjectEmployeeUUID() {
		return objectEmployeeUUID;
	}
	public void setObjectEmployeeUUID(UUID objectEmployeeUUID) {
		this.objectEmployeeUUID = objectEmployeeUUID;
	}
	public boolean isBasedLineRequestor() {
		return basedLineRequestor;
	}
	public void setBasedLineRequestor(boolean basedLineRequestor) {
		this.basedLineRequestor = basedLineRequestor;
	}
	public Long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Long transactionType) {
		this.transactionType = transactionType;
	}
	public int getTransactionApprovalStatus() {
		return transactionApprovalStatus;
	}
	public void setTransactionApprovalStatus(int transactionApprovalStatus) {
		this.transactionApprovalStatus = transactionApprovalStatus;
	}
	public Long getOrganizationDestinationId() {
		return organizationDestinationId;
	}
	public void setOrganizationDestinationId(Long organizationDestinationId) {
		this.organizationDestinationId = organizationDestinationId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getEbsFlag() {
		return ebsFlag;
	}
	public void setEbsFlag(String ebsFlag) {
		this.ebsFlag = ebsFlag;
	}
	public String getRaBranchType() {
		return raBranchType;
	}
	public void setSoBranchType(String raBranchType) {
		this.raBranchType = raBranchType;
	}
	public Long getStockOpnameUnregAssetId() {
		return stockOpnameUnregAssetId;
	}
	public void setStockOpnameUnregAssetId(Long stockOpnameUnregAssetId) {
		this.stockOpnameUnregAssetId = stockOpnameUnregAssetId;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
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
	public void setRaBranchType(String raBranchType) {
		this.raBranchType = raBranchType;
	}
	public String getRetiredFlag() {
		return retiredFlag;
	}
	public void setRetiredFlag(String retiredFlag) {
		this.retiredFlag = retiredFlag;
	}
	public Long getNetBookValue() {
		return netBookValue;
	}
	public void setNetBookValue(Long netBookValue) {
		this.netBookValue = netBookValue;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	public String getOpnameActionStatus() {
		return opnameActionStatus;
	}
	public void setOpnameActionStatus(String opnameActionStatus) {
		this.opnameActionStatus = opnameActionStatus;
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
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getLastUpdateByName() {
		return lastUpdateByName;
	}
	public void setLastUpdateByName(String lastUpdateByName) {
		this.lastUpdateByName = lastUpdateByName;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	
	
}
