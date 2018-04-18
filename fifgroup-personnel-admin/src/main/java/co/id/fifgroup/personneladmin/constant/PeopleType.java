package co.id.fifgroup.personneladmin.constant;


public enum PeopleType {
	SELECT("- Select - "),
	CWK("CWK"),
	EMPLOYEE("EMPLOYEE"), 
	EX_EMPLOYEE("EX-EMPLOYEE"), 
	EX_INTERNSHIP("EX-INTERNSHIP"), 
	INTERNSHIP("INTERNSHIP"),
	EX_CWK("EX-CWK");

	private String description;

	private PeopleType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String getPeopleTypeDescription(String peopleTypeCode) {
		for (PeopleType peopleType : PeopleType.values()) {
			if (peopleType.name().equalsIgnoreCase(peopleTypeCode)) {
				return peopleType.getDescription();
			}
		}
		return null;
	}
	
	public static PeopleType getPeopleType(String peopleTypeCode) {
		for (PeopleType peopleType : PeopleType.values()) {
			if (peopleType.name().equalsIgnoreCase(peopleTypeCode)) {
				return peopleType;
			}
		}
		return null;
	}

}
