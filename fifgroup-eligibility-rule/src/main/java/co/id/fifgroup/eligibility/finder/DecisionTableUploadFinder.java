package co.id.fifgroup.eligibility.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.eligibility.dto.DecisionTableStageDTO;

public interface DecisionTableUploadFinder {

	List<DecisionTableStageDTO> findByUploadId(@Param("modulePrefix") String modulePrefix, @Param("id") Long uploadId);
	
}
