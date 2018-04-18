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
import co.id.fifgroup.avm.constants.AVMApproverPriorityLevel;
import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.dao.AVMApprovalHistoryDAO;
import co.id.fifgroup.avm.dao.mybatis.mapper.AVMApprovalHistoryMapper;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprovalProcessData;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.util.StringUtil;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class MyBatisAVMApprovalHistoryDAO extends MyBatisAVMDAOHelper implements AVMApprovalHistoryDAO{

	private static final Logger logger = LoggerFactory.getLogger(MyBatisAVMApprovalHistoryDAO.class);
	
	private static final int INITIAL_LEVEL_SEQUENCE = 0;
	
	public int recordSubmission(AVMApprovalHistory avmApprovalHistory) throws FifException {
		try {
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			return mapper.doTrxSubmission(avmApprovalHistory);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public int insertMultipleNewAVMApprovalHistories(List<AVMApprovalHistory> historyList) throws FifException {
		try {
			int result = 0;
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			for (AVMApprovalHistory history : historyList) {
				result *= mapper.insertNewAVMApprovalHistory(history);
			}
			
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public UUID getTransactionSubmitter(UUID trxId) throws FifException {
		try {
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", trxId);
			Map<String, Object> resultMap = mapper.getTransactionSubmitter(parameterMap);
			if (resultMap != null) {
				return (UUID) resultMap.get("submitterId");
			}
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

//	public int doTrxCancellation(UUID avmTrxId, UUID approverId, String message) throws FifException {
//		try {
//			AVMApprovalHistoryMapper mapper = avmSqlSession.getMapper(AVMApprovalHistoryMapper.class);
//			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
//			avmApprovalHistory.setAvmTrxId(avmTrxId);
//			avmApprovalHistory.setLevelSequence(INITIAL_LEVEL_SEQUENCE);
//			avmApprovalHistory.setAvmActionTimeStamp(new Date());
//			avmApprovalHistory.setAvmActionType(AVMActionType.CANCEL_TRX.getCode());
//			avmApprovalHistory.setRemarks(message);
//			avmApprovalHistory.setApproverId(approverId);
//			
//			return mapper.doTrxCancellation(avmApprovalHistory);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
//		} 
//	}

	public AVMApprovalHistory getRecentApproversHistory(UUID avmTrxId, UUID approverId) throws FifException {
		try {
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("approverId", approverId);
			return mapper.getRecentApproversHistory(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	public int updateAVMApprovalHistory(AVMApprovalHistory approvalHistory) throws FifException {
		try {
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			return mapper.updateAVMApprovalHistory(approvalHistory);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	public int getApproversPriority(UUID avmTrxId, int levelSequence, UUID approverId) throws FifException {
		int approversPriority = AVMApproverPriorityLevel.EQUAL.getCode();
		try {
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("levelSequence", levelSequence);
			parameterMap.put("approverId", approverId);
			approversPriority = mapper.getApproversPriority(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
		return approversPriority;
	}

	public int alterOtherApproversAction(UUID avmTrxId, int actionType, String forcedApprovalRemarks) throws FifException {
		try {
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmTrxId", avmTrxId);
			parameterMap.put("avmActionType", actionType);
			parameterMap.put("avmActionTimeStamp", new Date());
			parameterMap.put("remarks", forcedApprovalRemarks);
			return mapper.alterOtherApproversAction(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	public int countApproversStillDoNothing(Map<String, Object> parameterMap) throws FifException {
		
		try {
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			return mapper.countApproversStillDoNothing(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
//	public List<AVMApplicationData> getPendingApproval(UUID approverId, int trxType) throws FifException {
//		List<AVMApplicationData> avmApplicationDataList = null;
//		try {
//			AVMApprovalHistoryMapper mapper = (AVMApprovalHistoryMapper) 
//				avmSqlSession.getMapper(AVMApprovalHistoryMapper.class);
//			Map<String, Object> parameterMap = new HashMap<String, Object>();
//			parameterMap.put("approverId", approverId);
//			parameterMap.put("trxType", trxType);
//			avmApplicationDataList = mapper.getPendingApproval(parameterMap);
//			
//			for (AVMApplicationData avmApplicationData : avmApplicationDataList) {
//				UUID avmTrxId = avmApplicationData.getAvmTrxId();
//				UUID submitterId = getTransactionSubmitter(avmTrxId);
//				avmApplicationData.setSubmitterId(submitterId);
//				avmApplicationData.setTrxType(trxType);
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
//		} 
//		return avmApplicationDataList;
//	}
	
	public AVMApprovalHistory getRecentApproversApprovalHistory(UUID trxId, UUID approverId) throws FifException{
		try{
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("avmTrxId", trxId);
			map.put("approverId", approverId);
			
			AVMApprovalHistory recentApproversHistory = mapper.getRecentApproversHistory(map);
			
			return recentApproversHistory;
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	}
	
	// add yahya
	public List<AVMApplicationData> getPendingApproval(UUID approverId, String subject, int trxType, int companyId, Date submitedDateFrom, Date submitedDateTo, UUID personUUID) throws FifException {
		List<AVMApplicationData> avmApplicationDataList = null;
		try {
			AVMApprovalHistoryMapper mapper = (AVMApprovalHistoryMapper) 
				sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			String wrappedParam = StringUtil.wrappedByWildCard(subject);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("approverId", approverId);
			parameterMap.put("subject", wrappedParam);
			parameterMap.put("trxType", trxType);
			parameterMap.put("companyId", companyId);
			parameterMap.put("submitedDateFrom", submitedDateFrom);
			parameterMap.put("submitedDateTo", submitedDateTo);
			parameterMap.put("personUUID", personUUID);
			
			avmApplicationDataList = mapper.getPendingApproval(parameterMap);
			
//			for (AVMApplicationData avmApplicationData : avmApplicationDataList) {
//				UUID avmTrxId = avmApplicationData.getAvmTrxId();
//				UUID submitterId = getTransactionSubmitter(avmTrxId);
//				avmApplicationData.setSubmitterId(submitterId);
//				avmApplicationData.setTrxType(trxType);
//			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
		return avmApplicationDataList;
	}
	
	public int doTrxCancellation(UUID avmTrxId, UUID approverId, String message) throws FifException {
		try {
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setLevelSequence(INITIAL_LEVEL_SEQUENCE);
			avmApprovalHistory.setAvmActionTimeStamp(new Date());
			avmApprovalHistory.setAvmReceivedTimeStamp(new Date());
			avmApprovalHistory.setAvmActionType(AVMActionType.CANCEL_TRX.getCode());
//			avmApprovalHistory.setAvmActionType(AVMActionType.REJECT_TRX.getCode());
			avmApprovalHistory.setRemarks(message);
			avmApprovalHistory.setApproverId(approverId);
			
			return mapper.doTrxCancellation(avmApprovalHistory);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}
	
	public List<AVMApprovalHistory> getApprovalHistoryByAVMTrxId(UUID trxId) throws FifException {
		try{
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("avmTrxId", trxId);
			
			List<AVMApprovalHistory> approvalHistoryList = mapper.getApprovalHistoryByAVMTrxId(map);
			
			return approvalHistoryList;
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		} 
	}
	
	public AVMApprovalHistory getLastApprovalHistory(UUID trxId) throws FifException{
		try{
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("avmTrxId", trxId);
			
			AVMApprovalHistory lastApproversHistory = mapper.getLastApprovalHistory(map);
			
			return lastApproversHistory;
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		} 
	}
	
	public AVMApprovalHistory getRecentApproversHistory(UUID trxId, int sequenceNumber) throws FifException{
		try{
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("avmTrxId", trxId);
			map.put("sequenceNumber", sequenceNumber);
			
			AVMApprovalHistory recentApproversHistory = mapper.getRecentApproversHistory(map);
			
			return recentApproversHistory;
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	}
	
	public List<AVMApplicationData> getPendingApproval(UUID jobUUID, List<UUID> rolesUUID, List<UUID> jobRolesUUID, 
			UUID employeeUUID, UUID deptOwnerUUID, Long organizationId, Long branchId, String subject, int trxType, Long companyId, UUID jobGroupUUID, List<Long> companiesGroup) throws FifException {
		List<AVMApplicationData> avmApplicationDataList = null;
		try {
			AVMApprovalHistoryMapper mapper = (AVMApprovalHistoryMapper) 
				sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("jobUUID", jobUUID);
			parameterMap.put("rolesUUID", rolesUUID);
			parameterMap.put("jobRolesUUID", jobRolesUUID);
			parameterMap.put("deptOwnerUUID", deptOwnerUUID);
			parameterMap.put("employeeUUID", employeeUUID);
			parameterMap.put("organizationId", organizationId);
			parameterMap.put("branchId", branchId);
			parameterMap.put("companyId", companyId);
			String paramString = StringUtil.wrappedByWildCard(subject);
			parameterMap.put("subject", paramString);
			parameterMap.put("trxType", trxType);
			parameterMap.put("jobGroupUUID", jobGroupUUID);
			parameterMap.put("companiesGroup", companiesGroup);
			avmApplicationDataList = mapper.getPendingApprovalCriteria(parameterMap);
			
			for (AVMApplicationData avmApplicationData : avmApplicationDataList) {
				UUID avmTrxId = avmApplicationData.getAvmTrxId();
				UUID submitterId = getTransactionSubmitter(avmTrxId);
				avmApplicationData.setSubmitterId(submitterId);
//				avmApplicationData.setTrxType(trxType);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
		return avmApplicationDataList;
	}
	
	public List<AVMApplicationData> getPendingApproval(UUID jobUUID, List<UUID> rolesUUID, List<UUID> jobRolesUUID, 
			UUID employeeUUID, UUID deptOwnerUUID, Long organizationId, Long branchId, String subject, int trxType, Long companyId, UUID jobGroupUUID, List<Long> companiesGroup,
			Date submitDateFrom, Date submitDateTo, UUID approverId, Integer company) throws FifException {
		List<AVMApplicationData> avmApplicationDataList = null;
		try {
			AVMApprovalHistoryMapper mapper = (AVMApprovalHistoryMapper) 
				sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("jobUUID", jobUUID);
			parameterMap.put("rolesUUID", rolesUUID);
			parameterMap.put("jobRolesUUID", jobRolesUUID);
			parameterMap.put("deptOwnerUUID", deptOwnerUUID);
			parameterMap.put("employeeUUID", employeeUUID);
			parameterMap.put("organizationId", organizationId);
			parameterMap.put("branchId", branchId);
			parameterMap.put("companyId", companyId);
			String paramString = StringUtil.wrappedByWildCard(subject);
			parameterMap.put("subject", paramString);
			parameterMap.put("trxType", trxType);
			parameterMap.put("jobGroupUUID", jobGroupUUID);
			parameterMap.put("companiesGroup", companiesGroup);
			parameterMap.put("submitDateFrom", submitDateFrom);
			parameterMap.put("submitDateTo", submitDateTo);
			parameterMap.put("approverId", approverId);
			parameterMap.put("companyTrx", company);
			avmApplicationDataList = mapper.getPendingApprovalInquiry(parameterMap);
			
			for (AVMApplicationData avmApplicationData : avmApplicationDataList) {
				UUID avmTrxId = avmApplicationData.getAvmTrxId();
				UUID submitterId = getTransactionSubmitter(avmTrxId);
				avmApplicationData.setSubmitterId(submitterId);
//				avmApplicationData.setTrxType(trxType);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
		return avmApplicationDataList;
	}

	@Override
	public List<AVMApprovalHistory> getApproverIdByAVMTrxId(UUID trxId) throws FifException {
		try{
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("avmTrxId", trxId);
			
			List<AVMApprovalHistory> approverIdyList = mapper.getApproverIdByAVMTrxId(map);
			
			return approverIdyList;
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	}

	@Override
	public List<AVMApprovalHistory> getApproverReadyByAVMTrxId(UUID trxId)
			throws FifException {
		try{
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("avmTrxId", trxId);			
			List<AVMApprovalHistory> approverList = mapper.getApproverReadyByAVMTrxId(map);
			
			return approverList;
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	}

	@Override
	public List<AVMApprovalProcessData> getApprovalProcess(int companyId,
			int trxType, int actionType, Date receivedDateFrom,
			Date receivedDateTo, List<UUID> listEmployee) throws FifException {
		try{
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("companyId", companyId);
			map.put("trxType", trxType);
			map.put("actionType", actionType);
			map.put("receivedDateFrom", receivedDateFrom);
			map.put("receivedDateTo", receivedDateTo);
			map.put("listEmployee", listEmployee);
			return mapper.getApprovalProcess(map);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	}

	@Override
	public List<AVMApprovalProcessData> getCompletedTaskByApproverId(UUID approverId,
			String subject, int trxType, int trxStatus, Date approvedDateFrom,
			Date approvedDateTo) throws FifException {
		try{
			String wrappedParam = StringUtil.wrappedByWildCard(subject);
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("approverId", approverId);
			map.put("subject", wrappedParam);
			map.put("trxType", trxType);
			map.put("trxStatus", trxStatus);
			map.put("approvedDateFrom", approvedDateFrom);
			map.put("approvedDateTo", approvedDateTo);
			List<AVMApprovalProcessData> approvalProcess = mapper.getCompletedTaskByApproverId(map);
			
			return approvalProcess;
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	}

	@Override
	public Date getLastCompletedActionApproval(UUID avmTrxId)
			throws FifException {
		try{
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("avmTrxId", avmTrxId);
			return mapper.getLastCompletedActionApproval(map);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	}

	@Override
	public int doTrxApproved(UUID avmTrxId, UUID approverId, String message)
			throws FifException {
		try {
			AVMApprovalHistoryMapper mapper = sqlSession.getMapper(AVMApprovalHistoryMapper.class);
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setLevelSequence(INITIAL_LEVEL_SEQUENCE);
			avmApprovalHistory.setAvmActionTimeStamp(new Date());
			avmApprovalHistory.setAvmReceivedTimeStamp(new Date());
			avmApprovalHistory.setAvmActionType(AVMActionType.APPROVE_TRX.getCode());
			avmApprovalHistory.setRemarks(message);
			avmApprovalHistory.setApproverId(approverId);
			
			return mapper.doTrxSubmission(avmApprovalHistory);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}
}
