package co.id.fifgroup.eligibility.validation;

import static co.id.fifgroup.core.validation.ValidationRule.isWord;

import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import com.google.common.base.Strings;

import co.id.fifgroup.eligibility.constant.FieldType;
import co.id.fifgroup.eligibility.dto.FactTypeFieldDTO;

@Component
public class FactTypeFieldValidator implements Validator<FactTypeFieldDTO> {

	public static final String FIELD_NAME = "fieldName";
	public static final String FIELD_TYPE = "fieldType";
	public static final String LOOKUP = "fieldLookup";
	
	@Override
	public void validate(FactTypeFieldDTO subject) throws ValidationException {
		ValidationException vex = new ValidationException();
		
		if (Strings.isNullOrEmpty(subject.getName()))
			vex.put(FIELD_NAME, Labels.getLabel("common.pleaseFill", new Object[] {"Field Name"}));
		
		if (!isWord(subject.getName()))
			vex.put(FIELD_NAME, Labels.getLabel("common.mustBeWord", new Object[] {"Field Name"}));
		
		if (null == subject.getFieldType())
			vex.put(FIELD_TYPE, Labels.getLabel("common.pleaseFill", new Object[] {"Field Type"}));
		else if (FieldType.LOOKUP == subject.getFieldType()
			&& Strings.isNullOrEmpty(subject.getLookupName()))
			vex.put(LOOKUP, Labels.getLabel("elr.err.lookupNameMandatory"));
		
		if (vex.hasErrors()) throw vex;
	}

}
