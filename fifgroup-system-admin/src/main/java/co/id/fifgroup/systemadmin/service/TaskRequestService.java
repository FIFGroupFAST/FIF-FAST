package co.id.fifgroup.systemadmin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import co.id.fifgroup.systemadmin.domain.ExecutableFile;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.domain.TaskParameter;
import co.id.fifgroup.systemadmin.domain.TaskParameterValue;
import co.id.fifgroup.systemadmin.domain.TaskRequest;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetail;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetailExample;
import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerDetail;
import co.id.fifgroup.systemadmin.dto.Schedule;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;
import co.id.fifgroup.systemadmin.dto.TaskRequestDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;

public interface TaskRequestService {
 
	public void save(TaskRequestDTO taskRequestDto) throws Exception;
	public int countTaskRequestDtoByExample(TaskRequestDTO example);
	public boolean isRequiredEntryTaskParameter(TaskRunner taskRunner);
	public void cancelJob(String jobkey, Long taskRequestId, String taskPhase);
	public String getTaskRequestParentRequestIdByTaskRequestId(Long id);
	public List<TaskRequestDTO> getTaskRequestByExample(TaskRequestDTO example,int limit, int offset);
	public List<TaskRequestDTO> getTaskRequestByExample(TaskRequestDTO example);
	public List<TaskRunnerDetail> getTaskRunnerDetailByTaskRunnerId(Long id);
	public List<TaskParameter> getTaskParameterByTaskRunnerId(Long id);
	public List<TaskParameterDTO> getTaskParameterByTaskId(Long id);
	public List<Task> getTaskByTaskRunnerId(Long id);
	public TaskRequest getTaskRequestByTaskRequestId(Long id);
	public int updateTaskReqeuest(TaskRequest taskRequest);
	public TaskParameterValue getTaskParameterValueByTaskParameterIdAndRequestId(
			Long taskParameterId, Long requestId);
	public TaskRequestDetail getTaskRequestDetailByExample(TaskRequestDetailExample example);
	public String getOutputFilePathByTaskRequestId(Long requestId);
	public Task getTaskByTaskId(Long id);
	public List<TaskRequestDetail> getTaskRequestDetails(TaskRequestDetailExample exampl);
	
	public List<TaskParameterValue> getTaskParameterValuesByTaskRunnerId(Long id);
	
	public void cancelTask(String keyJob);
	
	public void deleteTask(String keyJob);
	
	public void saveTaskRequestDetail(TaskRequestDetail requestDetail);
	public void updateTaskRequestDetail(TaskRequestDetail requestDetail);
	public TaskRequestDetail getTaskRequestDetailByTaskRequestDetailId(Long id);
	public TaskRequestDetail getScheduledOrPendingFirstTaskRequestDetailByTaskRequestId(Long id);
	
	public ExecutableFile executableFileBuilder(String executableFileName, Long moduleId, String taskType);
//	public TaskParameterValue taskParameterValueBuilder(Object value);
	public TaskParameterDTO taskParameterBuilder(String dataType, Object value, String key);
	public TaskDTO taskBuilder(List<TaskParameterDTO> taskParameters, String className, ExecutableFile executableFile);
	public TaskRunnerDetailDTO taskRunnerDetailBuilder(TaskDTO mainTask, TaskDTO successTask, TaskDTO errorTask, int sequence);
	public TaskRunnerDTO TaskRunnerBuilder(Date dateFrom, Date dateTo, String executionType, List<TaskRunnerDetailDTO> taskRunnerDetails);
	public TaskRequestDetail taskRequestDetailBuilder(Date dateStarted, Date dateCompleted, String outputLogPath,String outputFilePath);
	public String buildSchedule(List<TaskDTO> tasks, Schedule schedule, String taskExecutionType);
	public String buildScheduleWithCurrentRunner(String taskRunnerName, String className, Schedule schedule, String taskExecutionType, List<TaskParameterDTO> parameters);
	public Date getXthfireTimeDateByJobKey(String jobKey, int times, Date startDate);
	public void buildImmediatelySchedule(String taskRunnerName, Map<String, Object> param) throws Exception;
	void deleteJob(String jobkey, Long taskRequestId, String taskPhase);
	public boolean isAnyRunning(Long userId, String taskRunnerName);
	public int getFireTime(Long taskRequestId);
	
}
