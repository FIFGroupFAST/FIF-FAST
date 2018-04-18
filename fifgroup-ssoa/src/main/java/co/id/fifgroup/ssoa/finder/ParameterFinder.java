package co.id.fifgroup.ssoa.finder;

import co.id.fifgroup.ssoa.domain.Parameter;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.dto.ParameterDetailDTO;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.annotations.Param;

public interface ParameterFinder {
	
	List<ParameterDTO> selectByExample(@Param("example")Parameter example, @Param("rowBounds")RowBounds rowBounds,@Param("taskGroupId") Long taskGroupId);
	
	List<ParameterDetailDTO> selectDetailByHeaderId(@Param("id") Long id);
	
	int countByExample(@Param("example")Parameter example,@Param("taskGroupId") Long taskGroupId);
	
	
}
