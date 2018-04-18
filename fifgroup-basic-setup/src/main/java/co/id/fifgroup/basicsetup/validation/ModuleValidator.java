package co.id.fifgroup.basicsetup.validation;

import static co.id.fifgroup.core.validation.ValidationRule.*;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.ModuleExample;
import co.id.fifgroup.basicsetup.service.ModuleService;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

@Component
public class ModuleValidator implements Validator<Module>{
	public static final String MODULE_CODE = "Module.moduleCode";
	public static final String MODULE_NAME = "Module.moduleName";
	public static final String MODULE_DATE_FROM = "Module.dateFrom";
	
	
	@Autowired
	private ModuleService moduleService;

	@Override
	public void validate(Module subject) throws ValidationException {
		Map<String, String> errors = new HashMap<String, String>();
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		
		if(subject.getModuleId() == null) {
			insertValidation(subject, errors);
		} else{
			updateValidation(subject, errors);
		}
		
		if (errors.size() > 0) throw new ValidationException(errors); 
		
	}

	private void updateValidation(Module subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(MODULE_NAME)) {
			if(moduleService.isExistModuleForUpdate(subject)) {
				errors.put(MODULE_NAME, Labels.getLabel("bse.err.moduleNameAlreadyExist"));				
			}			
		}
		
	}

	private void formValidation(Module subject, Map<String, String> errors) {
		moduleCodeValidation(subject, errors);
		moduleNameValidation(subject, errors);
		moduleDateFromValidation(subject, errors);
	}

	private void moduleDateFromValidation(Module subject,
			Map<String, String> errors) {
		if(subject.getDateFrom()==null) {
			errors.put(MODULE_DATE_FROM, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.dateFrom") }));
			return;
		}else if(subject.getDateFrom().after(subject.getDateTo())){
			errors.put(MODULE_DATE_FROM, Labels.getLabel("bse.err.moduleDateToMustBeGreaterThanOrEqualToDateFrom"));
			return;
		}
		
		
	}

	private void moduleNameValidation(Module subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getModuleName())) {
			errors.put(MODULE_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.moduleName") }));
			return;
		}
		
	}

	private void moduleCodeValidation(Module subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getModuleCode())) {
			errors.put(MODULE_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.moduleCode") }));
			return;
		}
		if(!isWord(subject.getModuleCode())) {
			errors.put(MODULE_CODE, Labels.getLabel("common.mustBeAlphaNumericAndUnderscore", new Object[] { Labels.getLabel("bse.moduleCode") }));
			return;
		}
		
	}

	private void insertValidation(Module subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(MODULE_CODE)) {
			ModuleExample ModuleCodeExistExample = new ModuleExample();
			ModuleCodeExistExample.createCriteria().andModuleCodeEqualTo(subject.getModuleCode());
			if(moduleService.countByExample(ModuleCodeExistExample) > 0) {
				errors.put(MODULE_CODE, Labels.getLabel("bse.err.moduleCodeAlreadyExist"));				
			}
		}
		if(!errors.containsKey(MODULE_NAME)) {
			ModuleExample moduleNameExistExample = new ModuleExample();
			moduleNameExistExample.createCriteria().andModuleNameEqualTo(subject.getModuleName());
			if(moduleService.countByExample(moduleNameExistExample) > 0) {
				errors.put(MODULE_NAME, Labels.getLabel("bse.err.moduleNameAlreadyExist"));
			}
		}
		
	}

	
}
