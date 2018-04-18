package co.id.fifgroup.ssoa.constants;


public enum SOResult {
	CODE("STOCK_OPNAME_RESULT", "Stock Opname Result"),
	BROKEN("SO_RESULT_FOUND_BROKEN", "Ditemukan, kondisi rusak"), 
	GOOD_USED("SO_RESULT_FOUND_GOOD_USED", "Ditemukan, kondisi bagus, digunakan"), 
	GOOD_NOT_USED("SO_RESULT_FOUND_GOOD_NOT_USED","Ditemukan, kondisi bagus, tidak digunakan"), 
	NOT_FOUND("SO_RESULT_NOT_FOUND", "Tidak Ditemukan"),
	NOT_RECORDED("SO_RESULT_NOT_RECORDED", "Tidak Tercatat");
	
	private String code;
	private String value;

	private SOResult(String code, String value) {
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
