package co.id.fifgroup.notification.dao;

import java.util.List;

import co.id.fifgroup.notification.domain.TemplateMessage;

public interface TemplateMessageDAO {

	public void insertNewTemplate(TemplateMessage templateMessage) throws Exception;
	
	public List<TemplateMessage> getAllTemplate() throws Exception;
	
	public TemplateMessage getTemplateByName(String name) throws Exception;
	
	public TemplateMessage getTemplateById(Long templateId) throws Exception;
	
	public TemplateMessage getTemplateByNameAndId(String templateName, Long templateId) throws Exception;
	
	public void updateTemplate(TemplateMessage templateMessage) throws Exception;
	
	public int countTemplateByName(String name) throws Exception;
	
}
