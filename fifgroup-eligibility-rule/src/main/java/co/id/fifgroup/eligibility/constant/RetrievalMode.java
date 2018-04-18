package co.id.fifgroup.eligibility.constant;

import co.id.fifgroup.core.constant.DescriptiveEnum;

public enum RetrievalMode implements DescriptiveEnum {
	QUERY("SQL Query"),
	SUPPLIED("Supplied Fact");

	private String description;
	
	private RetrievalMode(String description) {
		this.description = description;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	
}
