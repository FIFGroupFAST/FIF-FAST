package co.id.fifgroup.core.constant;

public enum ResponsibilitiesType {

	HC_SELF_SERVICE("HC SELF SERVICE"),
	MANAGER_SELF_SERVICE("MANAGER SELF SERVICE"),
	EMPLOYEE_SELF_SERVICE("EMPLOYEE SELF SERVICE"),
	GLOBAL_SUPER_HC_ADMINISTRATOR("GLOBAL SUPER HC ADMINISTRATOR"),
	GLOBAL_SUPER_HC("GLOBAL SUPER HC"),
	GLOBAL_SUPER_HC_ADMIN("GLOBAL SUPER HC ADMIN"),
	GLOBAL_HC_SELF_SERVICE("GLOBAL HC SELF SERVICE"),
	HC_AREA_SELF_SERVICE("HC AREA SELF SERVICE"),
	HCEC("HCEC SELF SERVICE"),
	SUPERVISOR("SUPERVISOR SELF SERVICE"),
	HCOD("HCOD"),
	WORKFLOW_ADMINISTRATOR("WORKFLOW ADMINISTRATOR"),
	HCREC("HCREC SELF SERVICE"),
	HCPM_HEAD("HCPM HEAD"),
	HCPM_OFFICER("HCPM OFFICER"),
	HCREC_ADMIN("HCREC ADMIN"),
	PIC_TICKETING("PIC TICKETING"),
	PIC_VOUCHER("PIC VOUCHER"),
	DEPARTMENT_OWNER_SELF_SERVICE("DEPARTMENT OWNER SELF SERVICE"),
	OGS_SELF_SERVICE("OGS SELF SERVICE"),
	DEVELOPMENT_ADMINISTRATOR_SELF_SERVICE("DEVELOPMENT ADMINISTRATOR SELF SERVICE"),
	TRAINING_FOR_AREA_INSTRUCTURE("TRAINING FOR AREA INSTRUCTURE"),
	OSSH_SELF_SERVICE("OSSH SELF SERVICE"),
	HC_ADMINISTRATOR("HC ADMINISTRATOR")
	;
	
	private String value;
	
	private ResponsibilitiesType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
}
