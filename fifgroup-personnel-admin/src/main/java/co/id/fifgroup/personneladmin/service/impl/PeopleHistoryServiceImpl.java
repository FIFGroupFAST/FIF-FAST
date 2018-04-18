package co.id.fifgroup.personneladmin.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.personneladmin.service.PeopleHistoryService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.personneladmin.dao.PeopleHistoryMapper;
import co.id.fifgroup.personneladmin.domain.PeopleHistory;
import co.id.fifgroup.personneladmin.domain.PeopleHistoryExample;

@Transactional
@Service
public class PeopleHistoryServiceImpl implements PeopleHistoryService {

	@Autowired
	private PeopleHistoryMapper peopleHistoryMapper;
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Override
	public List<PeopleHistory> selectByExample(PeopleHistoryExample example) {
		return peopleHistoryMapper.selectByExample(example);
	}

	@Override
	public void save(PeopleHistory peopleHistory) {
		peopleHistoryMapper.insert(peopleHistory);
	}

	@Override
	public void deleteByPersonIdAndCompanyId(Long personId, Long companyId) {
		PeopleHistoryExample example = new PeopleHistoryExample();
		example.createCriteria().andPersonIdEqualTo(personId).andCompanyIdEqualTo(companyId);
		peopleHistoryMapper.deleteByExample(example);
	}
	
	@Override
	public void savePeopleHistory(Long personId, Long companyId, PeopleHistory peopleHistory) {
		Long createdBy = securityServiceImpl.getSecurityProfile() == null ? -1L : securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();
		peopleHistory.setPersonId(personId);
		peopleHistory.setCompanyId(companyId);
		peopleHistory.setCreatedBy(createdBy);
		peopleHistory.setCreationDate(createdDate);
		peopleHistory.setLastUpdatedBy(createdBy);
		peopleHistory.setLastUpdateDate(createdDate);
		save(peopleHistory);
	}

	@Override
	public void updateByExample(PeopleHistory peopleHistory,
			PeopleHistoryExample example) {
		peopleHistoryMapper.updateByExampleSelective(peopleHistory, example);
	}

}
