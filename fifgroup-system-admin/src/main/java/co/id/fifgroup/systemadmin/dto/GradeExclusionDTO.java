package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.systemadmin.domain.GradeExclusion;

public class GradeExclusionDTO extends GradeExclusion {

	private static final long serialVersionUID = 1L;
	private String gradeName;
	
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
}
