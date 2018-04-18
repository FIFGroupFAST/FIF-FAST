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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.dao.AVMDAO;
import co.id.fifgroup.avm.dao.mybatis.mapper.AVMMapper;
import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMRuleMapping;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.util.StringUtil;


/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class MyBatisAVMDAO extends MyBatisAVMDAOHelper implements AVMDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(MyBatisAVMDAO.class);
	
	public int insertNewAVM(AVM avm) throws FifException {
		
		try {
			AVMMapper avmMapper = sqlSession.getMapper(AVMMapper.class);
			return avmMapper.insertNewAVM(avm);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public int updateAVM(AVM avm) throws FifException {
		
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			return mapper.updateAVM(avm);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public List<AVM> getAVMTemplateList() throws FifException {		
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			return mapper.getAVMTemplateList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

//	public AVM getAVMById(UUID avmId) throws FifException{
//		try {
//			AVMMapper mapper = avmSqlSession.getMapper(AVMMapper.class);
//			Map<String, Object> parameterMap = new HashMap<String, Object>();
//			parameterMap.put("avmId", avmId);
//			return (AVM) mapper.getAVMById(parameterMap);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
//		}
//	}
	
	public List<AVM> getAVMByIdList(List<UUID> avmIdList) throws FifException {
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmIdList", avmIdList);
			return mapper.getAVMByIdList(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public List<String> getAllRuleAttributes() throws FifException {
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			return mapper.getAllRuleAttributes();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public long countAVM(String avmName) throws FifException {
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
//			String wrappedParam = StringUtil.wrappedByWildCard(avmName);
			return mapper.countAVM(avmName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}
	
	public long checkDuplicateRecords(String avmName) throws FifException {
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			
			String wrappedParam = StringUtil.wrappedByWildCard(avmName);
			return mapper.countAVM(wrappedParam);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public List<AVM> searchAVM(String avmName, int offset, int limit) throws FifException {
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			String wrappedParam = StringUtil.wrappedByWildCard(avmName);
			return mapper.searchAVM(wrappedParam,
					new RowBounds(offset, limit));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	public List<AVM> findAVM(String avmName) throws FifException {
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			
			String wrappedParam = StringUtil.wrappedByWildCard(avmName);
			return mapper.findAVM(wrappedParam);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public List<AVMRuleMapping> getAllRuleMappings() throws FifException {
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			return mapper.getAllRuleMappings();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	// add yahya
	public AVM getAVMById(UUID avmId) throws FifException{
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("date", new Date());
			return (AVM) mapper.getAVMById(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	@Override
	public int deleteAVM(UUID avmId) throws FifException {
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			return mapper.deleteAVM(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	@Override
	public List<AVMRuleMapping> getRuleMappingsByRuleType(int ruleType)
			throws FifException {
		try {
			AVMMapper mapper = sqlSession.getMapper(AVMMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("ruleType", ruleType);
			return mapper.getRuleMappingsByRuleType(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
}
