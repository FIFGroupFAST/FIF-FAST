package co.id.fifgroup.avm.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;

public interface AVMVersionsMapper {

	public int insertNewVersion(AVMVersions versionAVM) throws FifException;
	
	public int updateVersion(AVMVersions versionAVM) throws FifException;
	
	public int deleteVersion(AVMVersions versionAVM) throws FifException;
	
	public List<AVMVersions> getAllAVMVersion(Map<String, Object> parameterMap) throws FifException;
	
	public AVMVersions getCurrentActiveVersion(Map<String, Object> parameterMap) throws FifException;
	
	public AVMVersions getFutureAVMVersion(Map<String, Object> parameterMap) throws FifException;
	
	public int getLastVersionNumber(Map<String, Object> parameterMap) throws FifException;
	
	public AVMVersions getAVMVersionByNumberVersion(Map<String, Object> parameterMap) throws FifException;
	
	public AVMVersions getPreviousAVMVersion(Map<String, Object> parameterMap) throws FifException;
}
