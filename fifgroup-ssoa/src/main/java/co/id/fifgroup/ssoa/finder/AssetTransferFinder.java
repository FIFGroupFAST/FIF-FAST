package co.id.fifgroup.ssoa.finder;

import co.id.fifgroup.ssoa.domain.AssetTransfer;
import co.id.fifgroup.ssoa.domain.AssetTransferExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.RetirementExample;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.annotations.Param;

public interface AssetTransferFinder {
	
	List<AssetTransferDTO> selectByExample(AssetTransferExample example, RowBounds rowBounds);
	
	List<AssetTransferDTO> selectByExample(AssetTransferExample example);
	
	List<AssetTransferDetailDTO> selectDetailByHeaderKey(@Param("id") Long id);
	
	List<Assets> selectDetailByHeaderId(@Param("id") Long id);
	
	int countByExample(AssetTransferExample example);

	List<Assets> selectAssetByBranchId(@Param("example") Assets example, @Param("rowBounds")RowBounds rowBounds,@Param("taskGroupId") Long taskGroupId);
	
	int countAssetByBranchId(@Param("example")Assets example, @Param("taskGroupId") Long taskGroupId);
	
}
