package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.workstructure.dto.OrganizationStageDTO;
import co.id.fifgroup.workstructure.dto.OrganizationUploadsDTO;

public interface OrganizationUploadFinder {

	public List<OrganizationUploadsDTO> getOrganizationUploads(OrganizationUploadsDTO example);
	public List<OrganizationStageDTO> getOrganizationStageByUploadId(@Param("uploadId") Long uploadId);
	public List<UploadError> getOrganizationErrorByUploadId(@Param("uploadId") Long uploadId);
	public Integer getOrgStageInOneBatchNumberByUploadId(@Param("organizationStage") OrganizationStageDTO orgStage);
}
