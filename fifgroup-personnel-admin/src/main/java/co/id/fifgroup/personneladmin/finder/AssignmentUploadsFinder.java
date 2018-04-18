package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.AssignmentStageDTO;
import co.id.fifgroup.personneladmin.dto.AssignmentUploadsDTO;

import co.id.fifgroup.core.domain.BatchUpload;

public interface AssignmentUploadsFinder {
	
	public List<AssignmentUploadsDTO> getAssignmentUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") String batchNumberFrom, @Param("batchNumberTo") String batchNumberTo,
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public List<AssignmentStageDTO> getStageObjectByUploadId(@Param("uploadId") Long uploadId);
	
	public List<AssignmentUploadsDTO> getAssignmentBatchUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") Integer batchNumberFrom, @Param("batchNumberTo") Integer batchNumberTo,
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public int isGradeValid(@Param("jobId")Long jobId, @Param("companyId")Long companyId, @Param("grade")String grade, @Param("subGrade")String subGrade);
	
	public void updateBatchUpload(BatchUpload batchUpload);
}
