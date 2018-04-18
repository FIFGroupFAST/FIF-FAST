package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.Function;
import co.id.fifgroup.systemadmin.dto.FunctionDTO;

public interface FunctionFinder {

	List<FunctionDTO> findByExample(FunctionDTO functionDTO, RowBounds rowBounds);
	
	List<FunctionDTO> findByExample(FunctionDTO functionDTO);
	
	List<Function> getFunctionName();
	
}
