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
import co.id.fifgroup.avm.dao.AVMApproverDAO;
import co.id.fifgroup.avm.dao.mybatis.mapper.AVMApproverMapper;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;


/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class MyBatisAVMApproverDAO extends MyBatisAVMDAOHelper implements AVMApproverDAO{

	private static final Logger logger = LoggerFactory.getLogger(MyBatisAVMApproverDAO.class);
	
//	public void assignApproverToAVMLevel(UUID avmId, int levelSequence,
//			UUID userId, int priority) {
//		SqlSession session = null;
//		
//			session = getSqlSession();
//		
//		AVMApprover avmApprover = new AVMApprover();
//		avmApprover.setAvmId(avmId);
//		avmApprover.setLevelSequence(levelSequence);
//		avmApprover.setApproverId(userId);
//		avmApprover.setPriority(priority);
//		
//		session.insert("AVMApprover.assignApprover", avmApprover);
//		session.commit();
//		session.close();
//		
//	}
	
	public int insertMultipleNewAVMApprover(List<AVMApprover> approverList) throws FifException {
		try {
			AVMApproverMapper mapper = (AVMApproverMapper) sqlSession.getMapper(AVMApproverMapper.class);
			int result = 0;
			for (AVMApprover avmApprover : approverList) {
				result += mapper.insertNewAVMApprover(avmApprover);
			}
			
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public List<AVMApprover> getTopPriorityApproverList(UUID avmId, int levelSequence) 
	throws FifException {
		try {
			AVMApproverMapper mapper = sqlSession.getMapper(AVMApproverMapper.class);
			Map<String, Object> parameterMap = new HashMap<String,Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("levelSequence", levelSequence);
			return mapper.getTopPriorityApprovers(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
		
//		SqlSession session = getSqlSession();
//		
//		Map<String, Object> parameterMap = new HashMap<String, Object>();
//		parameterMap.put("avmId", avmId);
//		parameterMap.put("levelSequence", levelSequence);
//		List<AVMApprover> approverList = session.selectList("AVMApprover.getTopPriorityApproverList", parameterMap);
//		List<Map<String, Object>> resultList = session.selectList("AVMApprover.getTopPriorityApproverList", 
//												parameterMap);

//		List<UUID> approverList = new ArrayList<UUID>();
//		for (Object element: resultList) {
//			Map <String, Object> resultMap = (Map<String, Object>) element;
//			UUID approverId = (UUID) resultMap.get("approverId");
//			approverList.add(approverId);
//		}
		
		
		
	}
	
//	public List<AVMApprover> getApproverList(UUID avmId, int levelSequence, int priority) throws FifException {
//		try{
//			AVMApproverMapper mapper = avmSqlSession.getMapper(AVMApproverMapper.class);
//			Map<String, Object> parameterMap = new HashMap<String, Object>();
//			parameterMap.put("avmId", avmId);
//			parameterMap.put("levelSequence", levelSequence);
//			parameterMap.put("priority", priority);
//			
//			List<AVMApprover> resultList = mapper.getApproverList(parameterMap);
//	
//			return resultList;
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
//		} 
//	}
	
	public List<AVMApprover> getApproverList(UUID avmId, int levelSequence) throws FifException {
		try{
			AVMApproverMapper mapper = sqlSession.getMapper(AVMApproverMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("levelSequence", levelSequence);
			List<AVMApprover> resultList = mapper.getApproverList(parameterMap);
	
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public List<AVMApprover> getApproverList(UUID avmId) throws FifException {
		try {
			AVMApproverMapper mapper = sqlSession.getMapper(AVMApproverMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			List<AVMApprover> resultList = mapper.getApproverList(parameterMap);
	
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

//	public void updateAVMApprovers(List<AVMApprover> avmApproverList) {
//		SqlSession session = null;
		
//		try {
//			session = getSqlSession();
//			AVMApproverMapper mapper = session.getMapper(AVMApproverMapper.class);
//			UUID avmId = avmApproverList.get(0).getAvmId();
//			Map <String, Object> parameterMap = new HashMap<String, Object>();
//			parameterMap.put("avmId", avmId);
//			//mapper.deleteAVMApprovers(parameterMap);
//			
//		}
//		catch (JatisException e) {
//			throw e;
//		}
//		catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new JatisException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
//		}
//		finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//	}

	public int deleteAVMApprovers(UUID avmId) throws FifException {
		try {
			AVMApproverMapper mapper = sqlSession.getMapper(AVMApproverMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			return mapper.deleteApproverList(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	//add yahya
	public List<AVMApprover> getTopPriorityApproverList(UUID avmId, int levelSequence, int version) 
		throws FifException {
		try {
			AVMApproverMapper mapper = sqlSession.getMapper(AVMApproverMapper.class);
			Map<String, Object> parameterMap = new HashMap<String,Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("levelSequence", levelSequence);
			parameterMap.put("version", version);
			return mapper.getTopPriorityApprovers(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public List<AVMApprover> getApproverList(UUID avmId, int levelSequence, int priority, int version) throws FifException {
		try{
			AVMApproverMapper mapper = sqlSession.getMapper(AVMApproverMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("levelSequence", levelSequence);
			parameterMap.put("priority", priority);
			parameterMap.put("avmVersionId", version);
			
			List<AVMApprover> resultList = mapper.getApproverList(parameterMap);
	
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}
	
	public List<AVMApprover> getApproverList(UUID avmId, int levelSequence, int avmVersionId) throws FifException {
		try{
			AVMApproverMapper mapper = sqlSession.getMapper(AVMApproverMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("levelSequence", levelSequence);
			parameterMap.put("avmVersionId", avmVersionId);
			List<AVMApprover> resultList = mapper.getApproverList(parameterMap);
	
			return resultList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
//	public List<AVMApprover> getApproverList(UUID avmId, int version) throws FifException {
//		try {
//			AVMApproverMapper mapper = avmSqlSession.getMapper(AVMApproverMapper.class);
//			Map<String, Object> parameterMap = new HashMap<String, Object>();
//			parameterMap.put("avmId", avmId);
//			parameterMap.put("version", version);
//			List<AVMApprover> resultList = mapper.getApproverList(parameterMap);
//	
//			return resultList;
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
//		}
//	}
	
	public int deleteAVMApprovers(UUID avmId, int avmVersionId) throws FifException {
		try {
			AVMApproverMapper mapper = sqlSession.getMapper(AVMApproverMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("avmVersionId", avmVersionId);
			return mapper.deleteApproverList(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	@Override
	public int insertMultipleNewAVMApprover(List<AVMApprover> approverList,
			AVMVersions avmVersions) throws FifException {
		try {
			AVMApproverMapper mapper = (AVMApproverMapper) sqlSession.getMapper(AVMApproverMapper.class);
			int result = 0;
			for (AVMApprover avmApprover : approverList) {
				avmApprover.setAvmId(avmVersions.getAvmId());
				avmApprover.setAvmVersionId(avmVersions.getAvmVersionId());
				result += mapper.insertNewAVMApprover(avmApprover);
			}
			
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
}
