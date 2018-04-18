package co.id.fifgroup.core.dto;

import co.id.fifgroup.core.domain.interfacing.OcopInterface;

public class OcopInterfaceDTO extends OcopInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String companyCodeGl;
	private String locationName;
	private String locationCode;
	private Long companyId;
	private Long personId;
	private String employeeNumber;
	private String requestorNpk;
	
	public String getCompanyCodeGl() {
		return companyCodeGl;
	}
	public void setCompanyCodeGl(String companyCodeGl) {
		this.companyCodeGl = companyCodeGl;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getRequestorNpk() {
		return requestorNpk;
	}
	public void setRequestorNpk(String requestorNpk) {
		this.requestorNpk = requestorNpk;
	}
	@Override
	public String toString() {
		return "OcopInterfaceDTO [companyCodeGl=" + companyCodeGl
				+ ", locationName=" + locationName + ", locationCode="
				+ locationCode + ", companyId=" + companyId + ", personId="
				+ personId + ", employeeNumber=" + employeeNumber
				+ ", requestorNpk=" + requestorNpk
				+ ", getRsOcopInterfaceId()=" + getRsOcopInterfaceId()
				+ ", getOrganizationId()=" + getOrganizationId()
				+ ", getRentalTypeId()=" + getRentalTypeId()
				+ ", getAgentId()=" + getAgentId() + ", getAgentLocationId()="
				+ getAgentLocationId() + ", getInventoryItemId()="
				+ getInventoryItemId() + ", getItemTypeId()=" + getItemTypeId()
				+ ", getShipToLocationId()=" + getShipToLocationId()
				+ ", getPicId()=" + getPicId() + ", getGlLocationId()="
				+ getGlLocationId() + ", getCodeCombinationId()="
				+ getCodeCombinationId() + ", getRateType()=" + getRateType()
				+ ", getRateDate()=" + getRateDate() + ", getRate()="
				+ getRate() + ", getQuantity()=" + getQuantity()
				+ ", getUnitMeasLookupCode()=" + getUnitMeasLookupCode()
				+ ", getVatTaxName()=" + getVatTaxName() + ", getPphTaxName()="
				+ getPphTaxName() + ", getHasParentFlag()="
				+ getHasParentFlag() + ", getParentRentedItemId()="
				+ getParentRentedItemId() + ", getComments()=" + getComments()
				+ ", getAuthorizationStatus()=" + getAuthorizationStatus()
				+ ", getInterfaceStatus()=" + getInterfaceStatus()
				+ ", getErrReference()=" + getErrReference()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreationDate()="
				+ getCreationDate() + ", getLastUpdatedBy()="
				+ getLastUpdatedBy() + ", getLastUpdateDate()="
				+ getLastUpdateDate() + ", getLastUpdateLogin()="
				+ getLastUpdateLogin() + ", getRequestId()=" + getRequestId()
				+ ", getProgramApplicationId()=" + getProgramApplicationId()
				+ ", getProgramUpdateDate()=" + getProgramUpdateDate()
				+ ", getOcopRequestId()=" + getOcopRequestId()
				+ ", getAttribute1()=" + getAttribute1() + ", getAttribute2()="
				+ getAttribute2() + ", getAttribute3()=" + getAttribute3()
				+ ", getAttribute4()=" + getAttribute4() + ", getAttribute5()="
				+ getAttribute5() + ", getOrderHeaderId()="
				+ getOrderHeaderId() + ", getRentPeriods()=" + getRentPeriods()
				+ ", getAttribute6()=" + getAttribute6() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
}
