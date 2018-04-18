package co.id.fifgroup.personneladmin.constant;


public enum TransferInStatus {

	SELECT("- Select -"),
	SUBMITTED("Submitted"),
	HIRED("Hired");
	
	private String description;
	
	private TransferInStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
