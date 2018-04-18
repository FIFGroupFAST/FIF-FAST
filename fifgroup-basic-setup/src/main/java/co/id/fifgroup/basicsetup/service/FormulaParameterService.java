package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.domain.FormulaParameterExample;
import co.id.fifgroup.basicsetup.dto.FormulaParameterDTO;

public interface FormulaParameterService {

	public void save(FormulaParameter formulaParameter) throws Exception;

	public int countByExample(FormulaParameterExample formulaParameterExample);

	public int getCountFormulaParameterDtoByParameterName(String parameterName);
	public FormulaParameter getFormulaParameterByPrimaryKey(Long formulaParameterId);

	public List<FormulaParameterDTO> getFormulaDtoByFormulaParameterNameWithRowBounds(
			String parameterName, int limit, int offset);

	public List<FormulaParameter> getFormulaParameterByExample(
			FormulaParameterExample formulaParameterExample);

	public List<FormulaParameter> getFormulaParameterByExample(
			FormulaParameterExample formulaParameterExample, int limit,
			int offset);

	public FormulaParameter getFormulaParameterByParameterId(Long parameterId);

	public int getCountFormulaParameterDtoByParameterNameAndDescription(
			String parameterName, String description);

	public List<FormulaParameterDTO> getFormulaDtoByFormulaParameterNameAndDescription(
			String parameterName, String description);

}
