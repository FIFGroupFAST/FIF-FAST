package co.id.fifgroup.ssoa.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.ssoa.domain.AssetTransferBast;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Retirement;
import co.id.fifgroup.ssoa.domain.RetirementBast;
import co.id.fifgroup.ssoa.domain.RetirementDetail;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;

public interface RetirementNonEBSDetailMapper {
   

    int deleteByPrimaryKey(String id);

    int insert(RetirementDetailDTO record);

    RetirementDetail selectByPrimaryKey(Long id);
    RetirementDetailDTO getDetailIdForImages(@Param("id")Long id, @Param("assetId")Long assetId);
    
    
    List<RetirementDetailDTO> selectByHeaderKey(Long id);
    
    
    int updateByPrimaryKey(RetirementDetailDTO record);

    List<RetirementDetailDTO> selectAssetSODtlByCriteria(RetirementDetailExample example, RowBounds rowBounds);
    
    int countAssetSODtlByCriteria(RetirementDetailExample example);
    
    List<RetirementDetailDTO> selectAssetSODtl(RetirementDetailExample example, RowBounds rowBounds);
    
    int countAssetSODtl(RetirementDetailExample example);
    
    RetirementDetailDTO getSOResultByAssetId(Long assetId);
    
    int updateRvNumber(RetirementDTO record);
    }