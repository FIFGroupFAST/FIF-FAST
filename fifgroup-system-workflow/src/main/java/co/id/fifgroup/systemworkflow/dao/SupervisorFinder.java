package co.id.fifgroup.systemworkflow.dao;

import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.systemworkflow.dto.SupervisorDTO;

public interface SupervisorFinder {

	public Integer maxLevelSequence(@Param("approverType") String approverType, @Param("avmTrxId") UUID avmTrxId);
	
	public SupervisorDTO getPreviousSupervisor(@Param("approverType") String approverType, @Param("avmTrxId") UUID avmTrxId, @Param("levelSequence") int levelSequence, @Param("basedOn") int basedOn);
}
