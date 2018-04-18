package co.id.fifgroup.systemadmin.service;

import java.util.List;

import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;

import co.id.fifgroup.systemadmin.domain.JobResponsibility;
import co.id.fifgroup.systemadmin.domain.JobResponsibilityExample;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDTO;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDetailDTO;

public interface JobResponsibilityService {
	
	public void save(JobResponsibilityDTO jobResponsibilityDTO) throws Exception;
	
	public void deleteEdit(JobResponsibilityDTO jobResponsibilityDTO);
	
	public List<JobResponsibility> getResponsibilityByExample(JobResponsibilityExample example);
	
	public int countJobResponsibilityByExample(JobResponsibilityExample example);
	
	public int countByExample(JobResponsibilityExample example);
	
	public List<JobResponsibilityDTO> getJobResponsibilityByJobIdAndResponsibilityId(JobResponsibility jobResponsibility, int limit, int offset);
	
	public List<JobResponsibilityDTO> getJobResponsibilityByJobIdAndResponsibilityId(JobResponsibility jobResponsibility);
	
	public int countJobResponsibilityByJobIdAndResponsibilityId(JobResponsibility jobResponsibility);
	
	public List<JobResponsibilityDetailDTO> getJobResponsibilityByJobId(JobResponsibility jobResponsibility);
	
	public List<JobResponsibilityDTO> selectJobResponsibilityByResponsibility(Responsibility responsibility);
	
	public List<JobResponsibilityDetailDTO> getActiveJobResponsibilityByJobId(Long jobId);

	public List<Job> selectByExample(JobExample jobExample);
	
}
