package co.id.fifgroup.personneladmin.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.personneladmin.dto.AssignmentStageDTO;
import co.id.fifgroup.personneladmin.dto.AssignmentUploadsDTO;

import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.UploadDownloadService;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.personneladmin.domain.AssignmentStage;
import co.id.fifgroup.personneladmin.domain.AssignmentStageExample;


public interface AssignmentUploadService extends UploadDownloadService<AssignmentStageDTO> {
	
	public List<AssignmentStage> selectByExample(AssignmentStageExample assignmentStageExample);
	
	public List<AssignmentUploadsDTO> getAssignmentUploads(Long companyId, String batchNumberFrom, String batchNumberTo, Date dateFrom, Date dateTo);
	
	public List<AssignmentUploadsDTO> getAssignmentUploads(Long companyId, Integer batchNumberFrom, Integer batchNumberTo, Date dateFrom, Date dateTo);
	
	public void closeBatchUpload(BatchUpload batchUpload) throws Exception;
	
	public void closeBatchBackgroudProcess(BatchUpload batchUpload, SecurityProfile securityProfile, ExecutableTask executableTask, boolean isGlobalSuperHcAdministrator) throws Exception;
}
