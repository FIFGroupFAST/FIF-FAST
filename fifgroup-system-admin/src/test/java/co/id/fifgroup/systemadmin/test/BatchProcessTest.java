package co.id.fifgroup.systemadmin.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.systemadmin.batch.DirectResponsibilityNotification;
import co.id.fifgroup.systemadmin.batch.JobResponsibilitySynchronizer;
import co.id.fifgroup.systemadmin.batch.UnusedFileRemover;

public class BatchProcessTest {
	
	
	@Test
	public void runBatch(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:**/applicationContext-test.xml");
		
		//ExecutableTask task = new JobResponsibilitySynchronizer();
		/*ExecutableTask task = new UnusedFileRemover();*/
		ExecutableTask task = new DirectResponsibilityNotification();
		
		Map<String, Object> taskParameter = new HashMap<String, Object>();
		taskParameter.put("DELTATIME", 7);
		
		Map<String, String> loggerParam = new HashMap<>();
		loggerParam.put("taskName", "Test");
		loggerParam.put("taskId", "Test");
		loggerParam.put("taskRunnerId","Test");
		loggerParam.put("taskRequestid", "Test");
		
		String filePath = "D://batchLog.txt";
		
		task.setParameter(taskParameter);
		task.setApplicationContext(context);
		//task.setLogger(filePath, loggerParam);
		try {
			task.execute();
			task.closeLogger("Test", "Test");
		} catch (TaskExecutionException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
}
