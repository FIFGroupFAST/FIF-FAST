package co.id.fifgroup.core.service;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface WorkflowLookupServiceAdapter {

	public List<KeyValue> getAllJob(String code, String name, int limit, int offset);
	public int countAllJob(String code, String name);
	public KeyValue getJobByUUID(UUID jobUUID);
	
	public List<KeyValue> getLookupJob(String name, int limit, int offset);
	public int countLookupJob(String name);
	public KeyValue getLookupValueJob(String id);
	
	public List<KeyValue> getLookupGrade(String name, int limit, int offset);
	public int countLookupGrade(String name);
	public KeyValue getLookupValueGrade(String id);
	
	public List<KeyValue> getLookupOrganization(String name, int limit, int offset);
	public int countLookupOrganization(String name);
	public KeyValue getLookupValueOrganization(String id);
	
	public List<KeyValue> getLookupLocation(String name, int limit, int offset);
	public int countLookupLocation(String name);
	public KeyValue getLookupValueLocation(String id);
	
	public List<KeyValue> getLookupLeaveType(String name, int limit, int offset);
	public int countLookupLeaveType(String name);
	public KeyValue getLookupValueLeaveType(String id);
	
	public List<KeyValue> getLookupKeyValue(String lookupName, String name, int limit, int offset);
	public int countLookupKeyValue(String lookupName, String name);
	public KeyValue getKeyValue(String lookupName, String id);
	
	public List<KeyValue> getLookupTerminationReason(String name, int limit, int offset);
	public int countLookupTerminationReason(String name);
	public KeyValue getLookupValueTerminationReason(String id);
	
	public List<KeyValue> getLookupLoanType(String name, int limit, int offset);
	public int countLookupLoanType(String name);
	public KeyValue getLookupValueLoanType(String id);
	
	public List<KeyValue> getAllEmployee();
	public List<KeyValue> getAllEmployeeCriteria(String employeeNumber, String fullName, int limit, int offset);
	public int countAllEmployeeCriteria(String employeeNumber, String fullName);
	public KeyValue getValueEmployee(UUID personUUID);
	public List<KeyValue> getApproverByName(String approverName);
	
	public List<KeyValue> getAllPendingTransactionEmployee(Long personId, Long companyId);
}
