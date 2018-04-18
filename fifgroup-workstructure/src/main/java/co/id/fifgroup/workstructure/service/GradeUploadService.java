package co.id.fifgroup.workstructure.service;

import java.util.List;

import co.id.fifgroup.workstructure.domain.GradeUploadStage;
import co.id.fifgroup.workstructure.domain.GradeUploadStageExample;

import co.id.fifgroup.core.service.UploadDownloadService;
import co.id.fifgroup.workstructure.dto.GradeStageDTO;
import co.id.fifgroup.workstructure.dto.GradeUploadsDTO;

public interface GradeUploadService extends UploadDownloadService<GradeStageDTO> {
	public List<GradeUploadStage> selectByExample(GradeUploadStageExample gradeStageExample);
	public Integer countByExample(GradeUploadStageExample gradeStageExample);
	public List<GradeUploadsDTO>  getGradeUploads(GradeUploadsDTO gradeUploadsDTO);
}
