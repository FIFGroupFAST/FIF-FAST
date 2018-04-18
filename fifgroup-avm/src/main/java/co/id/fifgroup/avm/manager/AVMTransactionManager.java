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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.constants.AVMActionType;
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
import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.domain.AVMRejectionDoer;
import co.id.fifgroup.avm.domain.AVMSummarizedTransaction;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.util.AVMOutgoingReportComparator;
import co.id.fifgroup.avm.util.Serialization;


/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMTransactionManager {

	private static final Logger logger = LoggerFactory.getLogger(AVMTransactionManager.class);
	
	private AVMTransactionDAO avmTransactionDAO;
	private AVMApprovalHistoryDAO avmApprovalHistoryDAO;
	private AVMLevelDAO avmLevelDAO;
	
	private AVMSetupManager avmSetupManager;

	public AVMTransactionManager(){
		super();
		avmTransactionDAO = new MyBatisAVMTransactionDAO();
		avmApprovalHistoryDAO = new MyBatisAVMApprovalHistoryDAO();
		avmLevelDAO = new MyBatisAVMLevelDAO();
		
		avmSetupManager = new AVMSetupManager();
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

	public AVMTransaction getAVMTransactionById(UUID trxId) throws FifException{
		
		AVMTransaction avmTransaction = null;
		try {
			avmTransaction = avmTransactionDAO.getAVMTransactionById(trxId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		} 
		return avmTransaction;
	}
	
	public AVMTransaction setUpNewAVMTransaction(UUID trxId, UUID avmId,
			UUID submitterId, Object order, int trxType, int levelSequence, int requiredApproval) 
		throws FifException {
		

		int initialLevelSequence = AVMSetupManager.INITIAL_LEVEL_SEQUENCE;
		
		AVMTransaction avmTransaction = null;
		try {
			avmTransaction = new AVMTransaction();
			avmTransaction.setAvmTrxId(trxId);
			avmTransaction.setAvmId(avmId);
			avmTransaction.setLevelSequence(levelSequence);
			avmTransaction.setRemainingApproval(requiredApproval);
			avmTransaction.setSerializedData(Serialization.serializeObject(order));
			avmTransaction.setAvmTrxStatus(TransactionStatusType.IN_PROGRESS.getCode());
			avmTransaction.setAvmTimeStamp(new Date());
			avmTransaction.setTrxType(trxType);
			
			avmTransactionDAO.insertNewAVMTransaction(avmTransaction);
			
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setApproverId(submitterId);
			avmApprovalHistory.setAvmTrxId(trxId);
			avmApprovalHistory.setLevelSequence(initialLevelSequence);
			avmApprovalHistory.setAvmActionType(AVMActionType.SUBMIT_TRX.getCode());
			avmApprovalHistory.setAvmActionTimeStamp(new Date());
			avmApprovalHistoryDAO.recordSubmission(avmApprovalHistory);		
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmTransaction;
	}
	
	public AVMReturnCode cancelTransaction(UUID trxId, UUID approverId, String remarks) throws FifException {
		
		AVMReturnCode returnCode = null;
		try {
			avmTransactionDAO.updateAVMTrxStatus(trxId, TransactionStatusType.CANCELED.getCode());
			String forcedRemarks = "The transaction has been rejected";
			avmApprovalHistoryDAO.alterOtherApproversAction(trxId, AVMActionType.REJECT_DUE_TO_OCCURING_REJECTION.getCode(), 
				forcedRemarks);
//			avmTransactionDAO.updateAVMTrxStatus(trxId, TransactionStatusType.REJECTED.getCode());
			avmApprovalHistoryDAO.doTrxCancellation(trxId, approverId, remarks);
			returnCode = AVMReturnCode.TRANSACTION_IS_CANCELLED;
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

	public AVMTransaction resubmitTransaction(UUID avmTrxId, UUID submitterId,
			Object application, int levelSequence, int requiredApproval) throws FifException {
		AVMTransaction avmTransaction = null;
		try {
			avmTransaction = avmTransactionDAO.getAVMTransactionById(avmTrxId);
			int transactionStatus = TransactionStatusType.IN_PROGRESS.getCode();
			byte[] serializedData = Serialization.serializeObject(application);
			avmTransaction.setRemainingApproval(requiredApproval);
			avmTransaction.setLevelSequence(levelSequence);
			avmTransaction.setAvmTrxStatus(transactionStatus);
			avmTransaction.setSerializedData(serializedData);
			avmTransactionDAO.resubmitTransaction(avmTrxId, new Date(), requiredApproval, levelSequence, transactionStatus, serializedData);
			
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setApproverId(submitterId);
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setLevelSequence(levelSequence);
			avmApprovalHistory.setAvmActionType(AVMActionType.RESUBMIT_TRX.getCode());
			avmApprovalHistory.setAvmActionTimeStamp(new Date());
			avmApprovalHistoryDAO.recordSubmission(avmApprovalHistory);		
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmTransaction;
	}
	
	public List<AVMSummarizedTransaction> getSummarizedPendingApproval(UUID approverId)
		throws FifException {
		List<AVMSummarizedTransaction> pendingSummary = null;
		try {
			pendingSummary = avmTransactionDAO.getSummarizedPendingApproval(approverId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return pendingSummary;
	}
	
	public List<AVMSummarizedTransaction> getSummarizedPendingTransactions(UUID submitterId)
	throws FifException {
		List<AVMSummarizedTransaction> pendingSummary = null;
		try {
			pendingSummary = avmTransactionDAO.getSummarizedPendingTransactions(submitterId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return pendingSummary;
	}
	
	public List<AVMSummarizedTransaction> getSummarizedRejectedTransactions(UUID submitterId)
	throws FifException {
		List<AVMSummarizedTransaction> pendingSummary = null;
		try {
			pendingSummary = avmTransactionDAO.getSummarizedRejectedTransactions(submitterId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return pendingSummary;
	}
	
//	public List<AVMOutgoingReport> getAVMOutgoingReport(UUID submitterId, int trxType) throws FifException {
//		List<AVMOutgoingReport> outgoingReportList = null;
//		try {
//			outgoingReportList = avmTransactionDAO.getAVMOutgoingReport(submitterId, trxType);
//			for (AVMOutgoingReport outgoingReport : outgoingReportList) {
//				outgoingReport.setCompleteApproval(outgoingReport.getMinimumRequiredApproval()
//											- outgoingReport.getRemainingApproval());
//				UUID avmId = outgoingReport.getAvmId();
//				int currentLevelSequence = outgoingReport.getLevelSequence();
//				outgoingReport.setApproverList(avmSetupManager.getAVMApproversByLevel(avmId, currentLevelSequence));
//			}
//			Collections.sort(outgoingReportList, new AVMOutgoingReportComparator());
//		} catch (FifException e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
//		}
//		return outgoingReportList;
//	}

	public Map<String, List<AVMApplicationData>> getPendingTransactions(List<Integer> trxTypeList) throws FifException {
		Map<String, List<AVMApplicationData>> pendingTransactionsMap = null;
		try {
			pendingTransactionsMap = avmTransactionDAO.getPendingTransactions(trxTypeList);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return pendingTransactionsMap;
	}
	
	public Object pullApplicationData(UUID trxId) throws FifException{
		try{
			return avmTransactionDAO.pullApplicationData(trxId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public int checkAVMTransactionStatus(UUID trxId) throws FifException {
		try{
			return avmTransactionDAO.getAVMTransactionStatus(trxId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public List<AVMApplicationData> getRejectedTransactions(UUID submitterId, int trxType, String subject) throws FifException {
		try{
			return avmTransactionDAO.getSubmittedTransactions(submitterId, trxType, TransactionStatusType.REJECTED, subject);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public List<AVMApplicationData> getPendingTransactions(UUID submitterId, int trxType, String subject) throws FifException {
		try{
			return avmTransactionDAO.getSubmittedTransactions(submitterId, trxType, TransactionStatusType.IN_PROGRESS, subject);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	// add yahya
	public List<AVMOutgoingReport> getAVMOutgoingReport(UUID submitterId, int trxType, String subject, int companyId, int trxStatus, Date submitedDateFrom, Date submitedDateTo) throws FifException {
		List<AVMOutgoingReport> outgoingReportList = null;
		try {
			if (submitterId == null) {
				outgoingReportList = avmTransactionDAO.getAllAVMOutgoingReport(trxType, subject, companyId, trxStatus, submitedDateFrom, submitedDateTo);
			} else {
				outgoingReportList = avmTransactionDAO.getAVMOutgoingReport(submitterId, trxType, subject);
			}
			for (AVMOutgoingReport outgoingReport : outgoingReportList) {
				outgoingReport.setCompleteApproval(outgoingReport.getMinimumRequiredApproval()
											- outgoingReport.getRemainingApproval());
				UUID avmId = outgoingReport.getAvmId();
				int version = outgoingReport.getAvmVersionId();
				int currentLevelSequence = outgoingReport.getLevelSequence();
				outgoingReport.setApproverList(avmSetupManager.getAVMApproversByLevel(avmId, currentLevelSequence, version));
			}
			Collections.sort(outgoingReportList, new AVMOutgoingReportComparator());
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return outgoingReportList;
	}
	
	public AVMTransaction resubmitTransaction(UUID avmTrxId, UUID submitterId,
			Object application, int levelSequence, int requiredApproval, Long organizationId) throws FifException {
		AVMTransaction avmTransaction = null;
		try {
			avmTransaction = avmTransactionDAO.getAVMTransactionById(avmTrxId);
			int transactionStatus = TransactionStatusType.IN_PROGRESS.getCode();
			byte[] serializedData = Serialization.serializeObject(application);
			avmTransaction.setRemainingApproval(requiredApproval);
			avmTransaction.setLevelSequence(levelSequence);
			avmTransaction.setAvmTrxStatus(transactionStatus);
			avmTransaction.setSerializedData(serializedData);
			avmTransactionDAO.resubmitTransaction(avmTrxId, new Date(), requiredApproval, levelSequence, transactionStatus, serializedData);
			
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setApproverId(submitterId);
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setLevelSequence(levelSequence);
			avmApprovalHistory.setAvmActionType(AVMActionType.RESUBMIT_TRX.getCode());
			avmApprovalHistory.setAvmActionTimeStamp(new Date());
			avmApprovalHistory.setAvmReceivedTimeStamp(new Date());
			avmApprovalHistory.setOrganizationId(organizationId);
			avmApprovalHistoryDAO.recordSubmission(avmApprovalHistory);		
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmTransaction;
	}
	
	public AVMTransaction setUpNewAVMTransaction(UUID trxId, UUID avmId,
			UUID submitterId, Object order, int trxType, int levelSequence, int requiredApproval, int avmVersion, String subject, Long companyId) 
		throws FifException {
		

		int initialLevelSequence = AVMSetupManager.INITIAL_LEVEL_SEQUENCE;
		
		AVMTransaction avmTransaction = null;
		try {
			avmTransaction = new AVMTransaction();
			avmTransaction.setAvmTrxId(trxId);
			avmTransaction.setAvmId(avmId);
			avmTransaction.setLevelSequence(levelSequence);
			avmTransaction.setRemainingApproval(requiredApproval);
			avmTransaction.setSerializedData(Serialization.serializeObject(order));
			avmTransaction.setAvmTrxStatus(TransactionStatusType.IN_PROGRESS.getCode());
			avmTransaction.setAvmTimeStamp(new Date());
			avmTransaction.setTrxType(trxType);
			avmTransaction.setAvmVersionId(avmVersion);
			avmTransaction.setSubject(subject);
			avmTransaction.setCompanyId(companyId);
			
			avmTransactionDAO.insertNewAVMTransaction(avmTransaction);
			
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setApproverId(submitterId);
			avmApprovalHistory.setAvmTrxId(trxId);
			avmApprovalHistory.setLevelSequence(initialLevelSequence);
			avmApprovalHistory.setAvmActionType(AVMActionType.SUBMIT_TRX.getCode());
			avmApprovalHistory.setAvmActionTimeStamp(new Date());
			avmApprovalHistory.setAvmReceivedTimeStamp(new Date());
			avmApprovalHistoryDAO.recordSubmission(avmApprovalHistory);		
		} 
		catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmTransaction;
	}
	
	public List<AVMSummarizedTransaction> getAllSummarizedPendingTransactions() throws FifException {
		List<AVMSummarizedTransaction> pendingSummary = null;
		try {
			pendingSummary = avmTransactionDAO.getAllSummarizedPendingTransactions();
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return pendingSummary;
	}
	
	public List<AVMSummarizedTransaction> getSummarizedAllPendingApproval()
			throws FifException {
			List<AVMSummarizedTransaction> pendingSummary = null;
			try {
				pendingSummary = avmTransactionDAO.getSummarizedAllPendingApproval();
			} catch (FifException e) {
				logger.error(e.getMessage(), e);
				throw e;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
			}
			return pendingSummary;
		}
	
	public List<AVMSummarizedTransaction> getAllSummarizedRejectedTransactions()
			throws FifException {
				List<AVMSummarizedTransaction> pendingSummary = null;
				try {
					pendingSummary = avmTransactionDAO.getAllSummarizedRejectedTransactions();
				} catch (FifException e) {
					logger.error(e.getMessage(), e);
					throw e;
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
				}
				return pendingSummary;
			}
	
	public List<AVMApplicationData> getRejectedTransactionsByTrxType(int trxType) throws FifException {
		try{
			return avmTransactionDAO.getSubmittedTransactionsByTrxType(trxType, TransactionStatusType.REJECTED);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public List<AVMSummarizedTransaction> getSummarizedPendingApproval(UUID positionUUID, UUID roleUUID, UUID userUUID)
		throws FifException {
		List<AVMSummarizedTransaction> pendingSummary = null;
		try {
			pendingSummary = avmTransactionDAO.getSummarizedPendingApproval(positionUUID, roleUUID, userUUID);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return pendingSummary;
	}
	
	public AVMRejectionDoer getLastApproverResponseTransaction(UUID avmTrxId, int avmActionType)
		throws FifException {
		AVMRejectionDoer avmApprover = null;
		try {
			avmApprover = avmTransactionDAO.getLastApproverResponseTransaction(avmTrxId, avmActionType);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return avmApprover;
	}
	
	public int updateApplicationData(UUID avmTrxId, byte[] serializedData) throws FifException {
		try {
			return avmTransactionDAO.updateApplicationData(avmTrxId, serializedData);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public int closeTransaction(UUID avmTrxId) throws FifException {
		try {
			return avmTransactionDAO.closeTransaction(avmTrxId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public AVMReturnCode approveTransactionBySystem(UUID trxId, UUID approverId, String remarks) throws FifException {
		
		AVMReturnCode returnCode = null;
		try {
			String forcedRemarks = "The transaction has been approved by system";
			avmApprovalHistoryDAO.alterOtherApproversAction(trxId, AVMActionType.AUTOAPPROVE_BY_SYSTEM.getCode(), 
				forcedRemarks);
			avmTransactionDAO.updateAVMTrxStatus(trxId, TransactionStatusType.COMPLETED.getCode());
			avmApprovalHistoryDAO.doTrxApproved(trxId, approverId, remarks);
			returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
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
	
	public int errorTransaction(UUID avmTrxId) throws FifException {
		try {
			return avmTransactionDAO.errorTransaction(avmTrxId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
	
	public List<AVMOutgoingReport> getPendingTransaction(List<UUID> listTrxId) throws FifException {
		try {
			return avmTransactionDAO.getPendingTransaction(listTrxId);
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}
}
