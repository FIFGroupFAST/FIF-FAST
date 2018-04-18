package co.id.fifgroup.basicsetup.dto;


import java.util.Date;
import java.util.List;

import co.id.fifgroup.basicsetup.domain.FormulaParameter;
import co.id.fifgroup.core.version.Version;

public class FormulaDTO extends Version{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8122400660047407478L;
	private String formulaName;
	private String description;
	private String userName;
	private String returnType;
	private String formula;
	private List<FormulaParameter> listFormulaParameter;
	private Date effectiveOnDate;
	private String formulaNameLike;
	
	public String getFormulaName() {
		return formulaName;
	}
	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}
	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}
	@Override
	public String toString() {
		return " userName="
				+ userName + ", returnType=" + returnType + ", formula="
				+ formula + ", listFormulaParameter=" + listFormulaParameter
				+ ", getVersionNumber()=" + getVersionNumber() + "]";
	}
	public List<FormulaParameter> getListFormulaParameter() {
		return listFormulaParameter;
	}
	public void setListFormulaParameter(List<FormulaParameter> listFormulaParameter) {
		this.listFormulaParameter = listFormulaParameter;
	}
	
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFormulaNameLike() {
		return formulaNameLike;
	}
	public void setFormulaNameLike(String formulaNameLike) {
		this.formulaNameLike = formulaNameLike;
	}

}
