package co.id.fifgroup.audit;

public enum TrxType {
	SELECT("-Select-"),
	FUNCTION_SETUP("Function Setup"),
	MENU_SETUP("Menu Setup"),
	RESPONSIBILITY_SETUP("Responsibility Setup"),
	JOB_RESPONSIBILITY("Job Responsibility"),
	TASK_SETUP("Task Setup"),
	TASK_GROUP_SETUP("Task Group Setup"),
	SECONDARY_ASSIGNEMNT("Update Secondary Assignment"),
	BENEFIT_NON_PRAPAYMENT("Benefit Non Prepayment"),
	BENEFIT_PREPAYMENT("Benefit Prepayment"),
	CHANGE_TRAINEE_TO_PERMANENT("Change Trainee to Permanent"),
	HIRE_INTERNSHIP("Hire Internship"),
	ADD_NEW_EMPLOYEE("Add New Employee"),
	UPDATE_CONTACT_DATA("Update Contact Data"),
	MUTASI_AFFCO("Mutasi AFFCO"),
	CANCEL_JOIN("Cancel Join"),
	UPDATE_PERSONAL_INFORMATION("Update Personal Information Data"),
	TERMINATE_TRANINEE("Terminate Trainee"),
	TRANSFER_WITHIN_GROUP("Transfer Within Group");
	
	private String value;
	
	private TrxType(String value){
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
