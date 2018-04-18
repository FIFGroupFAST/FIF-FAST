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

import co.id.fifgroup.avm.constants.TransactionStatusType;
import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.domain.AVMRejectionDoer;
import co.id.fifgroup.avm.domain.AVMSummarizedTransaction;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMTransactionDAO{

	public int insertNewAVMTransaction(AVMTransaction avmTransaction) throws FifException;
	
	public int updateTrxStatus (UUID avmTrxId, int status) throws FifException;
	
	public AVMTransaction getAVMTransactionById(UUID avmTrxId) throws FifException;
	
	public int decrementTrxRemainingApproval(UUID avmTrxId, int currentRemainingApproval) throws FifException;
	
	public int updateAVMTrxStatus(UUID avmTrxId, int trxStatus) throws FifException;
	
	public int changeCurrentAVMTrxLevel(UUID avmTrxId, int nextLevel, int nextNumberOfApproval) throws FifException;
	
	public int resubmitTransaction(UUID avmTrxId, Date avmTimestamp, int requiredApproval, int levelSequence, int trxStatus, byte[] serializedData) throws FifException;
	
	public List<AVMSummarizedTransaction> getSummarizedPendingApproval(UUID approverId) throws FifException;
	
	public List<AVMSummarizedTransaction> getSummarizedPendingTransactions(UUID submitterId) throws FifException;
	
	public List<AVMSummarizedTransaction> getSummarizedRejectedTransactions(UUID submitterId) throws FifException;
	
	public Map<String, List<AVMApplicationData>> getPendingTransactions(List<Integer> trxTypeList) throws FifException;
	
	public List<AVMOutgoingReport> getAVMOutgoingReport(UUID submitterId, int trxType, String subject) throws FifException;
	
	public List<AVMApplicationData> getSubmittedTransactions(UUID submitterId, int trxType, TransactionStatusType transactionStatus, String subject) throws FifException;
	
	public int getAVMTransactionStatus(UUID avmTrxId) throws FifException;
	
	public Object pullApplicationData(UUID avmTrxId) throws FifException;
	
	public Map<String, Object> getTransactionSubmitter(UUID avmTrxId) throws FifException;
	
	// add yahya
	public List<AVMSummarizedTransaction> getAllSummarizedPendingTransactions() throws FifException;
	
	public List<AVMSummarizedTransaction> getSummarizedAllPendingApproval() throws FifException;
	
	public List<AVMSummarizedTransaction> getAllSummarizedRejectedTransactions() throws FifException;
	
	public List<AVMApplicationData> getSubmittedTransactionsByTrxType(int trxType, TransactionStatusType transactionStatus) throws FifException;
	
	public List<AVMOutgoingReport> getAllAVMOutgoingReport(int trxType, String subject, int companyId, int trxStatus, Date submitedDateFrom, Date submitedDateTo) throws FifException;
	
	public List<AVMSummarizedTransaction> getSummarizedPendingApproval(UUID positionUUID, UUID roleUUID, UUID userUUID) throws FifException;
	
	public AVMRejectionDoer getLastApproverResponseTransaction(UUID avmTrxId, int avmActionType) throws FifException;
	
	public int updateApplicationData(UUID avmTrxId, byte[] serializedData) throws FifException;
	
	public int closeTransaction(UUID avmTrxId) throws FifException;
	
	public int errorTransaction(UUID avmTrxId) throws FifException;
	
	public int updateRemainingApproval(UUID avmTrxId, Integer remainingApproval) throws FifException;
	
	public List<AVMOutgoingReport> getPendingTransaction(List<UUID> listTrxId) throws FifException;
	
//	cek status trxId (GAL - [15091510574968] Perbaikan NPK double setelah approve) 
//	 yang bisa di proses jika statusnya blm approved/reject
	public int checkStatusIsApproved(UUID avmTrxId) throws FifException;
}
