package co.id.fifgroup.ssoa.constants;


public enum ProcessType {
	CODE("ASSET_PROCESS_TYPE", "Asset Process Type"),
	PROCESS_TYPE_RETIRE("ASSET_PROCESS_TYPE_RETIREMENT", "Retire"), 
	PROCESS_TYPE_ADDITION("ASSET_PROCESS_TYPE_ADDITION", "Addition"),
	PROCESS_TYPE_TRANSFER("ASSET_PROCESS_TYPE_TRANSFER", "Transfer");
	
	private String code;
	private String value;

	private ProcessType(String code, String value) {
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
