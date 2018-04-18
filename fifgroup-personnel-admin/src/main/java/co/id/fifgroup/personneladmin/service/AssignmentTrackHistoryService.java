package co.id.fifgroup.personneladmin.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.personneladmin.domain.AssignmentTrackHistory;

/**
 * 14040808522990_CR HCMS – Personal Admin_RAH
 * 
 */
public interface AssignmentTrackHistoryService {
	public List<AssignmentTrackHistory> getAssTrackHistory(Date date);

	public List<AssignmentTrackHistory> getAssTrackHistory(Long personId);

	public void insertAssDateTracks(
			List<AssignmentTrackHistory> assignmentTrackHistories);
}