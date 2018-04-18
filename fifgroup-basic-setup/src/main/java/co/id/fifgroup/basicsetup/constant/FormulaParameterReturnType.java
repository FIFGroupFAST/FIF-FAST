package co.id.fifgroup.basicsetup.constant;

public enum FormulaParameterReturnType {

	SELECT("-Select-"),
	BOOLEAN("Boolean"),
	DOUBLE("Double"),
	LONG("Long"),
	STRING("String"),
	OBJECT("Object");
	
	
	String desc;

	public String getDesc() {
		return desc;
	}

	private FormulaParameterReturnType(String desc){
		this.desc = desc;
	}
	
}
