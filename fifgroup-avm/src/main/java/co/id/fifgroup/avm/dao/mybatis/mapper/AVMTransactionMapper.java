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

package co.id.fifgroup.avm.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.domain.AVMRejectionDoer;
import co.id.fifgroup.avm.domain.AVMSummarizedTransaction;
import co.id.fifgroup.avm.domain.AVMTransaction;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMTransactionMapper {

	//insert
	public int insertNewAVMTransaction(AVMTransaction avmTransaction);
	
	//select
	public AVMTransaction getAVMTransactionById(Map<String, Object> parameterMap);
	public List<AVMTransaction> getAllSerializedData();
	public List<AVMSummarizedTransaction> getSummarizedPendingApproval(Map<String, Object> parameterMap);
	public List<AVMSummarizedTransaction> getSummarizedPendingTransactions(Map<String, Object> parameterMap);
	public List<AVMApplicationData> getPendingTransactions(int trxType);
	public List<AVMSummarizedTransaction> getSummarizedRejectedTransactions(Map<String, Object> parameterMap);
	public int getAVMTransactionStatus(Map<String, Object> parameterMap);
	public AVMTransaction getApplicationData(Map<String, Object> parameterMap);
	public List<AVMApplicationData> getSubmittedTransactions(Map<String, Object> parameterMap);
	public List<AVMOutgoingReport> getAVMOutgoingReport(Map<String, Object> parameterMap);
//	public List<AVMApplicationData> getRejectedTransactions(Map<String, Object> parameterMap);
	public AVMRejectionDoer getApproverWhoDidRejection(Map<String, Object> parameterMap);
	public Map<String,Object> getTransactionSubmitter(Map<String, Object> parameterMap);
	
	//add yahya
	public List<AVMSummarizedTransaction> getAllSummarizedPendingTransactions(Map<String, Object> parameterMap);
	public List<AVMSummarizedTransaction> getSummarizedAllPendingApproval(Map<String, Object> parameterMap);
	public List<AVMSummarizedTransaction> getAllSummarizedRejectedTransactions(Map<String, Object> parameterMap);
	public List<AVMApplicationData> getSubmittedTransactionsByTrxType(Map<String, Object> parameterMap);
	public List<AVMOutgoingReport> getAllAVMOutgoingReport(Map<String, Object> parameterMap);
	public List<AVMSummarizedTransaction> getSummarizedPendingApprovalCriteria(Map<String, Object> parameterMap);
	public int updateApplicationData(Map<String, Object> parameterMap);
	public List<AVMOutgoingReport> getPendingTransaction(Map<String, Object> parameterMap);
	//-

	//update
	public int updateTrxStatus(Map<String, Object> parameterMap);
	public int updateTransaction(Map<String, Object> parameterMap);
	public int changeLevel(Map<String, Object> parameterMap);
	public int decrementRemainingApproval(Map<String, Object> parameterMap);
	public int resubmitTransaction(AVMTransaction avmTransaction);
	public int closeTransaction(Map<String, Object> parameterMap);
	public int removeApplicationData(Map<String, Object> parameterMap);
	public int errorTransaction(Map<String, Object> parameterMap);	
	public int updateRemainingApproval(Map<String, Object> parameterMap);
	
//	cek status trxId (GAL - [15091510574968] Perbaikan NPK double setelah approve) 
	public int checkStatusIsApproved(Map<String, Object> parameterMap);
}
