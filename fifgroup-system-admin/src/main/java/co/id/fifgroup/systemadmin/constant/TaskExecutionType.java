package co.id.fifgroup.systemadmin.constant;

public enum TaskExecutionType {
	SEQUENTIAL(0,"SEQUENTIAL"),
	PARALLEL(1, "PARALLEL");
	
	private int key;
	private String value;
	
	private TaskExecutionType(int key, String value) {
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

	public static int getKey(String value) {
		int n = 0;
		for (TaskExecutionType ex : TaskExecutionType.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public static String getValue(int key) {
		String n = "";
		for (TaskExecutionType ex : TaskExecutionType.values()) {
			if(ex.getKey() == key) {
				n = ex.getValue();
			}
		}
		return n;
	}
}
