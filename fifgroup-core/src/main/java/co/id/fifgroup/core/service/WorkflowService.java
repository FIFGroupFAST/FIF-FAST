package co.id.fifgroup.core.service;

import java.util.UUID;

import co.id.fifgroup.core.validation.ValidationException;

public interface WorkflowService {

	public void doValidateBeforeApprove(Object transaction) throws ValidationException;

	public void doValidateBeforeReject(Object transaction) throws ValidationException;
	
	public void doApprove(Object transaction, UUID avmTrxId, UUID approverId) throws Exception;
	
	public void doAfterApproved (Object transaction) throws Exception;
	
	public void doAfterRejected (Object transaction) throws Exception;
	
	public void doAfterCanceled (Object transaction) throws Exception;
	
}
