package co.id.fifgroup.workstructure.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.workstructure.dto.BranchSizeDTO;

public interface BranchSizeSetupService {
	
	void save(BranchSizeDTO branchSizeDto, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;
	void update(BranchSizeDTO branchSizeDto);
	void insertNewHistory(BranchSizeDTO object);
	void delete(BranchSizeDTO branchSizeDto);
	List<BranchSizeDTO> findByExample(BranchSizeDTO branchSizeDto);
	List<Long> findHistoriesById(Long organizationId);
	BranchSizeDTO findByIdAndHistoryId(Long id, Long organizationId);
	Boolean isFutureExist(Long organizationId);
}
