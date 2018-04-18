package co.id.fifgroup.avm.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;

public interface AVMVersionDAO {

	public int insertNewVersion(AVMVersions versionAVM) throws FifException;
	
	public int updateVersion(AVMVersions versionAVM) throws FifException;
	
	public int deleteVersion(AVMVersions versionAVM) throws FifException;
	
	public List<AVMVersions> getAllAVMVersion(UUID avmId) throws FifException;
	
	public AVMVersions getCurrentActiveVersion(UUID avmId, Date currentDate) throws FifException;
	
	public AVMVersions getFutureAVMVersion(UUID avmId, Date currentDate) throws FifException;
	
	public int getLastVersionNumber(UUID avmId) throws FifException;
	
	public AVMVersions getAVMVersionByNumberVersion(UUID avmId, int versionNumber) throws FifException;
	
	public AVMVersions getPreviousAVMVersion(UUID avmId, int numberVersion) throws FifException;
	
}
