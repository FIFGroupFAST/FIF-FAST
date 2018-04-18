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

import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMRuleLookupDetail;
import co.id.fifgroup.avm.domain.AVMRuleMapping;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public interface AVMMapper {
	//insert
	public int insertNewAVM(AVM avm);
	
	//select
	public AVM getAVMById(Map<String, Object> parameterMap);
	public List<AVM> getAVMByIdList(Map<String, Object> parameterMap);
	public List<String> getAllRuleAttributes();
	public List<AVM> searchAVM(String avmName, RowBounds rowBounds);
	public long countAVM(String avmName);
	public List<AVM> findAVM(String avmName);
	public List<AVMRuleMapping> getAllRuleMappings();
	public List<AVMRuleLookupDetail> getAVMRuleLookupDetail(
			Map<String, Object> parameterMap);
	public long checkDuplicateRecords(String avmName);
	
	public List<AVMRuleMapping> getRuleMappingsByRuleType(Map<String, Object> parameterMap);
	
	public List<AVM> getAVMTemplateList();
	
	//update
	public int updateAVM(AVM avm);
	
	public int deleteAVM(Map<String, Object> parameterMap);
	
}
