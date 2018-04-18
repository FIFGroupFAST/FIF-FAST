package co.id.fifgroup.personneladmin.constant;

public enum PersonnelAdminPage {

	ACCOUNT_INFORMATION("Account Information", "~./hcms/personneladmin/account_information.zul"),
	ADDRESS("Address", "~./hcms/personneladmin/address.zul"),
	ASSIGNMENT("Assignment", "~./hcms/personneladmin/assignment.zul"),
	ASSIGNMENT_INQUIRY("Assignment", "~./hcms/personneladmin/assignment_inquiry.zul"),
	ASSIGNMENT_INTERSHIP("Assignment", "~./hcms/personneladmin/assignment_internship.zul"),
	AWARD_INFROMATION("Award Information", "~./hcms/personneladmin/award_information.zul"),
	BANK_INFORMATION("Bank Information", "~./hcms/personneladmin/bank_information.zul"),
	BENEFIT_ENTITLEMENT("Benefit Entitlement", "~./hcms/personneladmin/benefit_entitlement.zul"),
	CAREER_MANAGEMENT("Career and Talent Information", "~./hcms/personneladmin/career_management.zul"),
	COMMUNICATION_MEDIA("Communication Media", "~./hcms/personneladmin/communication_media.zul"),
	COMPETENCY_MANAGEMENT("Competency Management", "~./hcms/personneladmin/competency_management.zul"),
	CONTACT("Contact", "~./hcms/personneladmin/contact.zul"),
	DEVELOPMENT_HISTORY("Development History", "~./hcms/personneladmin/development_history.zul"),
	DICIPLINARY_LETTER("Diciplinary Letter", "~./hcms/personneladmin/disciplinary_letter.zul"),
	DOCUMENTS("Documents", "~./hcms/personneladmin/document_list.zul"),
	EDUCATION("Education", "~./hcms/personneladmin/education.zul"),
	EXIT_CLEARANCE("Exit Clearance", "~./hcms/personneladmin/exit_clearance.zul"),
	//Remark and Add by RIM ticket : 15111616041796 - Otomatisasi Flag Boarding - 21 Jan 2016
	/*HOUSING_ALLOWANCE("Housing Allowance","~./hcms/personneladmin/housing_allowance.zul"),*/
	HOUSING_ALLOWANCE("Housing Allowance","~./hcms/personneladmin/housing_allowance_inquiry.zul"),
	//End Remark and Add by RIM ticket : 15111616041796 - Otomatisasi Flag Boarding - 21 Jan 2016
	HOUSING_TRANSACTION_INQUIRY("Housing Transaction Inquiry","~./hcms/personneladmin/housing_transaction_inquiry.zul"),
	LEAVE_ENTITLEMENT("Leave Entitlement", "~./hcms/personneladmin/leave_entitlement.zul"),
	MOBILITY("Mobility","~./hcms/personneladmin/mobility.zul"),
	OCOP("Ocop Transaction", "~./hcms/ocop/pea_ocop_transaction_inquiry.zul"),
	OTHER_INFO("Other Info", "~./hcms/personneladmin/other_information.zul"),
	PEOPLE_HISTORY("People History", "~./hcms/personneladmin/people_history.zul"), 
	PEOPLE_INFORMATION("People Information", "~./hcms/personneladmin/people_information.zul"), 
	PERFORMANCE_REVIEW("Performance Review", "~./hcms/personneladmin/performance_review.zul"),
	REDEMPTION_TRANSACTION("Redemption Transaction History","~./hcms/ilearning/pea_redemption_point_history.zul"),
	ROLE("Role", "~./hcms/personneladmin/role_information.zul"),
	SALARY_HISTORY("Salary History", "~./hcms/personneladmin/salary_history.zul"),
	SPP_HISTORY("SPP History","~./hcms/personneladmin/spp_history.zul"),
	TRAINING_HISTORY("eLearning History","~./hcms/ilearning/pea_training_history.zul"),
	TRAINING_INFORMATION("Training Information", "~./hcms/personneladmin/training_information.zul"),
	TRANSACTION_INQUIRY("Transaction Inquiry", "~./hcms/personneladmin/transaction_inquiry.zul"),
	VITAL_STATISTIC("Vital Statistic", "~./hcms/personneladmin/vital_statistic.zul"),
	WORKING_EQUIPMENT_HISTORY("Working Equipment History", "~./hcms/personneladmin/working_equipment_history.zul"),
	WORKING_EXPERIENCE("Working Experience", "~./hcms/personneladmin/working_experience.zul");

	private String description;
	private String url;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private PersonnelAdminPage(String description, String url) {
		this.description = description;
		this.url = url;
	}	
}
