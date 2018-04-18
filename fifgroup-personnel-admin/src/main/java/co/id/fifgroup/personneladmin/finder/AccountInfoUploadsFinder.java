package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.AccountInfoStageDTO;
import co.id.fifgroup.personneladmin.dto.AccountInfoUploadsDTO;

public interface AccountInfoUploadsFinder {

	public List<AccountInfoUploadsDTO> getAccountInfoUploads(@Param("companyId") Long companyId, @Param("accountType") Long accountType, @Param("batchNumberFrom") String batchNumberFrom, 
			@Param("batchNumberTo") String batchNumberTo, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public List<AccountInfoUploadsDTO> getAccountInfoBatchUploads(@Param("companyId") Long companyId, @Param("accountType") Long accountType, @Param("batchNumberFrom") Integer batchNumberFrom, 
			@Param("batchNumberTo") Integer batchNumberTo, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public List<AccountInfoStageDTO> getStageObjectByUploadId(@Param("uploadId") Long uploadId);
}
