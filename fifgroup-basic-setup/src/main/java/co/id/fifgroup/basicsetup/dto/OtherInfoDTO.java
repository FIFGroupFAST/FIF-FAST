package co.id.fifgroup.basicsetup.dto;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.OtherInfoHeader;

public class OtherInfoDTO extends OtherInfoHeader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5749051688120495054L;
	private List<OtherInfoDetailDTO> otherInfoDetail;
	private boolean validOtherInfo;
	
	public List<OtherInfoDetailDTO> getOtherInfoDetail() {
		return otherInfoDetail;
	}
	public void setOtherInfoDetail(List<OtherInfoDetailDTO> otherInfoDetail) {
		this.otherInfoDetail = otherInfoDetail;
	}
	public boolean isValidOtherInfo() {
		return validOtherInfo;
	}
	public void setValidOtherInfo(boolean validOtherInfo) {
		this.validOtherInfo = validOtherInfo;
	}

	
}
