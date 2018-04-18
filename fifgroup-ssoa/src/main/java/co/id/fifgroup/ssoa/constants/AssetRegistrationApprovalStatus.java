package co.id.fifgroup.ssoa.constants;


public enum AssetRegistrationApprovalStatus {
	CODE("REGISTRATION_STATUS","Asset Transfer Status"),
	APPROVED("ASSET_REGISTRATION_APPROVED", "Approved"), 
	IN_PROCESS("ASSET_REGISTRATION_INPROCESS", "On Approval"), 
	REJECTED("ASSET_REGISTRATION_REJECTED","Rejected"), 
	COMPLETED("ASSET_REGISTRATION_COMPLETED", "Completed");

	private String code;
	private String value;

	private AssetRegistrationApprovalStatus(String code, String value) {
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
