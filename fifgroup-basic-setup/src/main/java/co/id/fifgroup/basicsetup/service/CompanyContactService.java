package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.CompanyContact;

public interface CompanyContactService {

	public List<CompanyContact> getCompanyContactByCompanySeqNum(Long companySeqNum);
	
}
