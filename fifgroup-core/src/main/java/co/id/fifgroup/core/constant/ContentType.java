package co.id.fifgroup.core.constant;

public enum ContentType {
	APPLICATIONFILE("application/file"),
	APPLICATIONPDF("application/pdf"),
	IMAGEJPG("image/jpg");

	private String value;

	private ContentType(String value) {

		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
