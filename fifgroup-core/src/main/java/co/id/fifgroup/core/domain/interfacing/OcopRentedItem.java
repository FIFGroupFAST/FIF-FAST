package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.util.Date;

public class OcopRentedItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long transactionId;
	private Long companyId;
	private String documentNumber;
	private String authorizationStatus;
	private String itemStatus;
	private String itemTypeCode;
	private Long rentalTypeId;
	private Long inventoryItemId;
	private Long picId;
	private Double unitPrice;
	private Date rentalStartDate;
	private Date rentalEndDate;
	private Integer totalRentPeriods;
	private String vehicleBrand;
	private String vehicleType;
	private Date receivedDate;
	private String licensePlatNo;
	private String transactionStatus;
	private String rentsysStatus;
	
	private Long locationId;
	private Long prevLocationId;
	private String glLocationId;
	private String prevGlLocationId;
	private Long prevPicId;
	private Long codeCombinationId;
	private Long prevCodeCombinationId;
	private String exportedToCsvFlag;
	private Date lastUpdateDate;
	private Long lastUpdatedBy;
	private Long organizationId;
	private Long rentedItemId;
	
	private String interfaceStatus;
	
	private Long personId;
	
	private Boolean rentsysStatusIsNull;
	private Boolean receivedDateIsNotNull;
	
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getAuthorizationStatus() {
		return authorizationStatus;
	}
	public void setAuthorizationStatus(String authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	public Long getRentalTypeId() {
		return rentalTypeId;
	}
	public void setRentalTypeId(Long rentalTypeId) {
		this.rentalTypeId = rentalTypeId;
	}
	public Long getInventoryItemId() {
		return inventoryItemId;
	}
	public void setInventoryItemId(Long inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}
	public Long getPicId() {
		return picId;
	}
	public void setPicId(Long picId) {
		this.picId = picId;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Date getRentalStartDate() {
		return rentalStartDate;
	}
	public void setRentalStartDate(Date rentalStartDate) {
		this.rentalStartDate = rentalStartDate;
	}
	public Date getRentalEndDate() {
		return rentalEndDate;
	}
	public void setRentalEndDate(Date rentalEndDate) {
		this.rentalEndDate = rentalEndDate;
	}
	public Integer getTotalRentPeriods() {
		return totalRentPeriods;
	}
	public void setTotalRentPeriods(Integer totalRentPeriods) {
		this.totalRentPeriods = totalRentPeriods;
	}
	public String getVehicleBrand() {
		return vehicleBrand;
	}
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getLicensePlatNo() {
		return licensePlatNo;
	}
	public void setLicensePlatNo(String licensePlatNo) {
		this.licensePlatNo = licensePlatNo;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getRentsysStatus() {
		return rentsysStatus;
	}
	public void setRentsysStatus(String rentsysStatus) {
		this.rentsysStatus = rentsysStatus;
	}
	public Boolean getRentsysStatusIsNull() {
		return rentsysStatusIsNull;
	}
	public void setRentsysStatusIsNull(Boolean rentsysStatusIsNull) {
		this.rentsysStatusIsNull = rentsysStatusIsNull;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getPrevLocationId() {
		return prevLocationId;
	}
	public void setPrevLocationId(Long prevLocationId) {
		this.prevLocationId = prevLocationId;
	}
	public String getGlLocationId() {
		return glLocationId;
	}
	public void setGlLocationId(String glLocationId) {
		this.glLocationId = glLocationId;
	}
	public String getPrevGlLocationId() {
		return prevGlLocationId;
	}
	public void setPrevGlLocationId(String prevGlLocationId) {
		this.prevGlLocationId = prevGlLocationId;
	}
	public Long getPrevPicId() {
		return prevPicId;
	}
	public void setPrevPicId(Long prevPicId) {
		this.prevPicId = prevPicId;
	}
	public Long getCodeCombinationId() {
		return codeCombinationId;
	}
	public void setCodeCombinationId(Long codeCombinationId) {
		this.codeCombinationId = codeCombinationId;
	}
	public Long getPrevCodeCombinationId() {
		return prevCodeCombinationId;
	}
	public void setPrevCodeCombinationId(Long prevCodeCombinationId) {
		this.prevCodeCombinationId = prevCodeCombinationId;
	}
	public String getExportedToCsvFlag() {
		return exportedToCsvFlag;
	}
	public void setExportedToCsvFlag(String exportedToCsvFlag) {
		this.exportedToCsvFlag = exportedToCsvFlag;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getRentedItemId() {
		return rentedItemId;
	}
	public void setRentedItemId(Long rentedItemId) {
		this.rentedItemId = rentedItemId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getInterfaceStatus() {
		return interfaceStatus;
	}
	public void setInterfaceStatus(String interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}
	public Boolean getReceivedDateIsNotNull() {
		return receivedDateIsNotNull;
	}
	public void setReceivedDateIsNotNull(Boolean receivedDateIsNotNull) {
		this.receivedDateIsNotNull = receivedDateIsNotNull;
	}
	
	

}
