package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;

public class JournalBalance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String invoiceNumber;
	private Integer resultValue;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Integer getResultValue() {
		return resultValue;
	}

	public void setResultValue(Integer resultValue) {
		this.resultValue = resultValue;
	}

}
