package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.systemadmin.domain.TaskRunner;

public class TaskRunnerDTO extends TaskRunner{
	
	private static final long serialVersionUID = 8153696528893553702L;
	
	private List<TaskRunnerDetailDTO> taskRunnerDetailDto;
	private int taskCount;
	private String userName;
	
	public List<TaskRunnerDetailDTO> getTaskRunnerDetailDto() {
		return taskRunnerDetailDto;
	}
	public void setTaskRunnerDetailDto(List<TaskRunnerDetailDTO> taskRunnerDetailDto) {
		this.taskRunnerDetailDto = taskRunnerDetailDto;
	}
	public int getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
