package co.id.fifgroup.core.domain.transaction;

public interface CommonIcpSubmissionTrx extends CommonTrx{

	public Long getBranchSource();
	
	public Long getJobDestinationId();
	
	public Long getJobSource();
	
	public Long getJobDestination();

}



