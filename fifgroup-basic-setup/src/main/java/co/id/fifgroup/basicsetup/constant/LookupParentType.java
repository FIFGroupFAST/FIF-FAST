package co.id.fifgroup.basicsetup.constant;


public enum LookupParentType {

	SELECT(Long.parseLong("-1"), "-Select-"),
	NONE(null, "NONE");
	
	private Long value;
	private String desc;
	
	private LookupParentType(Long value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public Long getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
}
