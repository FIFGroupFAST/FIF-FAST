package co.id.fifgroup.workstructure.validation;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.domain.OrganizationLevelExample;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.workstructure.dao.OrganizationLevelMapper;
import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;

@Component
public class OrganizationLevelValidator implements Validator<OrganizationLevelDto>{
	
	@Autowired
	private OrganizationLevelMapper levelMapper;
	@Autowired
	private SecurityService securityServiceImpl;
	
	public static final String LEVEL_CODE = "levelCode";
	public static final String LEVEL_NAME = "levelName";
	public static final String COLOR = "color";
	public static final String DATE_FROM = "dateFrom";
	public static final String DATE_TO = "dateTo";
	
	public void validate(OrganizationLevelDto subject) throws ValidationException {
		String levelCode = subject.getCode();
		String levelName = subject.getName();
		String color = subject.getColor();
		
		OrganizationLevelExample levelExample = new OrganizationLevelExample();
		levelExample.createCriteria().andLevelCodeLikeInsensitive(levelCode)
			.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		int countCode = levelMapper.countByExample(levelExample);
		
		levelExample.clear();
		levelExample.createCriteria().andLevelNameLikeInsensitive(levelName)
			.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		int countName = levelMapper.countByExample(levelExample);
		
		levelExample.clear();
		levelExample.createCriteria().andColorLikeInsensitive(color)
			.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		int countColor = levelMapper.countByExample(levelExample);
		
		Map<String, String> violations = new HashMap<>();

		if(subject.getId() == null) {
			if(levelName == null || levelName.equals(""))
				violations.put(LEVEL_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationLevelName") }));
			else if(countName > 0)
				violations.put(LEVEL_NAME, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.organizationLevelName") }));
			
			if(levelCode == null || levelCode.equals(""))
				violations.put(LEVEL_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationLevelCode") }));
			else if(!ValidationRule.isAlphaNumeric(levelCode))
				violations.put(LEVEL_CODE, Labels.getLabel("common.cannotContainSpaceOrCharacter", new Object[] { Labels.getLabel("wos.organizationLevelCode") }));
			else if(countCode > 0)
				violations.put(LEVEL_CODE, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.organizationLevelCode") }));
			
			if(color == null || color.equals(""))
				violations.put(COLOR, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationLevelColor") }));
			else if(countColor > 0)
				violations.put(COLOR, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.organizationLevelColor") }));
		}
		
		if(subject.getStartDate() != null) {
			Date startDate = DateUtils.truncate(subject.getStartDate(), Calendar.DATE);
			Date endDate = DateUtils.truncate(subject.getEndDate(), Calendar.DATE);
			if(startDate.compareTo(endDate) > 0)
				violations.put(DATE_TO, Labels.getLabel("common.endDateMustBeHigherThanOrEqualToStartDate"));
			
			if(subject.getId() == null || subject.getIsFuture()) {
				if(startDate.compareTo(DateUtils.truncate(new Date(), Calendar.DATE)) <= 0)
					violations.put(DATE_FROM, Labels.getLabel("common.startDateMustBeHigherThanToday"));
			}
		}
		else
			violations.put(DATE_FROM, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.startDate") }));
		
		if(violations.size() > 0) {
			throw new ValidationException(violations);
		}		
	}

}
