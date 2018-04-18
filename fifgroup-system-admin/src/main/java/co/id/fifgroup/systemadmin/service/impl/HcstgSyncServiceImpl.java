package co.id.fifgroup.systemadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.systemadmin.domain.HcstgTable;
import co.id.fifgroup.systemadmin.finder.HcstgSyncMapper;
import co.id.fifgroup.systemadmin.service.HcstgSyncService;

@Transactional
@Service
public class HcstgSyncServiceImpl implements HcstgSyncService{

	@Autowired
	private HcstgSyncMapper hcstgSyncMapper;
	
	@Override
	public void truncateTable(HcstgTable hcstgTable) {
		hcstgSyncMapper.truncateTable(hcstgTable);
	}

	@Override
	public void insertTable(HcstgTable hcstgTable) {
		hcstgSyncMapper.insertTable(hcstgTable);
	}

	@Override
	public List<HcstgTable> getHcstgTables() {
		return hcstgSyncMapper.getHcstgTables();
	}
	
    public HcstgTable getRowNum(HcstgTable hcstgTable){
    	return hcstgSyncMapper.getRowNum(hcstgTable);
    }
	
	public void truncateTableWithRowNum(HcstgTable hcstgTable){
		hcstgSyncMapper.truncateTableWithRowNum(hcstgTable);
	}


	
}
