package co.id.fifgroup.personneladmin.constant;

import co.id.fifgroup.core.constant.DescriptiveEnum;

public enum AssignmentStatus implements DescriptiveEnum{
	
	SELECT("- Select -"),
	ACTING("Acting"),
	DEFINITIVE("Definitive"),
	TASK_FORCE("Task Force")
//	MIGRATION("Migration")
	;
	
	private String description;
	
	private AssignmentStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static AssignmentStatus getType(String stringType) {
		for(AssignmentStatus as : AssignmentStatus.values()) {
			if(as.toString().equalsIgnoreCase(stringType)) {
				return as;
			}
		}
		return null;
	}
	
}
