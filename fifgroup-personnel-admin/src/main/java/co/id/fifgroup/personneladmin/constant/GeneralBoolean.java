package co.id.fifgroup.personneladmin.constant;

public enum GeneralBoolean {
	SELECT(null, "- Select -"),
	YES(true, "Yes"),
	NO(false, "No");
	
	private Boolean valid;
	private String description;
	
	private GeneralBoolean(Boolean valid, String description) {
		this.valid = valid;
		this.description = description;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
