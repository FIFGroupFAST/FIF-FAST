package co.id.fifgroup.ssoa.dao;


import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.AssetRegistration;
import co.id.fifgroup.ssoa.domain.AssetRegistrationExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDTO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AssetRegistrationMapper {

    int countByExample(AssetRegistrationExample example);

    int insert(AssetRegistration record);
    
    int insertSocAsset(AssetRegistrationDTO record);

    List<AssetRegistrationDTO> selectByExampleWithRowbounds(AssetRegistrationExample example, RowBounds rowBounds);

    List<AssetRegistrationDTO> selectByExample(AssetRegistrationExample example);

    AssetRegistration selectByPrimaryKey(Long id);
    
    AssetRegistration getBranchCode (Long id);

    int updateByExample(@Param("record") AssetRegistration record, @Param("example") AssetRegistrationExample example);

    AssetRegistrationDTO selectByAvmTrxId(String avmTrxId);
    
    int updateByPrimaryKey(AssetRegistration record);
    
    int updateAssetIdSOUnregAsset(AssetRegistrationDTO record);

	List<Assets> selectByBranchId(Assets assets, RowBounds rowBounds);
	
	List<Assets> selectByBranchId(Long id);
	
	AssetRegistrationDTO getLastRegistrationNo();
	
	public List<KeyValue> getLookupKeyValues(@Param("lookupName") String lookupName,@Param("key")String key);

	List<AssetRegistrationDTO> selectUnregAssetByBranchId(@Param("branchId") Long branchId,
		@Param("companyId") Long companyId, @Param("registrationId") Long registrationId);
	
}