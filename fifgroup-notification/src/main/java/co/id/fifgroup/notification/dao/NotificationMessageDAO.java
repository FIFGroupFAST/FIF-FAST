package co.id.fifgroup.notification.dao;

import java.util.List;
import java.util.UUID;

import co.id.fifgroup.notification.domain.NotificationMessage;

public interface NotificationMessageDAO {

	public void insertNewMessage(NotificationMessage notificationMessage) throws Exception;

	public List<NotificationMessage> getMessageByToId(UUID toId) throws Exception;
	public List<NotificationMessage> getAllNotificationMessage() throws Exception;
	public NotificationMessage getNotificationMessageByMessageId(Long messageId) throws Exception;
	
	public int countUnreadMessage(UUID toId) throws Exception;	

	public void updateMessageStatus(Long messageId, int messageStatus) throws Exception;
	
	public List<NotificationMessage> getMessageByToId(UUID toId, UUID jobId, UUID roleId) throws Exception;	
	public List<NotificationMessage> getMessageByToId(UUID toId, int limit, int offset) throws Exception;
	
	public int countUnreadMessage(UUID toId, UUID jobId, UUID roleId) throws Exception;
	public int countAllMessage(UUID toId) throws Exception;
	
	public void deleteMessage(Long messageId) throws Exception;
}
