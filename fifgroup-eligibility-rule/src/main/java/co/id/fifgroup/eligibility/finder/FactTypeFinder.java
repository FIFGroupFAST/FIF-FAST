package co.id.fifgroup.eligibility.finder;

import java.util.List;

import co.id.fifgroup.eligibility.dto.FactTypeDTO;

public interface FactTypeFinder {

	List<FactTypeDTO> findByExample(FactTypeDTO example);
	
	Integer countByExample(FactTypeDTO example);
}
