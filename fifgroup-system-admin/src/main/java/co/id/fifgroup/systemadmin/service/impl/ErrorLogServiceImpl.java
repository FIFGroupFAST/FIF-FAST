package co.id.fifgroup.systemadmin.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.dto.ModuleErrorLogDTO;
import co.id.fifgroup.core.service.ErrorLogServiceAdapater;
import co.id.fifgroup.systemadmin.dao.ModuleErrorLogMapper;
import co.id.fifgroup.systemadmin.domain.ModuleErrorLog;
import co.id.fifgroup.systemadmin.domain.ModuleErrorLogExample;
import co.id.fifgroup.systemadmin.service.ErrorLogService;

@Service("errorLogService")
@Transactional
public class ErrorLogServiceImpl implements ErrorLogService, ErrorLogServiceAdapater{
	
	@Autowired
	ModuleErrorLogMapper moduleErrorLogMapper;
	@Autowired
	ModelMapper modelMapper;

	@Override
	@Transactional(readOnly=true)
	public List<ModuleErrorLog> getErrorLogByExample(ModuleErrorLogExample example) {
		example.setOrderByClause("LOG_TIME DESC");
		return moduleErrorLogMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int countErrorLogByExample(ModuleErrorLogExample example) {
		return moduleErrorLogMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ModuleErrorLog> getErrorLogByExample(ModuleErrorLogExample example, int limit, int offset) {
		return moduleErrorLogMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void insertErrorLog(ModuleErrorLog errorLog) throws Exception{
		moduleErrorLogMapper.insert(errorLog);
	}

	@Override
	public void insertErrorLog(ModuleErrorLogDTO errorLog) {
		ModuleErrorLog moduleErrorLog = modelMapper.map(errorLog, ModuleErrorLog.class);
		moduleErrorLogMapper.insert(moduleErrorLog);
	}

}
