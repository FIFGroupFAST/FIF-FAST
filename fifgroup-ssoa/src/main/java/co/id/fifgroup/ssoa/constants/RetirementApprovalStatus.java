package co.id.fifgroup.ssoa.constants;


public enum RetirementApprovalStatus {
	CODE("RETIREMENT_STATUS","Retirement Status"),
	APPROVED("RETIREMENT_STATUS_APPROVED", "Approved"), 
	ON_PROGRESS("RETIREMENT_STATUS_ON_PROGRESS", "On Progress"), 
	REJECTED("RETIREMENT_STATUS_REJECTED",
			"Rejected"), 
	NOT_STARTED("RETIREMENT_STATUS_NOT_STARTED", "Not Started"), 
	SUBMIT("RETIREMENT_STATUS_SUBMIT", "Submit"), 
	CLOSED("RETIREMENT_STATUS_CLOSED", "Closed");

	private String code;
	private String value;

	private RetirementApprovalStatus(String code, String value) {
		this.setCode(code);
		this.setValue(value);
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
