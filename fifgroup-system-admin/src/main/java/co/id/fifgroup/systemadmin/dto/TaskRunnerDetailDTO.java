package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.systemadmin.domain.TaskRunnerDetail;

public class TaskRunnerDetailDTO extends TaskRunnerDetail{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3283726987209046312L;
	
	private TaskDTO mainTask;
	private TaskDTO successTask;
	private TaskDTO errorTask;
	
	public TaskDTO getMainTask() {
		return mainTask;
	}
	public void setMainTask(TaskDTO mainTask) {
		this.mainTask = mainTask;
	}
	public TaskDTO getSuccessTask() {
		return successTask;
	}
	public void setSuccessTask(TaskDTO successTask) {
		this.successTask = successTask;
	}
	public TaskDTO getErrorTask() {
		return errorTask;
	}
	public void setErrorTask(TaskDTO errorTask) {
		this.errorTask = errorTask;
	}
	
	
}
