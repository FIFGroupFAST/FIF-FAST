package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.personneladmin.domain.SecondaryAssignment;

public class SecondaryAssignmentDTO extends SecondaryAssignment implements Traversable{

	private static final long serialVersionUID = 1L;

	private String organizationName;
	private String jobName;
	private Long branchId;
	private String branchName;
	private Long locationId;
	private String locationName;

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Override
	public Object getId() {
		return this.getAssignmentId();
	}

}
