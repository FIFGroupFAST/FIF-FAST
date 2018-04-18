package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.JobResponsibility;

public class JobResponsibilityDetailDTO extends JobResponsibility implements Traversable {

	private static final long serialVersionUID = 1L;
	
	private String responsibilityName;

	public String getResponsibilityName() {
		return responsibilityName;
	}

	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}

	@Override
	public Object getId() {
		return super.getJobId();
	}
}
