package co.id.fifgroup.systemadmin.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;

import co.id.fifgroup.systemadmin.constant.DayName;
import co.id.fifgroup.systemadmin.constant.TaskDailyPattern;
import co.id.fifgroup.systemadmin.constant.TaskDataType;
import co.id.fifgroup.systemadmin.constant.TaskEndDateType;
import co.id.fifgroup.systemadmin.constant.TaskExecutionType;
import co.id.fifgroup.systemadmin.constant.TaskScheduleType;
import co.id.fifgroup.systemadmin.dto.Schedule;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;
import co.id.fifgroup.systemadmin.service.TaskRequestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
@Transactional(readOnly=false)
@TransactionConfiguration(defaultRollback=false)
public class SchedulingAPITest {

	@Autowired
	private TaskRequestService taskRequestService;
	
	@Test
	public void runTest(){
		
//		String oneTimeJobKey = ""; Daily Every x Days
//		List<TaskParameterDTO> taskParameters = new ArrayList<TaskParameterDTO>();
//		Schedule schedule = new Schedule();
//		schedule.setScheduleType(TaskScheduleType.DAILY.getValue());
//		schedule.setPattern(TaskDailyPattern.EVERYDAYS.getValue());
//		schedule.setRecurEvery(xDay);
//		schedule.setStartTime(new Date());
//		schedule.setExecutionTime(getExecutionTime());
//		schedule.setEndType(TaskEndDateType.END_BY_DATE.getValue());
//		schedule.setEndDate(DateUtil.add(new Date(), Calendar.DATE, 8));
		
		String oneTimeJobKey = ""; /*Daily Every WeekDays*/
		Schedule schedule = new Schedule();
		schedule.setScheduleType(TaskScheduleType.ONE_TIME.getValue());
//		schedule.setPattern(TaskDailyPattern.EVERYDAYS.getValue());
//		schedule.setDayNum(DayName.SUNDAY.getKey());
		schedule.setStartTime(getStartTime());
		schedule.setExecutionTime(getExecutionTime());
		schedule.setEndType(TaskEndDateType.END_AFTER.getValue());
		
		
		
//		String oneTimeJobKey = ""; WEEKLY TEST
//		List<TaskParameterDTO> taskParameters = new ArrayList<TaskParameterDTO>();
//		Schedule schedule = new Schedule();
//		schedule.setScheduleType(TaskScheduleType.WEEKLY.getValue());
//		schedule.setRecurEvery(1);
//		schedule.setDayNum(DayName.SUNDAY.getKey());
//		schedule.setStartTime(new Date());
//		schedule.setExecutionTime(getExecutionTime());
//		schedule.setEndType(TaskEndDateType.END_BY_DATE.getValue());
//		schedule.setEndDate(DateUtil.add(new Date(), Calendar.DATE, 8));
		
		
//		String oneTimeJobKey = ""; //Monthly TEST pattern TaskMonthlyPattern.DAY OK
//		List<TaskParameterDTO> taskParameters = new ArrayList<TaskParameterDTO>();
//		Schedule schedule = new Schedule();
//		schedule.setScheduleType(TaskScheduleType.MONTHLY.getValue());
//		schedule.setPattern(TaskMonthlyPattern.DAY.getValue());
//		schedule.setDateNum(4);
//		schedule.setMonthlyMonthNum(2);
		
//		String oneTimeJobKey = ""; //Monthly TEST pattern TaskMonthlyPattern.THE OK
//		List<TaskParameterDTO> taskParameters = new ArrayList<TaskParameterDTO>();
//		Schedule schedule = new Schedule();
//		schedule.setScheduleType(TaskScheduleType.MONTHLY.getValue());
//		schedule.setPattern(TaskMonthlyPattern.THE.getValue());
//		schedule.setTheSequence(TaskSequenceType.SECOND.getValue());
//		schedule.setTheDayName(DayName.TUESDAY.getValue());
//		schedule.setMonthlyMonthNum(2);
		
		
//		String oneTimeJobKey = ""; //Yearly TEST pattern TaskYearlyPattern.MONTH.getValue() OK
//		List<TaskParameterDTO> taskParameters = new ArrayList<TaskParameterDTO>();
//		Schedule schedule = new Schedule();
//		schedule.setScheduleType(TaskScheduleType.YEARLY.getValue());
//		schedule.setPattern(TaskYearlyPattern.MONTH.getValue());
//		schedule.setMonthlyMonthName(MonthName.APR.getValue());
//		schedule.setRecurEvery(2);
//		schedule.setStartTime(getStartTime());
//		schedule.setExecutionTime(getExecutionTime());
//		schedule.setEndType(TaskEndDateType.END_AFTER.getValue());
//		schedule.setAfterOccurence(2);
		
		
		
//		String oneTimeJobKey = ""; //Yearly TEST pattern TaskYearlyPattern.MONTH.getValue() OK
//		List<TaskParameterDTO> taskParameters = new ArrayList<TaskParameterDTO>();
//		Schedule schedule = new Schedule();
//		schedule.setScheduleType(TaskScheduleType.YEARLY.getValue());
//		schedule.setPattern(TaskYearlyPattern.DAY.getValue());
//		schedule.setMonthlyMonthName(MonthName.APR.getValue());
//		schedule.setTheSequence(TaskSequenceType.FOURTH.getValue());
//		schedule.setTheDayName(DayName.TUESDAY.getValue());
//		schedule.setRecurEvery(2);
//		schedule.setStartTime(getStartTime());
//		schedule.setExecutionTime(getExecutionTime());
//		schedule.setEndType(TaskEndDateType.END_AFTER.getValue());
//		schedule.setAfterOccurence(2);
		
		
//		String oneTimeJobKey = ""; //Yearly TEST pattern TaskYearlyPattern.MONTH.getValue() OK
		List<TaskParameterDTO> taskParameters = new ArrayList<TaskParameterDTO>();
		taskParameters.add(taskRequestService.taskParameterBuilder(TaskDataType.TEXT.toString(), "value1", "value1"));
		taskParameters.add(taskRequestService.taskParameterBuilder(TaskDataType.TEXT.toString(), "value2", "value2"));
//		
//		schedule.setScheduleType(TaskScheduleType.IMMEDIATELY.getValue());
//		schedule.setStartTime(getStartTime());
//		schedule.setExecutionTime(getExecutionTime());
//		schedule.setEndType(TaskEndDateType.END_AFTER.getValue());
//		schedule.setAfterOccurence(2);
		
//		TaskDTO task = taskRequestService.taskBuilder(taskParameters,"co.id.fifgroup.systemadmin.test.SimpleTask", null);
//		TaskDTO task_ = taskRequestService.taskBuilder(taskParameters,"co.id.fifgroup.systemadmin.test.CopyOfSimpleTask", null);
		
//		List<TaskDTO> tasks = new ArrayList<TaskDTO>();
//		tasks.add(task);
//		tasks.add(task_);
//		oneTimeJobKey = taskRequestService.buildScheduleWithCurrentRunner(572L, schedule, TaskExecutionType.SEQUENTIAL.getValue(), taskParameters);
	}
	
	private Date getExecutionTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 17);
		cal.set(Calendar.MINUTE, 17);
		cal.set(Calendar.SECOND, 17);
		
		
		Date time = cal.getTime();
		return DateUtil.changeDateToOrigin(time.getTime());
	}
	private Date getStartTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
		
		
		Date time = cal.getTime();
		return time;
	}
}
