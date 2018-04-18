package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.CompanyContactMapper;
import co.id.fifgroup.basicsetup.domain.CompanyContact;
import co.id.fifgroup.basicsetup.domain.CompanyContactExample;
import co.id.fifgroup.basicsetup.service.CompanyContactService;

@Transactional
@Service
public class CompanyContactServiceImpl implements CompanyContactService {

	@Autowired
	private CompanyContactMapper companyContactMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<CompanyContact> getCompanyContactByCompanySeqNum(Long companySeqNum) {
		CompanyContactExample companyContactExample = new CompanyContactExample();
		companyContactExample.createCriteria().andCompanySeqNumEqualTo(companySeqNum);
		return companyContactMapper.selectByExample(companyContactExample);
	}

}
