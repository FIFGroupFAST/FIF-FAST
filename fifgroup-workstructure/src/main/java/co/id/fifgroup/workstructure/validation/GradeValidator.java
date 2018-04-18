package co.id.fifgroup.workstructure.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.domain.GradeExample;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.workstructure.dao.GradeMapper;
import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.GradeRateDTO;

@Component
public class GradeValidator implements Validator<GradeDTO> {

	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private GradeMapper gradeMapper;
	
	public static final String GRADE_CODE = "gradeCode";
	public static final String GRADE_NAME = "gradeName";
	public static final String SUB_GRADE = "subGrade";
	public static final String PERSONAL_GRADE = "personalGrade";
	public static final String HEADER_ID = "headerId";
	public static final String VERSION_NUMBER = "versionNumber";
	public static final String UNIQUE_FIELD = "uniqueField";
	public static final String GRADE_RATE = "gradeRate";
	public static final String OTHER_INFO = "other info validate";
	
	@Override
	public void validate(GradeDTO subject) throws ValidationException {
		String gradeCode = subject.getCode();
		String gradeName = subject.getGrade();
		String subGrade = subject.getSubGrade();
		String personalGrade = subject.getPersonalGradeCode();
		Map<String, String> violations = new HashMap<String, String>();
		int countCode = 0;
		
		GradeExample example = new GradeExample();
		example.createCriteria().andGradeCodeEqualTo(gradeCode)
		.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(subject.getId() == null){
			countCode = gradeMapper.countByExample(example);
		}
		
		if(gradeCode == null || gradeCode.equals(""))
			violations.put(GRADE_CODE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.gradeCode") }));
		else if(!ValidationRule.isAlphaNumeric(gradeCode))
			violations.put(GRADE_CODE, Labels.getLabel("common.mustBeAlphaNumeric", new Object[] { Labels.getLabel("wos.gradeCode") }));
		else if(subject.getId() == null) {
			if(countCode > 0)
				violations.put(GRADE_CODE, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.gradeCode") }));
		}
		
		if(gradeName == null || gradeName.equals(""))
			violations.put(GRADE_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.grade") }));
		else if(!ValidationRule.isWord(gradeName))
			violations.put(GRADE_NAME, Labels.getLabel("common.mustBeWord", new Object[] { Labels.getLabel("wos.grade") }));
		
		if(subGrade == null || subGrade.equals(""))
			violations.put(SUB_GRADE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.subGrade") }));
	
		if(!subject.isUpload()) {
			if(personalGrade == null || personalGrade.equals(""))
				violations.put(PERSONAL_GRADE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.personalGrade") }));
		}
		
		if (subject.isValidGradeRates()) {
			if(subject.getGradeRates().isEmpty())
				violations.put(GRADE_RATE, Labels.getLabel("wos.gradeRateAtLeastOneRow"));
			else {
				Set<GradeRateDTO> set = new HashSet<>(subject.getGradeRates());
				if(set.size() < subject.getGradeRates().size())
					violations.put(GRADE_RATE, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("common.branch") }));
			}
		} else {
			violations.put(GRADE_RATE, "");
		}
		
		if(!subject.isValidOtherInfo()){
			violations.put(OTHER_INFO, "");
		}
		
		
		
		if(violations.size() > 0)
			throw new ValidationException(violations);
	}
}
