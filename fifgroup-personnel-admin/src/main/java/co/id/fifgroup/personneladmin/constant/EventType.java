package co.id.fifgroup.personneladmin.constant;

public enum EventType {

	HIRED_INTERNSHIP("Hired Internship"),
	HIRED_PROBATION("Hired Probation"),
	HIRED_PERMANENT("Hired Permanent"),
	HIRED_TRAINEE("Hired Trainee"),
	TRANSFER("Transfer"),
	TASKFORCE("Taskforce"),
	PROMOTION("Promotion"),
	REGULAR_PROMOTION("Regular Promotion"),
	IRRPROM_ACTING("IRRPROM Acting"),
	IRRPROM_DEFINITIVE("IRRPROM Definitive"),
	TERMINATION("Termination"),
	TERMINATION_INTERNSHIP("Termination Internship"),
	REVERSED_TERMINATION("Reversed Termination"),
	SP1("SP1"),
	SP2("SP2"),
	SP3("SP3"),
	PASSED_PROBATION("Passed Probation"),
	FAILED_PROBATION("Failed Probation"),
	RECEIVED_AWARD("Receive Award"),
	DELETED_AWARD("Deleted Award"),
	DEMOTION("Demotion"),
	DELETED_ASSIGNMENT("Deleted Assignment"),
	EXTEND_INTERNSHIP("Extend Internship"),
	CANCEL_JOIN("Cancel Join");
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private EventType(String description) {
		this.description = description;
	}
	
	public static String getDescription(String eventType) {
		String description = "";
		for (EventType et : EventType.values()) {
			if(et.name().equals(eventType)) {
				description = et.getDescription();
			}
		}
		return description;
	}
}
