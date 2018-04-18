package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.FormulaUsageFinder;
import co.id.fifgroup.basicsetup.dao.FormulaUsageMapper;
import co.id.fifgroup.basicsetup.domain.FormulaUsage;
import co.id.fifgroup.basicsetup.domain.FormulaUsageExample;
import co.id.fifgroup.basicsetup.dto.FormulaUsageDTO;
import co.id.fifgroup.basicsetup.service.FormulaUsageService;

@Transactional
@Service
public class FormulaUsageServiceImpl implements FormulaUsageService {

	@Autowired
	private FormulaUsageFinder formulaUsageFinder;
	@Autowired
	private FormulaUsageMapper formulaUsageMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<FormulaUsageDTO> getFormulaUsageDtoByFormulaName(String formulaName) {
		return formulaUsageFinder.getFormulaUsageDtoByFormulaName(formulaName);
	}

	@Override
	@Transactional(readOnly=true)
	public List<FormulaUsage> getFormulaUsageByExample(FormulaUsageExample formulaUsageExample) {
		return formulaUsageMapper.selectByExample(formulaUsageExample);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountFormulaUsageByExample(FormulaUsageExample formulaUsageExample) {
		return formulaUsageMapper.countByExample(formulaUsageExample);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountFormulaUsageDtoByFormulaName(String formulaName) {
		return formulaUsageFinder.getCountFormulaUsageDtoByFormulaName(formulaName);
	}

	@Override
	@Transactional
	public void save(FormulaUsage formulaUsage) {
		formulaUsageMapper.insert(formulaUsage);
	}

}
