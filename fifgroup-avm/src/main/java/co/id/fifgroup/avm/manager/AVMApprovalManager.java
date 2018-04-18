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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.constants.AVMActionType;
import co.id.fifgroup.avm.constants.AVMApproverPriorityLevel;
import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.constants.TransactionStatusType;
import co.id.fifgroup.avm.dao.AVMApprovalHistoryDAO;
import co.id.fifgroup.avm.dao.AVMLevelDAO;
import co.id.fifgroup.avm.dao.AVMTransactionDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMApprovalHistoryDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMLevelDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMTransactionDAO;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprovalProcessData;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.util.Serialization;


/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public abstract class AVMApprovalManager{

	private static final Logger logger = LoggerFactory.getLogger(AVMApprovalManager.class);
	
	public static final String APPROVER_SYSTEM = "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF";
	public static final String APPROVER_SWF_ADMIN = "44444444-4444-4444-4444-444444444444";
	
	private AVMTransactionDAO avmTransactionDAO;
	private AVMApprovalHistoryDAO avmApprovalHistoryDAO;
	private AVMLevelDAO avmLevelDAO;
	
	private AVMSetupManager avmSetupManager;
	private AVMRuleEvaluationManager avmRuleEvaluationManager;

	public AVMApprovalManager(){
		super();
		avmTransactionDAO = new MyBatisAVMTransactionDAO();
		avmApprovalHistoryDAO = new MyBatisAVMApprovalHistoryDAO();
		avmLevelDAO = new MyBatisAVMLevelDAO();
		
		avmSetupManager = new AVMSetupManager();
		avmRuleEvaluationManager = new AVMRuleEvaluationManager();
			
	}
	
	public AVMTransactionDAO getAvmTransactionDAO() {
		return avmTransactionDAO;
	}
	public void setAvmTransactionDAO(AVMTransactionDAO avmTransactionDAO) {
		this.avmTransactionDAO = avmTransactionDAO;
	}
	public AVMApprovalHistoryDAO getAvmApprovalHistoryDAO() {
		return avmApprovalHistoryDAO;
	}
	public void setAvmApprovalHistoryDAO(AVMApprovalHistoryDAO avmApprovalHistoryDAO) {
		this.avmApprovalHistoryDAO = avmApprovalHistoryDAO;
	}
	public AVMLevelDAO getAvmLevelDAO() {
		return avmLevelDAO;
	}
	public void setAvmLevelDAO(AVMLevelDAO avmLevelDAO) {
		this.avmLevelDAO = avmLevelDAO;
	}
	
	public AVMSetupManager getAvmSetupManager() {
		return avmSetupManager;
	}

	public void setAvmSetupManager(AVMSetupManager avmSetupManager) {
		this.avmSetupManager = avmSetupManager;
	}

	public AVMRuleEvaluationManager getAvmRuleEvaluationManager() {
		return avmRuleEvaluationManager;
	}

	public void setAvmRuleEvaluationManager(
			AVMRuleEvaluationManager avmRuleEvaluationManager) {
		this.avmRuleEvaluationManager = avmRuleEvaluationManager;
	}

	public UUID getTransactionSubmitter(UUID trxId) throws FifException {
		UUID submitterId = null;
		try {
			submitterId = avmApprovalHistoryDAO.getTransactionSubmitter(trxId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);	
		}
		return submitterId;
	}
	
	protected void cancelTrxBySystem(UUID avmTrxId, AVMReturnCode returnCode) throws FifException{
		try {
			UUID approverId = UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM);
			avmTransactionDAO.updateTrxStatus(avmTrxId, TransactionStatusType.CANCELED.getCode());
			avmApprovalHistoryDAO.doTrxCancellation(avmTrxId, approverId, returnCode.getMessage());
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
//	public void notifyApprovers(List<UUID> approverList, AVMTransaction avmTransaction, int priority) throws FifException{
//		try {
//			UUID submitterId = getTransactionSubmitter(avmTransaction.getAvmTrxId());
//			
//			if (isApproverMissing(approverList,avmTransaction,priority)) {
//				logger.error(AVMReturnCode.MISSING_APPROVERS.getMessage());
//				cancelTrxBySystem(avmTransaction.getAvmTrxId(), AVMReturnCode.MISSING_APPROVERS);
//				throw new FifException(AVMReturnCode.MISSING_APPROVERS.getCode());
//			}
//			
//			if (isApproverCountInsufficient(approverList,avmTransaction,priority)) {
//				logger.error(AVMReturnCode.INSUFFICIENT_APPROVERS.getMessage());
//				cancelTrxBySystem(avmTransaction.getAvmTrxId(), AVMReturnCode.INSUFFICIENT_APPROVERS);
//				throw new FifException(AVMReturnCode.INSUFFICIENT_APPROVERS.getCode());
//			}
//			
//			List<UUID> avmApproverList = resolveAVMApproverList(approverList, avmTransaction);
//			
//			List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
//			
//			for (UUID approverId : avmApproverList) {
//				if (!(approverId.equals(submitterId))) {					
//						AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
//						avmApprovalHistory.setApproverId(approverId);
//						avmApprovalHistory.setAvmTrxId(avmTransaction.getAvmTrxId());
//						avmApprovalHistory.setLevelSequence(avmTransaction.getLevelSequence());
//						avmApprovalHistory.setAvmActionType(AVMActionType.READY_TO_APPROVE.getCode());
//						avmApprovalHistory.setAvmActionTimeStamp(new Date());
//						avmApprovalHistory.setPriority(priority);
//						historyList.add(avmApprovalHistory);					
//				}
//			}
//			getAvmApprovalHistoryDAO().insertMultipleNewAVMApprovalHistories(historyList);			
//		} catch (FifException e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		}
//		catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
//		}
//	}
	
//	protected abstract boolean isApproverCountInsufficient(List<UUID> approverList, AVMTransaction avmTransaction, int priority) throws FifException;
//
//	protected abstract boolean isApproverMissing(List<UUID> approverList, AVMTransaction avmTransaction, int priority) throws FifException;

	protected abstract boolean isApproverCountInsufficient(List<AVMApprover> approverList, AVMTransaction avmTransaction, int priority) throws FifException;

	protected abstract boolean isApproverMissing(List<AVMApprover> approverList, AVMTransaction avmTransaction, int priority) throws FifException;
	
//	protected abstract List<UUID> resolveAVMApproverList(List<UUID> rawApproverList, AVMTransaction avmTransaction) throws FifException;
	
	public AVMReturnCode giveResponseToTransaction(UUID avmTrxId, UUID approverId,
			String remarks, AVMActionType actionType, UUID actualApproverId) throws FifException {
		
		AVMReturnCode returnCode = null;
		try {
			AVMApprovalHistory approversApprovalHistory = getRecentApproversApprovalHistory(avmTrxId, approverId);
			
			//add to handling null -yahya-
			if (approversApprovalHistory != null) {
				AVMTransaction currentAVMTransaction = avmTransactionDAO.getAVMTransactionById(avmTrxId);
				
				if (actionType.equals(AVMActionType.APPROVE_TRX)) {
					returnCode = doApproval(currentAVMTransaction, approversApprovalHistory, remarks, actualApproverId);
				}
				else if (actionType.equals(AVMActionType.REJECT_TRX)) {
					returnCode = doRejection(currentAVMTransaction, approversApprovalHistory, remarks, actualApproverId);
				}				
			}
			
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return returnCode;
	}
	
	public AVMReturnCode doRejection(AVMTransaction avmTransaction,
			AVMApprovalHistory avmApprovalHistory, String remarks, UUID actualApproverId) throws FifException {
		AVMReturnCode returnCode = null;
		try {
			int sequenceNumber = avmApprovalHistory.getSequenceNumber();
			UUID trxId = avmTransaction.getAvmTrxId();
			int levelSequence = avmTransaction.getLevelSequence();
			UUID approverId = avmApprovalHistory.getApproverId();
			updateApproversHistory(sequenceNumber, trxId, levelSequence, actualApproverId,
						AVMActionType.REJECT_TRX.getCode(), remarks);
			avmTransactionDAO.updateAVMTrxStatus(trxId, TransactionStatusType.REJECTED.getCode());
			String forcedRemarks = "The transaction has been rejected";
			alterOtherApproversAction(trxId, AVMActionType.REJECT_DUE_TO_OCCURING_REJECTION.getCode(), 
				forcedRemarks);
			
			returnCode = AVMReturnCode.TRANSACTION_IS_REJECTED;
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return returnCode;
	}
	
//	/**
//	 * @param currentAVMTransaction
//	 * @param approversHistory
//	 * @param remarks
//	 * @return
//	 * @throws FifException
//	 */
//	public AVMReturnCode doApproval(AVMTransaction currentAVMTransaction, AVMApprovalHistory approversHistory, String remarks) throws FifException {
//		
//		int currentRemainingApproval = currentAVMTransaction.getRemainingApproval();
//		
//		int sequenceNumber = approversHistory.getSequenceNumber();
//		UUID trxId = currentAVMTransaction.getAvmTrxId();
//		int levelSequence = approversHistory.getLevelSequence();
//		UUID approverId = approversHistory.getApproverId();
//		UUID avmId = currentAVMTransaction.getAvmId();
//		logger.debug("avmId : " + avmId + " levelSequence : " + levelSequence
//				+ " approverId : " + approverId);
//		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
//
//		try {
//			updateApproversHistory(sequenceNumber, trxId, levelSequence, approverId, 
//						AVMActionType.APPROVE_TRX.getCode(), remarks);
//			
//			int priorityOfApprover = getApproversPriority(trxId, levelSequence,
//														approverId);
//			currentRemainingApproval = currentRemainingApproval - 1;
//			currentAVMTransaction.setRemainingApproval(currentRemainingApproval);
//			
//			avmTransactionDAO.decrementTrxRemainingApproval(trxId, currentRemainingApproval);
//			
//			if (currentRemainingApproval == 0) {
//				String forcedApprovalRemarks = "Number of approvals has reached sufficient required number of approvals";
//				alterOtherApproversAction(trxId, AVMActionType.APPROVE_TRX_DUE_TO_REACH_OF_MIN_APPROVALNUMBERS.getCode(), 
//												forcedApprovalRemarks);
//				
//				if (isNowTheLastLevel(avmId, levelSequence)) {
//					avmTransactionDAO.updateAVMTrxStatus(trxId, TransactionStatusType.COMPLETED.getCode());
//					logger.debug("Avmtransaction with id : " + trxId + " has been successfully finalized ");
//					returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
//				}
//				else {
//					returnCode = moveOnToNextLevel(currentAVMTransaction);
//				}
//			}
//			else {
//				if (countApproversStillDoNothing(trxId, levelSequence) == 0 
//						&& priorityOfApprover != AVMApproverPriorityLevel.EQUAL.getCode()) {
//					int nextLowerPriority = priorityOfApprover + 1;
//					
//					List<AVMApprover> approverList = avmSetupManager.getAVMApproversByLevelAndPriority(avmId, levelSequence, nextLowerPriority);
//					
//					List<UUID> approverIdList = new ArrayList<UUID>();
//					for (AVMApprover avmApprover : approverList) {
//						approverIdList.add(avmApprover.getApproverId());
//					}
//		
//					notifyApprovers(approverIdList, currentAVMTransaction, nextLowerPriority);
//				}
//			}
//		} catch (FifException e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		}
//		catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
//		}
//		return returnCode;
//	}
	
	private boolean isNowTheLastLevel(UUID avmId, int levelSequence) throws FifException {
		boolean isTheLastLevel = false;
		try {
			int lastLevelSequence = avmLevelDAO.getTheLastLevelSequence(avmId);
			isTheLastLevel = levelSequence == lastLevelSequence ? true : false;
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return isTheLastLevel;
	}
	
	private AVMReturnCode moveOnToNextLevel(AVMTransaction avmTransaction) throws FifException {
		UUID avmTrxId = avmTransaction.getAvmTrxId();
		UUID avmId = avmTransaction.getAvmId();
		int levelSequence= avmTransaction.getLevelSequence();
		AVMVersions currentVersion = new AVMVersions();
		currentVersion.setAvmId(avmId);
		currentVersion.setAvmVersionId(avmTransaction.getAvmVersionId());
		AVMLevel nextLevel;
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		try {
			Object application = Serialization.deserializedObject(avmTransaction.getSerializedData());
			int nextLevelSequence = levelSequence + 1;
			nextLevel = getApplicableLevelSequence(currentVersion, nextLevelSequence, application);
			
			if (nextLevel != null) {
				avmTransaction.setLevelSequence(nextLevel.getLevelSequence());
				
				List<AVMApprover> approverList = avmSetupManager
						.getTopPriorityApproverList(avmId, nextLevel.getLevelSequence(), avmTransaction.getAvmVersionId());
					int priority = approverList.get(0).getPriority();
					
				int numberApproval = 1;
				if (nextLevel.getLevelMethod() == 1) {
					numberApproval = approverList.size();
				} else if (nextLevel.getLevelMethod() == 2) {
					numberApproval = nextLevel.getNumberOfApprovals();
				}
				nextLevel.setNumberOfApprovals(numberApproval);
				avmTransaction.setRemainingApproval(nextLevel.getNumberOfApprovals());
				avmTransactionDAO.changeCurrentAVMTrxLevel(avmTrxId, nextLevel.getLevelSequence(), nextLevel.getNumberOfApprovals());
								
//				List<UUID> approverIdList = new ArrayList<UUID>();
//				for (AVMApprover avmApprover : approverList) {
//					approverIdList.add(avmApprover.getApproverId());
//				}					
	
				returnCode = notifyApproversNew(approverList, avmTransaction, priority);
				
			}
			else {
				avmTransaction.setAvmTrxStatus(TransactionStatusType.COMPLETED.getCode());
				avmTransactionDAO.updateAVMTrxStatus(avmTrxId, TransactionStatusType.COMPLETED.getCode());
				logger.info("Avmtransaction with id : " + avmTrxId
								+ " has been successfully finalized ");
				returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
			}
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		
		return returnCode;
	}
	
	public AVMLevel getApplicableLevelSequence(UUID avmId, int levelSequence, Object application) throws FifException {
		AVMLevel avmLevel = null;
		try {
			boolean result = false;
			do {
				String ruleExpression = "";
				
				avmLevel = avmLevelDAO.getAVMLevelByAVMIdAndLevelSequence(avmId, levelSequence++);
				
				if (avmLevel != null) {
					if (avmLevel.getRule() != null) {
						ruleExpression = avmLevel.getRule();
					}
//					avmRuleEvaluationManager.setActiontype(AVMRuleEvaluationManager.RULE_EVALUATION);
//					avmRuleEvaluationManager.setRuleexpression(ruleExpression);
//					avmRuleEvaluationManager.setApplication(application);
					
					result = avmRuleEvaluationManager.evaluateRule(AVMRuleEvaluationManager.RULE_EVALUATION, ruleExpression, application);
				}
			} while(!result && avmLevel != null);
			
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmLevel;
	}
	
	public void recordApproval(UUID approverId, AVMTransaction avmTransaction) throws FifException {
		try {
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setApproverId(approverId);
			avmApprovalHistory.setAvmTrxId(avmTransaction.getAvmTrxId());
			avmApprovalHistory.setLevelSequence(avmTransaction.getLevelSequence());
			avmApprovalHistory.setPriority(AVMApproverPriorityLevel.EQUAL.getCode());
			avmApprovalHistory.setAvmActionTimeStamp(new Date());
			avmApprovalHistory.setAvmReceivedTimeStamp(new Date());
			avmApprovalHistory.setAvmActionType(AVMActionType.APPROVE_TRX.getCode());
			List<AVMApprovalHistory> avmApprovalHistoryList = new ArrayList<AVMApprovalHistory>();
			avmApprovalHistoryList.add(avmApprovalHistory);
			avmApprovalHistoryDAO.insertMultipleNewAVMApprovalHistories(avmApprovalHistoryList);
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public int countApproversStillDoNothing(UUID trxId, int levelSequence) throws FifException {
		int numberOfApprovers = 0;
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", trxId);
			parameterMap.put("levelSequence", levelSequence);
			numberOfApprovers = avmApprovalHistoryDAO.countApproversStillDoNothing(parameterMap);
			
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		
		return numberOfApprovers;
	}
	public void alterOtherApproversAction(UUID avmTrxId, int actionType, String forcedApprovalRemarks) throws FifException {
		try {
			avmApprovalHistoryDAO.alterOtherApproversAction(avmTrxId, actionType, forcedApprovalRemarks);
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	
	public void updateApproversHistory(int sequenceNumber, UUID trxId, int levelSequence, UUID approverId, int actionType, String remarks) throws FifException {
		
		AVMApprovalHistory approvalHistory = new AVMApprovalHistory();
		approvalHistory.setSequenceNumber(sequenceNumber);
		approvalHistory.setAvmTrxId(trxId);
		approvalHistory.setLevelSequence(levelSequence);
		approvalHistory.setApproverId(approverId);
		approvalHistory.setAvmActionType(actionType);
		approvalHistory.setAvmActionTimeStamp(new Date());
		approvalHistory.setRemarks(remarks);
		
		try {
			avmApprovalHistoryDAO.updateAVMApprovalHistory(approvalHistory);
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public AVMApprovalHistory getRecentApproversApprovalHistory(UUID avmTrxId, UUID approverId) throws FifException {
		AVMApprovalHistory approversHistory = null;
		try {
			approversHistory = avmApprovalHistoryDAO.getRecentApproversHistory(avmTrxId, approverId);
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return approversHistory;
	}
	
	public int getApproversPriority(UUID avmTrxId, int levelSequence, UUID approverId) throws FifException {
		
		int approversPriority = AVMApproverPriorityLevel.EQUAL.getCode();
		try {
			
			approversPriority = avmApprovalHistoryDAO.getApproversPriority(avmTrxId, levelSequence, approverId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return approversPriority;
		
	}
	
	public int checkAVMApprovalIntegrity(UUID trxId, UUID approverId) throws FifException {
		try{
			AVMApprovalHistory approversApprovalHistory = getRecentApproversApprovalHistory(trxId, approverId);
			AVMTransaction currentStateOfAVMTransaction = avmTransactionDAO.getAVMTransactionById(trxId);
	
			if (checkAVMTransactionIntegrity(approversApprovalHistory, currentStateOfAVMTransaction)) {
				int approversLevelSequence = approversApprovalHistory.getLevelSequence();
				int avmTransactionLevelSequence = currentStateOfAVMTransaction.getLevelSequence();
				int lastStatusOfAVMTransaction = currentStateOfAVMTransaction.getAvmTrxStatus();
				int lastApproversAction = approversApprovalHistory.getAvmActionType();
				
				if (checkLevelSequenceCompatibility(approversLevelSequence,
						avmTransactionLevelSequence)) {
					return TransactionStatusType.INCOMPATIBLE_LEVEL_SEQUENCE.getCode();
				}
				else if (checkIfTransactionIsStillInProgress(lastStatusOfAVMTransaction)) {
					return TransactionStatusType.TRANSACTION_IS_NOT_IN_PROGRESS.getCode();
				}
				else if (checkIfApproverHasNotTakenAction(lastApproversAction)) {
					return TransactionStatusType.APPROVER_HAS_ALREADY_TAKEN_ACTION.getCode();
				}
				else {
					return TransactionStatusType.AVM_APPROVAL_IS_VALID.getCode();
				}
			}
			else {
				return TransactionStatusType.TRANSACTION_IS_NOT_AVAILABLE.getCode();
			}
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	private boolean checkAVMTransactionIntegrity(AVMApprovalHistory approversApprovalHistory,
			AVMTransaction currentStateOfAVMTransaction) {
		
		return currentStateOfAVMTransaction != null && approversApprovalHistory != null;
	}
	
	private boolean checkLevelSequenceCompatibility(int approversLevelSequence,
			int avmTransactionLevelSequence) {
		return approversLevelSequence != avmTransactionLevelSequence;
	}
	
	private boolean checkIfApproverHasNotTakenAction(int lastApproversAction) {
		return lastApproversAction != AVMActionType.READY_TO_APPROVE.getCode();
	}

	private boolean checkIfTransactionIsStillInProgress(
			int lastStatusOfAVMTransaction) {
		return lastStatusOfAVMTransaction != TransactionStatusType.IN_PROGRESS.getCode();
	}
	
	public List<AVMApplicationData> getPendingApprovalByApproverIdAndTrxType(UUID approverId, String subject, int trxType, int companyId, Date submitedDateFrom, Date submitedDateTo, UUID personUUID) throws FifException {
		
		try{
			List<AVMApplicationData> resultList = avmApprovalHistoryDAO.getPendingApproval(approverId, subject, trxType, companyId, submitedDateFrom, submitedDateTo, personUUID); 
			for (AVMApplicationData applicationData : resultList) {
				Map<String, Object> resultMap = avmTransactionDAO.getTransactionSubmitter(applicationData.getAvmTrxId()); 
				UUID submitterId = (UUID)resultMap.get("submitterId");
				applicationData.setSubmitterId(submitterId);
//				applicationData.setTrxType(trxType);
			}
			
			return resultList;
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	// add yahya
//	private AVMReturnCode moveOnToNextLevel(AVMTransaction avmTransaction) throws FifException {
//		UUID avmTrxId = avmTransaction.getAvmTrxId();
//		UUID avmId = avmTransaction.getAvmId();
//		AVM applicableAVM = avmSetupManager.getAVMById(avmId);
//		int levelSequence= avmTransaction.getLevelSequence();
//		AVMLevel nextLevel;
//		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
//		try {
//			Object application = Serialization.deserializedObject(avmTransaction.getSerializedData());
//			int nextLevelSequence = levelSequence + 1;
//			nextLevel = getApplicableLevelSequence(applicableAVM, nextLevelSequence, application);
//			
//			if (nextLevel != null) {
//				avmTransaction.setLevelSequence(nextLevel.getLevelSequence());
//				avmTransaction.setRemainingApproval(nextLevel.getNumberOfApprovals());
//				
//				avmTransactionDAO.changeCurrentAVMTrxLevel(avmTrxId, nextLevel.getLevelSequence(), nextLevel.getNumberOfApprovals());
//				
//				List<AVMApprover> approverList = avmSetupManager
//					.getTopPriorityApproverList(avmId, nextLevel.getLevelSequence(), nextLevel.getVersion());
//				int priority = approverList.get(0).getPriority();
//				
//				List<UUID> approverIdList = new ArrayList<UUID>();
//				for (AVMApprover avmApprover : approverList) {
//					approverIdList.add(avmApprover.getApproverId());
//				}
//	
//				notifyApprovers(approverIdList, avmTransaction, priority);
//				
//			}
//			else {
//				avmTransaction.setAvmTrxStatus(TransactionStatusType.COMPLETED.getCode());
//				avmTransactionDAO.updateAVMTrxStatus(avmTrxId, TransactionStatusType.COMPLETED.getCode());
//				logger.info("Avmtransaction with id : " + avmTrxId
//								+ " has been successfully finalized ");
//				
//				returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
//			}
//		} 
//		catch (FifException e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		}
//		catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
//		}
//		
//		return returnCode;
//	}
	
	/**
	 * @param currentAVMTransaction
	 * @param approversHistory
	 * @param remarks
	 * @return
	 * @throws FifException
	 */
	public AVMReturnCode doApproval(AVMTransaction currentAVMTransaction, AVMApprovalHistory approversHistory, String remarks, UUID actualApproverId) throws FifException {
		
		int currentRemainingApproval = currentAVMTransaction.getRemainingApproval();
		
		int sequenceNumber = approversHistory.getSequenceNumber();
		UUID trxId = currentAVMTransaction.getAvmTrxId();
		int levelSequence = approversHistory.getLevelSequence();
		UUID approverId = approversHistory.getApproverId();
		UUID avmId = currentAVMTransaction.getAvmId();
		int avmVersionId = currentAVMTransaction.getAvmVersionId();
		logger.debug("avmId : " + avmId + " levelSequence : " + levelSequence
				+ " approverId : " + approverId);
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;

		try {
			updateApproversHistory(sequenceNumber, trxId, levelSequence, actualApproverId, 
						AVMActionType.APPROVE_TRX.getCode(), remarks);
			
//			int priorityOfApprover = getApproversPriority(trxId, levelSequence,
//														approverId);
			
			int priorityOfApprover = 0;
			currentRemainingApproval = currentRemainingApproval - 1;
			currentAVMTransaction.setRemainingApproval(currentRemainingApproval);
			
			avmTransactionDAO.decrementTrxRemainingApproval(trxId, currentRemainingApproval);
			
			if (currentRemainingApproval == 0) {
				String forcedApprovalRemarks = "Number of approvals has reached sufficient required number of approvals";
				alterOtherApproversAction(trxId, AVMActionType.APPROVE_TRX_DUE_TO_REACH_OF_MIN_APPROVALNUMBERS.getCode(), 
												forcedApprovalRemarks);
				
				if (isNowTheLastLevel(avmId, levelSequence, avmVersionId)) {
					avmTransactionDAO.updateAVMTrxStatus(trxId, TransactionStatusType.COMPLETED.getCode());
					
					logger.debug("Avmtransaction with id : " + trxId + " has been successfully finalized ");
					returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
				}
				else {
					returnCode = moveOnToNextLevel(currentAVMTransaction);
				}
			}
			else {
				if (countApproversStillDoNothing(trxId, levelSequence) == 0 
						&& priorityOfApprover != AVMApproverPriorityLevel.EQUAL.getCode()) {
					int nextLowerPriority = priorityOfApprover;// + 1;
					
					List<AVMApprover> approverList = avmSetupManager.getAVMApproversByLevelAndPriority(avmId, levelSequence, nextLowerPriority, avmVersionId);
					
//					List<UUID> approverIdList = new ArrayList<UUID>();
//					for (AVMApprover avmApprover : approverList) {
//						approverIdList.add(avmApprover.getApproverId());
//					}
		
					returnCode = notifyApproversNew(approverList, currentAVMTransaction, nextLowerPriority);
				}
			}
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return returnCode;
	}
	
	public AVMReturnCode notifyApprovers(List<UUID> approverList, AVMTransaction avmTransaction, int priority) throws FifException{
		try {
			// add returnCode for handling autoapprove
			AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
//			UUID submitterId = getTransactionSubmitter(avmTransaction.getAvmTrxId());
			
//			if (isApproverMissing(approverList,avmTransaction,priority)) {
//				logger.error(AVMReturnCode.MISSING_APPROVERS.getMessage());
//				cancelTrxBySystem(avmTransaction.getAvmTrxId(), AVMReturnCode.MISSING_APPROVERS);
//				throw new FifException(AVMReturnCode.MISSING_APPROVERS.getCode());
//			}
//			
//			if (isApproverCountInsufficient(approverList,avmTransaction,priority)) {
//				logger.error(AVMReturnCode.INSUFFICIENT_APPROVERS.getMessage());
//				cancelTrxBySystem(avmTransaction.getAvmTrxId(), AVMReturnCode.INSUFFICIENT_APPROVERS);
//				throw new FifException(AVMReturnCode.INSUFFICIENT_APPROVERS.getCode());
//			}
			
//			List<UUID> avmApproverList = resolveAVMApproverList(approverList, avmTransaction);
//			List<AVMApprovalHistory> historyList = resolveAVMApproverList(approverList, avmTransaction);
			
//			List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
			
//			for (UUID approverId : avmApproverList) {
//				if (!(approverId.equals(submitterId))) {					
//						AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
//						avmApprovalHistory.setApproverId(approverId);
//						avmApprovalHistory.setAvmTrxId(avmTransaction.getAvmTrxId());
//						avmApprovalHistory.setLevelSequence(avmTransaction.getLevelSequence());
//						avmApprovalHistory.setAvmActionType(AVMActionType.READY_TO_APPROVE.getCode());
//						avmApprovalHistory.setAvmActionTimeStamp(new Date());
//						avmApprovalHistory.setPriority(priority);
//						historyList.add(avmApprovalHistory);					
//				}
//			}
//			for(AVMApprovalHistory avmApprovalHistory : historyList) {
//				if(!(avmApprovalHistory.getApproverId().equals(submitterId))) {
//					avmApprovalHistory.setAvmTrxId(avmTransaction.getAvmTrxId());
//					avmApprovalHistory.setLevelSequence(avmTransaction.getLevelSequence());
//					avmApprovalHistory.setAvmActionType(AVMActionType.READY_TO_APPROVE.getCode());
//					avmApprovalHistory.setAvmActionTimeStamp(new Date());
//					avmApprovalHistory.setAvmReceivedTimeStamp(new Date());
//					avmApprovalHistory.setPriority(priority);					
//				}
//			}
//			getAvmApprovalHistoryDAO().insertMultipleNewAVMApprovalHistories(historyList);
			
			// check autoapprove
//			returnCode = doAutoapprove(historyList, avmTransaction);
			
			return returnCode;
//		} catch (FifException e) {
//			logger.error(e.getMessage(), e);
//			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
//	protected abstract List<AVMApprovalHistory> resolveAVMApproverList(List<UUID> rawApproverList, AVMTransaction avmTransaction) throws FifException;
	protected abstract List<AVMApprovalHistory> resolveAVMApproverList(List<AVMApprover> rawApproverList, AVMTransaction avmTransaction) throws FifException;
	
	public AVMLevel getApplicableLevelSequence(AVMVersions currentVersion, int levelSequence, Object application) throws FifException {
		AVMLevel avmLevel = null;
		try {
			boolean result = false;
			do {
				String ruleExpression = "";
				
				avmLevel = avmLevelDAO.getAVMLevelByAVMIdAndLevelSequence(currentVersion.getAvmId(), levelSequence++, currentVersion.getAvmVersionId());
				
				if (avmLevel != null) {
					if (avmLevel.getRule() != null) {
						ruleExpression = avmLevel.getRule();
					}
//					avmRuleEvaluationManager.setActiontype(AVMRuleEvaluationManager.RULE_EVALUATION);
//					avmRuleEvaluationManager.setRuleexpression(ruleExpression);
//					avmRuleEvaluationManager.setApplication(application);
					
					result = avmRuleEvaluationManager.evaluateRule(AVMRuleEvaluationManager.RULE_EVALUATION, ruleExpression, application);
				}
			} while(!result && avmLevel != null);
			
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmLevel;
	}
	
	public List<AVMApprovalHistory> getApprovalHistoryByAVMTrxId(UUID trxId) throws FifException {
		try {
			List<AVMApprovalHistory> resultList = avmApprovalHistoryDAO.getApprovalHistoryByAVMTrxId(trxId);
			return resultList;
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}

	public List<AVMApprovalHistory> reassignApproverByAdmin(UUID avmTrxId, int sequenceNumber, UUID newApproverId, String remarks) throws FifException{
//		AVMReturnCode returnCode = null;
		List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
		try {
			AVMApprovalHistory approversApprovalHistory = getRecentApproversApprovalHistory(avmTrxId, sequenceNumber);
			
			AVMTransaction currentAVMTransaction = avmTransactionDAO.getAVMTransactionById(avmTrxId);
			
//			returnCode = doReassign(currentAVMTransaction, approversApprovalHistory, newApproverId, organizatonId, remarks);
			approvalHistories = doReassign(currentAVMTransaction, approversApprovalHistory, newApproverId, remarks);
						
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
//		return returnCode;
		return approvalHistories;
	}
	
	public List<AVMApprovalHistory> doReassign(AVMTransaction avmTransaction,
			AVMApprovalHistory avmApprovalHistory, UUID newApproverId, String remarks) throws FifException {
//		AVMReturnCode returnCode = null;
		List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
		try {
			int sequenceNumber = avmApprovalHistory.getSequenceNumber();
			UUID trxId = avmTransaction.getAvmTrxId();
			int levelSequence = avmTransaction.getLevelSequence();
			UUID approverId = avmApprovalHistory.getApproverId();
			int priority = avmApprovalHistory.getPriority();
//			UUID submitterId = getTransactionSubmitter(avmTransaction.getAvmTrxId());
			
			updateApproversHistory(sequenceNumber, trxId, levelSequence, approverId,
						AVMActionType.RE_ASSIGN_BY_ADMIN.getCode(), remarks);
			
			AVMApprovalHistory avmApprovalHistoryReassign = new AVMApprovalHistory();
			avmApprovalHistoryReassign.setApproverId(newApproverId);
			avmApprovalHistoryReassign.setAvmTrxId(avmTransaction.getAvmTrxId());
			avmApprovalHistoryReassign.setLevelSequence(avmTransaction.getLevelSequence());
			avmApprovalHistoryReassign.setAvmActionType(AVMActionType.READY_TO_APPROVE.getCode());
			avmApprovalHistoryReassign.setAvmActionTimeStamp(new Date());
			avmApprovalHistoryReassign.setAvmReceivedTimeStamp(new Date());
			avmApprovalHistoryReassign.setPriority(priority);
			avmApprovalHistoryReassign.setVacationApproverId(approverId);
			historyList.add(avmApprovalHistoryReassign);
			
			getAvmApprovalHistoryDAO().insertMultipleNewAVMApprovalHistories(historyList);

//			returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
//		return returnCode;
		return historyList;
	}
	
	public AVMApprovalHistory getLastApprovalHistory(UUID avmTrxId) throws FifException {
		
		AVMApprovalHistory lastApprovalHistory = null;
		try {
			
			lastApprovalHistory = avmApprovalHistoryDAO.getLastApprovalHistory(avmTrxId);
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return lastApprovalHistory;
		
	}
	
	// for reassign in sysadmin
	public AVMApprovalHistory getRecentApproversApprovalHistory(UUID avmTrxId, int sequenceNumber) throws FifException {
		AVMApprovalHistory approversHistory = null;
		try {
			approversHistory = avmApprovalHistoryDAO.getRecentApproversHistory(avmTrxId, sequenceNumber);
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return approversHistory;
	}
	
	public List<AVMApplicationData> getPendingApprovalByApproverIdAndTrxType(UUID jobUUID, List<UUID> rolesUUID, List<UUID> jobRolesUUID, 
			UUID employeeUUID, UUID deptOwnerUUID, Long organizationId, Long branchId, String subject, int trxType, Long companyId, UUID jobGropeUUID, List<Long> companiesGroup) throws FifException {
		
		try{
			List<AVMApplicationData> resultList = avmApprovalHistoryDAO.getPendingApproval(jobUUID, rolesUUID, jobRolesUUID, 
					employeeUUID, deptOwnerUUID, organizationId, branchId, subject, trxType, companyId, jobGropeUUID, companiesGroup); 
//			for (AVMApplicationData applicationData : resultList) {
//				Map<String, Object> resultMap = avmTransactionDAO.getTransactionSubmitter(applicationData.getAvmTrxId()); 
//				UUID submitterId = (UUID)resultMap.get("submitterId");
//				applicationData.setSubmitterId(submitterId);
//				applicationData.setTrxType(trxType);
//			}
			
			return resultList;
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	public List<AVMApplicationData> getPendingApprovalByApproverIdAndTrxType(UUID jobUUID, List<UUID> rolesUUID, List<UUID> jobRolesUUID, 
			UUID employeeUUID, UUID deptOwnerUUID, Long organizationId, Long branchId, String subject, int trxType, Long companyId, UUID jobGropeUUID, List<Long> companiesGroup,
			Date submitDateFrom, Date submitDateTo, UUID approverId, int company) throws FifException {
		
		try{
			List<AVMApplicationData> resultList = avmApprovalHistoryDAO.getPendingApproval(jobUUID, rolesUUID, jobRolesUUID, 
					employeeUUID, deptOwnerUUID, organizationId, branchId, subject, trxType, companyId, jobGropeUUID, companiesGroup,
					submitDateFrom, submitDateTo, approverId, company); 
//			for (AVMApplicationData applicationData : resultList) {
//				Map<String, Object> resultMap = avmTransactionDAO.getTransactionSubmitter(applicationData.getAvmTrxId()); 
//				UUID submitterId = (UUID)resultMap.get("submitterId");
//				applicationData.setSubmitterId(submitterId);
//				applicationData.setTrxType(trxType);
//			}
			
			return resultList;
		}
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	public List<AVMApprovalHistory> getApproverIdByAVMTrxId(UUID trxId) throws FifException {
		try {
			List<AVMApprovalHistory> resultList = avmApprovalHistoryDAO.getApproverIdByAVMTrxId(trxId);
			return resultList;
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	public List<AVMApprovalHistory> getApproverReadyByAVMTrxId(UUID trxId) throws FifException {
		try {
			List<AVMApprovalHistory> resultList = avmApprovalHistoryDAO.getApproverReadyByAVMTrxId(trxId);
			return resultList;
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	public AVMReturnCode doAutoapprove(List<AVMApprovalHistory> avmApprovalHistories, AVMTransaction currentAVMTransaction) throws FifException {
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		for (AVMApprovalHistory avmApprovalHistory : avmApprovalHistories) {
			if (avmApprovalHistory.getApproverId().equals(UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM))) {
				String remarks = "Autoapprove by system";
				returnCode = doApproval(currentAVMTransaction, avmApprovalHistory, remarks, UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM));
			}
		}
		return returnCode;
	}
	
	private boolean isNowTheLastLevel(UUID avmId, int levelSequence, int avmVersionId) throws FifException {
		boolean isTheLastLevel = false;
		try {
			int lastLevelSequence = avmLevelDAO.getTheLastLevelSequence(avmId, avmVersionId);
			isTheLastLevel = levelSequence == lastLevelSequence ? true : false;
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return isTheLastLevel;
	}	
	
	public AVMReturnCode notifyApproversNew(List<AVMApprover> approverList, AVMTransaction avmTransaction, int priority) throws FifException{
		try {
			// add returnCode for handling autoapprove
			AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
//			UUID submitterId = getTransactionSubmitter(avmTransaction.getAvmTrxId());
			
			if (isApproverMissing(approverList,avmTransaction,priority)) {
				logger.error(AVMReturnCode.MISSING_APPROVERS.getMessage());
//				cancelTrxBySystem(avmTransaction.getAvmTrxId(), AVMReturnCode.MISSING_APPROVERS);
				errorTransaction(avmTransaction.getAvmTrxId());
				throw new FifException(AVMReturnCode.MISSING_APPROVERS.getCode(), AVMReturnCode.MISSING_APPROVERS.getMessage());
			}
			
			if (isApproverCountInsufficient(approverList,avmTransaction,priority)) {
				logger.error(AVMReturnCode.INSUFFICIENT_APPROVERS.getMessage());
//				cancelTrxBySystem(avmTransaction.getAvmTrxId(), AVMReturnCode.INSUFFICIENT_APPROVERS);
				errorTransaction(avmTransaction.getAvmTrxId());
				throw new FifException(AVMReturnCode.INSUFFICIENT_APPROVERS.getCode(), AVMReturnCode.INSUFFICIENT_APPROVERS.getMessage());
			}
			
//			List<UUID> avmApproverList = resolveAVMApproverList(approverList, avmTransaction);
			List<AVMApprovalHistory> historyList = resolveAVMApproverList(approverList, avmTransaction);
			
			avmTransaction.setRemainingApproval(historyList.size());
			
			// check same approver
			List<AVMApprovalHistory> removeIndex = new ArrayList<AVMApprovalHistory>();
			for(int i = 0; i < historyList.size(); i++) {
				AVMApprovalHistory avmApprovalHistory = historyList.get(i);
				for(int j = (i + 1); j < historyList.size(); j++) {
					AVMApprovalHistory nextAvmApprovalHistory = historyList.get(j);
					if(isSameApprovers(avmApprovalHistory, nextAvmApprovalHistory) && !removeIndex.contains(nextAvmApprovalHistory)) {
						removeIndex.add(nextAvmApprovalHistory);
					}
				}
			}
//			for (int i = 0; i < approverList.size(); i++) {
//				for (int j = k; j < approverList.size(); j++) {
//					if (approverList.get(i).getApproverId().equals(approverList.get(j).getApproverId())
//							&& approverList.get(i).getApproverId().equals(approverList.get(j).getApproverId())) {
//						if (historyList.get(i).getApproverId().equals(historyList.get(j).getApproverId())
//								&& (historyList.get(i).getOrganizationId().equals(historyList.get(j).getOrganizationId()) 
//									|| ( historyList.get(i).getOrganizationId() != null &&
//										 historyList.get(j).getOrganizationId() != null &&
//										 historyList.get(i).getOrganizationId().equals(historyList.get(j).getOrganizationId())))
//								&& (historyList.get(i).getBranchId() == historyList.get(j).getBranchId() 
//										|| ( historyList.get(i).getBranchId() != null &&
//										 historyList.get(j).getBranchId() != null &&
//										 historyList.get(i).getBranchId().equals(historyList.get(j).getBranchId())))) {
//							removeIndex.add(historyList.get(j));
//						}
//					}
//					k++;
//				}
//			}
			
			AVMLevel level = avmLevelDAO.getAVMLevelByAVMIdAndLevelSequence(avmTransaction.getAvmId(), avmTransaction.getLevelSequence(), avmTransaction.getAvmVersionId());
			if (level != null) {
				Integer remainingApproval = null;
				if(removeIndex.isEmpty()) {
					remainingApproval = historyList.size();
				} else {
					if (level.getLevelMethod() == 1) {
						remainingApproval = avmTransaction.getRemainingApproval() - removeIndex.size();
					} else if (level.getLevelMethod() == 2) {
						remainingApproval = approverList.size() - removeIndex.size();
						remainingApproval = remainingApproval < avmTransaction.getRemainingApproval() ? remainingApproval : avmTransaction.getRemainingApproval();
					}
					historyList.removeAll(removeIndex);
				}
				if (remainingApproval != null) {
					avmTransactionDAO.updateRemainingApproval(avmTransaction.getAvmTrxId(), remainingApproval);
				}
			}
			
//			List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
			
//			for (UUID approverId : avmApproverList) {
//				if (!(approverId.equals(submitterId))) {					
//						AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
//						avmApprovalHistory.setApproverId(approverId);
//						avmApprovalHistory.setAvmTrxId(avmTransaction.getAvmTrxId());
//						avmApprovalHistory.setLevelSequence(avmTransaction.getLevelSequence());
//						avmApprovalHistory.setAvmActionType(AVMActionType.READY_TO_APPROVE.getCode());
//						avmApprovalHistory.setAvmActionTimeStamp(new Date());
//						avmApprovalHistory.setPriority(priority);
//						historyList.add(avmApprovalHistory);					
//				}
//			}
			for(AVMApprovalHistory avmApprovalHistory : historyList) {
//				if(!(avmApprovalHistory.getApproverId().equals(submitterId))) {
					avmApprovalHistory.setAvmTrxId(avmTransaction.getAvmTrxId());
					avmApprovalHistory.setLevelSequence(avmTransaction.getLevelSequence());
					avmApprovalHistory.setAvmActionType(AVMActionType.READY_TO_APPROVE.getCode());
					avmApprovalHistory.setAvmActionTimeStamp(new Date());
					avmApprovalHistory.setAvmReceivedTimeStamp(new Date());
					avmApprovalHistory.setPriority(priority);					
//				}
			}
			getAvmApprovalHistoryDAO().insertMultipleNewAVMApprovalHistories(historyList);
			
			// check autoapprove
			returnCode = doAutoapprove(historyList, avmTransaction);
			
			return returnCode;
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	private boolean isSameApprovers(AVMApprovalHistory avmApprovalHistory, AVMApprovalHistory avmApprovalHistory2) {
		for(UUID uuid : avmApprovalHistory.getApproverUUIDs()) {
			for(UUID uuid2 : avmApprovalHistory2.getApproverUUIDs()) {
				if(uuid.equals(uuid2)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<AVMApprovalProcessData> getApprovalProcess(int companyId, int trxType, int actionType, Date receivedDateFrom, Date receivedDateTo, List<UUID> listEmployee) throws FifException {
		try {
			List<AVMApprovalProcessData> resultList = avmApprovalHistoryDAO.getApprovalProcess(companyId, trxType, actionType, receivedDateFrom, receivedDateTo, listEmployee);
			return resultList;
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	public List<AVMApprovalProcessData> getCompletedTaskByApproverId(UUID approverId, String subject, int trxType, int trxStatus, Date approvedDateFrom, Date approvedDateTo) throws FifException {
		try {
			List<AVMApprovalProcessData> resultList = avmApprovalHistoryDAO.getCompletedTaskByApproverId(approverId, subject, trxType, trxStatus, approvedDateFrom, approvedDateTo);
			return resultList;
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	public Date getLastCompletedActionApproval(UUID avmTrxId)
			throws FifException {
		try {
			return avmApprovalHistoryDAO.getLastCompletedActionApproval(avmTrxId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	public void errorTransaction(UUID avmTrxId) throws FifException{
		try {
			avmTransactionDAO.errorTransaction(avmTrxId);
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	//	 cek status trxId (GAL - [15091510574968] Perbaikan NPK double setelah approve) 
	//	 yang bisa di proses jika statusnya blm approved/reject
	public int checkStatusIsApproved(UUID avmTrxId) throws FifException{
		try {
			return avmTransactionDAO.checkStatusIsApproved(avmTrxId);
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
}
