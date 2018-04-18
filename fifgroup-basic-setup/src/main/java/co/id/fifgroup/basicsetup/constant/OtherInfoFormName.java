package co.id.fifgroup.basicsetup.constant;


public enum OtherInfoFormName {

	SELECT("SELECT", null, "-Select-", null),
	COMPANY("COMPANY", "BSE_COY_OTHER_INFO", "Company Other Info", "BSE_COY_OTHER_INFO_S"),
	JOB("JOB",null,"Job Other Info",null);
	
	private String code;
	private String tableName;
	private String desc;
	private String sequenceName;
	
	private OtherInfoFormName(String code, String tableName, String desc, String sequenceName) {
		this.code = code;
		this.tableName = tableName;
		this.desc = desc;
		this.sequenceName = sequenceName;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
