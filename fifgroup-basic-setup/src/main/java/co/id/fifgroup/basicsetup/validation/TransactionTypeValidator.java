package co.id.fifgroup.basicsetup.validation;

import static com.google.common.base.Strings.isNullOrEmpty;
import static co.id.fifgroup.core.validation.ValidationRule.isWord;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.domain.TransactionTypeExample;
import co.id.fifgroup.basicsetup.domain.TransactionType;
import co.id.fifgroup.basicsetup.service.TransactionTypeService;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

@Component
public class TransactionTypeValidator implements Validator<TransactionType> {

	public static final String TRANSACTION_TYPE_TRX_TYPE = "TransactionType.trxType";
	public static final String TRANSACTION_TYPE_TRX_CODE = "TransactionType.trxCode";
	public static final String TRANSACTION_TYPE_MODULE_ID = "TransactionType.moduleId";
	
	@Autowired
	private TransactionTypeService transactionTypeService;

	@Override
	public void validate(TransactionType subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if (null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		if (subject.getTrxTypeId() == null) {
			insertValidation(subject, violations);
		} else {
			updateValidation(subject, violations);
		}

		if (violations.size() > 0)throw new ValidationException(violations);
	}

	private void updateValidation(TransactionType subject,Map<String, String> errors) {
		formValidation(subject, errors);
			if(!errors.containsKey(TRANSACTION_TYPE_TRX_TYPE)) {
				if(transactionTypeService.isExistTransactionTypeForUpdate(subject)) {
					errors.put(TRANSACTION_TYPE_TRX_TYPE, Labels.getLabel("bse.err.transactionTypeTrxTypeAlreadyExist"));				
				}			
			}
			if(!errors.containsKey(TRANSACTION_TYPE_TRX_CODE)) {
				if(transactionTypeService.isExistTransactionTypeForUpdate(subject)) {
					errors.put(TRANSACTION_TYPE_TRX_CODE, Labels.getLabel("bse.err.transactionTypeTrxCodeAlreadyExist"));				
				}			
			}
	}

	private void insertValidation(TransactionType subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if (!errors.containsKey(TRANSACTION_TYPE_TRX_CODE)) {
			TransactionTypeExample transactionTypeTrxCodeExistExample = new TransactionTypeExample();
			transactionTypeTrxCodeExistExample.createCriteria().andTrxCodeEqualTo(subject.getTrxCode());
			if (transactionTypeService.countByExample(transactionTypeTrxCodeExistExample) > 0) {
				errors.put(TRANSACTION_TYPE_TRX_CODE, Labels.getLabel("bse.err.transactionTypeTrxCodeAlreadyExist"));
			}
		}
	}

	private void formValidation(TransactionType subject,Map<String, String> errors) {
		transactionTypeTrxCodeValidation(subject, errors);
		transactionTypeTrxTypeValidation(subject, errors);
		transactionTypeModulIdValidation(subject, errors);
	}

	private void transactionTypeModulIdValidation(TransactionType subject, Map<String, String> errors) {
		if(subject.getModuleId() == null) {
			errors.put(TRANSACTION_TYPE_MODULE_ID, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.module") }));
			return;
		}
	}

	private void transactionTypeTrxTypeValidation(TransactionType subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getTrxType())) {
			errors.put(TRANSACTION_TYPE_TRX_TYPE, Labels.getLabel("common.mustBeFill", new Object[] { "transaction type" }));
			return;
		}
	}

	private void transactionTypeTrxCodeValidation(TransactionType subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getTrxCode())) {
			errors.put(TRANSACTION_TYPE_TRX_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.transactionCode") }));
			return;
		}
		if(!isWord(subject.getTrxCode())){
			errors.put(TRANSACTION_TYPE_TRX_CODE, Labels.getLabel("common.mustBeAlphaNumericAndUnderscore", new Object[] { Labels.getLabel("bse.transactionCode") }) );
		}
	}

}
