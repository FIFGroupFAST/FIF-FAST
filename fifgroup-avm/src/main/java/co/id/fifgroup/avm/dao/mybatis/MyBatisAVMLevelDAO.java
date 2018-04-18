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
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.dao.AVMLevelDAO;
import co.id.fifgroup.avm.dao.mybatis.mapper.AVMLevelMapper;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class MyBatisAVMLevelDAO extends MyBatisAVMDAOHelper implements AVMLevelDAO{

	private static final Logger logger = LoggerFactory.getLogger(MyBatisAVMLevelDAO.class);
	
	public int insertNewAVMLevel(AVMLevel avmLevel) throws FifException {
		try {
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmLevel.getAvmId());
			Integer lastLevelSequence = mapper.getTheLastLevelSequence(parameterMap);
			int levelSequence = 0;
			if (lastLevelSequence != null) {
				levelSequence = lastLevelSequence + 1;
			}
			avmLevel.setLevelSequence(levelSequence);
			return mapper.insertNewAVMLevel(avmLevel);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}
	
	public int insertNewMultipleAVMLevels(List<AVMLevel> avmLevels) throws FifException {
		try {
			int result = 0;
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			for (AVMLevel avmLevel : avmLevels) {
				result *= mapper.insertNewAVMLevel(avmLevel);
			}
			
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public AVMLevel getAVMLevel(UUID avmId, int levelSequence) throws FifException {
		try {
			Map<String, Object> parameterMapforAVMLevel = new HashMap<String, Object>();
			parameterMapforAVMLevel.put("avmId", avmId);
			parameterMapforAVMLevel.put("levelSequence", levelSequence);
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			return mapper.getAVMLevel(parameterMapforAVMLevel);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public int getTheLastLevelSequence(UUID avmId) throws FifException {
		try {
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			Map <String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			return mapper.getTheLastLevelSequence(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}
	
	public AVMLevel getAVMLevelByAVMIdAndLevelSequence(UUID avmId, int levelSequence) throws FifException {
		try {
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("levelSequence", levelSequence);
			AVMLevel avmLevel = (AVMLevel) mapper.getAVMLevel(parameterMap);
			
			return avmLevel;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public List<AVMLevel> getAVMLevels(UUID avmId) throws FifException {
		try {
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			
			return mapper.getAVMLevels(parameterMap);
		}  catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	// add yahya
	public int deleteAVMLevels(UUID avmId, int avmVersionId) throws FifException {
		try {
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("avmVersionId", avmVersionId);
			return mapper.deleteLevelList(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	
	public AVMLevel getAVMLevelByAVMIdAndLevelSequence(UUID avmId, int levelSequence, int avmVersionId) throws FifException {
		try {
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("levelSequence", levelSequence);
			parameterMap.put("avmVersionId", avmVersionId);
			AVMLevel avmLevel = (AVMLevel) mapper.getAVMLevel(parameterMap);
			
			return avmLevel;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public List<AVMLevel> getAVMLevels(UUID avmId, int avmVersionId) throws FifException {
		try {
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("avmVersionId", avmVersionId);
			
			return mapper.getAVMLevels(parameterMap);
		}  catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	@Override
	public int getTheLastLevelSequence(UUID avmId, int avmVersionId)
			throws FifException {
		try {
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			Map <String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("avmVersionId", avmVersionId);
			return mapper.getTheLastLevelSequence(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	@Override
	public int insertNewMultipleAVMLevels(List<AVMLevel> avmLevels,
			AVMVersions avmVersions) throws FifException {
		try {
			int result = 0;
			AVMLevelMapper mapper = sqlSession.getMapper(AVMLevelMapper.class);
			for (AVMLevel avmLevel : avmLevels) {
				avmLevel.setAvmId(avmVersions.getAvmId());
				avmLevel.setAvmVersionId(avmVersions.getAvmVersionId());
				result *= mapper.insertNewAVMLevel(avmLevel);
			}
			
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
}
