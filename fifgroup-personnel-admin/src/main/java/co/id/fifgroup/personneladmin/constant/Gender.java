package co.id.fifgroup.personneladmin.constant;

import org.zkoss.util.resource.Labels;

public enum Gender {

	SELECT(Labels.getLabel("common.pleaseSelect")),
	FEMALE(Labels.getLabel("pea.female")),
	MALE(Labels.getLabel("pea.male"));
	
	private String description;
	
	private Gender(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String getGenderDescription(String genderCode) {
		for (Gender gender : Gender.values()) {
			if (gender.name().equalsIgnoreCase(genderCode)) {
				return gender.getDescription();
			}
		}
		return null;
	}
}
