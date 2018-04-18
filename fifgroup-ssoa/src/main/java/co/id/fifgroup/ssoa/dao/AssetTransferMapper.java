package co.id.fifgroup.ssoa.dao;


import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.AssetTransfer;
import co.id.fifgroup.ssoa.domain.AssetTransferExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AssetTransferMapper {

    int countByExample(AssetTransferExample example);

    int insert(AssetTransfer record);

    List<AssetTransferDTO> selectByExampleWithRowbounds(AssetTransferExample example, RowBounds rowBounds);

    List<AssetTransferDTO> selectByExample(AssetTransferExample example);

    AssetTransfer selectByPrimaryKey(Long id);
    
    AssetTransfer getBranchCode (Long id);

    int updateByExample(@Param("record") AssetTransfer record, @Param("example") AssetTransferExample example);

    AssetTransferDTO selectByAvmTrxId(String avmTrxId);
    
    int updateByPrimaryKey(AssetTransfer record);
    
    int updateBASTByPrimaryKey(AssetTransfer record);

	List<Assets> selectByBranchId(Assets assets, RowBounds rowBounds);
	
	List<Assets> selectByBranchId(Long id);
	
	AssetTransferDTO getLastRequestNo();
	
	AssetTransferDTO getLastBastDocNo();
	
	AssetTransferDTO getLocationIdForEBS(AssetTransferDTO assetTransferDto);
	
    AssetTransferDTO getDataAccount(AssetTransferDTO assetTransferDTO);
    
    AssetTransferDTO getExpenseId(AssetTransferDTO assetTransferDTO);

	public List<KeyValue> getLookupKeyValues(@Param("lookupName") String lookupName,@Param("key")String key);
	
	public void insertAssetTransferToEbs(AssetTransferDTO assetTransferDTO);
	
	public void insertToGLCombinationCode(AssetTransferDTO assetTransferDTO);
	
}