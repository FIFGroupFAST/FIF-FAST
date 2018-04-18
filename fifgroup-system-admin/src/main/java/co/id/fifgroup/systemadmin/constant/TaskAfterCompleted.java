package co.id.fifgroup.systemadmin.constant;

public enum TaskAfterCompleted {
	SAVE(0,"SAVE"),
	PRINT(1,"PRINT");
	
	private int key;
	private String value;
	
	private TaskAfterCompleted(int key, String value){
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
		for (TaskAfterCompleted ex : TaskAfterCompleted.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public static String getValue(int key) {
		String n = "";
		for (TaskAfterCompleted ex : TaskAfterCompleted.values()) {
			if(ex.getKey() == key) {
				n = ex.getValue();
			}
		}
		return n;
	}

	
}
