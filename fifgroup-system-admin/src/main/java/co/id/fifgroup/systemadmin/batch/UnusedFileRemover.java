package co.id.fifgroup.systemadmin.batch;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetail;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetailExample;
import co.id.fifgroup.systemadmin.service.TaskRequestService;

public class UnusedFileRemover extends ExecutableTask{
	
	
	
	@Override
	public void execute() throws TaskExecutionException {
		
		debug("Executing Unused File Remover..");
		int deltaTime = (int) getParameter().get("DELTATIME");
		TaskRequestDetailExample example = new TaskRequestDetailExample();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -deltaTime);
		example.createCriteria().andDateCompletedLessThanOrEqualTo(calendar.getTime()).
		andOutputLogIsNotNull();
		debug("Getting list of file..");
		List<TaskRequestDetail> taskRequestDetails = getTaskRequestService().getTaskRequestDetails(example);
		debug(taskRequestDetails.size()+" files found");
		debug("Preparing to delete files..");
		for (TaskRequestDetail taskRequestDetail : taskRequestDetails) {
			deleteFile(taskRequestDetail.getOutputLog());
			if(taskRequestDetail.getOutputFile() != null)
				deleteFile(taskRequestDetail.getOutputFile());
		}
		debug("Finished!");
	}
	
	private void deleteFile(String path){
		File f = new File(path);
		if(f.exists()){
			debug("deleting file "+path);
			f.delete();
			debug("file deleted");
		}
		
	}
	
	
	public TaskRequestService getTaskRequestService(){
		return (TaskRequestService) getApplicationContext().getBean("taskRequestService");
	}

}
