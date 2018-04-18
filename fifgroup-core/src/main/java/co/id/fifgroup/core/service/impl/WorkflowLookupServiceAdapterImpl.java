package co.id.fifgroup.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.finder.WorkflowLookupFinder;
import co.id.fifgroup.core.service.WorkflowLookupServiceAdapter;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@Transactional(readOnly=true)
@Service
public class WorkflowLookupServiceAdapterImpl implements WorkflowLookupServiceAdapter {

	@Autowired
	private WorkflowLookupFinder workflowLookupFinder;
	
	@Override
	public List<KeyValue> getAllJob(String code, String name, int limit, int offset) {
		return workflowLookupFinder.getAllJobs(code, name, new RowBounds(offset, limit));
	}
	
	@Override
	public int countAllJob(String code, String name) {
		return workflowLookupFinder.countAllJobs(code, name);
	}

	@Override
	public KeyValue getJobByUUID(UUID jobUUID) {
		return workflowLookupFinder.getJobByUUID(jobUUID);
	}

	@Override
	public List<KeyValue> getLookupJob(String name, int limit, int offset) {
		return workflowLookupFinder.getLookupJob(name, new RowBounds(offset, limit));
	}

	@Override
	public int countLookupJob(String name) {
		return workflowLookupFinder.countLookupJob(name);
	}

	@Override
	public KeyValue getLookupValueJob(String id) {
		return workflowLookupFinder.getLookupValueJob(id);
	}

	@Override
	public List<KeyValue> getLookupGrade(String name, int limit, int offset) {
		return workflowLookupFinder.getLookupGrade(name, new RowBounds(offset, limit));
	}

	@Override
	public int countLookupGrade(String name) {
		return workflowLookupFinder.countLookupGrade(name);
	}

	@Override
	public KeyValue getLookupValueGrade(String id) {
		return workflowLookupFinder.getLookupValueGrade(id);
	}

	@Override
	public List<KeyValue> getLookupOrganization(String name, int limit,
			int offset) {
		return workflowLookupFinder.getLookupOrganization(name, new RowBounds(offset, limit));
	}

	@Override
	public int countLookupOrganization(String name) {
		return workflowLookupFinder.countLookupOrganization(name);
	}

	@Override
	public KeyValue getLookupValueOrganization(String id) {
		return workflowLookupFinder.getLookupValueOrganization(id);
	}

	@Override
	public List<KeyValue> getLookupLocation(String name, int limit, int offset) {
		return workflowLookupFinder.getLookupLocation(name, new RowBounds(offset, limit));
	}

	@Override
	public int countLookupLocation(String name) {
		return workflowLookupFinder.countLookupLocation(name);
	}

	@Override
	public KeyValue getLookupValueLocation(String id) {
		return workflowLookupFinder.getLookupValueLocation(id);
	}

	@Override
	public List<KeyValue> getLookupLeaveType(String name, int limit, int offset) {
		return workflowLookupFinder.getLookupLeaveType(name, new RowBounds(offset, limit));
	}

	@Override
	public int countLookupLeaveType(String name) {
		return workflowLookupFinder.countLookupLeaveType(name);
	}

	@Override
	public KeyValue getLookupValueLeaveType(String id) {
		return workflowLookupFinder.getLookupValueLeaveType(id);
	}

	@Override
	public List<KeyValue> getLookupKeyValue(String lookupName, String name,
			int limit, int offset) {
		return workflowLookupFinder.getLookupKeyValue(lookupName, name, new RowBounds(offset, limit));
	}

	@Override
	public int countLookupKeyValue(String lookupName, String name) {
		return workflowLookupFinder.countLookupKeyValue(lookupName, name);
	}

	@Override
	public KeyValue getKeyValue(String lookupName, String id) {
		List<KeyValue> keyValues = workflowLookupFinder.getKeyValue(lookupName, id); 
		return keyValues.size() > 0 ? keyValues.get(0) : null;
	}

	@Override
	public List<KeyValue> getLookupTerminationReason(String name, int limit, int offset) {
		return workflowLookupFinder.getLookupTerminationReason(name, new RowBounds(offset, limit));
	}

	@Override
	public int countLookupTerminationReason(String name) {
		return workflowLookupFinder.countLookupTerminationReason(name);
	}

	@Override
	public KeyValue getLookupValueTerminationReason(String id) {
		return workflowLookupFinder.getLookupValueTerminationReason(id);
	}

	@Override
	public List<KeyValue> getLookupLoanType(String name,
			int limit, int offset) {
		return workflowLookupFinder.getLookupLoanType(name, new RowBounds(offset, limit));
	}

	@Override
	public int countLookupLoanType(String name) {
		return workflowLookupFinder.countLookupLoanType(name);
	}

	@Override
	public KeyValue getLookupValueLoanType(String id) {
		return workflowLookupFinder.getLookupValueLoanType(id);
	}

	@Override
	public List<KeyValue> getAllEmployee() {
		return workflowLookupFinder.getAllEmployee();
	}

	@Override
	public List<KeyValue> getAllEmployeeCriteria(String employeeNumber,
			String fullName, int limit, int offset) {
		return workflowLookupFinder.getAllEmployeeCriteria(employeeNumber, fullName, new RowBounds(offset, limit));
	}

	@Override
	public int countAllEmployeeCriteria(String employeeNumber, String fullName) {
		return workflowLookupFinder.countAllEmployeeCriteria(employeeNumber, fullName);
	}

	@Override
	public KeyValue getValueEmployee(UUID personUUID) {
		return workflowLookupFinder.getValueEmployee(personUUID);
	}

	@Override
	public List<KeyValue> getApproverByName(String approverName) {
		return workflowLookupFinder.getApproverByName(approverName);
	}

	@Override
	public List<KeyValue> getAllPendingTransactionEmployee(Long personId,
			Long companyId) {
		List<KeyValue> listTranscation = new ArrayList<KeyValue>();
		// get transaction from benefit, business trip, leave, loan, overtime
		listTranscation.addAll(workflowLookupFinder.getPendingTransactionBenefit(personId, companyId));
		listTranscation.addAll(workflowLookupFinder.getPendingTransactionBusinessTrip(personId, companyId));
		listTranscation.addAll(workflowLookupFinder.getPendingTransactionLeave(personId, companyId));
		listTranscation.addAll(workflowLookupFinder.getPendingTransactionLoan(personId, companyId));
		listTranscation.addAll(workflowLookupFinder.getPendingTransactionOvertime(personId, companyId));
		
		return listTranscation;
	}

}
