package co.id.fifgroup.eligibility.constant;

import co.id.fifgroup.core.constant.DescriptiveEnum;

public enum Operator implements DescriptiveEnum {
	GREATER_THAN(">"),
	LOWER_THAN("<"),
	GREATER_THAN_EQUAL(">="),
	LOWER_THAN_EQUAL("<="),
	EQUALS("==");
	
	private String symbol;
	
	private Operator(String symbol) {
		this.symbol = symbol;
	}
	public String getSymbol() {
		return symbol;
	}
	@Override
	public String getDescription() {
		return symbol;
	}
}
