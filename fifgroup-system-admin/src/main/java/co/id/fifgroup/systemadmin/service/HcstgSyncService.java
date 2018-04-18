package co.id.fifgroup.systemadmin.service;

import java.util.List;

import co.id.fifgroup.systemadmin.domain.HcstgTable;

public interface HcstgSyncService {
	
	public List<HcstgTable> getHcstgTables();
	
	public void truncateTable(HcstgTable hcstgTable);

	public void insertTable(HcstgTable hcstgTable);
	
	public HcstgTable getRowNum(HcstgTable hcstgTable);
	
	public void truncateTableWithRowNum(HcstgTable hcstgTable);

}
