package co.id.fifgroup.systemworkflow.validation;

import static com.google.common.base.Strings.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.systemworkflow.constants.LevelMethod;
import co.id.fifgroup.systemworkflow.dto.LevelApproverDTO;

import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.manager.AVMRuleEvaluationManager;

@Component
public class LevelApproverValidator implements Validator<LevelApproverDTO> {

	public static final String LEVEL_NAME_VALIDATE = "level name validate";
	public static final String MINIMUM_REQUIRED_VALIDATE = "minimum required approver in level validate";
	public static final String LIST_LEVEL_APPROVER_VALIDATE = "list level approver validate";
	public static final String LEVEL_APPROVER_VALIDATE = "level approver validate";
	public static final String RULE_VALIDATE = "rule validate";
	public static final String TABULAR_ENTRY_VALIDATE = "tabular entry validate";
	
	@Autowired
	AVMRuleEvaluationManager avmRuleEvaluationManager;
	
	@Override
	public void validate(LevelApproverDTO subject) throws ValidationException {
		
		Map<String, String> mapValidate = new HashMap<>();
		
		// avm validation
		if (isNullOrEmpty(subject.getLevel().getLevelName())) {
			mapValidate.put(LEVEL_NAME_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.levelName")}));
		} else if (!ValidationRule.isText(subject.getLevel().getLevelName())) {
			mapValidate.put(LEVEL_NAME_VALIDATE, Labels.getLabel("common.err.validationRuleText"));
		}
		
		if (subject.getLevel().getLevelMethod() == LevelMethod.AND_MINIMUM.getCode() && subject.getLevel().getNumberOfApprovals() < 1) {
			mapValidate.put(MINIMUM_REQUIRED_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.minimumRequired")}));
		}
		
		// list level approver validation
		if (subject.getApprovers() == null || 
				subject.getApprovers().size() < 1) {
			mapValidate.put(LIST_LEVEL_APPROVER_VALIDATE, Labels.getLabel("common.err.listRequired", new Object[] {Labels.getLabel("swf.listApprover")}));
		} else {
			if (hasSameApprover(subject.getApprovers())) {
				mapValidate.put(LEVEL_APPROVER_VALIDATE, Labels.getLabel("swf.err.haveSameApprover"));
			}
		}
		
		if (!subject.isValidateRule()) {
			mapValidate.put(RULE_VALIDATE, Labels.getLabel("swf.err.validateRuleError"));
		} else if (hasSameConditionInRule(subject.getLevel().getRule())) {
				mapValidate.put(RULE_VALIDATE, Labels.getLabel("swf.err.hasSameRule"));
		}
				
		if(!subject.isValidateTabular()) {
			mapValidate.put(TABULAR_ENTRY_VALIDATE, Labels.getLabel("swf.err.notValidTabularEntryApprover"));
		}
				
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}		
	}
	
	private boolean hasSameConditionInRule(String rule){
		String[] parseString = avmRuleEvaluationManager.parseExpression(rule);
		List<String> listStatement = new ArrayList<String>();
		
		StringBuilder sb = new StringBuilder();
		int j = 1;
		for (int i = 0; i < parseString.length; i++) {
			++j;
			if (parseString[i].equals("&") || parseString[i].equals("|")) {
				continue;
			}		
			sb.append(parseString[i]);
			if (i > 0 && j%4 == 0) {
				listStatement.add(sb.toString());
				sb = new StringBuilder();
			}
		}
		
		int k = 1;
		for (String string : listStatement) {
			for (int i = k; i < listStatement.size(); i++) {
				if (string.equals(listStatement.get(i))) {
					return true;
				}
			}
			k++;
		}
		return false;
	}
	
	private boolean hasSameApprover(List<AVMApprover> avmApprovers) {
		boolean valid = false;
		int j = 1;
		for (AVMApprover approver : avmApprovers) {
			for (int i = j; i < avmApprovers.size(); i++) {
				if (approver.getApproverId() != null && avmApprovers.get(i).getApproverId() != null) {
					if (approver.getApproverId().equals(avmApprovers.get(i).getApproverId()) &&
							approver.getBasedOn() == avmApprovers.get(i).getBasedOn()) {
						return true;
					}					
				}
			}
			j++;
		}
		return valid;
	}
	
}
