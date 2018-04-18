package co.id.fifgroup.personneladmin.constant;

public enum EmploymentCategory {
	SELECT("- Select -"),
	PERMANENT("PERMANENT"),
	PROBATION("PROBATION"),
	TRAINEE("TRAINEE");
	
	private String description;
	
	private EmploymentCategory(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String getEmploymentCategoryDescription(String employmentCategoryCode) {
		for (EmploymentCategory employmentCategory : EmploymentCategory.values()) {
			if (employmentCategory.name().equalsIgnoreCase(employmentCategoryCode)) {
				return employmentCategory.getDescription();
			}
		}
		return null;
	}
}
