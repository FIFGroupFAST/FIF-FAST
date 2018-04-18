package co.id.fifgroup.ssoa.dao;

import co.id.fifgroup.ssoa.domain.AssetRegistrationDetail;
import co.id.fifgroup.ssoa.domain.AssetRegistrationDetailExample;
import co.id.fifgroup.ssoa.domain.AssetRegistrationExample;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDetailDTO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AssetRegistrationDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int countByExample(AssetRegistrationDetailExample example);

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
    int insert(AssetRegistrationDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int insertSelective(AssetRegistrationDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetRegistrationDetailDTO> selectByExampleWithRowbounds(AssetRegistrationExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetRegistrationDetailDTO> selectByExample(AssetRegistrationDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    AssetRegistrationDetailDTO selectByPrimaryKey(Long id);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetRegistrationDetailDTO> selectByHeaderKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByExampleSelective(@Param("record") AssetRegistrationDetail record, @Param("example") AssetRegistrationDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByExample(@Param("record") AssetRegistrationDetail record, @Param("example") AssetRegistrationDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByPrimaryKeySelective(AssetRegistrationDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByPrimaryKey(AssetRegistrationDetailDTO record);

	List<AssetRegistrationDetailDTO> selectDetailByHeaderId(Long id);
	
	AssetRegistrationDetailDTO getSOResultByAssetId(Long assetId);
	
	int countAssetSODtlByCriteria(AssetRegistrationDetailExample example);

	List<AssetRegistrationDetailDTO> selectAssetSODtlByCriteria(AssetRegistrationDetailExample example, RowBounds rowBounds);
}