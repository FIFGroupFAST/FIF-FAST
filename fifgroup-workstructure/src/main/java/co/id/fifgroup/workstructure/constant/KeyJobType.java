package co.id.fifgroup.workstructure.constant;

public enum KeyJobType {

	SELECT("-Select-"),
	KEY_JOB("Key Job"), 
	NOT_KEY_JOB("Not Key Job");
	
	private String desc;
	
	private KeyJobType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
}
