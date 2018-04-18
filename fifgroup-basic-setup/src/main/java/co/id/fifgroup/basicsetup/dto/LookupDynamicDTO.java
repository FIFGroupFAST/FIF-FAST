package co.id.fifgroup.basicsetup.dto;

import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.LookupDynamic;

public class LookupDynamicDTO extends LookupDynamic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5759908350312874997L;
	private Company company;
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
}
