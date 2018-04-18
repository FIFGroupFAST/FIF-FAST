package co.id.fifgroup.systemadmin.service;

import java.io.InputStream;
import java.util.List;

import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.systemadmin.domain.ExecutableFile;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.domain.TaskExample;
import co.id.fifgroup.systemadmin.domain.TaskParameter;
import co.id.fifgroup.systemadmin.domain.TaskParameterExample;
import co.id.fifgroup.systemadmin.dto.ExecutableFileDTO;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;

public interface TaskService {
	
	public List<ExecutableFile> getExecutableFileByExample(ExecutableFileDTO example,  int offset, int limit);
	
	public void save(TaskDTO taskDto) throws Exception;
	
	public List<Task> getTaskByExample(TaskExample example,  int limit, int offset);
	
	public List<TaskDTO> findTaskByExample(TaskDTO taskDTO,  int limit, int offset);
	
	public TaskDTO getTaskById(Long id);
	
	public int countByExample(TaskExample example);
	
	public List<ExecutableFile> getExecutableFileByExecutableFileName(String name);
	
	public void uploadExecutableFile(ExecutableFile executableFile, InputStream inputStream) throws Exception;
	
	public void uploadExecutableFile(ExecutableFile executableFile);
	public void updateExecutableFile(ExecutableFile executableFile);
	
	public void uploadFile(String moduleName, String fileName, InputStream inputStream);
	
	public void validateExecutableFile(ExecutableFile executableFile) throws ValidationException;
	
	public Task getEndOfTask();
	
	public int countTaskParameterByExample(TaskParameterExample example);
	
	public List<TaskParameter> getTaskParameterByExample(TaskParameterExample example);
	
	public List<TaskParameterDTO> getTaskParametersByTaskId(Long id);
	
	public Task getTaskByPrimaryKey(Long id);
	
}
