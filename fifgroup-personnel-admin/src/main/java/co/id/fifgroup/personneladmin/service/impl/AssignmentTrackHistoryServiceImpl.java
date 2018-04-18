package co.id.fifgroup.personneladmin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.constant.ActionType;
import co.id.fifgroup.personneladmin.service.AssignmentTrackHistoryService;

import co.id.fifgroup.personneladmin.dao.AssignmentTrackHistoryMapper;
import co.id.fifgroup.personneladmin.dao.PrimaryAssignmentMapper;
import co.id.fifgroup.personneladmin.domain.AssignmentTrackHistory;
import co.id.fifgroup.personneladmin.domain.PrimaryAssignment;

/**
 * 14040808522990_CR HCMS – Personal Admin_RAH
 * 
 */
@Transactional
@Service
public class AssignmentTrackHistoryServiceImpl implements
		AssignmentTrackHistoryService {
	@Autowired
	private AssignmentTrackHistoryMapper assignmentTrackHistoryMapper;
	@Autowired
	private PrimaryAssignmentMapper primaryAssignmentMapper;

	private void captureAssChanges(
			List<AssignmentTrackHistory> assignmentTrackHistories) {
		int index = 0;

		for (AssignmentTrackHistory assignmentTrackHistory : assignmentTrackHistories) {
			StringBuilder stringBuilder = new StringBuilder();
			int nextIndex = 0;

			if (assignmentTrackHistories.size() > (index + 1)) {
				nextIndex = index + 1;
			} else {
				nextIndex = index;
			}

			if (nextIndex != index) {
				if (assignmentTrackHistory.getCompanyId().compareTo(
						assignmentTrackHistories.get(nextIndex).getCompanyId()) != 0) {
					stringBuilder.append("Company ("
							+ assignmentTrackHistories.get(nextIndex)
									.getCompanyName() + " to "
							+ assignmentTrackHistory.getCompanyName() + "), ");
				}

				if (assignmentTrackHistory.getOrganizationId().compareTo(
						assignmentTrackHistories.get(nextIndex)
								.getOrganizationId()) != 0) {
					stringBuilder.append("Organization, ");
				}

				if (assignmentTrackHistory.getJobId().compareTo(
						assignmentTrackHistories.get(nextIndex).getJobId()) != 0) {
					stringBuilder.append("Job, ");
				}

				if (assignmentTrackHistory.getJobGroupCode() != null
						&& assignmentTrackHistories.get(nextIndex)
								.getJobGroupCode() != null) {
					if (assignmentTrackHistory.getJobGroupCode().compareTo(
							assignmentTrackHistories.get(nextIndex)
									.getJobGroupCode()) != 0) {
						stringBuilder.append("Job Group, ");
					}
				}

				if (assignmentTrackHistory
						.getGrade()
						.concat(assignmentTrackHistory.getSubGrade())
						.compareTo(
								assignmentTrackHistories
										.get(nextIndex)
										.getGrade()
										.concat(assignmentTrackHistories.get(
												nextIndex).getSubGrade())) != 0) {
					stringBuilder.append("Grade, ");
				}

				if (assignmentTrackHistory.getAssignmentStatus().compareTo(
						assignmentTrackHistories.get(nextIndex)
								.getAssignmentStatus()) != 0) {
					stringBuilder.append("Assignment Status, ");
				}

				if (assignmentTrackHistory.getHousingAllowance().compareTo(
						assignmentTrackHistories.get(nextIndex)
								.getHousingAllowance()) != 0) {
					stringBuilder.append("Housing Allowance, ");
				}

				if (assignmentTrackHistory.getTransferedBy() != null
						&& assignmentTrackHistories.get(nextIndex)
								.getTransferedBy() != null) {
					if (assignmentTrackHistory.getTransferedBy().compareTo(
							assignmentTrackHistories.get(nextIndex)
									.getTransferedBy()) != 0) {
						stringBuilder.append("Transfered By, ");
					}
				}

				if (stringBuilder != null
						&& stringBuilder.length() > 0
						&& stringBuilder.substring(stringBuilder.length() - 2)
								.equalsIgnoreCase(", ")) {
					assignmentTrackHistory.setDateTrackChange(stringBuilder
							.substring(0, stringBuilder.length() - 2)
							.toString());
				} else {
					assignmentTrackHistory.setDateTrackChange(null);
				}
			} else {
				assignmentTrackHistory.setDateTrackChange(null);
			}

			index++;
		}
	}

	public List<AssignmentTrackHistory> getAssTrackHistory(Date date) {
		List<AssignmentTrackHistory> assignmentTrackHistories = new ArrayList<>();
		List<Integer> personIds = new ArrayList<>();
		List<AssignmentTrackHistory> savedAssignmentTrackHistories = assignmentTrackHistoryMapper
				.getAssTrackHistory(null);

		if (savedAssignmentTrackHistories != null
				&& !savedAssignmentTrackHistories.isEmpty()) {
			personIds = assignmentTrackHistoryMapper.getAllPersonId(date,
					"ACTIVE");
		} else {
			personIds = assignmentTrackHistoryMapper
					.getAllPersonId(date, "ALL");
		}

		if (personIds != null && !personIds.isEmpty()) {
			if (savedAssignmentTrackHistories != null
					&& !savedAssignmentTrackHistories.isEmpty()) {
				for (Integer personId : personIds) {
					assignmentTrackHistoryMapper
							.deleteAssTrackHistory(new Long(personId));
				}
			}

			for (Integer personId : personIds) {
				List<AssignmentTrackHistory> assignmentTrackHistoriesByPersonId = assignmentTrackHistoryMapper
						.getAssTrackByPersonId(new Long(personId));

				if (assignmentTrackHistoriesByPersonId != null
						&& !assignmentTrackHistoriesByPersonId.isEmpty()) {
					captureAssChanges(assignmentTrackHistoriesByPersonId);

					for (AssignmentTrackHistory assignmentTrackHistory : assignmentTrackHistoriesByPersonId) {
						assignmentTrackHistories.add(assignmentTrackHistory);
					}
				}
			}
		}

		return assignmentTrackHistories;
	}

	public List<AssignmentTrackHistory> getAssTrackHistory(Long personId) {
		List<AssignmentTrackHistory> assignmentTrackHistories = assignmentTrackHistoryMapper
				.getAssTrackHistory(personId);

		for (AssignmentTrackHistory assignmentTrackHistory : assignmentTrackHistories) {
			PrimaryAssignment primaryAssignment = primaryAssignmentMapper
					.selectByPrimaryKey(assignmentTrackHistory
							.getAssignmentId());

			if (DateUtil.isBetween(DateUtil.truncate(new Date()), DateUtil
					.truncate(primaryAssignment.getEffectiveStartDate()),
					DateUtil.truncate(primaryAssignment.getEffectiveEndDate()))
					&& !ActionType.TERMINATION.toString().equalsIgnoreCase(
							primaryAssignment.getActionType())) {
				assignmentTrackHistory.setIsEffective("Yes");
			} else {
				assignmentTrackHistory.setIsEffective("-");
			}
		}

		return assignmentTrackHistoryMapper.getAssTrackHistory(personId);
	}

	public void insertAssDateTracks(
			List<AssignmentTrackHistory> assignmentTrackHistories) {
		for (AssignmentTrackHistory assignmentTrackHistory : assignmentTrackHistories) {
			assignmentTrackHistoryMapper
					.insertAssDateTracks(assignmentTrackHistory);
		}
	}
}