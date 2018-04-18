package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.workstructure.dto.GradeStageDTO;
import co.id.fifgroup.workstructure.dto.GradeUploadsDTO;

public interface GradeUploadFinder {

	public List<GradeUploadsDTO> getGradeUploads(GradeUploadsDTO example);
	public List<GradeStageDTO> getGradeStageByBatchNumber(@Param("batchNumber") String batchNumber);
	public List<UploadError> getGradeErrorByUploadId(@Param("uploadId") Long uploadId);
	public Integer getGradeStageInOneBatchNumberByUploadId(@Param("gradeStage") GradeStageDTO gradeStage);
}