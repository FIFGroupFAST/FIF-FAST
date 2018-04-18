package co.id.fifgroup.core.validation;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.version.Version;


public class VersionValidator implements Validator<Version>{

	public static final String DATE_FROM = " date from";
	public static final String DATE_TO = " date to";

	public VersionValidator() {
	}
	
	@Override
	public void validate(Version subject) throws ValidationException {
		Map<String, String> mapValidate = new HashMap<>();
		if(subject.getDateFrom() != null) {
			Date startDate = DateUtils.truncate(subject.getDateFrom(), Calendar.DATE);
			//edit GAL 15103120081532 - Modifikasi Form PD Untuk Budget Control
			if(startDate.compareTo(DateUtils.truncate(new Date(), Calendar.DATE)) <= 0)
				mapValidate.put(DATE_FROM, Labels.getLabel("common.effectiveDateFromMustBeLargerThanToday"));
			if(subject.getDateTo() != null && !subject.getDateTo().equals("")) {
				Date endDate = DateUtils.truncate(subject.getDateTo(), Calendar.DATE);
				if(startDate.compareTo(endDate) > 0)
					mapValidate.put(DATE_FROM, Labels.getLabel("common.effectiveDateFromMustBeLessThanOrEqualToEffectiveDateTo"));
				else if(endDate.compareTo(startDate) < 0)
					mapValidate.put(DATE_FROM, Labels.getLabel("common.effectiveDateFromMustBeLessThanOrEqualToEffectiveDateTo"));
			}
		}
		else
			mapValidate.put(DATE_FROM, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.effectiveDateFrom") }));
		
		
		if(subject.getDateTo() != null && !subject.getDateTo().equals("")) {
			Date endDate = DateUtils.truncate(subject.getDateTo(), Calendar.DATE);
			if(endDate.compareTo(DateUtils.truncate(new Date(), Calendar.DATE)) <= 0)
				mapValidate.put(DATE_TO, Labels.getLabel("common.effectiveDateToMustBeLargerThanOrEqualToday"));
			
		}
		else
			mapValidate.put(DATE_TO, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.effectiveDateTo") }));
		
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}	
	}

}
