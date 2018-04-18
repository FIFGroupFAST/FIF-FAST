package co.id.fifgroup.systemworkflow.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelDTO;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;

@Component
public class ApprovalModelValidator implements Validator<ApprovalModelDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(ApprovalModelValidator.class);
	
	public static final String APPROVAL_MODEL_NAME_VALIDATE = "approval model name validate";
	public static final String EFFECTIVE_START_DATE_VALIDATE = "effective start date validate";
	public static final String LIST_LEVEL_APPROVAL_VALIDATE = "list level approval validate";
	
	@Autowired
	private AvmAdapterService avmAdapterServiceImpl; 
	
//	private Calendar dateFromBefore;
//	
//	public Calendar getDateFromBefore() {
//		return dateFromBefore;
//	}
//
//	public void setDateFromBefore(Calendar dateFromBefore) {
//		this.dateFromBefore = dateFromBefore;
//	}

	@Override
	public void validate(ApprovalModelDTO selected) throws ValidationException {
		Calendar today = DateUtils.truncate(Calendar.getInstance(), Calendar.DATE);
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(selected.getAvmVersions().getEffectiveEndDate());
		AVMVersions currentVersions = null;
		
		Map<String, String> mapValidate = new HashMap<>();
		
		// version validator
		if(selected.getAvmVersions().getEffectiveStartDate() != null) {
			startDate.setTime(selected.getAvmVersions().getEffectiveStartDate());
			try {
				currentVersions = avmAdapterServiceImpl.getAVMVersionsByNumberVersion(selected.getAvm().getAvmId(), selected.getAvmVersions().getVersionNumber());
			} catch (FifException e) {
				logger.error(e.getMessage(), e);
			}
			if(startDate.after(endDate)) {
				mapValidate.put(EFFECTIVE_START_DATE_VALIDATE, Labels.getLabel("swf.err.effectiveDateFromMustBeLessThanOrEqualToDateTo"));
			} 
			else if ((startDate.before(today) || startDate.equals(today)) && currentVersions == null) { //&& (getDateFromBefore() == null || (today.before(getDateFromBefore()) || today.equals(getDateFromBefore())))) {
				mapValidate.put(EFFECTIVE_START_DATE_VALIDATE, Labels.getLabel("swf.err.effectiveDateFromMustBeLargerThanToday"));
			} 
			
			if (currentVersions != null) {
				if (avmAdapterServiceImpl.isFuture(currentVersions)) {
					if (startDate.before(today) || startDate.equals(today)) {
						mapValidate.put(EFFECTIVE_START_DATE_VALIDATE, Labels.getLabel("swf.err.effectiveDateFromMustBeLargerThanToday"));
					}
				} 
			}
 		}
		else {
			mapValidate.put(EFFECTIVE_START_DATE_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("common.effectiveDateFrom")}));
		}
		
		// avm validation
		try {
			if(isNullOrEmpty(selected.getAvm().getAvmName())) {
				mapValidate.put(APPROVAL_MODEL_NAME_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.approvalModelName")}));
			} 
//			else if (!ValidationRule.isText(selected.getAvm().getAvmName())) {
//				mapValidate.put(APPROVAL_MODEL_NAME_VALIDATE, Labels.getLabel("common.err.validationRuleText"));
//			}
			AVM currentAVM = avmAdapterServiceImpl.getAVMById(selected.getAvm().getAvmId());
			if (currentAVM == null && avmAdapterServiceImpl.getCountAVM(selected.getAvm().getAvmName()) > 0) {
				mapValidate.put(APPROVAL_MODEL_NAME_VALIDATE, Labels.getLabel("common.mustBeUnique", new Object[] {Labels.getLabel("swf.approvalModelName")}));
			}
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
		}
		
		// list level validation
		if (selected.getListLevel() == null || 
				selected.getListLevel().size() < 1) {
			mapValidate.put(LIST_LEVEL_APPROVAL_VALIDATE, Labels.getLabel("common.err.listRequired", new Object[] {Labels.getLabel("swf.levelApproval")}));
		} else {
			if (hasSameApprover(selected.getListApprovers())) {
				mapValidate.put(LIST_LEVEL_APPROVAL_VALIDATE, Labels.getLabel("swf.err.haveSameApprover"));
			}
		}
				
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}		
	}
	
	private boolean hasSameApprover(List<AVMApprover> avmApprovers) {
		boolean valid = false;
		int j = 1;
		for (AVMApprover approver : avmApprovers) {
			for (int i = j; i < avmApprovers.size(); i++) {
				if (approver.getApproverId().equals(avmApprovers.get(i).getApproverId()) &&
						approver.getBasedOn() == avmApprovers.get(i).getBasedOn()) {
					return true;
				}				
			}
			j++;
		}
		return valid;
	}
}
