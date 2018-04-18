package co.id.fifgroup.ssoa.validation;

import static com.google.common.base.Strings.isNullOrEmpty;
import static co.id.fifgroup.core.validation.ValidationRule.isWord;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.ssoa.domain.SystemProperty;
import co.id.fifgroup.ssoa.domain.SystemPropertyExample;
import co.id.fifgroup.ssoa.service.SystemPropertyService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;


@Component
public class SystemPropertyValidator implements Validator<SystemProperty>{
	public static final String SYSTEM_PROPERTY_CODE = "SystemProperty.systemPropertyCode";
	public static final String SYSTEM_PROPERTY_NAME = "SystemProperty.systemPropertyName";
	public static final String SYSTEM_PROPERTY_VALUE = "SystemProperty.systemPropertyValue";
	
	@Autowired
	private SystemPropertyService systemPropertyService;

	@Override
	public void validate(SystemProperty subject) throws ValidationException {
		Map<String, String> errors = new HashMap<String, String>();
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		
		if(subject.getSystemPropertyCode() == null) {
			insertValidation(subject, errors);
		}else{
			updateValidation(subject, errors);
		}
		
		if (errors.size() > 0) throw new ValidationException(errors); 
	}

	private void updateValidation(SystemProperty subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(SYSTEM_PROPERTY_NAME)) {
			if(systemPropertyService.isExistSystemPropertyForUpdate(subject)) {
				errors.put(SYSTEM_PROPERTY_NAME, Labels.getLabel("soc.err.systemPropertyNameAlreadyExist"));				
			}			
		}
	}

	private void formValidation(SystemProperty subject, Map<String, String> errors) {
		systemPropertyCodeValidation(subject, errors);
		systemPropertyNameValidation(subject, errors);
		systemPropertyValueValidation(subject, errors);
	}

	private void systemPropertyCodeValidation(SystemProperty subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getSystemPropertyCode())) {
			errors.put(SYSTEM_PROPERTY_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("soc.systemPropertyCode") }));
			return;
		}
		if(!isWord(subject.getSystemPropertyCode())) {
			errors.put(SYSTEM_PROPERTY_CODE, Labels.getLabel("common.mustBeAlphaNumericAndUnderscore", new Object[] { Labels.getLabel("soc.systemPropertyCode")}));
			return;
		}
	}

	private void systemPropertyNameValidation(SystemProperty subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getSystemPropertyName())) {
			errors.put(SYSTEM_PROPERTY_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("soc.systemPropertyName")}));
			return;
		}
	}

	private void systemPropertyValueValidation(SystemProperty subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getSystemPropertyValue())) {
			errors.put(SYSTEM_PROPERTY_VALUE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("soc.systemPropertyValue")}));
			return;
		}
	}

	private void insertValidation(SystemProperty subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(SYSTEM_PROPERTY_CODE)) {
			SystemPropertyExample SystemPropertyCodeExistExample = new SystemPropertyExample();
			SystemPropertyCodeExistExample.createCriteria().andSystemPropertyCodeEqualTo(subject.getSystemPropertyCode());
			if(systemPropertyService.countByExample(SystemPropertyCodeExistExample) > 0) {
				errors.put(SYSTEM_PROPERTY_CODE, Labels.getLabel("soc.err.systemPropertyCodeAlreadyExist"));				
			}
		}
		if(!errors.containsKey(SYSTEM_PROPERTY_NAME)) {
			SystemPropertyExample systemPropertyNameExistExample = new SystemPropertyExample();
			systemPropertyNameExistExample.createCriteria().andSystemPropertyNameEqualTo(subject.getSystemPropertyName());
			if(systemPropertyService.countByExample(systemPropertyNameExistExample) > 0) {
				errors.put(SYSTEM_PROPERTY_NAME, Labels.getLabel("soc.err.systemPropertyNameAlreadyExist"));
			}
		}
	}
}