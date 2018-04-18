package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.CompanyOtherInfoValue;

public interface CompanyOtherInfoValueService {

	public void save(List<CompanyOtherInfoValue> companyOtherInfoValue);
	public List<CompanyOtherInfoValue> getCompanyOtherInfoValueByCompanySeqNum(Long companySeqNum);
	
}
