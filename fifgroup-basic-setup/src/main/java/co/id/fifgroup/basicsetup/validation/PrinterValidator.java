package co.id.fifgroup.basicsetup.validation;

import static co.id.fifgroup.core.validation.ValidationRule.*;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.domain.PrinterExample;
import co.id.fifgroup.basicsetup.service.PrinterService;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

@Component
public class PrinterValidator implements Validator<Printer>{
	
	public static final String PRINTER_CODE = "Printer.printerCode";
	public static final String PRINTER_NAME = "Printer.printerName";
	public static final String PRINTER_ARGUMENT = "Printer.printerArgument";
	
	@Autowired
	private PrinterService printerService;


	@Override
	public void validate(Printer subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		if(subject.getPrinterId() == null) {
			insertValidation(subject, violations);
		} else {
			updateValidation(subject, violations);
		}
		
		if (violations.size() > 0) throw new ValidationException(violations);
		
	}
	private void printerCodeValidation(Printer subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getPrinterCode())) {
			errors.put(PRINTER_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.code") }));
			return;
		}
		if(!isWord(subject.getPrinterCode())) {
			errors.put(PRINTER_CODE, Labels.getLabel("common.mustBeAlphaNumericAndUnderscore", new Object[] { Labels.getLabel("bse.code") }));
			return;
		}
	}
	
	private void printerNameValidation(Printer subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getPrinterName())) {
			errors.put(PRINTER_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.printerName") }));
			return;
		}
		if(!isAlphaNumeric(subject.getPrinterName())) {
			errors.put(PRINTER_NAME, Labels.getLabel("common.mustBeAlphaNumeric", new Object[] { Labels.getLabel("bse.printerName") }));
			return;
		}
	}
	
	private void printerArgumentValidation(Printer subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getArgument())) {
			errors.put(PRINTER_ARGUMENT, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.argument") }));
			return;
		}
		
	}
	
	private void insertValidation(Printer subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(PRINTER_CODE)) {
			PrinterExample printerCodeExistExample = new PrinterExample();
			printerCodeExistExample.createCriteria().andPrinterCodeEqualTo(subject.getPrinterCode());
			if(printerService.countByExample(printerCodeExistExample) > 0) {
				errors.put(PRINTER_CODE, Labels.getLabel("bse.err.printerCodeAlreadyExist"));				
			}
		}
		if(!errors.containsKey(PRINTER_NAME)) {
			PrinterExample printerNameExistExample = new PrinterExample();
			printerNameExistExample.createCriteria().andPrinterNameEqualTo(subject.getPrinterName());
			if(printerService.countByExample(printerNameExistExample) > 0) {
				errors.put(PRINTER_NAME, Labels.getLabel("bse.err.printerNameAlreadyExist"));
			}
		}
	}
	
	
	
	private void updateValidation(Printer subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(PRINTER_NAME)) {
			if(printerService.isExistPrinterForUpdate(subject)) {
				errors.put(PRINTER_NAME, Labels.getLabel("bse.err.printerNameAlreadyExist"));				
			}			
		}
	}
	
	private void formValidation(Printer subject, Map<String, String> errors) {
		printerCodeValidation(subject, errors);
		printerNameValidation(subject, errors);
		printerArgumentValidation(subject, errors);
	}
	
	

}
