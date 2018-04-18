package co.id.fifgroup.workstructure.service;

import java.util.List;

import co.id.fifgroup.workstructure.domain.OrganizationUploadStage;
import co.id.fifgroup.workstructure.domain.OrganizationUploadStageExample;

import co.id.fifgroup.core.service.UploadDownloadService;
import co.id.fifgroup.workstructure.dto.OrganizationStageDTO;
import co.id.fifgroup.workstructure.dto.OrganizationUploadsDTO;

public interface OrganizationUploadService extends UploadDownloadService<OrganizationStageDTO> {

	public List<OrganizationUploadStage> selectByExample(OrganizationUploadStageExample organizationStageExample);
	public List<OrganizationUploadsDTO>  getOrganizationUploads(OrganizationUploadsDTO example);
}
