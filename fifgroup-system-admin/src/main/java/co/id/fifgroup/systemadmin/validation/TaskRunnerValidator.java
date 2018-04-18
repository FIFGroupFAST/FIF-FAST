package co.id.fifgroup.systemadmin.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.constant.TaskExecutionType;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;

public class TaskRunnerValidator implements Validator<TaskRunnerDTO> {

	public static String NAME_NOT_EMPTY = "name.notEmpty";
	public static String DESCRIPTION_NOT_EMPTY = "description.notEmpty";
	public static String DATE_FROM_NOT_EMPTY = "dateFrom.notEmpty";
	public static String CYCLIC_ERROR = "cyclicError";
	public static String TASK = "taskError";

	@Override
	public void validate(TaskRunnerDTO subject) throws ValidationException {

		if (null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		Map<String, String> violations = new HashMap<>();
		if (isNullOrEmpty(subject.getTaskRunnerName())) {
			violations.put(NAME_NOT_EMPTY,
					"Task Runner Name must not be empty.");
		}
		if (isNullOrEmpty(subject.getDescription())) {
			violations.put(DESCRIPTION_NOT_EMPTY,
					"Description must not be empty.");
		}
		if (subject.getDateFrom() == null) {
			violations.put(DATE_FROM_NOT_EMPTY, "Date From must not be empty.");
		}
		if (subject.getTaskRunnerDetailDto().isEmpty()) {
			violations.put(TASK, "Task must not be empty.1");
		} else if ((subject.getTaskRunnerDetailDto().get(0).getErrorTaskId() == null
				|| subject.getTaskRunnerDetailDto().get(0).getSccuessTaskId() == null || subject
				.getTaskRunnerDetailDto().get(0).getTaskId() == null)
				&& subject.getExecutionType().equalsIgnoreCase(
						TaskExecutionType.SEQUENTIAL.getValue())) {
			violations.put(TASK, "Task must not be empty.2");
		} else {
			
			for (int i = 0; i < subject.getTaskRunnerDetailDto().size(); i++) {
				for (int j = 0; j < subject.getTaskRunnerDetailDto().size(); j++) {
					if (i == j)
						break;
					else if (subject
							.getTaskRunnerDetailDto()
							.get(i)
							.getTaskId()
							.equals(subject.getTaskRunnerDetailDto().get(j)
									.getTaskId()))
						violations.put(TASK, Labels.getLabel(
								"sam.cannotBeDuplicated",
								new Object[] { Labels.getLabel("sam.task") }));
				}
			}
			if (isCrossCycleValidation(subject)
					|| isSuccessCycleValidation(subject)
					|| isErrorCycleValidation(subject)) {
				violations.put(CYCLIC_ERROR,
						"This set of Tasks will cause cyclic path.");
			}
		}

		if (violations.size() > 0)
			throw new ValidationException(violations);
	}

	private boolean isCrossCycleValidation(TaskRunnerDTO subject) {
		List<TaskRunnerDetailDTO> listOfTaskRunnerDetailDtoToCheck = subject
				.getTaskRunnerDetailDto();
		long[] idIndex = new long[listOfTaskRunnerDetailDtoToCheck.size()];
		int counter = 0;
		for (TaskRunnerDetailDTO taskRunnerDetailDto : listOfTaskRunnerDetailDtoToCheck) {
			if (taskRunnerDetailDto.getErrorTaskId() != null
					&& taskRunnerDetailDto.getErrorTaskId() != -1L) {
				idIndex[counter] = taskRunnerDetailDto.getTaskId();
				for (int i = counter; i >= 0; i--) {
					if (taskRunnerDetailDto.getErrorTaskId() == idIndex[i]) {
						System.out.println("cross cycle");
						return true;
					}
				}
				counter++;

			} else if (taskRunnerDetailDto.getSccuessTaskId() != null
					&& taskRunnerDetailDto.getSccuessTaskId() != -1L) {
				idIndex[counter] = taskRunnerDetailDto.getTaskId();
				for (int i = counter; i >= 0; i--) {
					if (taskRunnerDetailDto.getSccuessTaskId() == idIndex[i]) {
						System.out.println("cross cycle");
						return true;
					}
				}
				counter++;
			}
		}
		return false;
	}

	private boolean isErrorCycleValidation(TaskRunnerDTO subject) {
		List<TaskRunnerDetailDTO> listOfTaskRunnerDetailDtoToCheck = subject
				.getTaskRunnerDetailDto();
		long[] errorIdIndex = new long[listOfTaskRunnerDetailDtoToCheck.size()];
		int counter = 0;
		for (TaskRunnerDetailDTO taskRunnerDetailDto : listOfTaskRunnerDetailDtoToCheck) {
			if (taskRunnerDetailDto.getErrorTaskId() != null
					&& taskRunnerDetailDto.getErrorTaskId() != -1L) {
				errorIdIndex[counter] = taskRunnerDetailDto.getTaskId();
				for (int i = counter; i >= 0; i--) {
					if (taskRunnerDetailDto.getErrorTaskId() == errorIdIndex[i]) {
						System.out.println("cross cycle");
						return true;
					}
				}
				counter++;
			}
		}
		return false;
	}

	private boolean isSuccessCycleValidation(TaskRunnerDTO subject) {
		List<TaskRunnerDetailDTO> listOfTaskRunnerDetailDtoToCheck = subject
				.getTaskRunnerDetailDto();
		long[] successIdIndex = new long[listOfTaskRunnerDetailDtoToCheck
				.size()];
		int counter = 0;
		for (TaskRunnerDetailDTO taskRunnerDetailDto : listOfTaskRunnerDetailDtoToCheck) {
			if (taskRunnerDetailDto.getSccuessTaskId() != null
					&& taskRunnerDetailDto.getSccuessTaskId() != -1L) {
				successIdIndex[counter] = taskRunnerDetailDto.getTaskId();
				for (int i = counter; i >= 0; i--) {
					if (taskRunnerDetailDto.getSccuessTaskId() == successIdIndex[i]) {
						System.out.println("cross cycle");
						return true;
					}
				}
				counter++;
			}
		}
		return false;
	}
}
