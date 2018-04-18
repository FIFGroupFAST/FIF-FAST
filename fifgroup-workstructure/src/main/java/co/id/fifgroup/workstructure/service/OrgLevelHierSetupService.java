package co.id.fifgroup.workstructure.service;

import co.id.fifgroup.core.version.VersioningService;

import co.id.fifgroup.workstructure.dto.OrgLevelHierDTO;

public interface OrgLevelHierSetupService extends VersioningService<OrgLevelHierDTO> {
	
	void save(OrgLevelHierDTO subject) throws Exception;
	OrgLevelHierDTO find();
	Boolean isFutureExist(Long id);
}
