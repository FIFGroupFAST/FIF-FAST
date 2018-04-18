package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.workstructure.dto.LobStageDTO;
import co.id.fifgroup.workstructure.dto.LobUploadsDTO;

public interface LobUploadFinder {

	public List<LobUploadsDTO> getLobUploads(LobUploadsDTO example);
	public List<LobStageDTO> findStageByUploadId(@Param("id") Long uploadId);
	public List<LobStageDTO> findStageByBatchNumber(@Param("batchNumber") String batchNumber);
	public List<UploadError> getLobErrorByUploadId(@Param("uploadId") Long uploadId);
	public Integer countSuccessRowByUploadId(@Param("uploadId") Long uploadId);
}
