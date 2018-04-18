package co.id.fifgroup.workstructure.service;

import java.util.List;

import co.id.fifgroup.workstructure.domain.LocationUploadStage;
import co.id.fifgroup.workstructure.domain.LocationUploadStageExample;

import co.id.fifgroup.core.service.UploadDownloadService;
import co.id.fifgroup.workstructure.dto.LocationStageDTO;
import co.id.fifgroup.workstructure.dto.LocationUploadsDTO;

public interface LocationUploadService extends UploadDownloadService<LocationStageDTO> {
	
	public List<LocationUploadStage> selectByExample(LocationUploadStageExample locationStageExample);
	public List<LocationUploadsDTO>  getLocationUploads(LocationUploadsDTO example);
}
