package co.id.fifgroup.systemadmin.dto;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.systemadmin.domain.TaskParameterValue;
import co.id.fifgroup.systemadmin.domain.TaskRequest;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetail;
import co.id.fifgroup.systemadmin.domain.TaskRunner;

public class TaskRequestDTO extends TaskRequest {

	private static final long serialVersionUID = 1L;
	
	private TaskRequestDetail taskRequestDetail;
	private TaskRunner taskRunner;
	private List<TaskDTO> tasks;
	private String taskName;
	private String taskType;
	private Date dateSubmittedFrom;
	private Date dateSubmittedTo;
	
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public TaskRequestDetail getTaskRequestDetail() {
		return taskRequestDetail;
	}
	public void setTaskRequestDetail(TaskRequestDetail taskRequestDetail) {
		this.taskRequestDetail = taskRequestDetail;
	}
	public List<TaskDTO> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskDTO> tasks) {
		this.tasks = tasks;
	}
	private List<TaskParameterValue> taskParameterValues;
	
	public TaskRunner getTaskRunner() {
		return taskRunner;
	}
	public void setTaskRunner(TaskRunner taskRunner) {
		this.taskRunner = taskRunner;
	}
	
	public List<TaskParameterValue> getTaskParameterValues() {
		return taskParameterValues;
	}
	public void setTaskParameterValues(List<TaskParameterValue> taskParameterValues) {
		this.taskParameterValues = taskParameterValues;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getDateSubmittedFrom() {
		return dateSubmittedFrom;
	}
	public void setDateSubmittedFrom(Date dateSubmittedFrom) {
		this.dateSubmittedFrom = dateSubmittedFrom;
	}
	public Date getDateSubmittedTo() {
		return dateSubmittedTo;
	}
	public void setDateSubmittedTo(Date dateSubmittedTo) {
		this.dateSubmittedTo = dateSubmittedTo;
	}
}
