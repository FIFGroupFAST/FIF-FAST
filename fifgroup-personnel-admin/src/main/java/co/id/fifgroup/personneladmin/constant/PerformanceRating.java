package co.id.fifgroup.personneladmin.constant;

public enum PerformanceRating {
	KURANG("K", 1L, "Kurang"),
	CUKUP("C", 2L, "Cukup"),
	BAIK_MINUS("B-", 3L, "Baik Minus"),
	BAIK("B", 4L, "Baik"),
	BAIK_SEKALI_MINUS("BS-", 5L, "Baik Sekali Minus"),
	BAIK_SEKALI("BS-", 6L, "Baik Sekali"),
	BAIK_SEKALI_PLUS("BS+", 7L, "Baik Sekali Plus"),
	ISTIMEWA("IST", 8L, "Istimewa");	
	
	private String code;
	private Long value;
	private String description;
	
	private PerformanceRating(String code, Long value, String description) {
		this.code = code;
		this.value = value;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static Long getValueByCode(String code) {
		for (PerformanceRating pr : PerformanceRating.values()) {
			if (pr.getCode().equals(code)) {
				return pr.getValue();
			}
		}
		return null;
	}
}
