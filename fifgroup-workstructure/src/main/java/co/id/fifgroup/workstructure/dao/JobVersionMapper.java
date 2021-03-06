package co.id.fifgroup.workstructure.dao;

import co.id.fifgroup.workstructure.domain.JobVersion;
import co.id.fifgroup.workstructure.domain.JobVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JobVersionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    int countByExample(JobVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    int deleteByPrimaryKey(Long versionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    int insert(JobVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    int insertSelective(JobVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    List<JobVersion> selectByExampleWithRowbounds(JobVersionExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    List<JobVersion> selectByExample(JobVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    JobVersion selectByPrimaryKey(Long versionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    int updateByExampleSelective(@Param("record") JobVersion record, @Param("example") JobVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    int updateByExample(@Param("record") JobVersion record, @Param("example") JobVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    int updateByPrimaryKeySelective(JobVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_JOB_VERSIONS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    int updateByPrimaryKey(JobVersion record);
}