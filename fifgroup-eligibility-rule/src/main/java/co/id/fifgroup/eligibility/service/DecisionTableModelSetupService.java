package co.id.fifgroup.eligibility.service;

import java.util.List;
import java.util.Map;

import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;

public interface DecisionTableModelSetupService {

	void save(DecisionTableModelDTO subject) throws ValidationException;
	
	void validate(DecisionTableModelDTO subject) throws ValidationException;
	
	List<DecisionTableModelDTO> findByExample(DecisionTableModelDTO example);
	
	List<DecisionTableModelDTO> search(DecisionTableModelDTO example);
	
	Integer countByExample(DecisionTableModelDTO example);
	
	DecisionTableModelDTO findByIdAndVersionNumber(String id, Integer versionNumber);
	
	List<Integer> getAvailableVersions(String id);
	
	
	
}
