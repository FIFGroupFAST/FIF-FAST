package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.core.domain.Supplier;

public class SupplierDTO extends Supplier implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String privateSupplier;
    private Boolean isPrivateSupplier;
    private Long vendorId;
    private List<String> inSuplOpexSubtype;
    private String searchCriteria;
    
	
	public String getPrivateSupplier() {
		return privateSupplier;
	}
	public void setPrivateSupplier(String privateSupplier) {
		this.privateSupplier = privateSupplier;
	}
	public Boolean IsPrivateSupplier() {
		return isPrivateSupplier;
	}
	public void setIsPrivateSupplier(Boolean isPrivateSupplier) {
		this.isPrivateSupplier = isPrivateSupplier;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public List<String> getInSuplOpexSubtype() {
		return inSuplOpexSubtype;
	}
	public void setInSuplOpexSubtype(List<String> inSuplOpexSubtype) {
		this.inSuplOpexSubtype = inSuplOpexSubtype;
	}
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	@Override
	public String toString() {
		return "SupplierDTO [privateSupplier=" + privateSupplier
				+ ", isPrivateSupplier=" + isPrivateSupplier
				+ ", getIsSuplOpexType()=" + getIsSuplOpexType()
				+ ", getSuplOpexSubtype()=" + getSuplOpexSubtype()
				+ ", getOrgId()=" + getOrgId() + "]";
	}
}
