package co.id.fifgroup.basicsetup.dto;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.LookupHeader;

public class LookupDTO extends LookupHeader {

	/**
	 * 
	 */
	private static final long serialVersionUID = -127787890198823052L;
	private LookupHeader parentHeader;
	private List<LookupDependentDetailDTO> lookupDependentDetailDtos;
	private List<LookupDynamicDTO> lookupDynamicDtos;
	private String userName;
	private boolean validLookupDependent;
	
	public LookupHeader getParentHeader() {
		return parentHeader;
	}

	public void setParentHeader(LookupHeader parentHeader) {
		this.parentHeader = parentHeader;
	}
	
	public List<LookupDependentDetailDTO> getLookupDependentDetailDtos() {
		return lookupDependentDetailDtos;
	}

	public void setLookupDependentDetailDtos(
			List<LookupDependentDetailDTO> lookupDependentDetailDtos) {
		this.lookupDependentDetailDtos = lookupDependentDetailDtos;
	}

	public List<LookupDynamicDTO> getLookupDynamicDtos() {
		return lookupDynamicDtos;
	}

	public void setLookupDynamicDtos(List<LookupDynamicDTO> lookupDynamicDtos) {
		this.lookupDynamicDtos = lookupDynamicDtos;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isValidLookupDependent() {
		return validLookupDependent;
	}

	public void setValidLookupDependent(boolean validLookupDependent) {
		this.validLookupDependent = validLookupDependent;
	}
	
}
