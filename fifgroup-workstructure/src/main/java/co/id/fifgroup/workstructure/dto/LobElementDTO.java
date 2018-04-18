package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import co.id.fifgroup.workstructure.domain.LobElement;

public class LobElementDTO extends LobElement implements Serializable {

	private static final long serialVersionUID = 1L;

	public LobElementDTO() { }
	
	private String product;
	private Integer rowNum;
	
	public LobElementDTO(String productCode, BigDecimal percentage) {
		setProductCode(productCode);
		setPercentage(percentage);
	}	
	

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	@Override
	public String toString() {
		return "LobElementDTO [product=" + product + ", rowNum=" + rowNum
				+ ", getProductCode()=" + getProductCode()
				+ ", getPercentage()=" + getPercentage() + "]";
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((getProductCode() == null) ? 0 : getProductCode().hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		LobElementDTO other = (LobElementDTO) obj;
//		if (getProductCode() == null) {
//			if (other.getProductCode() != null)
//				return false;
//		} else if (!getProductCode().equals(other.getProductCode()))
//			return false;
//		return true;
//	}
	
	

	
	
}
