package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.CriteriaQuestionSetDTO;
import co.id.fifgroup.personneladmin.dto.JobQuestionSetDTO;

public interface JobQuestionSetFinder {
	
	public List<JobQuestionSetDTO> getJobQuestionSetByCriteria(CriteriaQuestionSetDTO criteriaQuestionSetDTO);
	
	public Long countJobQuestionSetByJobId(@Param("jobId") Long jobId, @Param("jobQuestionSetId") Long jobQuestionSetId,@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
}
