package co.id.fifgroup.personneladmin.constant;

public enum EmployeeFlag {

	NEW("N"),	
	MUTTATION("M"),
	RESIGN("R");
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private EmployeeFlag(String description) {
		this.description = description;
	}
}
