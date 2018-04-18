package co.id.fifgroup.systemadmin.constant;

public enum OutputFileFormat {
	Select("-Select-"),
	PDF("PDF"),
	EXCEL("EXCEL");;
	
	private String value;
	
	private OutputFileFormat(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
