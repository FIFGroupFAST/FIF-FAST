package co.id.fifgroup.ssoa.common;

import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.validation.TaskExecutionException;
import co.id.fifgroup.ssoa.service.NotificationSOInputService;

@VariableResolver(DelegatingVariableResolver.class)
public class NotificationSOInputJob extends ExecutableTask {

	@Override
	public void execute() throws TaskExecutionException {
		// TODO Auto-generated method stub
		
		NotificationSOInputService notificationSOInputService = (NotificationSOInputService)getApplicationContext().getBean("notificationSOInputService");
		System.out.println("notificationSOInputService==" + notificationSOInputService);
		
		notificationSOInputService.sendMessage();
    	
	}   

	}