package co.id.fifgroup.systemadmin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.notification.constant.MessageType;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.notification.manager.NotificationManager;
import co.id.fifgroup.notification.manager.SendEmailManager;
import co.id.fifgroup.systemadmin.constant.NotifBy;
import co.id.fifgroup.systemadmin.constant.NotificationTransactionType;
import co.id.fifgroup.systemadmin.dao.NotificationTemplateFinder;
import co.id.fifgroup.systemadmin.dto.NotificationTemplateDTO;
import co.id.fifgroup.systemadmin.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	public static final String APPROVER_SYSTEM = "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF";
	
	@Autowired
	private NotificationTemplateFinder notificationTemplateFinder;
	@Autowired
	private NotificationManager notificationManager;
	@Autowired
	private PersonService personService;
	@Autowired
	private SendEmailManager sendEmailManager;
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Override
	public void sendNotification(NotificationTransactionType trnType, Long companyId, Long subjectPersonId, Long receiverPersonId, Object data, String parameterUrl) throws Exception {
		
		NotificationTemplateDTO template = notificationTemplateFinder.getTemplateByType(trnType.toString());
		
		if(template == null)
			throw new Exception("Error when send notification. Caused by Notification template for "+trnType+" was not defined");
		
		String[] notifTypes = template.getNotifBy().split("\\|");
		for (String notifBy : notifTypes) {
			if(notifBy.equals(NotifBy.EMAIL.toString())){
				sendToMail(template, subjectPersonId, companyId, data, receiverPersonId);
			}else if(notifBy.equals(NotifBy.DASHBOARD.toString())){
				sendToDashboard(template, subjectPersonId, companyId, data, receiverPersonId, parameterUrl);
			}
		}
	}
	
	// added by WLY, added for HCMS phase 2, send notification to external person use email
	@Override
	public void sendNotificationToExternalEmail(NotificationTransactionType trnType, Long companyId, Long subjectPersonId, 
			String receiverName, String receiverEmail, Object data) throws Exception{
		
		NotificationTemplateDTO template = notificationTemplateFinder.getTemplateByType(trnType.toString());
		
		if(template == null)
			throw new Exception("Error when send notification. Caused by Notification template for "+trnType+" was not defined");
		
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTemplateSubject(template.getSubject());
		templateMessage.setTemplateContent(template.getContentMessage());
		
		if(receiverEmail != null && receiverName != null){
			Map<String, Object> map = new HashMap<String, Object>();
			
			String currentUserLogin = null;
			
			if(securityServiceImpl.getSecurityProfile() != null)
				currentUserLogin = securityServiceImpl.getSecurityProfile().getEmployeeNumber()+"-"+securityServiceImpl.getSecurityProfile().getFullName();
			else
				currentUserLogin = "SYSTEM";
				
			map.put("receiverName", receiverName);
			map.put("object", data);
			map.put("currentUserLogin", currentUserLogin);
			map.put("processDate",DateUtil.format(new Date()));
			Message message = notificationManager.templateMessageResolver(templateMessage, map);
			NotificationMessage notificationMessage = new NotificationMessage();
			notificationMessage.setSubject(message.getSubject());
			notificationMessage.setContentMessage(message.getContent());
			notificationMessage.setFromId(UUID.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"));
			notificationMessage.setToId(UUID.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"));
			notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
			notificationMessage.setReceivedTime(new Date());
			try {
				sendEmailManager.sendEmailExternal(notificationMessage, receiverEmail);						
			} catch (Exception e) {
				throw e;
			}
			
		}
	}
	// end by WLY
	
	@Transactional
	private void sendToMail(NotificationTemplateDTO template, Long personId, Long companyId, Object data, Long receiverPersonId) throws Exception{
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTemplateSubject(template.getSubject());
		templateMessage.setTemplateContent(template.getContentMessage());
		
		if(receiverPersonId != null){
			Map<String, Object> map = new HashMap<String, Object>();
			PersonDTO receiverPersonDTO = notificationTemplateFinder.getReceiverName(receiverPersonId);
			String receiverName = receiverPersonDTO.getEmployeeNumber()+"-"+receiverPersonDTO.getFullName();
			
			String currentUserLogin = null;
			
			if(securityServiceImpl.getSecurityProfile() != null)
				currentUserLogin = securityServiceImpl.getSecurityProfile().getEmployeeNumber()+"-"+securityServiceImpl.getSecurityProfile().getFullName();
			else
				currentUserLogin = "SYSTEM";
				
			map.put("receiverName", receiverName);
			map.put("object", data);
			map.put("currentUserLogin", currentUserLogin);
			map.put("processDate",DateUtil.format(new Date()));
			Message message = notificationManager.templateMessageResolver(templateMessage, map);
			NotificationMessage notificationMessage = new NotificationMessage();
			notificationMessage.setSubject(message.getSubject());
			notificationMessage.setContentMessage(message.getContent());
			notificationMessage.setFromId(UUID.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"));
			notificationMessage.setToId(receiverPersonDTO.getPersonUUID());
			notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
			notificationMessage.setReceivedTime(new Date());
			try {
				sendEmailManager.sendEmail(notificationMessage, receiverPersonDTO.getEmployeeNumber());						
			} catch (Exception e) {
				throw e;
			}
			
		}
		
	}
	
	@Transactional
	private void sendToDashboard(NotificationTemplateDTO template, Long subjectPersonId, Long companyId, Object data, Long receiverPersonId, String parameterUrl) throws Exception{
		if (receiverPersonId != null) {
			inserNotificationToDashboard(template, subjectPersonId, companyId, receiverPersonId, data, parameterUrl);
		}
	}
	
	private void inserNotificationToDashboard(NotificationTemplateDTO template, Long subjectPersonId, Long companyId, Long receiverPersonId, Object data, String parameterUrl) throws Exception{
		
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setTemplateSubject(template.getSubject());
		templateMessage.setTemplateContent(template.getContentMessage());
		Map<String, Object> map = new HashMap<String, Object>();
		PersonDTO subjectPersonDTO = new PersonDTO();
		if(subjectPersonId != null){
			subjectPersonDTO = personService.getPersonById(subjectPersonId, DateUtil.truncate(new Date()), companyId);
			map.put("person", subjectPersonDTO);
		}

		map.put("updaterName", "System");
		map.put("processDate",DateUtil.format(new Date()));
		PersonDTO receiverPersonDTO = notificationTemplateFinder.getReceiverName(receiverPersonId);
		map.put("receiverName", receiverPersonDTO.getFullName());
		map.put("object", data);
		Message message = notificationManager.templateMessageResolver(templateMessage, map);
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setSubject(message.getSubject());
		notificationMessage.setContentMessage(message.getContent());
		notificationMessage.setFromId(subjectPersonDTO.getPersonUUID() !=null ? subjectPersonDTO.getPersonUUID():UUID.fromString(APPROVER_SYSTEM));
		notificationMessage.setToId(receiverPersonDTO.getPersonUUID());
		notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
		notificationMessage.setReceivedTime(new Date());
		
		String url = null;
		
		
		if(template.getUrlDetail() != null){
			url = template.getUrlDetail();
			if(parameterUrl != null){
				url=url+"?"+parameterUrl;
			}
		}
		
		notificationMessage.setUrlDetail(url);
		notificationManager.insertNewMessage(notificationMessage);	
	}

}
