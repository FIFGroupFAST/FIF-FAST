package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.WorkEquipmentStageDTO;
import co.id.fifgroup.personneladmin.dto.WorkEquipmentUploadsDTO;

public interface WorkEquipmentUploadsFinder {

	public List<WorkEquipmentUploadsDTO> getWorkEquipmentUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") String batchNumberFrom, @Param("batchNumberTo") String batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public List<WorkEquipmentStageDTO> getStageObjectByUploadId(@Param("uploadId") Long uploadId);
	
	public List<WorkEquipmentUploadsDTO> getWorkEquipmentBatchUploads(@Param("companyId") Long companyId, @Param("batchNumberFrom") Integer batchNumberFrom, @Param("batchNumberTo") Integer batchNumberTo, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
}
