package co.id.fifgroup.systemadmin.constant;

public enum ReportType {
	SELECT(0,"-Select-"),
	GLOBAL(1, "Global"),
	HO(2, "HO"),
	BRANCH(3, "Branch"),
	BRANCH_WITHOUT_POS(4, "Branch without POS"),
	POS(5, "POS");
	
	private String value;
	private int key;
	
	private ReportType(int key, String value){
		this.value = value;
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static int getKey(String value){
		int n = 0;
		for (ReportType ex : ReportType.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public int getKey() {
		return key;
	}
	
	
	
}
