package co.id.fifgroup.systemadmin.constant;

public enum TaskMonthlyPattern {
	DAY(0,"DAY"),
	THE(1,"THE");
	
	private int key;
	private String value;
	
	private TaskMonthlyPattern(int key, String value) {
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
		for (TaskScheduleType ex : TaskScheduleType.values()) {
			if(ex.getValue().equals(value)){
				n = ex.getKey();
				break;
			}
		}
		return n;
	}
	
	public static String getValue(int key) {
		String n = "";
		for (TaskMonthlyPattern ex : TaskMonthlyPattern.values()) {
			if(ex.getKey() == key) {
				n = ex.getValue();
			}
		}
		return n;
	}
}
