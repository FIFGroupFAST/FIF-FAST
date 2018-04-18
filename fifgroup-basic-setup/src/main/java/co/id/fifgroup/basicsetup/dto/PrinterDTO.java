package co.id.fifgroup.basicsetup.dto;

import co.id.fifgroup.basicsetup.domain.Printer;

public class PrinterDTO extends Printer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4597295716882802352L;
	private Printer printer;
	private String userName;
	
	public Printer getPrinter() {
		return printer;
	}
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
