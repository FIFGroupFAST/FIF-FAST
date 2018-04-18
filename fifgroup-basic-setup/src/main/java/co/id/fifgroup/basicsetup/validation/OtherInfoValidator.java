package co.id.fifgroup.basicsetup.validation;

import static com.google.common.base.Strings.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.domain.OtherInfoHeaderExample;
import co.id.fifgroup.basicsetup.dto.OtherInfoDTO;
import co.id.fifgroup.basicsetup.service.OtherInfoService;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

@Component
public class OtherInfoValidator implements Validator<OtherInfoDTO> {
	
	public static final String FORM_NAME = "otherInfo.formName";
	
	@Autowired
	private OtherInfoService otherInfoServiceImpl;
	
	@Override
	public void validate(OtherInfoDTO subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if(subject == null) {
			throw new IllegalArgumentException("subject must not be null.");
		}
		if(isNullOrEmpty(subject.getFormName())) {
			violations.put(FORM_NAME, Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.formName") }));
		} else if(subject.getOtherInfoHdrId() == null) {
			OtherInfoHeaderExample otherInfoHeaderExample = new OtherInfoHeaderExample();
			otherInfoHeaderExample.createCriteria().andFormNameEqualTo(subject.getFormName()).andGroupIdEqualTo(subject.getGroupId());
			if(otherInfoServiceImpl.getCountOtherInfoHeaderByExample(otherInfoHeaderExample) > 0) {
				violations.put(FORM_NAME, Labels.getLabel("bse.err.otherInfoFormNameAlreadyExist"));
			}
		}
		if(violations.size() > 0) throw new ValidationException(violations);
	}

}
