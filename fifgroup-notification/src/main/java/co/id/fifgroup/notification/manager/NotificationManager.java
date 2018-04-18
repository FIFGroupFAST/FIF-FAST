package co.id.fifgroup.notification.manager;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.notification.dao.NotificationMessageDAO;
import co.id.fifgroup.notification.dao.TemplateMessageDAO;
import co.id.fifgroup.notification.dao.mybatis.MyBatisNotificationMessageDAO;
import co.id.fifgroup.notification.dao.mybatis.MyBatisTemplateMessageDAO;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.notification.util.FreemarkerConfiguration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class NotificationManager {

	private static final Logger logger = LoggerFactory.getLogger(NotificationManager.class);
	
	private TemplateMessageDAO templateMessageDAO;
	private NotificationMessageDAO notificationMessageDAO;
	
	public NotificationManager() {
		templateMessageDAO = new MyBatisTemplateMessageDAO();
		notificationMessageDAO = new MyBatisNotificationMessageDAO();
	}

	public TemplateMessageDAO getTemplateMessageDAO() {
		return templateMessageDAO;
	}

	public void setTemplateMessageDAO(
			TemplateMessageDAO templateMessageDAO) {
		this.templateMessageDAO = templateMessageDAO;
	}

	public NotificationMessageDAO getNotificationMessageDAO() {
		return notificationMessageDAO;
	}

	public void setNotificationMessageDAO(
			NotificationMessageDAO notificationMessageDAO) {
		this.notificationMessageDAO = notificationMessageDAO;
	}
	
	// Template Message
	public List<TemplateMessage> getAllTemplate() throws Exception {
		try{
			return templateMessageDAO.getAllTemplate();
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}

	public TemplateMessage getTemplateByName(String name)
			throws Exception {
		try{
			return templateMessageDAO.getTemplateByName(name);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}
	
	public void insertNewTemplate(TemplateMessage templateMessage)
			throws Exception {
		try{
			templateMessageDAO.insertNewTemplate(templateMessage);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		}
	}
	
	public TemplateMessage getTemplateById(Long templateId) throws Exception {
		try{
			return templateMessageDAO.getTemplateById(templateId);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}
	
	public TemplateMessage getTemplateByNameAndId(String templateName, Long templateId) throws Exception {
		try {
			return templateMessageDAO.getTemplateByNameAndId(templateName, templateId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	public void updateTemplate(TemplateMessage templateMessage)
			throws Exception {
		try{
			templateMessageDAO.updateTemplate(templateMessage);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		}
	}
	
	public int countTemplateMessage(String name) throws Exception {
		try {
			return templateMessageDAO.countTemplateByName(name);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		}
	}
	// end template message
	
	// notification message
	public List<NotificationMessage> getMessageByToId(UUID toId) throws Exception {
		try{
			return notificationMessageDAO.getMessageByToId(toId);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}
	
	public List<NotificationMessage> getMessageByToId(UUID toId, int limit, int offset) throws Exception {
		try{
			return notificationMessageDAO.getMessageByToId(toId, limit, offset);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}

	public int countUnreadMessage(UUID toId)
			throws Exception {
		try{
			return notificationMessageDAO.countUnreadMessage(toId);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}
	
	public int countAllMessage(UUID toId)
			throws Exception {
		try{
			return notificationMessageDAO.countAllMessage(toId);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}
	
	public List<NotificationMessage> getMessageByTolId(UUID toId,
			UUID jobId, UUID roleId) throws Exception {
		try{
			return notificationMessageDAO.getMessageByToId(toId, jobId, roleId);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}

	public int countUnreadMessage(UUID approvalId, UUID jobId, UUID roleId)
			throws Exception {
		try{
			return notificationMessageDAO.countUnreadMessage(approvalId, jobId, roleId);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}

	public void updateMessageStatus(Long messageId, int messageStatus) throws Exception {
		try{
			notificationMessageDAO.updateMessageStatus(messageId, messageStatus);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}

	public void insertNewMessage(NotificationMessage notificationMessage)
			throws Exception {
		try{
			notificationMessageDAO.insertNewMessage(notificationMessage);
		} catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			throw ex;
		} 
	}
	
	public List<NotificationMessage> getAllNotificationMessage() throws Exception{
		try {
			return notificationMessageDAO.getAllNotificationMessage();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	public NotificationMessage getNotificationMessageByMessageId(Long messageId) throws Exception {
		try {
			return notificationMessageDAO.getNotificationMessageByMessageId(messageId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	// end notification message
	
	public Message templateMessageResolver(TemplateMessage templateMessage, Map dataModel){
		Template template;
		Message message = new Message();
		try {
			template = new Template("resolverSubject", new StringReader(templateMessage.getTemplateSubject()), FreemarkerConfiguration.getConfiguration());
			Writer outputSubject = new StringWriter();
			template.process(dataModel, outputSubject);
			message.setSubject(outputSubject.toString());
			
			template = new Template("resolverContent", new StringReader(templateMessage.getTemplateContent()), FreemarkerConfiguration.getConfiguration());
			Writer outputContent = new StringWriter();
			template.process(dataModel, outputContent);
			message.setContent(outputContent.toString());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (TemplateException e) {
			logger.error(e.getMessage(), e);
		}
		return message;
	}

	public void deleteMessage(Long messageId) throws Exception {
		notificationMessageDAO.deleteMessage(messageId);
	}
	
}
