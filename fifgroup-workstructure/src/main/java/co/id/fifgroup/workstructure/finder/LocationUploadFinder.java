package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.workstructure.dto.LocationStageDTO;
import co.id.fifgroup.workstructure.dto.LocationUploadsDTO;

public interface LocationUploadFinder {
	
	public List<LocationUploadsDTO> getLocationUploads(LocationUploadsDTO example);
	public List<LocationStageDTO> getLocationStageByUploadId(@Param("uploadId") Long uploadId);
	public List<UploadError> getLocationErrorByUploadId(@Param("uploadId") Long uploadId);
	public Integer getLocationStageInOneBatchNumberByUploadId(@Param("locationStage") LocationStageDTO locationStage);
}
