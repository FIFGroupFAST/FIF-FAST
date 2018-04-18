package co.id.fifgroup.systemadmin.finder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.dto.AuditTrailDTO;

public interface AuditFinder {

	int countAuditByExample(@Param("dateFrom")  Date dateFrom, @Param("dateTo") Date dateTo, @Param("moduleId") BigDecimal moduleId,
			@Param("transactionType") String transactionType, @Param("userName") String userName);
	
	List<AuditTrailDTO> findAuditByExample(@Param("dateFrom")  Date dateFrom, @Param("dateTo") Date dateTo, @Param("moduleId") BigDecimal moduleId,
			@Param("transactionType") String transactionType, @Param("userName") String userName, RowBounds rowBounds);
}
