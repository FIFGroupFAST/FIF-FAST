package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.TaskGroup;
import co.id.fifgroup.systemadmin.dto.TaskGroupDTO;
import co.id.fifgroup.systemadmin.dto.TaskGroupDetailDTO;

public interface TaskGroupFinder {
	
	List<TaskGroupDetailDTO> getTaskGroupIdAndRunnerName(Long id);
	
	void deleteByTaskGroupId(@Param("taskGroupId") Long id);
	
	List<TaskGroupDTO> findByExampleWithRowbounds(TaskGroup taskGroup, RowBounds rowBounds);

}
