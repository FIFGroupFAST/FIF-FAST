package co.id.fifgroup.core.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface WorkingScheduleSetupFinder {
	
	List<KeyValue> getWorkingScheduleByCompanyId(@Param("companyId") Long companyId);
	KeyValue getWorkingScheduleById(@Param("id") Long id);
	KeyValue getWorkingScheduleByName(@Param("companyId") Long companyId, @Param("name") String name);
	int countWorkingScheduleByCompanyId(@Param("companyId") Long companyId);
	int countWorkingScheduleByWorkingScheduleId(@Param("workingScheduleId")Long workingScheduleId);

}
