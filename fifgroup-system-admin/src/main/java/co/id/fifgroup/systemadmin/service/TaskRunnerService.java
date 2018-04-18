package co.id.fifgroup.systemadmin.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerDetail;
import co.id.fifgroup.systemadmin.domain.TaskRunnerDetailExample;
import co.id.fifgroup.systemadmin.domain.TaskRunnerExample;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;

public interface TaskRunnerService {
	
	public void save(TaskRunnerDTO taskRunnerDto) throws Exception;
	
	public List<TaskRunnerDTO> getTaskRunnerDtoByExample(TaskRunner example, int limit, int offset, Long taskGroupId);
	
	public TaskRunnerDTO getTaskRunnerById(Long id);
	
	public List<TaskRunner> getTaskRunnerByExample(TaskRunnerExample example, int limit, int offset);
	public List<TaskRunner> getTaskRunnerByExample(TaskRunnerExample example);
	
	public int countTaskRunnerByExample(TaskRunnerExample example, Long taskGroupId);
	public int countByExample(TaskRunner example, Long taskGroupId);
	
	public List<TaskRunnerDetail> getTaskRunnerDetailByTaskRunnerId(Long id);
	public List<TaskRunnerDetailDTO> getTaskRunnerDetailDtoByTaskRunnerId(Long id);
	
	public void updateTaskRequestDetailByPrimaryKey(TaskRunnerDetail taskRunnerDetail);
	
	public List<TaskRunnerDetail> getTaskRunnerDetailByExample(TaskRunnerDetailExample example);
	
	public List<TaskRunnerDTO> getBatchByExample(TaskRunner example, int limit, int offset, Long taskGroupId);
	
	public int countBatchByExample(TaskRunner example, Long taskGroupId);
}
