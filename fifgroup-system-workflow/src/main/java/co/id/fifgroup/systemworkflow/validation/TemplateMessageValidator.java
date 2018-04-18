package co.id.fifgroup.systemworkflow.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.dto.TemplateMessageDTO;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;

@Component
public class TemplateMessageValidator implements Validator<TemplateMessageDTO>{

	private static final Logger logger = LoggerFactory.getLogger(TemplateMessageValidator.class);
	
	public static final String TEMPLATE_MESSAGE_NAME_VALIDATE = "template message name validate";
	public static final String TRANSACTION_TYPE_VALIDATE = "transaction type validate";
	public static final String ACTION_TYPE_VALIDATE = "action type validate";
	public static final String TEMPLATE_SUBJECT_VALIDATE = "template subject validate";
	public static final String TEMPLATE_CONTENT_VALIDATE = "template content validate";
	
	@Autowired
	TemplateMessageService templateMessageDtoServiceImpl;
	
	@Override
	public void validate(TemplateMessageDTO subject) throws ValidationException {
		Map<String, String> mapValidate = new HashMap<>();
		
		if (isNullOrEmpty(subject.getTemplateMessage().getTemplateName())) {
			mapValidate.put(TEMPLATE_MESSAGE_NAME_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.templateMessageName")}));
		} else if (!ValidationRule.isText(subject.getTemplateMessage().getTemplateName())) {
			mapValidate.put(TEMPLATE_MESSAGE_NAME_VALIDATE, Labels.getLabel("common.err.validationRuleText"));
		}
		if (subject.getTemplateMessage().getTemplateId() == null) {
			try {
				int count = templateMessageDtoServiceImpl.countTemplateMessageByName(subject.getTemplateMessage().getTemplateName());
				if (count > 0) {
					mapValidate.put(TEMPLATE_MESSAGE_NAME_VALIDATE, Labels.getLabel("common.mustBeUnique", new Object[] {Labels.getLabel("swf.templateMessageName")}));					
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		if (subject.getTemplateMessageMappingDTO().getTransactionId() == null) {
			mapValidate.put(TRANSACTION_TYPE_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.transactionType")}));
		}
		
		if (isNullOrEmpty(subject.getTemplateMessageMappingDTO().getActionType()) 
				|| subject.getTemplateMessageMappingDTO().getActionType().equals(ActionType.SELECT.name())) {
			mapValidate.put(ACTION_TYPE_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.actionType")}));
		}
		
		if (isNullOrEmpty(subject.getTemplateMessage().getTemplateSubject())) {
			mapValidate.put(TEMPLATE_SUBJECT_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.subject")}));
		}
		
		if (isNullOrEmpty(subject.getTemplateMessage().getTemplateContent())) {
			mapValidate.put(TEMPLATE_CONTENT_VALIDATE, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("swf.contentMessage")}));
		}
		
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}	
	}

}
