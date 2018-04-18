package co.id.fifgroup.workstructure.constant;

public enum PeopleCategory {

	SELECT("-Select-"),
	OFFICE_PEOPLE("Office People"), 
	FIELD_PEOPLE("Field People");
	
	private String desc;
	
	private PeopleCategory(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
}
