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

import co.id.fifgroup.avm.domain.AVMAndTransactionMapping;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMAndTransactionMappingDAO {
	
	public int mapAVMAndTransaction(UUID avmId, int trxType) throws FifException;
	
	public void mapAVMAndTransactionTypeList(UUID avmId, List<Integer> trxTypeList) throws FifException;

	public List<AVMAndTransactionMapping> getAllAVMAndTransactionMappings() throws FifException;

	public void deleteMappedAVMAndTrxTypeList(UUID avmId, List<Integer> trxTypeList)  throws FifException;

	public List<Integer> getMappedTrxTypeList(UUID avmId) throws FifException;
	
	// add yahya
	public AVMAndTransactionMapping getAVMByTrxType(int trxType) throws FifException;
	
	public void updateMappedAVMAndTrxTypeList(UUID avmId, List<Integer> trxTypeList)  throws FifException;
}
