package co.id.fifgroup.basicsetup.dto;

import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.domain.OtherInfoDetail;
import co.id.fifgroup.basicsetup.domain.OtherInfoHeader;

public class OtherInfoDetailDTO extends OtherInfoDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9207558486244871296L;
	private LookupHeader lookupHeader;
	private OtherInfoHeader otherInfoHeader;
	
	public LookupHeader getLookupHeader() {
		return lookupHeader;
	}
	public void setLookupHeader(LookupHeader lookupHeader) {
		this.lookupHeader = lookupHeader;
	}
	public OtherInfoHeader getOtherInfoHeader() {
		return otherInfoHeader;
	}
	public void setOtherInfoHeader(OtherInfoHeader otherInfoHeader) {
		this.otherInfoHeader = otherInfoHeader;
	}
	
}
