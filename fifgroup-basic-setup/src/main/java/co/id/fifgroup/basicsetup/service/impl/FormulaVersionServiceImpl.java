package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.FormulaVersionMapper;
import co.id.fifgroup.basicsetup.domain.FormulaVersion;
import co.id.fifgroup.basicsetup.domain.FormulaVersionExample;
import co.id.fifgroup.basicsetup.service.FormulaVersionService;

@Transactional
@Service
public class FormulaVersionServiceImpl implements FormulaVersionService{
	
	@Autowired
	private FormulaVersionMapper formulaVersionMapper;

	@Override
	@Transactional(readOnly=true)
	public List<FormulaVersion> getFormulaVersionByExample(
			FormulaVersionExample example) {
		return formulaVersionMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountFormulaVersionByExample(FormulaVersionExample example) {
		return formulaVersionMapper.countByExample(example);
	}

}
