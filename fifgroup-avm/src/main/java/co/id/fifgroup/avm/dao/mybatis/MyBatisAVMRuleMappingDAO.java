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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.dao.AVMRuleMappingDAO;
import co.id.fifgroup.avm.dao.mybatis.mapper.AVMRuleMappingMapper;
import co.id.fifgroup.avm.domain.AVMRuleLookupDetail;
import co.id.fifgroup.avm.domain.AVMRuleMapping;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class MyBatisAVMRuleMappingDAO extends MyBatisAVMDAOHelper implements AVMRuleMappingDAO{

	private static final Logger logger = LoggerFactory.getLogger(MyBatisAVMRuleMappingDAO.class);
	
	public AVMRuleMapping getAVMRuleMapping(String attributeLabel) throws FifException {
		
		try {
			AVMRuleMappingMapper mapper = sqlSession.getMapper(AVMRuleMappingMapper.class);
			return mapper.getAVMRuleMapping(attributeLabel);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public List<AVMRuleLookupDetail> getRuleLookupDetails(String lookupHeaderId) throws FifException {
		try{
			AVMRuleMappingMapper mapper = sqlSession.getMapper(AVMRuleMappingMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("lookupHeaderId", lookupHeaderId);
			return mapper.getAVMRuleLookupDetails(parameterMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), ex);
		}
	} 
	
	public AVMRuleLookupDetail getRuleLookupDetail(String lookupHeaderId, String lookupValue) throws FifException {
		
		try {
			List<AVMRuleLookupDetail> result;
			AVMRuleMappingMapper mapper = sqlSession.getMapper(AVMRuleMappingMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("lookupHeaderId", lookupHeaderId);
			parameterMap.put("lookupValue", lookupValue);
			result = mapper.getAVMRuleLookupDetails(parameterMap);
			return result.get(0);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	} 
}
