package co.id.fifgroup.systemadmin.batch;

import java.util.List;

import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.systemadmin.dto.UserDTO;
import co.id.fifgroup.systemadmin.dto.UserResponsibilityDTO;
import co.id.fifgroup.systemadmin.service.UserService;

public class DirectResponsibilityNotification extends ExecutableTask{

	@Override
	public void execute() throws TaskExecutionException {
		// TODO Auto-generated method stub
		debug("Executing Direct Responsibility Notification Task");
		debug("Getting users who has direct resposnibility");
		List<UserDTO> users = getUserService().getUserWithHasDirectResposnibility();
		debug(users.size()+" users found");
		debug("Preparing to send notification");
		for (UserDTO userDTO : users) {
			debug("Sending for user "+userDTO.getUserName());
			for (UserResponsibilityDTO uresp : userDTO.getJobResponsibilityDto()) {
				debug("-----Responsibility : "+uresp.getDirectResponsibilityName()+" ; Remark : "+uresp.getRemark());
				//TODO: Send message to HC
			}
		}
		
	}
	
	private UserService getUserService(){
		return (UserService) getApplicationContext().getBean("userService");
	}
	
	
}
