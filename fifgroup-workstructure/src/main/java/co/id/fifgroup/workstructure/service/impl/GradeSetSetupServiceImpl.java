package co.id.fifgroup.workstructure.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.GradeSet;
import co.id.fifgroup.workstructure.domain.GradeSetElement;
import co.id.fifgroup.workstructure.domain.GradeSetElementExample;
import co.id.fifgroup.workstructure.domain.GradeSetExample;
import co.id.fifgroup.workstructure.service.GradeSetSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.workstructure.dao.GradeSetElementMapper;
import co.id.fifgroup.workstructure.dao.GradeSetMapper;
import co.id.fifgroup.workstructure.dto.GradeSetDTO;
import co.id.fifgroup.workstructure.dto.GradeSetElementDTO;
import co.id.fifgroup.workstructure.finder.GradeSetFinder;
import co.id.fifgroup.workstructure.validation.GradeSetValidator;

@Transactional
@Service
public class GradeSetSetupServiceImpl implements GradeSetSetupService {

	@Autowired
	private GradeSetValidator gradeSetValidator;
	@Autowired
	private GradeSetFinder gradeSetFinder;
	@Autowired
	private GradeSetMapper gradeSetMapper;
	@Autowired
	private GradeSetElementMapper gradeSetElementMapper;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Transactional
	@Override
	public void save(GradeSetDTO gradeSetDto) throws ValidationException {
		validate(gradeSetDto);
		if(gradeSetDto.getId() == null) {
			insertHeader(gradeSetDto);
			insertDetail(gradeSetDto);
		} else {
			if(!gradeSetDto.getIsFuture())
				updateHeader(gradeSetDto);
			else {
				updateHeader(gradeSetDto);
				updateDetail(gradeSetDto);
			}
		}		
	}
	
	private void updateHeader(GradeSetDTO gradeSetDto) {
		
//		GradeSetExample gradeSetExample = new GradeSetExample();
//		gradeSetExample.createCriteria().andIdEqualTo(gradeSetDto.getId());
		gradeSetMapper.updateByPrimaryKeySelective(gradeSetDto);
	}
	
	private void updateDetail(GradeSetDTO gradeSetDto) {		
		
		GradeSetElementExample gradeSetElementExample = new GradeSetElementExample();
		gradeSetElementExample.createCriteria().andIdEqualTo(gradeSetDto.getId());
		gradeSetElementMapper.deleteByExample(gradeSetElementExample);
		
		for(GradeSetElementDTO elementDto : gradeSetDto.getGradeSetElements()) {
			GradeSetElement gradeSetElement = modelMapper.map(elementDto, GradeSetElement.class);
			gradeSetElement.setId(gradeSetDto.getId());
			gradeSetElement.setCreatedBy(gradeSetDto.getCreatedBy());
			gradeSetElement.setCreationDate(gradeSetDto.getCreationDate());
			gradeSetElement.setLastUpdatedBy(gradeSetDto.getLastUpdatedBy());
			gradeSetElement.setLastUpdateDate(gradeSetDto.getLastUpdateDate());
			gradeSetElementMapper.insert(gradeSetElement);
		}
	}
	
	private void insertHeader(GradeSetDTO gradeSetDto) {
		
		GradeSet gradeSet = modelMapper.map(gradeSetDto, GradeSet.class);
		gradeSetMapper.insert(gradeSet);
		gradeSetDto.setId(gradeSet.getId());
	}
	
	private void insertDetail(GradeSetDTO subject) {
		GradeSetElement gradeSetElement;
		for(GradeSetElementDTO elementDto : subject.getGradeSetElements()) {
			gradeSetElement = modelMapper.map(elementDto, GradeSetElement.class);
			gradeSetElement.setId(subject.getId());
			gradeSetElement.setCreatedBy(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			gradeSetElement.setCreationDate(new Date());
			gradeSetElement.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			gradeSetElement.setLastUpdateDate(new Date());
			gradeSetElementMapper.insert(gradeSetElement);
		}
	}

	private void validate(GradeSetDTO gradeSetDto) throws ValidationException {
		gradeSetValidator.validate(gradeSetDto);
	}

	@Override
	public List<GradeSetDTO> findByExample(GradeSetDTO gradeSetDto) {
		return gradeSetFinder.findByExample(gradeSetDto);
	}

	@Override
	public List<GradeSet> findByExample(GradeSetExample example) {
		example.createCriteria().andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		return gradeSetMapper.selectByExample(example);
	}

	@Override
	public Integer countByExample(GradeSetExample example) {
		example.createCriteria().andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		return gradeSetMapper.countByExample(example);
	}

	@Override
	public GradeSetDTO findById(Long id) {
		return gradeSetFinder.findById(id);
	}

	@Override
	public List<GradeSetDTO> findByInquiry(GradeSetDTO example) {
		return gradeSetFinder.findByInquiry(example);
	}

	@Override
	public Long findIdByName(String gradeSetName) {
		return gradeSetFinder.findIdByName(gradeSetName, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
	}

	@Override
	public Integer countByExample(GradeSetDTO gradeSetDto) {
		return gradeSetFinder.countByExample(gradeSetDto);
	}

	@Override
	public List<GradeSetElementDTO> findGradeSetElementByGradeIdInOneGradeSet(Long gradeId) {
		return gradeSetFinder.findGradeSetElementByGradeIdInOneGradeSet(gradeId);
	}

	@Override
	public GradeSetElementDTO findGradeSetElementByGradeId(Long gradeId) {
		return gradeSetFinder.findGradeSetElementByGradeId(gradeId);
	}

	@Override
	public List<GradeSetElementDTO> findGradeIdInGradeSetId(List<Long> gradeSetId) {
		return gradeSetFinder.findGradeIdInGradeSetId(gradeSetId);
	}

	@Override
	public List<GradeSetElementDTO> findValidGradeElementById(Long jobId) {
		return gradeSetFinder.findValidGradeElementById(jobId);
	}

	@Override
	public List<GradeSetElementDTO> findOrderedGradeByGradeSetId(
			List<Long> gradeSetId) {
		return gradeSetFinder.findOrderedGradeByGradeSetId(gradeSetId);
	}

	@Override
	public List<GradeSetElement> findElementByExample(GradeSetElementExample example) {
		return gradeSetElementMapper.selectByExample(example);
	}

	@Override
	public Long getMinimumValidGrade(Long jobId, Date effectiveDate) {
		return gradeSetFinder.getMinimumValidGrade(jobId, effectiveDate);
	}
	
	@Override
	public List<GradeSetElementDTO> findOrderedGradeByGradeSetId(
			List<Long> gradeSetId, Date effectiveOnDate) {
		return gradeSetFinder.findOrderedGradeByGradeSetIdAndEffectiveDate(gradeSetId, effectiveOnDate);
	}
	
	@Override
	public List<GradeSetElementDTO> findValidGradeElementByIdAndEffectiveDate(Long jobId, Date effectiveOnDate) {
		return gradeSetFinder.findValidGradeElementByIdAndEffectiveDate(jobId, effectiveOnDate);
	}
}
