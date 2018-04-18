package co.id.fifgroup.eligibility.constant;

import co.id.fifgroup.core.constant.DescriptiveEnum;

public enum LookupValueFrom  implements DescriptiveEnum{
	CODE("Lookup Code"),
	MEANING("Lookup Meaning"),
	DESCRIPTION("Lookup Description");

	private final String description;
	
	
	private LookupValueFrom(String description) {
		this.description = description;
	}
	
	@Override
	public String getDescription() {
		return description;
	} 

}
