package co.id.fifgroup.basicsetup.constant;

public enum SequenceGeneratorType {

	PREFIX("SEQUENCE_"),
	MAXVALUE_CHAR("9");
	
	private String desc;
	
	private SequenceGeneratorType(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
}
