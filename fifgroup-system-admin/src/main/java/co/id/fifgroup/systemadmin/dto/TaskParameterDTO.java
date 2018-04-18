package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.TaskParameter;
import co.id.fifgroup.systemadmin.domain.TaskParameterValue;

public class TaskParameterDTO extends TaskParameter implements Traversable{

	private static final long serialVersionUID = -2673196067103965828L;
	
	private TaskParameterValue taskParameterValue;
	
	private String lookupCode;
	
	public TaskParameterValue getTaskParameterValue() {
		return taskParameterValue;
	}
	public void setTaskParameterValue(TaskParameterValue taskParameterValue) {
		this.taskParameterValue = taskParameterValue;
	}
	public String getLookupCode() {
		return lookupCode;
	}
	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}	
	
	@Override
	public Long getId() {
		return super.getId();
	}
}
