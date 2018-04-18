package co.id.fifgroup.ssoa.finder;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.AssetLabeling;
import co.id.fifgroup.ssoa.domain.AssetLabelingExample;
import co.id.fifgroup.ssoa.dto.AssetLabelingDTO;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.annotations.Param;

public interface AssetLabelingFinder {
	
	List<AssetLabelingDTO> selectByExample(AssetLabelingExample example, RowBounds rowBounds);
	
	List<AssetLabelingDTO> selectByExample(AssetLabelingExample example);
	
	List<AssetLabelingDetailDTO> selectDetailByHeaderId(@Param("id") Long id);
	
	int countByExample(AssetLabelingExample example);
	
	public List<KeyValue> getLookupKeyValues(@Param("lookupName") String lookupName,@Param("key")String key);
}
