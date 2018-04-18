package co.id.fifgroup.workstructure.service;

import java.util.List;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.OrganizationLevel;
import co.id.fifgroup.workstructure.domain.OrganizationLevelExample;

import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;

public interface OrganizationLevelSetupService {

	void save(OrganizationLevelDto subject) throws ValidationException;
	void validate(OrganizationLevelDto subject) throws ValidationException;
	List<OrganizationLevelDto> findAll(Long companyId);
	List<OrganizationLevelDto> findByExample(OrganizationLevelDto example);
	Integer countByExample(OrganizationLevelDto example);
	List<OrganizationLevel> findByExample(OrganizationLevelExample example, int limit, int offset);
	Integer countByExample(OrganizationLevelExample example);
	String findByCode(String levelCode);
	OrganizationLevel findById(Long id);
}
