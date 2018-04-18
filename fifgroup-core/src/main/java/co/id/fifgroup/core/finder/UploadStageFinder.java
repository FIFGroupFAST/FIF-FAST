package co.id.fifgroup.core.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.UploadElement;

public interface UploadStageFinder {

	public void deleteByUploadElements(@Param("tableName") String tableName, @Param("uploadElements") List<UploadElement> uploadElements);
	public int countByUploadId(@Param("tableName") String tableName, @Param("uploadId") Long uploadId);
	public void updateStatusToClosedUploadStageByUploadId(@Param("tableName") String tableName, @Param("uploadId") Long uploadId);
	
}
