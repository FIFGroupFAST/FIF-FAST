package co.id.fifgroup.ssoa.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.ssoa.domain.StockOpnameExample;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;

public interface StockOpnameFinder {
	
	List<StockOpnameDTO> selectByExample(StockOpnameExample example, RowBounds rowBounds);
	
	List<StockOpnameDTO> selectByExample(StockOpnameExample example);
	
	List<StockOpnameDetailDTO> selectDetailByHeaderId(@Param("id") Long id);
	
	int countByExample(StockOpnameExample example);
	
	
}
