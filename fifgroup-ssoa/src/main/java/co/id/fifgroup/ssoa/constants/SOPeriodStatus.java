package co.id.fifgroup.ssoa.constants;


public enum SOPeriodStatus {
	CODE("SO_PERIOD_STATUS", "Stock Opname Period"),
	NOT_STARTED("SO_PERIOD_STATUS_NOT_STARTED", "Not Started"), 
	IN_PROCESS("SO_PERIOD_STATUS_IN_PROCESS", "In Process"), 
	FINISH("SO_PERIOD_STATUS_IN_FINISH","Finish"),
	
	SUCESS("SO_PERIOD_SEND_SUCESS", "Send"), 
	FAIL("SO_PERIOD_SEND_FAIL","Fail"),
	CODE_SO_PERIOD_SEND_STATUS("SO_PERIOD_SEND_STATUS","Stock Opname Period Send Status");
	
	
	private String code;
	private String value;

	private SOPeriodStatus(String code, String value) {
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
