package co.id.fifgroup.workstructure.dao;

import co.id.fifgroup.workstructure.domain.GradeSet;
import co.id.fifgroup.workstructure.domain.GradeSetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GradeSetMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    int countByExample(GradeSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    int deleteByExample(GradeSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    int insert(GradeSet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    int insertSelective(GradeSet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    List<GradeSet> selectByExampleWithRowbounds(GradeSetExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    List<GradeSet> selectByExample(GradeSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    GradeSet selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    int updateByExampleSelective(@Param("record") GradeSet record, @Param("example") GradeSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    int updateByExample(@Param("record") GradeSet record, @Param("example") GradeSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    int updateByPrimaryKeySelective(GradeSet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    int updateByPrimaryKey(GradeSet record);
}