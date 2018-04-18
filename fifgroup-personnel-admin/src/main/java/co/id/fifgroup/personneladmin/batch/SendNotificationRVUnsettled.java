package co.id.fifgroup.personneladmin.batch;

import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.service.ExecuteableTaskServiceAdapter;
import co.id.fifgroup.core.task.ExecutableTask;

public class SendNotificationRVUnsettled extends ExecutableTask {

	private ExecuteableTaskServiceAdapter sendNotificationRvUnsettledImpl;
	
	@Override
	public void execute() throws TaskExecutionException {
		debug("Executing Send Notification RV Unsettled");
		sendNotificationRvUnsettledImpl = (ExecuteableTaskServiceAdapter) getApplicationContext().getBean("sendNotificationRvUnsettledImpl");
		sendNotificationRvUnsettledImpl.execute(getParameter());
		debug("Finished");
	}	
}
