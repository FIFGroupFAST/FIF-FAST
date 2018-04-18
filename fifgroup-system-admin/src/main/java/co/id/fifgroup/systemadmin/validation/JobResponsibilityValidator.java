package co.id.fifgroup.systemadmin.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.dto.JobResponsibilityDTO;

@Component
public class JobResponsibilityValidator implements Validator<JobResponsibilityDTO> {
	
	public static String JOB_NAME_NOT_EMPTY = "jobName.notEmpty";
	
	@Override
	public void validate(JobResponsibilityDTO subject) throws ValidationException {
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		Map<String, String> violations = new HashMap<>();
		if (subject.getJobId() == null)
			violations.put(JOB_NAME_NOT_EMPTY, "Job Name must not be empty.");	
		
		if (violations.size() > 0) throw new ValidationException(violations); 
	}
}
