package co.id.fifgroup.systemadmin.dto;

import java.io.Serializable;

public class ExecutableFileDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String executableFileName;
	private String taskType;
	public String getExecutableFileName() {
		return executableFileName;
	}
	public void setExecutableFileName(String executableFileName) {
		this.executableFileName = executableFileName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	
}
