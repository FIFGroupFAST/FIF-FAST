package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.workstructure.dto.JobStageDTO;
import co.id.fifgroup.workstructure.dto.JobUploadsDTO;

public interface JobUploadFinder {
	
	public List<JobUploadsDTO> getJobUploads(JobUploadsDTO example);
	public List<JobStageDTO> getJobStageByUploadId(@Param("uploadId") Long uploadId);
	public List<UploadError> getJobErrorByUploadId(@Param("uploadId") Long uploadId);
	public Integer getJobStageInOneBatchNumberByUploadId(@Param("jobStage") JobStageDTO jobStage);
}
