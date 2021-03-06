package co.id.fifgroup.systemadmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.systemadmin.domain.GradeExclusion;
import co.id.fifgroup.systemadmin.domain.GradeExclusionExample;

public interface GradeExclusionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    int countByExample(GradeExclusionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    int insert(GradeExclusion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    int insertSelective(GradeExclusion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    List<GradeExclusion> selectByExample(GradeExclusionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    GradeExclusion selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    int updateByExampleSelective(@Param("record") GradeExclusion record, @Param("example") GradeExclusionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    int updateByExample(@Param("record") GradeExclusion record, @Param("example") GradeExclusionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    int updateByPrimaryKeySelective(GradeExclusion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    int updateByPrimaryKey(GradeExclusion record);
}