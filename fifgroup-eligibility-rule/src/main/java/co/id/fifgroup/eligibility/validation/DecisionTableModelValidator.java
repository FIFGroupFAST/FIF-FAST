package co.id.fifgroup.eligibility.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.ValidationRule;
import co.id.fifgroup.core.validation.Validator;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.eligibility.dao.DecisionTableModelMapper;
import co.id.fifgroup.eligibility.domain.DecisionTableModelExample;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.util.IdGenerator;

@Component
public class DecisionTableModelValidator implements Validator<DecisionTableModelDTO>{
	
	public static final String NAME = "modelName";
	public static final String COLUMNS = "columns";
	public static final String RESULT_TYPE = "resultType";
	public static final String RESULTS = "results";
	
	@Autowired private DecisionTableModelMapper dtMapper;
	@Autowired private SecurityService securityService;

	@Override
	public void validate(DecisionTableModelDTO subject) throws ValidationException {
		ValidationException vex = new ValidationException();
		
		if (Strings.isNullOrEmpty(subject.getName()))
			vex.put(NAME, Labels.getLabel("common.pleaseFill", new Object[] {"Decision Table Model Name"}));
		else if (subject.getName().trim().equals(""))
			vex.put(NAME, Labels.getLabel("common.pleaseFill", new Object[] {"Decision Table Model Name"}));
		else if (!ValidationRule.isAlphanumWithSpace(subject.getName()))
			vex.put(NAME, Labels.getLabel("common.mustBeAlphaNumeric",new Object[] {"Decision Table Model Name"}));
		else if (Strings.isNullOrEmpty(subject.getId()) && !isUnique(subject))
			vex.put(NAME, Labels.getLabel("common.mustBeUnique",new Object[] {"Decision Table Model Name"}));
		
		if (subject.getConditions() == null || subject.getConditions().size() <= 0)
			vex.put(COLUMNS, Labels.getLabel("common.pleaseFill",new Object[] {"Condition Column"}));
		else {
			// check duplicate entry on columns
			Multiset<String> occurences = HashMultiset.create();
			for (DecisionTableColumnDTO condition : subject.getConditions()) {
				occurences.add(condition.getName());
			}
			for (String column : occurences.elementSet()) {
				if (occurences.count(column) > 1) 
					vex.put(COLUMNS, String.format("Column Name: %s must be unique", column));
			}
		}
//		if (subject.getResultType().getId() == null)
//			vex.put(RESULT_TYPE,  Labels.getLabel("common.pleaseFill",new Object[] {"Result Type"}));
//		
		if (subject.getResultType().getId() != null && (subject.getResults() == null || subject.getResults().size() <= 0))
			vex.put(RESULTS, Labels.getLabel("common.pleaseFill",new Object[] {"Result Column"}));
		else {
			// check duplicate entry on columns
			Multiset<String> occurences = HashMultiset.create();
			for (DecisionTableColumnDTO result : subject.getResults()) {
				occurences.add(result.getName());
			}
			for (String column : occurences.elementSet()) {
				if (occurences.count(column) > 1) 
					vex.put(RESULTS, String.format("Column Name: %s must be unique", column));
			}
		}
		
		if(vex.hasErrors()) {
			throw vex;
		}
		
	}
	
	private boolean isUnique(DecisionTableModelDTO subject) {
		DecisionTableModelExample example = new DecisionTableModelExample();
		example.or()
			.andIdEqualTo(IdGenerator.generateFrom(subject.getName()))
			.andCompanyIdEqualTo(securityService.getSecurityProfile().getWorkspaceCompanyId());
		return dtMapper.selectByExample(example).size() <= 0;
	}

}
