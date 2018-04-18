package co.id.fifgroup.systemadmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.dto.UserAccessRecordDTO;

public interface UserAccessRecordFinder {

	public List<UserAccessRecordDTO> getUserAccessRecordDtoByAllParameter(
			@Param("moduleName") String moduleName,
			@Param("functionName") String functionName,
			@Param("userName") String userName,
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo, RowBounds rowBounds);
	
	public List<UserAccessRecordDTO> getUserAccessRecordDtoByAllParameter(
			@Param("moduleName") String moduleName,
			@Param("functionName") String functionName,
			@Param("userName") String userName,
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public int countUserAccessRecordDtoByAllParameter(@Param("moduleName") String moduleName,
			@Param("functionName") String functionName,
			@Param("userName") String userName,
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}
