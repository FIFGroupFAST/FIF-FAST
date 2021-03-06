package co.id.fifgroup.workstructure.dao;

import co.id.fifgroup.workstructure.domain.GradeInfo;
import co.id.fifgroup.workstructure.domain.GradeInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GradeInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    int countByExample(GradeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    int deleteByExample(GradeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    int deleteByPrimaryKey(@Param("versionId") Long versionId, @Param("infoId") Long infoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    int insert(GradeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    int insertSelective(GradeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    List<GradeInfo> selectByExampleWithRowbounds(GradeInfoExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    List<GradeInfo> selectByExample(GradeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    GradeInfo selectByPrimaryKey(@Param("versionId") Long versionId, @Param("infoId") Long infoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    int updateByExampleSelective(@Param("record") GradeInfo record, @Param("example") GradeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    int updateByExample(@Param("record") GradeInfo record, @Param("example") GradeInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    int updateByPrimaryKeySelective(GradeInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_GRADE_INFOS
     *
     * @mbggenerated Tue May 07 09:58:53 ICT 2013
     */
    int updateByPrimaryKey(GradeInfo record);
}