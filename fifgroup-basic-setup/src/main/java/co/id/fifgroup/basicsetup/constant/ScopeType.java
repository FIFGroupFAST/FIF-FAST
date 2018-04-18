package co.id.fifgroup.basicsetup.constant;


public enum ScopeType {

	SELECT(null, "-Select-"),
	COMMON(Long.parseLong("-1"), "COMMON");
	
	private Long value;
	private String desc;
	
	private ScopeType(Long value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public Long getValue() {
		return this.value;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
}
