package co.id.fifgroup.workstructure.validation;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import com.google.common.base.Strings;

import co.id.fifgroup.workstructure.dto.OrgLevelHierDTO;

@Component
public class OrgLevelHierValidator implements Validator<OrgLevelHierDTO> {
	
	public static final String LEVEL_HIER_NAME = "levelHierName";
	public static final String DESCRIPTION = "description";
	public static final String SEQUENCE = "sequence";
	public static final String LEVEL_ID = "levelId";
	public static final String DATE_FROM = "dateFrom";
	public static final String DATE_TO = "dateTo";
	public static final String ELEMENTS = "elements";
	
	@Override
	public void validate(OrgLevelHierDTO subject) throws ValidationException {
		String hierName = subject.getLevelHierName();
		
		Map<String, String> violations = new HashMap<String, String>();
		
		if(hierName == null || hierName.equals(""))
			violations.put(LEVEL_HIER_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationLevelHierarchyName") }));
		
		if(subject.getDateFrom() != null) {
			Date startDate = DateUtils.truncate(subject.getDateFrom(), Calendar.DATE);
			Date endDate = DateUtils.truncate(subject.getDateTo(), Calendar.DATE);
			if(startDate.compareTo(endDate) > 0)
				violations.put(DATE_FROM, Labels.getLabel("common.effectiveDateToMustBeHigherThanOrEqualToEffectiveDateFrom"));
			else if(endDate.compareTo(startDate) < 0)
				violations.put(DATE_FROM, Labels.getLabel("common.effectiveDateToMustBeHigherThanOrEqualToEffectiveDateFrom"));
			
			if(subject.getId() == null) {
				if(startDate.compareTo(DateUtils.truncate(new Date(), Calendar.DATE)) <= 0)
					violations.put(DATE_FROM, Labels.getLabel("common.effectiveDateFromMustBeHigherThanToday"));
			}
		}
		else
			violations.put(DATE_FROM, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.effectiveDateFrom") }));
		
		if(!subject.getDateTo().equals(DateUtil.MAX_DATE))
			violations.put(DATE_TO, Labels.getLabel("wos.effectiveDateToMustBeMaxDate"));
		
			if(Strings.isNullOrEmpty(subject.getDescription())){
			violations.put(DESCRIPTION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationLevelHierarchyDescription") }));
		}
		
		if(subject.getHierElements().size() <= 0) {
			violations.put(ELEMENTS, Labels.getLabel("wos.err.tableOrganizationLevelHierarchy"));
		}
		if(violations.size() > 0)
			throw new ValidationException(violations);
	}
}
