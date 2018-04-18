package co.id.fifgroup.workstructure.dao;

import co.id.fifgroup.workstructure.domain.OrganizationUploadStage;
import co.id.fifgroup.workstructure.domain.OrganizationUploadStageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrganizationUploadStageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_STG
     *
     * @mbggenerated Wed Jul 03 14:35:19 ICT 2013
     */
    int countByExample(OrganizationUploadStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_STG
     *
     * @mbggenerated Wed Jul 03 14:35:19 ICT 2013
     */
    int deleteByExample(OrganizationUploadStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_STG
     *
     * @mbggenerated Wed Jul 03 14:35:19 ICT 2013
     */
    int insert(OrganizationUploadStage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_STG
     *
     * @mbggenerated Wed Jul 03 14:35:19 ICT 2013
     */
    int insertSelective(OrganizationUploadStage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_STG
     *
     * @mbggenerated Wed Jul 03 14:35:19 ICT 2013
     */
    List<OrganizationUploadStage> selectByExampleWithRowbounds(OrganizationUploadStageExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_STG
     *
     * @mbggenerated Wed Jul 03 14:35:19 ICT 2013
     */
    List<OrganizationUploadStage> selectByExample(OrganizationUploadStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_STG
     *
     * @mbggenerated Wed Jul 03 14:35:19 ICT 2013
     */
    int updateByExampleSelective(@Param("record") OrganizationUploadStage record, @Param("example") OrganizationUploadStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_STG
     *
     * @mbggenerated Wed Jul 03 14:35:19 ICT 2013
     */
    int updateByExample(@Param("record") OrganizationUploadStage record, @Param("example") OrganizationUploadStageExample example);
}