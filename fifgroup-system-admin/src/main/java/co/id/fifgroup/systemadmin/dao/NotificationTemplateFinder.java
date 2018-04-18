package co.id.fifgroup.systemadmin.dao;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.PersonDTO;

import co.id.fifgroup.systemadmin.dto.NotificationTemplateDTO;

public interface NotificationTemplateFinder {
	
	public NotificationTemplateDTO getTemplateByType(String transactionType);
	
	public PersonDTO getReceiverName(@Param("personId")Long personId);
}
