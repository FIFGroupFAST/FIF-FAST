package co.id.fifgroup.ssoa.dao;


import co.id.fifgroup.ssoa.domain.AssetTransferBast;
import co.id.fifgroup.ssoa.domain.AssetTransferDetail;
import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample;
import co.id.fifgroup.ssoa.domain.AssetTransferExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AssetTransferDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int countByExample(AssetTransferDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int deleteByPrimaryKey(Long id);
    
    int deleteByHeaderKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int insert(AssetTransferDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int insertSelective(AssetTransferDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetTransferDetailDTO> selectByExampleWithRowbounds(AssetTransferExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetTransferDetailDTO> selectByExample(AssetTransferDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    AssetTransferDetailDTO selectByPrimaryKey(Long id);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetTransferDetailDTO> selectByHeaderKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByExampleSelective(@Param("record") AssetTransferDetail record, @Param("example") AssetTransferDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByExample(@Param("record") AssetTransferDetail record, @Param("example") AssetTransferDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByPrimaryKeySelective(AssetTransferDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByPrimaryKey(AssetTransferDetailDTO record);

	List<AssetTransferDetailDTO> selectDetailByHeaderId(Long id);
	
	AssetTransferDetailDTO getSOResultByAssetId(Long assetId);
	
	List<AssetTransferBast> selectDetailBastByHeaderId(Long id);

	int submitBast(AssetTransferBast record);
	
	int countAssetSODtlByCriteria(AssetTransferDetailExample example);

	List<AssetTransferDetailDTO> selectAssetSODtlByCriteria(AssetTransferDetailExample example, RowBounds rowBounds);
}