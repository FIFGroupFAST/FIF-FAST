package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;

public interface TaskRunnerFinder {
	
	List<TaskRunnerDTO> selectByExample(@Param("example")TaskRunner example, @Param("rowBounds")RowBounds rowBounds,@Param("taskGroupId") Long taskGroupId);
	
	List<TaskRunnerDetailDTO> selectDetailByHeaderId(@Param("id") Long id);
	
	int countByExample(@Param("example")TaskRunner example,@Param("taskGroupId") Long taskGroupId);
	
	List<TaskRunnerDTO> selectBatchByExample(@Param("example")TaskRunner example, @Param("rowBounds")RowBounds rowBounds,@Param("taskGroupId") Long taskGroupId);
	
	int countBatchByExample(@Param("example")TaskRunner example,@Param("taskGroupId") Long taskGroupId);
}
