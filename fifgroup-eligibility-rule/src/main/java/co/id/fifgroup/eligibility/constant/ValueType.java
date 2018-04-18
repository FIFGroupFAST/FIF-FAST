package co.id.fifgroup.eligibility.constant;

import co.id.fifgroup.core.constant.DescriptiveEnum;

public enum ValueType implements DescriptiveEnum {
	SCALAR("Scalar"),
	LIST("List");
	
	private String description;

	
	private ValueType(String description) {
		this.description = description;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
}
