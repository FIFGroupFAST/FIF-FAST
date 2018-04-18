package co.id.fifgroup.ssoa.finder;

import co.id.fifgroup.ssoa.domain.AssetRegistration;
import co.id.fifgroup.ssoa.domain.AssetRegistrationExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.RetirementExample;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDTO;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDetailDTO;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.annotations.Param;

public interface AssetRegistrationFinder {
	
	List<AssetRegistrationDTO> selectByExample(AssetRegistrationExample example, RowBounds rowBounds);
	
	List<AssetRegistrationDTO> selectByExample(AssetRegistrationExample example);
	
	List<AssetRegistrationDetailDTO> selectDetailByHeaderKey(@Param("id") Long id);
	
	List<Assets> selectDetailByHeaderId(@Param("id") Long id);
	
	int countByExample(AssetRegistrationExample example);

	List<Assets> selectAssetByBranchId(@Param("example") Assets example, @Param("rowBounds")RowBounds rowBounds,@Param("taskGroupId") Long taskGroupId);
	
	int countAssetByBranchId(@Param("example")Assets example, @Param("taskGroupId") Long taskGroupId);
	
}
