package co.id.fifgroup.systemworkflow.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.WorkflowResolverService;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.avm.util.Serialization;


@Transactional
public class AVMApprovalManagerHcms extends AVMApprovalManager {
	
	private static final Logger logger = LoggerFactory.getLogger(AVMApprovalManagerHcms.class);

	@Autowired
	private WorkflowResolverService workflowResolverServiceImpl;
	@Autowired
	private AvmAdapterService avmAdapterServiceImpl;
	
	@Override
	protected boolean isApproverCountInsufficient(List<AVMApprover> approverList,
			AVMTransaction avmTransaction, int priority) throws FifException {

		if (approverList != null && approverList.size() > 0) {
			Object serializedData = Serialization.deserializedObject(avmTransaction
					.getSerializedData());
			AVMVersions currentVersion = getAvmSetupManager().getCurrentActiveVersion(avmTransaction.getAvmId(), DateUtil.truncate(avmTransaction.getAvmTimeStamp()));
			AVMLevel level = getApplicableLevelSequence(currentVersion, approverList.get(0).getLevelSequence(), serializedData);
			if (level != null && level.getNumberOfApprovals() > approverList.size()) {
				try {
					avmAdapterServiceImpl.sendNotificationErrorToAdmin(AVMReturnCode.INSUFFICIENT_APPROVERS, avmTransaction.getSubject());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean isApproverMissing(List<AVMApprover> approverList,
			AVMTransaction avmTransaction, int priority) throws FifException {
		List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
		UUID submitterId = getTransactionSubmitter(avmTransaction.getAvmTrxId());
		historyList = workflowResolverServiceImpl.getResolvingApproverList(approverList, avmTransaction, submitterId);
		if (historyList.size() == 0) {
			try {
				avmAdapterServiceImpl.sendNotificationErrorToAdmin(AVMReturnCode.MISSING_APPROVERS, avmTransaction.getSubject());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return true;
		}
		return false;
	}

	@Override
	protected List<AVMApprovalHistory> resolveAVMApproverList(
			List<AVMApprover> rawApproverList, AVMTransaction avmTransaction)
			throws FifException {
		List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
		UUID submitterId = getTransactionSubmitter(avmTransaction.getAvmTrxId());
		historyList = workflowResolverServiceImpl.getResolvingApproverList(rawApproverList, avmTransaction, submitterId);
		return historyList;
	}

}
