package co.id.fifgroup.ssoa.constants;


public enum AssetType {
	CODE("ASSET_TYPE", "Asset Type"),
	ASSET_TYPE_AST("ASSET_TYPE_AST", "AST"), 
	ASSET_TYPE_LVA("ASSET_TYPE_LVA", "LVA");
	
	private String code;
	private String value;

	private AssetType(String code, String value) {
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
