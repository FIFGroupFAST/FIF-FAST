package co.id.fifgroup.workstructure.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.workstructure.dto.LobDTO;

public interface LobSetupService {
	
	public void save(LobDTO lobDto, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;
	void delete(LobDTO jobLobDto);
	void insertNewHistory(LobDTO object);
	List<LobDTO> findByExample(LobDTO jobLobDto);
	List<Long> findHistoriesById(Long jobId, Long organizationId);
	LobDTO findByIdAndHistoryId(Long id, Long jobId, Long organizationId);
	LobDTO findById(Long id);
	void validate(LobDTO subject) throws ValidationException;
	public List<LobDTO> findByInquiry(LobDTO jobLobDto);
	Boolean isFutureExist(Long organizationId, Long jobId);
}
