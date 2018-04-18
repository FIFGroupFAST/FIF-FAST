package co.id.fifgroup.basicsetup.dto;

import co.id.fifgroup.basicsetup.domain.SequenceGenerator;


public class SequenceGeneratorDTO extends SequenceGenerator{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2832025251971955707L;
	
	private String trxType;
	private String userName;
	private String companyName;
	private String format;
	
	public String getTrxType() {
		return trxType;
	}
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	

}
