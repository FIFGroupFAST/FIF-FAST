package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.systemadmin.domain.User;

public class UserDTO extends User {
	
	private static final long serialVersionUID = 1L;
	private List<UserResponsibilityDTO> directResponsibilityDto;
	private List<UserResponsibilityDTO> jobResponsibilityDto;
	private String employeeName;
	private String name;
	private Long jobId;
	
	public List<UserResponsibilityDTO> getDirectResponsibilityDto() {
		return directResponsibilityDto;
	}
	public void setDirectResponsibilityDto(List<UserResponsibilityDTO> directResponsibilityDto) {
		this.directResponsibilityDto = directResponsibilityDto;
	}
	public List<UserResponsibilityDTO> getJobResponsibilityDto() {
		return jobResponsibilityDto;
	}
	public void setJobResponsibilityDto(List<UserResponsibilityDTO> jobResponsibilityDto) {
		this.jobResponsibilityDto = jobResponsibilityDto;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
}
