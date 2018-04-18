package co.id.fifgroup.basicsetup.dto;

import co.id.fifgroup.basicsetup.domain.LookupDependent;

public class LookupDependentDetailDTO extends LookupDependent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6966067644702060048L;
	private LookupDependent parentDetail;
	
	public LookupDependent getParentDetail() {
		return parentDetail;
	}
	
	public void setParentDetail(LookupDependent parentDetail) {
		this.parentDetail = parentDetail;
	}
	
}
