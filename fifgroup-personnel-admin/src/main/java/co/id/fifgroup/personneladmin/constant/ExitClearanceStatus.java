package co.id.fifgroup.personneladmin.constant;

public enum ExitClearanceStatus {
	SELECT("- Select -"),
	CANCELED("CANCELED"),
	CLEARED("CLEARED"),
	PENDING("PENDING");
	
	private String desc;

	private ExitClearanceStatus(String desc) {
		this.setDesc(desc);
	}
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
