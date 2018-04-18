package co.id.fifgroup.eligibility.service;

import java.util.List;

import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.eligibility.dto.FactTypeDTO;

public interface FactTypeSetupService {

	void save(FactTypeDTO subject) throws ValidationException;
	
	void validate(FactTypeDTO subject) throws ValidationException;
	
	List<FactTypeDTO> findByExample(FactTypeDTO example);
	
	Integer countByExample(FactTypeDTO example);
	
	FactTypeDTO findById(String factTypeId);
	
	
}
