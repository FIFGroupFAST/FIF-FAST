package co.id.fifgroup.systemadmin.constant;

public enum TaskStatus {
	SELECT("-Select-"),
	CANCELED("CANCELED"),
	ERROR("ERROR"),
	NORMAL("NORMAL");
	
	private String value;
	
	private TaskStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
