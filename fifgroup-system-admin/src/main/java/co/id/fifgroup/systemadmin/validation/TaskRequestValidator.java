package co.id.fifgroup.systemadmin.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.domain.TaskRequest;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;

public class TaskRequestValidator implements Validator<TaskRequest>{
	
	public static String TASK_RUNNER_NAME_NOT_EMPTY = "task.runner.name.not.empty";
	public static String TASK_PARAMETER_NOT_EMPTY = "task.parameter.not.empty";
	public static String TASK_SCHEDULE_NOT_EMPTY = "task.schedule.not.empty";
	
	private List<TaskParameterDTO> taskParameterDto;
	
	@Override
	public void validate(TaskRequest subject) throws ValidationException {
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		Map<String, String> violations = new HashMap<>();
		if (subject.getTaskRunnerId() == null)
			violations.put(TASK_RUNNER_NAME_NOT_EMPTY, "Task Runner must not be empty.");
		/*if(validateTaskParameter())
			violations.put(TASK_PARAMETER_NOT_EMPTY, "Task parameters must not be empty.");*/
		if(subject.getScheduleType() == null)
			violations.put(TASK_SCHEDULE_NOT_EMPTY, "Time schedule must not be empty.");		
		
		if (violations.size() > 0) throw new ValidationException(violations); 
	}
	
	public List<TaskParameterDTO> getTaskParameterDto() {
		return taskParameterDto;
	}

	public void setTaskParameters(List<TaskParameterDTO> taskParameterDto) {
		this.taskParameterDto = taskParameterDto;
	}

}
