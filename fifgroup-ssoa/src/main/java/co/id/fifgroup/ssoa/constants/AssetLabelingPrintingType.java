package co.id.fifgroup.ssoa.constants;


public enum AssetLabelingPrintingType {
	CODE("LABEL_REPRINTING_TYPE", "Alasan Cetak Ulang Label"),
	LABEL_REPRINTING_TYPE_BROKEN("LABEL_REPRINTING_TYPE_BROKEN", "Rusak"); 
	
	private String code;
	private String value;

	private AssetLabelingPrintingType(String code, String value) {
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
