package co.id.fifgroup.eligibility.constant;

import co.id.fifgroup.core.constant.DescriptiveEnum;

public enum ColumnType implements DescriptiveEnum{
	
	CONDITION("Condition Column"),
	RESULT("Result Column")	;

	private String description;
	
	private ColumnType(String description) {
		this.description = description;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
}
