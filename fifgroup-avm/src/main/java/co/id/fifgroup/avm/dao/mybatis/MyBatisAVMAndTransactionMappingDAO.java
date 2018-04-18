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

import co.id.fifgroup.avm.constants.AVMAndTransactionMappingStatus;
import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.dao.AVMAndTransactionMappingDAO;
import co.id.fifgroup.avm.dao.mybatis.mapper.AVMAndTransactionMapper;
import co.id.fifgroup.avm.domain.AVMAndTransactionMapping;
import co.id.fifgroup.avm.exception.FifException;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class MyBatisAVMAndTransactionMappingDAO extends MyBatisAVMDAOHelper implements AVMAndTransactionMappingDAO {

	private static final int STATUS_ENABLED = 1;
	//private static final int STATUS_DISABLED = 0;
	
	private static final Logger logger = LoggerFactory.getLogger(MyBatisAVMAndTransactionMappingDAO.class);
	@Override
	public int mapAVMAndTransaction(UUID avmId, int trxType) throws FifException {
		try{
			AVMAndTransactionMapper mapper = sqlSession.getMapper(AVMAndTransactionMapper.class);
			
			AVMAndTransactionMapping avmMapping = new AVMAndTransactionMapping();
			avmMapping.setAvmId(avmId);
			avmMapping.setTrxType(trxType);
			AVMAndTransactionMappingStatus status = AVMAndTransactionMappingStatus.ENABLED;
			avmMapping.setAvmStatus(status.getCode());
			
			return mapper.insertNewMapping(avmMapping);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(),ex);
		} 
	}
	
	public void mapAVMAndTransactionTypeList(UUID avmId, List<Integer> trxTypeList) throws FifException {
		try {
			AVMAndTransactionMapper mapper = sqlSession.getMapper(AVMAndTransactionMapper.class);
			for (int trxType : trxTypeList) {
				AVMAndTransactionMapping mapping = new AVMAndTransactionMapping();
				mapping.setAvmId(avmId);
				mapping.setTrxType(trxType);
				mapping.setAvmStatus(STATUS_ENABLED);
				mapper.insertNewMapping(mapping);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}

	public List<AVMAndTransactionMapping> getAllAVMAndTransactionMappings() throws FifException {
		try{
			AVMAndTransactionMapper mapper = sqlSession.getMapper(AVMAndTransactionMapper.class);
			return mapper.getAllAVMAndTransactionMappings();
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(),ex);
		} 
	}

	public void deleteMappedAVMAndTrxTypeList(UUID avmId, List<Integer> trxTypeList)  throws FifException {
		try {
			AVMAndTransactionMapper mapper = sqlSession.getMapper(AVMAndTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("trxTypeList", trxTypeList);
			mapper.deleteMappedAVMAndTrxTypeList(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	public List<Integer> getMappedTrxTypeList(UUID avmId) throws FifException {
		try {
			AVMAndTransactionMapper mapper = sqlSession.getMapper(AVMAndTransactionMapper.class);
			return mapper.getMappedTrxTypeList(avmId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	//add yahya
	public AVMAndTransactionMapping getAVMByTrxType(int trxType) throws FifException {
		try {
			AVMAndTransactionMapper mapper = sqlSession.getMapper(AVMAndTransactionMapper.class);
			return mapper.getAVMByTrxType(trxType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		}
	}
	
	public void updateMappedAVMAndTrxTypeList(UUID avmId, List<Integer> trxTypeList)  throws FifException {
		try {
			AVMAndTransactionMapper mapper = sqlSession.getMapper(AVMAndTransactionMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("trxTypeList", trxTypeList);
			mapper.updateMappedAVMAndTrxTypeList(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 		
	}
}
