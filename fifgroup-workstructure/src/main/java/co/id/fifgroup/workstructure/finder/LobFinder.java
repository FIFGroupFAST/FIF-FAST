package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.workstructure.dto.LobDTO;

public interface LobFinder {
	
	List<LobDTO> findByExample(LobDTO jobLobDto);
	List<Long> findHistoriesById(@Param("jobId") Long jobId, @Param("organizationId") Long organizationId);
	LobDTO findCurrentHistoryById(@Param("jobId") Long jobId, @Param("organizationId") Long organizationId);
	LobDTO findByIdAndHistroyId(@Param("id") Long id, @Param("jobId") Long jobId, @Param("organizationId") Long organizationId);
	LobDTO findById(@Param("id") Long id);
	Integer isFutureExist(@Param("organizationId") Long organizationId, @Param("jobId") Long jobId);
}
