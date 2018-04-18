package co.id.fifgroup.workstructure.service;

import java.util.List;

import co.id.fifgroup.workstructure.domain.LobUploadStage;
import co.id.fifgroup.workstructure.domain.LobUploadStageExample;

import co.id.fifgroup.core.service.UploadDownloadService;
import co.id.fifgroup.workstructure.dto.LobStageDTO;
import co.id.fifgroup.workstructure.dto.LobUploadsDTO;

public interface LobUploadService extends UploadDownloadService<LobStageDTO> {
	public List<LobUploadStage> selectByExample(LobUploadStageExample lobStageExample);
	public List<LobUploadsDTO>  getLobUploads(LobUploadsDTO example);
	public Integer countByExample(LobUploadStageExample example);
}
