package co.id.fifgroup.systemworkflow.service;

import java.util.UUID;

public interface WorkflowUpdateTransactionService {

	public void doUpdateTransaction(Object transaction, UUID avmTrxId) throws Exception;
	
	public String getUrl(Long trxId);
}
