package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.TaskParameterValue;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetail;
import co.id.fifgroup.systemadmin.dto.TaskRequestDTO;

public interface TaskRequestFinder {
	
	public List<TaskRequestDTO> selectByExampleWithRowbounds(@Param("example") TaskRequestDTO example, @Param("rowBounds")RowBounds rowBounds);
	public List<TaskRequestDTO> selectByExampleWithRowbounds(@Param("example") TaskRequestDTO example);
	
	public int countTaskRequestDtoByExample(@Param("example")TaskRequestDTO example);
	public List<TaskRequestDetail> getTaskRequestDetailToBeCanceled(
			@Param("parentRequestId")String parentRequestId,
			@Param("taskRequestId")Long taskRequestId,
			@Param("taskPhase")String taskPhase);
	
	public List<TaskParameterValue> getTaskParameterValuesByTaskRunnerId(@Param("id") Long id);
	
	public TaskRequestDetail getScheduledOrPendingFirstTaskRequestDetailByTaskRequestId(@Param("id")Long id);

	public Boolean isJobCompleted(@Param("parentRequestId")String parentRequestId);
}
