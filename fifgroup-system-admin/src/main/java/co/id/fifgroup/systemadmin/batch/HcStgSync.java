package co.id.fifgroup.systemadmin.batch;

import java.util.List;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.util.Stream;
import co.id.fifgroup.core.validation.TaskExecutionException;
import co.id.fifgroup.systemadmin.domain.HcstgTable;
import co.id.fifgroup.systemadmin.service.HcstgSyncService;

public class HcStgSync extends ExecutableTask {
	private HcstgSyncService hcstgSyncServiceImpl;

	@Override
	public void execute() throws TaskExecutionException {
		try {
			hcstgSyncServiceImpl = (HcstgSyncService) getApplicationContext().getBean("hcstgSyncServiceImpl");
			Stream stream = new Stream();
			stream.start();
			
			List<HcstgTable> hcstgTables = hcstgSyncServiceImpl.getHcstgTables();

			for (HcstgTable table : hcstgTables) {
				HcstgTable hcstgTable = hcstgSyncServiceImpl.getRowNum(table);
				if(hcstgTable.getRowNum().doubleValue() < 5000){
				hcstgSyncServiceImpl.truncateTable(table);
				}else{
					double data = hcstgTable.getRowNum()/5000;
					long value = Math.round(data)+1;
					for(int i=0; i<value; i++){
						table.setRowNum(new Long(5000));
						hcstgSyncServiceImpl.truncateTableWithRowNum(table);
					}
				}
				hcstgSyncServiceImpl.insertTable(table);
			}

			
			stream.stop("Sync Stop");

		} catch (Exception e) {
			throw new TaskExecutionException(e.getMessage());
		}
	}
}
