package co.id.fifgroup.core.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.dto.PersonAssignmentProfileDTO;
import co.id.fifgroup.core.ui.lookup.KeyValue;

/**
 * This service provide to get an attribute related with Employee data
 * 
 * @author fifuser
 *
 */
public interface PersonProfileService {

	public PersonAssignmentProfileDTO selectPersonByAssignment(Long personId, Long companyId);
	
	public Long getPersonIdByEmployeeNumber(String employeeNumber, Long companyId, Date effectiveOnDate);
	
	public Long getWorkingScheduleIdByPersonIdAndAttendanceDate(Long personId, Date attendanceDate);
	
	public List<KeyValue> getPersonByCompanyId(Long companyId, Date effectiveOnDate);
	
	public int countPersonByCompanyId(Long companyId, Date effectiveOnDate);
	
	public KeyValue getCompanyByPersonId(Long personId, Date effectiveOnDate);
	
	public KeyValue getOrganizationByPersonId(Long personId, Date effectiveOnDate);
	
	public KeyValue getJobByPersonId(Long personId, Date effectiveOnDate);
	
	public KeyValue getGradeByPersonId(Long personId, Date effectiveOnDate);
	
	public String getAssignmentStatusByPersonId(Long personId, Date effectiveOnDate);
	
	public KeyValue getBranchByPersonId(Long personId, Date effectiveOnDate);
	
	public String getNPKByPersonId(Long personId, Date effectiveOnDate);
	
	public KeyValue getSpv1ByPersonId(Long personId, Date effectiveOnDate);
	
	public KeyValue getSpv2ByPersonId(Long personId, Date effectiveOnDate);
	
	public List<KeyValue> getHcAdmin(Long companyId, Date effectiveOnDate);
	
	public List<KeyValue> getPersonByRole(String roleName, Date effectiveOnDate);
	
	public List<KeyValue> getHomeBaseByPersonId(Long personId, Date effectiveOnDate);
	
	public Date getAstraJoinDateByPersonId(Long personId, Date effectiveOnDate);
	
	public Date getHireDateByPersonId(Long personId, Date effectiveOnDate);
	
	public String getCostCenterByPersonId(Long personId, Date effectiveOnDate);
	
	public String getNpwpByPersonId(Long personId, Date effectiveOnDate);
	
	public List<KeyValue> getLobByPersonId(Long personId, Date effectiveOnDate);
	
	public Integer countActiveAssignmentEmployee(Long companyId, Long organizationId, Long jobId, String jobForCode);
}
