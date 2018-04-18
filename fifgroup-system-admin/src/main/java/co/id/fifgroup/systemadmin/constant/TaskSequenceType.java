package co.id.fifgroup.systemadmin.constant;

public enum TaskSequenceType {
	FIRST(1,"FIRST","First"),
	SECOND(2,"SECOND","Second"),
	THIRD(3,"THIRD","Third"),
	FOURTH(4,"FOURTH","Fourth"),
	LAST(5,"LAST", "Last");
	
	private int key;
	private String value;
	private String description;
	
	private TaskSequenceType(int key, String value, String description) {
		this.key = key;
		this.value = value;
		this.description = description;
	}   
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
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
		for (TaskSequenceType ex : TaskSequenceType.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public static String getValue(int key) {
		String n = "";
		for (TaskSequenceType ex : TaskSequenceType.values()) {
			if(ex.getKey() == key) {
				n = ex.getValue();
			}
		}
		return n;
	}
}
