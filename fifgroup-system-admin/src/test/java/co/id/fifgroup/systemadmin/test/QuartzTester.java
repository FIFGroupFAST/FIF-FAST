package co.id.fifgroup.systemadmin.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

public class QuartzTester{

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		try {
			
			Scheduler scheduler;
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			
			
			Map<String, Object> taskParameter = new HashMap<String, Object>();
			taskParameter.put("name", "job1");
			taskParameter.put("group","group1");

			JobDataMap jobData = new JobDataMap(taskParameter);
			Random random = new Random();
			
			JobKey jobKey = new JobKey(((Long) random.nextLong()).toString());
			
			
			JobDetail job1 = JobBuilder.newJob(HelloJob.class)
					.withIdentity(jobKey)
					.storeDurably()
					.usingJobData(jobData)
					.build();
			
			Trigger jobTrigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
					.build();
			
			
			scheduler.getListenerManager().addJobListener(new HelloJob(), KeyMatcher.keyEquals(jobKey));
			scheduler.scheduleJob(job1, jobTrigger);
			
			scheduler.getListenerManager().getJobListener(jobKey.toString());
			try {
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scheduler.deleteJob(jobKey);
			System.out.println("terhapus");
					
			
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}



}
