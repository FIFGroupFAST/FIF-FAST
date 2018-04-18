package co.id.fifgroup.basicsetup.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.FormulaFinder;
import co.id.fifgroup.basicsetup.dao.FormulaMapper;
import co.id.fifgroup.basicsetup.dao.FormulaVersionMapper;
import co.id.fifgroup.basicsetup.dao.FormulaVersionParamMapper;
import co.id.fifgroup.basicsetup.domain.Formula;
import co.id.fifgroup.basicsetup.domain.FormulaExample;
import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.domain.FormulaVersion;
import co.id.fifgroup.basicsetup.domain.FormulaVersionParam;
import co.id.fifgroup.basicsetup.domain.FormulaVersionParamExample;
import co.id.fifgroup.basicsetup.dto.FormulaDTO;
import co.id.fifgroup.basicsetup.service.FormulaService;
import co.id.fifgroup.basicsetup.validation.FormulaValidator;
import co.id.fifgroup.core.util.IdGenerator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.version.AbstractVersioningService;


@Transactional
@Service
public class FormulaServiceImpl extends AbstractVersioningService<FormulaDTO> implements FormulaService{
	
	@Autowired
	private FormulaMapper formulaMapper;
	@Autowired
	private FormulaFinder formulaFinder;
	@Autowired
	private FormulaValidator formulaValidator;
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private FormulaVersionMapper formulaVersionMapper;
	@Autowired
	private FormulaVersionParamMapper formulaVersionParamMapper;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional
	public void save(FormulaDTO formulaDto) throws Exception {
		super.save(formulaDto);
	}

	@Override
	@Transactional(readOnly = true)
	public int countByExample(FormulaExample example) {
		return formulaMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public int getCountFormulaDtoByFormulaNameAndDescription(
			String formulaName, String description) {
		return formulaFinder.getCountFormulaDtoByFormulaNameAndDescription(formulaName, description);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FormulaDTO> getFormulaDtoByFormulaNameAndDescription(String formulaName, String description) {
		return formulaFinder.getFormulaDtoByFormulaNameAndDescription(formulaName, description);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Formula> getFormulaByExample(FormulaExample formulaExample,
			int limit, int offset) {
		return formulaMapper.selectByExampleWithRowbounds(formulaExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Formula> getFormulaByExample(FormulaExample formulaExample) {
		return formulaMapper.selectByExample(formulaExample);
	}

	@Override
	@Transactional(readOnly = true)
	public FormulaDTO getFormulaDtoById(Long id) {
		return formulaFinder.getFormulaDtoById(id);
	}

	@Override
	@Transactional
	public void insertHeader(FormulaDTO subject) {
		
		Formula formula = modelMapper.map(subject, Formula.class);
		formulaMapper.insert(formula);
		subject.setId(formula.getId());
		
	}

	@Override
	@Transactional
	public void insertDetail(FormulaDTO subject) {
		
		FormulaVersion version = modelMapper.map(subject, FormulaVersion.class);
		version.setId(subject.getId());
		formulaVersionMapper.insert(version);
		for(FormulaParameter formulaParameter : subject.getListFormulaParameter()) {
			FormulaVersionParam formulaVersionParam = new FormulaVersionParam();
			formulaVersionParam.setVersionId(version.getVersionId());
			formulaVersionParam.setFormulaParameterId(formulaParameter.getFormulaParameterId());
			formulaVersionParam.setCreatedBy(version.getCreatedBy());
			formulaVersionParam.setCreationDate(version.getCreationDate());
			formulaVersionParam.setLastUpdatedBy(version.getLastUpdatedBy());
			formulaVersionParam.setLastUpdateDate(version.getLastUpdateDate());
			formulaVersionParamMapper.insert(formulaVersionParam);
		}
	}

	@Override
	@Transactional
	public void delete(FormulaDTO subject) {
		super.delete(subject);
	}

	@Override
	@Transactional
	public void deleteHeader(FormulaDTO subject) {
		formulaMapper.deleteByPrimaryKey(subject.getId());
	}

	@Override
	@Transactional
	public void deleteDetail(FormulaDTO subject) {
		FormulaVersionParamExample formulaVersionParamExample = new FormulaVersionParamExample();
		formulaVersionParamExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		formulaVersionParamMapper.deleteByExample(formulaVersionParamExample);
		formulaVersionMapper.deleteByPrimaryKey(subject.getVersionId());
	}

	@Override
	@Transactional
	public void updateDetail(FormulaDTO subject) {
		
		FormulaVersion version = modelMapper.map(subject, FormulaVersion.class);
		formulaVersionMapper.updateByPrimaryKeyWithBLOBs(version);
		if(subject.isFuture()) {
			FormulaVersionParamExample formulaVersionParamExample = new FormulaVersionParamExample();
			formulaVersionParamExample.createCriteria().andVersionIdEqualTo(version.getVersionId());
			formulaVersionParamMapper.deleteByExample(formulaVersionParamExample);
			for(FormulaParameter formulaParameter : subject.getListFormulaParameter()) {
				FormulaVersionParam formulaVersionParam = new FormulaVersionParam();
				formulaVersionParam.setVersionId(version.getVersionId());
				formulaVersionParam.setFormulaParameterId(formulaParameter.getFormulaParameterId());
				formulaVersionParam.setCreatedBy(version.getCreatedBy());
				formulaVersionParam.setCreationDate(version.getCreationDate());
				formulaVersionParam.setLastUpdatedBy(version.getLastUpdatedBy());
				formulaVersionParam.setLastUpdateDate(version.getLastUpdateDate());
				formulaVersionParamMapper.insert(formulaVersionParam);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Long getNextHeaderId() {
		return idGenerator.getNextHeaderId("BSE_FORMULA_S");
	}

	@Override
	@Transactional(readOnly = true)
	public FormulaDTO findByIdAndVersionNumber(Long id, Integer versionNumber) {
		return formulaFinder.findByIdAndVersionNumber(id, versionNumber);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> findVersionsById(Long id) {
		return formulaFinder.findVersionsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public void validate(FormulaDTO subject) throws ValidationException {
		formulaValidator.validate(subject);
	}
	
	@Override
	@Transactional(readOnly = true)
	public FormulaDTO getFormulaDtoByIdAndVersionNumber(Long id,
			int currentVersionNumber) {
		return formulaFinder.getFormulaDtoByIdAndVersionNumber(id,currentVersionNumber);
	}

	@Override
	@Transactional(readOnly = true)
	public int getCurrentFormulaVersionByFormulaId(Long id) {
		return formulaFinder.getCurrentFormulaVersionByFormulaId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public FormulaDTO getCurrentFormulaDto(Long formulaId) {
		return formulaFinder.getCurrentFormulaDto(formulaId, DateUtils.truncate(new Date(), Calendar.DATE));
	}

	@Override
	@Transactional(readOnly=true)
	public FormulaDTO getLastFormulaDto(Long formulaId) {
		return formulaFinder.getLastFormulaDto(formulaId);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountFormulaVersionByFormulaId(Long formulaId) {
		return formulaFinder.getCountFormulaVersionByFormulaId(formulaId);
	}

	@Override
	public boolean isHaveFuture(Long id) {
		return formulaFinder.isFormulaVersionFutureExist(id) == 1;
	}

	@Override
	public void updateHeader(FormulaDTO subject) {
		
		Formula formula = modelMapper.map(subject, Formula.class);
		formulaMapper.updateByPrimaryKey(formula);
	}

	@Override
	@Transactional(readOnly=true)
	public List<FormulaDTO> getFormulaDtoByExample(FormulaDTO example) {
		return formulaFinder.getFormulaDtoByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountFormulaByExample(FormulaDTO example) {
		return 0;
	}

	@Override
	@Transactional(readOnly=true)
	public FormulaDTO getFormulaDtoByIdAndDate(Long id, Date effectiveDate) {
		return formulaFinder.getFormulaDtoByIdAndDate(id, effectiveDate);
	}

	@Override
	@Transactional(readOnly=true)
	public List<FormulaDTO> getFormulaHeaderDtoByExample(FormulaDTO example, int limit, int offset) {
		return formulaFinder.getFormulaHeaderDtoByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountFormulaByIdAndDate(Long id, Date effectiveDate) {
		return formulaFinder.getCountFormulaByIdAndDate(id, DateUtils.truncate(effectiveDate, Calendar.DATE));
	}
}
