package co.id.fifgroup.notification.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import co.id.fifgroup.notification.domain.TemplateMessage;

public interface TemplateMessageMapper {

	public List<TemplateMessage> getAllTemplate() throws Exception;
	
	public TemplateMessage getTemplateByName(Map<String, Object> parameterMap) throws Exception;

	public TemplateMessage getTemplateById(Map<String, Object> parameterMap) throws Exception;
	
	public void insertNewTemplate(TemplateMessage templateMessage) throws Exception;
	
	public void updateTemplate(TemplateMessage templateMessage) throws Exception;
	
	public int countTemplateByName(String name) throws Exception;
	
	public TemplateMessage getTemplateByNameAndId(Map<String, Object> parameterMap) throws Exception;
}
