package co.id.fifgroup.basicsetup.constant;


public enum LookupType {
	SELECT("-Select-"),
	DEPENDENT("DEPENDENT"), 
	DYNAMIC("DYNAMIC");
	
	private String desc;
	
	private LookupType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
	
}
