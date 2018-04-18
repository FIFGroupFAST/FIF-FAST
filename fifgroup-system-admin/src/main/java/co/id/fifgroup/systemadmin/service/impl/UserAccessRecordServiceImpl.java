package co.id.fifgroup.systemadmin.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.systemadmin.dao.UserAccessRecordMapper;
import co.id.fifgroup.systemadmin.domain.UserAccessRecord;
import co.id.fifgroup.systemadmin.domain.UserAccessRecordExample;
import co.id.fifgroup.systemadmin.dto.UserAccessRecordDTO;
import co.id.fifgroup.systemadmin.finder.UserAccessRecordFinder;
import co.id.fifgroup.systemadmin.service.UserAccessRecordService;

@Service("userAccessRecordService")
@Transactional
public class UserAccessRecordServiceImpl implements UserAccessRecordService{
	@Autowired
	UserAccessRecordMapper userAccessRecordMapper;
	@Autowired
	UserAccessRecordFinder userAccesRecordFinder;
	
	@Override
	@Transactional(readOnly = true)
	public int countUserAccessRecordByExample(UserAccessRecordExample example) {
		return userAccessRecordMapper.countByExample(example) ;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserAccessRecord> getUserAccessRecordByExample(UserAccessRecordExample example) {
		return userAccessRecordMapper.selectByExample(example);
	}

	@Override
	public void record(UserAccessRecord userAccessRecord) throws Exception{
		userAccessRecordMapper.insert(userAccessRecord);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserAccessRecord> getUserAccessRecordByExample(UserAccessRecordExample example, int limit, int offset) {
		return userAccessRecordMapper.selectByExampleWithRowbounds(example, new RowBounds(offset,limit));
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserAccessRecordDTO> getUserAccessRecordDtoByAllParameter(
			String moduleName, String functionName, String userName, Date dateFrom, Date dateTo, int limit, int offset) {
		return userAccesRecordFinder.getUserAccessRecordDtoByAllParameter(moduleName, functionName, userName, dateFrom, 
				dateTo, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countUserAccessRecordDtoByAllParameter(String moduleName,
			String functionName, String userName, Date dateFrom, Date dateTo) {
		return userAccesRecordFinder.countUserAccessRecordDtoByAllParameter(moduleName, functionName, userName, dateFrom, dateTo);
	}

	@Override
	public UserAccessRecord recordFunctionAccess(UserAccessRecord userAccessRecord) {
		return userAccessRecord;
	}

	@Override
	public List<UserAccessRecordDTO> getUserAccessRecordDtoByAllParameter(
			String moduleName, String functionName, String userName,
			Date dateFrom, Date dateTo) {
		return userAccesRecordFinder.getUserAccessRecordDtoByAllParameter(moduleName, functionName, userName, dateFrom,  dateTo);
	}

}
