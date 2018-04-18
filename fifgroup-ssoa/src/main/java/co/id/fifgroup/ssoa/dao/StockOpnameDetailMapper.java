package co.id.fifgroup.ssoa.dao;


import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.StockOpnameDetailExample;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StockOpnameDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int countByExample(StockOpnameDetailExample example);

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
    int insert(StockOpnameDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int insertSelective(StockOpnameDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<StockOpnameDetailDTO> selectByExampleWithRowbounds(StockOpnameDetailExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<StockOpnameDetailDTO> selectByExample(StockOpnameDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    StockOpnameDetailDTO selectByPrimaryKey(Long id);
    
    StockOpnameDetailDTO selectByHeaderIdAndAssetId(@Param("soId")Long soId, @Param("assetId")Long assetId);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<StockOpnameDetailDTO> selectByHeaderKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByExampleSelective(@Param("record") StockOpnameDetail record, @Param("example") StockOpnameDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByExample(@Param("record") StockOpnameDetail record, @Param("example") StockOpnameDetailExample example);
    
    int updateOpnameStatus(StockOpnameDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByPrimaryKeySelective(StockOpnameDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByPrimaryKey(@Param("record") StockOpnameDetailDTO record);
    
    List<StockOpnameDetailDTO> getSODtlIdByAssetId(Long assetId);
}