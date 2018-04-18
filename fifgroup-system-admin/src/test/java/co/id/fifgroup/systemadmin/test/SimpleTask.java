package co.id.fifgroup.systemadmin.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.service.TaskService;

public class SimpleTask extends ExecutableTask{
//	oneTimeMonthlyEntitlementServiceImpl = (EntitlementExecuteableTask) getApplicationContext().getBean("oneTimeMonthlyEntitlementServiceImpl");
	
	private TaskService taskServiceImpl;
	private Map<String, Object> param = new HashMap<String, Object>();

	@Override
	public void execute() throws TaskExecutionException {
		param = getParameter();
		taskServiceImpl = (TaskService) getApplicationContext().getBean("taskServiceImpl");
		
		debug("begin creating task");
		try{
			TaskDTO task = new TaskDTO();
			String value1 = (String) param.get("value1");
			String value2 = (String) param.get("value2");
			task.setClassName(value1);
			task.setHasStaticParams(false);
			task.setTaskName("taskTEstScheduler");
			task.setCreatedBy(-1L);
			task.setCreationDate(new Date());
			task.setDescription(value2);
			task.setExecutableFileId(-1L);
			task.setLastUpdateDate(new Date());
			task.setLastUpdatedBy(-1L);
			task.setTaskType(TaskType.BATCH.getValue());
			
			taskServiceImpl.save(task);
			debug("task successfully created");
			
			Thread.sleep(100*60*10);
		}catch (Exception e) {
			debug("error creating task");
			debug("task wasn't created, error caused by : ");
			debug(e.getMessage());
			throw new TaskExecutionException(e);
		}
		
	}


}
