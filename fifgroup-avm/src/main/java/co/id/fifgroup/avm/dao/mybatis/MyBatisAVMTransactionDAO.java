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

package co.id.fifgroup.avm.dao.mybatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.constants.AVMActionType;
import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.constants.TransactionStatusType;
import co.id.fifgroup.avm.dao.AVMTransactionDAO;
import co.id.fifgroup.avm.dao.mybatis.mapper.AVMTransactionMapper;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.domain.AVMRejectionDoer;
import co.id.fifgroup.avm.domain.AVMSummarizedTransaction;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.util.Serialization;
import co.id.fifgroup.avm.util.StringUtil;


/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class MyBatisAVMTransactionDAO extends MyBatisAVMDAOHelper implements AVMTransactionDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(MyBatisAVMTransactionDAO.class);

	public int insertNewAVMTransaction(AVMTransaction avmTransaction)
			throws FifException {
		try {
			AVMTransactionMapper mapper = (AVMTransactionMapper) sqlSession.getMapper(AVMTransactionMapper.class);
			return mapper.insertNewAVMTransaction(avmTransaction);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	public int updateTrxStatus(UUID avmTrxId, int status) throws FifException {
		try {
			AVMTransactionMapper mapper = (AVMTransactionMapper) sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("status", status);
			return mapper.updateTrxStatus(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public AVMTransaction getAVMTransactionById(UUID avmTrxId) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			return mapper.getAVMTransactionById(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public int decrementTrxRemainingApproval(UUID avmTrxId, int currentRemainingApproval) throws FifException {

		int result = 0;
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("remainingApproval", currentRemainingApproval);
			result = mapper.updateTransaction(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
		return result;
	}
	
	public int updateAVMTrxStatus(UUID avmTrxId, int trxStatus) throws FifException {

		int result = 0;
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("status", trxStatus);
			result = mapper.updateTransaction(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
		return result;
	}
	
	public int changeCurrentAVMTrxLevel(UUID avmTrxId, int nextLevel, int nextNumberOfApproval) throws FifException {

		int result = 0;
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("levelSequence", nextLevel);
			parameterMap.put("remainingApproval", nextNumberOfApproval);
			parameterMap.put("avmTrxId", avmTrxId);
			result = mapper.updateTransaction(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
		return result;
	}
	
	public int resubmitTransaction(UUID avmTrxId, Date avmTimestamp, int requiredApproval, int levelSequence, int trxStatus, byte[] serializedData) throws FifException {

		int result = 0;
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("avmTimeStamp", avmTimestamp);
			parameterMap.put("remainingApproval", requiredApproval);
			parameterMap.put("levelSequence", levelSequence);
			parameterMap.put("status", trxStatus);
			parameterMap.put("serializedData", serializedData);
			result = mapper.updateTransaction(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
		return result;
	}

	public List<AVMSummarizedTransaction> getSummarizedPendingApproval(UUID approverId) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("approverId", approverId);
			
			return mapper.getSummarizedPendingApproval(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public List<AVMSummarizedTransaction> getSummarizedPendingTransactions(
			UUID submitterId) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("submitterId", submitterId);
			return mapper.getSummarizedPendingTransactions(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public List<AVMSummarizedTransaction> getSummarizedRejectedTransactions(UUID submitterId) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("submitterId", submitterId);
			return mapper.getSummarizedRejectedTransactions(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public Map<String, List<AVMApplicationData>> getPendingTransactions(
			List<Integer> trxTypeList) throws FifException {
		try {
			Map<String, List<AVMApplicationData>> pendingTransactionsMap = new HashMap<String, List<AVMApplicationData>>();
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			for (int trxType : trxTypeList) {
				List<AVMApplicationData> pendingTransactionsPerTrxType = mapper
						.getPendingTransactions(trxType);
				if (pendingTransactionsPerTrxType != null
						&& !pendingTransactionsPerTrxType.isEmpty()) {
					pendingTransactionsMap.put(String.valueOf(trxType),
							pendingTransactionsPerTrxType);
					// avmApplicationDataList.addAll();
				}
			}
			return pendingTransactionsMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public List<AVMOutgoingReport> getAVMOutgoingReport(UUID submitterId,
			int trxType, String subject) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("submitterId", submitterId);
			subject = StringUtil.wrappedByWildCard(subject);
			parameterMap.put("trxType", trxType);
			parameterMap.put("subject", subject);
			return mapper.getAVMOutgoingReport(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(),
					e.getMessage());
		}
	}

	public List<AVMApplicationData> getSubmittedTransactions(UUID submitterId, int trxType, TransactionStatusType transactionStatus, String subject) throws FifException {
		try{
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			String wrappedText = StringUtil.wrappedByWildCard(subject);
			logger.debug("submitterId : " + submitterId);
			parameterMap.put("submitterId", submitterId);
			parameterMap.put("trxType", trxType);
			parameterMap.put("trxStatus", transactionStatus.getCode());
			parameterMap.put("submitActionType", AVMActionType.SUBMIT_TRX.getCode());
			parameterMap.put("resubmitActionType", AVMActionType.RESUBMIT_TRX.getCode());
			parameterMap.put("subject", wrappedText);
			List<AVMApplicationData> resultList = mapper.getSubmittedTransactions(parameterMap);
			if (transactionStatus.equals(TransactionStatusType.REJECTED)) {
				for (int i = 0; i < resultList.size(); i++) {	
					AVMApplicationData applicationData = resultList.get(i);
					parameterMap.put("avmTrxId", applicationData.getAvmTrxId());
					parameterMap.put("avmActionType", AVMActionType.REJECT_TRX.getCode());
					AVMRejectionDoer avmRejectionDoer = mapper.getApproverWhoDidRejection(parameterMap); 
					applicationData.setSubmitterId(avmRejectionDoer.getApproverId());
					applicationData.setActionTime(avmRejectionDoer.getAvmActionTimeStamp());
					applicationData.setRemarks(avmRejectionDoer.getRemarks());
					applicationData.setSequenceNumber(avmRejectionDoer.getSequenceNumber());
//					applicationData.setTrxType(trxType);
				}
			}
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e.getMessage());
		}
	}
	
	public int getAVMTransactionStatus(UUID avmTrxId) throws FifException {
		try{
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			
			parameterMap.put("avmTrxId", avmTrxId);
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			int avmTransactionStatus = mapper.getAVMTransactionStatus(parameterMap);
			return avmTransactionStatus;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e.getMessage());
		}
	}
	
	public Object pullApplicationData(UUID avmTrxId) throws FifException {
		try{
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			
			AVMTransaction resultMap = mapper.getApplicationData(parameterMap); 
			Object applicationData = Serialization.deserializedObject(resultMap.getSerializedData());
			
			mapper.removeApplicationData(parameterMap);
			return applicationData;
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	}
	
	public Map<String, Object> getTransactionSubmitter(UUID avmTrxId) throws FifException{
		try{
			AVMTransactionMapper trxMapper = sqlSession.getMapper(AVMTransactionMapper.class);
			
			Map<String,Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("avmActionType", AVMActionType.SUBMIT_TRX.getCode());
			return trxMapper.getTransactionSubmitter(parameterMap);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	}
	
	// add yahya
	public List<AVMSummarizedTransaction> getAllSummarizedPendingTransactions() throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			return mapper.getAllSummarizedPendingTransactions(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public List<AVMSummarizedTransaction> getSummarizedAllPendingApproval() throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
//			parameterMap.put("approverId", approverId);
			
			return mapper.getSummarizedAllPendingApproval(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public List<AVMSummarizedTransaction> getAllSummarizedRejectedTransactions() throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			return mapper.getAllSummarizedRejectedTransactions(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public List<AVMApplicationData> getSubmittedTransactionsByTrxType(int trxType, TransactionStatusType transactionStatus) throws FifException {
		try{
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("trxType", trxType);
			parameterMap.put("trxStatus", transactionStatus.getCode());
			parameterMap.put("submitActionType", AVMActionType.SUBMIT_TRX.getCode());
			parameterMap.put("resubmitActionType", AVMActionType.RESUBMIT_TRX.getCode());
			List<AVMApplicationData> resultList = mapper.getSubmittedTransactionsByTrxType(parameterMap);
			if (transactionStatus.equals(TransactionStatusType.REJECTED)) {
				for (int i = 0; i < resultList.size(); i++) {	
					AVMApplicationData applicationData = resultList.get(i);
					parameterMap.put("avmTrxId", applicationData.getAvmTrxId());
					parameterMap.put("avmActionType", AVMActionType.REJECT_TRX.getCode());
					AVMRejectionDoer avmRejectionDoer = mapper.getApproverWhoDidRejection(parameterMap); 
					applicationData.setSubmitterId(avmRejectionDoer.getApproverId());
					applicationData.setActionTime(avmRejectionDoer.getAvmActionTimeStamp());
					applicationData.setRemarks(avmRejectionDoer.getRemarks());
					applicationData.setTrxType(trxType);
				}
			}
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e.getMessage());
		}
	}
	
	public List<AVMOutgoingReport> getAllAVMOutgoingReport(int trxType, String subject, int companyId, int trxStatus, Date submitedDateFrom, Date submitedDateTo) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			subject = StringUtil.wrappedByWildCard(subject);
			parameterMap.put("trxType", trxType);
			parameterMap.put("subject", subject);
			parameterMap.put("companyId", companyId);
			parameterMap.put("trxStatus", trxStatus);
			parameterMap.put("submitedDateFrom", submitedDateFrom);
			parameterMap.put("submitedDateTo", submitedDateTo);
			return mapper.getAllAVMOutgoingReport(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(),
					e.getMessage());
		}
	}

	@Override
	public List<AVMSummarizedTransaction> getSummarizedPendingApproval(
			UUID positionUUID, UUID roleUUID, UUID userUUID)
			throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("positionUUID", positionUUID);
			parameterMap.put("roleUUID", roleUUID);
			parameterMap.put("userUUID", userUUID);
			
			return mapper.getSummarizedPendingApprovalCriteria(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	@Override
	public AVMRejectionDoer getLastApproverResponseTransaction(UUID avmTrxId, int avmActionType)
			throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("avmActionType", avmActionType);
			return mapper.getApproverWhoDidRejection(parameterMap); 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(
					AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	@Override
	public int updateApplicationData(UUID avmTrxId, byte[] serializedData)
			throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("serializedData", serializedData);
			return mapper.updateApplicationData(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	@Override
	public int closeTransaction(UUID avmTrxId) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			return mapper.closeTransaction(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	@Override
	public int errorTransaction(UUID avmTrxId) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			return mapper.errorTransaction(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	@Override
	public int updateRemainingApproval(UUID avmTrxId, Integer remainingApproval) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("remainingApproval", remainingApproval);
			return mapper.updateRemainingApproval(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	@Override
	public List<AVMOutgoingReport> getPendingTransaction(List<UUID> listTrxId)
			throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("listTrxId", listTrxId);
			return mapper.getPendingTransaction(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
//	(GAL - [15091510574968] Perbaikan NPK double setelah approve) 
	@Override
	public int checkStatusIsApproved(UUID avmTrxId) throws FifException {
		try {
			AVMTransactionMapper mapper = sqlSession.getMapper(AVMTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			return mapper.checkStatusIsApproved(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
//	
	
}
