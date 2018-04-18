package co.id.fifgroup.eligibility.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import com.google.common.base.Strings;

import co.id.fifgroup.eligibility.constant.ColumnType;
import co.id.fifgroup.eligibility.constant.ValueType;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;

@Component
public class DecisionTableColumnValidator implements Validator<DecisionTableColumnDTO> {

	public static final String COLUMN_NAME = "columnName";
	public static final String COLUMN_FACT_TYPE = "columnFactType";
	public static final String COLUMN_FIELD = "columnField";
	public static final String OPERATOR = "operator";
	public static final String VALUE_TYPE = "valueType";
	public static final String VALUE_LIST = "valueList";
	
	private static final Pattern COLUMN_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9 ]+$");
	
	@Override
	public void validate(DecisionTableColumnDTO subject)
			throws ValidationException {
		ValidationException vex = new ValidationException();
		if (Strings.isNullOrEmpty(subject.getName()))
			vex.put(COLUMN_NAME, Labels.getLabel("common.pleaseFill", new Object[] {"Column Name"}));
		if (!ValidationRule.matches(subject.getName(), COLUMN_NAME_PATTERN))
			vex.put(COLUMN_NAME, Labels.getLabel("common.mustBeAlphaNumeric", new Object[] {"Column Name"}));
		if (null == subject.getFactType())
			vex.put(COLUMN_FACT_TYPE, Labels.getLabel("common.pleaseFill", new Object[] {"Fact Type"}));
		if (null == subject.getField())
			vex.put(COLUMN_FIELD, Labels.getLabel("common.pleaseFill", new Object[] {"Field"}));
		if (null == subject.getOperator() && subject.getColumnType() == ColumnType.CONDITION)
			vex.put(OPERATOR, Labels.getLabel("common.pleaseFill", new Object[] {"Operator"}));
		if (null ==	subject.getValueType())
			vex.put(VALUE_TYPE, Labels.getLabel("common.pleaseFill", new Object[] {"Value Type"}));
		else if (ValueType.LIST == subject.getValueType() 
				&& Strings.isNullOrEmpty(subject.getValueList()))
			vex.put(VALUE_LIST, Labels.getLabel("common.pleaseFill", new Object[] {"Value List"}));
		
		
		if (vex.hasErrors()) 
			throw vex;
	}

}
