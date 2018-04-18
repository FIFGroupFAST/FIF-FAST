package co.id.fifgroup.systemworkflow.dao;

import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApprovalModelMappingDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int countByExample(ApprovalModelMappingDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int deleteByPrimaryKey(Long mappingDetailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int insert(ApprovalModelMappingDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int insertSelective(ApprovalModelMappingDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    List<ApprovalModelMappingDetail> selectByExampleWithBLOBsWithRowbounds(ApprovalModelMappingDetailExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    List<ApprovalModelMappingDetail> selectByExampleWithBLOBs(ApprovalModelMappingDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    List<ApprovalModelMappingDetail> selectByExampleWithRowbounds(ApprovalModelMappingDetailExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    List<ApprovalModelMappingDetail> selectByExample(ApprovalModelMappingDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    ApprovalModelMappingDetail selectByPrimaryKey(Long mappingDetailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByExampleSelective(@Param("record") ApprovalModelMappingDetail record, @Param("example") ApprovalModelMappingDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByExampleWithBLOBs(@Param("record") ApprovalModelMappingDetail record, @Param("example") ApprovalModelMappingDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByExample(@Param("record") ApprovalModelMappingDetail record, @Param("example") ApprovalModelMappingDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByPrimaryKeySelective(ApprovalModelMappingDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByPrimaryKeyWithBLOBs(ApprovalModelMappingDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByPrimaryKey(ApprovalModelMappingDetail record);
}