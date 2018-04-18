package co.id.fifgroup.eligibility.constant;

import co.id.fifgroup.core.constant.DescriptiveEnum;

public enum FieldType implements DescriptiveEnum{ 
	TEXT("Text"),
	NUMBER("Number"),
	LOOKUP("Lookup"),
	DATE("Date");

	private final String description;
	
	private FieldType(String description) {
		this.description = description;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
}
