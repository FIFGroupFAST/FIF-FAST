package co.id.fifgroup.core.ui.lookup;

class StaticLookupCriteria implements LookupCriteria {

	private static final long serialVersionUID = -5297231918383429301L;
	
	private String lookupName;
	private String parentDetailCode;
	
	public StaticLookupCriteria(String lookupName, String parentDetailCode) {
		this.lookupName = lookupName;
		this.parentDetailCode = parentDetailCode;
	}
	
	@Override
	public String getLookupName() {
		return lookupName;
	}

	@Override
	public String getParentDetailCode() {
		return parentDetailCode;
	}

}
