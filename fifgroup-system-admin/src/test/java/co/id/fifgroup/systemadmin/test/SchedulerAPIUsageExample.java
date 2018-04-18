package co.id.fifgroup.systemadmin.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import co.id.fifgroup.core.util.DateUtil;

import co.id.fifgroup.systemadmin.constant.TaskDataType;
import co.id.fifgroup.systemadmin.constant.TaskEndDateType;
import co.id.fifgroup.systemadmin.constant.TaskExecutionType;
import co.id.fifgroup.systemadmin.constant.TaskScheduleType;
import co.id.fifgroup.systemadmin.dto.Schedule;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;
import co.id.fifgroup.systemadmin.service.TaskRequestService;

public class SchedulerAPIUsageExample {
	
	/*
	 * Initialize any service you may need.
	 */
	@Autowired
	private TaskRequestService taskRequestService;

	
	/*
	 * create the body of the scheduler
	 */
	private String createSchedulerExample(){
		/*
		 * return jobKey if needed.
		 */
		String jobKey;
		
		/*
		 * initialize parameter(s).
		 */
		List<TaskParameterDTO> taskParameters = new ArrayList<TaskParameterDTO>();
		/*
		 * insert some parameters using taskRequestService.taskParameterBuilder, with parameterType etc, just like example below.
		 */
		taskParameters.add(taskRequestService.taskParameterBuilder(TaskDataType.TEXT.toString(), (String) "value1", "key1"));
		taskParameters.add(taskRequestService.taskParameterBuilder(TaskDataType.TEXT.toString(), (String) "value2", "key2"));
		
		
		/*
		 * initialize and create a schedule.
		 */
		Schedule schedule = new Schedule();
		
		/*
		 * example for Immediately schedule
		 */
		schedule.setScheduleType(TaskScheduleType.IMMEDIATELY.getValue());
		
		/*
		 * example for Daily on every x days interval
		 */
		schedule.setScheduleType(TaskScheduleType.DAILY.getValue());
		schedule.setStartTime(new Date()); //date mean, dd-MMM-yyyy format, time(hour) will be ignored.
		schedule.setExecutionTime(DateUtil.changeDateToOrigin(new Date().getTime()));
		//time mean, HH:mi format, date will be ignored, better use DateUtil.changeDateToOrigin to make the date on 01-JAN-1970.
		
		/*
		 * example for Daily on every weekday
		 */
		
		
		
//		schedule.setScheduleType(TaskScheduleType.DAILY.getValue());
//		schedule.setDayNum(2);
//		schedule.setRecurEvery(2);
//		schedule.setPattern(TaskDailyPattern.WEEKDAY.getValue());
//		schedule.setStartTime(new Date());
//		schedule.setExecutionTime(new Date());
//		schedule.setEndType(TaskEndDateType.END_BY_DATE.getValue());
//		schedule.setEndDate(DateUtil.add(new Date(), Calendar.DATE, 3));
//		schedule.setAfterOccurence(5);
		
		
		schedule.setEndType(TaskEndDateType.END_AFTER.getValue());//if schedule specified as end after x occurence do not forget to setAfterOccurence
		schedule.setAfterOccurence(1);//for example afterOccurence is set to be 1.
				
		schedule.setEndType(TaskEndDateType.END_BY_DATE.getValue());//if schedule specified as end by date, do not forget to specify the end date.
		schedule.setEndDate(DateUtil.add(new Date(), Calendar.DATE, 7));//for example i pick a week later for end date.
		
		
		TaskDTO task = taskRequestService.taskBuilder(taskParameters, "co.id.fifgroup.systemadmin.test.SimpleTask", null);
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();
		tasks.add(task);
		jobKey = taskRequestService.buildSchedule(tasks, schedule, TaskExecutionType.SEQUENTIAL.getValue());
		return jobKey;
	}
}
