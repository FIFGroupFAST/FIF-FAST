package co.id.fifgroup.notification.test;

import java.util.List;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.id.fifgroup.notification.constant.MessageStatus;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.manager.NotificationManager;

public class TestNotificationMessage {

	ApplicationContext ctx = new ClassPathXmlApplicationContext("notification-context.xml");
	NotificationManager engine = (NotificationManager) ctx.getBean("notificationManager");
	
//	@Test
//	public void insertNewMessage() throws Exception {
//		UUID fromId = UUID.fromString("11111111-1111-1111-1111-111111111111");
//		UUID toId = UUID.fromString("11111111-1112-1111-1111-111111111111");
//		NotificationMessage notificationMessage = new NotificationMessage();
//		notificationMessage.setFromId(fromId);
//		notificationMessage.setToId(toId);
//		notificationMessage.setReceivedTime(new Date());
//		notificationMessage.setSubject("subject message");
//		notificationMessage.setContent("content message");
//		engine.insertNewMessage(notificationMessage);
//	}
	
	
	@Test
	public void getMessageByToId() throws Exception {
		UUID toId = UUID.fromString("11111111-1112-1111-1111-111111111111");
		List<NotificationMessage> messageList = engine.getMessageByToId(toId);
		Assert.assertEquals(3, messageList.size());
		
		int countUnreadMessageBefore = engine.countUnreadMessage(toId);
		Assert.assertEquals(3, countUnreadMessageBefore);
		
		engine.updateMessageStatus(messageList.get(0).getMessageId(), MessageStatus.READ.getCode());
		
		int countUnreadMessageAfter = engine.countUnreadMessage(toId);
		Assert.assertEquals(3, countUnreadMessageAfter);
	}
	
	
}
