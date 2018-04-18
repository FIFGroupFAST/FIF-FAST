package co.id.fifgroup.basicsetup.validation;

import static co.id.fifgroup.core.validation.ValidationRule.isNumeric;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.constant.SequenceGeneratorType;
import co.id.fifgroup.basicsetup.domain.SequenceGenerator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;

@Component
public class SequenceGeneratorValidator implements Validator<SequenceGenerator>{
	
	public static final String SEQUENCE_GENERATOR_COMPANY_SCOPE = "sequenceGenerator.companyScope";
	public static final String SEQUENCE_GENERATOR_TRX_CODE = "sequenceGenerator.trxCode";
	public static final String SEQUENCE_GENERATOR_LENGTH = "sequenceGenerator.length";
	public static final String SEQUENCE_GENERATOR_START_FROM = "sequenceGenerator.startFrom";
	public static final String SEQUENCE_GENERATOR_INCREMENT = "sequenceGenerator.increment";
	public static final String SEQUENCE_GENERATOR_PADDING_CHAR = "sequenceGenerator.paddingChar";
	public static final String SEQUENCE_GENERATOR_PREFIX = "sequenceGenerator.prefix";
	
	@Override
	public void validate(SequenceGenerator subject) throws ValidationException {

		Map<String, String> violations = new HashMap<String, String>();
		if (null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		if (subject.getSequenceGeneratorId() == null) {
			insertValidation(subject, violations);
		}
		if (violations.size() > 0) throw new ValidationException(violations);
		
	}

	

	private void insertValidation(SequenceGenerator subject,
			Map<String, String> errors) {
		formValidation(subject, errors);
	}



	private void formValidation(SequenceGenerator subject,
			Map<String, String> errors) {
		sequenceGeneratorTrxCodeValidation(subject, errors);
		sequenceGeneratorScopeValidation(subject, errors);
		sequenceGeneratorLengthValidation(subject, errors);
		startFromValidation(subject, errors);
		incrementValidation(subject, errors);
		paddingCharValidation(subject, errors);
		prefixValidation(subject, errors);
	}



	private void sequenceGeneratorLengthValidation(SequenceGenerator subject,
			Map<String, String> errors) {
		if(subject.getLength()==null) {
			errors.put(SEQUENCE_GENERATOR_LENGTH, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.length") }));
			return;
		}
		if(!isNumeric(subject.getLength().toString())){
			errors.put(SEQUENCE_GENERATOR_LENGTH, Labels.getLabel("common.mustBeNumeric", new Object[] { Labels.getLabel("bse.length") }));
			return;
		}
		if(subject.getLength().intValue() > 20) {
			errors.put(SEQUENCE_GENERATOR_LENGTH, Labels.getLabel("bse.err.maximumLengthSequenceGenerator"));
			return;
		}
		if(subject.getLength().intValue() <= 0) {
			errors.put(SEQUENCE_GENERATOR_LENGTH, Labels.getLabel("bse.err.sequenceGeneratorLengthMustBeGreaterThanZero"));
			return;
		}
		
		String strMaxValue = "";
		for(int i = 0; i < subject.getLength().intValue(); i++) {
			strMaxValue += SequenceGeneratorType.MAXVALUE_CHAR.getDesc();
		}
		
		BigDecimal maxValue = new BigDecimal(strMaxValue);
		BigDecimal startFrom = new BigDecimal(String.valueOf(subject.getSequenceStartFrom()));
		BigDecimal increment = new BigDecimal(String.valueOf(subject.getSeqIncrement()));
		if(startFrom.add(increment).compareTo(maxValue) == 1 || startFrom.add(increment).compareTo(maxValue) == 0) {
			errors.put(SEQUENCE_GENERATOR_LENGTH, Labels.getLabel("bse.err.sequenceGeneratorLengthNotValidWithStartFromAndIncrement"));
			return;
		}
	}



	private void sequenceGeneratorTrxCodeValidation(SequenceGenerator subject,
			Map<String, String> errors) {
		if(isNullOrEmpty(subject.getTrxCode())) {
			errors.put(SEQUENCE_GENERATOR_TRX_CODE, Labels.getLabel("common.mustBeFill",  new Object[] { Labels.getLabel("bse.trxType") }));
			return;
		}
		
	}

	private void sequenceGeneratorScopeValidation(SequenceGenerator subject,
			Map<String, String> errors) {
		if(subject.getCompanyScope()==null) {
			errors.put(SEQUENCE_GENERATOR_COMPANY_SCOPE, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.scope") }));
			return;
		}
		
	}
	
	private void startFromValidation(SequenceGenerator subject, Map<String, String> errors) {
		if(subject.getSequenceStartFrom().intValue() <= 0) {
			errors.put(SEQUENCE_GENERATOR_START_FROM, Labels.getLabel("bse.err.sequenceGeneratorStartFromMustBeGreaterThanZero"));
			return;
		}
	}
	
	private void incrementValidation(SequenceGenerator subject, Map<String, String> errors) {
		if(subject.getSeqIncrement().intValue() <= 0) {
			errors.put(SEQUENCE_GENERATOR_INCREMENT, Labels.getLabel("bse.err.sequenceGeneratorIncrementMustBeGreaterThanZero"));
			return;
		}
	}

	private void paddingCharValidation(SequenceGenerator subject, Map<String, String> errors) {
		if(subject.getPaddingChar() != null && !subject.getPaddingChar().equals("") && !ValidationRule.isAlphaNumeric(subject.getPaddingChar())) {
			errors.put(SEQUENCE_GENERATOR_PADDING_CHAR, Labels.getLabel("common.mustBeAlphaNumeric", new Object[] { Labels.getLabel("bse.paddingChar") }));
			return;
		}
	}
	
	private void prefixValidation(SequenceGenerator subject, Map<String, String> errors) {
		if(subject.getPrefix() != null && !subject.getPrefix().equals("") && !ValidationRule.isWord(subject.getPrefix())) {
			errors.put(SEQUENCE_GENERATOR_PREFIX, Labels.getLabel("common.mustBeAlphaNumericAndUnderscore", new Object[] { Labels.getLabel("bse.prefix") }));
			return;
		}
	}
	
}
