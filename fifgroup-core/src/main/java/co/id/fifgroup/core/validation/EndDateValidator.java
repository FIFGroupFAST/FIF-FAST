package co.id.fifgroup.core.validation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.version.Version;


public class EndDateValidator implements Validator<Version>{

	public static final String DATE_TO = "dateTo";
	
	@Override
	public void validate(Version subject) throws ValidationException {
		
		Calendar today = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(subject.getDateTo());
		
		Map<String, String> mapValidate = new HashMap<>();
		
		if(subject.getDateTo() != null) {
			if(DateUtils.truncate(endDate, Calendar.DATE).before(DateUtils.truncate(today, Calendar.DATE))) {
				mapValidate.put(DATE_TO, Labels.getLabel("common.effectiveDateToMustBeLargerThanOrEqualToday"));
			}
		}
		
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}	
	}

}
