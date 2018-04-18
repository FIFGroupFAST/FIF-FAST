package co.id.fifgroup.systemworkflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.systemworkflow.constants.WorkflowReference;
import co.id.fifgroup.systemworkflow.service.RuleLookupService;

import co.id.fifgroup.core.service.WorkflowLookupServiceAdapter;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@Transactional
@Service
public class RuleLookupServiceImpl implements RuleLookupService {

	@Autowired
	private WorkflowLookupServiceAdapter worklfowLookupServiceAdapterImpl;
	
	@Override
	public List<KeyValue> getLookups(String searchCriteria,
			String attributeLabel, int limit, int offset) {
		
		if (attributeLabel.equalsIgnoreCase(WorkflowReference.JOB.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupJob(searchCriteria, limit, offset);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.GRADE.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupGrade(searchCriteria, limit, offset);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.ORGANIZATION.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupOrganization(searchCriteria, limit, offset);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.LOCATION.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupLocation(searchCriteria, limit, offset);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.JOB_GROUP.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupKeyValue(WorkflowReference.WSU_JOB_GROUP.name(), searchCriteria, limit, offset);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.TERMINATION_REASON.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupTerminationReason(searchCriteria, limit, offset);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.LOAN_TYPE.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupLoanType(searchCriteria, limit, offset);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.LEAVE_TYPE.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupLeaveType(searchCriteria, limit, offset);
		}
		
		return null;
	}

	@Override
	public int countLookups(String searchCriteria, String attributeLabel) {
		if (attributeLabel.equalsIgnoreCase(WorkflowReference.JOB.name())) {
			return worklfowLookupServiceAdapterImpl.countLookupJob(searchCriteria);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.GRADE.name())) {
			return worklfowLookupServiceAdapterImpl.countLookupGrade(searchCriteria);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.ORGANIZATION.name())) {
			return worklfowLookupServiceAdapterImpl.countLookupOrganization(searchCriteria);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.LOCATION.name())) {
			return worklfowLookupServiceAdapterImpl.countLookupLocation(searchCriteria);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.JOB_GROUP.name())) {
			return worklfowLookupServiceAdapterImpl.countLookupKeyValue(WorkflowReference.WSU_JOB_GROUP.name(), searchCriteria);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.TERMINATION_REASON.name())) {
			return worklfowLookupServiceAdapterImpl.countLookupTerminationReason(searchCriteria);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.LOAN_TYPE.name())) {
			return worklfowLookupServiceAdapterImpl.countLookupLoanType(searchCriteria);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.LEAVE_TYPE.name())) {
			return worklfowLookupServiceAdapterImpl.countLookupLeaveType(searchCriteria);
		}
		return 0;
	}

	@Override
	public KeyValue getLookupValue(String attributeLabel, String key) {
		if (attributeLabel.equalsIgnoreCase(WorkflowReference.JOB.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupValueJob(key);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.GRADE.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupValueGrade(key);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.ORGANIZATION.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupValueOrganization(key);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.LOCATION.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupValueLocation(key);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.JOB_GROUP.name())) {
			return worklfowLookupServiceAdapterImpl.getKeyValue(WorkflowReference.WSU_JOB_GROUP.name(), key);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.TERMINATION_REASON.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupValueTerminationReason(key);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.LOAN_TYPE.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupValueLoanType(key);
		} else if (attributeLabel.equalsIgnoreCase(WorkflowReference.LEAVE_TYPE.name())) {
			return worklfowLookupServiceAdapterImpl.getLookupValueLeaveType(key);
		}
		return null;
	}

}
