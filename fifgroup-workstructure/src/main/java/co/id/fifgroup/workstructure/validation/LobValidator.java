package co.id.fifgroup.workstructure.validation;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.service.LobSetupService;

import co.id.fifgroup.workstructure.dto.LobDTO;
import co.id.fifgroup.workstructure.dto.LobElementDTO;

@Component
public class LobValidator implements Validator<LobDTO>{
	
	public static final String ORGANIZATION = "organization";
	public static final String JOB = "job";
	public static final String EFFECTIVE_START_DATE = "effectiveStartDate";
	public static final String EFFECTIVE_END_DATE = "effectiveEndDate";
	public static final String PRODUCT_CODE = "productCode";
	public static final String PERCENTAGE = "percentage";
	public static final String HISTORY = "history";
	public static final String LOB_ELEMENT = "lobElement";
	
	@Autowired
	private LobSetupService lobSetupServiceImpl;
	
	@Override
	public void validate(LobDTO subject) throws ValidationException {
		List<Long> histories = lobSetupServiceImpl.findHistoriesById(subject.getJobId(), subject.getOrganizationId()); 
		Long organization = subject.getOrganizationId();
		Long job = subject.getJobId();
		Date effectiveStartDate = subject.getEffectiveStartDate();
		List<LobElementDTO> jobLobElements = subject.getLobElements();
	
		Map<String, String> violations = new HashMap<>();

		if(histories.size() > 0) {
			Long lastHistoryId = histories.get(histories.size()-1);
			LobDTO lastLob = lobSetupServiceImpl.findByIdAndHistoryId(lastHistoryId, subject.getJobId(), subject.getOrganizationId());
			if(lastLob.getEffectiveEndDate().before(new Date()))
				violations.put(HISTORY, "Inactive");
		}
		
		if(organization == null)
			violations.put(ORGANIZATION, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.organization") }));
		
		if(job == null)
			violations.put(JOB, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.job") }));
		
		if(effectiveStartDate == null)
			violations.put(EFFECTIVE_START_DATE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.effectiveDateFrom") }));
		else if(effectiveStartDate.compareTo(new Date()) <= 0)
			violations.put(EFFECTIVE_START_DATE, Labels.getLabel("common.effectiveDateFromMustBeHigherThanToday"));
		
		if(subject.getEffectiveEndDate() != null) {
			if(DateUtils.truncate(subject.getEffectiveEndDate(), Calendar.DATE).before(DateUtils.truncate(new Date(), Calendar.DATE))) {
				violations.put(EFFECTIVE_END_DATE, Labels.getLabel("common.effectiveDateToMustBeHigherThanOrEqualToEffectiveDateFrom"));
			}
		}

		if(jobLobElements != null) {
			Set<LobElementDTO> setElements = new HashSet<>(jobLobElements);
			if(jobLobElements.size() > setElements.size())
				violations.put(PRODUCT_CODE, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.product") }));
			
			BigDecimal percent = BigDecimal.ZERO;
			for(LobElementDTO element : setElements) {
				percent = percent.add(element.getPercentage());
			}
			if(percent.compareTo(new BigDecimal(100.0)) == 0)
				percent = new BigDecimal(100);

			if(!percent.equals(new BigDecimal(100))) {
				violations.put(PERCENTAGE, Labels.getLabel("wos.totalOfPercentageMustBeOneHundred"));
			}
		}
		
		if (!subject.isValidLobElements())
			violations.put(LOB_ELEMENT, "");
		
		if(violations.size() > 0) {
			throw new ValidationException(violations);
		}		
	}

}
