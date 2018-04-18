package co.id.fifgroup.systemadmin.constant;

public enum TaskEndDateType {
	NEVER_END(0,"NEVER_END"),
	END_AFTER(1,"END_AFTER"),
	END_BY_DATE(2,"END_BY_DATE");
	
	private int key;
	private String value;
	
	private TaskEndDateType(int key, String value) {
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
		for (TaskEndDateType ex : TaskEndDateType.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public static String getValue(int key) {
		String n = "";
		for (TaskEndDateType ex : TaskEndDateType.values()) {
			if(ex.getKey() == key) {
				n = ex.getValue();
			}
		}
		return n;
	}
}
