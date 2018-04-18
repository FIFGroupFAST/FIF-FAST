package co.id.fifgroup.systemadmin.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.dto.TaskDTO;

@Component
public class TaskValidator implements Validator<TaskDTO>{
	
	public static String TASK_NAME_NOT_EMPTY = "taskName.notEmpty";
	public static String DESCRIPTION_NOT_EMPTY = "description.notEmpty";
	public static String EXECUTABLE_FILE_NAME_NOT_EMPTY = "executableFileName.notEmpty";
	public static String CLASS_NAME_NOT_EMPTY = "className.notEmpty";
	
	@Override
	public void validate(TaskDTO subject) throws ValidationException {
		
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		Map<String, String> violations = new HashMap<>();
		if (isNullOrEmpty(subject.getTaskName()))
			violations.put(TASK_NAME_NOT_EMPTY, Labels.getLabel("common.mustBeFill", new Object[]{"Task name"}));
		if (isNullOrEmpty(subject.getDescription()))
			violations.put(DESCRIPTION_NOT_EMPTY, Labels.getLabel("common.mustBeFill", new Object[]{"Description"}));			
		if(subject.getExecutableFileId() == null)
			violations.put(EXECUTABLE_FILE_NAME_NOT_EMPTY, Labels.getLabel("common.mustBeFill", new Object[]{Labels.getLabel("sam.executableFile")}));		
		
		if(TaskType.getKey(subject.getTaskType()) == 0) {
			if(isNullOrEmpty(subject.getClassName()))
				violations.put(CLASS_NAME_NOT_EMPTY, Labels.getLabel("common.mustBeFill", new Object[]{Labels.getLabel("sam.className")}));	
		}
		
		if (violations.size() > 0) throw new ValidationException(violations); 
	}

}
