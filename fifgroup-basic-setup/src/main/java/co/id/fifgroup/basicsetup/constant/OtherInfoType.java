package co.id.fifgroup.basicsetup.constant;

public enum OtherInfoType {

	FORMAT_DATE("dd-MM-yyyy"),
	BOOLEAN_TRUE("1"),
	BOOLEAN_FALSE("2"),
	SUFFIX_ID("_otherInfo");
	
	private String value;
	
	private OtherInfoType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
}
