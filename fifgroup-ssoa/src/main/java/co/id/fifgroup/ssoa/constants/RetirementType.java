package co.id.fifgroup.ssoa.constants;


public enum RetirementType {
	CODE("RETIREMENT_TYPE", "Retirement Type"),
	SELL("RETIREMENT_TYPE_SELL", "Penjualan"), 
	INSURANCE("RETIREMENT_TYPE_INSURANCE", "Klaim Asuransi"), 
	GRANT("RETIREMENT_TYPE_GRANT","Hibah"), 
	LOSE("RETIREMENT_TYPE_LOSE", "Unit tidak ditemukan");
	
	private String code;
	private String value;

	private RetirementType(String code, String value) {
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
