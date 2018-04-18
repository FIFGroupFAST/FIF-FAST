package co.id.fifgroup.basicsetup.validation;

import static com.google.common.base.Strings.isNullOrEmpty;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.constant.GlobalSettingDataType;
import co.id.fifgroup.basicsetup.domain.GlobalSetting;
import co.id.fifgroup.basicsetup.domain.GlobalSettingExample;
import co.id.fifgroup.basicsetup.service.GlobalSettingService;
import co.id.fifgroup.core.validation.ValidationException;
import static co.id.fifgroup.core.validation.ValidationRule.*;
import co.id.fifgroup.core.validation.Validator;

@Component
public class GlobalSettingValidator implements Validator<GlobalSetting> {

	public static final String CODE = "globalSetting.code";
	public static final String DESCRIPTION = "globalSetting.description";
	public static final String DATA_TYPE = "globalSetting.dataType";
	public static final String VALUE = "globalSetting.value";
	
	@Autowired
	private GlobalSettingService globalSettingService;
	
	@Override
	public void validate(GlobalSetting subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if(subject == null) {
			throw new IllegalArgumentException("subject must not be null.");
		}
		if(subject.getGlobalSettingId() == null) {
			insertValidation(subject, violations);
		} else {
			updateValidation(subject, violations);
		}
		
		if(violations.size() > 0) throw new ValidationException(violations);
	}
	
	private void formValidation(GlobalSetting subject, Map<String, String> errors) {
		codeValidation(subject, errors);
		descriptionValidation(subject, errors);
		dataTypeValidation(subject, errors);
		valueValidation(subject, errors);
	}
	
	private void codeValidation(GlobalSetting subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getCode())) {
			errors.put(CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.code") }));
			return;
		}
		if(!isWord(subject.getCode())) {
			errors.put(CODE, Labels.getLabel("common.mustBeAlphaNumericAndUnderscore", new Object[] { Labels.getLabel("bse.code") }));
			return;
		}
	}
	
	private void descriptionValidation(GlobalSetting subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getDescription())) {
			errors.put(DESCRIPTION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.description") }));
			return;
		}
	}
	
	private void dataTypeValidation(GlobalSetting subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getDataType())) {
			errors.put(DATA_TYPE, Labels.getLabel("common.mustBeSelected",  new Object[] { Labels.getLabel("bse.dataType") }));
			return;
		}
	}
	
	private void valueValidation(GlobalSetting subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getValue())) {
			errors.put(VALUE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.value") }));
			return;
		}else{
			if(subject.getDataType().equals(GlobalSettingDataType.NUMERIC.toString()) && !isDigit(subject.getValue())){
				errors.put(VALUE, "Value must be digit");
			}
		}
	}
	
	private void insertValidation(GlobalSetting subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(CODE)) {
			GlobalSettingExample globalSettingExample = new GlobalSettingExample();
			globalSettingExample.createCriteria().andCodeEqualTo(subject.getCode());
			if(globalSettingService.getGlobalSettingByExample(globalSettingExample).size() > 0) {
				errors.put(CODE, Labels.getLabel("bse.err.globalSettingCodeAlreadyExist"));
				return;
			}
		}
	}
	
	private void updateValidation(GlobalSetting subject, Map<String, String> errors) {
		formValidation(subject, errors);
	}

}
