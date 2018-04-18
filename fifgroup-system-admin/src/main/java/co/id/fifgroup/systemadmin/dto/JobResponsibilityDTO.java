package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.JobResponsibility;

public class JobResponsibilityDTO extends JobResponsibility implements Traversable {

	private static final long serialVersionUID = 1L;
	private String jobName;
	private String companyName;
	private String jobResponsibilityName;
	private String userName;
	private List<JobResponsibilityDetailDTO> jobResponsibilityDetailDTO;
	
	
	public String getJobName() {
		return jobName;
	}
	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getJobResponsibilityName() {
		return jobResponsibilityName;
	}
	
	public void setJobResponsibilityName(String jobResponsibilityName) {
		this.jobResponsibilityName = jobResponsibilityName;
	}
	
	public List<JobResponsibilityDetailDTO> getJobResponsibilityDetailDTO() {
		return jobResponsibilityDetailDTO;
	}
	
	public void setJobResponsibilityDetailDTO(
			List<JobResponsibilityDetailDTO> jobResponsibilityDetailDTO) {
		this.jobResponsibilityDetailDTO = jobResponsibilityDetailDTO;
	}

	@Override
	public Object getId() {
		return super.getJobId();
	}
}
