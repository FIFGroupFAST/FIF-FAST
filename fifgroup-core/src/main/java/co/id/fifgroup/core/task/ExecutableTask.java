package co.id.fifgroup.core.task;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import co.id.fifgroup.core.validation.TaskExecutionException;

public abstract class ExecutableTask {
	
	private ApplicationContext applicationContext;
	
	private Map<String, Object> parameter;
	
	public  Logger log = LoggerFactory.getLogger(ExecutableTask.class);
	
	public  PrintWriter logger;
	
	/**
	 * @return the logger
	 */
	public PrintWriter getLogger() {
		return logger;
	}

	public abstract void execute() throws TaskExecutionException;
	
	public void setParameter(Map<String, Object> params){
		this.parameter = params;
	}
	
	public Map<String, Object> getParameter(){
		return this.parameter;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public void setLogger(PrintWriter logger){
		this.logger = logger;
	}
	
	public PrintWriter getPrinterWriter(){
		return logger;
	}
	
	public void debug(String message){
		Date timeStamp = new Date();
		logger.println(timeStamp+ " " +message);
		logger.flush();
	}
	
	public void error(String message,Throwable t){
		StringWriter sw=new StringWriter();
		PrintWriter pw=new PrintWriter(sw);
		t.printStackTrace(pw);
		logger.println(message);
		logger.println(sw.toString());
		log.error(message, t);
	}
	
	public void closeLogger(String taskStatus, String errorMessage){
		
		logger.println();
		logger.println("=====================================");
		if(taskStatus.equalsIgnoreCase("normal")){
			logger.println("Task "+taskStatus+" at "+new Timestamp(new Date().getTime()));
		}else{
			logger.println("Task done at "+new Timestamp(new Date().getTime()));
		}
		if(taskStatus.equalsIgnoreCase("error")){
			logger.println("Error caused by : "+errorMessage);
		}
		logger.println("========= Proccess Completed =========");
		logger.flush();
		logger.close();
	}
	
	public Logger getLog(){
		return log;
	}
	

}
