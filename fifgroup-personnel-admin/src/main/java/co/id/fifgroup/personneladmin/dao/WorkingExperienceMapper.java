package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.WorkingExperience;
import co.id.fifgroup.personneladmin.domain.WorkingExperienceExample;

public interface WorkingExperienceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int countByExample(WorkingExperienceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByExample(WorkingExperienceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByPrimaryKey(Long experienceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insert(WorkingExperience record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insertSelective(WorkingExperience record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<WorkingExperience> selectByExampleWithRowbounds(WorkingExperienceExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<WorkingExperience> selectByExample(WorkingExperienceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    WorkingExperience selectByPrimaryKey(Long experienceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExampleSelective(@Param("record") WorkingExperience record, @Param("example") WorkingExperienceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExample(@Param("record") WorkingExperience record, @Param("example") WorkingExperienceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKeySelective(WorkingExperience record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKey(WorkingExperience record);
}