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

import java.util.Date;
import java.util.List;
import java.util.Map;

import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprovalProcessData;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMApprovalHistoryMapper {

	//insert
	public int insertNewAVMApprovalHistory(AVMApprovalHistory avmApprovalHistory);
	public int doTrxSubmission(AVMApprovalHistory avmApprovalHistory);
	public int doTrxCancellation(AVMApprovalHistory avmApprovalHistory);
	
	//update
	public int updateAVMApprovalHistory(AVMApprovalHistory approvalHistory);
	public int alterOtherApproversAction(Map<String, Object> parameterMap);
	
	//select
	public Map<String, Object> getTransactionSubmitter(Map<String, Object> parameterMap);
	public AVMApprovalHistory getRecentApproversHistory(Map<String, Object> parameterMap);
	public int getApproversPriority(Map<String, Object> parameterMap);
	public int countApproversStillDoNothing(Map<String, Object> parameterMap);
	public List<AVMApplicationData> getPendingApproval(Map<String, Object> parameterMap);
	
	//add yahya
	public List<AVMApprovalHistory> getApprovalHistoryByAVMTrxId(Map<String, Object> parameterMap);
	public AVMApprovalHistory getLastApprovalHistory(Map<String, Object> parameterMap);
	public List<AVMApplicationData> getPendingApprovalCriteria(Map<String, Object> parameterMap);	
	public List<AVMApprovalHistory> getApproverIdByAVMTrxId(Map<String, Object> parameterMap);
	public List<AVMApprovalHistory> getApproverReadyByAVMTrxId(Map<String, Object> parameterMap);
	public List<AVMApprovalProcessData> getApprovalProcess(Map<String, Object> parameterMap);
	public List<AVMApprovalProcessData> getCompletedTaskByApproverId(Map<String, Object> parameterMap);
	public Date getLastCompletedActionApproval(Map<String, Object> parameterMap);	
	public List<AVMApplicationData> getPendingApprovalInquiry(Map<String, Object> parameterMap);

}
