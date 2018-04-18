package co.id.fifgroup.workstructure.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.IdGenerator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.version.AbstractVersioningService;
import co.id.fifgroup.workstructure.constant.GradeSequence;
import co.id.fifgroup.workstructure.constant.MinMax;
import co.id.fifgroup.workstructure.constant.PeopleCategory;
import co.id.fifgroup.workstructure.domain.Grade;
import co.id.fifgroup.workstructure.domain.GradeExample;
import co.id.fifgroup.workstructure.domain.GradeInfo;
import co.id.fifgroup.workstructure.domain.GradeInfoExample;
import co.id.fifgroup.workstructure.domain.GradeRate;
import co.id.fifgroup.workstructure.domain.GradeRateExample;
import co.id.fifgroup.workstructure.domain.GradeVersion;
import co.id.fifgroup.workstructure.domain.GradeVersionExample;
import co.id.fifgroup.workstructure.service.GradeSetSetupService;
import co.id.fifgroup.workstructure.service.GradeSetupService;
import co.id.fifgroup.workstructure.service.JobSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.workstructure.dao.GradeInfoMapper;
import co.id.fifgroup.workstructure.dao.GradeMapper;
import co.id.fifgroup.workstructure.dao.GradeRateMapper;
import co.id.fifgroup.workstructure.dao.GradeVersionMapper;
import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.GradeRateDTO;
import co.id.fifgroup.workstructure.dto.GradeSequenceDTO;
import co.id.fifgroup.workstructure.dto.GradeSetElementDTO;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.OtherInfoDTO;
import co.id.fifgroup.workstructure.finder.GradeFinder;
import co.id.fifgroup.workstructure.validation.GradeValidator;

@Service
@Transactional(readOnly=false)
public class GradeSetupServiceImpl extends AbstractVersioningService<GradeDTO> 
	implements GradeSetupService {

	@Autowired private GradeValidator gradeValidator;
	@Autowired private GradeMapper gradeMapper;
	@Autowired private GradeVersionMapper gradeVersionMapper;
	@Autowired private GradeRateMapper gradeRateMapper;
	@Autowired private GradeInfoMapper infoMapper;
	@Autowired private GradeFinder gradeFinder;
	@Autowired private IdGenerator idGenerator;
	@Autowired private SecurityService securityServiceImpl;
	@Autowired private JobSetupService jobSetupServiceImpl;
	@Autowired private GradeSetSetupService gradeSetSetupServiceImpl;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public void insertHeader(GradeDTO subject) {
		
		//Grade grade = mapper.map(subject, Grade.class);
		Grade grade = new Grade();
		grade.setId(subject.getId());
		grade.setGradeCode(subject.getCode());
		grade.setCompanyId(subject.getCompanyId());
		grade.setGrade(subject.getGrade());
		grade.setSubGrade(subject.getSubGrade());
		grade.setCreatedBy(subject.getCreatedBy());
		grade.setCreationDate(subject.getCreationDate());
		grade.setLastUpdateDate(subject.getLastUpdateDate());
		grade.setLastUpdatedBy(subject.getLastUpdatedBy());
		gradeMapper.insert(grade);
		subject.setId(grade.getId());
	}

	@Override
	public void insertDetail(GradeDTO subject) {
		
		//GradeVersion gradeVersion = mapper.map(subject, GradeVersion.class);
		GradeVersion gradeVersion = new GradeVersion();
		gradeVersion.setId(subject.getId());
		gradeVersion.setVersionNumber(subject.getVersionNumber());
		gradeVersion.setDateFrom(subject.getDateFrom());
		gradeVersion.setDateTo(subject.getDateTo());
		gradeVersion.setDescription(subject.getDescription());
		gradeVersion.setPersonalGradeCode(subject.getPersonalGradeCode());
		gradeVersion.setCreatedBy(subject.getCreatedBy());
		gradeVersion.setCreationDate(subject.getCreationDate());
		gradeVersion.setLastUpdatedBy(subject.getLastUpdatedBy());
		gradeVersion.setLastUpdateDate(subject.getLastUpdateDate());
		gradeVersion.setWorkingScheduleId(subject.getWorkingScheduleId());
		gradeVersionMapper.insert(gradeVersion);
		subject.setVersionId(gradeVersion.getVersionId());
		if (null != subject.getGradeRates()) {
			for(GradeRateDTO rate : subject.getGradeRates()) {
				//GradeRate gradeRate = mapper.map(rate, GradeRate.class);
				GradeRate gradeRate = new GradeRate();
				gradeRate.setVersionId(subject.getVersionId());
				gradeRate.setId(subject.getId());
				gradeRate.setBranchId(rate.getBranchId());
				gradeRate.setMinSalary(rate.getMinSalary());
				gradeRate.setMaxSalary(rate.getMaxSalary());
				gradeRate.setCreatedBy(subject.getCreatedBy());
				gradeRate.setCreationDate(subject.getCreationDate());
				gradeRate.setLastUpdatedBy(subject.getLastUpdatedBy());
				gradeRate.setLastUpdateDate(subject.getLastUpdateDate());
				gradeRateMapper.insert(gradeRate);
			}
		}
		if(subject.getGradeInfos() != null) {
			for(OtherInfoDTO gradeInfo : subject.getGradeInfos()) {
				//GradeInfo info = mapper.map(gradeInfo, GradeInfo.class);
				GradeInfo info = new GradeInfo();
				info.setVersionId(subject.getVersionId());
				info.setInfoId(gradeInfo.getInfoId());
				info.setInfoValue(gradeInfo.getInfoValue());
				info.setCreatedBy(subject.getCreatedBy());
				info.setCreationDate(subject.getCreationDate());
				info.setLastUpdatedBy(subject.getLastUpdatedBy());
				info.setLastUpdateDate(subject.getLastUpdateDate());
				infoMapper.insert(info);
			}
		}
		
	}

	@Override
	public void deleteHeader(GradeDTO subject) {
		gradeMapper.deleteByPrimaryKey(subject.getId());
		
	}

	@Override
	public void deleteDetail(GradeDTO subject) {
		
		GradeRateExample example = new GradeRateExample();
		example.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		gradeRateMapper.deleteByExample(example);
		
		GradeInfoExample infoExample = new GradeInfoExample();
		infoExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		infoMapper.deleteByExample(infoExample);
		
		gradeVersionMapper.deleteByPrimaryKey(subject.getVersionId());		
	}

	
	public void updateHeader(GradeDTO subject) {
		
		//Grade grade = mapper.map(subject, Grade.class);
		Grade grade = new Grade();
		grade.setId(subject.getId());
		grade.setCompanyId(subject.getCompanyId());
		grade.setGradeCode(subject.getCode());
		grade.setGrade(subject.getGrade());
		grade.setSubGrade(subject.getSubGrade());
		grade.setCreatedBy(subject.getCreatedBy());
		grade.setCreationDate(subject.getCreationDate());
		grade.setLastUpdateDate(subject.getLastUpdateDate());
		grade.setLastUpdatedBy(subject.getLastUpdatedBy());
		gradeMapper.updateByPrimaryKey(grade);
		
	}

	@Override
	public void updateDetail(GradeDTO subject) {
		
		//GradeVersion gradeVersion = mapper.map(subject, GradeVersion.class);
		GradeVersion gradeVersion = new GradeVersion();
		gradeVersion.setId(subject.getId());
		gradeVersion.setVersionId(subject.getVersionId());
		gradeVersion.setVersionNumber(subject.getVersionNumber());
		gradeVersion.setDateFrom(subject.getDateFrom());
		gradeVersion.setDateTo(subject.getDateTo());
		gradeVersion.setDescription(subject.getDescription());
		gradeVersion.setPersonalGradeCode(subject.getPersonalGradeCode());
		gradeVersion.setCreatedBy(subject.getCreatedBy());
		gradeVersion.setCreationDate(subject.getCreationDate());
		gradeVersion.setLastUpdatedBy(subject.getLastUpdatedBy());
		gradeVersion.setLastUpdateDate(subject.getLastUpdateDate());
		gradeVersion.setWorkingScheduleId(subject.getWorkingScheduleId());
		
		//delete all grade infos
		GradeInfoExample infoExample = new GradeInfoExample();
		infoExample.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		infoMapper.deleteByExample(infoExample);
		
		//insert new grade infos
		if(subject.getGradeInfos() != null) {
			for(OtherInfoDTO gradeInfo : subject.getGradeInfos()) {
				if(gradeInfo.getInfoId() != null) {
					//GradeInfo info = mapper.map(gradeInfo, GradeInfo.class);
					//info.setVersionId(subject.getVersionId());
					GradeInfo info = new GradeInfo();
					info.setVersionId(subject.getVersionId());
					info.setInfoId(gradeInfo.getInfoId());
					info.setInfoValue(gradeInfo.getInfoValue());
					info.setCreatedBy(subject.getCreatedBy());
					info.setCreationDate(subject.getCreationDate());
					info.setLastUpdatedBy(subject.getLastUpdatedBy());
					info.setLastUpdateDate(subject.getLastUpdateDate());
					infoMapper.insert(info);
				}
			}
		}
		gradeVersionMapper.updateByPrimaryKey(gradeVersion);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getNextHeaderId() {
		return idGenerator.getNextHeaderId("WOS_GRADES_S");
	}

	@Override
	@Transactional(readOnly = true)
	public GradeDTO findByIdAndVersionNumber(Long id, Integer versionNumber) {
		return gradeFinder.findByIdAndVersionNumber(id, versionNumber);
	}

	@Override
	@Transactional(readOnly = true)
	public void validate(GradeDTO subject) throws ValidationException {
		gradeValidator.validate(subject);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> findVersionsById(Long id) {
		return gradeFinder.findVersionsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GradeDTO> findByGradeName(String gradeName, Long companyId) {
		return gradeFinder.findByName(gradeName, companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<GradeDTO> findByGradeCode(String gradeCode, Long companyId) {
		return gradeFinder.findByCode(gradeCode, companyId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Grade> findByExample(GradeExample example) {
		return gradeMapper.selectByExample(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Grade> findByExample(GradeExample example, int limit, int offset) {
		return gradeMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Override
	public List<GradeDTO> findByExampleCurrentAndFuture(GradeDTO example, int limit, int offset) {
		return gradeFinder.findByExampleCurrentAndFuture(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public List<GradeDTO> findByInquiry(GradeDTO example) {
		return gradeFinder.findByInquiry(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByExample(GradeExample example) {
		return gradeMapper.countByExample(example);
	}
	
	@Override
	public Integer countByExampleCurrentAndFuture(GradeDTO example){
		return gradeFinder.countByExampleCurrentAndFuture(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GradeDTO> findByExample(GradeDTO example) {
		return gradeFinder.findByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isHaveFuture(Long id) {
		return gradeFinder.isHaveFuture(id) == 1;
	}

	@Override
	@Transactional(readOnly = true)
	public List<GradeDTO> findByGradeSetId(Long gradeSetId) {
		return gradeFinder.findByGradeSetId(gradeSetId, null, null, new RowBounds());
	}

	@Override
	@Transactional(readOnly = true)
	public List<GradeDTO> findByGradeSetIdAndCodeAndName(Long gradeSetId, String gradeCode, String gradeName, int limit, int offset) {
		return gradeFinder.findByGradeSetId(gradeSetId, gradeCode, gradeName, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Integer countByGradeSetIdAndCodeAndName(Long gradeSetId,
			String gradeCode, String gradeName) {
		return gradeFinder.countByGradeSetId(gradeSetId, gradeCode, gradeName);
	}

	@Override
	@Transactional(readOnly=true)
	public GradeDTO findByIdAndDate(Long id, Date date) {
		return gradeFinder.findByIdAndDate(id, date);
	}

	@Override
	@Transactional(readOnly = true)
	public GradeSetElementDTO findGradeSetElementByJobIdAndGradeId(Long jobId,
			Long gradeId) {
		return gradeFinder.findGradeSetElementByJobIdAndGradeId(jobId, gradeId);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer findMaxGradeSequenceByJobId(Long jobId) {
		List<Integer> sequences = gradeFinder.findGradeSequenceValidGradeByJobId(jobId);
		Integer maxSequence = 0;
		for(Integer seq : sequences) {
			if(seq > maxSequence)
				maxSequence = seq;
		}
		return maxSequence;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findGradeActiveByCompanyId(Long companyId, String grade, int limit, int offset) {
		return gradeFinder.findGradeActiveByCompanyId(companyId, grade, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findSubGradeActiveByCompanyId(Long companyId, String subGrade) {
		return gradeFinder.findSubGradeActiveByCompanyId(companyId, subGrade);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> findSubGradeByGradeCompanyId(Long companyId, String grade) {
		return gradeFinder.findSubGradeByGradeCompanyId(companyId, grade);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> findStringGradeByCompanyId(Long companyId, Date effectiveOnDate) {
		return gradeFinder.findStringGradeByCompanyId(companyId, effectiveOnDate);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Long> findGradeIdsByStringGrade(Long companyId, String grade, String subgrade) {
		return gradeFinder.findGradeIdsByStringGrade(companyId, grade, subgrade);
	}

	@Override
	@Transactional(readOnly = true)
	public GradeDTO findGradeByGradeAndSubGrade(Long companyId,
			Long gradeSetId, String grade, String subGrade) {
		return gradeFinder.findGradeByGradeAndSubGrade(companyId, gradeSetId, grade, subGrade);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getMinSalaryByGradeIdAndBranchId(Long gradeId,
			Long branchId, Date date) {
		BigDecimal minSalary = BigDecimal.ZERO;

		GradeDTO gradeDto = findByIdAndDate(gradeId, date);
		if(gradeDto != null) {
			if(gradeDto.getGradeRates().size() == 1 && gradeDto.getGradeRates().get(0).getBranchId().equals(-1L)) {
				minSalary = gradeDto.getGradeRates().get(0).getMinSalary();
			} else {
				if(branchId != null)
					minSalary = gradeFinder.getMinSalaryByGradeIdAndBranchId(gradeId, branchId, date);
				if(minSalary == null)
					minSalary = BigDecimal.ZERO;
			}
		}
		
		return minSalary;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getMaxSalaryByGradeIdAndBranchId(Long gradeId,
			Long branchId, Date date) {
		BigDecimal maxSalary = BigDecimal.ZERO;
		
		GradeDTO gradeDto = findByIdAndDate(gradeId, date);
		if(gradeDto != null) {
			if(gradeDto.getGradeRates().size() == 1 && gradeDto.getGradeRates().get(0).getBranchId().equals(-1L)) {
				maxSalary = gradeDto.getGradeRates().get(0).getMaxSalary();
			} else {
				if(branchId != null)
					maxSalary = gradeFinder.getMaxSalaryByGradeIdAndBranchId(gradeId, branchId, date);
				if(maxSalary == null)
					maxSalary = BigDecimal.ZERO;
			}
		}
		return maxSalary;
	}

	@Transactional(readOnly = true)
	private Integer getIdxGradeIdFromGradeSet(List<GradeSetElementDTO> gradeSet, GradeDTO gradeDto, Long gradeSetId) {
		Integer idx = null;
		for(int i = 0; i < gradeSet.size(); i++) {
			GradeSetElementDTO gradeSetElementDto = gradeSet.get(i);
			if(gradeSetElementDto.getGrade() != null && gradeSetElementDto.getGrade().equals(gradeDto.getGrade()) && gradeSetElementDto.getSubGrade() != null && gradeSetElementDto.getSubGrade().equals(gradeDto.getSubGrade()) && gradeSetElementDto.getId().equals(gradeSetId)) {
				idx = i;
				break;
			}
		}
		return idx;
	}
	
	private boolean isValidSequenceGrade(List<GradeSetElementDTO> gradeSets, int idxFrom, int idxTo, int level) {
		try {
			GradeSetElementDTO personGradeSetElementDto = (GradeSetElementDTO) gradeSets.get(idxFrom).clone();
			GradeSetElementDTO previousGradeSetElementDto = (GradeSetElementDTO) gradeSets.get(idxFrom).clone();
			int count = 0;
			if(idxTo > idxFrom) {
				for(int i = 0; i < gradeSets.size(); i++) {
					if(i <= idxFrom) {
						continue;
					} else if(i > idxTo) {
						break;
					}
					GradeSetElementDTO currentGradeSetElementDto = (GradeSetElementDTO) gradeSets.get(i).clone();
					if(previousGradeSetElementDto.getGrade().equals(currentGradeSetElementDto.getGrade()) && previousGradeSetElementDto.getSubGrade().equals(currentGradeSetElementDto.getSubGrade())) {
						previousGradeSetElementDto = currentGradeSetElementDto;
						continue;
					}
					if(!(personGradeSetElementDto.getGrade().equals(currentGradeSetElementDto.getGrade()) && personGradeSetElementDto.getSubGrade().equals(currentGradeSetElementDto.getSubGrade()))) {
						count++;
					}
					previousGradeSetElementDto = currentGradeSetElementDto;
				}
			}
			if(count <= level && count > 0) {
				return true;
			}
		} catch (CloneNotSupportedException e) {
			return false;
		}
		return false;
	}
	
	private Integer getIdxActing(List<GradeSetElementDTO> gradeSets, int idxFrom, int idxTo, Long destinationGradeSet) {
		Integer idx = null;
		try {
			
			GradeSetElementDTO destinationGradeSetElementDto = (GradeSetElementDTO) gradeSets.get(idxTo).clone();
			
			if(idxTo > idxFrom) {
				
				for(int i = (idxTo - 1); i >= 0; i--) {
					
					GradeSetElementDTO currentGradeSetElementDto = (GradeSetElementDTO) gradeSets.get(i).clone();
					if(currentGradeSetElementDto.getId().equals(destinationGradeSet) && !(destinationGradeSetElementDto.getGrade().equals(currentGradeSetElementDto.getGrade()) && destinationGradeSetElementDto.getSubGrade().equals(currentGradeSetElementDto.getSubGrade()))) {
						return i;
					}
					
				}
				
			}
		} catch (CloneNotSupportedException e) {
			return null;
		}
		return idx;
	}
	
	@Override
	@Transactional(readOnly = true)
	public GradeSequenceDTO getGradeSequence(Long fromJobId, Long toJobId, Long fromGradeId, Long toGradeId, Integer level, String status, Long validGradeInJob) {
		GradeSequenceDTO gradeSequence = new GradeSequenceDTO();
		
		GradeDTO gradeFrom = findByIdAndDate(fromGradeId, DateUtils.truncate(new Date(), Calendar.DATE));
		GradeDTO gradeTo = findByIdAndDate(toGradeId, DateUtils.truncate(new Date(), Calendar.DATE));
		
		if(fromJobId != null && toJobId != null) {
			JobDTO jobFrom = jobSetupServiceImpl.findById(fromJobId);
			JobDTO jobTo = jobSetupServiceImpl.findById(toJobId);
		
			if(fromGradeId != null && toGradeId != null) {
				if(!jobFrom.getPeopleCategoryCode().equalsIgnoreCase(jobTo.getPeopleCategoryCode())) {
					gradeSequence.setValid(Boolean.TRUE);
					gradeSequence.setValidGradeId(toGradeId);
					if(jobFrom.getPeopleCategoryCode().equals(PeopleCategory.FIELD_PEOPLE.toString())) {
						gradeSequence.setGradeSequence(GradeSequence.GRADE);
					} else {
						gradeSequence.setGradeSequence(GradeSequence.NONE);
					}
				} else {
					List<Long> gradeSetIds = new ArrayList<Long>();
					gradeSetIds.add(jobFrom.getGradeSetId());
					gradeSetIds.add(jobTo.getGradeSetId());
					
					List<GradeSetElementDTO> gradeSet = gradeSetSetupServiceImpl.findGradeIdInGradeSetId(gradeSetIds);
					
					Integer idxGradeFrom = getIdxGradeIdFromGradeSet(gradeSet, gradeFrom, jobFrom.getGradeSetId());
					Integer idxGradeTo = getIdxGradeIdFromGradeSet(gradeSet, gradeTo, jobTo.getGradeSetId());
					
					if(!gradeSet.isEmpty()) {
						if(idxGradeFrom == null && idxGradeTo != null) {
							GradeSetElementDTO gradeSetElementTo = gradeSet.get(idxGradeTo.intValue());
							if(toGradeId.equals(validGradeInJob)) {
								gradeSequence.setValidGradeId(validGradeInJob);
								if(!gradeFrom.getGrade().equals(gradeSetElementTo.getGrade())) {
									gradeSequence.setGradeSequence(GradeSequence.GRADE);
								} else {
									gradeSequence.setGradeSequence(GradeSequence.SUB_GRADE);
								}
							} else {
								gradeSequence.setValid(Boolean.FALSE);
							}
						} else if(idxGradeFrom != null && idxGradeTo != null && !fromJobId.equals(toJobId)) {
							if(validGradeInJob != null && toGradeId.equals(validGradeInJob)) {
								GradeSetElementDTO gradeSetElementDtoFrom = gradeSet.get(idxGradeFrom.intValue());
								GradeSetElementDTO gradeSetElementDtoTo = gradeSet.get(idxGradeTo.intValue());
								gradeSequence.setValid(Boolean.TRUE);
								if(status.equalsIgnoreCase("ACTING")) {
									Integer idxActing = getIdxActing(gradeSet, idxGradeFrom, idxGradeTo, jobTo.getGradeSetId());
									if(idxActing != null) {
										GradeSetElementDTO gradeSetElementDto = gradeSet.get(idxActing);
										gradeSequence.setValidGradeId(gradeSetElementDto.getGradeId());
										if(gradeSetElementDtoFrom.getGrade().equalsIgnoreCase(gradeSetElementDto.getGrade()) && gradeSetElementDtoFrom.getSubGrade().equalsIgnoreCase(gradeSetElementDto.getSubGrade())) {
											gradeSequence.setGradeSequence(GradeSequence.STAY);
										} else if(!gradeSetElementDtoFrom.getGrade().equalsIgnoreCase(gradeSetElementDto.getGrade())) {
											gradeSequence.setGradeSequence(GradeSequence.GRADE);
										} else {
											gradeSequence.setGradeSequence(GradeSequence.SUB_GRADE);
										}
									} else {
										gradeSequence.setValid(Boolean.FALSE);
									}
								} else if(status.equalsIgnoreCase("DEFINITIVE")) {
									gradeSequence.setValidGradeId(toGradeId);
									if(!gradeSetElementDtoFrom.getGrade().equalsIgnoreCase(gradeSetElementDtoTo.getGrade())) {
										gradeSequence.setGradeSequence(GradeSequence.GRADE);
									} else {
										gradeSequence.setGradeSequence(GradeSequence.SUB_GRADE);
									}
								}
							} else {
								gradeSequence.setValid(Boolean.FALSE);
							}
						} else if(idxGradeFrom != null && idxGradeTo != null && isValidSequenceGrade(gradeSet, idxGradeFrom, idxGradeTo, level)) {
							GradeSetElementDTO gradeSetElementDtoFrom = gradeSet.get(idxGradeFrom.intValue());
							GradeSetElementDTO gradeSetElementDtoTo = gradeSet.get(idxGradeTo.intValue());
							
							gradeSequence.setValid(Boolean.TRUE);
							if(status.equalsIgnoreCase("ACTING")) {
								GradeSetElementDTO gradeSetElementDto = gradeSet.get(getIdxActing(gradeSet, idxGradeFrom, idxGradeTo, jobTo.getGradeSetId()));
								gradeSequence.setValidGradeId(gradeSetElementDto.getGradeId());
								
								if(!gradeSetElementDtoFrom.getGrade().equalsIgnoreCase(gradeSetElementDto.getGrade())) {
									gradeSequence.setGradeSequence(GradeSequence.GRADE);
								} else {
									gradeSequence.setGradeSequence(GradeSequence.SUB_GRADE);
								}
							} else if(status.equalsIgnoreCase("DEFINITIVE")) {
								gradeSequence.setValidGradeId(toGradeId);
								if(!gradeSetElementDtoFrom.getGrade().equalsIgnoreCase(gradeSetElementDtoTo.getGrade())) {
									gradeSequence.setGradeSequence(GradeSequence.GRADE);
								} else {
									gradeSequence.setGradeSequence(GradeSequence.SUB_GRADE);
								}
							} else {
								gradeSequence.setValid(Boolean.FALSE);
							}
						} else {
							gradeSequence.setValid(Boolean.FALSE);
						}
					} else {
						gradeSequence.setValid(Boolean.FALSE);
					}
				}
			} else {
				gradeSequence.setValid(Boolean.FALSE);
			}
		} else {
			gradeSequence.setValid(Boolean.FALSE);
		}
		
		return gradeSequence;
	}

	@Override
	@Transactional(readOnly = true)
	public GradeSetElementDTO getMinOrMaxValidGradeByJobId(Long jobId, MinMax minMax) {
		List<GradeSetElementDTO> validGrades = gradeSetSetupServiceImpl.findValidGradeElementById(jobId);
		
		GradeSetElementDTO newGradeSet = new GradeSetElementDTO();
		if(!validGrades.isEmpty()) {
			int index = 0;
			if(minMax.equals(MinMax.MAXIMAL)) {
				index = validGrades.size() - 1;
			}
			newGradeSet.setGradeId(validGrades.get(index).getGradeId());
			newGradeSet.setGrade(validGrades.get(index).getGrade());
			newGradeSet.setSubGrade(validGrades.get(index).getSubGrade());
			newGradeSet.setGradeSequence(validGrades.get(index).getGradeSequence());
			newGradeSet.setGradeCode(validGrades.get(index).getGradeCode());
			return newGradeSet;
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<GradeDTO> findByExample(GradeDTO example, int limit, int offset) {
		return gradeFinder.findByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(GradeDTO example) {
		return gradeFinder.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isGradeToHigherOrEqualGradeFrom(Long gradeIdFrom, Long gradeIdTo, Long jobIdFrom, Long jobIdTo) {
		GradeDTO gradeFrom = findByIdAndDate(gradeIdFrom, DateUtil.truncate(new Date()));
		GradeDTO gradeTo = findByIdAndDate(gradeIdTo, DateUtil.truncate(new Date()));
		
		JobDTO jobFrom = jobSetupServiceImpl.findById(jobIdFrom);
		JobDTO jobTo = jobSetupServiceImpl.findById(jobIdTo);

		if (jobFrom.getPeopleCategoryCode().equalsIgnoreCase(jobTo.getPeopleCategoryCode())) {
			List<Long> gradeSetIds = new ArrayList<Long>();
			gradeSetIds.add(jobFrom.getGradeSetId());
			gradeSetIds.add(jobTo.getGradeSetId());
			
			List<GradeSetElementDTO> gradeSetElements = gradeSetSetupServiceImpl.findOrderedGradeByGradeSetId(gradeSetIds);
			int indexGradeFrom	= getIndexGrade(gradeSetElements, gradeFrom);
			int indexGradeTo	= getIndexGrade(gradeSetElements, gradeTo);
			
			if (indexGradeTo >= indexGradeFrom) {
				return true;
			}
		}
		return false;
	}
	
	private int getIndexGrade(List<GradeSetElementDTO> gradeSetElements, GradeDTO grade) {
		for (int i = 0; i < gradeSetElements.size(); i++) {
			if (gradeSetElements.get(i).getGrade().equalsIgnoreCase(grade.getGrade())
					&& gradeSetElements.get(i).getSubGrade().equalsIgnoreCase(grade.getSubGrade())) {
				return i;
			}
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isFutureExist(Long id) {
		return gradeFinder.isFutureExist(id) > 0;
	}

	@Override
	@Transactional(readOnly = true)
	public Long findVersionIdByIdAndDate(Long id, Date date) {
		return gradeFinder.findVersionIdByIdAndDate(id, date);
	}	
	
	@Override
	public void validateGradeRateSalary(Long branchId, Long gradeId, Date effectiveOnDate) throws ValidationException {
		Map<String, String> mapValidate = new HashMap<String, String>();
		BigDecimal minSalary = getMinSalaryByGradeIdAndBranchId(gradeId, branchId, DateUtil.truncate(effectiveOnDate));
		if (minSalary.equals(BigDecimal.ZERO)) {
			mapValidate.put("GRADE_RATE", Labels.getLabel("pea.err.validateSalaryRateGrade"));
		}
		
		if (mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}
	}

	@Override
	public List<GradeDTO> findActiveGradeForLov(GradeDTO example, int limit,
			int offset) {
		return gradeFinder.findActiveGradeForLov(example, new RowBounds(offset, limit));
	}

	@Override
	public Integer countActiveGradeForLov(GradeDTO example) {
		return gradeFinder.countActiveGradeForLov(example);
	}

	@Override
	public Grade findByPrimaryKey(Long gradeId) {
		return gradeMapper.selectByPrimaryKey(gradeId);
	}

	@Override
	public GradeDTO findLastVersionByCode(String code, Long companyId) {
		return gradeFinder.findLastVersionByCode(code, companyId);
	}

	@Override
	public GradeRate getGradeRateByGradeIdAndBranchId(Long gradeId,
			Long branchId, Date date) {
		return gradeFinder.getGradeRateByGradeIdAndBranchId(gradeId, branchId, date);
	}

	@Override
	public boolean isActiveGrade(Long gradeId, Date effectiveOnDate) {
		GradeVersionExample example = new GradeVersionExample();
		example.createCriteria().andIdEqualTo(gradeId)
			.andDateFromLessThanOrEqualTo(effectiveOnDate)
			.andDateToGreaterThanOrEqualTo(effectiveOnDate);
		return gradeVersionMapper.countByExample(example) > 0;
	}

	@Override
	public List<String> findGradeActiveByDateAndCompanyId(Long companyId,
			Date effectiveDate) {
		return gradeFinder.findGradeActiveByDateAndCompanyId(companyId, effectiveDate);
	}

	@Override
	public List<String> findSubGradeActiveByDateAndCompanyId(Long companyId,
			Date effectiveDate) {
		return gradeFinder.findSubGradeActiveByDateAndCompanyId(companyId, effectiveDate);
	}

	//recommit
	@Override
	public Boolean isGradeToHigherOrEqualGradeFrom(Long gradeIdFrom,
			Long gradeIdTo, Long jobIdFrom, Long jobIdTo, Date effectiveOnDate) {
		GradeDTO gradeFrom = findByIdAndDate(gradeIdFrom, DateUtil.truncate(new Date()));
		GradeDTO gradeTo = findByIdAndDate(gradeIdTo, DateUtil.truncate(effectiveOnDate));
		
		JobDTO jobFrom = jobSetupServiceImpl.findById(jobIdFrom, new Date());
		JobDTO jobTo = jobSetupServiceImpl.findById(jobIdTo, effectiveOnDate);

		if (jobFrom.getPeopleCategoryCode().equalsIgnoreCase(jobTo.getPeopleCategoryCode())) {
			List<Long> gradeSetIds = new ArrayList<Long>();
			gradeSetIds.add(jobFrom.getGradeSetId());
			gradeSetIds.add(jobTo.getGradeSetId());
			
			List<GradeSetElementDTO> gradeSetElements = gradeSetSetupServiceImpl.findOrderedGradeByGradeSetId(gradeSetIds, effectiveOnDate);
			int indexGradeFrom	= getIndexGrade(gradeSetElements, gradeFrom);
			int indexGradeTo	= getIndexGrade(gradeSetElements, gradeTo);
			
			if (indexGradeTo >= indexGradeFrom) {
				return true;
			}
		}
		return false;
	}
	
	//recommit
	@Override
	@Transactional(readOnly = true)
	public GradeSetElementDTO getMinOrMaxValidGradeByJobIdAndEffectiveDate(Long jobId, MinMax minMax, Date effectiveOnDate) {
		List<GradeSetElementDTO> validGrades = gradeSetSetupServiceImpl.findValidGradeElementByIdAndEffectiveDate(jobId, effectiveOnDate);
		
		GradeSetElementDTO newGradeSet = new GradeSetElementDTO();
		if(!validGrades.isEmpty()) {
			int index = 0;
			if(minMax.equals(MinMax.MAXIMAL)) {
				index = validGrades.size() - 1;
			}
			newGradeSet.setGradeId(validGrades.get(index).getGradeId());
			newGradeSet.setGrade(validGrades.get(index).getGrade());
			newGradeSet.setSubGrade(validGrades.get(index).getSubGrade());
			newGradeSet.setGradeSequence(validGrades.get(index).getGradeSequence());
			newGradeSet.setGradeCode(validGrades.get(index).getGradeCode());
			return newGradeSet;
		}
		return null;
	}
	
	@Override
	public GradeDTO findByGradeById(Long gradeId) {
		return gradeFinder.findById(gradeId);
	}
	
}