package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.CompanyOtherInfoValueMapper;
import co.id.fifgroup.basicsetup.domain.CompanyOtherInfoValue;
import co.id.fifgroup.basicsetup.domain.CompanyOtherInfoValueExample;
import co.id.fifgroup.basicsetup.service.CompanyOtherInfoValueService;

@Transactional
@Service
public class CompanyOtherInfoValueServiceImpl implements CompanyOtherInfoValueService {

	@Autowired
	private CompanyOtherInfoValueMapper companyOtherInfoValueMapper;
	
	@Override
	@Transactional
	public void save(List<CompanyOtherInfoValue> companyOtherInfoValue) {
		for(CompanyOtherInfoValue companyOtherInfoValueSelected : companyOtherInfoValue) {
			companyOtherInfoValueMapper.insert(companyOtherInfoValueSelected);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<CompanyOtherInfoValue> getCompanyOtherInfoValueByCompanySeqNum(Long companySeqNum) {
		CompanyOtherInfoValueExample companyOtherInfoValueExample = new CompanyOtherInfoValueExample();
		companyOtherInfoValueExample.createCriteria().andCompanySeqNumEqualTo(companySeqNum);
		return companyOtherInfoValueMapper.selectByExample(companyOtherInfoValueExample);
	}

}
