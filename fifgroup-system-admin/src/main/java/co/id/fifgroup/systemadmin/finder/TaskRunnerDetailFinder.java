package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;

public interface TaskRunnerDetailFinder {
	
	List<TaskRunnerDetailDTO> getTaskRunnerDetailsByTaskRunnerId(@Param("id")Long id);

}
