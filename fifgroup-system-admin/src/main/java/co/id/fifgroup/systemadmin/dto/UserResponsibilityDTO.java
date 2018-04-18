package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.domain.UserResponsibility;

public class UserResponsibilityDTO extends UserResponsibility {
	
	private static final long serialVersionUID = 1L;
	private List<Responsibility> responsibility;
	private List<JobResponsibilityDTO> jobResponsibility;
	private String directResponsibilityName;
	private String jobResponsibilityName;
	
	public List<Responsibility> getResponsibility() {
		return responsibility;
	}
	
	public void setResponsibility(List<Responsibility> responsibility) {
		this.responsibility = responsibility;
	}
	
	public List<JobResponsibilityDTO> getJobResponsibility() {
		return jobResponsibility;
	}
	
	public void setJobResponsibility(List<JobResponsibilityDTO> jobResponsibility) {
		this.jobResponsibility = jobResponsibility;
	}
	
	public String getDirectResponsibilityName() {
		return directResponsibilityName;
	}
	
	public void setDirectResponsibilityName(String directResponsibilityName) {
		this.directResponsibilityName = directResponsibilityName;
	}
	
	public String getJobResponsibilityName() {
		return jobResponsibilityName;
	}
	
	public void setJobResponsibilityName(String jobResponsibilityName) {
		this.jobResponsibilityName = jobResponsibilityName;
	}
}
