package co.id.fifgroup.core.service;

import java.util.Date;

public interface ScheduleTypeAdapterService {

	boolean isHolidayFromScheduleTypeByPersonIdAndDate(Long personId, Date targetDate, Long companyId);
	
}
