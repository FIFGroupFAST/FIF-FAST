package co.id.fifgroup.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaxRate implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal endAmount;
	private BigDecimal startAmount;
	private String taxName;
	private float taxRate;
	
	public BigDecimal getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}
	public BigDecimal getStartAmount() {
		return startAmount;
	}
	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	public float getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}
	@Override
	public String toString() {
		return "TaxRate [endAmount=" + endAmount + ", startAmount="
				+ startAmount + ", taxName=" + taxName + ", taxRate=" + taxRate
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endAmount == null) ? 0 : endAmount.hashCode());
		result = prime * result
				+ ((startAmount == null) ? 0 : startAmount.hashCode());
		result = prime * result + ((taxName == null) ? 0 : taxName.hashCode());
		result = prime * result + Float.floatToIntBits(taxRate);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaxRate other = (TaxRate) obj;
		if (endAmount == null) {
			if (other.endAmount != null)
				return false;
		} else if (!endAmount.equals(other.endAmount))
			return false;
		if (startAmount == null) {
			if (other.startAmount != null)
				return false;
		} else if (!startAmount.equals(other.startAmount))
			return false;
		if (taxName == null) {
			if (other.taxName != null)
				return false;
		} else if (!taxName.equals(other.taxName))
			return false;
		if (Float.floatToIntBits(taxRate) != Float
				.floatToIntBits(other.taxRate))
			return false;
		return true;
	}	
}
