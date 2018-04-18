package co.id.fifgroup.systemworkflow.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;
import co.id.fifgroup.systemworkflow.service.WorkflowUpdateTransactionService;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.manager.AVMTransactionManager;
import co.id.fifgroup.avm.util.Serialization;

@Service
public class WorkflowUpdateTransactionServiceImpl implements WorkflowUpdateTransactionService {

	@Autowired
	private AvmAdapterService avmAdapterServiceImpl;
	@Autowired
	private AVMTransactionManager avmTransactionManager;
	@Autowired
	private TemplateMessageService templateMessageServiceImpl;
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void doUpdateTransaction(Object transaction, UUID avmTrxId) throws Exception {
		templateMessageServiceImpl.deletePreviousNotification(avmTrxId);
		avmAdapterServiceImpl.updateApplicationData(avmTrxId, Serialization.serializeObject(transaction));
		AVMTransaction trx = avmTransactionManager.getAVMTransactionById(avmTrxId);
		avmAdapterServiceImpl.doSendMessage(trx.getAvmId(), avmTrxId, AVMReturnCode.TRANSACTION_IS_IN_PROGRESS, avmAdapterServiceImpl.getTransactionSubmitter(avmTrxId), transaction, TrxType.fromCode((long) trx.getTrxType()), trx.getCompanyId(), trx.getAvmTimeStamp());
	}

	@Override
	public String getUrl(Long trxId) {
		if (trxId.equals(TrxType.OCOP_REQUEST
				.getCode())) {
			return "~./hcms/ocop/swf_update_ocop_transaction.zul";
		} else if (trxId.equals(TrxType.DIL_SP_CANCELLATION
				.getCode())) {
			return "~./hcms/disciplinary-letter/swf_update_cancellation_sp.zul";
		} else if (trxId.equals(TrxType.DIL_SP_EVALUATION
				.getCode())) {
			return "~./hcms/disciplinary-letter/swf_update_evaluation_sp.zul";
		}
		return null;
	}
	
}
