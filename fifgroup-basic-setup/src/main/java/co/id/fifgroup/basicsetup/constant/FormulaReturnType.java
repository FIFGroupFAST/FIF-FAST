package co.id.fifgroup.basicsetup.constant;

public enum FormulaReturnType {
	
	SELECT("-Select-"),
	LONG("Long"),
	DOUBLE("Double"),
	STRING("String"),
	BOOLEAN("Boolean"),
	OBJECT("Object");
	
	private String desc;

	public String getDesc() {
		return desc;
	}

	private FormulaReturnType(String desc){
		this.desc = desc;
	}
	
	
}
