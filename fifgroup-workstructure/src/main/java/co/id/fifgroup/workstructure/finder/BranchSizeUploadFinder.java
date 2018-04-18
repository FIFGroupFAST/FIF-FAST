package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.workstructure.dto.BranchSizeStageDTO;
import co.id.fifgroup.workstructure.dto.BranchSizeUploadsDTO;

public interface BranchSizeUploadFinder {

	public List<BranchSizeUploadsDTO> getBranchSizeUploads(BranchSizeUploadsDTO example);
	public List<BranchSizeStageDTO> getBranchSizeStage(@Param("uploadId") Long uploadId);
	public List<UploadError> getBranchSizeErrorByUploadId(@Param("uploadId") Long uploadId);
}
