package co.id.fifgroup.personneladmin.constant;

import org.zkoss.util.resource.Labels;

public enum Mobility {
	
	SELECT("SELECT", Labels.getLabel("common.pleaseSelect")),
	UNLIMITED("UNLIMITED", Labels.getLabel("cm.unlimited")),
	NOT_MOBILE("NOT_MOBILE", Labels.getLabel("cm.notMobile")),
	LIMITED("LIMITED", Labels.getLabel("cm.limited"));

	private String code;
	private String description;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Mobility(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public static Mobility fromCode(String code) {
		for (Mobility mobility : Mobility.values()) {
			if (mobility.getCode() == code) {
				return mobility;
			}
		}
		return null;
	}

	public static String getMobilityDescription(String mobilityCode){
		for (Mobility mobility : Mobility.values()){
			if (mobility.name().equalsIgnoreCase(mobilityCode)){
				return mobility.getDescription();
			}
		}
		return null;
	}
}
