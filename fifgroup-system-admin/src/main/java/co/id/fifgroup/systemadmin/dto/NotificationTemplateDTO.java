package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.systemadmin.domain.NotificationReceiver;
import co.id.fifgroup.systemadmin.domain.NotificationTemplate;

public class NotificationTemplateDTO extends NotificationTemplate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<NotificationReceiver> receivers;

	public List<NotificationReceiver> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<NotificationReceiver> receivers) {
		this.receivers = receivers;
	}


}
