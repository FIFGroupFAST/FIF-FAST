package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class OcopGlExpense implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long glCcId;
	private String companyGlCode;
	private Long personId;
	private String inventoryItemCode;
	public Long getGlCcId() {
		return glCcId;
	}
	public void setGlCcId(Long glCcId) {
		this.glCcId = glCcId;
	}
	public String getCompanyGlCode() {
		return companyGlCode;
	}
	public void setCompanyGlCode(String companyGlCode) {
		this.companyGlCode = companyGlCode;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getInventoryItemCode() {
		return inventoryItemCode;
	}
	public void setInventoryItemCode(String inventoryItemCode) {
		this.inventoryItemCode = inventoryItemCode;
	}
	
	

}
