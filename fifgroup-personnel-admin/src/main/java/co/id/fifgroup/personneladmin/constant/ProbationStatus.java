package co.id.fifgroup.personneladmin.constant;


public enum ProbationStatus {
	SELECT("- Select -"),
	FAILED("FAILED"),
	OPEN("OPEN"),
	PASSED("PASSED");
	
	private String desc;

	private ProbationStatus(String desc) {
		this.setDesc(desc);
	}
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
