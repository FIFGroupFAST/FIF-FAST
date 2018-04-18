package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.FormulaUsage;
import co.id.fifgroup.basicsetup.domain.FormulaUsageExample;
import co.id.fifgroup.basicsetup.dto.FormulaUsageDTO;

public interface FormulaUsageService {

	public List<FormulaUsageDTO> getFormulaUsageDtoByFormulaName(String formulaName);
	public int getCountFormulaUsageDtoByFormulaName(String formulaName);
	public List<FormulaUsage> getFormulaUsageByExample(FormulaUsageExample formulaUsageExample);
	public int getCountFormulaUsageByExample(FormulaUsageExample formulaUsageExample);
	public void save(FormulaUsage formulaUsage);
	
}
