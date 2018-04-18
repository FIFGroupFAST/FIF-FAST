package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.AwardStageDTO;
import co.id.fifgroup.personneladmin.dto.AwardUploadsDTO;

public interface AwardUploadsFinder {

	public List<AwardUploadsDTO> getAwardUploads(@Param("companyId") Long companyId, 
			@Param("batchNumberFrom") String batchNumberFrom, 
			@Param("batchNumberTo") String batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public List<AwardStageDTO> getStageObjectByUploadId(@Param("uploadId") Long uploadId);
	
	public List<AwardUploadsDTO> getAwardBatchUploads(@Param("companyId") Long companyId, 
			@Param("batchNumberFrom") Integer batchNumberFrom, 
			@Param("batchNumberTo") Integer batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
}
