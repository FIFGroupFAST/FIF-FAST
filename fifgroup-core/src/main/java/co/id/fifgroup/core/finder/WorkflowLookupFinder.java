package co.id.fifgroup.core.finder;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface WorkflowLookupFinder {

	public List<KeyValue> getAllJobs(@Param("code") String code, @Param("name") String name, RowBounds rowBounds);
	public int countAllJobs(@Param("code") String code, @Param("name") String name);
	public KeyValue getJobByUUID(@Param("jobUUID") UUID jobUUID);
	
	public List<KeyValue> getLookupJob(String name, RowBounds rowBounds);
	public int countLookupJob(String name);
	public KeyValue getLookupValueJob(String id);
	
	public List<KeyValue> getLookupGrade(String name, RowBounds rowBounds);
	public int countLookupGrade(String name);
	public KeyValue getLookupValueGrade(String id);
	
	public List<KeyValue> getLookupOrganization(String name, RowBounds rowBounds);
	public int countLookupOrganization(String name);
	public KeyValue getLookupValueOrganization(String id);
	
	public List<KeyValue> getLookupLocation(String name, RowBounds rowBounds);
	public int countLookupLocation(String name);
	public KeyValue getLookupValueLocation(String id);
	
	public List<KeyValue> getLookupLeaveType(String name, RowBounds rowBounds);
	public int countLookupLeaveType(String name);
	public KeyValue getLookupValueLeaveType(String id);
	
	public List<KeyValue> getLookupKeyValue(@Param("lookupName") String lookupName, @Param("name") String name, RowBounds rowBounds);
	public int countLookupKeyValue(@Param("lookupName") String lookupName, @Param("name") String name);
	public List<KeyValue> getKeyValue(@Param("lookupName") String lookupName, @Param("id") String id);
	
	public List<KeyValue> getLookupTerminationReason(String name, RowBounds rowBounds);
	public int countLookupTerminationReason(String name);
	public KeyValue getLookupValueTerminationReason(String id);
	
	public List<KeyValue> getLookupLoanType(String name, RowBounds rowBounds);
	public int countLookupLoanType(String name);
	public KeyValue getLookupValueLoanType(String id);
	
	public List<KeyValue> getAllEmployee();
	public List<KeyValue> getAllEmployeeCriteria(@Param("employeeNumber") String employeeNumber, @Param("fullName")String fullName, RowBounds rowBounds);
	public int countAllEmployeeCriteria(@Param("employeeNumber") String employeeNumber, @Param("fullName")String fullName);
	public KeyValue getValueEmployee(@Param("personUUID") UUID personUUID);
	public List<KeyValue> getApproverByName(@Param("approverName") String approverName);
	
	public List<KeyValue> getPendingTransactionBenefit(@Param("personId") Long personId, @Param("companyId") Long companyId);
	public List<KeyValue> getPendingTransactionBusinessTrip(@Param("personId") Long personId, @Param("companyId") Long companyId);
	public List<KeyValue> getPendingTransactionLeave(@Param("personId") Long personId, @Param("companyId") Long companyId);
	public List<KeyValue> getPendingTransactionLoan(@Param("personId") Long personId, @Param("companyId") Long companyId);
	public List<KeyValue> getPendingTransactionOvertime(@Param("personId") Long personId, @Param("companyId") Long companyId);
}
