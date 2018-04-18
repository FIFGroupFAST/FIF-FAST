package co.id.fifgroup.notification.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import co.id.fifgroup.notification.domain.NotificationMessage;

public interface NotificationMessageMapper {

	public List<NotificationMessage> getMessageByToId(Map<String, Object> parameterMap) throws Exception;
	public List<NotificationMessage> getMessageByToIdPagging(Map<String, Object> parameterMap) throws Exception;
	public List<NotificationMessage> getAllNotificationMessage() throws Exception;
	public NotificationMessage getNotificationMessageByMessageId(Map<String, Object> parameterMap) throws Exception;
	
	public int countUnreadMessage(Map<String, Object> parameterMap) throws Exception;
	public int countAllMessage(Map<String, Object> parameterMap) throws Exception;
	
	public void updateMessageStatus(Map<String, Object> parameterMap) throws Exception;
	
	public void insertNewMessage(NotificationMessage notificationMessage) throws Exception;
	
	public void deleteNotificationMessageByMessageId(Map<String, Object> parameterMap) throws Exception;
}
