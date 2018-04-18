package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.TaskGroupDetail;

public class TaskGroupDetailDTO extends TaskGroupDetail implements Traversable{
	
	private static final long serialVersionUID = 1L;
	private String taskRunnerName;
	
	public String getTaskRunnerName() {
		return taskRunnerName;
	}
	public void setTaskRunnerName(String taskRunnerName) {
		this.taskRunnerName = taskRunnerName;
	}
	
	@Override
	public Long getId() {
		return super.getId();
	}
}
