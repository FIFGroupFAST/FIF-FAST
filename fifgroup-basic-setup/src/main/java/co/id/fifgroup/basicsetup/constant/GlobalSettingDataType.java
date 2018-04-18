package co.id.fifgroup.basicsetup.constant;


/**
 * @author Achmad Ridwan
 * @version 1.0
 * @created 14-Feb-2013 3:23:03 PM
 */
public enum GlobalSettingDataType {
	SELECT("-Select-"),
	NUMERIC("NUMERIC"),
	TEXT("TEXT");
	
	private String desc;
	
	private GlobalSettingDataType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}