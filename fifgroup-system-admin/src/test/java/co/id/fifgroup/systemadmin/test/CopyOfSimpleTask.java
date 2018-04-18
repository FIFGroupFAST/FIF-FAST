package co.id.fifgroup.systemadmin.test;

import java.util.Date;

import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.service.TaskService;

public class CopyOfSimpleTask extends ExecutableTask{
//	oneTimeMonthlyEntitlementServiceImpl = (EntitlementExecuteableTask) getApplicationContext().getBean("oneTimeMonthlyEntitlementServiceImpl");
	
	private TaskService taskServiceImpl;

	@Override
	public void execute() throws TaskExecutionException {
		taskServiceImpl = (TaskService) getApplicationContext().getBean("taskServiceImpl");
		
		debug("begin creating task");
		try{
			TaskDTO task = new TaskDTO();
			task.setClassName("lalala");
			task.setHasStaticParams(false);
			task.setTaskName("taskTEstScheduler");
			task.setCreatedBy(-1L);
			task.setCreationDate(new Date());
			task.setDescription("lilili0");
			task.setExecutableFileId(-1L);
			task.setLastUpdateDate(new Date());
			task.setLastUpdatedBy(-1L);
			task.setTaskType(TaskType.BATCH.getValue());
			Long iniNull = null;
			Long dibikinNull = iniNull*2L;
			System.out.println(dibikinNull);
			
			taskServiceImpl.save(task);
			debug("task successfully created");
		}catch (Exception e) {
			debug("error creating task");
			debug("task wasn't created, error caused by : ");
			debug(e.getMessage());
			throw new TaskExecutionException(e);
		}
		
	}


}
