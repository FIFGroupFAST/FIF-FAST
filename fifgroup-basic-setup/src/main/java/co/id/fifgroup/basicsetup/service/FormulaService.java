package co.id.fifgroup.basicsetup.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.basicsetup.domain.Formula;
import co.id.fifgroup.basicsetup.domain.FormulaExample;
import co.id.fifgroup.basicsetup.dto.FormulaDTO;
import co.id.fifgroup.core.version.VersioningService;

public interface FormulaService extends VersioningService<FormulaDTO>{
	public void save(FormulaDTO formulaDto) throws Exception;
	public int countByExample(FormulaExample example);
	public int getCountFormulaDtoByFormulaNameAndDescription(String formulaName, String description);
	public List<FormulaDTO> getFormulaDtoByFormulaNameAndDescription(String formulaName, String description);
	public List<FormulaDTO> getFormulaDtoByExample(FormulaDTO example);
	public int getCountFormulaByExample(FormulaDTO example);
	public List<Formula> getFormulaByExample(FormulaExample formulaExample, int limit, int offset);
	public List<Formula> getFormulaByExample(FormulaExample formulaExample);
	public FormulaDTO getFormulaDtoById(Long id);
	public FormulaDTO getFormulaDtoByIdAndVersionNumber(Long id, int currentVersionNumber);
	public int getCurrentFormulaVersionByFormulaId(Long id);
	public FormulaDTO getCurrentFormulaDto(Long formulaId);
	public FormulaDTO getLastFormulaDto(Long formulaId);
	public int getCountFormulaVersionByFormulaId(Long formulaId);
	public FormulaDTO getFormulaDtoByIdAndDate(Long id, Date effectiveDate);
	public List<FormulaDTO> getFormulaHeaderDtoByExample(FormulaDTO example, int limit, int offset);
	public int getCountFormulaByIdAndDate(Long id, Date effectiveDate);
	
}
