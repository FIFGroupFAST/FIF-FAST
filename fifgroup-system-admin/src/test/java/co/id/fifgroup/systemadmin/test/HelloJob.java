package co.id.fifgroup.systemadmin.test;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.task.ExecutableTask;

public class HelloJob implements Job, JobListener{
	private ExecutableTask executebleTask;
	static String jobListenerName = "HELLO_JOB_LISTENER";
	static String  jobName;
	static String  jobGroup;
	static int executionCount;
	JobKey jobKey;
	

	@Override
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		jobKey = context.getJobDetail().getKey();
		jobName = data.getString("name");
		jobGroup = data.getString("group");
		execute();
		
	}

	private void execute() {
		System.out.println("execute");
			try {
				Class<?> clazz = Class.forName("co.id.fifgroup.systemadmin.task.SimpleTask");
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("name", jobName);
					param.put("group", jobGroup);
					executebleTask = (ExecutableTask) clazz.newInstance();
					executebleTask.setParameter(param);
//					executebleTask.setApplicationContext(SpringUtil.getApplicationContext());
					try {
						
						executebleTask.execute();
						
					} catch (TaskExecutionException e) {
						e.printStackTrace();
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return jobListenerName;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		String jobName = context.getJobDetail().getKey().toString();
		JobDataMap data = context.getJobDetail().getJobDataMap();
		executionCount++;
		data.put("executionCount", executionCount);
		System.out.println("job with jobName : "+jobName + " is going to be executed");
		
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println("jobExecutionVetoed");
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("job with jobName : "+jobName + " is going to be finished");
		System.out.println("job have been executed "+ data.getInt("executionCount") + " times");
		if(data.getInt("executionCount") == 5 ){
			try {
				context.getScheduler().deleteJob(context.getJobDetail().getKey());
//				context.getScheduler().shutdown();
				System.out.println("deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



}
