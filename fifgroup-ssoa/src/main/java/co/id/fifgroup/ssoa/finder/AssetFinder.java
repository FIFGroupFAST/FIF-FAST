package co.id.fifgroup.ssoa.finder;

import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.StockOpnameExample;
import co.id.fifgroup.ssoa.dto.AssetDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;


public interface AssetFinder {
	
	List<AssetDTO> selectByExample(@Param("example")Assets example, @Param("rowBounds")RowBounds rowBounds,@Param("taskGroupId") Long taskGroupId);
	
	List<AssetDTO> selectDetailByHeaderId(@Param("id") Long id);
	
	int countByExample(@Param("example")Assets example,@Param("taskGroupId") Long taskGroupId);
	
	List<AssetDTO> selectAllAsset(AssetsExample example);
	
}
