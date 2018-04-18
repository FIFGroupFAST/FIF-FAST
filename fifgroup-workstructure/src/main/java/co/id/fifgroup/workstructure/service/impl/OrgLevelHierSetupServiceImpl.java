package co.id.fifgroup.workstructure.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.IdGenerator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.version.AbstractVersioningService;
import co.id.fifgroup.workstructure.domain.OrgLevelHierElement;
import co.id.fifgroup.workstructure.domain.OrgLevelHierElementExample;
import co.id.fifgroup.workstructure.domain.OrgLevelHierVersion;
import co.id.fifgroup.workstructure.domain.OrgLevelHierarchy;
import co.id.fifgroup.workstructure.service.OrgLevelHierSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.workstructure.dao.OrgLevelHierElementMapper;
import co.id.fifgroup.workstructure.dao.OrgLevelHierVersionMapper;
import co.id.fifgroup.workstructure.dao.OrgLevelHierarchyMapper;
import co.id.fifgroup.workstructure.dto.OrgLevelHierDTO;
import co.id.fifgroup.workstructure.dto.OrgLevelHierElementDTO;
import co.id.fifgroup.workstructure.finder.OrgLevelHierFinder;
import co.id.fifgroup.workstructure.validation.OrgLevelHierValidator;

@Transactional
@Service
public class OrgLevelHierSetupServiceImpl extends AbstractVersioningService<OrgLevelHierDTO> implements OrgLevelHierSetupService{

	@Autowired
	private OrgLevelHierFinder orgLevelHierFinder;
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private OrgLevelHierarchyMapper levelHierMapper;
	@Autowired
	private OrgLevelHierVersionMapper levelHierVersionMapper;
	@Autowired
	private OrgLevelHierElementMapper levelHierElementMapper;
	@Autowired
	private OrgLevelHierValidator levelHierValidator;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void save(OrgLevelHierDTO subject) throws Exception {
		super.save(subject);
	}

	@Override
	public void insertHeader(OrgLevelHierDTO subject) {
		
		OrgLevelHierarchy levelHier = modelMapper.map(subject, OrgLevelHierarchy.class);
		levelHierMapper.insert(levelHier);
		subject.setId(levelHier.getId());
	}

	@Override
	public void updateHeader(OrgLevelHierDTO subject) {
		
		OrgLevelHierarchy levelHier = modelMapper.map(subject, OrgLevelHierarchy.class);
		levelHierMapper.updateByPrimaryKey(levelHier);
	}

	@Override
	public void insertDetail(OrgLevelHierDTO subject) {
		
		OrgLevelHierVersion levelHierVersion = modelMapper.map(subject, OrgLevelHierVersion.class);
		levelHierVersionMapper.insert(levelHierVersion);
		subject.setVersionId(levelHierVersion.getVersionId());
		
		if(subject.getHierElements() != null) {
			for(OrgLevelHierElementDTO elementDto : subject.getHierElements()) {
				OrgLevelHierElement element = modelMapper.map(elementDto, OrgLevelHierElement.class);
				element.setVersionId(subject.getVersionId());
				element.setId(subject.getId());
				element.setCreatedBy(subject.getCreatedBy());
				element.setCreationDate(subject.getCreationDate());
				element.setLastUpdatedBy(subject.getLastUpdatedBy());
				element.setLastUpdateDate(subject.getLastUpdateDate());
				levelHierElementMapper.insert(element);
			}
		}
	}

	@Override
	public void deleteHeader(OrgLevelHierDTO subject) {
		levelHierMapper.deleteByPrimaryKey(subject.getId());
	}

	@Override
	public void deleteDetail(OrgLevelHierDTO subject) {
		OrgLevelHierElementExample example = new OrgLevelHierElementExample();
		example.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		levelHierElementMapper.deleteByExample(example);
		levelHierVersionMapper.deleteByPrimaryKey(subject.getVersionId());
	}

	@Override
	public void updateDetail(OrgLevelHierDTO subject) {
		
		OrgLevelHierVersion hierVersion = modelMapper.map(subject, OrgLevelHierVersion.class);
		
		OrgLevelHierElementExample example = new OrgLevelHierElementExample();
		example.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		levelHierElementMapper.deleteByExample(example);
		
		if(subject.getHierElements() != null) {
			for(OrgLevelHierElementDTO elementDto : subject.getHierElements()) {
				OrgLevelHierElement element = modelMapper.map(elementDto, OrgLevelHierElement.class);
				element.setVersionId(subject.getVersionId());
				element.setId(subject.getId());
				element.setCreatedBy(subject.getCreatedBy());
				element.setCreationDate(subject.getCreationDate());
				element.setLastUpdatedBy(subject.getLastUpdatedBy());
				element.setLastUpdateDate(subject.getLastUpdateDate());
				levelHierElementMapper.insert(element);
			}
		}	
		levelHierVersionMapper.updateByPrimaryKey(hierVersion);
	}

	@Override
	public Long getNextHeaderId() {
		return idGenerator.getNextHeaderId("WOS_ORG_LEVEL_HIER_S");
	}
	
	@Override
	public OrgLevelHierDTO find() {
		return orgLevelHierFinder.find(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	public OrgLevelHierDTO findByIdAndVersionNumber(Long id,
			Integer versionNumber) {
		return orgLevelHierFinder.findByIdAndVersionNumber(id, versionNumber);
	}

	@Override
	public List<Integer> findVersionsById(Long id) {
		return orgLevelHierFinder.findVersionsById(id);
	}

	@Override
	public void validate(OrgLevelHierDTO subject) throws ValidationException {
		levelHierValidator.validate(subject);
	}

	@Override
	public boolean isHaveFuture(Long id) {
		return orgLevelHierFinder.isHaveFuture(id) == 1;
	}

	@Override
	public Boolean isFutureExist(Long id) {
		return orgLevelHierFinder.isFutureExist(id) > 0;
	}	
}
