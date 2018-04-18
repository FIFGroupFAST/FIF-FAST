package co.id.fifgroup.systemworkflow.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.systemworkflow.domain.TemplateMessageMapping;
import co.id.fifgroup.systemworkflow.dto.TemplateMessageMappingDTO;

public interface TemplateMessageFinderMapper {
	
	public TemplateMessageMapping getTemplateMessageByCriteria(@Param("scope") Long scope, @Param("avmId") UUID avmId, @Param("transactionId") Long transactionId, 
			@Param("levelApproval") Long levelApproval, @Param("actionType") String actionType, @Param("currentDate") Date currentDate, @Param("priority") Long priority);
	
	public List<TemplateMessageMappingDTO> getTemplateMessageMapping(@Param("trxType") Long trxType, @Param("actionType") String actionType);
}
