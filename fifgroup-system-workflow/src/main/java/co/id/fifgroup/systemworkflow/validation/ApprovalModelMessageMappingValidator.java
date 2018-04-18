package co.id.fifgroup.systemworkflow.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMessageMappingDTO;

import co.id.fifgroup.systemworkflow.controller.WorkflowApprovalModelMappingAddComposer;

@Component
public class ApprovalModelMessageMappingValidator implements Validator<ApprovalModelMessageMappingDTO> {

	public static final String APPROVAL_MODEL_VALIDATE = "approval model validate";
	public static final String LEVEL_MESSAGE_VALIDATE = "level message validate";
	public static final String RULE_VALIDATE = "rule validate";
	
	@Override
	public void validate(ApprovalModelMessageMappingDTO subject)
			throws ValidationException {
		
		Map<String, String> mapValidate = new HashMap<String, String>();
		if (subject.getModelMappingDetail().getApprovalModel() == null) {
			mapValidate.put(APPROVAL_MODEL_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.approvalModelName")}));
		} else if (!subject.getModelMappingDetail().getApprovalModel().equals(WorkflowApprovalModelMappingAddComposer.NO_APPROVAL_MODEL_UUID)) {
			if (subject.getListLevelMessage() != null && subject.getListLevelMessage().size() > 0) {
				if (subject.getListLevelMessage().get(0).getListLevelMessage().get(0).getTemplateMappingId() == null) {
					mapValidate.put(LEVEL_MESSAGE_VALIDATE, Labels.getLabel("swf.err.messageMappingForFirstLevelCantBeEmpty"));
				}
			}
		}
		
		if (!subject.isValidateRule()) {
			mapValidate.put(RULE_VALIDATE, Labels.getLabel("swf.err.validateRuleError"));
		}
		
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}
	}

}
