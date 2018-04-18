package co.id.fifgroup.systemadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.UserManualDownloadUtil;

import co.id.fifgroup.systemadmin.domain.Function;
import co.id.fifgroup.systemadmin.service.FunctionService;

@Transactional(readOnly=true)
@Service
public class UserManualDownloadUtilImpl implements UserManualDownloadUtil {

	@Autowired
	private FunctionService functionService;
	
	@Override
	public String getManualFilePath(Long functionId) {
		Function function = functionService.getFunctionById(functionId);
		return (null == function) ? "" : function.getUserManual();
	}

}
