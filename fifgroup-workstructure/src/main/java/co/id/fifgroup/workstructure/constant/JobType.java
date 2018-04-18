package co.id.fifgroup.workstructure.constant;

public enum JobType {

	SELECT("-Select-"),
	COORDINATOR("Coordinator"),
	PROCESSOR("Processor"),
	CLERICAL("Clerical"),
	NONE("None");
	
	private String desc;
	
	private JobType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
}
