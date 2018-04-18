package co.id.fifgroup.systemworkflow.validation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.systemworkflow.domain.VacationRule;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;

@Component
public class VacationRuleValidator implements Validator<VacationRule>{

	public static final String APPROVER_ID_VALIDATE = "approver id validate";
	public static final String SUBSTITUTE_APPROVER_ID_VALIDATE = "substitute approver id validate";
	public static final String EFFECTIVE_DATE_VALIDATE = "effective date validate";
	
	@Autowired
	VacationRuleService vacationRuleServiceImpl;
	
	@Override
	public void validate(VacationRule subject) throws ValidationException {
		Date today = DateUtil.truncate(new Date());
		
		Map<String, String> mapValidate = new HashMap<>();
		
		if (subject.getApproverId() == null) {
			mapValidate.put(APPROVER_ID_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.approver")}));
		}  else if (subject.getEffectiveDateFrom() != null && subject.getEffectiveDateTo() != null && subject.getVacationRuleId() == null) {
			if (vacationRuleServiceImpl.countActiveVacationRule(subject) > 0) {
				mapValidate.put(APPROVER_ID_VALIDATE, Labels.getLabel("swf.err.approverIdHasCurrentActiveVacationRule"));				
			}
		} 
		
		if (subject.getSubstituteApproverId() == null) {
			mapValidate.put(SUBSTITUTE_APPROVER_ID_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.substituteApprover")}));
		} else {
			VacationRule vacation = new VacationRule();
			vacation.setApproverId(subject.getSubstituteApproverId());
			vacation.setEffectiveDateFrom(subject.getEffectiveDateFrom());
			vacation.setEffectiveDateTo(subject.getEffectiveDateTo());
			if (subject.getApproverId().equals(subject.getSubstituteApproverId())) {
				mapValidate.put(SUBSTITUTE_APPROVER_ID_VALIDATE, Labels.getLabel("swf.err.approverIdSameWithSubstituteApproverId"));
			} else if (vacation.getEffectiveDateFrom() != null && vacation.getEffectiveDateTo() != null) {
				if (vacationRuleServiceImpl.countActiveVacationRule(vacation) > 0) {
					mapValidate.put(SUBSTITUTE_APPROVER_ID_VALIDATE, Labels.getLabel("swf.err.substituteHasCurrentActiveVacationRule"));				
				}
			}
		}
		
		if (subject.getEffectiveDateFrom() == null || 
				subject.getEffectiveDateTo() == null) {
			mapValidate.put(EFFECTIVE_DATE_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("common.effectiveDateFrom")}));
		} else if (subject.getEffectiveDateFrom().after(subject.getEffectiveDateTo())) {
			mapValidate.put(EFFECTIVE_DATE_VALIDATE, Labels.getLabel("swf.err.effectiveStartDateCantLessThanEndDate"));
		} else if((subject.getEffectiveDateFrom().before(today)) && subject.getVacationRuleId() == null) {
			mapValidate.put(EFFECTIVE_DATE_VALIDATE, Labels.getLabel("swf.err.effectiveStartDateMustGreaterThenOrEqualsToToday"));
		} else if (subject.getEffectiveDateTo().before(today)) {
			mapValidate.put(EFFECTIVE_DATE_VALIDATE, Labels.getLabel("swf.err.effectiveEndDateMustGreaterThanOrEqualsToday"));
		}
		
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}	
	}

}
