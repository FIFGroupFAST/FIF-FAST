package co.id.fifgroup.basicsetup.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.basicsetup.common.DefaultParameterFormula;
import co.id.fifgroup.basicsetup.domain.FormulaParameter;

public interface FormulaUtilService {

	public <T> T executeFormula(String formulaName, Date effectiveOnDate, DefaultParameterFormula defaultParameterFormula, Class<T> clazz) throws Exception;
	
	public <T> T executeFormula(String formulaName, String script, List<FormulaParameter> formulaParameters, DefaultParameterFormula defaultParameterFormula, Class<T> clazz) throws Exception;
	
}
