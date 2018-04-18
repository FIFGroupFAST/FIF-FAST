package co.id.fifgroup.workstructure.constant;

public enum HierarchyType {

	SELECT("-Select-"),
	STRUCTURAL("STRUCTURAL"), 
	FUNCTIONAL("FUNCTIONAL");
	
	private String desc;
	
	private HierarchyType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
}
