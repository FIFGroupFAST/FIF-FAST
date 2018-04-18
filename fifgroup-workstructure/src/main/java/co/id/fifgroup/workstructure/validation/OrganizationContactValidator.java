package co.id.fifgroup.workstructure.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.Serializable;
import java.util.List;

import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationRule;

import co.id.fifgroup.core.constant.ContactType;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.workstructure.dto.OrganizationContactDTO;

public class OrganizationContactValidator implements Serializable, TabularValidationRule<OrganizationContactDTO> {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean validate(OrganizationContactDTO data, List<String> errorRow) {
		if(data.getTypeCode() == null) {
			errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.communicationType") }));
		}
		
		if(data.getInformation() == null) {
			errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.title.information") }));
		}
		if(data.getTypeCode() != null && data.getInformation() != null) {
			if (ContactType.MAIL.toString().equalsIgnoreCase(data.getTypeCode())) {
				if (!ValidationRule.isEmail(data.getInformation())) {
					errorRow.add(Labels.getLabel("common.formatEmailNotValid"));
				}
			} else if(ContactType.OFFICE_PHONE.toString().equalsIgnoreCase(data.getTypeCode())
					|| ContactType.HOME_PHONE.toString().equalsIgnoreCase(data.getTypeCode())
					|| ContactType.FAXIMILE.toString().equalsIgnoreCase(data.getTypeCode())
					|| ContactType.EMERGENCY_PHONE.toString().equalsIgnoreCase(data.getTypeCode())
					|| ContactType.MOBILE_PHONE.toString().equalsIgnoreCase(data.getTypeCode())) {
				if(!ValidationRule.isPhoneNumber(data.getInformation())) {
					errorRow.add(Labels.getLabel("common.formatPhoneNotValid", new Object[] {data.getTypeCode()}));
				}
			}					
		}
		
		if(data.getStartDate() == null) {
			errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.startDate") }));
		}
		if(data.getEndDate() == null) {
			data.setEndDate(DateUtil.MAX_DATE);
		}
		if(data.getStartDate() != null && data.getEndDate() != null) {
			if(data.getStartDate().compareTo(data.getEndDate()) > 0)
				errorRow.add(Labels.getLabel("common.effectiveDateToMustBeHigherThanOrEqualToEffectiveDateFrom"));
		}
		
		if(errorRow.size() > 0)
			return false;
		
		return true;
	}

}
