package co.id.fifgroup.basicsetup.constant;


public enum LookupDataType {

	SELECT("-Select-"),
	NUMERIC("NUMERIC"),
	TEXT("TEXT"),
	DATE("DATE");
	
	private String desc;
	
	private LookupDataType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
}
