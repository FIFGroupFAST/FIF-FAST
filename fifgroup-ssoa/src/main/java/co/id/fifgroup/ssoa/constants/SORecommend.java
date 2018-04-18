package co.id.fifgroup.ssoa.constants;


public enum SORecommend {
	CODE("STOCK_OPNAME_RECOMMEND", "Stock Opname Recommend"),
	RETIRE_SELL("SO_RECOMMEND_RETIRE_SELL", "Retirement - Penjualan"), 
	RETIRE_INSURANCE("SO_RECOMMEND_RETIRE_INSURANCE", "Retirement - Asuransi"), 
	RETIRE_GRANT("SO_RECOMMEND_RETIRE_GRANT","Retirement - Hibah"), 
	RETIRE_LOSE("SO_RECOMMEND_RETIRE_LOSE", "Retirement - Tidak Ditemukan"), 
	TRANSFER("SO_RECOMMEND_TRANSFER", "Mutasi");

	private String code;
	private String value;

	private SORecommend(String code, String value) {
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
