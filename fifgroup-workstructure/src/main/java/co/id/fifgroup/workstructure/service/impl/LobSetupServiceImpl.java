package co.id.fifgroup.workstructure.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.Lob;
import co.id.fifgroup.workstructure.domain.LobElement;
import co.id.fifgroup.workstructure.domain.LobElementExample;
import co.id.fifgroup.workstructure.domain.LobExample;
import co.id.fifgroup.workstructure.service.LobSetupService;

import co.id.fifgroup.basicsetup.common.AbstractHistoricalService;
import co.id.fifgroup.workstructure.dao.LobElementMapper;
import co.id.fifgroup.workstructure.dao.LobMapper;
import co.id.fifgroup.workstructure.dto.LobDTO;
import co.id.fifgroup.workstructure.dto.LobElementDTO;
import co.id.fifgroup.workstructure.finder.LobFinder;
import co.id.fifgroup.workstructure.validation.LobValidator;

@Transactional
@Service
public class LobSetupServiceImpl extends AbstractHistoricalService<LobDTO> implements LobSetupService{

	@Autowired
	private LobFinder lobFinder;
	@Autowired
	private LobMapper lobMapper;
	@Autowired
	private LobElementMapper lobElementMapper;
	@Autowired
	private LobValidator lobValidator;
	@Autowired
	private ModelMapper modelMapper;

	
	@Override
	public List<LobDTO> findByExample(LobDTO jobLobDto) {
		List<LobDTO> lobs = lobFinder.findByExample(jobLobDto);
		if(lobs.isEmpty()) {
			List<LobElementDTO> elements = new ArrayList<>();
			LobDTO lob = modelMapper.map(jobLobDto, LobDTO.class);
			LobElementDTO element = new LobElementDTO();
			element.setId(-1L);
			element.setProductCode("00");
			element.setPercentage(new BigDecimal(100));
			elements.add(element);
			lob.setLobElements(elements);
			lobs.add(lob);
		}
		return lobs; 
	}

	@Override
	public List<Long> findHistoriesById(Long jobId, Long organizationId) {
		return lobFinder.findHistoriesById(jobId, organizationId);
	}

	@Override
	public void insertNewHistory(LobDTO object) {
		object.setEffectiveStartDate(DateUtils.truncate(object.getEffectiveStartDate(), Calendar.DATE));
		object.setEffectiveEndDate(DateUtils.truncate(object.getEffectiveEndDate(), Calendar.DATE));
		lobMapper.insert(object);
		object.setId(object.getId());
		
		if(object.getLobElements() != null) {
			for(LobElementDTO element : object.getLobElements()) {
				
				LobElement el = modelMapper.map(element, LobElement.class);
				el.setId(object.getId());
				el.setCreatedBy(object.getCreatedBy());
				el.setCreationDate(object.getCreationDate());
				el.setLastUpdatedBy(object.getLastUpdatedBy());
				el.setLastUpdateDate(object.getLastUpdateDate());
				lobElementMapper.insert(el);
			}
		}
	}

	@Override
	public void endDateCurrentHistory(LobDTO object, Date dateTo,
			Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) {
		LobExample example = new LobExample();
		example.createCriteria().andIdEqualTo(object.getId())
			.andEffectiveStartDateEqualTo(DateUtils.truncate(dateFromBeforeCurrentEdit, Calendar.DATE)).andEffectiveEndDateEqualTo(DateUtils.truncate(dateToBeforeCurrentEdit, Calendar.DATE));
		LobDTO lobDtoEndDate = new LobDTO();
		lobDtoEndDate.setEffectiveEndDate(DateUtils.truncate(dateTo, Calendar.DATE));
		
		lobMapper.updateByExampleSelective(lobDtoEndDate, example);
	}

	@Override
	public boolean hasFuture(LobDTO object) {
		LobExample example = new LobExample();
		example.createCriteria().andIdEqualTo(object.getId()).andEffectiveStartDateGreaterThan(DateUtils.truncate(object.getEffectiveEndDate(), Calendar.DATE));
		List<Lob> resultLob = lobMapper.selectByExample(example);
		if(resultLob.size() == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void save(LobDTO lobDto, Date dateFromBeforeCurrentEdit,
			Date dateToBeforeCurrentEdit) throws Exception {
		validate(lobDto);
		saveHistory(lobDto, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
	}

	@Override
	public void delete(LobDTO lobDto) {
		LobElementExample elementExample = new LobElementExample();
		elementExample.createCriteria().andIdEqualTo(lobDto.getId());
		lobElementMapper.deleteByExample(elementExample);
		lobMapper.deleteByPrimaryKey(lobDto.getId());
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(lobDto.getEffectiveStartDate());
		cal.add(Calendar.DATE, -1);
		
		LobExample lobExample = new LobExample();
		lobExample.createCriteria().andEffectiveEndDateEqualTo(DateUtil.truncate(cal.getTime())).andOrganizationIdEqualTo(lobDto.getOrganizationId()).andJobIdEqualTo(lobDto.getJobId());
		
		Lob lob = new Lob();
		lob.setEffectiveEndDate(DateUtil.MAX_DATE);
		
		lobMapper.updateByExampleSelective(lob, lobExample);
	}

	@Override
	public LobDTO findByIdAndHistoryId(Long id, Long jobId, Long organizationId) {
		return lobFinder.findByIdAndHistroyId(id, jobId, organizationId);
	}

	@Override
	public LobDTO findById(Long id) {
		return lobFinder.findById(id);
	}

	@Override
	public void validate(LobDTO subject) throws ValidationException {
		lobValidator.validate(subject);
	}

	@Override
	public List<LobDTO> findByInquiry(LobDTO jobLobDto) {
		return lobFinder.findByExample(jobLobDto);
	}

	@Override
	public Boolean isFutureExist(Long organizationId, Long jobId) {
		return lobFinder.isFutureExist(organizationId, jobId) > 0;
	}
	
}
