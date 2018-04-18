package co.id.fifgroup.systemadmin.util;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import co.id.fifgroup.systemadmin.constant.TaskPhase;
import co.id.fifgroup.systemadmin.constant.TaskStatus;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetail;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetailExample;
import co.id.fifgroup.systemadmin.service.TaskRequestService;


public class TaskRunnerShutdownHookPlugin implements SchedulerPlugin{
	
	private final Logger log = LoggerFactory.getLogger(TaskRunnerShutdownHookPlugin.class);
	private ApplicationContext context;
	
	private boolean cleanShutdown = true;

	@Override
	public void initialize(String name, final Scheduler scheduler)
			throws SchedulerException {
		log.info("Registering Quartz shutdown hook.");
		
		context = (ApplicationContext) scheduler.getContext().get("applicationContext");

        Thread t = new Thread("Quartz Shutdown-Hook "
                + scheduler.getSchedulerName()) {
            @Override
            public void run() {
                log.info("Shutting down Quartz...");
                try {
                    scheduler.shutdown(isCleanShutdown());
                } catch (SchedulerException e) {
                    log.info(
                            "Error shutting down Quartz: " + e.getMessage(), e);
                }
            }
        };

        Runtime.getRuntime().addShutdownHook(t);
		
	}
	

	public boolean isCleanShutdown() {
		return cleanShutdown;
	}



	public void setCleanShutdown(boolean cleanShutdown) {
		this.cleanShutdown = cleanShutdown;
	}



	@Override
	public void start() {
		// Do nothing.
		
	}

	@Override
	public void shutdown() {
		// clean all task request status where RUNNING, NORMAL to PENDING, NORMAL
//		log.info("Cleaning Task Request Detail...");
//		TaskRequestService service = (TaskRequestService) context.getBean("taskRequestService");
//		TaskRequestDetailExample example = new TaskRequestDetailExample();
//		example.or()
//			.andTaskPhaseEqualTo(TaskPhase.RUNNING.getValue())
//			.andTaskStatusEqualTo(TaskStatus.NORMAL.getValue());
//		
//		TaskRequestDetail record = new TaskRequestDetail();
//		record.setTaskPhase(TaskPhase.PENDING.getValue());
//		service.updateRequestDetailByExample(record, example);
//		log.info("Task Request Detail cleaning complete.");
		
	}	
	

}
