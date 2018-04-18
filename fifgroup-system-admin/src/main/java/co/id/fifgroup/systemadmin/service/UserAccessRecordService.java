package co.id.fifgroup.systemadmin.service;



import java.util.Date;
import java.util.List;

import co.id.fifgroup.systemadmin.domain.UserAccessRecord;
import co.id.fifgroup.systemadmin.domain.UserAccessRecordExample;
import co.id.fifgroup.systemadmin.dto.UserAccessRecordDTO;

public interface UserAccessRecordService {
	
	public int countUserAccessRecordByExample(UserAccessRecordExample example);
	
	public List<UserAccessRecord> getUserAccessRecordByExample(UserAccessRecordExample example);
	
	public List<UserAccessRecord> getUserAccessRecordByExample(UserAccessRecordExample example, int limit, int offset);
	
	public void record(UserAccessRecord userAccessRecord) throws Exception;
	
	public List<UserAccessRecordDTO> getUserAccessRecordDtoByAllParameter(String moduleName, String functionName, 
			String userName, Date dateFrom, Date dateTo, int limit, int offset);
	
	public List<UserAccessRecordDTO> getUserAccessRecordDtoByAllParameter(String moduleName, String functionName, 
			String userName, Date dateFrom, Date dateTo);
	
	public int countUserAccessRecordDtoByAllParameter(String moduleName, String functionName, String userName, 
			Date dateFrom, Date dateTo);
	
	public UserAccessRecord recordFunctionAccess(UserAccessRecord userAccessRecord);
	
}
