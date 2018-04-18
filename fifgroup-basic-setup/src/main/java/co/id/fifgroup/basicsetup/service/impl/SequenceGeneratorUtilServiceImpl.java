package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.dao.SequenceGeneratorMapper;
import co.id.fifgroup.basicsetup.domain.SequenceGenerator;
import co.id.fifgroup.basicsetup.domain.SequenceGeneratorExample;
import co.id.fifgroup.basicsetup.service.SequenceGeneratorService;
import co.id.fifgroup.basicsetup.service.SequenceGeneratorUtilService;
import co.id.fifgroup.core.service.SequenceGeneratorUtilServiceAdapter;
import co.id.fifgroup.core.util.IdGenerator;

@Transactional
@Service
public class SequenceGeneratorUtilServiceImpl implements SequenceGeneratorUtilService, SequenceGeneratorUtilServiceAdapter {

	@Autowired
	private SequenceGeneratorMapper sequenceGeneratorMapper;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorServiceImpl;
	@Autowired
	private IdGenerator idGenerator;
	
	@Override
	@Transactional(readOnly=true)
	public String getSequenceGeneratorFormat(Long groupId, Long scope,
			String trxCode) {
		SequenceGeneratorExample sequenceGeneratorExampleCompany = new SequenceGeneratorExample();
		SequenceGeneratorExample.Criteria criteriaSequenceGeneratorCompany = sequenceGeneratorExampleCompany.createCriteria();
		criteriaSequenceGeneratorCompany.andGroupIdEqualTo(groupId).andCompanyScopeEqualTo(scope).andTrxCodeEqualTo(trxCode);
		List<SequenceGenerator> sequenceGeneratorsCompany = sequenceGeneratorMapper.selectByExample(sequenceGeneratorExampleCompany);
		if(sequenceGeneratorsCompany.size() == 1) {
			SequenceGenerator sequenceGeneratorCompany = sequenceGeneratorsCompany.get(0);
			Long nextSeq = idGenerator.getNextHeaderId(sequenceGeneratorServiceImpl.getSequenceGeneratorName(sequenceGeneratorCompany));
			return getFormat(sequenceGeneratorCompany, nextSeq);
		} else {
			SequenceGeneratorExample sequenceGeneratorExampleCommon = new SequenceGeneratorExample();
			SequenceGeneratorExample.Criteria criteriaSequenceGeneratorCommon = sequenceGeneratorExampleCommon.createCriteria();
			criteriaSequenceGeneratorCommon.andGroupIdEqualTo(groupId).andCompanyScopeEqualTo(ScopeType.COMMON.getValue()).andTrxCodeEqualTo(trxCode);
			List<SequenceGenerator> sequenceGeneratorsCommon = sequenceGeneratorMapper.selectByExample(sequenceGeneratorExampleCommon);
			if(sequenceGeneratorsCommon.size() == 1) {
				SequenceGenerator sequenceGeneratorCommon = sequenceGeneratorsCommon.get(0);
				Long nextSeq = idGenerator.getNextHeaderId(sequenceGeneratorServiceImpl.getSequenceGeneratorName(sequenceGeneratorCommon));
				return getFormat(sequenceGeneratorCommon, nextSeq);
			} else {
				return null;
			}
		}
	}

	private String getFormat(SequenceGenerator sequenceGenerator, Long runningNumber) {
		return (sequenceGenerator.getPrefix() == null ? "" : sequenceGenerator.getPrefix()) + lpad(runningNumber, sequenceGenerator.getLength(), sequenceGenerator.getPaddingChar());
	}
	
	private String lpad(Long runningNumber, Integer length, String paddingChar) {
		int lengthRunningNumber = String.valueOf(runningNumber).length();
		String format = "";
		if(paddingChar != null) {
			for(int i = 0; i < (length.intValue() - lengthRunningNumber); i++) {
				format += paddingChar;
			}			
		}
		return format + String.valueOf(runningNumber);
	}
	
}
