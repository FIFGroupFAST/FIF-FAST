package co.id.fifgroup.ssoa.constants;


public enum SOApprovalStatus {
	CODE("STOCK_OPNAME_STATUS","Stock Opname Status"),
	APPROVED("SO_STATUS_APPROVED", "Approved (Open)"), 
	ON_PROGRESS("SO_STATUS_ON_PROGRESS", "In Process"), 
	REJECTED("SO_STATUS_REJECTED",
			"Revised"), 
	NOT_STARTED("SO_STATUS_NOT_STARTED", "Not Started"), 
	SUBMIT("SO_STATUS_SUBMIT", "Submit"), 
	CLOSED("SO_STATUS_CLOSED", "Closed");

	private String code;
	private String value;

	private SOApprovalStatus(String code, String value) {
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
