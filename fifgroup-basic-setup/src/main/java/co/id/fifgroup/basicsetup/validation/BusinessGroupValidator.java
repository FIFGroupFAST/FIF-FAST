package co.id.fifgroup.basicsetup.validation;

import static com.google.common.base.Strings.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.domain.BusinessGroup;
import co.id.fifgroup.basicsetup.domain.BusinessGroupExample;
import co.id.fifgroup.basicsetup.service.BusinessGroupService;
import co.id.fifgroup.core.validation.ValidationException;
import static co.id.fifgroup.core.validation.ValidationRule.*;
import co.id.fifgroup.core.validation.Validator;

@Component
public class BusinessGroupValidator implements Validator<BusinessGroup> {

	public static final String GROUP_CODE = "businessGroup.GroupCode";
	public static final String GROUP_NAME = "businessGroup.GroupName";
	
	@Autowired
	private BusinessGroupService businessGroupService;
	
	@Override
	public void validate(BusinessGroup subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		
		if(subject.getGroupId() == null) {
			insertValidation(subject, violations);
		} else {
			updateValidation(subject, violations);
		}
		if (violations.size() > 0) throw new ValidationException(violations);
		
	}

	private void groupCodeValidation(BusinessGroup subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getGroupCode())) {
			errors.put(GROUP_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.groupCode") }));
			return;
		}
		if(!isAlphaNumeric(subject.getGroupCode())) {
			errors.put(GROUP_CODE, "Group Code must be alphanumeric");
			return;
		}
	}
	
	private void groupNameValidation(BusinessGroup subject, Map<String, String> errors) {
		if(isNullOrEmpty(subject.getGroupName())) {
			errors.put(GROUP_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.groupName") }));
			return;
		}
	}
	
	private void formValidation(BusinessGroup subject, Map<String, String> errors) {
		groupCodeValidation(subject, errors);
		groupNameValidation(subject, errors);
	}
	
	private void insertValidation(BusinessGroup subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(GROUP_CODE)) {
			BusinessGroupExample businessGroupCodeExistExample = new BusinessGroupExample();
			businessGroupCodeExistExample.createCriteria().andGroupCodeEqualTo(subject.getGroupCode());
			if(businessGroupService.countByExample(businessGroupCodeExistExample) > 0) {
				errors.put(GROUP_CODE, Labels.getLabel("bse.err.groupCodeAlreadyExist"));
			}
		}
		if(!errors.containsKey(GROUP_NAME)) {
			BusinessGroupExample businessGroupNameExistExample = new BusinessGroupExample();
			businessGroupNameExistExample.createCriteria().andGroupNameEqualTo(subject.getGroupName());
			if(businessGroupService.countByExample(businessGroupNameExistExample) > 0) {
				errors.put(GROUP_NAME, Labels.getLabel("bse.err.groupNameAlreadyExist"));
			}
		}
	}
	
	private void updateValidation(BusinessGroup subject, Map<String, String> errors) {
		formValidation(subject, errors);
		if(!errors.containsKey(GROUP_NAME)) {
			if(businessGroupService.isExistBusinessGroupForUpdate(subject)) {
				errors.put(GROUP_NAME, Labels.getLabel("bse.err.groupNameAlreadyExist"));				
			}			
		}
	}

}
