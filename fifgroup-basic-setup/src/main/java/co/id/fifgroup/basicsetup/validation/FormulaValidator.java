package co.id.fifgroup.basicsetup.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.common.DefaultParameterFormula;
import co.id.fifgroup.basicsetup.common.GroovyEngine;
import co.id.fifgroup.basicsetup.constant.FormulaReturnType;
import co.id.fifgroup.basicsetup.domain.FormulaExample;
import co.id.fifgroup.basicsetup.dto.FormulaDTO;
import co.id.fifgroup.basicsetup.service.FormulaService;
import co.id.fifgroup.basicsetup.service.ServiceModel;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;


@Component
public class FormulaValidator implements Validator<FormulaDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(FormulaValidator.class);
	
	public static final String FORMULA_NAME = "Formula.formulaName";
	public static final String RETURN_TYPE = "FormulaVersion.returnType";
	public static final String FORMULA = "FormulaVersion.formula";
	public static final String DATE_FROM = "FormulaVersion.dateFrom";
	public static final String DATE_TO = "FormulaVersion.dateTo";
	public static final String VERSION_NUMBER = "FormulaVersion.versionNumber";
	public static final String GROOVY_ERROR = "parameterFormula.groovyError";

	@Autowired
	private ServiceModel serviceGroovyImpl;
	@Autowired
	private FormulaService formulaServiceImpl;
	
	@Override
	public void validate(FormulaDTO subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if (null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		if (subject.getId() == null) {
			insertValidation(subject, violations);
		}else{
			updateValidations(subject, violations);
		}
		if (violations.size() > 0) throw new ValidationException(violations);
		
	}

	private void updateValidations(FormulaDTO subject,
			Map<String, String> errors) {
		formValidation(subject, errors);
	}

	private void insertValidation(FormulaDTO subject,
			Map<String, String> errors) {
		formValidation(subject, errors);
		
	}

	private void formValidation(FormulaDTO subject, Map<String, String> errors) {
		formulaNameValidation(subject, errors);
		returnTypeValidation(subject, errors);
		formulaValidation(subject, errors);
		dateFromValidation(subject, errors);
		dateToValidation(subject, errors);
		
	}

	private void formulaNameValidation(FormulaDTO subject,
			Map<String, String> errors) {
		if(isNullOrEmpty(subject.getFormulaName())){
			errors.put(FORMULA_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.formulaName") }));
			return;
		}
		if(!ValidationRule.isWord(subject.getFormulaName())) {
			errors.put(FORMULA_NAME, Labels.getLabel("common.mustBeAlphaNumericAndUnderscore", new Object[] { Labels.getLabel("bse.formulaName") }));
			return;
		}
		FormulaExample formulaExample = new FormulaExample();
		formulaExample.createCriteria().andFormulaNameEqualTo(subject.getFormulaName());
		if(subject.getId() == null && formulaServiceImpl.countByExample(formulaExample) > 0) {
			errors.put(FORMULA_NAME, Labels.getLabel("bse.err.formulaFormulaNameAlreadyExist"));
			return;
		}
	}

	private void returnTypeValidation(FormulaDTO subject,
			Map<String, String> errors) {
		if(isNullOrEmpty(subject.getReturnType())){
			errors.put(RETURN_TYPE, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.formulaReturnType") }));
			return;
		}
		
	}

	private void formulaValidation(FormulaDTO subject,
			Map<String, String> errors) {
		if(isNullOrEmpty(subject.getFormula())){
			errors.put(FORMULA, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.formula") }));
			return;
		}
		if(!errors.containsKey(RETURN_TYPE)) {
			try {
				GroovyEngine groovyEngine = new GroovyEngine();
				DefaultParameterFormula defaultParameterFormula = new DefaultParameterFormula();
				Object returnFormula = groovyEngine.executeGroovyScriptFormula(subject.getFormula(), defaultParameterFormula, subject.getListFormulaParameter(), serviceGroovyImpl);
				if(returnFormula != null) {
					if(subject.getReturnType().equals(FormulaReturnType.LONG.toString())) {
						if(!(returnFormula instanceof Long)) {
							errors.put(FORMULA, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					} else if(subject.getReturnType().equals(FormulaReturnType.DOUBLE.toString())) {
						if(!(returnFormula instanceof Double)) {
							errors.put(FORMULA, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					} else if(subject.getReturnType().equals(FormulaReturnType.BOOLEAN.toString())) {
						if(!(returnFormula instanceof Boolean)) {
							errors.put(FORMULA, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					} else if(subject.getReturnType().equals(FormulaReturnType.STRING.toString())) {
						if(!(returnFormula instanceof String)) {
							errors.put(FORMULA, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					} else if(subject.getReturnType().equals(FormulaReturnType.OBJECT.toString())) {
						if(!(returnFormula instanceof Object)) {
							errors.put(FORMULA, Labels.getLabel("bse.err.formulaParameterFunctionResultTypeNotMatchWithReturnType"));
							return;
						}
					}
				}
			} catch (MultipleCompilationErrorsException e) {
				logger.error(e.getMessage(), e);
				errors.put(GROOVY_ERROR, e.getMessage());
				return;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				errors.put(GROOVY_ERROR, e.getMessage());
				return;
			}
		}
		
	}

	private void dateFromValidation(FormulaDTO subject,
			Map<String, String> errors) {
		if(subject.getDateFrom()==null){
			errors.put(DATE_FROM, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.dateFrom") }));
			return;
		}else if(subject.getDateFrom().after(subject.getDateTo())){
			errors.put(DATE_FROM, Labels.getLabel("bse.err.formulaVersionDateToMustBeGreaterThanOrEqualToDateFrom"));
			return;
		}
		
	}

	private void dateToValidation(FormulaDTO subject, Map<String, String> errors) {
		if(subject.getDateTo().before(new Date())){
			errors.put(DATE_TO, Labels.getLabel("bse.err.formulaVersionDateToMustBeGreaterThanTodayDate"));
			return;
		}
		
	}

}
