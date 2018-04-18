package co.id.fifgroup.notification.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class NotificationMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long messageId;
	private Date receivedTime;
	private UUID fromId;
	private UUID toId;
	private String subject;
	private String contentMessage;
	private int status;
	private String urlDetail;
	private int messageType;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	public Date getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public UUID getFromId() {
		return fromId;
	}

	public void setFromId(UUID fromId) {
		this.fromId = fromId;
	}

	public UUID getToId() {
		return toId;
	}

	public void setToId(UUID toId) {
		this.toId = toId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContentMessage() {
		return contentMessage;
	}

	public void setContentMessage(String contentMessage) {
		this.contentMessage = contentMessage;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUrlDetail() {
		return urlDetail;
	}

	public void setUrlDetail(String urlDetail) {
		this.urlDetail = urlDetail;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}	
}
