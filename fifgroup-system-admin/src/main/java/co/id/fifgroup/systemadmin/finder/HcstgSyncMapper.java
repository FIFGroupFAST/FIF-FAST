package co.id.fifgroup.systemadmin.finder;

import co.id.fifgroup.systemadmin.domain.HcstgTable;

import java.util.List;

public interface HcstgSyncMapper {

	public List<HcstgTable> getHcstgTables();

	public void truncateTable(HcstgTable hcstgTable);

	public void insertTable(HcstgTable hcstgTable);
	
	public HcstgTable getRowNum(HcstgTable hcstgTable);
	
	public void truncateTableWithRowNum(HcstgTable hcstgTable);

}
