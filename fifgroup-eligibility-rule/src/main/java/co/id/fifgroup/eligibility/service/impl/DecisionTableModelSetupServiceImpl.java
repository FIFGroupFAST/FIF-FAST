package co.id.fifgroup.eligibility.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.validation.ValidationException;
import com.google.common.base.Strings;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.eligibility.constant.ColumnType;
import co.id.fifgroup.eligibility.dao.DecisionTableColumnMapper;
import co.id.fifgroup.eligibility.dao.DecisionTableModelMapper;
import co.id.fifgroup.eligibility.domain.DecisionTableColumn;
import co.id.fifgroup.eligibility.domain.DecisionTableModel;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.finder.DecisionTableModelFinder;
import co.id.fifgroup.eligibility.service.DecisionTableModelSetupService;
import co.id.fifgroup.eligibility.util.IdGenerator;
import co.id.fifgroup.eligibility.validation.DecisionTableModelValidator;

@Service
public class DecisionTableModelSetupServiceImpl
	implements DecisionTableModelSetupService{
	
	@Autowired
	private DecisionTableModelMapper decisionTableModelMapper;
	
	@Autowired
	private DecisionTableColumnMapper columnMapper;
	
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Autowired
	private DecisionTableModelFinder finder;
	
	@Autowired
	private DecisionTableModelValidator validator;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	@Transactional
	public void save(DecisionTableModelDTO subject) throws ValidationException {
		final Date today = new Date();
		final Long userId = securityServiceImpl.getSecurityProfile().getUserId();
		validate(subject);
		
		subject.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		DecisionTableModel model = modelMapper.map(subject, DecisionTableModel.class);
		model.setCreatedBy(userId);
		model.setCreationDate(today);
		model.setLastUpdatedBy(userId);
		model.setLastUpdateDate(today);
		
		if (Strings.isNullOrEmpty(model.getId())) {
			model.setId(IdGenerator.generateFrom(model.getName()));
			model.setVersionNumber(1);
		} else if (model.getVersionNumber() == null || model.getVersionNumber().equals(0)){
			model.setVersionNumber(finder.getNextVersion(model.getId()));
		}
		
		decisionTableModelMapper.insert(model);
		
		for(DecisionTableColumnDTO columnDTO : subject.getConditions()) {
			DecisionTableColumn column = modelMapper.map(columnDTO, DecisionTableColumn.class);
			column.setModelId(model.getId());
			column.setVersionNumber(model.getVersionNumber());
			column.setCreatedBy(userId);
			column.setCreationDate(today);
			column.setLastUpdatedBy(userId);
			column.setLastUpdateDate(today);
			columnMapper.insert(column);
		}
		
		for(DecisionTableColumnDTO columnDTO : subject.getResults()) {
			DecisionTableColumn column = modelMapper.map(columnDTO, DecisionTableColumn.class);
			column.setModelId(model.getId());
			column.setVersionNumber(model.getVersionNumber());
			column.setCreatedBy(userId);
			column.setCreationDate(today);
			column.setLastUpdatedBy(userId);
			column.setLastUpdateDate(today);
			columnMapper.insert(column);
		}
		
	}

	@Override
	public void validate(DecisionTableModelDTO subject)
			throws ValidationException {
		validator.validate(subject);
		
	}

	@Override
	public List<DecisionTableModelDTO> findByExample(
			DecisionTableModelDTO example) {
		return finder.findByExample(example);
	}

	@Override
	public Integer countByExample(DecisionTableModelDTO example) {
		return finder.countByExample(example);
	}

	@Override
	public DecisionTableModelDTO findByIdAndVersionNumber(String id, Integer versionNumber) {
		DecisionTableModelDTO result =  finder.findByIdAndVersionNumber(id, versionNumber);
		result.setConditions(finder.findColumns(id, versionNumber, ColumnType.CONDITION));
		result.setResults(finder.findColumns(id, versionNumber, ColumnType.RESULT));
		return result;
	}

	@Override
	public List<Integer> getAvailableVersions(String id) {
		return finder.findAvailableVersions(id);
	}

	@Override
	public List<DecisionTableModelDTO> search(DecisionTableModelDTO example) {
		return finder.search(example);
	}
	
	
	
}
