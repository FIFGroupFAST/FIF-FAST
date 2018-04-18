package co.id.fifgroup.systemadmin.constant;

public enum ExecutableFileType {
	Batch(0,"BATCH"),
	Report(1,"REPORT");
	
	private int key;
	private String value;
	
	private ExecutableFileType(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
