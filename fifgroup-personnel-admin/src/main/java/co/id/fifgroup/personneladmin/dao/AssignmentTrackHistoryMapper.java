package co.id.fifgroup.personneladmin.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.domain.AssignmentTrackHistory;

/**
 * 14040808522990_CR HCMS – Personal Admin_RAH
 * 
 */
public interface AssignmentTrackHistoryMapper {
	int deleteAssTrackHistory(@Param("personId") Long personId);

	public List<Integer> getAllPersonId(@Param("date") Date date,
			@Param("status") String status);

	public List<AssignmentTrackHistory> getAssTrackByPersonId(
			@Param("personId") Long personId);

	public List<AssignmentTrackHistory> getAssTrackHistory(
			@Param("personId") Long personId);

	int insertAssDateTracks(AssignmentTrackHistory assignmentTrackHistory);
}