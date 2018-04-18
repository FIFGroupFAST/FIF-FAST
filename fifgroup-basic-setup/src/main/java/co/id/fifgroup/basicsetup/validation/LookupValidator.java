package co.id.fifgroup.basicsetup.validation;

import static co.id.fifgroup.core.validation.ValidationRule.isWord;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.constant.LookupParentType;
import co.id.fifgroup.basicsetup.constant.LookupType;
import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.domain.LookupHeaderExample;
import co.id.fifgroup.basicsetup.dto.LookupDTO;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

@Component
public class LookupValidator implements Validator<LookupDTO> {

	public static final String LOOKUP_NAME = "lookup.lookupName";
	public static final String DESCRIPTION = "lookup.description";
	public static final String TYPE = "lookup.type";
	public static final String PARENT = "lookup.parent";
	public static final String DATA_TYPE = "lookup.dataType";
	public static final String LOOKUP_DEPENDENT = "lookup.dependent";
	public static final String LOOKUP_DYNAMIC = "lookup.dynamic";
	
	@Autowired
	private LookupService lookupServiceImpl;
	
	@Override
	public void validate(LookupDTO subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if(subject == null) {
			throw new IllegalArgumentException("subject must not be null.");
		}
		if(isNullOrEmpty(subject.getLookupType())) {
			lookupFormValidation(subject, violations);
		} else if(subject.getLookupType().equals(LookupType.DEPENDENT.toString())) {
			lookupDependentValidation(subject, violations);
		} else if(subject.getLookupType().equals(LookupType.DYNAMIC.toString())) {
			lookupDynamicValidation(subject, violations);
		}
		
		if(violations.size() > 0) throw new ValidationException(violations);
	}

	private void lookupNameValidation(LookupDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getName())) {
			errors.put(LOOKUP_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.lookupName") }));
			return;
		}
		if(!isWord(subject.getName())) {
			errors.put(LOOKUP_NAME, Labels.getLabel("common.mustBeAlphaNumericAndUnderscore", new Object[] { Labels.getLabel("bse.lookupName") }));
		}
		if(subject.getLookupId() == null) {
			LookupHeaderExample lookupHeaderExample = new LookupHeaderExample();
			LookupHeaderExample.Criteria criteriaLookupHeaderExample = lookupHeaderExample.createCriteria();
			criteriaLookupHeaderExample.andNameEqualTo(subject.getName()).andGroupIdEqualTo(subject.getGroupId());
			List<LookupHeader> resultLookupHeader = lookupServiceImpl.getLookupHeaderByExample(lookupHeaderExample);
			if(resultLookupHeader != null && resultLookupHeader.size() > 0) {
				errors.put(LOOKUP_NAME, Labels.getLabel("bse.err.lookupNameAlreadyExist"));
				return;
			}
		}
	}
	
	private void descriptionValidation(LookupDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getDescription())) {
			errors.put(DESCRIPTION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.description") }));
			return;
		}
	}
	
	private void typeValidation(LookupDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getLookupType())) {
			errors.put(TYPE, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("common.type") }));
			return;
		}
	}
	
	private void parentLookupValidation(LookupDTO subject, Map<String, String> errors) {
		if(subject.getParentId() != null && subject.getParentId().equals(LookupParentType.SELECT.getValue())) {
			errors.put(PARENT, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.parent") }));
			return;
		}
	}
	
	private void dataTypeValidation(LookupDTO subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getDataType())) {
			errors.put(DATA_TYPE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.dataType") }));
			return;
		}
	}
	
	private void lookupFormValidation(LookupDTO subject, Map<String, String> errors) {
		lookupNameValidation(subject, errors);
		descriptionValidation(subject, errors);
		typeValidation(subject, errors);
	}
	
	private void lookupDependentFormValidation(LookupDTO subject, Map<String, String> errors) {
		lookupFormValidation(subject, errors);
		parentLookupValidation(subject, errors);
		dataTypeValidation(subject, errors);
	}
	
	private void lookupDependentValidation(LookupDTO subject, Map<String, String> errors) {
		lookupDependentFormValidation(subject, errors);
	}
	
	private void lookupDynamicValidation(LookupDTO subject, Map<String, String> errors) {
		lookupFormValidation(subject, errors);
	}
	
}
