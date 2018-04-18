package co.id.fifgroup.systemadmin.constant;

public enum TaskPhase {
	SELECT("-Select-"),
	COMPLETED("COMPLETED"),
	PENDING("PENDING"),
	RUNNING("RUNNING");
	
	private String value;
	
	private TaskPhase(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
