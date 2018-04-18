package co.id.fifgroup.core.constant;

public enum FilterType {
	INCLUDE(0,"INCLUDE"),
	EXCLUDE(1,"EXCLUDE");
	
	private int key;
	private String value;

	private FilterType(int key, String value) {
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
		for (FilterType ex : FilterType.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public static String getValue(int key) {
		String n = "";
		for (FilterType ex : FilterType.values()) {
			if(ex.getKey() == key) {
				n = ex.getValue();
			}
		}
		return n;
	}
}
