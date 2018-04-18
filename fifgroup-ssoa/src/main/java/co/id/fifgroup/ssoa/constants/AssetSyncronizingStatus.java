package co.id.fifgroup.ssoa.constants;


public enum AssetSyncronizingStatus {
	CODE("ASSET_SYNC_STATUS", "Asset Sync Status"),
	ON_SUCCESS("ASSET_SYNC_STATUS_SUCCESS", "Finish and Success"), 
	ON_PROCESS("ASSET_SYNC_STATUS_ON_PROGRESS", "On Progress");
	
	
	private String code;
	private String value;

	private AssetSyncronizingStatus(String code, String value) {
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
