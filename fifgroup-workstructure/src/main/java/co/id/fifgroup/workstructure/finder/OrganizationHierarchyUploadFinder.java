package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.workstructure.dto.OrganizationHierarchyStageDTO;
import co.id.fifgroup.workstructure.dto.OrganizationHierarchyUploadsDTO;

public interface OrganizationHierarchyUploadFinder {

	public List<OrganizationHierarchyUploadsDTO> getOrganizationHierarchyUploads(OrganizationHierarchyUploadsDTO example);
	public List<OrganizationHierarchyStageDTO> getOrganizationHierarchyStageByUploadId(@Param("uploadId") Long uploadId);
	public List<OrganizationHierarchyStageDTO> getOrganizationHierarchyStageByBatchNumber(@Param("batchNumber") String batchNumber);
	public List<OrganizationHierarchyStageDTO> getOrganizationHierarchyStageByExample(OrganizationHierarchyStageDTO example);
	public List<UploadError> getOrganizationHierarchyErrorByUploadId(@Param("uploadId") Long uploadId);
	public Integer getHierarchyStageInOneBatchNumberByUploadId(@Param("organizationHierarchyStage") OrganizationHierarchyStageDTO orgStage);
	List<String> findAllOrgHierName();
}
