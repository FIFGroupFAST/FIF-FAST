package co.id.fifgroup.personneladmin.constant;

public enum HousingAllowance {
	
	SELECT("- Select -"),
	NONE("NONE"),
	HOUSING("HOUSING"),
	BOARDING("BOARDING");

	private String description;

	private HousingAllowance(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String getHousingAllowanceDescription(String peopleTypeCode) {
		for (HousingAllowance housingAllowance : HousingAllowance.values()) {
			if (housingAllowance.name().equalsIgnoreCase(peopleTypeCode)) {
				return housingAllowance.getDescription();
			}
		}
		return null;
	}
	
	public static HousingAllowance getHousingAllowance(String peopleTypeCode) {
		for (HousingAllowance housingAllowance : HousingAllowance.values()) {
			if (housingAllowance.name().equalsIgnoreCase(peopleTypeCode)) {
				return housingAllowance;
			}
		}
		return null;
	}

	
}
