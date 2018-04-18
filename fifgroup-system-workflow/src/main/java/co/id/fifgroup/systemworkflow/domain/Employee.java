package co.id.fifgroup.systemworkflow.domain;

import java.io.Serializable;
import java.util.UUID;

public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	private String npk;
	private String employeeName;
	private UUID employeeUUID;
	private Long organizationId;
	private Long jobId;
	private Long branchId;

	public Employee(String npk, String name, UUID empUuid) {
		this.npk = npk;
		this.employeeName = name;
		this.employeeUUID = empUuid;
	}

	public Employee() {

	}

	public String getNpk() {
		return npk;
	}

	public void setNpk(String npk) {
		this.npk = npk;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public UUID getEmployeeUUID() {
		return employeeUUID;
	}

	public void setEmployeeUUID(UUID employeeUUID) {
		this.employeeUUID = employeeUUID;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

}
