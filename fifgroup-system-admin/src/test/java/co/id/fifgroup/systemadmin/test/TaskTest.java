package co.id.fifgroup.systemadmin.test;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;

public class TaskTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		 SchedulerFactory sf = new StdSchedulerFactory();
//	     try {
//			Scheduler sched = sf.getScheduler();
//			String schedId = sched.getSchedulerInstanceId();
//			JobDetail job = newJob(SimpleTask.class) 
//	                .withIdentity("job_test", schedId) // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
//	                .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went down...
//	                .build();
//
//	            
//	            SimpleTrigger trigger = newTrigger()
//	                .withIdentity("triger_test", schedId)
//	                .startAt(futureDate(1, IntervalUnit.SECOND))
//	                .withSchedule(simpleSchedule().withIntervalInSeconds(5).withRepeatCount(20))
//	                .build();
//	                
//
//	            System.out.println(job.getKey() +
//	                    " will run at: " + trigger.getNextFireTime() +  
//	                    " and repeat: " + trigger.getRepeatCount() + 
//	                    " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
//	            sched.scheduleJob(job, trigger);
//	            sched.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}

	}

}
