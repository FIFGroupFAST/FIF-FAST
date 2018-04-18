package co.id.fifgroup.workstructure.constant;

public enum JobFor {

	SELECT("-Select-"),
	NPK("NPK"), 
	CWK("CWK"),
	NPKCWK("NPK and CWK"),
	INTERNSHIP("Internship");
	
	private String desc;
	
	private JobFor(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
	
}
