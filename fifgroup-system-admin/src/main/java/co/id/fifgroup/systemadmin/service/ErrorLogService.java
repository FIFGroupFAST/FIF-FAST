package co.id.fifgroup.systemadmin.service;

import java.util.List;

import co.id.fifgroup.systemadmin.domain.ModuleErrorLog;
import co.id.fifgroup.systemadmin.domain.ModuleErrorLogExample;

public interface ErrorLogService {
	
	public List<ModuleErrorLog> getErrorLogByExample(ModuleErrorLogExample example);
	
	public List<ModuleErrorLog> getErrorLogByExample(ModuleErrorLogExample example, int limit, int offset);
	
	public int countErrorLogByExample(ModuleErrorLogExample example);
	
	public void insertErrorLog(ModuleErrorLog errorLog) throws Exception;

}
