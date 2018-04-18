package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.JobResponsibility;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDTO;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDetailDTO;

public interface JobResponsibilityFinder {
	
	List<JobResponsibilityDTO> getJobResponsibilityByJobIdAndResponsibilityId(JobResponsibility jobResponsibility, RowBounds rowBounds);
	
	List<JobResponsibilityDTO> getJobResponsibilityByJobIdAndResponsibilityId(JobResponsibility jobResponsibility);
	
	int countJobResponsibilityByJobIdAndResponsibilityId(JobResponsibility jobResponsibility);
	
	List<JobResponsibilityDetailDTO> getJobResponsibilityByJobId(JobResponsibility jobResponsibility);
	
	void deleteByJobId(@Param("jobId") Long id);
	
	List<JobResponsibilityDTO> selectJobResponsibilityByResponsibility(Responsibility responsibility);
	
	List<JobResponsibilityDetailDTO> selectActiveJobResponsibilityByJobId(Long id);
	
}
