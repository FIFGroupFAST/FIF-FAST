package co.id.fifgroup.systemworkflow.service;

import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.exception.FifException;

public interface WorkflowResolverService {

	public List<AVMApprovalHistory> getResolvingApproverList(List<AVMApprover> rawApproverList, AVMTransaction avmTransaction, UUID submitterId) throws FifException;
	
	public void doAfterApprovalProcess(AVMReturnCode returnCode, Long transactionId, AVMTransaction avmTransaction, Object application) throws FifException, Exception;
	
	public void doApproveTransaction(Long transactionId, Object application, UUID avmTrxId, UUID approverId) throws Exception;
	
	public void doValidateBeforeApprove(Long transactionId, byte[] serializedData) throws ValidationException;
	
	public void doValidateBeforeReject(Long transactionId, byte[] serializedData) throws ValidationException;
}
