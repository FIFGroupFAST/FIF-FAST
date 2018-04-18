package co.id.fifgroup.systemadmin.dto;

import java.io.Serializable;
import java.util.Map;

import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.domain.TaskRequest;

public class ExecutableTaskDTO extends TaskRequest implements Cloneable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TaskDTO mainTask;
	private TaskDTO successTask;
	private TaskDTO errorTask;
	private Integer sequence;
	private String executionType;
	private Long requestDetailId;
	private Map<String,Object>keyValues;
	



	public Map<String, Object> getKeyValues() {
		return keyValues;
	}

	public void setKeyValues(Map<String, Object> keyValues) {
		this.keyValues = keyValues;
	}

	public Long getRequestDetailId() {
		return requestDetailId;
	}

	public void setRequestDetailId(Long requestDetailId) {
		this.requestDetailId = requestDetailId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	
	public String taskDetail(Task task, String taskName){
		String returnValue = "";
		if(task == null || task.getId()== -1){
			returnValue = "\n" + taskName + "\n"+ "Task Id : " + task.getId() + "\n";
		}else{
			returnValue = "\n" + taskName + "\n"
					+ "Task Id : " + task.getId() + "\n"
					+ "Task Name : " + task.getTaskName() + "\n"
					+ "Task Type : " + task.getTaskType() + "\n"
					+ "Task ExecutableFileID : " + task.getExecutableFileId() + "\n";
		}
		returnValue += "========";
		return returnValue;
	}

	@Override
	public String toString() {
		StringBuilder returnValue = new StringBuilder();
		returnValue.append(taskDetail(this.mainTask, "Main Task"));
		returnValue.append(taskDetail(this.successTask, "Success Task"));
		returnValue.append(taskDetail(this.errorTask, "Error Task"));
		String otherInfo = 
				"\ngetSequence()="+getSequence() 
				+ "\ngetExecutionType()=" + getExecutionType()
				+ "\ngetId()=" + getId() 
				+ "\ngetTaskRunnerId()="+ getTaskRunnerId() 
				+ "\ngetTaskId()=" + getTaskId()
				+ "\ngetDateSubmitted()=" + getDateSubmitted()
				+ "\ngetDateCompleted()=" + getDateCompleted()
//				+ "\ngetTaskStatus()=" + getTaskStatus() 
//				+ "\ngetTaskPhase()=" + getTaskPhase() 
				+ "\ngetScheduleType()=" + getScheduleType()
				+ "\ngetStartTime()=" + getStartTime() 
				+ "\ngetEndType()=" + getEndType() 
				+ "\ngetEndByDate()=" + getEndByDate()
				+ "\ngetEndAfterOccurence()=" + getEndAfterOccurence()
				+ "\ngetDailyPattern()=" + getDailyPattern() 
				+ "\ngetMonthlyPattern()=" + getMonthlyPattern() 
				+ "\ngetYearlyPattern()=" + getYearlyPattern()
				+ "\ngetTotalDay()=" + getTotalDay() 
				+ "\ngetRecurEvery()=" + getRecurEvery() 
				+ "\ngetRecurOn()=" + getRecurOn()
				+ "\ngetDayNum()=" + getDayNum() 
				+ "\ngetTotalMonth()=" + getTotalMonth() 
				+ "\ngetDayName()=" + getDayName()
				+ "\ngetSequenceType()=" + getSequenceType()
				+ "\ngetMonthName()=" + getMonthName()
				+ "\ngetCronExpression()=" + getCronExpression()
				+ "\ngetCreationDate()=" + getCreationDate()
				+ "\ngetCreatedBy()=" + getCreatedBy()
				+ "\ngetLastUpdateDate()=" + getLastUpdateDate()
				+ "\ngetLastUpdatedBy()=" + getLastUpdatedBy();
		returnValue.append(otherInfo);
		return returnValue.toString();
		

	}

	public String getExecutionType() {
		return executionType;
	}

	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
}
