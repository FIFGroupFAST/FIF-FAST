package co.id.fifgroup.systemadmin.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.domain.ExecutableFile;

@Component
public class TaskFileUploadValidator implements Validator<ExecutableFile> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6230563443042272732L;

	public static final String EXECUTABLE_FILE_NAME = "executableFile.executableFileName";
	public static final String EXECUTABLE_FILE_EXIST = "executableFile.exist";
	public static final String MODULE_ID = "executableFile.moduleId";
	public static final String TASK_TYPE = "executableFile.taskType";

	@Override
	public void validate(ExecutableFile subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if (null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		if (subject.getId() == null) {
			uploadValidation(subject, violations);
		}
		if (violations.size() > 0) throw new ValidationException(violations);
	}

	private void uploadValidation(ExecutableFile subject,
			Map<String, String> errors) {
		formValidation(subject, errors);

	}

	private void formValidation(ExecutableFile subject,
			Map<String, String> errors) {
		moduleValidation(subject, errors);
		fileValidations(subject, errors);
		taskTypeValidation(subject, errors);

	}

	private void fileValidations(ExecutableFile subject,
			Map<String, String> errors) {

		if (isNullOrEmpty(subject.getExecutableFileName())) {
			errors.put(EXECUTABLE_FILE_NAME,
					Labels.getLabel("sam.err.executableFileCannotBeEmpty"));
			return;
		} 

	}

	private void taskTypeValidation(ExecutableFile subject,
			Map<String, String> errors) {
		String fileExtention;
		if(subject.getExecutableFileName() != null){
			if (subject.getExecutableFileName().endsWith(".jar")) {
				fileExtention = "batch";
			} else {
				fileExtention = "report";
			}
			if (!subject.getTaskType().equalsIgnoreCase(fileExtention)) {
				errors.put(TASK_TYPE,
						Labels.getLabel("sam.err.taskTypeDidNotMatchFileType"));
				return;
			}
		}
	}

	private void moduleValidation(ExecutableFile subject,
			Map<String, String> errors) {
		if (subject.getModuleId() == null) {
			errors.put(MODULE_ID,
					Labels.getLabel("sam.err.moduleMustBeSelected"));
			return;
		}

	}

}
