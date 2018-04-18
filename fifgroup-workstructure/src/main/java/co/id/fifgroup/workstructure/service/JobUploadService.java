package co.id.fifgroup.workstructure.service;

import java.util.List;

import co.id.fifgroup.workstructure.domain.JobUploadStage;
import co.id.fifgroup.workstructure.domain.JobUploadStageExample;

import co.id.fifgroup.core.service.UploadDownloadService;
import co.id.fifgroup.workstructure.dto.JobStageDTO;
import co.id.fifgroup.workstructure.dto.JobUploadsDTO;

public interface JobUploadService extends UploadDownloadService<JobStageDTO> {
	public List<JobUploadStage> selectByExample(JobUploadStageExample jobStageExample);
	public List<JobUploadsDTO>  getJobUploads(JobUploadsDTO example);
}
