package co.id.fifgroup.core.constant;

public enum FunctionAccessType {
	FULL(0,"FULL"),
	READ(1,"READ"),
	CUSTOM(2,"CUSTOM");
	
	private int key;
	private String value;
	
	private FunctionAccessType(int key, String value) {
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
		for (FunctionAccessType ex : FunctionAccessType.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public static String getValue(int key) {
		String n = "";
		for (FunctionAccessType ex : FunctionAccessType.values()) {
			if(ex.getKey() == key) {
				n = ex.getValue();
			}
		}
		return n;
	}
}
