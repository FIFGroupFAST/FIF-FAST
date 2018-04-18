package co.id.fifgroup.avm.dao.mybatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.dao.AVMVersionDAO;
import co.id.fifgroup.avm.dao.mybatis.mapper.AVMVersionsMapper;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;

public class MyBatisAVMVersionDAO extends MyBatisAVMDAOHelper implements AVMVersionDAO {

	private static final Logger logger = LoggerFactory.getLogger(MyBatisAVMVersionDAO.class);

	@Override
	public int insertNewVersion(AVMVersions versionAVM) throws FifException {
		try{
			AVMVersionsMapper mapper = sqlSession.getMapper(AVMVersionsMapper.class);						
			return mapper.insertNewVersion(versionAVM);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(),ex);
		} 
	}

	@Override
	public int updateVersion(AVMVersions versionAVM) throws FifException {
		try{
			AVMVersionsMapper mapper = sqlSession.getMapper(AVMVersionsMapper.class);						
			return mapper.updateVersion(versionAVM);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(),ex);
		} 
	}

	@Override
	public int deleteVersion(AVMVersions versionAVM) throws FifException {
		try{
			AVMVersionsMapper mapper = sqlSession.getMapper(AVMVersionsMapper.class);						
			return mapper.deleteVersion(versionAVM);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(),ex);
		} 
	}

	@Override
	public List<AVMVersions> getAllAVMVersion(UUID avmId)
			throws FifException {
		try {
			AVMVersionsMapper mapper = sqlSession.getMapper(AVMVersionsMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			return mapper.getAllAVMVersion(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	@Override
	public AVMVersions getCurrentActiveVersion(UUID avmId, Date currentDate)
			throws FifException {
		try {
			AVMVersionsMapper mapper = sqlSession.getMapper(AVMVersionsMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("currentDate", currentDate);
			return mapper.getCurrentActiveVersion(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	@Override
	public AVMVersions getFutureAVMVersion(UUID avmId, Date currentDate)
			throws FifException {
		try {
			AVMVersionsMapper mapper = sqlSession.getMapper(AVMVersionsMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("currentDate", currentDate);
			return mapper.getFutureAVMVersion(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	@Override
	public int getLastVersionNumber(UUID avmId) throws FifException {
		try {
			AVMVersionsMapper mapper = sqlSession.getMapper(AVMVersionsMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			return mapper.getLastVersionNumber(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	@Override
	public AVMVersions getAVMVersionByNumberVersion(UUID avmId,
			int versionNumber) throws FifException {
		try {
			AVMVersionsMapper mapper = sqlSession.getMapper(AVMVersionsMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("versionNumber", versionNumber);
			return mapper.getAVMVersionByNumberVersion(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}

	@Override
	public AVMVersions getPreviousAVMVersion(UUID avmId, int versionNumber)
			throws FifException {
		try {
			AVMVersionsMapper mapper = sqlSession.getMapper(AVMVersionsMapper.class);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("avmId", avmId);
			parameterMap.put("versionNumber", versionNumber);
			return mapper.getPreviousAVMVersion(parameterMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FifException(AVMReturnCode.ERROR_DATABASE_ACCESS.getCode(), e);
		} 
	}
}
