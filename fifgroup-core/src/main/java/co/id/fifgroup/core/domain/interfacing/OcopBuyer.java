package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class OcopBuyer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String companyGlCode;
	private Long inventoryItemId;
	private Long glLocationId;
	private Long buyer;
	public String getCompanyGlCode() {
		return companyGlCode;
	}
	public void setCompanyGlCode(String companyGlCode) {
		this.companyGlCode = companyGlCode;
	}
	public Long getInventoryItemId() {
		return inventoryItemId;
	}
	public void setInventoryItemId(Long inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}
	public Long getGlLocationId() {
		return glLocationId;
	}
	public void setGlLocationId(Long glLocationId) {
		this.glLocationId = glLocationId;
	}
	public Long getBuyer() {
		return buyer;
	}
	public void setBuyer(Long buyer) {
		this.buyer = buyer;
	}
	
	
	
}
