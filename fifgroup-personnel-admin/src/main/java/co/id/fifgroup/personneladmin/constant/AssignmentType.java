package co.id.fifgroup.personneladmin.constant;

public enum AssignmentType {

	SELECT("- Select - "),	
	PRIMARY("Primary"),
	SECONDARY("Secondary");
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private AssignmentType(String description) {
		this.description = description;
	}
	
}
