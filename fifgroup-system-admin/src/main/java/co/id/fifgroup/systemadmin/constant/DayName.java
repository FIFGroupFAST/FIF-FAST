package co.id.fifgroup.systemadmin.constant;

import java.util.Calendar;

public enum DayName {
	SUNDAY(0,"SUN","Sunday"),
	MONDAY(1,"MON","Monday"),
	TUESDAY(2,"TUE","Tuesday"),
	WEDNESDAY(3,"WED","Wednesday"),
	THURSDAY(4,"THU","Thursday"),
	FRIDAY(5,"FRI","Friday"),
	SATURDAY(6,"SAT","Saturday");
	
	private int key;
	private String value;
	private String description;
	
	private DayName(int key, String value, String description) {
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
		for (DayName ex : DayName.values()) {
			if(ex.getValue().equals(value)) {
				n = ex.getKey();
			}
		}
		return n;
	}
	
	public int getDayIntEqualToJavaCalendar(String value){
		if(value.contains("SUN")){
			return Calendar.SUNDAY;
		}else if(value.contains("MON")){
			return Calendar.MONDAY;
		}else if(value.contains("TUE")){
			return Calendar.TUESDAY;
		}else if(value.contains("WED")){
			return Calendar.WEDNESDAY;
		}else if(value.contains("THU")){
			return Calendar.THURSDAY;
		}else if(value.contains("FRI")){
			return Calendar.FRIDAY;
		}else if(value.contains("SAT")){
			return Calendar.SATURDAY;
		}else return 0;
	}
	
	public static String getValue(int key) {
		String n = "";
		for (DayName ex : DayName.values()) {
			if(ex.getKey() == key) {
				n = ex.getValue();
			}
		}
		return n;
	}
}
