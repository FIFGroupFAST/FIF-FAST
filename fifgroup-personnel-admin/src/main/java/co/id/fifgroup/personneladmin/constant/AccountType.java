package co.id.fifgroup.personneladmin.constant;

public enum AccountType {

	SELECT(null, "- Select - "), 
	DPA(2L, "Dana Pensiun Astra"), 
	INSURANCE(1L, "Insurance"), 
	JAMSOSTEK(3L, "JAMSOSTEK");

	private Long code;
	private String description;

	private AccountType(Long code, String description) {
		this.code = code;
		this.description = description;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static AccountType getDescriptionByCode(Long code) {
		for (AccountType accountType : AccountType.values()) {
			if (accountType.getCode() != null) {
				if (accountType.getCode().equals(code)) {
					return accountType;
				}				
			}
		}
		return null;
	}

}
