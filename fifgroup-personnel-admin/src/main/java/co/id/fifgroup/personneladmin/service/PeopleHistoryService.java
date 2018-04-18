package co.id.fifgroup.personneladmin.service;

import java.util.List;

import co.id.fifgroup.personneladmin.domain.PeopleHistory;
import co.id.fifgroup.personneladmin.domain.PeopleHistoryExample;

public interface PeopleHistoryService {

	public List<PeopleHistory> selectByExample(PeopleHistoryExample example);
	
	public void save(PeopleHistory peopleHistory);
	
	public void deleteByPersonIdAndCompanyId(Long personId, Long companyId);
	
	public void savePeopleHistory(Long personId, Long companyId, PeopleHistory peopleHistory);
	
	public void updateByExample(PeopleHistory peopleHistory, PeopleHistoryExample example);
}
