package co.id.fifgroup.core.domain.transaction;

public interface CommonLeaveTrx extends CommonTrx {

	public Long getLeaveType();
	public String getOrganizationName();
	public String getEmployeeNumber();
	public String getEmployeeName();
	public String getLeaveTypeName();
	
}
