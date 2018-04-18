package co.id.fifgroup.core.constant;

public enum TerminationReason {
	MUTASI_AFFCO_OUT("MUTASI AFFCO OUT"),
	TRANSFER_OUT_WITHIN_GROUP("TRANSFER OUT WITHIN GROUP"),
	PROBATION_FAILED("PROBATION FAILED"),
	INTERNAL_MARIED("INTERNAL MARIED"),
	MENGUNDURKAN_DIRI("MENGUNDURKAN DIRI"),
	TRAINEE_FAILED("TRAINEE FAILED"),
	PENINGKATAN_DARI_SP_3("PENINGKATAN DARI SP 3");
	
	private String value;
	
	private TerminationReason(String value){
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
