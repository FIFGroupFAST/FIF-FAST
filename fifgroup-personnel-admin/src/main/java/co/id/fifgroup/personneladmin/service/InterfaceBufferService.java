package co.id.fifgroup.personneladmin.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.personneladmin.dto.HousEmployeeDTO;

import co.id.fifgroup.personneladmin.domain.InterfaceBuffer;
import co.id.fifgroup.personneladmin.domain.InterfaceBufferExample;

public interface InterfaceBufferService {

	public void insertInterfaceBuffer(Long personId, String employeeNumber, String employeeName, 
			String employmentCategory, Date assignmentEffectiveDate, String employeePhoneNumber, 
			Long jobId, Long sourceOrganizationId, Long destinationOrganizationId, boolean isNewEmployee, 
			Long companyId, String employeeFlag);
	
	public void deleteInterfaceBuffer(Long personId, Long companyId, Date assignmentEffectiveDate);
	
	public List<InterfaceBuffer> getInterfaceData();
	
	public List<InterfaceBuffer> selectByExample(InterfaceBufferExample interfaceBufferExample);
	
	public Integer updateInterfaceBuffer(InterfaceBuffer interfaceBuffer);
	
	public Integer insertHousEmployee(HousEmployeeDTO housEmployee);
	
	public Integer insertUser(String userName, Long personId, Date dateFrom, Date dateTo);
}
