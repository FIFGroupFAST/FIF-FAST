package co.id.fifgroup.ssoa.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.ssoa.dto.AssetDTO;

public interface EbsToSsoaMapper {
	
public String ebsToSsoaSynchronizing();
	
	public List<AssetDTO> getViewAssetFromEbsDaily();
	
	public List<AssetDTO> getAssetbyEbsId(@Param("ebsAssetId") Long ebsAssetId);
	
	public void insert(AssetDTO record);
	
	public int updateByEbsId(AssetDTO record);
	
	public int updateLastSyncDate(@Param("paramDate") Timestamp paramDate,
			@Param("formatDate") String formatDate,
			@Param("code") String code );
	
	public int deleteSocEbsBookTypes();
	public int deleteSocEbsCategories();
	public int insertSocEbsBookTypes();
	public int insertSocEbsCategories();
}