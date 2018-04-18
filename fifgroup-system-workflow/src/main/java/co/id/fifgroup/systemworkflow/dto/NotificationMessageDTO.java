package co.id.fifgroup.systemworkflow.dto;

import co.id.fifgroup.notification.domain.NotificationMessage;

public class NotificationMessageDTO extends NotificationMessage {

	private static final long serialVersionUID = 5278624734909806054L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
