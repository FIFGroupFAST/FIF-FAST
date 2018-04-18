package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.ExecutableFile;
import co.id.fifgroup.systemadmin.dto.ExecutableFileDTO;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;

public interface TaskFinder {
	
	List<ExecutableFile> selectExecutableFileByExample(ExecutableFileDTO example, RowBounds rowBounds);
	
	Long getExecutableFileIdByExecutableFileName(String name);
	
	List<TaskParameterDTO> selectTaskParameterByTaskId(Long id);
	
	List<TaskParameterDTO> getTaskParametersByTaskId(Long id);
	
	List<TaskParameterDTO> getTaskParametersNonStaticByTaskId(Long id);
	
	TaskDTO getTaskDtoById(Long id);
	
	List<TaskDTO> findTaskByExample(TaskDTO taskDTO, RowBounds rowBounds);
	
}
