package co.id.fifgroup.workstructure.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.OrganizationLevel;
import co.id.fifgroup.workstructure.domain.OrganizationLevelExample;
import co.id.fifgroup.workstructure.service.OrganizationLevelSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.workstructure.dao.OrganizationLevelMapper;
import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;
import co.id.fifgroup.workstructure.finder.OrganizationLevelFinder;
import co.id.fifgroup.workstructure.validation.OrganizationLevelValidator;

@Transactional
@Service
public class OrganizationLevelSetupServiceImpl implements OrganizationLevelSetupService {

	@Autowired
	private OrganizationLevelMapper levelMapper;
	@Autowired
	private OrganizationLevelValidator levelValidator;
	@Autowired
	private OrganizationLevelFinder levelFinder;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	@Transactional(readOnly=false)
	public void save(OrganizationLevelDto subject) throws ValidationException {
		
		OrganizationLevel level = modelMapper.map(subject, OrganizationLevel.class);
		level.setLevelCode(subject.getCode());
		level.setLevelName(subject.getName());
		validate(subject);
		if(null ==subject.getId()) {
			levelMapper.insert(level);
			subject.setId(level.getLevelId());
		} else {			
			levelMapper.updateByPrimaryKey(level);
		}
	}

	@Override
	public void validate(OrganizationLevelDto subject) throws ValidationException {
		levelValidator.validate(subject);
	}

	@Override
	public List<OrganizationLevelDto> findAll(Long companyId) {
		return levelFinder.findAll(companyId);
	}

	@Override
	public List<OrganizationLevel> findByExample(
			OrganizationLevelExample example, int limit, int offset) {
		example.createCriteria().andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		return levelMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	public Integer countByExample(OrganizationLevelExample example) {
		example.createCriteria().andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		return levelMapper.countByExample(example);
	}

	@Override
	public List<OrganizationLevelDto> findByExample(OrganizationLevelDto example) {
		return levelFinder.findByExample(example);
	}
	
	@Override
	public Integer countByExample(OrganizationLevelDto example) {
		return levelFinder.countByExample(example);
	}

	@Override
	public String findByCode(String levelCode) {
		return levelFinder.findByCode(levelCode, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	public OrganizationLevel findById(Long id) {
		return levelMapper.selectByPrimaryKey(id);
	}	
}
