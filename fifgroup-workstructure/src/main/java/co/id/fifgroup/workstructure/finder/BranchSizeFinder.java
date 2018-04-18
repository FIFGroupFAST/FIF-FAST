package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.workstructure.dto.BranchSizeDTO;

public interface BranchSizeFinder {
	
	List<BranchSizeDTO> findByExample(BranchSizeDTO branchSizeDto);
	List<Long> findHistoriesById(@Param("organizationId") Long organizationId);
	BranchSizeDTO findCurrentHistoryById(@Param("organizationId") Long organizationId);
	BranchSizeDTO findByIdAndHistroyId(@Param("id") Long id, @Param("organizationId") Long organizationId);
	Integer isFutureExist(@Param("organizationId") Long organizationId);
}
