package co.id.fifgroup.systemadmin.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import com.google.common.base.Strings;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.service.TaskRequestServiceAdapter;
import co.id.fifgroup.systemadmin.constant.DayName;
import co.id.fifgroup.systemadmin.constant.MonthName;
import co.id.fifgroup.systemadmin.constant.TaskDailyPattern;
import co.id.fifgroup.systemadmin.constant.TaskEndDateType;
import co.id.fifgroup.systemadmin.constant.TaskExecutionType;
import co.id.fifgroup.systemadmin.constant.TaskMonthlyPattern;
import co.id.fifgroup.systemadmin.constant.TaskPhase;
import co.id.fifgroup.systemadmin.constant.TaskScheduleType;
import co.id.fifgroup.systemadmin.constant.TaskSequenceType;
import co.id.fifgroup.systemadmin.constant.TaskStatus;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.constant.TaskYearlyPattern;
import co.id.fifgroup.systemadmin.dao.ExecutableFileMapper;
import co.id.fifgroup.systemadmin.dao.TaskMapper;
import co.id.fifgroup.systemadmin.dao.TaskParameterMapper;
import co.id.fifgroup.systemadmin.dao.TaskParameterValueMapper;
import co.id.fifgroup.systemadmin.dao.TaskRequestDetailMapper;
import co.id.fifgroup.systemadmin.dao.TaskRequestMapper;
import co.id.fifgroup.systemadmin.dao.TaskRunnerDetailMapper;
import co.id.fifgroup.systemadmin.dao.TaskRunnerMapper;
import co.id.fifgroup.systemadmin.domain.ExecutableFile;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.domain.TaskExample;
import co.id.fifgroup.systemadmin.domain.TaskParameter;
import co.id.fifgroup.systemadmin.domain.TaskParameterExample;
import co.id.fifgroup.systemadmin.domain.TaskParameterValue;
import co.id.fifgroup.systemadmin.domain.TaskParameterValueExample;
import co.id.fifgroup.systemadmin.domain.TaskRequest;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetail;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetailExample;
import co.id.fifgroup.systemadmin.domain.TaskRequestExample;
import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerDetail;
import co.id.fifgroup.systemadmin.domain.TaskRunnerDetailExample;
import co.id.fifgroup.systemadmin.domain.TaskRunnerExample;
import co.id.fifgroup.systemadmin.dto.ExecutableTaskDTO;
import co.id.fifgroup.systemadmin.dto.Schedule;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;
import co.id.fifgroup.systemadmin.dto.TaskRequestDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;
import co.id.fifgroup.systemadmin.finder.TaskRequestFinder;
import co.id.fifgroup.systemadmin.service.TaskRequestService;
import co.id.fifgroup.systemadmin.service.TaskRunnerService;
import co.id.fifgroup.systemadmin.service.TaskService;
import co.id.fifgroup.systemadmin.task.TaskRunnerManager;

@Transactional
@Service("taskRequestService")
public class TaskRequestServiceImpl implements TaskRequestService, TaskRequestServiceAdapter {

	private static Logger log = LoggerFactory
			.getLogger(TaskRequestServiceImpl.class);

	// Task Request Entity
	@Autowired
	private TaskRequestMapper taskRequestMapper;
	@Autowired
	private TaskRequestDetailMapper taskRequestDetailMapper;
	@Autowired
	private TaskParameterValueMapper taskParameterValueMapper;

	// Finder
	@Autowired
	private TaskRequestFinder taskRequestFinder;

	// Other Module
	@Autowired
	private TaskRunnerService taskRunnerService;
	@Autowired
	private TaskService taskServiceImpl;
	/**uncommment for local scheduler */
	@Autowired(required = false)
//	@Autowired
	private Scheduler scheduler;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ExecutableFileMapper executableFileMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private TaskParameterMapper taskParameterMapper;
	@Autowired
	private TaskRunnerMapper taskRunnerMapper;
	@Autowired
	private TaskRunnerDetailMapper taskRunnerDetailMapper;	
	
	// private void findTaskRequestByTaskRequestId(Long taskRequestId){
	// taskRequestMapper
	// }

	@Override
	@Transactional
	public void save(TaskRequestDTO taskRequestDto) throws Exception {
		List<ExecutableTaskDTO> executableTaskDtos = new ArrayList<>();
		String key = "";
		Object value = null;
		ExecutableTaskDTO taskRequest = convertDomain(taskRequestDto);
		Long randomedParentRequestId = new Random().nextLong();
		taskRequest.setParentRequestId(randomedParentRequestId.toString()
				.substring(0, 10));
		TaskRunnerDTO taskRunner = taskRunnerService.getTaskRunnerById(taskRequest
				.getTaskRunnerId());
		List<TaskParameterValue> listOfValue = taskRequestDto.getTaskParameterValues();
		int indexTaskRunnerDetailDto = 0;
		int indexListOfValue = 0;
		for (TaskRunnerDetailDTO trd : taskRunner.getTaskRunnerDetailDto()) {
			ExecutableTaskDTO executtableDtoClone = null;
			Map<String, Object> keyValue = new HashMap<>();
			try {
				executtableDtoClone = (ExecutableTaskDTO) ((ExecutableTaskDTO) taskRequest)
						.clone();
			} catch (CloneNotSupportedException e1) {
				log.error(e1.getMessage(), e1);
			}

			int indexOfTaskParameterValues = 0;
			TaskDTO task = trd.getMainTask();
			executtableDtoClone.setTaskId(task.getId());
			taskRequestMapper.insertSelective(executtableDtoClone);
			for (TaskParameterDTO tpDto : task.getTaskParameters()) {
				key = tpDto.getKey();

				value = listOfValue.get(indexListOfValue).getValue();
				TaskParameterValue taskParameterValue = new TaskParameterValue();
				taskParameterValue
						.setTaskRequestId(executtableDtoClone.getId());
				taskParameterValue.setTaskParameterId(tpDto.getId());
				// System.out.println(executtableDtoClone.getCreatedBy());
				taskParameterValue.setCreatedBy(executtableDtoClone
						.getCreatedBy());
				taskParameterValue.setCreationDate(executtableDtoClone
						.getCreationDate());
				taskParameterValue.setLastUpdateDate(executtableDtoClone
						.getLastUpdateDate());
				taskParameterValue.setLastUpdatedBy(executtableDtoClone
						.getLastUpdatedBy());
				taskParameterValue.setValue(value);
				taskRunner.getTaskRunnerDetailDto()
						.get(indexTaskRunnerDetailDto).getMainTask()
						.getTaskParameters().get(indexOfTaskParameterValues)
						.setTaskParameterValue(taskParameterValue);
				taskParameterValueMapper.insert(taskParameterValue);
				indexOfTaskParameterValues++;
				indexListOfValue++;
				keyValue.put(key, value);
			}

			// taskRequestDetailIds.add(requestDetail.getId());

			ExecutableTaskDTO executableTaskDto = new ExecutableTaskDTO();
			TaskDTO mainTask = taskRunner.getTaskRunnerDetailDto()
					.get(indexTaskRunnerDetailDto).getMainTask() == null ? new TaskDTO()
					: taskRunner.getTaskRunnerDetailDto()
							.get(indexTaskRunnerDetailDto).getMainTask();
			TaskDTO successTask = taskRunner.getTaskRunnerDetailDto()
					.get(indexTaskRunnerDetailDto).getSuccessTask() == null ? new TaskDTO()
					: taskRunner.getTaskRunnerDetailDto()
							.get(indexTaskRunnerDetailDto).getSuccessTask();
			TaskDTO errorTask = taskRunner.getTaskRunnerDetailDto()
					.get(indexTaskRunnerDetailDto).getErrorTask() == null ? new TaskDTO()
					: taskRunner.getTaskRunnerDetailDto()
							.get(indexTaskRunnerDetailDto).getErrorTask();

			executableTaskDto = modelMapper.map(executtableDtoClone,
					ExecutableTaskDTO.class);
			// executableTaskDto.setRequestDetail(requestDetail);
			executableTaskDto.setExecutionType(taskRunner.getExecutionType());

			if (trd.getSccuessTaskId() == null) {
				trd.setSccuessTaskId(securityServiceImpl.getSecurityProfile().getUserId());
				taskRunnerService.updateTaskRequestDetailByPrimaryKey(trd);
			}

			if (trd.getErrorTaskId() == null) {
				trd.setErrorTaskId(securityServiceImpl.getSecurityProfile().getUserId());
				taskRunnerService.updateTaskRequestDetailByPrimaryKey(trd);
			}

			if (successTask.getId() != null && successTask.getId() != -1) {
				successTask = taskServiceImpl.getTaskById(successTask.getId());
			}

			if (errorTask.getId() != null && errorTask.getId() != -1) {
				errorTask = taskServiceImpl.getTaskById(errorTask.getId());
			}

			executableTaskDto.setMainTask(mainTask);
			executableTaskDto.setSuccessTask(successTask);
			executableTaskDto.setErrorTask(errorTask);
			executableTaskDto.setSequence(trd.getSequence());
			executableTaskDto.setKeyValues(keyValue);
			executableTaskDtos.add(executableTaskDto);
			indexTaskRunnerDetailDto++;
		}
		try {
			runScheduler(executableTaskDtos);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}


	private Date getNextXDay(int calendarDayName) {
		Calendar cal = Calendar.getInstance();
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		if (weekday != Calendar.MONDAY) {
			int days = (Calendar.SATURDAY - weekday + 2) % 7;
			cal.add(Calendar.DAY_OF_YEAR, days);
		}
		System.out.println(cal.getTime());
		
		cal.add(Calendar.DAY_OF_YEAR,(calendarDayName-2));
		cal.add(Calendar.DAY_OF_YEAR,-7);
		Date buffer = cal.getTime();
		if(buffer.before(new Date())){
			cal.add(Calendar.DAY_OF_YEAR,7);
		}
		return cal.getTime();
	}

	private int getDayIntEqualToJavaCalendar(String value) {
		if (value.contains("SUN")) {
			return Calendar.SUNDAY;
		} else if (value.contains("MON")) {
			return Calendar.MONDAY;
		} else if (value.contains("TUE")) {
			return Calendar.TUESDAY;
		} else if (value.contains("WED")) {
			return Calendar.WEDNESDAY;
		} else if (value.contains("THU")) {
			return Calendar.THURSDAY;
		} else if (value.contains("FRI")) {
			return Calendar.FRIDAY;
		} else if (value.contains("SAT")) {
			return Calendar.SATURDAY;
		} else
			return 0;
	}

	@Transactional
	private void runScheduler(List<ExecutableTaskDTO> executableTaskDto)
			throws SchedulerException {
		int counter = 0;
		List<TaskRequestDetail> requestDetails = new ArrayList<>();

		if (executableTaskDto.get(0).getExecutionType()
				.equalsIgnoreCase(TaskExecutionType.SEQUENTIAL.getValue())) {
			TaskRequestDetail taskRequestDetail = new TaskRequestDetail();
			taskRequestDetail.setDateCompleted(null);
			taskRequestDetail.setDateStarted(executableTaskDto.get(0)
					.getStartTime());
			taskRequestDetail.setTaskPhase(TaskPhase.PENDING.getValue());
			taskRequestDetail
					.setTaskRequestId(executableTaskDto.get(0).getId());
			taskRequestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
			taskRequestDetailMapper.insertSelective(taskRequestDetail);
			executableTaskDto.get(0).setRequestDetailId(
					taskRequestDetail.getId());
		} else {
			for (ExecutableTaskDTO etd : executableTaskDto) {
				TaskRequestDetail taskRequestDetail = new TaskRequestDetail();
				taskRequestDetail.setDateCompleted(null);
				taskRequestDetail.setDateStarted(etd.getStartTime());
				taskRequestDetail.setTaskPhase(TaskPhase.PENDING.getValue());
				taskRequestDetail.setTaskRequestId(etd.getId());
				taskRequestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
				taskRequestDetailMapper.insertSelective(taskRequestDetail);
				executableTaskDto.get(counter).setRequestDetailId(
						taskRequestDetail.getId());
				requestDetails.add(taskRequestDetail);

				counter++;
			}
		}
		Map<String, Object> taskParameter = new HashMap<String, Object>();
		taskParameter.put("executableTaskDto", executableTaskDto);
		taskParameter.put("securityProfile",securityServiceImpl.getSecurityProfile());
		taskParameter.put("executionCount", 0);
		Calendar cal = Calendar.getInstance();

		JobDataMap jobParam = new JobDataMap(taskParameter);
		JobKey jobKey = new JobKey(executableTaskDto.get(0)
				.getParentRequestId());

		JobDetail job = JobBuilder.newJob(TaskRunnerManager.class)
				.withIdentity(jobKey).usingJobData(jobParam).storeDurably()
				.build();

		int taskSchedule = TaskScheduleType.getKey(executableTaskDto
				.get(0).getScheduleType());
		Trigger jobTrigger;
		if (taskSchedule == 2) {
			cal.setTime(executableTaskDto.get(0).getStartTime());
			Date triggerStartTime = new Date();

			triggerStartTime = getNextXDay(getDayIntEqualToJavaCalendar(executableTaskDto
					.get(0).getRecurOn()));

			jobTrigger = TriggerBuilder
					.newTrigger()
					.withIdentity(executableTaskDto.get(0).getParentRequestId())
					.withSchedule(
							CalendarIntervalScheduleBuilder
									.calendarIntervalSchedule()
									.withIntervalInWeeks(
											executableTaskDto.get(0)
													.getRecurEvery())
					)
					.startAt(DateUtils.truncate(triggerStartTime, Calendar.DATE))
					.endAt(executableTaskDto.get(0).getEndByDate() == null ? null
							: DateUtil.truncate(executableTaskDto.get(0)
									.getEndByDate())).build();
		} else if (taskSchedule == 0) {
			jobTrigger = TriggerBuilder
					.newTrigger()
					.withIdentity(executableTaskDto.get(0).getParentRequestId())
					.startNow()
//					.endAt(executableTaskDto.get(0).getEndByDate() == null ? null
//							: DateUtil.truncate(executableTaskDto.get(0)
//									.getEndByDate()))
									.build();

		} else if (taskSchedule == 5) {
			jobTrigger = TriggerBuilder
					.newTrigger()
					.withIdentity(executableTaskDto.get(0).getParentRequestId())
					.startAt(executableTaskDto.get(0).getStartTime())
					.endAt(executableTaskDto.get(0).getEndByDate() == null ? null
							: DateUtil.truncate(executableTaskDto.get(0)
									.getEndByDate())).build();
		} else {

			jobTrigger = TriggerBuilder
					.newTrigger()
					.withIdentity(executableTaskDto.get(0).getParentRequestId())
					.startAt(executableTaskDto.get(0).getStartTime())
					.withSchedule(
							CronScheduleBuilder.cronSchedule(executableTaskDto
									.get(0).getCronExpression()))
					.endAt(executableTaskDto.get(0).getEndByDate() == null ? null
							: DateUtil.truncate(executableTaskDto.get(0)
									.getEndByDate())).build();
		}
		scheduler.getListenerManager().addJobListener(new TaskRunnerManager(),
				KeyMatcher.keyEquals(jobKey));
		scheduler.getListenerManager().getJobListener(jobKey.toString());
		scheduler.scheduleJob(job, jobTrigger);

	}

	public int getHourFromDate(Date date) {
		int hour = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	public int getMinuteFromDate(Date date) {
		int hour = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.get(Calendar.MINUTE);
		return hour;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskRequestDTO> getTaskRequestByExample(TaskRequestDTO example,
			int limit, int offset) {
		return taskRequestFinder.selectByExampleWithRowbounds(example,
				new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TaskRequestDTO> getTaskRequestByExample(TaskRequestDTO example) {
		return taskRequestFinder.selectByExampleWithRowbounds(example);
	}

	@Override
	@Transactional(readOnly = true)
	public int countTaskRequestDtoByExample(TaskRequestDTO example) {
		return taskRequestFinder.countTaskRequestDtoByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isRequiredEntryTaskParameter(TaskRunner taskRunner) {
		TaskRunnerDetailExample example = new TaskRunnerDetailExample();
		example.createCriteria().andTaskRunnerHdrIdEqualTo(taskRunner.getId());
		List<TaskRunnerDetail> taskRunnerDetails = taskRunnerService
				.getTaskRunnerDetailByExample(example);
		int taskParamCount = 0;
		for (TaskRunnerDetail taskRunnerDetail : taskRunnerDetails) {
			TaskParameterExample taskParamExample = new TaskParameterExample();
			taskParamExample.createCriteria().andTaskIdEqualTo(
					taskRunnerDetail.getTaskId());
			taskParamCount = taskParamCount
					+ taskServiceImpl
							.countTaskParameterByExample(taskParamExample);
		}
		if (taskParamCount > 0)
			return true;
		else
			return false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskParameter> getTaskParameterByTaskRunnerId(Long id) {
		List<TaskParameter> taskParameters = new ArrayList<TaskParameter>();
		for (TaskRunnerDetail taskRunnerDetail : getTaskRunnerDetailByTaskRunnerId(id)) {
			taskParameters.addAll(getTaskParameterByTaskId(taskRunnerDetail
					.getTaskId()));
		}
		return taskParameters;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> getTaskByTaskRunnerId(Long id) {
		TaskRunnerDTO taskRunnerDto = taskRunnerService.getTaskRunnerById(id);
		List<TaskRunnerDetailDTO> taskRunnerDetailDtos = taskRunnerDto
				.getTaskRunnerDetailDto();
		List<Task> tasks = new ArrayList<Task>();
		for (TaskRunnerDetailDTO taskRunnerDetailDto : taskRunnerDetailDtos) {
			Task task = new Task();
			task.setId(taskRunnerDetailDto.getTaskId());
			task.setTaskName(taskRunnerDetailDto.getMainTask().getTaskName());
			tasks.add(task);
		}
		return tasks;
	}

	@Override
	@Transactional(readOnly = true)
	public TaskParameterValue getTaskParameterValueByTaskParameterIdAndRequestId(
			Long taskParameterId, Long requestId) {
		TaskParameterValueExample example = new TaskParameterValueExample();
		example.createCriteria().andTaskParameterIdEqualTo(taskParameterId)
				.andTaskRequestIdEqualTo(requestId);
		List<TaskParameterValue> taskParameterValues = taskParameterValueMapper
				.selectByExample(example);
		if (taskParameterValues.size() > 0)
			return taskParameterValues.get(0);
		else
			return null;
	}

	@Override
	@Transactional
	public List<TaskRunnerDetail> getTaskRunnerDetailByTaskRunnerId(Long id) {
		TaskRunnerDetailExample example = new TaskRunnerDetailExample();
		example.createCriteria().andTaskRunnerHdrIdEqualTo(id);
		return taskRunnerService.getTaskRunnerDetailByExample(example);
	}

	@Override
	@Transactional
	public Task getTaskByTaskId(Long id) {
		TaskExample example = new TaskExample();
		example.createCriteria().andIdEqualTo(id);
		return taskServiceImpl.getTaskByPrimaryKey(id);

	}

	@Override
	@Transactional
	public List<TaskParameterDTO> getTaskParameterByTaskId(Long id) {
		TaskParameterExample example = new TaskParameterExample();
		example.createCriteria()
		.andTaskIdEqualTo(id);
		example.setOrderByClause("SEQUENCE_NO, TASK_PARAM_ID ASC");
		return taskServiceImpl.getTaskParametersByTaskId(id);
	}

	@Transactional
	public List<TaskParameterDTO> getTaskParameterDtoByTaskId(Long id) {
		TaskParameterExample example = new TaskParameterExample();
		example.createCriteria().andTaskIdEqualTo(id);
		List<TaskParameter> list = taskServiceImpl
				.getTaskParameterByExample(example);
		List<TaskParameterDTO> returnValue = new ArrayList<>();
		for (TaskParameter taskRunnerDetail : list) {
			TaskParameterDTO trdDto = new TaskParameterDTO();
			trdDto = modelMapper.map(taskRunnerDetail, TaskParameterDTO.class);
			returnValue.add(trdDto);
		}
		return returnValue;
	}


	@Transactional
	private ExecutableTaskDTO convertDomain(TaskRequestDTO taskRequestDto) {
		ExecutableTaskDTO taskRequest = new ExecutableTaskDTO();
		taskRequest.setTaskRunnerId(taskRequestDto.getTaskRunnerId());
		taskRequest.setDateSubmitted(new Date());
		taskRequest.setScheduleType(taskRequestDto.getScheduleType());
		taskRequest.setStartTime(taskRequestDto.getStartTime());
		taskRequest.setEndByDate(taskRequestDto.getEndByDate());
		taskRequest.setEndAfterOccurence(taskRequestDto.getEndAfterOccurence());
		taskRequest.setDailyPattern(taskRequestDto.getDailyPattern());
		taskRequest.setMonthlyPattern(taskRequestDto.getMonthlyPattern());
		taskRequest.setYearlyPattern(taskRequestDto.getYearlyPattern());
		taskRequest.setTotalDay(taskRequestDto.getTotalDay());
		taskRequest.setRecurEvery(taskRequestDto.getRecurEvery());
		taskRequest.setRecurOn(taskRequestDto.getRecurOn());
		taskRequest.setDayNum(taskRequestDto.getDayNum());
		taskRequest.setSequenceType(taskRequestDto.getSequenceType());
		taskRequest.setMonthName(taskRequestDto.getMonthName());
		taskRequest.setTotalMonth(taskRequestDto.getTotalMonth());
		taskRequest.setExecutionHours(taskRequestDto.getExecutionHours());
		taskRequest.setEndType(taskRequestDto.getEndType());
		taskRequest.setCronExpression(getCronExpression(taskRequestDto));
		// if(securityServiceImpl.getSecurityProfile() == null){
		taskRequest.setCreatedBy(/*securityServiceImpl.getSecurityProfile().getUserId()*/securityServiceImpl.getSecurityProfile().getUserId());
		taskRequest.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		// }else{
		// taskRequest.setCreatedBy(securityServiceImpl.getSecurityProfile()
		// .getUserId());
		// taskRequest.setLastUpdatedBy(securityServiceImpl.getSecurityProfile()
		// .getUserId());
		// }
		taskRequest.setCreationDate(DateUtil.truncate(new Date()));
		taskRequest.setLastUpdateDate(new Date());
		taskRequest.setOutputFormat(taskRequestDto.getOutputFormat());
		taskRequest.setAfterCompleted(taskRequestDto.getAfterCompleted());
		taskRequest.setPrinterId(taskRequestDto.getPrinterId());
		return taskRequest;
	}

	private String getCronExpression(TaskRequestDTO taskRequestDto) {
		String cron = "";
		int hour = 0;
		int minute = 0;
		if(taskRequestDto.getExecutionHours() != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(taskRequestDto.getExecutionHours());
			if (TaskScheduleType.getKey(taskRequestDto.getScheduleType()) != 0) {
				hour = cal.get(Calendar.HOUR_OF_DAY);
				minute = cal.get(Calendar.MINUTE);

			}
		}
		
		switch (TaskScheduleType.getKey(taskRequestDto.getScheduleType())) {//TODO
		case 1:
			if (taskRequestDto.getDailyPattern().equals(
					TaskDailyPattern.EVERYDAYS.getValue())) {
				int dayNum = taskRequestDto.getTotalDay();

				cron = "0 " + minute + " " + hour + " 1/" + dayNum + " * ? *";
			} else {
				cron = "0 " + minute + " " + hour + " ? * MON-FRI *";
			}
			break;
		case 3:
			if (taskRequestDto.getMonthlyPattern().equals(
					TaskMonthlyPattern.DAY.getValue())) {
				int dayNum = taskRequestDto.getDayNum();
				int totalMonth = taskRequestDto.getTotalMonth();
				cron = "0 " + minute + " " + hour + " " + dayNum + " 1/"
						+ totalMonth + " ? *";
			} else {
				int seq = TaskSequenceType.getKey(taskRequestDto
						.getSequenceType());
				String dayName = taskRequestDto.getDayName();
				int totalMonth = taskRequestDto.getTotalMonth();
				cron = "0 " + minute + " " + hour + " ? 1/" + totalMonth + " "
						+ dayName + "#" + seq + " *";
				if(seq<5){
					cron = "0 " + minute + " " + hour + " ? 1/" + totalMonth + " "
							+ dayName + "#" + seq + " *";
				}else{
					cron = "0 " + minute + " " + hour + " ? 1/" + totalMonth + " "
							+ dayName + "L" + " *";
				}
			}
			break;
		case 4:
			int recurEvery = taskRequestDto.getRecurEvery();
			if (taskRequestDto.getYearlyPattern().equals(
					TaskYearlyPattern.MONTH.getValue())) {
				int date = taskRequestDto.getDayNum();
				int monthIdx = MonthName.getKey(taskRequestDto.getMonthName());
				cron = "0 " + minute + " " + hour + " " + date + " " + monthIdx + " ? 1/"+recurEvery;
			} else {
				int seq = TaskSequenceType.getKey(taskRequestDto
						.getSequenceType());
				String dayName = taskRequestDto.getDayName();
				int monthIdx = MonthName.getKey(taskRequestDto.getMonthName());
				if(seq<5){
					cron = "0 " + minute + " " + hour + " ? " + monthIdx + " "
							+ dayName + "#" + seq + " 1/"+recurEvery;
				}else{
					cron = "0 " + minute + " " + hour + " ? " + monthIdx + " "
							+ dayName+ "L" +  " 1/"+recurEvery;
				}
				
			}
		default:
			break;
		}
		return cron;
	}

	@Override
	@Transactional
	public void cancelTask(String keyJob) {
		cancelJob(keyJob, null, null);
	}
	
	@Override
	@Transactional
	public void deleteTask(String keyJob) {
		deleteJob(keyJob, null, null);
	}



	@Override
	@Transactional
	public void deleteJob(String jobkey, Long taskRequestId, String taskPhase){
		if (Strings.isNullOrEmpty(jobkey))
			return;
		JobKey jobKey = new JobKey(jobkey);
		log.debug("JobKey : " + jobKey);
		try {

			log.debug("scheduler.interrupt(jobKey)" + scheduler.deleteJob(jobKey)
					+ scheduler.interrupt(jobKey)) ;
			
			List<TaskRequestDetail> detail = taskRequestFinder
					.getTaskRequestDetailToBeCanceled(jobkey, taskRequestId,
							taskPhase);
			for (TaskRequestDetail trd : detail) {
				trd.setTaskStatus(TaskStatus.CANCELED.getValue());
				trd.setTaskPhase(TaskPhase.COMPLETED.getValue());
				trd.setDateCompleted(new Date());
				taskRequestDetailMapper.updateByPrimaryKey(trd);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	@Transactional
	public void cancelJob(String jobkey, Long taskRequestId, String taskPhase) {
		JobKey jobKey = new JobKey(jobkey);
		log.debug("JobKey : " + jobKey);
		try {

			log.debug("scheduler.interrupt(jobKey)" + scheduler.interrupt(jobKey)) ;
			
			List<TaskRequestDetail> detail = taskRequestFinder
					.getTaskRequestDetailToBeCanceled(jobkey, taskRequestId,
							taskPhase);
			for (TaskRequestDetail trd : detail) {
				trd.setTaskStatus(TaskStatus.CANCELED.getValue());
				trd.setTaskPhase(TaskPhase.COMPLETED.getValue());
				trd.setDateCompleted(new Date());
				taskRequestDetailMapper.updateByPrimaryKey(trd);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public String getTaskRequestParentRequestIdByTaskRequestId(Long id) {
		TaskRequest tr = taskRequestMapper.selectByPrimaryKey(id);
		return tr.getParentRequestId();
	}

	@Override
	@Transactional
	public TaskRequest getTaskRequestByTaskRequestId(Long id) {
		return taskRequestMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public TaskRequestDetail getTaskRequestDetailByExample(
			TaskRequestDetailExample example) {
		return taskRequestDetailMapper.selectByExample(example).get(0);
	}

	@Override
	@Transactional
	public String getOutputFilePathByTaskRequestId(Long requestId) {
		TaskRequestDetailExample example = new TaskRequestDetailExample();
		example.createCriteria().andTaskRequestIdEqualTo(requestId);
		TaskRequestDetail taskRequestDetail = taskRequestDetailMapper
				.selectByExample(example).get(0);
		return taskRequestDetail.getOutputLog();
	}

	@Override
	@Transactional
	public List<TaskRequestDetail> getTaskRequestDetails(
			TaskRequestDetailExample example) {
		return taskRequestDetailMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public List<TaskParameterValue> getTaskParameterValuesByTaskRunnerId(Long id) {
		return taskRequestFinder.getTaskParameterValuesByTaskRunnerId(id);
	}

	@Override
	@Transactional
	public void saveTaskRequestDetail(TaskRequestDetail requestDetail) {
		taskRequestDetailMapper.insertSelective(requestDetail);

	}

	@Override
	@Transactional
	public void updateTaskRequestDetail(TaskRequestDetail requestDetail) {
		taskRequestDetailMapper.updateByPrimaryKey(requestDetail);

	}

	@Override
	@Transactional
	public TaskRequestDetail getTaskRequestDetailByTaskRequestDetailId(Long id) {
		TaskRequestDetailExample example = new TaskRequestDetailExample();
		example.createCriteria().andIdEqualTo(id);
		List<TaskRequestDetail> listRequestDetail = taskRequestDetailMapper
				.selectByExample(example);
		return listRequestDetail.get(listRequestDetail.size() - 1);
	}

	@Override
	@Transactional
	public TaskRequestDetail getScheduledOrPendingFirstTaskRequestDetailByTaskRequestId(
			Long id) {
		return taskRequestFinder
				.getScheduledOrPendingFirstTaskRequestDetailByTaskRequestId(id);
	}

	@Override
	@Transactional
	public ExecutableFile executableFileBuilder(String executableFileName,
			Long moduleId, String taskType) {
		ExecutableFile executableFile = new ExecutableFile();
		executableFile.setExecutableFileName(executableFileName);
		executableFile.setModuleId(moduleId);
		executableFile.setTaskType(taskType);
		executableFile.setCreatedBy(securityServiceImpl.getSecurityProfile()
				.getUserId());
		executableFile.setCreationDate(DateUtil.truncate(new Date()));
		executableFile.setLastUpdatedBy(securityServiceImpl
				.getSecurityProfile().getUserId());
		executableFile.setLastUpdateDate(new Date());
		executableFileMapper.insertSelective(executableFile);
		return executableFile;
	}

	// @Override
	// @Transactional
	// public TaskParameterValue taskParameterValueBuilder(Object value){
	//
	// return taskParameterValue;
	//
	// }
	@Override
	@Transactional
	public TaskParameterDTO taskParameterBuilder(String dataType, Object value,
			String key) {
		List<TaskParameterValue> listOfValue = new ArrayList<>();
		TaskParameterDTO parameterDto = new TaskParameterDTO();
		parameterDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		parameterDto.setCreationDate(DateUtil.truncate(new Date()));
		parameterDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		parameterDto.setLastUpdateDate(new Date());

		parameterDto.setDataType(dataType);
		parameterDto.setKey(key);
		parameterDto.setDisplayName("no_display_name");
		// taskParameterMapper.insertSelective(parameterDto);

		TaskParameterValue taskParameterValue = new TaskParameterValue();
		taskParameterValue.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		taskParameterValue.setCreationDate(DateUtil.truncate(new Date()));
		taskParameterValue.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		taskParameterValue.setLastUpdateDate(new Date());
		taskParameterValue.setTaskParameterId(parameterDto.getId());
		taskParameterValue.setValue(value);
		listOfValue.add(taskParameterValue);
		// taskParameterValueMapper.insertSelective(taskParameterValue);

		parameterDto.setTaskParameterValue(taskParameterValue);
		// taskParameterMapper.updateByPrimaryKey(parameterDto);
		return parameterDto;

	}

	@Override
	@Transactional
	public TaskDTO taskBuilder(List<TaskParameterDTO> taskParameters,
			String className, ExecutableFile executableFile) {
		TaskDTO task = new TaskDTO();
		if (executableFile != null) {
			task.setExecutableFile(executableFile);
			task.setExecutableFileId(executableFile.getId());
			task.setTaskType(executableFile.getTaskType());
			executableFile.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			executableFile.setCreationDate(DateUtil.truncate(new Date()));
			executableFile.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			executableFile.setLastUpdateDate(new Date());
		}
		task.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		task.setCreationDate(DateUtil.truncate(new Date()));
		task.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		task.setLastUpdateDate(new Date());
		task.setExecutableFileId(securityServiceImpl.getSecurityProfile().getUserId());
		task.setClassName(className);
		task.setTaskParameters(taskParameters);
		task.setTaskName(className);
		task.setDescription(className);
		task.setTaskType(TaskType.BATCH.getValue());
		taskMapper.insert(task);
		for (TaskParameterDTO params : taskParameters) {
			params.setTaskId(task.getId());
			taskParameterMapper.insertSelective(params);
		}
		return task;
	}

	@Override
	@Transactional
	public TaskRunnerDetailDTO taskRunnerDetailBuilder(TaskDTO mainTask,
			TaskDTO successTask, TaskDTO errorTask, int sequence) {
		TaskRunnerDetailDTO taskRunnerDetail = new TaskRunnerDetailDTO();
		taskRunnerDetail.setErrorTask(errorTask);
		taskRunnerDetail.setMainTask(mainTask);
		taskRunnerDetail.setTaskId(mainTask.getId());
		taskRunnerDetail.setSuccessTask(successTask);
		taskRunnerDetail.setSequence(sequence);
		taskRunnerDetail.setErrorTaskId(errorTask.getId());
		taskRunnerDetail.setSccuessTaskId(successTask.getId());
		taskRunnerDetail.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		taskRunnerDetail.setCreationDate(DateUtil.truncate(new Date()));
		taskRunnerDetail.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		taskRunnerDetail.setLastUpdateDate(new Date());
		// taskRunnerDetailMapper.insertSelective(taskRunnerDetail);
		return taskRunnerDetail;
	}

	@Override
	@Transactional
	public TaskRunnerDTO TaskRunnerBuilder(Date dateFrom, Date dateTo,
			String executionType, List<TaskRunnerDetailDTO> taskRunnerDetails) {
		TaskRunnerDTO taskRunner = new TaskRunnerDTO();
		taskRunner.setDateFrom(dateFrom);
		taskRunner.setDateTo(dateTo);
		taskRunner.setExecutionType(executionType);
		taskRunner.setTaskRunnerName("no_name");
		taskRunner.setDescription("no_desc");

		taskRunner.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		taskRunner.setCreationDate(DateUtil.truncate(new Date()));
		taskRunner.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		taskRunner.setLastUpdateDate(new Date());
		taskRunnerMapper.insert(taskRunner);
		// for(TaskRunnerDetailDTO taskRunnerDetail : taskRunnerDetails){
		// taskRunnerDetail.setTaskRunnerHdrId(taskRunner.getId());
		// taskRunnerDetailMapper.updateByPrimaryKeySelective(taskRunnerDetail);
		// }
		return taskRunner;
	}

	@Override
	@Transactional
	public TaskRequestDetail taskRequestDetailBuilder(Date dateStarted,
			Date dateCompleted, String outputLogPath, String outputFilePath) {
		TaskRequestDetail requestDetail = new TaskRequestDetail();
		requestDetail.setDateStarted(dateStarted);
		requestDetail.setDateCompleted(dateCompleted);
		requestDetail.setOutputLog(outputLogPath);
		requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
		requestDetail.setTaskPhase(TaskPhase.PENDING.getValue());
		requestDetail.setOutputFile(outputFilePath);
		// taskRequestDetailMapper.insert(requestDetail);
		return requestDetail;

	}

	@Transactional
	private TaskRequestDTO taskRequestBuilder(
			TaskRequestDetail taskRequestDetail, TaskRunnerDTO taskRunner,
			List<TaskDTO> tasks, String scheduleName) {
		TaskRequestDTO taskRequestDto = new TaskRequestDTO();
		taskRequestDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		taskRequestDto.setCreationDate(DateUtil.truncate(new Date()));
		taskRequestDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		taskRequestDto.setLastUpdateDate(new Date());

		taskRequestDto.setTaskRunner(taskRunner);
		// taskRequestDto.setTaskType(scheduleType);
		taskRequestDto.setTaskName(scheduleName);
		taskRequestDto.setTaskRequestDetail(taskRequestDetail);

		return taskRequestDto;

	}

	@Override
	@Transactional
	public String buildSchedule(List<TaskDTO> tasks, Schedule schedule,
			String taskExecutionType) {
		List<TaskParameterValue> listOfValue = new ArrayList<>();
		Long randomedParentRequestId = new Random().nextLong();
		String parentKey = randomedParentRequestId.toString().substring(0, 9);
		int indexSequence = 0;
		List<TaskRunnerDetailDTO> taskRunnerDetails = new ArrayList<>();

		TaskRunnerDTO taskRunner = TaskRunnerBuilder(
				DateUtil.truncate(new Date()), DateUtil.MAX_DATE,
				taskExecutionType, taskRunnerDetails);
		for (TaskDTO task : tasks) {
			TaskRunnerDetailDTO taskRunnerDetail = taskRunnerDetailBuilder(
					task, new TaskDTO(), new TaskDTO(), ++indexSequence);
			taskRunnerDetail.setTaskRunnerHdrId(taskRunner.getId());
			taskRunnerDetails.add(taskRunnerDetail);
			taskRunnerDetailMapper.insert(taskRunnerDetail);
			for (TaskParameterDTO taskParameter : task.getTaskParameters()) {
				listOfValue.add(taskParameter.getTaskParameterValue());
			}

		}
		taskRunner.setTaskCount(tasks.size());
		taskRunner.setTaskCount(taskRunnerDetails.size());
		taskRunner.setTaskRunnerDetailDto(taskRunnerDetails);
		taskRunnerMapper.updateByPrimaryKey(taskRunner);

		TaskRequestDetail taskRequestDetail = taskRequestDetailBuilder(null,
				null, null, null);
		TaskRequestDTO taskRequestDto = taskRequestBuilder(taskRequestDetail,
				taskRunner, tasks, parentKey);
		taskRequestDto.setParentRequestId(parentKey);
		taskRequestDto.setTaskId(tasks.get(0).getId());
		taskRequestDto.setDateSubmitted(new Date());
		taskRequestDto.setTaskParameterValues(listOfValue);

		if (schedule.getScheduleType().equalsIgnoreCase(
				TaskScheduleType.DAILY.getValue())) {// on it
			taskRequestDto.setScheduleType(schedule.getScheduleType());
			if (schedule.getPattern().equals(
					TaskDailyPattern.EVERYDAYS.getValue())) {
				taskRequestDto.setDailyPattern(TaskDailyPattern.EVERYDAYS
						.getValue());
				taskRequestDto.setTotalDay(schedule.getRecurEvery());
			} else {
				schedule.setEveryWeekDays(true);
				taskRequestDto.setDailyPattern(TaskDailyPattern.WEEKDAY
						.getValue());
			}
			taskRequestDto.setDayName(schedule.getTheDayName());
			taskRequestDto.setStartTime(schedule.getStartTime());
			taskRequestDto.setExecutionHours(schedule.getExecutionTime());
			taskRequestDto.setEndType(schedule.getEndType());
			taskRequestDto.setEndAfterOccurence(schedule.getAfterOccurence());
			taskRequestDto.setEndByDate(schedule.getEndDate());

		} else if (schedule.getScheduleType().equalsIgnoreCase(
				TaskScheduleType.WEEKLY.getValue())) {
			taskRequestDto.setScheduleType(schedule.getScheduleType());
			taskRequestDto.setRecurEvery(schedule.getRecurEvery());
			taskRequestDto.setRecurOn(DayName.getValue(schedule.getDayNum()));
			taskRequestDto.setStartTime(schedule.getStartTime());
			taskRequestDto.setExecutionHours(schedule.getExecutionTime());
			taskRequestDto.setEndType(schedule.getEndType());
			taskRequestDto.setEndAfterOccurence(schedule.getAfterOccurence());
			taskRequestDto.setEndByDate(schedule.getEndDate());
		} else if (schedule.getScheduleType().equalsIgnoreCase(
				TaskScheduleType.MONTHLY.getValue())) {
			taskRequestDto.setScheduleType(schedule.getScheduleType());
			taskRequestDto.setMonthlyPattern(schedule.getPattern());
			if (schedule.getPattern().equalsIgnoreCase("Day")) {
				taskRequestDto.setDayNum(schedule.getDateNum());
			} else {
				taskRequestDto.setSequenceType(schedule.getTheSequence());
				taskRequestDto.setDayName(schedule.getTheDayName());
			}
			taskRequestDto.setTotalMonth(schedule.getMonthlyMonthNum());
			taskRequestDto.setStartTime(schedule.getStartTime());
			taskRequestDto.setExecutionHours(schedule.getExecutionTime());

			taskRequestDto.setEndType(schedule.getEndType());
			taskRequestDto.setEndAfterOccurence(schedule.getAfterOccurence());
			taskRequestDto.setEndByDate(schedule.getEndDate());

		} else if (schedule.getScheduleType().equalsIgnoreCase(
				TaskScheduleType.YEARLY.getValue())) {
			if (schedule.getPattern().equals(TaskYearlyPattern.DAY.getValue())) {
				taskRequestDto.setSequenceType(schedule.getTheSequence());
				taskRequestDto.setDayName(schedule.getTheDayName());
			}
			taskRequestDto.setMonthName(schedule.getMonthlyMonthName());
			taskRequestDto.setScheduleType(schedule.getScheduleType());
			taskRequestDto.setYearlyPattern(schedule.getPattern());
			taskRequestDto.setSequenceType(schedule.getTheSequence());
			taskRequestDto.setDayNum(schedule.getDateNum());
			taskRequestDto.setTotalMonth(schedule.getMonthlyMonthNum());
			taskRequestDto.setStartTime(schedule.getStartTime());
			taskRequestDto.setExecutionHours(schedule.getExecutionTime());
			taskRequestDto.setRecurEvery(schedule.getRecurEvery());
			taskRequestDto.setEndType(schedule.getEndType());
			taskRequestDto.setEndAfterOccurence(schedule.getAfterOccurence());
			taskRequestDto.setEndByDate(schedule.getEndDate());
		} else if (schedule.getScheduleType().equalsIgnoreCase(
				TaskScheduleType.IMMEDIATELY.getValue())) {
			taskRequestDto.setScheduleType(schedule.getScheduleType());
			taskRequestDto.setEndAfterOccurence(1);
		} else if (schedule.getScheduleType().equalsIgnoreCase(
				TaskScheduleType.ONE_TIME.getValue())) {
			 taskRequestDto.setScheduleType(TaskScheduleType.DAILY.getValue());
//			taskRequestDto.setScheduleType(TaskScheduleType.IMMEDIATELY
//					.getValue());
			taskRequestDto.setEndAfterOccurence(1);

			taskRequestDto.setDailyPattern(TaskDailyPattern.EVERYDAYS
					.getValue());
			taskRequestDto.setTotalDay(schedule.getDayNum());
			taskRequestDto.setStartTime(schedule.getStartTime());
			taskRequestDto.setExecutionHours(schedule.getExecutionTime());
			taskRequestDto.setEndType(schedule.getEndType());
			taskRequestDto.setEndAfterOccurence(schedule.getAfterOccurence());
			taskRequestDto.setEndByDate(schedule.getEndDate());
		}
		taskRequestDto.setEndType(schedule.getEndType());
		taskRequestDto.setTaskRunner(taskRunner);
		taskRequestDto.setTaskRunnerId(taskRunner.getId());
		// taskRequestMapper.insert(taskRequestDto);
		taskRequestDetail.setTaskRequestId(taskRequestDto.getId());
		// taskRequestDetailMapper.insert(taskRequestDetail);
		taskRequestDto.setTaskName(parentKey);
		// taskRequestMapper.updateByPrimaryKeySelective(taskRequestDto);
		try {
			save(taskRequestDto, randomedParentRequestId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return String.valueOf(parentKey);

	}
	
	
	@Override
	public String buildScheduleWithCurrentRunner(String taskRunnerName, String className, Schedule schedule, String taskExecutionType, List<TaskParameterDTO> parameters) {
		List<TaskParameterValue> listOfValue = new ArrayList<>();
				Long randomedParentRequestId = new Random().nextLong();
				String parentKey = randomedParentRequestId.toString().substring(0, 9);

				TaskRunnerExample taskRunnerExample = new TaskRunnerExample();
				taskRunnerExample.createCriteria().andTaskRunnerNameEqualTo(taskRunnerName);
				
				List<TaskRunner> taskRunners = taskRunnerService.getTaskRunnerByExample(taskRunnerExample);
				
				TaskRunnerDTO taskRunner = null; //taskRunnerService.getTaskRunnerById(taskRunnerId);
				
				if(taskRunners.isEmpty()) {
					try {
						TaskDTO taskDto = new TaskDTO();
						taskDto.setTaskName(taskRunnerName);
						taskDto.setClassName(className);
						taskDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						taskDto.setCreationDate(new Date());
						taskDto.setDescription(taskRunnerName);
						taskDto.setLastUpdateDate(new Date());
						taskDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						taskDto.setTaskParameters(parameters);
						taskDto.setTaskType(TaskType.BATCH.getValue());
						taskDto.setExecutableFileId(-1L);
						
						taskServiceImpl.save(taskDto);
						
						List<TaskRunnerDetailDTO> taskRunnerDetailDtos = new ArrayList<TaskRunnerDetailDTO>();
						
						TaskDTO endOfTask = new TaskDTO();
						endOfTask.setId(-1L);
						
						TaskRunnerDetailDTO taskRunnerDetailDTO = new TaskRunnerDetailDTO();
						taskRunnerDetailDTO.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						taskRunnerDetailDTO.setCreationDate(new Date());
						taskRunnerDetailDTO.setErrorTask(endOfTask);
						taskRunnerDetailDTO.setErrorTaskId(-1L);
						taskRunnerDetailDTO.setLastUpdateDate(new Date());
						taskRunnerDetailDTO.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						taskRunnerDetailDTO.setSuccessTask(endOfTask);
						taskRunnerDetailDTO.setSccuessTaskId(-1L);
						taskRunnerDetailDTO.setMainTask(taskDto);
						taskRunnerDetailDTO.setSequence(0);
						taskRunnerDetailDTO.setTaskId(taskDto.getId());
						
						taskRunnerDetailDtos.add(taskRunnerDetailDTO);
						
						taskRunner = new TaskRunnerDTO();
						taskRunner.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						taskRunner.setCreationDate(new Date());
						taskRunner.setDateFrom(DateUtils.truncate(new Date(), Calendar.DATE));
						taskRunner.setDateTo(DateUtils.truncate(DateUtil.MAX_DATE, Calendar.DATE));
						taskRunner.setDescription(taskRunnerName);
						taskRunner.setExecutionType(taskExecutionType);
						taskRunner.setLastUpdateDate(new Date());
						taskRunner.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						taskRunner.setTaskCount(1);
						taskRunner.setTaskRunnerDetailDto(taskRunnerDetailDtos);
						taskRunner.setTaskRunnerName(taskRunnerName);
						
						taskRunnerService.save(taskRunner);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
						throw new RuntimeException(e.getMessage());
					}
					
				} else {
					taskRunner = modelMapper.map(taskRunners.get(0), TaskRunnerDTO.class);
					taskRunner.setTaskRunnerDetailDto(taskRunnerService.getTaskRunnerDetailDtoByTaskRunnerId(taskRunner.getId()));
				}
				
				
				for (TaskParameterDTO taskParameter : parameters) {
					listOfValue.add(taskParameter.getTaskParameterValue());
				}
				
				TaskRequestDetail taskRequestDetail = taskRequestDetailBuilder(null,
						null, null, null);
				TaskRequestDTO taskRequestDto = taskRequestBuilder(taskRequestDetail,
						taskRunner, null, parentKey);
				taskRequestDto.setParentRequestId(parentKey);
//				taskRequestDto.setTaskId(tasks.get(0).getId());
				taskRequestDto.setDateSubmitted(new Date());
				taskRequestDto.setTaskParameterValues(listOfValue);

				if (schedule.getScheduleType().equalsIgnoreCase(
						TaskScheduleType.DAILY.getValue())) {// on it
					taskRequestDto.setScheduleType(schedule.getScheduleType());
					if (schedule.getPattern().equals(
							TaskDailyPattern.EVERYDAYS.getValue())) {
						taskRequestDto.setDailyPattern(TaskDailyPattern.EVERYDAYS
								.getValue());
						taskRequestDto.setTotalDay(schedule.getRecurEvery());
					} else {
						schedule.setEveryWeekDays(true);
						taskRequestDto.setDailyPattern(TaskDailyPattern.WEEKDAY
								.getValue());
					}
					taskRequestDto.setDayName(schedule.getTheDayName());
					taskRequestDto.setStartTime(schedule.getStartTime());
					taskRequestDto.setExecutionHours(schedule.getExecutionTime());
					taskRequestDto.setEndType(schedule.getEndType());
					taskRequestDto.setEndAfterOccurence(schedule.getAfterOccurence());
					taskRequestDto.setEndByDate(schedule.getEndDate());

				} else if (schedule.getScheduleType().equalsIgnoreCase(
						TaskScheduleType.WEEKLY.getValue())) {
					taskRequestDto.setScheduleType(schedule.getScheduleType());
					taskRequestDto.setRecurEvery(schedule.getRecurEvery());
					taskRequestDto.setRecurOn(DayName.getValue(schedule.getDayNum()));
					taskRequestDto.setStartTime(schedule.getStartTime());
					taskRequestDto.setExecutionHours(schedule.getExecutionTime());
					taskRequestDto.setEndType(schedule.getEndType());
					taskRequestDto.setEndAfterOccurence(schedule.getAfterOccurence());
					taskRequestDto.setEndByDate(schedule.getEndDate());
				} else if (schedule.getScheduleType().equalsIgnoreCase(
						TaskScheduleType.MONTHLY.getValue())) {
					taskRequestDto.setScheduleType(schedule.getScheduleType());
					taskRequestDto.setMonthlyPattern(schedule.getPattern());
					if (schedule.getPattern().equalsIgnoreCase("Day")) {
						taskRequestDto.setDayNum(schedule.getDateNum());
					} else {
						taskRequestDto.setSequenceType(schedule.getTheSequence());
						taskRequestDto.setDayName(schedule.getTheDayName());
					}
					taskRequestDto.setTotalMonth(schedule.getMonthlyMonthNum());
					taskRequestDto.setStartTime(schedule.getStartTime());
					taskRequestDto.setExecutionHours(schedule.getExecutionTime());

					taskRequestDto.setEndType(schedule.getEndType());
					taskRequestDto.setEndAfterOccurence(schedule.getAfterOccurence());
					taskRequestDto.setEndByDate(schedule.getEndDate());

				} else if (schedule.getScheduleType().equalsIgnoreCase(
						TaskScheduleType.YEARLY.getValue())) {
					if (schedule.getPattern().equals(TaskYearlyPattern.DAY.getValue())) {
						taskRequestDto.setSequenceType(schedule.getTheSequence());
						taskRequestDto.setDayName(schedule.getTheDayName());
					}
					taskRequestDto.setMonthName(schedule.getYearlyMonthName());
					taskRequestDto.setScheduleType(schedule.getScheduleType());
					taskRequestDto.setYearlyPattern(schedule.getPattern());
					taskRequestDto.setSequenceType(schedule.getTheSequence());
					taskRequestDto.setDayNum(schedule.getDateNum());
					taskRequestDto.setTotalMonth(schedule.getMonthlyMonthNum());
					taskRequestDto.setStartTime(schedule.getStartTime());
					taskRequestDto.setExecutionHours(schedule.getExecutionTime());
					taskRequestDto.setRecurEvery(schedule.getRecurEvery());
					taskRequestDto.setEndType(schedule.getEndType());
					taskRequestDto.setEndAfterOccurence(schedule.getAfterOccurence());
					taskRequestDto.setEndByDate(schedule.getEndDate());
				} else if (schedule.getScheduleType().equalsIgnoreCase(
						TaskScheduleType.IMMEDIATELY.getValue())) {
					taskRequestDto.setScheduleType(schedule.getScheduleType());
					taskRequestDto.setEndAfterOccurence(1);
					taskRequestDto.setEndType(TaskEndDateType.END_AFTER.getValue());
				} else if (schedule.getScheduleType().equalsIgnoreCase(
						TaskScheduleType.ONE_TIME.getValue())) {
					 taskRequestDto.setScheduleType(TaskScheduleType.DAILY.getValue());//TODO

					taskRequestDto.setDailyPattern(TaskDailyPattern.EVERYDAYS
							.getValue());
					taskRequestDto.setTotalDay(1);
					taskRequestDto.setStartTime(schedule.getStartTime());
					taskRequestDto.setExecutionHours(schedule.getExecutionTime());
					taskRequestDto.setEndType(TaskEndDateType.END_AFTER.getValue());
					taskRequestDto.setEndAfterOccurence(1);
					taskRequestDto.setEndByDate(schedule.getStartTime());
				}
				taskRequestDto.setTaskRunner(taskRunner);
				taskRequestDto.setTaskRunnerId(taskRunner.getId());
				// taskRequestMapper.insert(taskRequestDto);
				taskRequestDetail.setTaskRequestId(taskRequestDto.getId());
				// taskRequestDetailMapper.insert(taskRequestDetail);
				taskRequestDto.setTaskName(parentKey);
				// taskRequestMapper.updateByPrimaryKeySelective(taskRequestDto);
				try {
					save(taskRequestDto, randomedParentRequestId);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return String.valueOf(parentKey);

			}

	@Transactional
	public void save(TaskRequestDTO taskRequestDto, Long randomedParentRequestId)
			throws Exception {
		List<TaskParameterValue> listOfValue = new ArrayList<>();
		List<ExecutableTaskDTO> executableTaskDtos = new ArrayList<>();
		String key = "";
		Object value = null;
		
		ExecutableTaskDTO taskRequest = convertDomain(taskRequestDto);
		taskRequest.setId(taskRequestDto.getId());

		taskRequest.setParentRequestId(randomedParentRequestId.toString()
				.substring(0, 9));
		TaskRunnerDTO taskRunner = taskRunnerService.getTaskRunnerById(taskRequest
				.getTaskRunnerId());
		listOfValue = taskRequestDto.getTaskParameterValues();
		int indexTaskRunnerDetailDto = 0;
		int indexListOfValue = 0;
		for (TaskRunnerDetailDTO trd : taskRunner.getTaskRunnerDetailDto()) {
			ExecutableTaskDTO executtableDtoClone = null;
			Map<String, Object> keyValue = new HashMap<>();
			try {
				executtableDtoClone = (ExecutableTaskDTO) ((ExecutableTaskDTO) taskRequest)
						.clone();
			} catch (CloneNotSupportedException e1) {
				log.error(e1.getMessage(), e1);
			}

			int indexOfTaskParameterValues = 0;
			TaskDTO task = trd.getMainTask();
			executtableDtoClone.setTaskId(task.getId());
			taskRequestMapper.insertSelective(executtableDtoClone);
			for (TaskParameterDTO tpDto : task.getTaskParameters()) {
				key = tpDto.getKey();

				value = listOfValue.get(indexListOfValue).getValue();
				TaskParameterValue taskParameterValue = new TaskParameterValue();
				taskParameterValue
						.setTaskRequestId(executtableDtoClone.getId());
				taskParameterValue.setTaskParameterId(tpDto.getId());
				// System.out.println(executtableDtoClone.getCreatedBy());
				taskParameterValue.setCreatedBy(executtableDtoClone
						.getCreatedBy());
				taskParameterValue.setCreationDate(executtableDtoClone
						.getCreationDate());
				taskParameterValue.setLastUpdateDate(executtableDtoClone
						.getLastUpdateDate());
				taskParameterValue.setLastUpdatedBy(executtableDtoClone
						.getLastUpdatedBy());
				taskParameterValue.setValue(value);
				taskRunner.getTaskRunnerDetailDto()
						.get(indexTaskRunnerDetailDto).getMainTask()
						.getTaskParameters().get(indexOfTaskParameterValues)
						.setTaskParameterValue(taskParameterValue);
				taskParameterValueMapper.insert(taskParameterValue);
				indexOfTaskParameterValues++;
				indexListOfValue++;
				keyValue.put(key, value);
			}

			// taskRequestDetailIds.add(requestDetail.getId());

			ExecutableTaskDTO executableTaskDto = new ExecutableTaskDTO();
			TaskDTO mainTask = taskRunner.getTaskRunnerDetailDto()
					.get(indexTaskRunnerDetailDto).getMainTask() == null ? new TaskDTO()
					: taskRunner.getTaskRunnerDetailDto()
							.get(indexTaskRunnerDetailDto).getMainTask();
			TaskDTO successTask = taskRunner.getTaskRunnerDetailDto()
					.get(indexTaskRunnerDetailDto).getSuccessTask() == null ? new TaskDTO()
					: taskRunner.getTaskRunnerDetailDto()
							.get(indexTaskRunnerDetailDto).getSuccessTask();
			TaskDTO errorTask = taskRunner.getTaskRunnerDetailDto()
					.get(indexTaskRunnerDetailDto).getErrorTask() == null ? new TaskDTO()
					: taskRunner.getTaskRunnerDetailDto()
							.get(indexTaskRunnerDetailDto).getErrorTask();

			executableTaskDto = modelMapper.map(executtableDtoClone,
					ExecutableTaskDTO.class);
			// executableTaskDto.setRequestDetail(requestDetail);
			executableTaskDto.setExecutionType(taskRunner.getExecutionType());

			if (trd.getSccuessTaskId() == null) {
				trd.setSccuessTaskId(securityServiceImpl.getSecurityProfile().getUserId());
				taskRunnerService.updateTaskRequestDetailByPrimaryKey(trd);
			}

			if (trd.getErrorTaskId() == null) {
				trd.setErrorTaskId(securityServiceImpl.getSecurityProfile().getUserId());
				taskRunnerService.updateTaskRequestDetailByPrimaryKey(trd);
			}

			if (successTask.getId() != null && successTask.getId() != -1) {
				successTask = taskServiceImpl.getTaskById(successTask.getId());
			}

			if (errorTask.getId() != null && errorTask.getId() != -1) {
				errorTask = taskServiceImpl.getTaskById(errorTask.getId());
			}

			executableTaskDto.setMainTask(mainTask);
			executableTaskDto.setSuccessTask(successTask);
			executableTaskDto.setErrorTask(errorTask);
			executableTaskDto.setSequence(trd.getSequence());
			executableTaskDto.setKeyValues(keyValue);
			executableTaskDtos.add(executableTaskDto);
			indexTaskRunnerDetailDto++;
		}
		try {
			runScheduler(executableTaskDtos);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public Date getXthfireTimeDateByJobKey(String jobKey, int times,
			Date startDate) {
		JobKey schedulerJobKey = new JobKey(jobKey);
		Date expectedDate = new Date();
		try {
			if (scheduler.getTriggersOfJob(schedulerJobKey).isEmpty()) {
				return null;
			}
			Trigger trigger = scheduler.getTriggersOfJob(schedulerJobKey)
					.get(0);
			if (startDate == null) {
				if (trigger.getPreviousFireTime() != null) {
					expectedDate = trigger.getPreviousFireTime();
				} else {
					expectedDate = trigger.getStartTime();
				}
			} else {
				expectedDate = startDate;
			}
			for (int i = 0; i < times; i++) {
				expectedDate = trigger.getFireTimeAfter(expectedDate);
			}
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
		return expectedDate;
	}

	@Override
	public int updateTaskReqeuest(TaskRequest taskRequest) {
		return taskRequestMapper.updateByPrimaryKey(taskRequest);
	}


	@Override
	public void buildImmediatelySchedule(String taskRunnerName, Map<String, Object> param) throws Exception{
		
		//Get Task Runner by name
		TaskRunnerExample taskRunnerExample = new TaskRunnerExample();
		taskRunnerExample.createCriteria().andTaskRunnerNameEqualTo(taskRunnerName);
		List<TaskRunner> taskRunners = taskRunnerMapper.selectByExample(taskRunnerExample);
		
		TaskRunner taskRunner = null;
		if(!taskRunners.isEmpty()){
			taskRunner = taskRunners.get(0);
		
			TaskRunnerDetailExample taskRunnerDetailExample = new TaskRunnerDetailExample();
			taskRunnerDetailExample.createCriteria().andTaskRunnerHdrIdEqualTo(taskRunner.getId());
			List<TaskRunnerDetail> taskRunnerDetails = taskRunnerDetailMapper.selectByExample(taskRunnerDetailExample);
			
			if(!taskRunnerDetails.isEmpty()){
				TaskRunnerDetail taskRunnerDetail = taskRunnerDetails.get(0);
				
				TaskDTO mainTask = taskServiceImpl.getTaskById(taskRunnerDetail.getTaskId());
				
				TaskDTO errorTask = new TaskDTO();
				errorTask.setId(-1L);
				
				TaskDTO successTask = new TaskDTO();
				successTask.setId(-1L);
				
				ExecutableTaskDTO executableTaskDTO = new ExecutableTaskDTO();
				Long random = new Random().nextLong();
				
				executableTaskDTO.setParentRequestId(random.toString().substring(0, 9));
				executableTaskDTO.setDateSubmitted(new Date());
				executableTaskDTO.setTaskRunnerId(taskRunner.getId());
				executableTaskDTO.setTaskId(taskRunnerDetail.getTaskId());
				executableTaskDTO.setScheduleType(TaskScheduleType.IMMEDIATELY.toString());
				executableTaskDTO.setStartTime(new Date());
				executableTaskDTO.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				executableTaskDTO.setCreationDate(new Date());
				executableTaskDTO.setLastUpdateDate(new Date());
				executableTaskDTO.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				executableTaskDTO.setMainTask(mainTask);
				executableTaskDTO.setErrorTask(errorTask);
				executableTaskDTO.setSuccessTask(successTask);
				executableTaskDTO.setExecutionType(TaskExecutionType.SEQUENTIAL.toString());
				executableTaskDTO.setSequence(0);
				executableTaskDTO.setKeyValues(param);
				executableTaskDTO.setEndType(TaskEndDateType.END_AFTER.toString());
				
				//Insert Task Request
				taskRequestMapper.insert(executableTaskDTO);
				
				
				//Insert Task Request Detail
			/*	TaskRequestDetail taskRequestDetail = new TaskRequestDetail();
				taskRequestDetail.setDateCompleted(null);
				taskRequestDetail.setDateStarted(executableTaskDTO.getStartTime());
				taskRequestDetail.setTaskPhase(TaskPhase.PENDING.getValue());
				taskRequestDetail.setTaskRequestId(executableTaskDTO.getId());
				taskRequestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
				taskRequestDetailMapper.insertSelective(taskRequestDetail);*/
				
				List<ExecutableTaskDTO> executableTaskDTOs = new ArrayList<ExecutableTaskDTO>();
				executableTaskDTOs.add(executableTaskDTO);
				
				runScheduler(executableTaskDTOs);
				/*Map<String, Object> taskParameter = new HashMap<String, Object>();
				taskParameter.put("executableTaskDto", executableTaskDTOs);
				taskParameter.put("securityProfile",securityServiceImpl.getSecurityProfile());
				taskParameter.put("executionCount", 0);

				JobDataMap jobParam = new JobDataMap(taskParameter);
				JobKey jobKey = new JobKey(executableTaskDTO.getParentRequestId());

				JobDetail job = JobBuilder.newJob(TaskRunnerManager.class)
						.withIdentity(jobKey).usingJobData(jobParam).storeDurably()
						.build();

				Trigger jobTrigger = TriggerBuilder.newTrigger()
							.withIdentity(executableTaskDTO.getParentRequestId())
							.startNow().build();
			
				scheduler.getListenerManager().addJobListener(new TaskRunnerManager(),KeyMatcher.keyEquals(jobKey));
				scheduler.getListenerManager().getJobListener(jobKey.toString());
				scheduler.scheduleJob(job, jobTrigger);*/
				//TODO
				
			}else
				throw new Exception("Caount not find task runner detail of "+taskRunnerName);
			
		}else
			throw new Exception("Could not find task runner name : "+taskRunnerName);
		
		
	}


	@Override
	public boolean isAnyRunning(Long userId, String taskRunnerName) {
		TaskRunnerExample runnerExample= new TaskRunnerExample();
		runnerExample.createCriteria()
		.andTaskRunnerNameLikeInsensitive(taskRunnerName);
		
		
		List<TaskRunner> runners = taskRunnerService.getTaskRunnerByExample(runnerExample);
		if(runners!=null && !runners.isEmpty()){
			TaskRequestExample requestExample = new  TaskRequestExample();
			requestExample.createCriteria()
			.andTaskRunnerIdEqualTo(runners.get(0).getId())
			.andCreatedByEqualTo(userId);
			
 			List<TaskRequest> requests = taskRequestMapper.selectByExample(requestExample);
 			if(requests!=null && requests.isEmpty()){
				return true;
			}else
 			if(requests!=null && !requests.isEmpty()){
 				return taskRequestFinder.isJobCompleted(requests.get(0).getParentRequestId());
			} 
		}
		return false;
		
	}


	@Override
	public int getFireTime(Long taskRequestId) {
		TaskRequestDetailExample example = new TaskRequestDetailExample();
		example.createCriteria()
		.andTaskPhaseLikeInsensitive(TaskPhase.COMPLETED.toString())
		.andTaskRequestIdEqualTo(taskRequestId);
		
		return taskRequestDetailMapper.countByExample(example);
	}

	
}
