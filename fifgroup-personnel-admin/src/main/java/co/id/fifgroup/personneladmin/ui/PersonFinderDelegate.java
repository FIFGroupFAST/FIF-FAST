package co.id.fifgroup.personneladmin.ui;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;

public interface PersonFinderDelegate extends Serializable {
	
	List<PersonDTO> find(PersonCriteriaDTO criteria,
			Long companyId, int offset, int limit);
	
	int count(PersonCriteriaDTO criteria,
			Long companyId);
}
