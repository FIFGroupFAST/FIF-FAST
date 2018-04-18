package co.id.fifgroup.notification.test;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.manager.NotificationManager;
import co.id.fifgroup.notification.manager.SendEmailManager;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
//@Transactional(readOnly=false)
@TransactionConfiguration(defaultRollback=false)
public class TestNotificationEmail {

	
	@Autowired SendEmailManager sendEmailManager;
	@Autowired NotificationManager notificationManager;

	@Test
	public void emailNotificationTest() throws Exception{
		UUID fromId = UUID.fromString("11111111-1111-1111-1111-111111111111");
		UUID toId = UUID.fromString("11111111-1112-1111-1111-111111111111");
		
		//Assert.assertEquals(1, messageList.size());
		
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setFromId(fromId);
		notificationMessage.setToId(toId);
		notificationMessage.setReceivedTime(new Date());
		notificationMessage.setSubject("subject message");
		notificationMessage.setContentMessage("content message");
		notificationManager.insertNewMessage(notificationMessage);
		
		
		sendEmailManager.sendEmail(notificationMessage, "out205");
	}
	
}
