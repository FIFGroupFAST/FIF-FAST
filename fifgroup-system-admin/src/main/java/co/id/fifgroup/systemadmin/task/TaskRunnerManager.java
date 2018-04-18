package co.id.fifgroup.systemadmin.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.query.JRJdbcQueryExecuterFactory;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Transactional;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import co.id.fifgroup.basicsetup.common.PrinterParam;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.domain.PrinterExample;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.PrinterService;
import co.id.fifgroup.basicsetup.service.PrinterUtilService;
import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.systemadmin.constant.OutputFileFormat;
import co.id.fifgroup.systemadmin.constant.TaskAfterCompleted;
import co.id.fifgroup.systemadmin.constant.TaskEndDateType;
import co.id.fifgroup.systemadmin.constant.TaskExecutionType;
import co.id.fifgroup.systemadmin.constant.TaskPhase;
import co.id.fifgroup.systemadmin.constant.TaskStatus;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.domain.ModuleErrorLog;
import co.id.fifgroup.systemadmin.domain.TaskRequest;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetail;
import co.id.fifgroup.systemadmin.dto.ExecutableTaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.security.ReportSecurityResolver;
import co.id.fifgroup.systemadmin.service.ErrorLogService;
import co.id.fifgroup.systemadmin.service.TaskRequestService;

@SuppressWarnings("deprecation")
@PersistJobDataAfterExecution
public class TaskRunnerManager implements Job, JobListener, InterruptableJob {

	private static final Logger logger = LoggerFactory.getLogger(TaskRunnerManager.class);

	private PrintWriter log;
	private Map<String, Object> taskParameter;
	List<ExecutableTaskDTO> executableTaskDtoList;
	private ExecutableTask executebleTask;
	private String jobListenerName = "JOB_LISTENER";
	private int executionCount = 0;
	private int occurrence;
	private ApplicationContext applicationContext;
	private JobExecutionContext globalContext;
	private TaskRequestService taskRequestServiceImpl;
	private TaskRequestDetail requestDetail;
	private Long firstTaskRequestDetailId;
	private ExecutorService executor = Executors.newFixedThreadPool(GlobalVariable.getMaxThreadPool());
	private PrinterUtilService printerUtilService;
	private PrinterService printerService;
	private String logPath="";
	private String reportPath= "";
	private ReportSecurityResolver reportSecurityResolver;
	private SecurityProfile securityProfile;
	private CompanyService companyService;
	private OrganizationSetupService organizationService;
	private static String MODULE_ROOT_FOLDER = "SYSTEM_ADMINISTRATION";
	private static String TASK_ROOT_FOLDER = "TASK";
	private static String EXECUTABLE_FILE_ROOT_FOLDER = "EXECUTABLE_FILES";
	private static String LOG_ROOT_FOLDER = "LOGS";
	private static String REPORT_ROOT_FOLDER = "REPORTS";
	

	private volatile boolean interrupted = false;
	private volatile boolean completed = false;
	private volatile Thread currentThread;
	private boolean isTaskError = false;
	
	private JarClassLoader jcl = new JarClassLoader();
	private JclObjectFactory factory = JclObjectFactory.getInstance();

	@Transactional
	public void executeTask(JobDataMap data) {
		currentThread = Thread.currentThread();
		jcl.add(GlobalVariable.getAppsRootFolder()+File.separator+MODULE_ROOT_FOLDER+File.separator+TASK_ROOT_FOLDER+File.separator+EXECUTABLE_FILE_ROOT_FOLDER+File.separator);
		try {
			if (getExecutionType().equalsIgnoreCase(TaskExecutionType.SEQUENTIAL.getValue()))
				sequentialExecution(data);
			else
				parallelExecution();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private void createRootPath() {
		File root = new File(GlobalVariable.getAppsRootFolder());

		try{
			if (!root.isDirectory()) {
				root.mkdir();
			}
			String outputLogStr = root.getPath()+File.separator+MODULE_ROOT_FOLDER+File.separator+TASK_ROOT_FOLDER+File.separator+LOG_ROOT_FOLDER+File.separator;
			File outputLogPath = new File(outputLogStr);
			if (!outputLogPath.isDirectory()) {
				outputLogPath.mkdirs();
			}
			logPath = outputLogPath.getPath();
			
			String outputReportPathStr = root.getPath()+File.separator+MODULE_ROOT_FOLDER+File.separator+TASK_ROOT_FOLDER+File.separator+REPORT_ROOT_FOLDER+File.separator;
			File outputReportPath = new File(outputReportPathStr);
			if (!outputReportPath.isDirectory()) {
				outputReportPath.mkdirs();
			}
			reportPath = outputReportPath.getPath();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			debug(e.getMessage());
		}
	}

	private void sequentialExecution(final JobDataMap data) throws InterruptedException {
		int counter = 0;
		ExecutableTaskDTO firstTask = getFirstTask();
		ExecutableTaskDTO taskToBeExecuted = getFirstTask();
		logger.debug("Preparing to execute task...");
		logger.debug("Initializing...");
		isTaskError = false;
		requestDetail = taskRequestServiceImpl.getTaskRequestDetailByTaskRequestDetailId(firstTaskRequestDetailId);
		data.put("requestDetailId", requestDetail.getId());
		// add comment biar ke-merge,TPS ITSM 15102208595234 20151002
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMYYYYHHmmss");
		Date today = new Date();
		String date = sdf.format(today);
		Map<String, Object> loggerParam = new HashMap<>();
		
		createRootPath();
		
		loggerParam.put("taskName", taskToBeExecuted.getMainTask().getTaskName());
		loggerParam.put("taskId", ""+ taskToBeExecuted.getMainTask().getId());
		loggerParam.put("taskRunnerId","" + taskToBeExecuted.getTaskRunnerId());
		loggerParam.put("taskRequestid", "" + taskToBeExecuted.getId());
		loggerParam.put("taskRequestDetailId", "" + taskToBeExecuted.getRequestDetailId());
		loggerParam.put("parameterValues", taskToBeExecuted.getKeyValues());

		String logFilePath = logPath + File.separator+ requestDetail.getTaskRequestId() + "_" + date+ ".txt";
		System.out.println(logFilePath);
		setLogger(logFilePath, loggerParam);
		requestDetail.setOutputLog(logFilePath);
		logger.debug("Initialization completed...");
		Company company = companyService.getCompanyByIdAndEffectiveDate(securityProfile.getWorkspaceCompanyId(), DateUtils.truncate(today, Calendar.DATE));
		
		while (taskToBeExecuted != null) {
			completed = false;
			try {
				logger.debug("executing task with...");
				logger.debug("Task Request ID : "+ taskToBeExecuted.getId());
				logger.debug("Task Runner ID : "+ taskToBeExecuted.getTaskRunnerId());
				logger.debug("Trigger ID : "+ taskToBeExecuted.getParentRequestId());
				if (company != null)
					logger.debug("Company : "+ company.getCompanyName());
				
				
				logger.debug("Updating Task Request Detail with ID : " + requestDetail.getId() + " pahse into Running...");
				
				requestDetail.setDateStarted(new Date());
				requestDetail.setTaskPhase(TaskPhase.RUNNING.getValue());
				requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
				taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
				if (taskToBeExecuted.getMainTask().getTaskType().equalsIgnoreCase(TaskType.BATCH.getValue())) {
					Map<String, Object> param = new HashMap<String, Object>();
					param = taskToBeExecuted.getKeyValues();
					executebleTask = (ExecutableTask) factory.create(jcl, taskToBeExecuted.getMainTask().getClassName());
					param.put(StaticParameterKey.COMPANY_ID.toString(), securityProfile.getWorkspaceCompanyId());
					param.put(StaticParameterKey.SECURITY_PROFILE.toString(), securityProfile);
					param.put(StaticParameterKey.BUSINESS_GROUP_ID.toString(), securityProfile.getWorkspaceBusinessGroupId());
					/*Add By RIM #14060510530040 Perbaikan Report HCMS Fase 1 – Report Finalize*/
					param.put(StaticParameterKey.REQUEST_ID.toString(), taskToBeExecuted.getId());
					/*End Add By RIM #14060510530040 Perbaikan Report HCMS Fase 1 – Report Finalize*/
					executebleTask.setParameter(param);
					executebleTask.setApplicationContext(applicationContext);
					logger.debug("logFilePath" + logFilePath);
					
					logger.debug("Executing task...");
					
					executebleTask.setLogger(log);
					Thread t = new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								executebleTask.execute();
							} catch(Exception ex) {
								//logger.error(ex.getMessage(), ex);
								isTaskError = true;
								data.put("isTaskError", isTaskError);
								throw new RuntimeException(ex.getMessage(), ex);
							}
							finally{
								completed = true;
							}
							
						}
					});
					t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
						
						@Override
						public void uncaughtException(Thread t, Throwable e) {
							logger.error(e.getMessage(), e);
							
							StringBuilder errorMessage = new StringBuilder();
							errorMessage.append(e.getMessage()+"\n");
							
							for(int errorLine = 0; errorLine<e.getStackTrace().length; errorLine++){
								errorMessage.append(e.getStackTrace()[errorLine]+"\n");
							}
							
							if(errorMessage.length() > 4000){
								errorMessage.substring(0, 3999);
							}
							
							logger.debug("Task is finished with error occurred. \n");
							requestDetail.setTaskStatus(TaskStatus.ERROR.getValue());
							requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
							requestDetail.setDateCompleted(new Date());
							taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
							
							closeLogger(TaskStatus.ERROR.getValue(), errorMessage.toString());
						}
					});
					t.start();
					while(!(interrupted || completed)) {
						try {
							t.join(10 * 1000);
						} catch (InterruptedException e) {
							t.stop(); // Very dangerous
							isTaskError = true;
							data.put("isTaskError", isTaskError);
							throw e;
						} catch (Exception e) {
							isTaskError = true;
							data.put("isTaskError", isTaskError);
							throw e;
						}
					}
					if (interrupted) {
						t.stop(); // Very dangerous
						throw new InterruptedException("Task interrupted");
					}
				} else {
					executeReport(taskToBeExecuted, logFilePath, loggerParam);
				}
				taskToBeExecuted = getExecuteSuccesTask(taskToBeExecuted);
				
			} catch (Exception e) {
				
				
				logger.error(e.getMessage(), e);
				
				StringBuilder errorMessage = new StringBuilder();
				errorMessage.append(e.getMessage()+"\n");
				
				for(int errorLine = 0; errorLine<e.getStackTrace().length; errorLine++){
					errorMessage.append(e.getStackTrace()[errorLine]+"\n");
				}
				
				if(errorMessage.length() > 4000){
					errorMessage.substring(0, 3999);
				}
				
				if (interrupted) {
					logger.debug("Task was interrupted. \n");
					requestDetail.setTaskStatus(TaskStatus.CANCELED.getValue());
					requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
					requestDetail.setDateCompleted(new Date());
					taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
					saveError(errorMessage.toString(), taskToBeExecuted);
					reserveRequestDetailForNextOccurrence(globalContext, requestDetail, firstTask, counter);
					closeLogger(TaskStatus.CANCELED.getValue(), errorMessage.toString());
					taskToBeExecuted = null;
				} else {
					isTaskError = true;
					data.put("isTaskError", isTaskError);
					logger.debug("Task is finished with error occurred. \n");
					requestDetail.setTaskStatus(TaskStatus.ERROR.getValue());
					requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
					requestDetail.setDateCompleted(new Date());
					taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
					saveError(errorMessage.toString(), taskToBeExecuted);
					
					reserveRequestDetailForNextOccurrence(globalContext,requestDetail, firstTask, counter);
					
					taskToBeExecuted = getExecuteErrorTask(taskToBeExecuted);
					if(taskToBeExecuted== null){
						closeLogger(TaskStatus.ERROR.getValue(), errorMessage.toString());
					}else{
						reserverRequestDetailForNextTask(taskToBeExecuted,requestDetail, globalContext);
						if (taskToBeExecuted.getMainTask().getTaskType().equalsIgnoreCase(TaskType.BATCH.getValue())){//TODO null pointer di maintask get TaskType
							closeLogger(TaskStatus.ERROR.getValue(), errorMessage.toString());
						}else{
							closeLogger(TaskStatus.ERROR.getValue(), errorMessage.toString());
						}
					}
					
				}
				
			} finally {
				counter++;
			}
		}
		
		if (!isTaskError && !interrupted) {
			requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
			requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
			requestDetail.setDateCompleted(new Date());
			taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
			if (firstTask.getMainTask().getTaskType().equalsIgnoreCase(TaskType.BATCH.getValue())){
				executebleTask.debug("Task execution is finished.\n");
				logger.debug("Task execution is finished.\n");
				closeLogger(TaskStatus.NORMAL.getValue(), null);
			}else{
				debug("Task execution is finished.\n");
				closeLogger(TaskStatus.NORMAL.getValue(), null);
			}
		} else if (completed && isTaskError) {
			
		}
		reserveRequestDetailForNextOccurrence(globalContext, requestDetail, firstTask, 0);
		
		
	}

	private void reserverRequestDetailForNextTask(ExecutableTaskDTO taskToBeExecuted,TaskRequestDetail requestDetail, JobExecutionContext globalContext) {
		if (taskToBeExecuted != null) {
			requestDetail.setDateCompleted(null);
			requestDetail.setDateStarted(globalContext.getNextFireTime());
			requestDetail.setTaskPhase(TaskPhase.PENDING.getValue());
			requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
			requestDetail.setTaskRequestId(taskToBeExecuted.getId());
			if (taskToBeExecuted.getMainTask().getId() != null && taskToBeExecuted.getMainTask().getId() != -1) {
				taskRequestServiceImpl.saveTaskRequestDetail(requestDetail);
				taskToBeExecuted.setRequestDetailId(requestDetail.getId());
			}
		}

	}

	private void reserveRequestDetailForNextOccurrence(JobExecutionContext globalContext, TaskRequestDetail requestDetail, ExecutableTaskDTO taskToBeExecuted, int counter) {
		if (globalContext.getNextFireTime() != null) {
			requestDetail.setDateCompleted(null);
			requestDetail.setDateStarted(globalContext.getNextFireTime());
			requestDetail.setTaskPhase(TaskPhase.PENDING.getValue());
			requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
			requestDetail.setOutputLog(null);
			if (taskToBeExecuted.getMainTask().getId() != null && taskToBeExecuted.getMainTask().getId() != -1) {
				if (taskToBeExecuted.getRequestDetailId() == firstTaskRequestDetailId) {
					if(taskToBeExecuted.getEndType().equals(TaskEndDateType.END_AFTER.getValue())){
						if (occurrence != executionCount + 1) {
							taskRequestServiceImpl.saveTaskRequestDetail(requestDetail);
						}
					}else{
						taskRequestServiceImpl.saveTaskRequestDetail(requestDetail);
					}
				}
				taskToBeExecuted.setRequestDetailId(requestDetail.getId());
				executableTaskDtoList.set(counter, taskToBeExecuted);
			}
		}

	}

	@Transactional
	private void parallelExecution() {
		for (ExecutableTaskDTO taskToBeExecuted : executableTaskDtoList) {
			ParallelExecutionTask parallelExecutionTask = new ParallelExecutionTask();
			parallelExecutionTask.setContext(globalContext);
			parallelExecutionTask.setExecutebleTask(executebleTask);
			requestDetail = taskRequestServiceImpl.getTaskRequestDetailByTaskRequestDetailId(taskToBeExecuted.getRequestDetailId());
			parallelExecutionTask.setRequestDetail(requestDetail);
			parallelExecutionTask.setTaskToBeExecuted(taskToBeExecuted);
			parallelExecutionTask.setFactory(factory);
			parallelExecutionTask.setJcl(jcl);
			parallelExecutionTask.setSecurityProfile(securityProfile);
			Thread t = new Thread(parallelExecutionTask);
			executor.execute(t);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			logger.debug("terminating parallel task...");
			executor.shutdownNow();
			logger.error(e.getMessage(), e);
		}

	}

	private ExecutableTaskDTO getExecuteSuccesTask(ExecutableTaskDTO exeDto) {
		if (exeDto.getSuccessTask().getId() != null && exeDto.getSuccessTask().getId() != -1) {
			logger.debug("Task with Task Name : " + exeDto.getSuccessTask().getTaskName() + " is going to be executed");
			return exeDto = getNextSuccessTask(exeDto);
		} else {
			return exeDto = null;
		}
	}

	private ExecutableTaskDTO getExecuteErrorTask(ExecutableTaskDTO exeDto) {
		if (exeDto.getErrorTask().getId() != null && exeDto.getErrorTask().getId() != -1) {
			return exeDto = getNextErrorTask(exeDto);
		} else {
			return exeDto = null;
		}
	}
	
	@Transactional
	private void saveError(String message, ExecutableTaskDTO taskToBeExecuted) {
		ErrorLogService errorLogServiceImpl = (ErrorLogService) applicationContext .getBean("errorLogService");
		ModuleErrorLog moduleErrorLog = new ModuleErrorLog();
		moduleErrorLog.setErrorMessage(message);
		moduleErrorLog.setLogTime(new Date());
		moduleErrorLog.setModuleName("BATCH PROCCESS - " + taskToBeExecuted.getMainTask().getTaskName() + " - TASK REQUEST ID (" + taskToBeExecuted.getId() + ")");
		moduleErrorLog.setTrxId(Long.parseLong(taskToBeExecuted .getParentRequestId()));
		try {
			errorLogServiceImpl.insertErrorLog(moduleErrorLog);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	@Transactional
	private String getExecutionType() {
		ExecutableTaskDTO executableTaskDto = executableTaskDtoList.get(0);
		if (executableTaskDto.getExecutionType().equalsIgnoreCase(TaskExecutionType.SEQUENTIAL.toString()))
			return TaskExecutionType.SEQUENTIAL.getValue();
		else
			return TaskExecutionType.PARALLEL.getValue();
	}

	@Transactional
	private ExecutableTaskDTO getNextSuccessTask(ExecutableTaskDTO currentTask) {
		ExecutableTaskDTO nextSuccessTask = null;
		for (ExecutableTaskDTO executableTaskDto : executableTaskDtoList) {
			if (executableTaskDto.getMainTask().getTaskName().equals(currentTask.getSuccessTask().getTaskName())) {
				nextSuccessTask = executableTaskDto;
			}
		}
		return nextSuccessTask;
	}

	@Transactional
	private ExecutableTaskDTO getNextErrorTask(ExecutableTaskDTO currentTask) {
		ExecutableTaskDTO nextErrorTask = null;
		for (ExecutableTaskDTO executableTaskDto : executableTaskDtoList) {
			if (executableTaskDto.getMainTask().getTaskName().equals(currentTask.getErrorTask().getTaskName())) {
				nextErrorTask = executableTaskDto;
			}
		}
		return nextErrorTask;
	}

	@Transactional
	private ExecutableTaskDTO getFirstTask() {
		int min = Integer.MAX_VALUE;
		ExecutableTaskDTO firstTask = new ExecutableTaskDTO();
		for (ExecutableTaskDTO executableTaskDto : executableTaskDtoList) {
			executableTaskDto.setRequestDetailId(executableTaskDtoList.get(0).getRequestDetailId());
			if (executableTaskDto.getSequence() < min) {
				min = executableTaskDto.getSequence();
				firstTask = executableTaskDto;
			}
		}
		return firstTask;
	}

	@Transactional
	public Map<String, Object> getTaskParameter() {
		return taskParameter;
	}

	public void setTaskParameter(Map<String, Object> taskParameter) {
		this.taskParameter = taskParameter;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		JobDataMap data = context.getJobDetail().getJobDataMap();
		logger.info("Initializing Job" + context.getJobDetail().getKey()+ "...");
		globalContext = context;

		try {
			applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
		executableTaskDtoList = (List<ExecutableTaskDTO>) data.get("executableTaskDto");
		for(ExecutableTaskDTO list : executableTaskDtoList){
			list.setRequestDetailId(executableTaskDtoList.get(0).getRequestDetailId());
		}
		
		Collections.sort(executableTaskDtoList, new Comparator<ExecutableTaskDTO>() {
			
			@Override
			public int compare(ExecutableTaskDTO o1, ExecutableTaskDTO o2) {
				return o1.getSequence().compareTo(o2.getSequence());
			}
		}
				);
		
		firstTaskRequestDetailId = executableTaskDtoList.get(0).getRequestDetailId();
		if (executableTaskDtoList.get(0).getEndAfterOccurence() != null) {
			occurrence = (executableTaskDtoList.get(0).getEndAfterOccurence());
		} else {
			occurrence = 0;
		}
		executionCount = (int) data.get("executionCount");
		if(data.get("executionCount") == null){
			executionCount = 0;
		}else{
			executionCount = data.getInt("executionCount");
		}
		securityProfile = (SecurityProfile) data.get("securityProfile");
		companyService = (CompanyService) applicationContext.getBean("companyServiceImpl");
		taskRequestServiceImpl = (TaskRequestService) applicationContext.getBean("taskRequestService");
		printerUtilService = (PrinterUtilService) applicationContext.getBean("printerUtilServiceImpl");
		printerService = (PrinterService) applicationContext.getBean("printerServiceImpl");
		organizationService = (OrganizationSetupService) applicationContext.getBean("organizationSetupServiceImpl");
		reportSecurityResolver = (ReportSecurityResolver) applicationContext.getBean("reportSecurityResolver");
		logger.info("Finish initializing Job" + context.getJobDetail().getKey()+ "...");
		
		executeTask(data);
//		JobExecutionException jee = new JobExecutionException();
//		jobWasExecuted(context,  jee);

	}

	@Override
	@Transactional
	public String getName() {
		return jobListenerName;
	}

	@Override
	@Transactional
	public void jobToBeExecuted(JobExecutionContext context) {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		data.put("executionCount", executionCount);
		
	}

	@Override
	@Transactional
	public void jobExecutionVetoed(JobExecutionContext context) {
		/*String jobKey = context.getJobDetail().getKey().toString();*/

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		String jobName = context.getJobDetail().getKey().toString();
		
		try {
			applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e); 
		}
		
//		if(data.containsKey("executionCount")){
//			executionCount = data.getInt("executionCount");
//		}
//		++executionCount;
//		data.put("executionCount", executionCount);
		executableTaskDtoList = (List<ExecutableTaskDTO>) data.get("executableTaskDto");
		if (executableTaskDtoList.get(0).getEndAfterOccurence() != null) {
			occurrence = (executableTaskDtoList.get(0).getEndAfterOccurence() );
		} else {
			occurrence = 0;
		}
		isTaskError = data.getBoolean("isTaskError");
		executableTaskDtoList = (List<ExecutableTaskDTO>) data.get("executableTaskDto");
		taskRequestServiceImpl = (TaskRequestService) applicationContext.getBean("taskRequestService");
		Collections.sort(executableTaskDtoList, new Comparator<ExecutableTaskDTO>() {
			
			@Override
			public int compare(ExecutableTaskDTO o1, ExecutableTaskDTO o2) {
				return o1.getSequence().compareTo(o2.getSequence());
			}
		}
				);
		executionCount = taskRequestServiceImpl.getFireTime(executableTaskDtoList.get(0).getId());
		if(!isTaskError && !interrupted){
			if(executableTaskDtoList.get(0).getEndType().equals(TaskEndDateType.END_AFTER.getValue())){
				if ((executionCount >= occurrence)) {
					completeTask(context, jobName);
					return;
				}
			}else
			if(executableTaskDtoList.get(0).getEndType().equals(TaskEndDateType.END_BY_DATE.getValue()) &&context.getNextFireTime()==null){
				completeTask(context, jobName);
				return;
			}
		}else if(!interrupted){
			completeErrorTask(context, jobName);
			return;
		}

	}

	@Transactional
	private void completeTask(JobExecutionContext context, String jobName) {
		final JobDataMap data = context.getJobDetail().getJobDataMap();
		ApplicationContext applicationContext= null;
		try {
			applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
		
		TaskRequestService taskRequestServiceImpl = (TaskRequestService) applicationContext.getBean("taskRequestService");
		try {
			requestDetail = taskRequestServiceImpl.getTaskRequestDetailByTaskRequestDetailId((Long) data.get("requestDetailId"));
			requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
			requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
			requestDetail.setDateCompleted(new Date());
			taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
			
			TaskRequest finishedRequest = taskRequestServiceImpl.getTaskRequestByTaskRequestId(executableTaskDtoList.get(0).getId());
			finishedRequest.setDateCompleted(new Date());
			taskRequestServiceImpl.updateTaskReqeuest(finishedRequest);
			context.getScheduler().deleteJob(context.getJobDetail().getKey());
			logger.debug("job with jobName : " + jobName + " is going to be deleted for it has reached its occurence times.");
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Transactional
	private void completeErrorTask(JobExecutionContext context, String jobName) {
		final JobDataMap data = context.getJobDetail().getJobDataMap();
		ApplicationContext applicationContext= null;
		try {
			applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
		
		TaskRequestService taskRequestServiceImpl = (TaskRequestService) applicationContext.getBean("taskRequestService");
		try {
			requestDetail = taskRequestServiceImpl.getTaskRequestDetailByTaskRequestDetailId((Long) data.get("requestDetailId"));
			requestDetail.setTaskStatus(TaskStatus.ERROR.getValue());
			requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
			requestDetail.setDateCompleted(new Date());
			taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
			
			TaskRequest finishedRequest = taskRequestServiceImpl.getTaskRequestByTaskRequestId(executableTaskDtoList.get(0).getId());
			finishedRequest.setDateCompleted(new Date());
			taskRequestServiceImpl.updateTaskReqeuest(finishedRequest);
			// FIXME: check occurrence then delete job if occurrence >= executionCount 
			// context.getScheduler().deleteJob(context.getJobDetail().getKey());
			logger.debug("job with jobName : " + jobName + " is going to be deleted for it has reached its occurence times.");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void interrupt() throws UnableToInterruptJobException {
		logger.debug("Interrupting Task");
		interrupted = true;
		if (getExecutionType().equalsIgnoreCase( TaskExecutionType.PARALLEL.getValue())) {
			try {
				globalContext.getScheduler().deleteJob( globalContext.getJobDetail().getKey());
			} catch (SchedulerException e) {
				logger.error(e.getMessage(), e);
			}
			executor.shutdown();
			currentThread.interrupt();
		} else {
			currentThread.interrupt();
		}

	}
	
	
	@Transactional
	public void setLogger(String filePath, Map<String, Object> param){
		File file = new File(filePath);
		try {
			log = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		log.println("========== Proccess Starts ==========");
		log.println("Task Name\t\t: "+param.get("taskName"));
		log.println("Task Id\t\t\t: "+param.get("taskId"));
		log.println("Task Runner Id \t\t: "+param.get("taskRunnerId"));
		log.println("Task Request Id \t:"+param.get("taskRequestid"));
		log.println("Task Request Detail Id \t:"+param.get("taskRequestDetailId"));
		log.println("Proccess start at "+new Timestamp(new Date().getTime()));
		log.println("=====================================");
		log.println();
		@SuppressWarnings("unchecked")
		Map<String,Object> parameterValues = (Map<String, Object>) param.get("parameterValues");
		if(parameterValues!=null){
			log.println("Task parameter values : ");
			for(String key : parameterValues.keySet()){
				log.println("\t" + key +" : " + parameterValues.get(key));
			}
			log.println();
		}
		log.flush();
	}
	
	public PrintWriter getPrinterWriter(){
		return log;
	}
	
	public void debug(String message){
		Date timeStamp = new Date();
		log.println(timeStamp+ " " +message);
	}
	
	@Transactional
	public void closeLogger(String taskStatus, String errorMessage){
		log.println();
		log.println("=====================================");
		log.println("Task "+taskStatus+" at "+new Timestamp(new Date().getTime()));
		log.println("Task done at "+new Timestamp(new Date().getTime()));
		if(taskStatus.equalsIgnoreCase(TaskStatus.ERROR.getValue())){
			log.println("Error caused by : "+errorMessage);
		}
		log.println("========= Proccess Completed =========");
		log.flush();
		log.close();
	}
	
	private void executeReport(ExecutableTaskDTO taskToBeExecuted, String logFilePath, Map<String, Object> loggerParam)
			throws Exception{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmS");
		sdf.format(date);
		requestDetail.setOutputLog(logFilePath);
		setLogger(logFilePath, loggerParam);
		debug("Begin Executing Jasper report....");
		Map<String, Object> param = new HashMap<String, Object>();
		param = taskToBeExecuted.getKeyValues();
		Map<String, Object> jasperParam = new HashMap<String, Object>();
		for(String key : param.keySet()){
			Object object = param.get(key);
			if(object != null){
				if(!object.toString().isEmpty() 
						&& object.toString().length() >= 11
						&& object.toString().charAt(2)=='-'
						&& object.toString().charAt(6)=='-'){
					try {
						object = sdf.parse(object.toString());
					} catch(Exception ex) {
						object = new Date();
					}
				}
				jasperParam.put(key, object.toString());
			}
			
		} 
		
//		param.put("IS_IGNORE_PAGINATION", true);
		
		//Get Security Filter based on Responsibility
		param.put(StaticParameterKey.COMPANY_ID.toString(), securityProfile.getWorkspaceCompanyId());
		param.put(StaticParameterKey.BUSINESS_GROUP_ID.toString(), securityProfile.getWorkspaceBusinessGroupId());
//		System.out.println(param.get(StaticParameterKey.STATIC_PARAM_BRANCH.toString()));
//		System.out.println(param.get(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString()));
//		System.out.println(param.get(StaticParameterKey.STATIC_PARAM_WITHOUT_POS.toString()));
		
		List<Long> orgs = new ArrayList<Long>();
		
		if(taskToBeExecuted.getMainTask().isHasStaticParams()){
			Long branchParam = null;
			Long orgParam = null;
			
			if(param.get(StaticParameterKey.STATIC_PARAM_BRANCH.toString()) != null && !param.get(StaticParameterKey.STATIC_PARAM_BRANCH.toString()).equals("")){
				branchParam = (Long) param.get(StaticParameterKey.STATIC_PARAM_BRANCH.toString());
				Organization selectedBranch = null;
				if (branchParam.equals(-1L)) {
					selectedBranch = new Organization();
					selectedBranch.setOrganizationName("Head Office");
				} else {
					selectedBranch = organizationService.findOrganizationById(branchParam);
				}
				param.put(StaticParameterKey.STATIC_BRANCH_NAME.toString()
						, selectedBranch != null ? selectedBranch.getOrganizationName() : "" );
			} 
//			else {
//				branchParam = securityProfile.getBranchId();
//			}
			
			if(param.get(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString()) != null && !param.get(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString()).equals("")){
				orgParam = (Long) param.get(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString());
				Organization selectedOrganization = organizationService.findOrganizationById(orgParam);
				param.put(StaticParameterKey.STATIC_ORG_NAME.toString()
						, selectedOrganization != null ? selectedOrganization.getOrganizationName() : "");
			}
//			else {
//				orgParam = securityProfile.getOrganizationId();
//			}
			
			Boolean isWithoutPos = (Boolean) param.get(StaticParameterKey.STATIC_PARAM_WITHOUT_POS.toString());
			
			if (branchParam != null || orgParam != null) {
				orgs = reportSecurityResolver.getAuthorizedOrganization(
						branchParam,
						orgParam,
						isWithoutPos,
						securityProfile.getSecurity().getSecurityFilter(),
						securityProfile.getWorkspaceCompanyId());
			} else {
				orgs = securityProfile.getSecurity().getSecurityFilter().getInOrganizations();
			}
			/*if(!branchParam.isEmpty() || branchParam != null){
				param.put("branchParam",branchParam);
			}
			*/
			
			if(param.get(StaticParameterKey.DEPENDENT_PARAM_COMPANY.toString()) != null && !param.get(StaticParameterKey.DEPENDENT_PARAM_COMPANY.toString()).equals("")){
				Long companyId = Long.valueOf(param.get(StaticParameterKey.DEPENDENT_PARAM_COMPANY.toString()).toString());
				param.put(StaticParameterKey.DEPENDENT_PARAM_COMPANY.toString(),companyId);
			}else if(param.get(StaticParameterKey.DEPENDENT_PARAM_BRANCH.toString()) != null && !param.get(StaticParameterKey.DEPENDENT_PARAM_BRANCH.toString()).equals("")){
				Long branchId = Long.valueOf(param.get(StaticParameterKey.DEPENDENT_PARAM_BRANCH.toString()).toString());
				param.put(StaticParameterKey.DEPENDENT_PARAM_BRANCH.toString(),branchId);
			}else if(param.get(StaticParameterKey.DEPENDENT_PARAM_LOCATION.toString()) != null && !param.get(StaticParameterKey.DEPENDENT_PARAM_LOCATION.toString()).equals("")){
				Long locationId = Long.valueOf(param.get(StaticParameterKey.DEPENDENT_PARAM_LOCATION.toString()).toString());
				param.put(StaticParameterKey.DEPENDENT_PARAM_LOCATION.toString(),locationId);
			}else if(param.get(StaticParameterKey.DEPENDENT_PARAM_ORGANIZATION.toString()) != null && !param.get(StaticParameterKey.DEPENDENT_PARAM_ORGANIZATION.toString()).equals("")){
				Long locationId = Long.valueOf(param.get(StaticParameterKey.DEPENDENT_PARAM_ORGANIZATION.toString()).toString());
				param.put(StaticParameterKey.DEPENDENT_PARAM_ORGANIZATION.toString(),locationId);
			}else if(param.get(StaticParameterKey.DEPENDENT_PARAM_EMPLOYEE.toString()) != null && !param.get(StaticParameterKey.DEPENDENT_PARAM_EMPLOYEE.toString()).equals("")){
				Long personId = Long.valueOf(param.get(StaticParameterKey.DEPENDENT_PARAM_EMPLOYEE.toString()).toString());
				param.put(StaticParameterKey.DEPENDENT_PARAM_EMPLOYEE.toString(),personId);
			}
		}else{
			orgs = reportSecurityResolver.getAuthorizedOrganization(securityProfile.getSecurity().getSecurityFilter(), securityProfile.getWorkspaceCompanyId());
			if(orgs == null){
				orgs = new ArrayList<>();
			}
		}
		
		if(param.containsKey("branchParam")){
			orgs.remove(0);
			//remove branch id itself
		}
		param.put(StaticParameterKey.AUTHORIZED_ORGANIZATIONS.toString(), orgs);
		if (orgs != null && !orgs.isEmpty()) {
			List<String> orgQuery = Lists.transform(orgs, new Function<Long, String>() {
	
				@Override
				public String apply(Long input) {
					return "{0} = " + String.valueOf(input);
				}
			});
						
			param.put(StaticParameterKey.ORG_QUERY.toString(), "AND (" + Joiner.on(" OR ").skipNulls().join(orgQuery) + ")");
		}else
			param.put(StaticParameterKey.ORG_QUERY.toString(), "");
		
		param.put(StaticParameterKey.STATIC_DATE.toString(), DateUtils.truncate(date, Calendar.DATE));
		
		if (SecurityType.VIEW_OWN.toString().equals(securityProfile.getSecurity().getSecurityType())) {
			param.put(StaticParameterKey.STATIC_PERSON_ID.toString(), securityProfile.getPersonId());
			param.put(StaticParameterKey.STATIC_PARAM_EMPLOYEE.toString(), securityProfile.getEmployeeNumber());
		}
		
		param.put(StaticParameterKey.STATIC_TIME.toString(), date);
		
		TaskDTO currentTask = taskToBeExecuted.getMainTask();
		
        String jasperUrl = currentTask.getExecutableFile().getExecutableFileName();
		
		//String jasperUrl = "D:\\" + currentTask.getExecutableFile().getExecutableFileName();
		
		File subDir = new File(jasperUrl);
		String subdirPath = subDir.getParent()+File.separator;
		param.put(StaticParameterKey.SUBREPORT_DIR.toString(),subdirPath);
		String resourceDir = GlobalVariable.getAppsRootFolder() + File.separator + "SYSTEM_ADMINISTRATION" 
				+ File.separator + "Task" + File.separator + "Executable Files" + File.separator + "resources" + File.separator;
		param.put(StaticParameterKey.RESOURCES_DIR.toString(), resourceDir);
		
		File file = new File(jasperUrl);
		String fileName = file.getName().substring(0, file.getName().length() - 7);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromLocation(jasperUrl);
		jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
		jasperReport.setWhenResourceMissingType(WhenResourceMissingTypeEnum.NULL);
		jasperReport.setProperty(JRJdbcQueryExecuterFactory.PROPERTY_JDBC_FETCH_SIZE, "1000");
		jasperReport.setProperty("net.sf.jasperreports.xpath.executer.factory", "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
		
		String outputFormat = taskToBeExecuted.getOutputFormat();
		String outputFileName;
		if(outputFormat==null){
			param.put("IS_IGNORE_PAGINATION", false);
			outputFormat = OutputFileFormat.PDF.toString();
			outputFileName = reportPath + File.separator + fileName + "_" + requestDetail.getTaskRequestId() + "_" + sdf.format(date) + ".pdf";
		}else{
			if(outputFormat.equals(OutputFileFormat.EXCEL.toString())){
				param.put("IS_IGNORE_PAGINATION", true);
				outputFormat = "xls";
				outputFileName = reportPath + File.separator + fileName + "_" + requestDetail.getTaskRequestId() + "_" + sdf.format(date) + ".xls";
			}else{
				param.put("IS_IGNORE_PAGINATION", false);
				outputFileName = reportPath + File.separator + fileName + "_" + requestDetail.getTaskRequestId() + "_" + sdf.format(date) + ".pdf";
			}
		}
		
		DataSource ds = (DataSource) applicationContext .getBean("dataSource");
		Connection conn = DataSourceUtils.getConnection(ds);
		
		try{
		debug("filling report....");
		debug("Params : " + param);
		debug("Branch Id : " + param.get(StaticParameterKey.STATIC_PARAM_BRANCH.toString()));
		debug("Organization Id : " + param.get(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString()));
		debug("Authorized Organization : " + orgs);
		debug("Resources directory : " + param.get(StaticParameterKey.RESOURCES_DIR.toString()));
		JasperPrint jPrint = (JasperPrint) JasperFillManager .fillReport(jasperReport, param, conn);
		executableTaskDtoList.get(0).getKeyValues().clear();
		debug("exporting report....");
		if(outputFormat.equals(OutputFileFormat.PDF.toString())){
			JasperExportManager.exportReportToPdfFile(jPrint, outputFileName);
		}else{
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jPrint);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, outputFileName);
			exporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 65000);
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporter.exportReport();
		}
		requestDetail.setOutputFile(outputFileName);
		debug("Jasper Report Execution was done.");
		debug("Output file path "+outputFileName);
		param.clear();
		}catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			e.printStackTrace();
			StackTraceElement[] stactTraceElements = e.getStackTrace();
			for(StackTraceElement stactTraceElement : stactTraceElements) {
				sb.append("\tat " + stactTraceElement + "\n");
			}
			debug("========================== Error Stack Trace ==========================");
			debug(sb.toString());
			debug("========================== End Stack Trace ============================");
			conn.close();
			throw e;
		}finally{
			conn.close();
		}
		
		 
		if(taskToBeExecuted.getAfterCompleted().equals(TaskAfterCompleted.PRINT.toString())){
			debug("Preparing for print..");
			PrinterExample example = new PrinterExample();
			example.createCriteria().andPrinterIdEqualTo(currentTask.getPrinterId());
			Printer printer = printerService.getPrinterByExample(example).get(0);
			Map<String, String> printerParam = new HashMap<>();
			printerParam.put(PrinterParam.PRINTER_NAME,printer.getPrinterName());
			printerParam.put(PrinterParam.FILENAME, outputFileName);
			debug("Printing file "+outputFileName+" printer name : "+printer.getPrinterName());
			printerUtilService.print(printer, printerParam);
			
			//Delete Generated File
			debug("Deleting generated file ");
			File f = new File(outputFileName);
			if(f.exists()){
				f.delete();
				requestDetail.setOutputFile(null);
			}
		}
		
		// printing proccess
		/*if (currentTask.isPrintAfterCompleted()) {
			debug("Preparing for print..");
			PrinterExample example = new PrinterExample();
			example.createCriteria().andPrinterIdEqualTo(currentTask.getPrinterId());
			Printer printer = printerService.getPrinterByExample(example).get(0);
			Map<String, String> printerParam = new HashMap<>();
			printerParam.put(PrinterParam.PRINTER_NAME,printer.getPrinterName());
			printerParam.put(PrinterParam.FILENAME, outputFileName);
			debug("Printing file "+outputFileName+" printer name : "+printer.getPrinterName());
			printerUtilService.print(printer, printerParam);
		}
		
		if(!currentTask.isSaveAfterCompleted()){
				File f = new File(outputFileName);
				if(f.exists()){
					f.delete();
					requestDetail.setOutputFile(null);
				}
		}*/
		
	}
}
