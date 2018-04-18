package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.DpaAmountStageDTO;
import co.id.fifgroup.personneladmin.dto.DpaAmountUploadsDTO;

public interface DpaAmountUploadsFinder {

	public List<DpaAmountUploadsDTO> getDpaAmountUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") String batchNumberFrom, @Param("batchNumberTo") String batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public List<DpaAmountStageDTO> getStageObjectByUploadId(@Param("uploadId") Long uploadId);
	
	public List<DpaAmountUploadsDTO> getDpaAmountBatchUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") Integer batchNumberFrom, @Param("batchNumberTo") Integer batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
}
