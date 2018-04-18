package co.id.fifgroup.systemadmin.batch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.Stream;
import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.systemadmin.constant.ResponsibilityType;
import co.id.fifgroup.systemadmin.dao.UserResponsibilityMapper;
import co.id.fifgroup.systemadmin.domain.UserResponsibility;
import co.id.fifgroup.systemadmin.domain.UserResponsibilityExample;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDetailDTO;
import co.id.fifgroup.systemadmin.dto.UserDTO;
import co.id.fifgroup.systemadmin.service.JobResponsibilityService;
import co.id.fifgroup.systemadmin.service.UserService;

public class JobResponsibilitySynchronizer extends ExecutableTask{

	
	@Override
	public void execute() throws TaskExecutionException {
		debug("Executing task..");

		String userName = (String)getParameter().get("USER_NAME");
		
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		if(!userName.equals("")){
			userDTOs = getUserService().getActiveUserWithJobId(userName);
		}else{
			userDTOs = getUserService().getActiveUserWithJobId(null);
		}
		
		
		debug("Total user : "+userDTOs.size());
		debug("==========Synchronizing..=========");
		Stream stream = new Stream();
		for (UserDTO userDTO : userDTOs) {
			stream.start();
			List<JobResponsibilityDetailDTO> jobResponsibilities = getJobResponsibilityService().getActiveJobResponsibilityByJobId(userDTO.getJobId());
			List<JobResponsibilityDetailDTO> responsibilityExist = new ArrayList<>();
			
			UserResponsibilityExample userRespExp = new UserResponsibilityExample();
			userRespExp.createCriteria().andUserIdEqualTo(userDTO.getId());
			List<UserResponsibility> userResponsibilities = getUserResponsibilityMapper().selectByExample(userRespExp);
			for (UserResponsibility userResponsibility : userResponsibilities) {
				boolean found = false;
				for (JobResponsibilityDetailDTO jobRespDetail : jobResponsibilities) {
					//update when existing
					if(jobRespDetail.getResponsibilityId().equals(userResponsibility.getResponsibilityId())) {
						found = true;
						responsibilityExist.add(jobRespDetail);
						if (userResponsibility.getDateFrom().compareTo(DateUtil.truncate(new Date())) > 0) {
							userResponsibility.setDateFrom(DateUtil.truncate(new Date()));
						} else {
							userResponsibility.setDateFrom(DateUtil.truncate(jobRespDetail.getDateFrom()));
						}
						userResponsibility.setDateTo(jobRespDetail.getDateTo());
						break;
					}
				}
				
				if (found) {
					getUserResponsibilityMapper().updateByPrimaryKey(userResponsibility);
				} else if (userResponsibility.getResponsibilityType().equalsIgnoreCase(ResponsibilityType.JOB.name())) {
					userResponsibility.setDateTo(DateUtil.add(DateUtil.truncate(new Date()), Calendar.DATE, -1));
					getUserResponsibilityMapper().updateByPrimaryKey(userResponsibility);
				}
			}
			
			jobResponsibilities.removeAll(responsibilityExist);
			
			for (JobResponsibilityDetailDTO jobResponsibilityDetailDTO : jobResponsibilities) {
				UserResponsibility userResponsibility = new UserResponsibility();
				userResponsibility.setUserId(userDTO.getId());
				userResponsibility.setResponsibilityType(ResponsibilityType.JOB.toString());
				userResponsibility.setResponsibilityId(jobResponsibilityDetailDTO.getResponsibilityId());
				userResponsibility.setDateFrom(DateUtil.truncate(new Date()));
				userResponsibility.setDateTo(jobResponsibilityDetailDTO.getDateTo());
				userResponsibility.setCreatedBy(-1L);
				userResponsibility.setCreationDate(new Date());
				userResponsibility.setLastUpdatedBy(-1L);
				userResponsibility.setLastUpdateDate(new Date());
				getUserService().saveUserResponsibility(userResponsibility);
			}
			debug(stream.stop(userDTO.getUserName()));
		}
		
		debug("=========End of process==========");
		
	}
	
	
	private JobResponsibilityService getJobResponsibilityService(){
		return (JobResponsibilityService) getApplicationContext().getBean("jobResponsibilityService");
	}
	
	private UserService getUserService(){
		return (UserService) getApplicationContext().getBean("userService");
	}
	
	private UserResponsibilityMapper getUserResponsibilityMapper(){
		return (UserResponsibilityMapper) getApplicationContext().getBean("userResponsibilityMapper");
	}

}
