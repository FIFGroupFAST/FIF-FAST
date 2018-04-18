package co.id.fifgroup.systemworkflow.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.systemworkflow.domain.LevelMessageMapping;

public class LevelMessageDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID levelMessageUUID;
	private Long level;
	private List<LevelMessageMapping> listLevelMessage;

	public UUID getLevelMessageUUID() {
		return levelMessageUUID;
	}

	public void setLevelMessageUUID(UUID levelMessageUUID) {
		this.levelMessageUUID = levelMessageUUID;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public List<LevelMessageMapping> getListLevelMessage() {
		return listLevelMessage;
	}

	public void setListLevelMessage(List<LevelMessageMapping> listLevelMessage) {
		this.listLevelMessage = listLevelMessage;
	}

}
