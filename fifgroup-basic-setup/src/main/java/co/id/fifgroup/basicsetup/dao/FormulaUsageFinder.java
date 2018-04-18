package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.basicsetup.dto.FormulaUsageDTO;

public interface FormulaUsageFinder {

	public List<FormulaUsageDTO> getFormulaUsageDtoByFormulaName(@Param("formulaName") String formulaName);
	public int getCountFormulaUsageDtoByFormulaName(@Param("formulaName") String formulaName);
	
}
