package co.id.fifgroup.ssoa.constants;


public enum SOActionStatus {
	CODE("SO_ACTION_STATUS","Stock Opname Status"),
	NOT_STARTED("SO_ACTION_NOT_STARTED", "Not Started"), 
	CLOSED("SO_ACTION_CLOSED", "Closed");

	private String code;
	private String value;

	private SOActionStatus(String code, String value) {
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
