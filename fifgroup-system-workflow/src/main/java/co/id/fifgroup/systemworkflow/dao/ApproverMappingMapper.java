package co.id.fifgroup.systemworkflow.dao;

import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.domain.ApproverMappingExample;
import java.util.List;
import java.util.UUID;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApproverMappingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    int countByExample(ApproverMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    int deleteByPrimaryKey(@Param("approverId") UUID approverId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    int insert(ApproverMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    int insertSelective(ApproverMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    List<ApproverMapping> selectByExampleWithRowbounds(ApproverMappingExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    List<ApproverMapping> selectByExample(ApproverMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    ApproverMapping selectByPrimaryKey(@Param("approverId") UUID approverId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    int updateByExampleSelective(@Param("record") ApproverMapping record, @Param("example") ApproverMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    int updateByExample(@Param("record") ApproverMapping record, @Param("example") ApproverMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    int updateByPrimaryKeySelective(ApproverMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_APPROVER_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    int updateByPrimaryKey(ApproverMapping record);
}