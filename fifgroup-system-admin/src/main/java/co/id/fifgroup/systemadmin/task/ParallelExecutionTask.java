package co.id.fifgroup.systemadmin.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

import co.id.fifgroup.basicsetup.common.PrinterParam;
import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.domain.PrinterExample;
import co.id.fifgroup.basicsetup.service.PrinterService;
import co.id.fifgroup.basicsetup.service.PrinterUtilService;
import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.systemadmin.constant.OutputFileFormat;
import co.id.fifgroup.systemadmin.constant.TaskAfterCompleted;
import co.id.fifgroup.systemadmin.constant.TaskPhase;
import co.id.fifgroup.systemadmin.constant.TaskStatus;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.domain.ModuleErrorLog;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetail;
import co.id.fifgroup.systemadmin.dto.ExecutableTaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.security.ReportSecurityResolver;
import co.id.fifgroup.systemadmin.service.ErrorLogService;
import co.id.fifgroup.systemadmin.service.TaskRequestService;

public class ParallelExecutionTask implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ParallelExecutionTask.class);
	
	private static String EXECUTABLE_FILE_ROOT_FOLDER = "EXECUTABLE_FILES";
	
	private PrintWriter log;
	private ExecutableTaskDTO taskToBeExecuted;
	private ApplicationContext applicationContext;
	private JobExecutionContext context;
	private ExecutableTask executebleTask;
	private TaskRequestDetail requestDetail;
	private TaskRequestService taskRequestServiceImpl;
	private PrinterService printerService;
	private PrinterUtilService printerUtilService;
	private int occurrence;
	private int executionCount;
	private String logPath="";
	private String reportPath="";
	private JarClassLoader jcl;
	private JclObjectFactory factory;
	private SecurityProfile securityProfile;
	private ReportSecurityResolver reportSecurityResolver;
	private static String MODULE_ROOT_FOLDER = "SYSTEM_ADMINISTRATION";
	private static String TASK_ROOT_FOLDER = "Task";
	private static String LOG_ROOT_FOLDER = "Logs";
	private static String REPORT_ROOT_FOLDER = "Reports";

	public int getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(int occurrence) {
		this.occurrence = occurrence;
	}

	public int getExecutionCount() {
		return executionCount;
	}

	public void setExecutionCount(int executionCount) {
		this.executionCount = executionCount;
	}

	public ExecutableTaskDTO getTaskToBeExecuted() {
		return taskToBeExecuted;
	}

	public void setTaskToBeExecuted(ExecutableTaskDTO taskToBeExecuted) {
		this.taskToBeExecuted = taskToBeExecuted;
	}

	public ExecutableTask getExecutebleTask() {
		return executebleTask;
	}

	public void setExecutebleTask(ExecutableTask executebleTask) {
		this.executebleTask = executebleTask;
	}

	public TaskRequestDetail getRequestDetail() {
		return requestDetail;
	}

	public void setRequestDetail(TaskRequestDetail requestDetail) {
		this.requestDetail = requestDetail;
	}

	public JobExecutionContext getContext() {
		return context;
	}

	public void setContext(JobExecutionContext context) {
		this.context = context;
	}

	private void saveError(String message, ExecutableTaskDTO taskToBeExecuted) {
		ErrorLogService errorLogServiceImpl = (ErrorLogService) applicationContext.getBean("errorLogService");
		ModuleErrorLog moduleErrorLog = new ModuleErrorLog();
		moduleErrorLog.setErrorMessage(message);
		moduleErrorLog.setLogTime(new Date());
		moduleErrorLog.setModuleName(taskToBeExecuted.getMainTask().getTaskName());
		moduleErrorLog.setTrxId(Long.parseLong(taskToBeExecuted.getParentRequestId()));
		try {
			errorLogServiceImpl.insertErrorLog(moduleErrorLog);
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

	@Override
	public void run() {
		createRootPath();
		Map<String, String> loggerParam = new HashMap<>();
		loggerParam.put("taskName", taskToBeExecuted.getMainTask().getTaskName());
		loggerParam.put("taskId", ""+ taskToBeExecuted.getMainTask().getId());
		loggerParam.put("taskRunnerId","" + taskToBeExecuted.getTaskRunnerId());
		loggerParam.put("taskRequestid", "" + taskToBeExecuted.getId());
		
		try {
			applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
		} catch (SchedulerException e1) {
			logger.error(e1.getMessage(), e1);
		}
		taskRequestServiceImpl = (TaskRequestService) applicationContext.getBean("taskRequestService");
	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
		String date = sdf.format(new Date());
		
		String logFilePath = logPath + File.separator+ requestDetail.getTaskRequestId() + "_" + date+ ".txt";
		System.out.println(logFilePath);
		setLogger(logFilePath, loggerParam);
		requestDetail.setOutputLog(logFilePath);
		logger.debug("Initialization completed...");
		try {
			
			logger.debug("executing task with...");
			logger.debug("Task Request ID : "+ taskToBeExecuted.getId());
			logger.debug("Task Runner ID : "+ taskToBeExecuted.getTaskRunnerId());
			logger.debug("Trigger ID : "+ taskToBeExecuted.getParentRequestId());
			
			logger.debug("Updating Task Request Detail with ID : " + requestDetail.getId() + " pahse into Running...");
			
			requestDetail.setTaskRequestId(taskToBeExecuted.getId());
			requestDetail.setDateStarted(new Date());
			requestDetail.setTaskPhase(TaskPhase.RUNNING.getValue());
			requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
			taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
			
			
			if (taskToBeExecuted.getMainTask().getTaskType().equalsIgnoreCase(TaskType.BATCH.getValue())) {
				Map<String, Object> param = new HashMap<String, Object>();
				param = taskToBeExecuted.getKeyValues();
				executebleTask = (ExecutableTask) factory.create(jcl, taskToBeExecuted.getMainTask().getClassName());
				param.put(StaticParameterKey.COMPANY_ID.toString(), securityProfile.getWorkspaceCompanyId());
				param.put(StaticParameterKey.BUSINESS_GROUP_ID.toString(), securityProfile.getWorkspaceBusinessGroupId());
				/*Add By RIM #14060510530040 Perbaikan Report HCMS Fase 1 – Report Finalize*/
				param.put(StaticParameterKey.REQUEST_ID.toString(), taskToBeExecuted.getId());
				/*End Add By RIM - #14060510530040 Perbaikan Report HCMS Fase 1 – Report Finalize*/
				executebleTask.setParameter(param);
				executebleTask.setApplicationContext(applicationContext);
				
				
				executebleTask.setLogger(log);
				executebleTask.execute();

				requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
				requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
				requestDetail.setDateCompleted(new Date());
				taskRequestServiceImpl
						.updateTaskRequestDetail(requestDetail);

				reserveRequestDetailForNextOccurrence(context, requestDetail,
						taskToBeExecuted, occurrence);

			} else {
				applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
				printerService = (PrinterService) applicationContext.getBean("printerServiceImpl");
				printerUtilService = (PrinterUtilService) applicationContext.getBean("printerUtilServiceImpl");
				reportSecurityResolver = (ReportSecurityResolver) applicationContext.getBean("reportSecurityResolver");
				
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
							object = sdf.parse(object.toString());
						}
						jasperParam.put(key, object.toString());
					}
				} 
				
				param.put("IS_IGNORE_PAGINATION", true);
				
				//Get Security Filter based on Responsibility
				param.put(StaticParameterKey.COMPANY_ID.toString(), securityProfile.getWorkspaceCompanyId());
				param.put(StaticParameterKey.BUSINESS_GROUP_ID.toString(), securityProfile.getWorkspaceBusinessGroupId());
//				System.out.println(param.get(StaticParameterKey.STATIC_PARAM_BRANCH.toString()));
//				System.out.println(param.get(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString()));
//				System.out.println(param.get(StaticParameterKey.STATIC_PARAM_WITHOUT_POS.toString()));
				
				List<Long> orgs = new ArrayList<Long>();
				
				if(taskToBeExecuted.getMainTask().isHasStaticParams()){
					Long branchParam = null;
					Long orgParam = null;
					
					if(!param.get(StaticParameterKey.STATIC_PARAM_BRANCH.toString()).equals("")){
						branchParam = (Long) param.get(StaticParameterKey.STATIC_PARAM_BRANCH.toString());
					}
					if(!param.get(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString()).equals("")){
						orgParam = (Long) param.get(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString());
					}
					
					Boolean isWithoutPos = (Boolean) param.get(StaticParameterKey.STATIC_PARAM_WITHOUT_POS.toString());
					
					
					orgs = reportSecurityResolver.getAuthorizedOrganization(
							branchParam,
							orgParam,
							isWithoutPos,
							securityProfile.getSecurity().getSecurityFilter(),
							securityProfile.getWorkspaceCompanyId());
					/*if(!branchParam.isEmpty() || branchParam != null){
						param.put("branchParam",branchParam);
					}
					*/
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
				
				TaskDTO currentTask = taskToBeExecuted.getMainTask();
				
				String jasperUrl = currentTask.getExecutableFile().getExecutableFileName();
				
				File subDir = new File(jasperUrl);
				String subdirPath = subDir.getParent()+File.separator;
				param.put(StaticParameterKey.SUBREPORT_DIR.toString(),subdirPath);
				
				File file = new File(jasperUrl);
				String fileName = file.getName().substring(0, file.getName().length() - 7);
				@SuppressWarnings("deprecation")
				JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromLocation(jasperUrl);
				jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
				jasperReport.setWhenResourceMissingType(WhenResourceMissingTypeEnum.NULL);
				
				String outputFormat = taskToBeExecuted.getOutputFormat();
				String outputFileName;
				if(outputFormat==null){
					outputFormat = OutputFileFormat.PDF.toString();
					outputFileName = reportPath + File.separator + fileName + "_" + requestDetail.getTaskRequestId() + "_" + date + ".pdf";
				}else{
					if(outputFormat.equals(OutputFileFormat.EXCEL.toString())){
						outputFormat = "xls";
						outputFileName = reportPath + File.separator + fileName + "_" + requestDetail.getTaskRequestId() + "_" + date + ".xls";
					}else{
						outputFileName = reportPath + File.separator + fileName + "_" + requestDetail.getTaskRequestId() + "_" + date + ".pdf";
					}
				}
				
				DataSource ds = (DataSource) applicationContext .getBean("dataSource");
				Connection conn = DataSourceUtils.getConnection(ds);
				
				debug("filling report....");
				debug("Params : " + param);
				debug("Branch Id : " + param.get(StaticParameterKey.STATIC_PARAM_BRANCH.toString()));
				debug("Organization Id : " + param.get(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString()));
				debug("Authorized Organization : " + orgs);
				JasperPrint jPrint = (JasperPrint) JasperFillManager .fillReport(jasperReport, param, conn);
				debug("exporting report....");
				if(outputFormat.equals(OutputFileFormat.PDF.toString())){
					JasperExportManager.exportReportToPdfFile(jPrint, outputFileName);
				}else{
					JRXlsExporter exporter = new JRXlsExporter();
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jPrint);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, outputFileName);
					exporter.exportReport();
				}
				requestDetail.setOutputFile(outputFileName);
				debug("Jasper Report Execution was done.");
				debug("Output file path "+outputFileName);

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
			}

			requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
			requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
			requestDetail.setDateCompleted(new Date());
			taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
			if (taskToBeExecuted.getMainTask().getTaskType().equalsIgnoreCase(TaskType.BATCH.getValue())){
				debug("Task execution is finished.\n");
				closeLogger(TaskStatus.NORMAL.getValue(), null);
			}else{
				debug("Task execution is finished.\n");
				closeLogger(TaskStatus.NORMAL.getValue(), null);
			}

		} catch (Exception e) {
			if (e.getMessage().contains("InterruptedException")) {
				requestDetail.setTaskStatus(TaskStatus.CANCELED.getValue());
				requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
				requestDetail.setDateCompleted(new Date());
				saveError(e.getMessage(), taskToBeExecuted);
				
				
				if (taskToBeExecuted.getMainTask().getTaskType().equalsIgnoreCase(TaskType.BATCH.getValue())){
					closeLogger(TaskStatus.CANCELED.getValue(), e.getMessage());
				}else{
					closeLogger(TaskStatus.CANCELED.getValue(), e.getMessage());
				}
		 	} else {
		 		requestDetail.setTaskStatus(TaskStatus.ERROR.getValue());
		 		requestDetail.setTaskPhase(TaskPhase.COMPLETED.getValue());
		 		requestDetail.setDateCompleted(new Date());
		 		saveError(e.getMessage(), taskToBeExecuted);
		 		
		 		if(taskToBeExecuted== null){
					closeLogger(TaskStatus.ERROR.getValue(), e.getMessage());
				}else{
					if (taskToBeExecuted.getMainTask().getTaskType().equalsIgnoreCase(TaskType.BATCH.getValue())){
						closeLogger(TaskStatus.ERROR.getValue(), e.getMessage());
					}else{
						closeLogger(TaskStatus.ERROR.getValue(), e.getMessage());
					}
				}
				
			}
			taskRequestServiceImpl.updateTaskRequestDetail(requestDetail);
			logger.error(e.getMessage(), e);

			if (context.getNextFireTime() != null) {
				requestDetail = new TaskRequestDetail();
				requestDetail.setDateCompleted(null);
				requestDetail.setDateStarted(context.getNextFireTime());
				requestDetail.setTaskPhase(TaskPhase.PENDING.getValue());
				requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
				requestDetail.setTaskRequestId(taskToBeExecuted.getMainTask()
						.getTaskParameters().get(0).getTaskParameterValue()
						.getTaskRequestId());
				if (occurrence != executionCount + 1) {
					taskRequestServiceImpl
							.saveTaskRequestDetail(requestDetail);
					taskToBeExecuted.setRequestDetailId(requestDetail.getId());
				}
			}
		}
	}

	private void reserveRequestDetailForNextOccurrence(
			JobExecutionContext context, TaskRequestDetail requestDetail,
			ExecutableTaskDTO taskToBeExecuted, int occurrence) {
		if (context.getNextFireTime() != null) {
			requestDetail = new TaskRequestDetail();
			requestDetail.setDateCompleted(null);
			requestDetail.setDateStarted(context.getNextFireTime());
			requestDetail.setTaskPhase(TaskPhase.PENDING.getValue());
			requestDetail.setTaskStatus(TaskStatus.NORMAL.getValue());
			requestDetail.setTaskRequestId(taskToBeExecuted.getMainTask()
					.getTaskParameters().get(0).getTaskParameterValue()
					.getTaskRequestId());
			if (taskToBeExecuted.getMainTask().getId() != null
					&& taskToBeExecuted.getMainTask().getId() != -1) {
				if (occurrence != executionCount + 1) {
					taskRequestServiceImpl
							.saveTaskRequestDetail(requestDetail);
					taskToBeExecuted.setRequestDetailId(requestDetail.getId());
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private void interrupt() {
		this.interrupt();
	}

	public JarClassLoader getJcl() {
		return jcl;
	}

	public void setJcl(JarClassLoader jcl) {
		this.jcl = jcl;
	}

	public JclObjectFactory getFactory() {
		return factory;
	}

	public void setFactory(JclObjectFactory factory) {
		this.factory = factory;
	}

	public SecurityProfile getSecurityProfile() {
		return securityProfile;
	}

	public void setSecurityProfile(SecurityProfile securityProfile) {
		this.securityProfile = securityProfile;
	}
	
	public void setLogger(String filePath, Map<String, String> param){
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
		log.println("Proccess start at "+new Timestamp(new Date().getTime()));
		log.println("=====================================");
		log.println();
	}
	
	public PrintWriter getPrinterWriter(){
		return log;
	}
	
	public void debug(String message){
		Date timeStamp = new Date();
		log.println(timeStamp+ " " +message);
	}
	
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

}
