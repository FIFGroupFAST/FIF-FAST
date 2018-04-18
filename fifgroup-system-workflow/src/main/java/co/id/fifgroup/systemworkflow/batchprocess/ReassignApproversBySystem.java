package co.id.fifgroup.systemworkflow.batchprocess;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.core.validation.TaskExecutionException;
import co.id.fifgroup.personneladmin.service.PrimaryAssignmentService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.service.WorkflowLookupServiceAdapter;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public class ReassignApproversBySystem extends ExecutableTask {
	
	private static final Logger logger = LoggerFactory.getLogger(ReassignApproversBySystem.class);
	
	@Override
	public void execute() throws TaskExecutionException {
		List<AVMOutgoingReport> dataList = new ArrayList<AVMOutgoingReport>();
		Long companyId = Long.parseLong(getParameter().get(StaticParameterKey.COMPANY_ID.name()).toString());		
		
		try {
			List<Long> listPerson = getPrimaryAssignmentService().getPersonChangeAssignment(companyId);
			for (Long personId : listPerson) {
				// get transaction from benefit, business trip, leave, loan, overtime
				List<KeyValue> listTrxId = new ArrayList<KeyValue>();
				listTrxId.addAll(geWorkflowLookupServiceAdapter().getAllPendingTransactionEmployee(personId, companyId));
				dataList = getAvmAdapterService().getPendingTransaction(listTrxId);
				if (dataList.size() > 0) {
					getAvmAdapterService().doMultipleReassignBySystem(dataList);
				}						
			}
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private AvmAdapterService getAvmAdapterService() {
		return (AvmAdapterService) getApplicationContext().getBean("avmAdapterServiceImpl");
	}
	
	private PrimaryAssignmentService getPrimaryAssignmentService() {
		return (PrimaryAssignmentService) getApplicationContext().getBean("primaryAssignmentService");
	}
	
	private WorkflowLookupServiceAdapter geWorkflowLookupServiceAdapter() {
		return (WorkflowLookupServiceAdapter) getApplicationContext().getBean("workflowLookupServiceAdapterImpl");
	}

}
