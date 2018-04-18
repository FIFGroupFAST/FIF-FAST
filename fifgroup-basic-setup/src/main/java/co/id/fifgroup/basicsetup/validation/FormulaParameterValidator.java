package co.id.fifgroup.basicsetup.validation;

import static co.id.fifgroup.core.validation.ValidationRule.isWord;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;

//import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.common.DefaultParameterFormula;
import co.id.fifgroup.basicsetup.common.GroovyEngine;
import co.id.fifgroup.basicsetup.constant.FormulaParameterReturnType;
import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.domain.FormulaParameterExample;
import co.id.fifgroup.basicsetup.service.FormulaParameterService;
import co.id.fifgroup.basicsetup.service.ServiceModel;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

@Component
public class FormulaParameterValidator implements Validator<FormulaParameter> {

	public static final String PARAMETER_NAME = "parameterFormula.parameterName";
	public static final String RETURN_TYPE = "parameterFormula.returnType";
	public static final String PARAMETER_FUNCTION = "parameterFormula.parameterFunction";
	public static final String GROOVY_ERROR = "parameterFormula.groovyError";
	
	@Autowired
	private FormulaParameterService formulaParameterServiceImpl;
	@Autowired
	private ServiceModel serviceGroovyImpl;
	
	@Override
	public void validate(FormulaParameter subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		if(subject.getFormulaParameterId() == null) {
			insertValidation(subject, violations);
		} else {
			updateValidation(subject, violations);
		}
		if (violations.size() > 0) throw new ValidationException(violations);
	}

	private void parameterNameValidation(FormulaParameter subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getParameterName())) {
			errors.put(PARAMETER_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.parameterName") }));
			return;
		}
		if(!isWord(subject.getParameterName())) {
			errors.put(PARAMETER_NAME, Labels.getLabel("common.mustBeAlphaNumericAndUnderscore", new Object[] { Labels.getLabel("bse.parameterName") }));
			return;
		}
	}
	
	private void returnTypeValidation(FormulaParameter subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getReturnType())) {
			errors.put(RETURN_TYPE, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.formulaReturnType") }));
			return;
		}
	}
	
	private void parameterFunctionValidation(FormulaParameter subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getParameterFunction())) {
			errors.put(PARAMETER_FUNCTION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.parameterFunction") }));
			return;
		}
		if(!errors.containsKey(RETURN_TYPE)) {
			try {
				GroovyEngine groovyEngine = new GroovyEngine();
				DefaultParameterFormula defaultParameterFormula = new DefaultParameterFormula();
				Object returnParameterFunction = groovyEngine.executeGroovyScriptFormulaParameter(subject.getParameterFunction(), defaultParameterFormula, serviceGroovyImpl, subject.getParameterName());
				if(returnParameterFunction != null) {
					if(subject.getReturnType().equals(FormulaParameterReturnType.LONG.toString())) {
						if(!(returnParameterFunction instanceof Long)) {
							errors.put(PARAMETER_FUNCTION, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					} else if(subject.getReturnType().equals(FormulaParameterReturnType.DOUBLE.toString())) {
						if(!(returnParameterFunction instanceof Double)) {
							errors.put(PARAMETER_FUNCTION, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					} else if(subject.getReturnType().equals(FormulaParameterReturnType.BOOLEAN.toString())) {
						if(!(returnParameterFunction instanceof Boolean)) {
							errors.put(PARAMETER_FUNCTION, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					} else if(subject.getReturnType().equals(FormulaParameterReturnType.STRING.toString())) {
						if(!(returnParameterFunction instanceof String)) {
							errors.put(PARAMETER_FUNCTION, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					} else if(subject.getReturnType().equals(FormulaParameterReturnType.OBJECT.toString())) {
						if(!(returnParameterFunction instanceof Object)) {
							errors.put(PARAMETER_FUNCTION, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					}
				}
			} catch (MultipleCompilationErrorsException e) {
				errors.put(GROOVY_ERROR, e.getMessage());
				return;
			} catch (Exception e) {
				errors.put(GROOVY_ERROR, e.getMessage());
				return;
			}
		}
	}
	
	private void formValidation(FormulaParameter subject, Map<String, String> errors) {
		parameterNameValidation(subject, errors);
		returnTypeValidation(subject, errors);
		parameterFunctionValidation(subject, errors);
	}
	
	private void insertValidation(FormulaParameter subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(PARAMETER_NAME)) {
			FormulaParameterExample formulaParameterExample = new FormulaParameterExample();
			formulaParameterExample.createCriteria().andParameterNameEqualTo(subject.getParameterName());
			if(formulaParameterServiceImpl.countByExample(formulaParameterExample) > 0) {
				errors.put(PARAMETER_NAME, Labels.getLabel("bse.err.formulaParameterNameAlreadyExist"));
			}
		}
	}
	
	private void updateValidation(FormulaParameter subject, Map<String, String> errors) {
		formValidation(subject, errors);
	}
}
