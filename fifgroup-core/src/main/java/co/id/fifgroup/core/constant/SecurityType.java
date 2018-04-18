package co.id.fifgroup.core.constant;

public enum SecurityType {
	VIEW_OWN(0,"VIEW_OWN"),
	VIEW_SINGLE(1,"VIEW_SINGLE"),
	VIEW_HIERARCHY(2,"VIEW_HIERARCHY"),
	VIEW_BRANCH(3,"VIEW_BRANCH"),
	VIEW_FUNCTIONAL(4,"VIEW_FUNCTIONAL"),
	VIEW_ALL(5,"VIEW_ALL");
	
	private int key;
	private String value;

	private SecurityType(int key, String value) {
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
		for (SecurityType ex : SecurityType.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public static String getValue(int key) {
		String n = "";
		for (SecurityType ex : SecurityType.values()) {
			if(ex.getKey() == key) {
				n = ex.getValue();
			}
		}
		return n;
	}
}
