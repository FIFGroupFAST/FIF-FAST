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

import java.util.List;
import java.util.UUID;

import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMApproverDAO{

	public int insertMultipleNewAVMApprover(List<AVMApprover> approverList) throws FifException;
	
	public List<AVMApprover> getTopPriorityApproverList(UUID avmId, int levelSequence) throws FifException;
	
//	public List<AVMApprover> getApproverList(UUID avmId, int levelSequence, int priority) throws FifException;
	
//	public List<AVMApprover> getApproverList(UUID avmId, int levelSequence) throws FifException;
	
	public List<AVMApprover> getApproverList(UUID avmId) throws FifException;

	public int deleteAVMApprovers(UUID avmId) throws FifException;
	
	// update yahya add version
	public List<AVMApprover> getTopPriorityApproverList(UUID avmId, int levelSequence, int version) throws FifException;
	
	public List<AVMApprover> getApproverList(UUID avmId, int levelSequence, int priority, int version) throws FifException;
	
	public List<AVMApprover> getApproverList(UUID avmId, int levelSequence, int avmVersionId) throws FifException;
	
	public List<AVMApprover> getApproverList(UUID avmId, int version) throws FifException;

	public int deleteAVMApprovers(UUID avmId, int avmVersionId) throws FifException;
	
	public int insertMultipleNewAVMApprover(List<AVMApprover> approverList, AVMVersions avmVersions) throws FifException;
}
