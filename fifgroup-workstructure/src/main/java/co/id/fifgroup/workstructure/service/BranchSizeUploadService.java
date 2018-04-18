package co.id.fifgroup.workstructure.service;

import java.util.List;

import co.id.fifgroup.workstructure.domain.BranchSizeUploadStage;
import co.id.fifgroup.workstructure.domain.BranchSizeUploadStageExample;

import co.id.fifgroup.core.service.UploadDownloadService;
import co.id.fifgroup.workstructure.dto.BranchSizeStageDTO;
import co.id.fifgroup.workstructure.dto.BranchSizeUploadsDTO;

public interface BranchSizeUploadService extends UploadDownloadService<BranchSizeStageDTO> {

	public List<BranchSizeUploadStage> selectByExample(BranchSizeUploadStageExample branchSizeStageExample);
	public Integer countByExample(BranchSizeUploadStageExample branchSizeStageExample);
	public List<BranchSizeUploadsDTO>  getBranchSizeUploads(BranchSizeUploadsDTO example);
}
