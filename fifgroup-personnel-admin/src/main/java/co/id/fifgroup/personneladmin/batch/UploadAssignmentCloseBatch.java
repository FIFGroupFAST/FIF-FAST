package co.id.fifgroup.personneladmin.batch;

import java.util.Date;

import co.id.fifgroup.core.util.Stream;
import co.id.fifgroup.core.validation.TaskExecutionException;
import co.id.fifgroup.personneladmin.service.AssignmentUploadService;

import co.id.fifgroup.core.constant.BatchStatus;
import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.personneladmin.finder.AssignmentUploadsFinder;

public class UploadAssignmentCloseBatch extends ExecutableTask{

	@Override
	public void execute() throws TaskExecutionException {
		
		BatchUpload batchUpload = (BatchUpload) getParameter().get(BatchUpload.class.getName());
		SecurityProfile securityProfile = (SecurityProfile)getParameter().get(SecurityProfile.class.getName());
		boolean isGlobalSuperHcAdminstrator = (boolean) getParameter().get("isGlobalSuperHcAdminstrator");
		
		BatchUpload newBatchUpload = new BatchUpload();
		newBatchUpload.setCreatedBy(batchUpload.getCreatedBy());
		newBatchUpload.setCreationDate(batchUpload.getCreationDate());
		newBatchUpload.setCompanyId(batchUpload.getCompanyId());
		newBatchUpload.setBatchNumber(batchUpload.getBatchNumber());
		newBatchUpload.setStatus(BatchStatus.CLOSING);
		newBatchUpload.setLastUpdateDate(new Date());
		newBatchUpload.setLastUpdatedBy(securityProfile.getUserId());
		getAssignmentUploadsFinder().updateBatchUpload(newBatchUpload);
		
		try {
			Stream stream = new Stream();
			stream.start();
			getAssignmentUploadService().closeBatchBackgroudProcess(newBatchUpload, securityProfile, this, isGlobalSuperHcAdminstrator);
			
			newBatchUpload.setStatus(BatchStatus.CLOSED);
			newBatchUpload.setLastUpdateDate(new Date());
			newBatchUpload.setLastUpdatedBy(securityProfile.getUserId());
			getAssignmentUploadsFinder().updateBatchUpload(newBatchUpload);
			debug(stream.stop("Close Batch Process"));
		} catch (Exception e) {
			
			newBatchUpload.setStatus(BatchStatus.OPEN);
			newBatchUpload.setLastUpdateDate(new Date());
			newBatchUpload.setLastUpdatedBy(securityProfile.getUserId());
			getAssignmentUploadsFinder().updateBatchUpload(newBatchUpload);
			
			throw new TaskExecutionException(e.getMessage(), e);
		}
	}
	
	private AssignmentUploadService getAssignmentUploadService(){
		return (AssignmentUploadService) getApplicationContext().getBean("assignmentUploadServiceImpl");
	}
	
	private AssignmentUploadsFinder getAssignmentUploadsFinder(){
		return (AssignmentUploadsFinder) getApplicationContext().getBean("assignmentUploadsFinder");
	}

}
