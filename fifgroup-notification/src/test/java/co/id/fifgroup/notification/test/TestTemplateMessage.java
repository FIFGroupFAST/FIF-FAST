package co.id.fifgroup.notification.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.notification.manager.NotificationManager;

public class TestTemplateMessage {

	ApplicationContext ctx = new ClassPathXmlApplicationContext("notification-context.xml");
	NotificationManager engine = (NotificationManager) ctx.getBean("notificationManager");
	
//	@Test
//	public void insertNewTemplate() throws Exception {
//		TemplateMessage templateMessage = new TemplateMessage();
//		templateMessage.setName("test template 1");
//		templateMessage.setTemplateSubject("test template subject 1");
//		templateMessage.setTemplateContent("test template content 1");
//		engine.insertNewTemplate(templateMessage);
//	}
	
	@Test
	public void getAllTemplate() throws Exception {
		List<TemplateMessage> templateList = engine.getAllTemplate();
		Assert.assertEquals(4, templateList.size());
	}
	
	@Test
	public void getTemplateByName() throws Exception {
		TemplateMessage templateMessages = engine.getTemplateByName("%test%");
		Assert.assertEquals(4, templateMessages);
	}
	
	@Test
	public void getTemplateById() throws Exception {
		TemplateMessage templateMessage = engine.getTemplateById((long)1);
		Assert.assertEquals("test request", templateMessage.getTemplateName());
	}
}
