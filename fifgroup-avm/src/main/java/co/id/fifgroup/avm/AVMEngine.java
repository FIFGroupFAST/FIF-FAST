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

package co.id.fifgroup.avm;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.constants.AVMActionType;
import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMAndTransactionMapping;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMSummarizedTransaction;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.avm.manager.AVMSetupManager;
import co.id.fifgroup.avm.manager.AVMTransactionManager;
import co.id.fifgroup.avm.manager.DefaultAVMApprovalManager;
import co.id.fifgroup.avm.util.Serialization;

/**
 * The entry point to all AVM usage
 * @author Iswanto and Erlangga
 *
 */
public class AVMEngine implements IAVMEngine, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AVMSetupManager avmSetupManager;
	private AVMTransactionManager avmTransactionManager;
	private AVMApprovalManager avmApprovalManager;
	
	private static final Logger log = LoggerFactory.getLogger(AVMEngine.class);
	
	public AVMEngine(){
		avmSetupManager = new AVMSetupManager();
		avmTransactionManager = new AVMTransactionManager();
		avmApprovalManager = new DefaultAVMApprovalManager();
	}
	
	public AVMSetupManager getAvmSetupManager() {
		return avmSetupManager;
	}

	public void setAvmSetupManager(AVMSetupManager avmSetupManager) {
		this.avmSetupManager = avmSetupManager;
	}

	public AVMTransactionManager getAvmTransactionManager() {
		return avmTransactionManager;
	}

	public void setAvmTransactionManager(AVMTransactionManager avmTransactionManager) {
		this.avmTransactionManager = avmTransactionManager;
	}

	public AVMApprovalManager getAvmApprovalManager() {
		return avmApprovalManager;
	}

	public void setAvmApprovalManager(AVMApprovalManager avmApprovalManager) {
		this.avmApprovalManager = avmApprovalManager;
	}

	public void createAVMTemplate(AVM avm, List<AVMLevel> avmLevels) throws FifException {
		avmSetupManager.createAVMTemplate(avm, avmLevels);	
	}
	
	public void updateAVMTemplate(Map<String, Object> parameterMap) {
		
	}
	
	public void deleteAVM(UUID avmId) {
		
	}
	
	public List<AVMLevel> getAVMLevels(UUID avmId) throws FifException {
		return avmSetupManager.getAVMLevelList(avmId);
	}
	
	public AVM getAVM(UUID avmId) {
		return null;
	}
	
	public AVMLevel getApplicableLevelSequence(UUID avmId, int levelSequence, Object application)
		throws FifException {
		return avmApprovalManager.getApplicableLevelSequence(avmId, levelSequence, application);
	}
	
	public int createAVMLevel(UUID avmId, int requiredNumberOfApprovals, String ruleExpression) throws FifException {
		
		AVMLevel avmLevel = new AVMLevel();
		avmLevel.setAvmId(avmId);
		avmLevel.setNumberOfApprovals(requiredNumberOfApprovals);
		avmLevel.setRule(ruleExpression);
//		MyBatisAVMLevelDAO avmLevelDAO = new MyBatisAVMLevelDAO();
//		return avmLevelDAO.insertNewAVMLevel(avmLevel);
		return avmSetupManager.insertNewAVMLevel(avmLevel);
	}
	
	public void updateAVMLevel(Map<String, Object> parameterMap){
		
	}
	
	public void deleteAVMLevel(Map<String, Object> parameterMap) {
		
	}
	
	public AVMLevel getAVMLevel(Map<String, Object> parameterMap) {
		return null;
	}
	
	public void mapAVMAndTrxTypeList(UUID avmId, List<Integer> trxTypeList) throws FifException {
		avmSetupManager.mapAVMAndTransactionTypeList(avmId, trxTypeList);
	}
	
	public void setUpNewAVMTransaction(UUID trxId, UUID avmId, UUID submitterId, 
						Object order, int trxType) throws FifException {
		AVMLevel applicableAVMLevelSequence = 
				getApplicableLevelSequence(avmId, AVMSetupManager.INITIAL_LEVEL_SEQUENCE, order);
		AVMTransaction avmTransaction = avmTransactionManager.setUpNewAVMTransaction(trxId, avmId, submitterId, 
				order, trxType, applicableAVMLevelSequence.getLevelSequence(), applicableAVMLevelSequence.getNumberOfApprovals());
		List<AVMApprover> approverList = getTopPriorityApproverList(avmId, applicableAVMLevelSequence.getLevelSequence());
		
		int priority = approverList.get(0).getPriority();
		
		List<UUID> approverIdList = new ArrayList<UUID>();
		for (AVMApprover avmApprover : approverList) {
			approverIdList.add(avmApprover.getApproverId());
		}
		
		avmApprovalManager.notifyApprovers(approverIdList, avmTransaction, priority);
	}
	
	public int checkAVMTransactionStatus(UUID trxId) throws FifException {
		return avmTransactionManager.checkAVMTransactionStatus(trxId);
	}
	
	public AVMReturnCode giveReponseToTransaction(UUID trxId, UUID approverId, String remarks,
			AVMActionType actionType, UUID actualApproverId) throws FifException {
		return avmApprovalManager.giveResponseToTransaction(trxId, approverId, remarks, actionType, actualApproverId);
	}
	
	public AVMReturnCode approveTransaction(UUID trxId, UUID approverId, String remarks, UUID actualApproverId) throws FifException {
		try{
			return giveReponseToTransaction(trxId, approverId, remarks, AVMActionType.APPROVE_TRX, actualApproverId);
		} catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}
	
	public AVMReturnCode rejectTransaction(UUID trxId, UUID approverId, String remarks, UUID actualApproverId) throws FifException {
		try{
			return giveReponseToTransaction(trxId, approverId, remarks, AVMActionType.REJECT_TRX, actualApproverId);
		} catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}	
	}
	
	public AVMReturnCode cancelTransaction(UUID trxId, UUID approverId, String remarks) throws FifException {
		return avmTransactionManager.cancelTransaction(trxId, approverId, remarks);
	}
	
	public void resubmitTransaction(UUID trxId, UUID submitterId, Object application) throws FifException {
		AVMTransaction currentAVMTransaction = getAVMTransactionById(trxId);
		AVMLevel applicableAVMLevelSequence = getApplicableLevelSequence(currentAVMTransaction.getAvmId(), AVMSetupManager.INITIAL_LEVEL_SEQUENCE, application);
		AVMTransaction avmTransaction = avmTransactionManager.resubmitTransaction(trxId, submitterId, application, applicableAVMLevelSequence.getLevelSequence(), applicableAVMLevelSequence.getNumberOfApprovals());
		
		List<AVMApprover> approverList = getTopPriorityApproverList(currentAVMTransaction.getAvmId(), applicableAVMLevelSequence.getLevelSequence());
		int priority = approverList.get(0).getPriority();
		
		List<UUID> approverIdList = new ArrayList<UUID>();
		for (AVMApprover avmApprover : approverList) {
			approverIdList.add(avmApprover.getApproverId());
		}
		
		avmApprovalManager.notifyApprovers(approverIdList, avmTransaction, priority);
	}
	
	public List<AVMApprover> getTopPriorityApproverList(UUID avmId, int levelSequence) throws FifException {
	
		List<AVMApprover> approverList = avmSetupManager.getTopPriorityApproverList(avmId, levelSequence);
		
		return approverList;
	}
	
	public List<AVMApprover> getAVMApproverList(UUID avmId) throws FifException {
	
		List<AVMApprover> approverList = avmSetupManager.getAVMApprovers(avmId);
			
		return approverList;
	}
	
//	public void notifyApprovers(final List<UUID> approverList,
//			final AVMTransaction avmTransaction, final int priority) throws FifException {
//		avmApprovalManager.notifyApprovers(approverList, avmTransaction, priority);
//	}
	
	
	// get pending for admin
	public List<AVMApplicationData> getPendingApprovalByApproverIdAndTrxType(UUID approverId, String subject,
			int trxType, int companyId, Date submitedDateFrom, Date submitedDateTo, UUID personUUID) throws FifException {
		return avmApprovalManager.getPendingApprovalByApproverIdAndTrxType(approverId, subject, trxType, companyId, submitedDateFrom, submitedDateTo, personUUID);
	}
	
	public List<AVMApplicationData> getRejectedTransactions(UUID submitterId,
			int trxType, String subject) throws FifException {
		return avmTransactionManager.getRejectedTransactions(submitterId, trxType, subject);
	}
	
	public List<AVMApplicationData> getPendingTransactions(UUID submitterId, int trxType, String subject) throws FifException {
		return avmTransactionManager.getPendingTransactions(submitterId, trxType, subject);
	}

	public int checkAVMApprovalIntegrity(UUID trxId, UUID approverId) throws FifException {
		try{
			return avmApprovalManager.checkAVMApprovalIntegrity(trxId, approverId);
		} catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}
	}

	public void assignApprover(List<AVMApprover> approverList) throws FifException {
		avmSetupManager.assignApprover(approverList);
	}

	public AVMTransaction getAVMTransactionById(UUID trxId) throws FifException {
		return avmTransactionManager.getAVMTransactionById(trxId);
		
	}
	
	public List<String> getAllRuleAttributes() throws FifException {
		return avmSetupManager.getAllRuleAttributes();
	}
	
	public List<AVMSummarizedTransaction> getSummarizedPendingApproval(UUID approverId)
	throws FifException {
		return avmTransactionManager.getSummarizedPendingApproval(approverId);
	}
	
	public List<AVMSummarizedTransaction> getSummarizedPendingTransactions(UUID submitterId)
	throws FifException {
		return avmTransactionManager.getSummarizedPendingTransactions(submitterId);
	}
	
	public List<AVMSummarizedTransaction> getSummarizedRejectedTransactions(UUID submitterId)
	throws FifException {
		return avmTransactionManager.getSummarizedRejectedTransactions(submitterId);
	}	
	
	//add yahya
	public AVMLevel getApplicableLevelSequence(AVMVersions currentVersion, int levelSequence, Object application)
			throws FifException {
			return avmApprovalManager.getApplicableLevelSequence(currentVersion, levelSequence, application);
		}
	
	public List<AVMApprover> getTopPriorityApproverList(UUID avmId, int levelSequence, int version) throws FifException {
		
		List<AVMApprover> approverList = avmSetupManager.getTopPriorityApproverList(avmId, levelSequence, version);
		
		return approverList;
	}
	
	public List<AVMApprover> getAVMApproverList(UUID avmId, int version) throws FifException {
	
		List<AVMApprover> approverList = avmSetupManager.getAVMApprovers(avmId, version);
			
		return approverList;
	}
	
	public AVMReturnCode setUpNewAVMTransaction(UUID trxId, UUID avmId, UUID submitterId, 
			Object order, int trxType, String subject, Long companyId) throws FifException {
		AVMReturnCode returnCode = null;
		AVMVersions currentVersion = getApplicableAVMVersion(avmId);
		if (currentVersion != null) {
			AVMLevel applicableAVMLevelSequence = 
					getApplicableLevelSequence(currentVersion, AVMSetupManager.INITIAL_LEVEL_SEQUENCE, order);

			if (applicableAVMLevelSequence != null) {
			
				returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
				
				List<AVMApprover> approverList = getTopPriorityApproverList(avmId, applicableAVMLevelSequence.getLevelSequence(), applicableAVMLevelSequence.getAvmVersionId());
				
				int priority = approverList.get(0).getPriority();
				
	//			List<UUID> approverIdList = new ArrayList<UUID>();
	//			for (AVMApprover avmApprover : approverList) {
	//				approverIdList.add(avmApprover.getApproverId());
	//			}
				
				// level method
				int numberApproval = 1;
				if (applicableAVMLevelSequence.getLevelMethod() == 1) {
					numberApproval = approverList.size();
				} else if (applicableAVMLevelSequence.getLevelMethod() == 2) {
					numberApproval = applicableAVMLevelSequence.getNumberOfApprovals();
				}
				
				AVMTransaction avmTransaction = avmTransactionManager.setUpNewAVMTransaction(trxId, avmId, submitterId, 
						order, trxType, applicableAVMLevelSequence.getLevelSequence(), numberApproval, 
						currentVersion.getAvmVersionId(), subject, companyId);
				
				returnCode = avmApprovalManager.notifyApproversNew(approverList, avmTransaction, priority);
			
			} else {
				returnCode = AVMReturnCode.NO_APPLICABLE_AVM_LEVEL_FOUND;
			}
		} else {
			returnCode = AVMReturnCode.NO_APPLICABLE_AVM_FOUND;
		}
		
		
		return returnCode;
	}
	
	public int createAVMLevel(UUID avmId, int requiredNumberOfApprovals, String ruleExpression, String levelName) throws FifException {
		
		AVMLevel avmLevel = new AVMLevel();
		avmLevel.setAvmId(avmId);
		avmLevel.setNumberOfApprovals(requiredNumberOfApprovals);
		avmLevel.setRule(ruleExpression);
		avmLevel.setLevelName(levelName);
		return avmSetupManager.insertNewAVMLevel(avmLevel);
	}

	public List<AVMLevel> getAVMLevels(UUID avmId, int version) throws FifException {
		return avmSetupManager.getAVMLevelList(avmId, version);
	}
	
	public AVMAndTransactionMapping getAVMByTrxType(int trxType) throws FifException {
		return avmSetupManager.getAVMByTrxType(trxType);
	}
	
	public void updateMappedAVMAndTrxTypeList(UUID avmId, List<Integer> trxTypeList) throws FifException {
		avmSetupManager.updateMappedAVMAndTrxTypeList(avmId, trxTypeList);
	}
	
	public List<AVMApprovalHistory> getApprovalHistoryByAVMTrxId(UUID trxId) throws FifException {
		return avmApprovalManager.getApprovalHistoryByAVMTrxId(trxId);
	}
	
	public List<AVMSummarizedTransaction> getAllSummarizedPendingTransactions() throws FifException {
		return avmTransactionManager.getAllSummarizedPendingTransactions();
	}
	
	public Map<String, List<AVMApplicationData>> getPendingTransactions(List<Integer> trxTypeList) throws FifException {
		return avmTransactionManager.getPendingTransactions(trxTypeList);
	}
	
//	public AVMReturnCode reassignApproverByAdmin(UUID avmTrxId, int sequenceNumber, UUID newApproverId, long organizationId, String remarks) throws FifException{
	public List<AVMApprovalHistory> reassignApproverByAdmin(UUID avmTrxId, int sequenceNumber, UUID newApproverId, String remarks) throws FifException{
		try{
			return avmApprovalManager.reassignApproverByAdmin(avmTrxId, sequenceNumber, newApproverId, remarks);
		} catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(),ex);
		}	
	}
	
	public AVMVersions getApplicableAVMVersion(UUID avmId) throws FifException{
		try {
			return avmSetupManager.getCurrentActiveVersion(avmId, new Date());
		} catch(Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), ex);
		}
	}
	
	public List<AVMSummarizedTransaction> getSummarizedAllPendingApproval()	throws FifException {
		return avmTransactionManager.getSummarizedAllPendingApproval();
	}
	
	public List<AVMSummarizedTransaction> getAllSummarizedRejectedTransactions() throws FifException {
		return avmTransactionManager.getAllSummarizedRejectedTransactions();
	}
	
	public List<AVMApplicationData> getRejectedTransactionsByTrxType(int trxType) throws FifException {
		return avmTransactionManager.getRejectedTransactionsByTrxType(trxType);
	}
	
	public List<AVMSummarizedTransaction> getSummarizedPendingApproval(UUID positionUUID, UUID roleUUID, UUID userUUID)
	throws FifException {
		return avmTransactionManager.getSummarizedPendingApproval(positionUUID, roleUUID, userUUID);
	}
	
	// get pending for employee
	public List<AVMApplicationData> getPendingApprovalByApproverIdAndTrxType(UUID jobUUID, List<UUID> rolesUUID, List<UUID> jobRolesUUID, 
			UUID employeeUUID, UUID deptOwnerUUID, Long organizationId, Long branchId, String subject, int trxType, Long companyId, UUID jobGroupUUID, List<Long> companiesGroup) throws FifException {
		return avmApprovalManager.getPendingApprovalByApproverIdAndTrxType(jobUUID, rolesUUID, jobRolesUUID, employeeUUID, deptOwnerUUID, organizationId, 
				branchId, subject, trxType, companyId, jobGroupUUID, companiesGroup);
	}
	
	public List<AVMApplicationData> getPendingApprovalByApproverIdAndTrxType(UUID jobUUID, List<UUID> rolesUUID, List<UUID> jobRolesUUID, 
			UUID employeeUUID, UUID deptOwnerUUID, Long organizationId, Long branchId, String subject, int trxType, Long companyId, UUID jobGroupUUID, List<Long> companiesGroup,
			Date submitDateFrom, Date submitDateTo, UUID approverId, int company) throws FifException {
		return avmApprovalManager.getPendingApprovalByApproverIdAndTrxType(jobUUID, rolesUUID, jobRolesUUID, employeeUUID, deptOwnerUUID, organizationId, 
				branchId, subject, trxType, companyId, jobGroupUUID, companiesGroup, submitDateFrom, submitDateTo, approverId, company);
	}
	
	public List<AVMApprover> getApplicableLevelApproverList(
			AVMTransaction avmTransaction) throws FifException {
		
		Object serializedData = Serialization.deserializedObject(avmTransaction
				.getSerializedData()); 
		AVMVersions currentVersion = getApplicableAVMVersion(avmTransaction.getAvmId());
		
		List<AVMApprover> listApprover = new ArrayList<AVMApprover>();
		
		List<AVMLevel> listAVMLevel = getAVMLevels(currentVersion.getAvmId(), currentVersion.getAvmVersionId());
		int i = 0;
		while (i < listAVMLevel.size()) {
			AVMLevel applicableLevel = getApplicableLevelSequence(currentVersion, i, serializedData);
			if (applicableLevel != null) {
				i = applicableLevel.getLevelSequence();
				List<AVMApprover> listAVMApprover = avmSetupManager.getAVMApproversByLevel(applicableLevel.getAvmId(), applicableLevel.getLevelSequence(), applicableLevel.getAvmVersionId());
				listApprover.addAll(listAVMApprover);
				i++;
			} else {
				break;
			}			
		}
		
		return listApprover;		
	}
	
	public void createAVMTemplate(AVM avm, AVMVersions avmVersions, List<AVMLevel> avmLevels, List<AVMApprover> avmApprovers) throws FifException {
		avmSetupManager.createAVMTemplate(avm, avmVersions, avmLevels, avmApprovers);	
	}
	
	public AVMReturnCode notifyApprovers(final List<UUID> approverList,
			final AVMTransaction avmTransaction, final int priority) throws FifException {
		return avmApprovalManager.notifyApprovers(approverList, avmTransaction, priority);
	}
	
	public AVMReturnCode approveTransactionBySystem(UUID trxId, UUID approverId, String remarks) throws FifException {
		return avmTransactionManager.approveTransactionBySystem(trxId, approverId, remarks);
	}
	
}
