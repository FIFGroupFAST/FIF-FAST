package co.id.fifgroup.eligibility.finder;

public interface FactTypeCacheSynchronizeMapper {

	void deleteFactTypeCache(Long companyId);
	void populateEmployee(Long companyId);
	void synchronizeCOP();
	void synchronizeCOP2();
	void synchronizeMOP();
	void synchronizeMOP2();
	void synchronizeCashable();
	void synchronizeOvertime();
	void synchronizeOPEX();
	void synchronizeOPEX2();
	void synchronizeOCOP();
	void synchronizeUnpaidLeave();
	void synchronizeUnpaidAbsent();
}
