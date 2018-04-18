package co.id.fifgroup.workstructure.validation;

import java.util.ArrayList;
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
import co.id.fifgroup.workstructure.domain.GradeSetElement;
import co.id.fifgroup.workstructure.domain.GradeSetElementExample;
import co.id.fifgroup.workstructure.domain.GradeSetExample;

import co.id.fifgroup.workstructure.dao.GradeSetElementMapper;
import co.id.fifgroup.workstructure.dao.GradeSetMapper;
import co.id.fifgroup.workstructure.dto.GradeSetDTO;
import co.id.fifgroup.workstructure.dto.GradeSetElementDTO;

@Component
public class GradeSetValidator implements Validator<GradeSetDTO> {
	
	public static final String GRADE_SET_NAME = "gradeSetName";
	public static final String GRADE = "grade";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String GRADE_UNIQUE = "gradeUnique";
	private List<String> messages = new ArrayList<String>();

	@Autowired
	private GradeSetMapper gradeSetMapper;
	@Autowired
	private GradeSetElementMapper gradeSetElementMapper;
	
	@Override
	public void validate(GradeSetDTO subject) throws ValidationException {
		messages = new ArrayList<String>();
		String gradeSetName = subject.getGradeSetName();
		List<GradeSetElementDTO> elements = subject.getGradeSetElements();
//		Set<GradeSetElementDTO> setElements = new HashSet<>(elements);
		
		Map<String, String> violations = new HashMap<String, String>();
		
		GradeSetExample example = new GradeSetExample();
		example.createCriteria().andGradeSetNameLikeInsensitive(subject.getGradeSetName());
		int countName = gradeSetMapper.countByExample(example);
		
		if(subject.getId() == null) {
			if(gradeSetName == null || gradeSetName.equals(""))
				violations.put(GRADE_SET_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.gradeSetName") }));
			else if(countName > 0)
				violations.put(GRADE_SET_NAME, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.gradeSetName") }));
		}
		
		if(elements.isEmpty())
			violations.put(GRADE, Labels.getLabel("wos.gradeAtLeastOneRow"));
		
		if(subject.getId() == null) {
			if(subject.getStartDate()!= null) {
				Date startDate = DateUtils.truncate(subject.getStartDate(), Calendar.DATE);
				Date endDate = DateUtils.truncate(subject.getEndDate(), Calendar.DATE);
				if(startDate.compareTo(endDate) > 0)
					violations.put(START_DATE, Labels.getLabel("common.endDateMustBeHigherThanOrEqualToStartDate"));
				else if(startDate.compareTo(endDate) == 0)
					violations.put(START_DATE, Labels.getLabel("common.endDateMustBeHigherThanOrEqualToStartDate"));
				
				if(subject.getId() == null || subject.getIsFuture()) {
					if(startDate.compareTo(DateUtils.truncate(new Date(), Calendar.DATE)) <= 0)
						violations.put(START_DATE, Labels.getLabel("common.startDateMustBeHigherThanToday"));
				}
			}
			else
				violations.put(START_DATE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.startDate") }));
		} else {
			Date startDate = DateUtils.truncate(subject.getStartDate(), Calendar.DATE);
			Date endDate = DateUtils.truncate(subject.getEndDate(), Calendar.DATE);
			if(startDate.compareTo(endDate) >= 0)
				violations.put(END_DATE, Labels.getLabel("common.endDateMustBeHigherThanOrEqualToStartDate"));
			else if(endDate.compareTo(DateUtils.truncate(new Date(), Calendar.DATE)) <= 0)
				violations.put(END_DATE, Labels.getLabel("common.startDateMustBeHigherThanToday"));

		}

		/*for(GradeSetElementDTO gradeSetElement : setElements) {
			GradeSetElementExample elementExample = new GradeSetElementExample();
			elementExample.createCriteria().andGradeIdEqualTo(gradeSetElement.getGradeId());
			List<GradeSetElement> countGrades = gradeSetElementMapper.selectByExample(elementExample);
			
			if(!countGrades.isEmpty() && countGrades.size() > 0) {
				if(subject.getId() != null) {
					if(!subject.getId().equals(countGrades.get(0).getId())) {
						addErrorMessage(gradeSetElement.getGradeId());		
					}
				} else {
					addErrorMessage(gradeSetElement.getGradeId());
				}				
			}
		}*/
		
		if(messages.size() > 0)
			violations.put(GRADE_UNIQUE, Labels.getLabel("wos.gradeCodeMustBeUniqueInDifferentGradeSet"));
		
		if(violations.size() > 0)
			throw new ValidationException(violations);
	}
	
	/*private void addErrorMessage(Long errorMsg) {
		String msg = String.valueOf(errorMsg) + " | ";
		messages.add(msg);
	}*/

}
