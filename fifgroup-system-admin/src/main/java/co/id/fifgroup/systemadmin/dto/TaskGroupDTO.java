package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.TaskGroup;

public class TaskGroupDTO extends TaskGroup implements Traversable{

	private static final long serialVersionUID = 1L;
	private String userName;
	private List<TaskGroupDetailDTO> taskGroupDetailDto;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<TaskGroupDetailDTO> getTaskGroupDetailDto() {
		return taskGroupDetailDto;
	}
	public void setTaskGroupDetailDto(List<TaskGroupDetailDTO> taskGroupDetailDto) {
		this.taskGroupDetailDto = taskGroupDetailDto;
	}
	
	@Override
	public Long getId() {
		return super.getId();
	}
}
