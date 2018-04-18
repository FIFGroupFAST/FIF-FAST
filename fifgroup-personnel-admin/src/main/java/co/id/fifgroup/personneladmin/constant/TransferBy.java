package co.id.fifgroup.personneladmin.constant;

public enum TransferBy {

	SELECT("- Select -"),
	TRANSFERED_BY_OWN("Transfered by Own"), 
	TRANSFERED_BY_COMPANY("Transfered by Company");

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private TransferBy(String description) {
		this.description = description;
	}
}
