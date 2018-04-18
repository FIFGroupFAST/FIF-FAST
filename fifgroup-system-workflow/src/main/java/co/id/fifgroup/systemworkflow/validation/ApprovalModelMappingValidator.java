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
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMappingDTO;
import co.id.fifgroup.systemworkflow.service.ApprovalModelMappingService;

@Component
public class ApprovalModelMappingValidator implements Validator<ApprovalModelMappingDTO>{

	public static final String SCOPE_VALIDATE = "scope validate";
	public static final String TRANSACTION_TYPE_VALIDATE = "transaction type validate";
	public static final String EFFECTIVE_DATE_FROM_VALIDATE = "effective date from validate";
	public static final String EFFECTIVE_DATE_TO_VALIDATE = "effective date to validate";
	public static final String LIST_APPROVAL_MODEL_MAPPING_DETAIL_VALIDATE = "list approval model mapping detail validate";
	
	@Autowired
	private ApprovalModelMappingService approvalModelMappingServiceImpl;
	
	@Override
	public void validate(ApprovalModelMappingDTO subject)
			throws ValidationException {
		Date today = DateUtil.truncate(new Date());
		Date startDate;
		Date endDate = DateUtil.truncate(subject.getModelMappingHeader().getEffectiveEndDate());
		
		Map<String, String> mapValidate = new HashMap<String, String>();
		
		if (subject.getModelMappingHeader().getEffectiveStartDate() != null) {
			startDate = DateUtil.truncate(subject.getModelMappingHeader().getEffectiveStartDate());
			if (startDate.after(endDate)) {
				mapValidate.put(EFFECTIVE_DATE_FROM_VALIDATE, Labels.getLabel("swf.err.effectiveStartDateCantLessThanEndDate"));
			} else if((startDate.before(today) || startDate.equals(today)) && subject.getModelMappingHeader().getModelMappingId() == null) {
				mapValidate.put(EFFECTIVE_DATE_FROM_VALIDATE, Labels.getLabel("swf.err.effectiveStartDateMustTodayPlusOne"));
			} 
						
			if (subject.getModelMappingHeader().getModelMappingId() == null && subject.getModelMappingHeader().getTransactionId() != null) {
				int count = approvalModelMappingServiceImpl.countApprovalModelMappingActiveByScopeAndTransactionId(subject.getModelMappingHeader().getScope(), subject.getModelMappingHeader().getTransactionId(), subject.getModelMappingHeader().getEffectiveStartDate());
				if (count > 0) {
					mapValidate.put(TRANSACTION_TYPE_VALIDATE, Labels.getLabel("swf.err.hasCurrentActiveMappingForThisTransactionTypeAndScope"));
				}			
			}
		} else {
			mapValidate.put(EFFECTIVE_DATE_FROM_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("common.effectiveDateFrom")}));
		}
		
		if (endDate.before(today)) {
			mapValidate.put(EFFECTIVE_DATE_TO_VALIDATE, Labels.getLabel("swf.err.effectiveEndateDateMustGreaterThanOrEqualsToday"));
		}
		
		if (subject.getModelMappingHeader().getScope() == null) {
			mapValidate.put(SCOPE_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.scope")}));
		} 
		
		if (subject.getModelMappingHeader().getTransactionId() == null) {
			mapValidate.put(TRANSACTION_TYPE_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.transactionType")}));
		}
		
		if (subject.getListModelMessageMapping() == null || subject.getListModelMessageMapping().size() < 1) {
			mapValidate.put(LIST_APPROVAL_MODEL_MAPPING_DETAIL_VALIDATE, Labels.getLabel("common.err.listRequired", new Object[] {Labels.getLabel("swf.approvalModelMapping")}));
		} 		
		
		if (mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}
	}	

}
