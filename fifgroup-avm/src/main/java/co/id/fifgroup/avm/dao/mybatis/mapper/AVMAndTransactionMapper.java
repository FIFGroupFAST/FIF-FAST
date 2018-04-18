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
import java.util.UUID;

import co.id.fifgroup.avm.domain.AVMAndTransactionMapping;


/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMAndTransactionMapper {

	public int insertNewMapping(AVMAndTransactionMapping mapping);

	public void deleteMappedAVMAndTrxTypeList(Map<String, Object> parameterMap);

	public List<Integer> getMappedTrxTypeList(UUID avmId);
	
	public List<AVMAndTransactionMapping> getAllAVMAndTransactionMappings();
	
	
	//add yahya
	public AVMAndTransactionMapping getAVMByTrxType(int trxType);
	
	public void updateMappedAVMAndTrxTypeList(Map<String, Object> parameterMap);
}
