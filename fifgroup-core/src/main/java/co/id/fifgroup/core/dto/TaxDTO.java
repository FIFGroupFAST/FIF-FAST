package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaxDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String taxCode;
	private BigDecimal amount;
	private BigDecimal cummAmount;
	private BigDecimal taxAmount;
	
	public TaxDTO() {
		super();
	}
	public TaxDTO(String taxCode, BigDecimal amount, BigDecimal cummAmount,
			BigDecimal taxAmount) {
		super();
		this.taxCode = taxCode;
		this.amount = amount;
		this.cummAmount = cummAmount;
		this.taxAmount = taxAmount;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getCummAmount() {
		return cummAmount;
	}
	public void setCummAmount(BigDecimal cummAmount) {
		this.cummAmount = cummAmount;
	}
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
}
