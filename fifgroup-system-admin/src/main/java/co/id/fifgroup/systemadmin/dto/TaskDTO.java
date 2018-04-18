package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.ExecutableFile;
import co.id.fifgroup.systemadmin.domain.Task;

public class TaskDTO extends Task implements Traversable{
	
	private static final long serialVersionUID = -3509313313193873336L;
	
	private ExecutableFile executableFile;
	private String userName;
	private List<TaskParameterDTO> taskParameters;
	
	public ExecutableFile getExecutableFile() {
		return executableFile;
	}
	public void setExecutableFile(ExecutableFile executableFile) {
		this.executableFile = executableFile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<TaskParameterDTO> getTaskParameters() {
		return taskParameters;
	}
	public void setTaskParameters(List<TaskParameterDTO> taskParameters) {
		this.taskParameters = taskParameters;
	}
	
	@Override
	public Long getId() {
		return super.getId();
	}
}
