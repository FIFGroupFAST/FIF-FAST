package co.id.fifgroup.workstructure.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.workstructure.domain.BranchSize;
import co.id.fifgroup.workstructure.domain.BranchSizeExample;
import co.id.fifgroup.workstructure.service.BranchSizeSetupService;

import co.id.fifgroup.basicsetup.common.AbstractHistoricalService;
import co.id.fifgroup.workstructure.dao.BranchSizeMapper;
import co.id.fifgroup.workstructure.dto.BranchSizeDTO;
import co.id.fifgroup.workstructure.finder.BranchSizeFinder;
import co.id.fifgroup.workstructure.validation.BranchSizeValidator;

@Transactional(readOnly=true)
@Service
public class BranchSizeSetupServiceImpl extends AbstractHistoricalService<BranchSizeDTO> implements BranchSizeSetupService {

	@Autowired
	private BranchSizeValidator branchSizeValidator;
	@Autowired
	private BranchSizeMapper branchSizeMapper;
	@Autowired
	private BranchSizeFinder branchSizeFinder;
	
	@Override
	@Transactional(readOnly=false)
	public void insertNewHistory(BranchSizeDTO object) {
		object.setEffectiveStartDate(DateUtils.truncate(object.getEffectiveStartDate(), Calendar.DATE));
		object.setEffectiveEndDate(DateUtils.truncate(object.getEffectiveEndDate(), Calendar.DATE));
		branchSizeMapper.insert(object);
	}

	@Override
	@Transactional(readOnly=false)
	public void endDateCurrentHistory(BranchSizeDTO object, Date dateTo,
			Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) {
		BranchSizeExample example = new BranchSizeExample();
		example.createCriteria().andIdEqualTo(object.getId())
			.andEffectiveStartDateEqualTo(DateUtils.truncate(dateFromBeforeCurrentEdit, Calendar.DATE)).andEffectiveEndDateEqualTo(DateUtils.truncate(dateToBeforeCurrentEdit, Calendar.DATE));
		BranchSizeDTO branchSizeDtoEndDate = new BranchSizeDTO();
		branchSizeDtoEndDate.setEffectiveEndDate(DateUtils.truncate(dateTo, Calendar.DATE));
		
		branchSizeMapper.updateByExampleSelective(branchSizeDtoEndDate, example);
	}
	@Override
	public boolean hasFuture(BranchSizeDTO object) {
		BranchSizeExample example = new BranchSizeExample();
		example.createCriteria().andIdEqualTo(object.getId()).andEffectiveStartDateGreaterThan(DateUtils.truncate(object.getEffectiveEndDate(), Calendar.DATE));
		List<BranchSize> resultBranchSize = branchSizeMapper.selectByExample(example);
		if(resultBranchSize.size() == 1) {
			return true;
		}
		return false;
	}
	@Override
	@Transactional(readOnly=false)
	public void save(BranchSizeDTO branchSizeDto,
			Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit)
			throws Exception {
		branchSizeValidator.validate(branchSizeDto);
		saveHistory(branchSizeDto, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
	}
	@Override
	@Transactional(readOnly=false)
	public void update(BranchSizeDTO branchSizeDto) {
		BranchSize branchSize = new BranchSize();
		branchSize.setId(branchSizeDto.getId());
		branchSize.setEffectiveEndDate(branchSizeDto.getEffectiveEndDate());
		branchSizeMapper.updateByPrimaryKeySelective(branchSize);
	}
	@Override
	@Transactional(readOnly=false)
	public void delete(BranchSizeDTO branchSizeDto) {
		BranchSizeExample example = new BranchSizeExample();
		example.createCriteria().andIdEqualTo(branchSizeDto.getId());
		branchSizeMapper.deleteByExample(example);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(branchSizeDto.getEffectiveStartDate());
		cal.add(Calendar.DATE, -1);
		
		BranchSizeExample example2 = new BranchSizeExample();
		
		example2.createCriteria().andOrganizationIdEqualTo(branchSizeDto.getOrganizationId()).andEffectiveEndDateEqualTo(DateUtil.truncate(cal.getTime()));
		
		BranchSize branchSize = new BranchSize();
		branchSize.setEffectiveEndDate(DateUtil.MAX_DATE);
		
		branchSizeMapper.updateByExampleSelective(branchSize, example2);
		
		
	}
	@Override
	public List<BranchSizeDTO> findByExample(BranchSizeDTO branchSizeDto) {
		return branchSizeFinder.findByExample(branchSizeDto);
	}
	@Override
	public List<Long> findHistoriesById(Long organizationId) {
		return branchSizeFinder.findHistoriesById(organizationId);
	}
	@Override
	public BranchSizeDTO findByIdAndHistoryId(Long id, Long organizationId) {
		return branchSizeFinder.findByIdAndHistroyId(id, organizationId);
	}

	@Override
	public Boolean isFutureExist(Long organizationId) {
		return branchSizeFinder.isFutureExist(organizationId) > 0;
	}
		
}
