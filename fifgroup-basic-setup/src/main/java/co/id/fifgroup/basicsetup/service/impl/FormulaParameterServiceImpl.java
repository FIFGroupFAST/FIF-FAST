package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.FormulaParameterFinder;
import co.id.fifgroup.basicsetup.dao.FormulaParameterMapper;
import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.domain.FormulaParameterExample;
import co.id.fifgroup.basicsetup.dto.FormulaParameterDTO;
import co.id.fifgroup.basicsetup.service.FormulaParameterService;
import co.id.fifgroup.basicsetup.validation.FormulaParameterValidator;

@Transactional
@Service
public class FormulaParameterServiceImpl implements FormulaParameterService {

	@Autowired
	private FormulaParameterMapper formulaParameterMapper;
	@Autowired
	private FormulaParameterFinder formulaParameterFinder;
	@Autowired
	private FormulaParameterValidator formulaParameterValidator;

	@Override
	@Transactional
	public void save(FormulaParameter formulaParameter) throws Exception {
		formulaParameterValidator.validate(formulaParameter);
		if(formulaParameter.getFormulaParameterId() == null) {
			insert(formulaParameter);
		} else {
			update(formulaParameter);
		}
	}

	@Transactional
	private void insert(FormulaParameter formulaParameter) {
		formulaParameterMapper.insert(formulaParameter);
	}
	
	@Transactional
	private void update(FormulaParameter formulaParameter) {
		formulaParameterMapper.updateByPrimaryKeyWithBLOBs(formulaParameter);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countByExample(FormulaParameterExample formulaParameterExample) {
		return formulaParameterMapper.countByExample(formulaParameterExample);

	}

	@Override
	@Transactional(readOnly = true)
	public List<FormulaParameterDTO> getFormulaDtoByFormulaParameterNameWithRowBounds(
			String parameterName, int limit, int offset) {
		return formulaParameterFinder
				.getFormulaParameterDtoByParameterNameWithRowbounds(
						parameterName, new RowBounds(offset, limit));
	}
	

	@Override
	@Transactional(readOnly=true)
	public List<FormulaParameter> getFormulaParameterByExample(
			FormulaParameterExample formulaParameterExample) {
		return formulaParameterMapper.selectByExample(formulaParameterExample);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FormulaParameter> getFormulaParameterByExample(
			FormulaParameterExample formulaParameterExample, int limit,
			int offset) {
		return formulaParameterMapper.selectByExampleWithRowbounds(
				formulaParameterExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int getCountFormulaParameterDtoByParameterName(String parameterName) {
		return formulaParameterFinder
				.getCountFormulaParameterDtoByParameterName(parameterName);
	}

	@Override
	@Transactional(readOnly=true)
	public FormulaParameter getFormulaParameterByPrimaryKey(
			Long formulaParameterId) {
		return formulaParameterMapper.selectByPrimaryKey(formulaParameterId);
	}

	@Override
	@Transactional(readOnly = true)
	public FormulaParameter getFormulaParameterByParameterId(Long parameterId) {
		return formulaParameterMapper.selectByPrimaryKey(parameterId);
	}

	@Override
	@Transactional(readOnly = true)
	public int getCountFormulaParameterDtoByParameterNameAndDescription(
			String parameterName, String description) {
		return formulaParameterFinder.getCountFormulaParameterDtoByParameterNameAndDescription(parameterName, description);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FormulaParameterDTO> getFormulaDtoByFormulaParameterNameAndDescription(
			String parameterName, String description) {
		return formulaParameterFinder.getFormulaParameterDtoByParameterNameAndDescription(parameterName, description);
	}

}
