package co.id.fifgroup.core.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.ExitInterviewDocument;
import co.id.fifgroup.core.domain.TerminationDocument;
import co.id.fifgroup.core.dto.PersonTransferWithinGroupDTO;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface TerminationFinder {

	public List<PersonTransferWithinGroupDTO> selectByExample(PersonTransferWithinGroupDTO personTransferWithinGroupDTO);
	
	public List<KeyValue> getTerminationType(@Param("companyId") Long companyId);
	
	public List<TerminationDocument> getTerminationDocument(@Param("requestId") Long requestId);
	
	public int countTransactionTermination(@Param("personId") Long personId, @Param("companyId") Long companyId);
	
	public ExitInterviewDocument getExitInterviewDocument(@Param("requestId") Long requestId);
	
	public void updateStatusTermination(@Param("requestId") Long requestId, @Param("transferStatus") String transferStatus, 
			@Param("terminationStatus") String terminationStatus, @Param("lastUpdatedBy") Long lastUpdatedBy);
	
	public KeyValue getSourceDestinationTerminationCompanyByPersonAndCompanyId(@Param("personId")Long personId, @Param("destinationCompanyId")Long destinationCompanyId);
	
	public int countValidReverseTermination(@Param("requestId") Long requestId);
	
	public Date getTerminationDateById(Long requestId);
}
