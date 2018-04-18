package co.id.fifgroup.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.id.fifgroup.core.dto.PersonAdapterDTO;
import co.id.fifgroup.core.dto.PersonAssignmentProfileDTO;
import co.id.fifgroup.core.finder.PersonProfileFinder;
import co.id.fifgroup.core.service.PersonProfileService;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@Service
public class PersonProfileServiceImpl implements PersonProfileService {

	@Autowired
	private PersonProfileFinder personProfileFinder;
	
	@Override
	public PersonAssignmentProfileDTO selectPersonByAssignment(Long personId, Long companyId) {
		PersonAssignmentProfileDTO check = personProfileFinder.selectAssignmentByPersonId(personId, companyId);
		return check;
	}

	@Override
	public Long getPersonIdByEmployeeNumber(String employeeNumber, Long companyId, Date effectiveOnDate) {
		return personProfileFinder.selectPersonIdByEmployeeNumber(employeeNumber, companyId, effectiveOnDate);
	}
	
	/*
	 * (non-Javadoc)
	 * @see return working schedule which is possesed by person's, or if the person itself doesn't have a working schedule
	 * the method will return the person's grade working schedule continuosly until company, following the hierarchy.
	 */
	@Override
	public Long getWorkingScheduleIdByPersonIdAndAttendanceDate(Long personId, Date attendanceDate) {
		//jika person tidak punya workingscheduleId gunakan hierarchy grade > job > organisasi > location > company
		Long workingScheduleId = null;
		try{
			PersonAdapterDTO person = personProfileFinder.getWorkingScheduleIdByPersonIdAndAttendanceDate(personId, attendanceDate);
			if(person==null){
				return null;
			}else{
				workingScheduleId = person.getPersonWorkingScheduleId() != null ? person.getPersonWorkingScheduleId() : 
					person.getGradeWorkingScheduleId() != null ? person.getGradeWorkingScheduleId() : 
						person.getJobWorkingScheduleId() != null ? person.getJobWorkingScheduleId() : 
							person.getOrganizationWorkingScheduleId() != null ? person.getOrganizationWorkingScheduleId() : 
								person.getLocationWorkingScheduleId() != null ? person.getLocationWorkingScheduleId() :
									person.getCompanyWorkingScheduleId() != null ? person.getCompanyWorkingScheduleId() : null;
									return workingScheduleId;
			}
		}catch (TooManyResultsException e) {
			return null;
		}
	}

	@Override
	public KeyValue getCompanyByPersonId(Long personId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyValue getOrganizationByPersonId(Long personId,
			Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyValue getJobByPersonId(Long personId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyValue getGradeByPersonId(Long personId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAssignmentStatusByPersonId(Long personId,
			Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyValue getBranchByPersonId(Long personId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNPKByPersonId(Long personId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyValue getSpv1ByPersonId(Long personId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyValue getSpv2ByPersonId(Long personId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyValue> getHcAdmin(Long companyId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyValue> getPersonByRole(String roleName, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyValue> getHomeBaseByPersonId(Long companyId,
			Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getAstraJoinDateByPersonId(Long companyId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getHireDateByPersonId(Long companyId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCostCenterByPersonId(Long companyId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNpwpByPersonId(Long companyId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyValue> getLobByPersonId(Long companyId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KeyValue> getPersonByCompanyId(Long companyId,
			Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countPersonByCompanyId(Long companyId, Date effectiveOnDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer countActiveAssignmentEmployee(Long companyId, Long organizationId,
			Long jobId, String jobForCode) {
		List<String> listJobFor = null;
		if (jobForCode != null) {
			listJobFor = new ArrayList<String>();
			listJobFor.add(jobForCode);
			if (jobForCode.equalsIgnoreCase("NPK") || jobForCode.equalsIgnoreCase("CWK")) {
				listJobFor.add("NPKCWK");
			}
		}
		return personProfileFinder.countActiveAssignmentEmployee(companyId, organizationId, jobId, listJobFor);
	}

}
