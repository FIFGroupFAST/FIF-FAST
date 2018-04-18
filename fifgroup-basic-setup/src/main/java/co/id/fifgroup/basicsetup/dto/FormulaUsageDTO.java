package co.id.fifgroup.basicsetup.dto;

import co.id.fifgroup.basicsetup.domain.Formula;
import co.id.fifgroup.basicsetup.domain.FormulaUsage;

public class FormulaUsageDTO extends FormulaUsage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -380034159708062586L;
	private Formula formula;
	
	public Formula getFormula() {
		return formula;
	}
	
	public void setFormula(Formula formula) {
		this.formula = formula;
	}
	
}
