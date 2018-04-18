package co.id.fifgroup.systemadmin.constant;

public enum MonthName {
	JAN(1,"JAN", "January"),
	FEB(2,"FEB", "February"),
	MAR(3,"MAR", "March"),
	APR(4,"APR", "April"),
	MAY(5,"MAY", "May"),
	JUN(6,"JUN", "June"),
	JUL(7,"JUL", "July"),
	AUG(8,"AUG", "August"),
	SEP(9,"SEP", "September"),
	OCT(10,"OCT", "October"),
	NOV(11,"NOV", "November"),
	DEC(12,"DEC", "December");
	
	private int key;
	private String value;
	private String description;
	
	private MonthName(int key, String value, String description) {
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
	
	public static int getKey(String value){
		int n = 0;
		for (MonthName ex : MonthName.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public static MonthName getTypeByKey(int key) {
		for(MonthName mn : MonthName.values()) {
			if(mn.getKey() == key) {
				return mn;
			}
		}
		return null;
	}
}
