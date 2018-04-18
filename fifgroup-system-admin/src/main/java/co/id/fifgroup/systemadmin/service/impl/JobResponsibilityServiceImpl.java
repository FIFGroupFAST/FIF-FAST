package co.id.fifgroup.systemadmin.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.JobExample;

import co.id.fifgroup.systemadmin.dao.JobResponsibilityMapper;
import co.id.fifgroup.systemadmin.domain.JobResponsibility;
import co.id.fifgroup.systemadmin.domain.JobResponsibilityExample;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDTO;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDetailDTO;
import co.id.fifgroup.systemadmin.finder.JobResponsibilityFinder;
import co.id.fifgroup.systemadmin.service.JobResponsibilityService;
import co.id.fifgroup.systemadmin.validation.JobResponsibilityValidator;
import co.id.fifgroup.workstructure.dao.JobMapper;

@Transactional
@Service("jobResponsibilityService")
public class JobResponsibilityServiceImpl implements JobResponsibilityService {
	
	//Job Responsibility Entity
	@Autowired
	private JobResponsibilityMapper jobResponsibilityMapper;
	@Autowired
	private JobResponsibilityFinder jobResponsibilityFinder;
	
	//Other Module Entity
	@Autowired
	private JobMapper jobMapper;

	//Validation
	@Autowired
	private JobResponsibilityValidator jobResponsibilityValidator;
	
	
	@Override
	@Transactional
	public void save(JobResponsibilityDTO jobResponsibilityDTO) throws Exception{
		jobResponsibilityValidator.validate(jobResponsibilityDTO);
		for(JobResponsibilityDetailDTO jobResponsibilityDetailDTO : jobResponsibilityDTO.getJobResponsibilityDetailDTO()) {
			jobResponsibilityDetailDTO.setJobId(jobResponsibilityDTO.getJobId());
			jobResponsibilityDetailDTO.setResponsibilityId(jobResponsibilityDetailDTO.getResponsibilityId());
			jobResponsibilityDetailDTO.setCreatedBy(jobResponsibilityDTO.getCreatedBy());
			jobResponsibilityDetailDTO.setCreationDate(jobResponsibilityDTO.getCreationDate());
			jobResponsibilityDetailDTO.setLastUpdatedBy(jobResponsibilityDTO.getLastUpdatedBy());
			jobResponsibilityDetailDTO.setLastUpdateDate(jobResponsibilityDTO.getLastUpdateDate());
			
			if(jobResponsibilityDetailDTO.getDateTo() == null) {
				jobResponsibilityDetailDTO.setDateTo(DateUtil.MAX_DATE);
			}
			jobResponsibilityMapper.insert(jobResponsibilityDetailDTO);
		}
	}

	@Override
	@Transactional
	public void deleteEdit(JobResponsibilityDTO jobResponsibilityDTO) {
		jobResponsibilityFinder.deleteByJobId(jobResponsibilityDTO.getJobId());
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<JobResponsibility> getResponsibilityByExample(JobResponsibilityExample example) {
		return jobResponsibilityMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int countJobResponsibilityByExample(JobResponsibilityExample example) {
		return jobResponsibilityMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<JobResponsibilityDTO> getJobResponsibilityByJobIdAndResponsibilityId(JobResponsibility jobResponsibility, int limit, int offset) {
		return jobResponsibilityFinder.getJobResponsibilityByJobIdAndResponsibilityId(jobResponsibility, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public List<JobResponsibilityDTO> getJobResponsibilityByJobIdAndResponsibilityId(JobResponsibility jobResponsibility) {
		return jobResponsibilityFinder.getJobResponsibilityByJobIdAndResponsibilityId(jobResponsibility);
	}
	
	@Override
	@Transactional(readOnly=true)
	public int countJobResponsibilityByJobIdAndResponsibilityId(JobResponsibility jobResponsibility) {
		return jobResponsibilityFinder.countJobResponsibilityByJobIdAndResponsibilityId(jobResponsibility);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<JobResponsibilityDetailDTO> getJobResponsibilityByJobId(JobResponsibility jobResponsibility) {
		return jobResponsibilityFinder.getJobResponsibilityByJobId(jobResponsibility);
	}

	@Override
	@Transactional(readOnly=true)
	public List<JobResponsibilityDTO> selectJobResponsibilityByResponsibility(Responsibility responsibility) {
		return jobResponsibilityFinder.selectJobResponsibilityByResponsibility(responsibility);
	}

	@Override
	@Transactional(readOnly=true)
	public List<JobResponsibilityDetailDTO> getActiveJobResponsibilityByJobId(Long jobId) {
		return jobResponsibilityFinder.selectActiveJobResponsibilityByJobId(jobId);
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(JobResponsibilityExample example) {
		return jobResponsibilityMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Job> selectByExample(JobExample jobExample) {
		return jobMapper.selectByExample(jobExample);
	}
}
