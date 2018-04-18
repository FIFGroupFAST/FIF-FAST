package co.id.fifgroup.core.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.constant.UploadErrorStatus;
import co.id.fifgroup.core.domain.UploadElement;
import co.id.fifgroup.core.domain.UploadError;

public interface UploadErrorFinder {

	public void insert(@Param("tableName") String tableName, @Param("sequenceName") String sequenceName, @Param("uploadError") UploadError uploadError);
	public void deleteByExample(@Param("tableName") String tableName, @Param("criteria") UploadError criteria);
	public void deleteByUploadElements(@Param("tableName") String tableName, @Param("uploadElements") List<UploadElement> uploadElements);
	public List<UploadError> selectByExample(@Param("tableName") String tableName, @Param("criteria") UploadError citeria);
	public int countByExample(@Param("tableName") String tableName, @Param("criteria") UploadError citeria);
	public void updateStatusToClosedUploadErrorByUploadId(@Param("tableName") String tableName, @Param("uploadId") Long uploadId);
	public void deleteUploadErrorByUploadId(@Param("tableName") String tableName, @Param("uploadId") Long uploadId);
	public List<UploadError> getUploadError(@Param("tableNameUploadElement") String tableNameUploadElement, @Param("tableNameUploadError") String tableNameUploadError, @Param("batchNumber") String batchNumber, @Param("status") UploadErrorStatus uploadErrorStatus);
	
	//START ITSM 15052708450385 by: HS
	//menambahkan method untuk insert bulk ke database
	public void insertBulk(@Param("tableName") String tableName, @Param("sequenceName") String sequenceName, @Param("uploadErrors") List<UploadError> uploadErrors);
	//END ITSM 15052708450385
}
