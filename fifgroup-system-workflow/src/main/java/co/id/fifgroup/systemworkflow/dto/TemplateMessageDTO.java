package co.id.fifgroup.systemworkflow.dto;

import java.io.Serializable;

import co.id.fifgroup.notification.domain.TemplateMessage;

public class TemplateMessageDTO implements Serializable, Comparable<TemplateMessageDTO>{

	private static final long serialVersionUID = 1L;
	
	private TemplateMessage templateMessage;
	private TemplateMessageMappingDTO templateMessageMappingDTO;

	public TemplateMessage getTemplateMessage() {
		return templateMessage;
	}

	public void setTemplateMessage(TemplateMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	public TemplateMessageMappingDTO getTemplateMessageMappingDTO() {
		return templateMessageMappingDTO;
	}

	public void setTemplateMessageMappingDTO(
			TemplateMessageMappingDTO templateMessageMappingDTO) {
		this.templateMessageMappingDTO = templateMessageMappingDTO;
	}

	@Override
	public int compareTo(TemplateMessageDTO o) {
		return this.templateMessage.getTemplateName().compareToIgnoreCase(o.templateMessage.getTemplateName());
	}

	

}
