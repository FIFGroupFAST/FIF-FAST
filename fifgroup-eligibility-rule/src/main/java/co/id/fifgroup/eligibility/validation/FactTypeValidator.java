package co.id.fifgroup.eligibility.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.eligibility.constant.RetrievalMode;
import co.id.fifgroup.eligibility.dto.FactTypeDTO;
import co.id.fifgroup.eligibility.dto.FactTypeFieldDTO;
import co.id.fifgroup.eligibility.service.FactTypeSetupService;
import co.id.fifgroup.eligibility.util.IdGenerator;

@Component
public class FactTypeValidator implements Validator<FactTypeDTO> {
	
	public static final String NAME = "factTypeName";
	public static final String FIELDS = "fields";
	public static final String RETRIEVAL_MODE = "retrievalMode";
	public static final String QUERY = "query";
	
	@Autowired private FactTypeSetupService factTypeSetupService;
	@Autowired private SecurityService securityService;

	@Override
	public void validate(FactTypeDTO subject) throws ValidationException {
		ValidationException vex = new ValidationException();

		if(Strings.isNullOrEmpty(subject.getName())) {
			vex.put(NAME, Labels.getLabel("common.pleaseFill", new Object[] {"Fact Type Name"}));
		} else if (!checkDuplicateEntry(subject)) {
			vex.put(NAME, "Fact Type Name must be unique");
		}

		if (subject.getFields().size() <= 0) {
			vex.put(FIELDS, "Fact Type Fields size must greater than 0");
		} else {
			// check duplicate entry on fields
			Multiset<FactTypeFieldDTO> occurences = HashMultiset.create();
			occurences.addAll(subject.getFields());
			for (FactTypeFieldDTO field : occurences.elementSet()) {
				if (occurences.count(field) > 1) 
					vex.put(FIELDS, String.format("Field Name: %s must be unique", field.getName()));
				else if (field.getFieldType() == null)
					vex.put(FIELDS, Labels.getLabel("common.pleaseFill", new Object[] {"Fact Type Field"}));
			}
		}
		
		if(subject.getRetrievalMode() == null) {
			vex.put(RETRIEVAL_MODE, Labels.getLabel("common.pleaseFill", new Object[] {"Retrieval Mode"}));
		} else if(subject.getRetrievalMode() == RetrievalMode.QUERY) {
			if(Strings.isNullOrEmpty(subject.getSqlQuery()))
				vex.put(QUERY, Labels.getLabel("common.pleaseFill", new Object[] {"SQL Query"}));
		}
		
		if(vex.hasErrors())
			throw vex;		
	}

	
	private boolean checkDuplicateEntry(FactTypeDTO subject) {
		if (subject.getId() != null)
			return true;
		FactTypeDTO example = new FactTypeDTO(IdGenerator.generateFrom(subject.getName()));
		example.setCompanyId(securityService.getSecurityProfile().getWorkspaceCompanyId());
		return factTypeSetupService.countByExample(example).compareTo(0) == 0;
		
	}
}
