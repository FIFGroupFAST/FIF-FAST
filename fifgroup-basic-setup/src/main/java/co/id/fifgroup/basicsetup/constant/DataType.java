package co.id.fifgroup.basicsetup.constant;

public enum DataType {

	NUMERIC,
	TEXT,
	DATE,
	LOOKUP,
	BOOLEAN;
	
	public static DataType getType(String name) {
		return DataType.valueOf(name);
	}
}
