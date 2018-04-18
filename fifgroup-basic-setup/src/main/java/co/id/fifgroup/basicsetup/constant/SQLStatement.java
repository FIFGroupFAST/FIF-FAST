package co.id.fifgroup.basicsetup.constant;

public enum SQLStatement {

	SELECT("SELECT"),
	FROM("FROM"),
	WHERE("WHERE"),
	ORDER_BY("ORDER BY");
	
	private String value;
	
	private SQLStatement(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
}
