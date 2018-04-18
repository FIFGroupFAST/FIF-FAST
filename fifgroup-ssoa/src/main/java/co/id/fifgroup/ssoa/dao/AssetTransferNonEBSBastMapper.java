package co.id.fifgroup.ssoa.dao;


import co.id.fifgroup.ssoa.domain.AssetTransferBast;
import co.id.fifgroup.ssoa.domain.AssetTransferBastExample;
import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AssetTransferNonEBSBastMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int countByExample(AssetTransferBastExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int insert(AssetTransferBast record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int insertSelective(AssetTransferBast record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetTransferBastDTO> selectByExampleWithRowbounds(AssetTransferBastExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetTransferBastDTO> selectByExample(AssetTransferBastExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    AssetTransferBastDTO selectByPrimaryKey(Long id);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    List<AssetTransferBastDTO> selectByHeaderKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByExampleSelective(@Param("record") AssetTransferBast record, @Param("example") AssetTransferBastExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByExample(@Param("record") AssetTransferBast record, @Param("example") AssetTransferBastExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByPrimaryKeySelective(AssetTransferBast record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    int updateByPrimaryKey(AssetTransferBast record);

}