package co.id.fifgroup.basicsetup.constant;


public enum OtherInfoDataType {

	SELECT("-Select-"),
	NUMERIC("NUMERIC"),
	TEXT("TEXT"),
	DATE("DATE"),
	BOOLEAN("BOOLEAN"),
	LOOKUP("LOOKUP");
	
	private String desc;
	
	private OtherInfoDataType(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
}
