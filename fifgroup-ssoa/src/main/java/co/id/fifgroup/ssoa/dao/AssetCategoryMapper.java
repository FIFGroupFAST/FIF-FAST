package co.id.fifgroup.ssoa.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.ssoa.domain.AssetCategory;
import co.id.fifgroup.ssoa.domain.AssetCategoryExample;

public interface AssetCategoryMapper {
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    AssetCategory selectByPrimaryKey(@Param("categoryId")Long id);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetCategory> selectAssetCategory();
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetCategory> selectByExampleWithRowbounds(AssetCategoryExample example, RowBounds rowBounds);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int countByExample(AssetCategoryExample example);
    

    
    
}