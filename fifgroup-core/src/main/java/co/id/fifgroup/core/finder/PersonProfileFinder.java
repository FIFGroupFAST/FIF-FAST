package co.id.fifgroup.core.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.dto.PersonAdapterDTO;
import co.id.fifgroup.core.dto.PersonAssignmentProfileDTO;

public interface PersonProfileFinder {
	
	public PersonAssignmentProfileDTO selectAssignmentByPersonId(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	public Long selectPersonIdByEmployeeNumber(@Param("employeeNumber") String employeeNumber, @Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate );
	
	public PersonAdapterDTO getWorkingScheduleIdByPersonIdAndAttendanceDate(@Param("personId")Long personId, @Param("attendanceDate")Date attendanceDate);
	
	public Integer countActiveAssignmentEmployee(@Param("companyId") Long companyId, @Param("organizationId") Long organizationId, @Param("jobId") Long jobId, @Param("listJobFor") List<String> listJobFor);
}
