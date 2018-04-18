package co.id.fifgroup.core.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.dto.ActivityDTO;


public interface ActivityFinder {
	
	List<ActivityDTO> findByExample(ActivityDTO example);

	List<ActivityDTO> findByExampleByBandbox(@Param("activityCode")String activityCode,@Param("activityName")String activityName,RowBounds rowBounds);
	
	Integer countByExample(@Param("activityCode")String activityCode,@Param("activityName")String activityName);
	
	ActivityDTO selectBudgetActivityByCode(@Param("activityCode")String activityCode);
}
