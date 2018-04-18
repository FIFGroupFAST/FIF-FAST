package co.id.fifgroup.systemadmin.constant;

public enum TaskScheduleType {

	IMMEDIATELY(0,"IMMEDIATELY"),
	DAILY(1,"DAILY"),
	WEEKLY(2,"WEEKLY"),
	MONTHLY(3,"MONTHLY"),
	YEARLY(4,"YEARLY"),
	ONE_TIME(5,"ONE_TIME");

	private int key;
	private String value;
	
	private TaskScheduleType(int key, String value) {
		this.key = key;
		this.setValue(value);
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
		for (TaskScheduleType ex : TaskScheduleType.values()) {
			if(ex.getKey() == key){
				n = ex.getValue();
				break;
			}
		}
		return n;
	}
}
