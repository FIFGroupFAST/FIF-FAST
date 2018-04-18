package co.id.fifgroup.personneladmin.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.constant.AssignmentStatus;
import co.id.fifgroup.personneladmin.dto.AssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PrimaryAssignmentDTO;
import co.id.fifgroup.personneladmin.service.PrimaryAssignmentService;

import co.id.fifgroup.basicsetup.common.AbstractHistoricalService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.personneladmin.dao.AssignmentOtherInfoMapper;
import co.id.fifgroup.personneladmin.dao.PrimaryAssignmentMapper;
import co.id.fifgroup.personneladmin.domain.AssignmentOtherInfo;
import co.id.fifgroup.personneladmin.domain.AssignmentOtherInfoExample;
import co.id.fifgroup.personneladmin.domain.PrimaryAssignment;
import co.id.fifgroup.personneladmin.domain.PrimaryAssignmentExample;
import co.id.fifgroup.personneladmin.finder.AssignmentFinder;

@Service("primaryAssignmentService")
@Transactional(readOnly = true)
public class PrimaryAssignmentServiceImpl extends
		AbstractHistoricalService<PrimaryAssignmentDTO> implements
		PrimaryAssignmentService {

	@Autowired
	private PrimaryAssignmentMapper primaryAssignmentMapper;
	@Autowired
	private AssignmentOtherInfoMapper assignmentOtherInfoMapper;
	@Autowired
	private AssignmentFinder assignmentFinder;
	@Autowired
	private SecurityService securityServiceImpl;

	@Override
	@Transactional(readOnly = false)
	public void insertNewHistory(PrimaryAssignmentDTO object) {
		object.setEffectiveStartDate(DateUtil.truncate(object
				.getEffectiveStartDate()));
		object.setEffectiveEndDate(DateUtil.truncate(object
				.getEffectiveEndDate()));
		primaryAssignmentMapper.insert(object);

		if (object.getAssignmentOtherInfos() != null) {
			for (AssignmentOtherInfo assignmentOtherInfoValue : object
					.getAssignmentOtherInfos()) {
				assignmentOtherInfoValue.setAssignmentId(object
						.getAssignmentId());
				assignmentOtherInfoMapper.insert(assignmentOtherInfoValue);
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void endDateCurrentHistory(PrimaryAssignmentDTO object, Date dateTo,
			Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) {
		PrimaryAssignmentExample primaryAssignmentExample = new PrimaryAssignmentExample();
		primaryAssignmentExample
				.createCriteria()
				.andPersonIdEqualTo(object.getPersonId())
				.andCompanyIdEqualTo(object.getCompanyId())
				.andEffectiveStartDateEqualTo(
						DateUtil.truncate(dateFromBeforeCurrentEdit))
				.andEffectiveEndDateEqualTo(
						DateUtil.truncate(dateToBeforeCurrentEdit));
		PrimaryAssignment primaryAssignmentEndDate = new PrimaryAssignment();
		primaryAssignmentEndDate.setEffectiveEndDate(DateUtils.truncate(dateTo,
				Calendar.DATE));
		primaryAssignmentMapper.updateByExampleSelective(
				primaryAssignmentEndDate, primaryAssignmentExample);
	}

	@Override
	public boolean hasFuture(PrimaryAssignmentDTO object) {
		PrimaryAssignmentExample primaryAssignmentExample = new PrimaryAssignmentExample();
		primaryAssignmentExample
				.createCriteria()
				.andPersonIdEqualTo(object.getPersonId())
				.andCompanyIdEqualTo(object.getCompanyId())
				.andEffectiveStartDateGreaterThan(
						DateUtil.truncate(object.getEffectiveEndDate()));
		List<PrimaryAssignment> resultPrimaryAssignment = primaryAssignmentMapper
				.selectByExample(primaryAssignmentExample);
		if (resultPrimaryAssignment.size() >= 1) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(PrimaryAssignmentDTO primaryAssignmentDTO,
			Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit)
			throws Exception {
		saveHistory(primaryAssignmentDTO, dateFromBeforeCurrentEdit,
				dateToBeforeCurrentEdit);
	}

	@Override
	public PrimaryAssignmentDTO getPrimaryAssignmentByEffectiveOnDate(
			Long personId, Long companyId, Date effectiveOnDate) {
		return assignmentFinder.getPrimaryAssignmentByEffectiveOnDate(personId,
				companyId, effectiveOnDate);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteFuturePrimaryAssignment(Long personId, Long companyId) {
		PrimaryAssignmentExample example = new PrimaryAssignmentExample();
		example.createCriteria()
				.andPersonIdEqualTo(personId)
				.andCompanyIdEqualTo(companyId)
				.andEffectiveStartDateGreaterThan(DateUtil.truncate(new Date()));
		List<PrimaryAssignment> listPrimaryAssignment = primaryAssignmentMapper
				.selectByExample(example);
		for (PrimaryAssignment primaryAssignment : listPrimaryAssignment) {
			AssignmentOtherInfoExample otherInfoExample = new AssignmentOtherInfoExample();
			otherInfoExample.createCriteria().andAssignmentIdEqualTo(
					primaryAssignment.getAssignmentId());
			assignmentOtherInfoMapper.deleteByExample(otherInfoExample);
			primaryAssignmentMapper.deleteByPrimaryKey(primaryAssignment
					.getAssignmentId());
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(PrimaryAssignmentDTO primaryAssignment) {
		AssignmentOtherInfoExample otherInfoExample = new AssignmentOtherInfoExample();
		otherInfoExample.createCriteria().andAssignmentIdEqualTo(
				primaryAssignment.getAssignmentId());
		assignmentOtherInfoMapper.deleteByExample(otherInfoExample);
		primaryAssignmentMapper.deleteByPrimaryKey(primaryAssignment
				.getAssignmentId());

		if (primaryAssignment.getSource() != null
				&& primaryAssignment.getRefId() != null) {
			PrimaryAssignmentExample deleteSameSourceAndRefId = new PrimaryAssignmentExample();
			deleteSameSourceAndRefId.createCriteria()
					.andSourceEqualTo(primaryAssignment.getSource())
					.andRefIdEqualTo(primaryAssignment.getRefId());
			primaryAssignmentMapper.deleteByExample(deleteSameSourceAndRefId);
		}

		PrimaryAssignmentExample primaryAssignmentExample = new PrimaryAssignmentExample();
		primaryAssignmentExample
				.createCriteria()
				.andPersonIdEqualTo(primaryAssignment.getPersonId())
				.andCompanyIdEqualTo(primaryAssignment.getCompanyId())
				.andEffectiveStartDateLessThanOrEqualTo(
						DateUtil.truncate(DateUtil.add(
								primaryAssignment.getEffectiveStartDate(),
								Calendar.DATE, -1)))
				.andEffectiveEndDateGreaterThanOrEqualTo(
						DateUtil.truncate(DateUtil.add(
								primaryAssignment.getEffectiveStartDate(),
								Calendar.DATE, -1)));
		PrimaryAssignment primaryAssignmentEndDate = new PrimaryAssignment();
		primaryAssignmentEndDate.setEffectiveEndDate(DateUtil.MAX_DATE);

		primaryAssignmentMapper.updateByExampleSelective(
				primaryAssignmentEndDate, primaryAssignmentExample);
	}

	@Override
	@Transactional(readOnly = false)
	public void savePrimaryAssignment(
			PrimaryAssignmentDTO primaryAssignmentDTO,
			Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit)
			throws Exception {
		Long createdBy = securityServiceImpl.getSecurityProfile() == null ? -1L
				: securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();

		primaryAssignmentDTO.setCreatedBy(createdBy);
		primaryAssignmentDTO.setCreationDate(createdDate);
		primaryAssignmentDTO.setLastUpdatedBy(createdBy);
		primaryAssignmentDTO.setLastUpdateDate(createdDate);
		save(primaryAssignmentDTO, dateFromBeforeCurrentEdit,
				dateToBeforeCurrentEdit);
	}

	@Override
	public PrimaryAssignmentDTO getPrimaryAssignmentById(Long assignmentId) {
		return assignmentFinder.findPrimaryAssignmentById(assignmentId);
	}

	@Override
	public boolean hasFutureTermination(Long personId, Long companyId) {
		return assignmentFinder.countFutureTermination(personId, companyId) < 1 ? false
				: true;
	}

	@Override
	public PrimaryAssignmentDTO getPrimaryAssignmentByEffectiveOnDateForEpmd(
			Long personId, Long companyId, Date effectiveOnDate) {
		return assignmentFinder.getPrimaryAssignmentByEffectiveOnDate(personId,
				companyId, effectiveOnDate);
	}

	@Override
	public boolean isTaskForceActive(PrimaryAssignmentDTO primaryAssignmentDTO) {
		PrimaryAssignmentExample assignmentSameSourceAndRefId = new PrimaryAssignmentExample();
		assignmentSameSourceAndRefId
				.createCriteria()
				.andSourceEqualTo(primaryAssignmentDTO.getSource())
				.andRefIdEqualTo(primaryAssignmentDTO.getRefId())
				.andAssignmentIdNotEqualTo(
						primaryAssignmentDTO.getAssignmentId());
		List<PrimaryAssignment> assignments = primaryAssignmentMapper
				.selectByExample(assignmentSameSourceAndRefId);
		if (assignments.size() > 0) {
			if (assignments.get(0).getAssignmentStatus()
					.equalsIgnoreCase(AssignmentStatus.TASK_FORCE.name())) {
				if (DateUtil.truncate(
						assignments.get(0).getEffectiveStartDate()).compareTo(
						DateUtil.truncate(new Date())) <= 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public PrimaryAssignment getTaskForceTransaction(
			PrimaryAssignmentDTO primaryAssignmentDTO) {
		PrimaryAssignmentExample example = new PrimaryAssignmentExample();
		example.createCriteria()
				.andSourceEqualTo(primaryAssignmentDTO.getSource())
				.andRefIdEqualTo(primaryAssignmentDTO.getRefId())
				.andAssignmentStatusEqualTo(AssignmentStatus.TASK_FORCE.name());
		List<PrimaryAssignment> primaryAssignments = primaryAssignmentMapper
				.selectByExample(example);
		if (primaryAssignments.size() > 0) {
			return primaryAssignments.get(0);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public PrimaryAssignmentDTO getAssignmentIdByEffectiveOnDate(Long personId,
			Long companyId, Date effectiveOnDate) {
		return assignmentFinder.getAssignmentIdByEffectiveOnDate(personId,
				companyId, DateUtils.truncate(effectiveOnDate, Calendar.DATE));
	}

	@Override
	public List<Long> getPersonChangeAssignment(Long companyId) {
		return assignmentFinder.getPersonChangeAssignment(companyId);
	}

	@Override
	public int countHomebasedRadiusAssignmentFromDateToCurrent(Long personId,
			Long companyId, Date fromDate, Date toDate, List<Long> listLocationId) {
		return assignmentFinder.countHomebasedRadiusAssignmentFromDateToCurrent(personId, companyId, fromDate, toDate, listLocationId);
	}

	@Override
	public List<AssignmentDTO> findHomebasedRadiusAssignmentFromDateToCurrent(
			Long personId, Long companyId, Date fromDate,
			List<Long> listLocationId) {
		return assignmentFinder.findHomebasedRadiusAssignmentFromDateToCurrent(personId, companyId, fromDate, listLocationId);
	}

	@Override
	public AssignmentDTO findLastNonHomeBasedAssignment(Long personId,
			Long CompanyId, List<Long> homebasedRadiusLocationId) {
		return assignmentFinder.findLastNonHomebasedRadiusAssignment(personId, CompanyId,  homebasedRadiusLocationId);
	}

	@Override
	public List<PrimaryAssignmentDTO> getPrimaryAssignmentByEffectiveOnDateHousing(
			Long personId, Date effectiveOnDate) {
		return assignmentFinder.getPrimaryAssignmentByEffectiveOnDateHousing(personId, effectiveOnDate);
	}

	
}
