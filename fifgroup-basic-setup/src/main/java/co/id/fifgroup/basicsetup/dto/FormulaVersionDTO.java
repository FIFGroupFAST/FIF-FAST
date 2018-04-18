package co.id.fifgroup.basicsetup.dto;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.basicsetup.domain.FormulaVersion;

public class FormulaVersionDTO extends FormulaVersion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5677153160725614165L;
	private List<FormulaParameter> formulaParameters;
	
	public List<FormulaParameter> getFormulaParameters() {
		return formulaParameters;
	}
	
	public void setFormulaParameters(List<FormulaParameter> formulaParameters) {
		this.formulaParameters = formulaParameters;
	}
	
}
