package co.id.fifgroup.ssoa.constants;


public enum AssetTransferApprovalStatus {
	CODE("TRANSFER_STATUS","Asset Transfer Status"),
	APPROVED("ASSET_TRANSFER_APPROVED", "Approved"), 
	IN_PROCESS("ASSET_TRANSFER_INPROCESS", "On Approval"), 
	REJECTED("ASSET_TRANSFER_REJECTED",
			"Rejected"), 
	COMPLETED("ASSET_TRANSFER_COMPLETED", "Completed");

	private String code;
	private String value;

	private AssetTransferApprovalStatus(String code, String value) {
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
