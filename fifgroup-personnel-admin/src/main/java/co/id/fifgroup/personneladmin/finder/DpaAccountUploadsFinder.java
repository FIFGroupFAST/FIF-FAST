package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.DpaAccountStageDTO;
import co.id.fifgroup.personneladmin.dto.DpaAccountUploadsDTO;

public interface DpaAccountUploadsFinder {

	public List<DpaAccountUploadsDTO> getDpaAccountUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") String batchNumberFrom, @Param("batchNumberTo") String batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public List<DpaAccountStageDTO> getStageObjectByUploadId(@Param("uploadId") Long uploadId);
	
	public List<DpaAccountUploadsDTO> getDpaAccountBatchUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") Integer batchNumberFrom, @Param("batchNumberTo") Integer batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
}
