package co.id.fifgroup.personneladmin.constant;

public enum ReferencePersonnelAdministration {

	// lookup name
	LOOKUP_COUNTRY("MST_COUNTRY"),
	LOOKUP_RELIGION("MST_RELIGION"),
	LOOKUP_BLOOD_TYPE("MST_BLOOD_TYPE"),
	LOOKUP_BLOOD_TYPE_RHESUS("MST_BLOOD_TYPE_RHESUS"),
	LOOKUP_DISABILITY("PEA_DISABILITY"),
	LOOKUP_HOBBY("MST_HOBBY"),
	LOOKUP_MARITAL("MST_MARITAL"),
	LOOKUP_PTKP_STATUS("MST_PTKP_STATUS"),
	LOOKUP_DRIVING_LICENSE("MST_DRIVING_LICENSE"),
	LOOKUP_ACTION_TYPE("PEA_ACTION_TYPE"),
	LOOKUP_TRANSFER_BY("TRA_TRANSFER_BY"),
	LOOKUP_ASSIGNMENT_STATUS("PEA_ASSIGNMENT_STATUS"),
	LOOKUP_COMMUNICATION_TYPE("MST_COMMUNICATION_TYPE"),
	LOOKUP_EDUCATION_LEVEL("MST_EDU_LEVEL"),
	LOOKUP_INSTITUTION("MST_INSTITUTION"),
	LOOKUP_FACULTY("MST_FACULTY"),
	LOOKUP_CONTACT("MST_CONTACT"),
	LOOKUP_FAMILY_MEMBER_PRIORITY("MST_FAMILY_MEMBER_PRIORITY"),
	LOOKUP_DOCUMENTS("PEA_DOCUMENTS"),
	LOOKUP_MOBILITY_STATUS("MOBILITY_STATUS"),
	// 14040715241425_CR HCMS – Recruitment_RAH
	LOOKUP_REC_DOCUMENTS("REC_DOCUMENTS"),
	LOOKUP_APPAREL("PEA_APPAREL"),
	LOOKUP_AWARD_TYPE("PEA_AWARD_TYPE"),
	LOOKUP_AWARD_PRIZE("PEA_AWARD_PRIZE"),
	LOOKUP_ROLE("PEA_ROLE"),
	LOOKUP_SECONDARY_TYPE("PEA_SECONDARY_TYPE"),
	LOOKUP_ACCOUNT_TYPE("PEA_ACCOUNT_TYPE"),
	LOOKUP_VALID_HIRE_DATE("VALID_HIRE_DATE"),
	LOOKUP_JOB_GROUP("WSU_JOB_GROUP"),
	LOOKUP_PERSONAL_GRADE("WSU_PERSONAL_GRADE"),
	LOOKUP_APPAREL_SIZE("PEA_APPAREL_SIZE"),
	LOOKUP_HOUSING_ALLOWANCE("MST_HOUSING_ALLOWANCE"), 
	
	// global setting name
	GLOBAL_PROBATION_PERIOD("PROBATION_PERIOD"),
	GLOBAL_VALID_AGE("VALID_AGE"),
	
	// Other info name
	OTHER_INFO_ASSIGNMENT("ASSIGNMENT"),
	OTHER_INFO_PERSONNEL_ADMINISTRATION("PERSONNEL_ADMINISTRATION"),
	OTHER_INFO_JOB("JOB"),
	
	// Other info detail code
	DETAIL_CODE_IS_TRAINEE("TRAINING_FLAG"),
	DETAIL_CODE_TRAINEE_PERIOD("TRAINING_PERIOD"),
	DETAIL_CODE_JOB_CODE_HRMS("HRMS_JOB_CODE"),
	
	// sequence generator
	SEQUENCE_EMPLOYEE_NUMBER("PEA_EMP_NUM"),
	SEQUENCE_CWK_NUMBER("PEA_CWK_NUM"),
	SEQUENCE_INTERNSHIP_NUMBER("PEA_INTERNSHIP_NUM"),
	
	// matrix location
	MATRIX_HOUSING("HOUSING"),
	MATRIX_BOARDING("BOARDING"),
	
	// fix value
	DEFAULT_NATIONALITY_CODE("ID"),
	
	// approver system
	APPROVER_SYSTEM("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF");
	;
	
	private String reference;
		
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}

	private ReferencePersonnelAdministration(String reference) {
		this.reference = reference;
	}
	
}
