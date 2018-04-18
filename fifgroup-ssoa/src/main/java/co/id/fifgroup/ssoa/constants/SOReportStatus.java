package co.id.fifgroup.ssoa.constants;

import co.id.fifgroup.personneladmin.constant.AssignmentStatus;

public enum SOReportStatus {
	CODE("SO_REPORT_STATUS","SO Report Status"),
	ON_APPROVAL("SO_REPORT_ON_APPROVAL", "On Approval"), 
	APPROVED("SO_REPORT_APPROVED", "Approved"),
	REJECTED("SO_REPORT_REJECTED", "Revised");

	private String code;
	private String value;

	private SOReportStatus(String code, String value) {
		this.setCode(code);
		this.setValue(value);
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static String getValueByCode(String code) {
		for(SOReportStatus as : SOReportStatus.values()) {
			if(as.getCode().equalsIgnoreCase(code)) {
				return as.getValue();
			}
		}
		return null;
	}
}
