package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.FormulaVersion;
import co.id.fifgroup.basicsetup.domain.FormulaVersionExample;

public interface FormulaVersionService {
	public List<FormulaVersion> getFormulaVersionByExample(FormulaVersionExample example);
	public int getCountFormulaVersionByExample(FormulaVersionExample example);
//	public List<FormulaVersion> getFormulaVersionsByFormulaId(Long id);

}
