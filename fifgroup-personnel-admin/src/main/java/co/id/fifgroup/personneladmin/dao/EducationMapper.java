package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.Education;
import co.id.fifgroup.personneladmin.domain.EducationExample;

public interface EducationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int countByExample(EducationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByExample(EducationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByPrimaryKey(Long educationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insert(Education record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insertSelective(Education record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Education> selectByExampleWithRowbounds(EducationExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Education> selectByExample(EducationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    Education selectByPrimaryKey(Long educationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExampleSelective(@Param("record") Education record, @Param("example") EducationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExample(@Param("record") Education record, @Param("example") EducationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKeySelective(Education record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKey(Education record);
}