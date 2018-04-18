package co.id.fifgroup.systemadmin.service;

import java.util.List;

import co.id.fifgroup.systemadmin.domain.TaskGroup;
import co.id.fifgroup.systemadmin.domain.TaskGroupExample;
import co.id.fifgroup.systemadmin.dto.TaskGroupDTO;
import co.id.fifgroup.systemadmin.dto.TaskGroupDetailDTO;

public interface TaskGroupService {

	public List<TaskGroup> getTaskGroupByExample(TaskGroupExample taskGroupExample, int limit, int offset);
	
	public List<TaskGroupDTO> getTaskGroupByExampleByRowBounds(TaskGroup taskGroup, int limit, int offset);
	
	public List<TaskGroupDetailDTO> getTaskGroupIdAndRunnerName(Long id);
	
	public void save(TaskGroupDTO taskGroupDto) throws Exception;
	
	public TaskGroup getTaskGroupById(Long id);
	
	public int countByExample(TaskGroupExample example);
	
}
