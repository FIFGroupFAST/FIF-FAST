package co.id.fifgroup.personneladmin.constant;

public enum ResultProbation {
	PASS("Pass The Probation"),
	FAIL("Fail The Probation");

	private String value;
	
	private ResultProbation(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
