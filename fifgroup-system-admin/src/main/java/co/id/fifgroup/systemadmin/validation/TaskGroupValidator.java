package co.id.fifgroup.systemadmin.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.dto.TaskGroupDTO;

@Component
public class TaskGroupValidator implements Validator<TaskGroupDTO>{
	
	public static String GROUP_NAME_NOT_EMPTY = "groupName.notEmpty";
	
	@Override
	public void validate(TaskGroupDTO subject) throws ValidationException {
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		Map<String, String> violations = new HashMap<>();
		if (isNullOrEmpty(subject.getTaskGroupName()))
			violations.put(GROUP_NAME_NOT_EMPTY, "Function Name must not be empty.");	
		
		if (violations.size() > 0) throw new ValidationException(violations); 
	}

}
