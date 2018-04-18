package co.id.fifgroup.core.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.UploadElement;
import co.id.fifgroup.core.dto.UploadElementDTO;

public interface UploadElementFinder {

	public void insert(@Param("tableName") String tableName, @Param("sequenceName") String sequenceName, @Param("uploadElement") UploadElement uploadElement);
	public void updateById(@Param("tableName") String tableName, @Param("uploadElement") UploadElement uploadElement);
	public List<UploadElement> selectByExample(@Param("tableName") String tableName, @Param("criteria") UploadElement uploadElement);
	public List<UploadElementDTO> selectUploadElementDtoByExample(@Param("batchUploadTableName") String batchUploadTableName, @Param("uploadElementTableName") String uploadElementTableName, @Param("criteria") UploadElementDTO uploadElementDto);
	public UploadElementDTO selectUploadElementDtoById(@Param("batchUploadTableName") String batchUploadTableName, @Param("uploadElementTableName") String uploadElementTableName, @Param("uploadId") Long uploadId);
	public UploadElement getUploadElementById(@Param("uploadElementTableName") String uploadElementTableName, @Param("uploadId") Long uploadId);
	
}
