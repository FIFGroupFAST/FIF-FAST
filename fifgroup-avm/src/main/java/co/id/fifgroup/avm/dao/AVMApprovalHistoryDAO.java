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

package co.id.fifgroup.avm.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprovalProcessData;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMApprovalHistoryDAO{

	public int recordSubmission(AVMApprovalHistory avmApprovalHistory) throws FifException;

	public int insertMultipleNewAVMApprovalHistories(List<AVMApprovalHistory> historyList) throws FifException;

	public UUID getTransactionSubmitter(UUID trxId) throws FifException;

	public int doTrxCancellation(UUID avmTrxId, UUID approverId, String message) throws FifException;

	public AVMApprovalHistory getRecentApproversHistory(UUID trxId, UUID approverId) throws FifException;

	public int updateAVMApprovalHistory(AVMApprovalHistory approvalHistory) throws FifException;

	public int getApproversPriority(UUID avmTrxId, int levelSequence, UUID approverId) throws FifException;

	public int alterOtherApproversAction(UUID avmTrxId, int actionType, String forcedApprovalRemarks) throws FifException;

	public int countApproversStillDoNothing(Map<String, Object> parameterMap) throws FifException;
	
	public List<AVMApplicationData> getPendingApproval(UUID approverId, String subject, int trxType, int companyId, Date submitedDateFrom, Date submitedDateTo, UUID personUUID) throws FifException;
	
	public AVMApprovalHistory getRecentApproversApprovalHistory(UUID trxId, UUID approverId) throws FifException;
	
	//add yahya
	public List<AVMApprovalHistory> getApprovalHistoryByAVMTrxId(UUID trxId) throws FifException;
	
	public AVMApprovalHistory getLastApprovalHistory(UUID trxId) throws FifException;
	
	public AVMApprovalHistory getRecentApproversHistory(UUID trxId, int sequenceNumber) throws FifException;
	
	public List<AVMApplicationData> getPendingApproval(UUID jobUUID, List<UUID> rolesUUID, List<UUID> jobRolesUUID, 
			UUID employeeUUID, UUID deptOwnerUUID, Long organizationId, Long branchId, String subject, int trxType, Long companyId, UUID jobGroupUUID, List<Long> companiesGroup) throws FifException;
	
	public List<AVMApplicationData> getPendingApproval(UUID jobUUID, List<UUID> rolesUUID, List<UUID> jobRolesUUID, 
			UUID employeeUUID, UUID deptOwnerUUID, Long organizationId, Long branchId, String subject, int trxType, Long companyId, UUID jobGroupUUID, List<Long> companiesGroup,
			Date submitDateFrom, Date submitDateTo, UUID approverId, Integer company) throws FifException;
	
	public List<AVMApprovalHistory> getApproverIdByAVMTrxId(UUID trxId) throws FifException;
	
	public List<AVMApprovalHistory> getApproverReadyByAVMTrxId(UUID trxId) throws FifException;
	
	public List<AVMApprovalProcessData> getApprovalProcess(int companyId, int trxType, int actionType, Date receivedDateFrom, Date receivedDateTo, List<UUID> listEmployee) throws FifException;
	
	public List<AVMApprovalProcessData> getCompletedTaskByApproverId(UUID approverId, String subject, int trxType, int trxStatus, Date approvedDateFrom, Date approvedDateTo) throws FifException;
	
	public Date getLastCompletedActionApproval(UUID avmTrxId) throws FifException;
	
	public int doTrxApproved(UUID avmTrxId, UUID approverId, String message) throws FifException;
}
