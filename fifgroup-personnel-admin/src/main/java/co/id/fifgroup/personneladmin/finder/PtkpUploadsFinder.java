package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.PtkpStageDTO;
import co.id.fifgroup.personneladmin.dto.PtkpUploadsDTO;

public interface PtkpUploadsFinder {

	public List<PtkpUploadsDTO> getPtkpUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") String batchNumberFrom, @Param("batchNumberTo") String batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public List<PtkpUploadsDTO> getPtkpBatchUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") Integer batchNumberFrom, @Param("batchNumberTo") Integer batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public List<PtkpStageDTO> getStageObjectByUploadId(@Param("uploadId") Long uploadId);
}
