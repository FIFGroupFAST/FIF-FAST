package co.id.fifgroup.systemadmin.service;

import co.id.fifgroup.systemadmin.constant.NotificationTransactionType;

public interface NotificationService {
	
	public void sendNotification(NotificationTransactionType trnType, Long companyId, Long subjectPersonId, Long receiverPersonId, Object data, String parameterUrl) throws Exception;
	
	// added by WLY, added for HCMS phase 2, send notification to external person use email
	public void sendNotificationToExternalEmail(NotificationTransactionType trnType, Long companyId, Long subjectPersonId, String receiverName, String receiverEmail, Object data) throws Exception;
	// end added by WLY
}
