package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.constant.SequenceGeneratorType;
import co.id.fifgroup.basicsetup.dao.SequenceGeneratorFinder;
import co.id.fifgroup.basicsetup.dao.SequenceGeneratorMapper;
import co.id.fifgroup.basicsetup.domain.SequenceGenerator;
import co.id.fifgroup.basicsetup.domain.SequenceGeneratorExample;
import co.id.fifgroup.basicsetup.dto.SequenceGeneratorDTO;
import co.id.fifgroup.basicsetup.service.SequenceGeneratorService;
import co.id.fifgroup.basicsetup.validation.SequenceGeneratorValidator;


@Transactional
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService{
	
	@Autowired
	private SequenceGeneratorMapper sequenceGeneratorMapper;
	@Autowired
	private SequenceGeneratorFinder sequenceGeneratorFinder;
	@Autowired
	private SequenceGeneratorValidator sequenceGeneratorValidator;

	@Override
	@Transactional
	public void save(SequenceGenerator sequenceGenerator) throws Exception {
		sequenceGeneratorValidator.validate(sequenceGenerator);
		if(sequenceGenerator.getSequenceGeneratorId() == null) {
			sequenceGeneratorMapper.insert(sequenceGenerator);
			String sequenceName = getSequenceGeneratorName(sequenceGenerator);
			String maxValue = getMaxValue(sequenceGenerator);
			sequenceGeneratorFinder.executeSequenceGenerator(sequenceName, String.valueOf(sequenceGenerator.getSeqIncrement()), maxValue, String.valueOf(sequenceGenerator.getSequenceStartFrom()));
		} else {
			sequenceGeneratorMapper.updateByPrimaryKey(sequenceGenerator);
		}
		
	}
	
	private String getMaxValue(SequenceGenerator sequenceGenerator) {
		String maxValue = "";
		for(int i = 0; i < sequenceGenerator.getLength(); i++) {
			maxValue += SequenceGeneratorType.MAXVALUE_CHAR.getDesc();
		}
		return maxValue;
	}
	
	@Override
	public String getSequenceGeneratorName(SequenceGenerator sequenceGenerator) {
		return SequenceGeneratorType.PREFIX.getDesc() + sequenceGenerator.getGroupId().toString() + "_" + sequenceGenerator.getSequenceGeneratorId() + "_" + (sequenceGenerator.getCompanyScope() < 0 ? "0" : String.valueOf(sequenceGenerator.getCompanyScope()));
	}
	
	@Override
	@Transactional(readOnly=true)
	public int countByExample(SequenceGeneratorExample example) {
		return sequenceGeneratorMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountSequenceGeneratorDtoBySequenceGeneratorId(
			String sequenceGeneratorId) {
		return sequenceGeneratorFinder.getCountSequenceGeneratorBySequenceGeneratorId(sequenceGeneratorId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<SequenceGeneratorDTO> getSequenceGeneratorDtoBySequenceGeneratorId(
			String sequenceGeneratorId, int limit, int offset) {
		return sequenceGeneratorFinder.getSequenceGeneratorBySequenceGeneratorIdWithRowBounds(sequenceGeneratorId, new RowBounds(offset,limit));
	}

	@Override
	@Transactional(readOnly=true)
	public List<SequenceGenerator> getSequenceGeneratorByExample(
			SequenceGeneratorExample example, int limit,
			int offset) {
		return sequenceGeneratorMapper.selectByExampleWithRowbounds(example, new RowBounds(offset,limit));
	}

	@Override
	@Transactional(readOnly=true)
	public List<SequenceGenerator> getSequenceGeneratorByExample(
			SequenceGeneratorExample example) {
		return sequenceGeneratorMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isExistSequenceGeneratorForUpdate(SequenceGenerator record) {
		SequenceGeneratorExample countSequenceGeneratorById = new SequenceGeneratorExample();
		countSequenceGeneratorById.createCriteria().andSequenceGeneratorIdEqualTo(record.getSequenceGeneratorId());
		if(countByExample(countSequenceGeneratorById) > 0) {
				return false;
			} else {
				return true;
			}
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountSequenceGeneratorDtoByTrxCode(String trxCode, Long groupId) {
		return sequenceGeneratorFinder.getCountSequenceGeneratorDtoByTrxCode(trxCode, groupId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<SequenceGeneratorDTO> getSequenceGeneratorDtoByTrxCode(
			String trxCode, Long groupId) {
		return sequenceGeneratorFinder.getSequenceGeneratorByTrxCodeWithRowBounds(trxCode, groupId);
	}

	@Override
	@Transactional
	public void resetSequenceGenerator(SequenceGenerator sequenceGenerator) {
		String sequenceName = getSequenceGeneratorName(sequenceGenerator);
		sequenceGeneratorFinder.dropSequenceGenerator(sequenceName);
		sequenceGeneratorFinder.executeSequenceGenerator(sequenceName, String.valueOf(sequenceGenerator.getSeqIncrement()), getMaxValue(sequenceGenerator), String.valueOf(sequenceGenerator.getSequenceStartFrom()));
	}

}
