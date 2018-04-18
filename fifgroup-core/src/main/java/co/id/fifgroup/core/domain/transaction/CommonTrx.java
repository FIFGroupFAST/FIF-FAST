package co.id.fifgroup.core.domain.transaction;

import java.io.Serializable;
import java.util.UUID;

public interface CommonTrx extends Serializable {

	public Long getBranchType();
	public Long getJobId();
	public Long getGradeId();
	public Long getOrganizationId();
	public Long getLocationId();
	public String getJobGroup();
	public Long getCompanyId();
	public Long getBusinessGroupId();
	
	public Long getKeyJob();
	public String getEmploymentStatus();
	
	public UUID getObjectEmployeeUUID();
	public boolean isBasedLineRequestor();
	public Long getTransactionType();
	public int getTransactionApprovalStatus();
	public Long getOrganizationDestinationId();
}
