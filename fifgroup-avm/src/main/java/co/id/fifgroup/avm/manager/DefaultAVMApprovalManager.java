/**
 * Copyright (c) 2012 FIF. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of FIF.
 * ("Confidential Information"). <BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall only be
 * used in accordance with the terms of the license agreement entered into with
 * FIF; other than in accordance with the written permission of FIF. <BR>
 * 
 * Created on: Jun 22, 2010
 *
 * Author           Date         Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * 
 *  
 */

package co.id.fifgroup.avm.manager;

import java.util.ArrayList;
import java.util.List;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class DefaultAVMApprovalManager extends AVMApprovalManager{

	protected boolean isApproverCountInsufficient(
			List<AVMApprover> approverList, AVMTransaction avmTransaction,
			int priority) throws FifException {
		List<AVMApprover> approverListInCurrentLevel = getAvmSetupManager().getAVMApproversByLevel(avmTransaction.getAvmId(), avmTransaction.getLevelSequence());
		return approverListInCurrentLevel.size() < avmTransaction.getRemainingApproval() ? true : false;
	}

	@Override
	protected boolean isApproverMissing(List<AVMApprover> approverList,
			AVMTransaction avmTransaction, int priority) throws FifException {
		List<AVMApprover> approverListInCurrentLevel = getAvmSetupManager().getAVMApproversByLevel(avmTransaction.getAvmId(), avmTransaction.getLevelSequence());
		return approverListInCurrentLevel.size() == 0 ? true : false;
	}

	@Override
	protected List<AVMApprovalHistory> resolveAVMApproverList(
			List<AVMApprover> rawApproverList, AVMTransaction avmTransaction)
			throws FifException {
		List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
		for (AVMApprover approver : rawApproverList) {
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setApproverId(approver.getApproverId());
			avmApprovalHistory.setOrganizationId((long)1);
			historyList.add(avmApprovalHistory);
		}
		return historyList;
	}
	
	

}
