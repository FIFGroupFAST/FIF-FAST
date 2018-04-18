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

import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMRuleMapping;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */
public interface AVMDAO {
	public int insertNewAVM(AVM avm) throws FifException;
	
	public int updateAVM(AVM avm) throws FifException;
	
	public List<AVM> getAVMTemplateList() throws FifException;

	public AVM getAVMById(UUID avmId) throws FifException;
	
	public List<AVM> getAVMByIdList(List<UUID> avmIdList) throws FifException;

	public List<String> getAllRuleAttributes() throws FifException;
	
	public long countAVM(String avmName) throws FifException;
	
	public long checkDuplicateRecords(String avmName) throws FifException;

	public List<AVM> searchAVM(String avmName, int offset, int limit) throws FifException;

	public List<AVM> findAVM(String avmName) throws FifException;

	public List<AVMRuleMapping> getAllRuleMappings() throws FifException;
	
	public List<AVMRuleMapping> getRuleMappingsByRuleType(int ruleType) throws FifException;
	
	// add yahya
	public int deleteAVM(UUID avmId) throws FifException;
	
}
