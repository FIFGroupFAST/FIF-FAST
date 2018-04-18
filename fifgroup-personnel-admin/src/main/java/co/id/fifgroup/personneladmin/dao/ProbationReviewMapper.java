package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.ProbationReview;
import co.id.fifgroup.personneladmin.domain.ProbationReviewExample;

public interface ProbationReviewMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    int countByExample(ProbationReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    int deleteByExample(ProbationReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    int deleteByPrimaryKey(Long probationReviewId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    int insert(ProbationReview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    int insertSelective(ProbationReview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    List<ProbationReview> selectByExampleWithRowbounds(ProbationReviewExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    List<ProbationReview> selectByExample(ProbationReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    ProbationReview selectByPrimaryKey(Long probationReviewId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    int updateByExampleSelective(@Param("record") ProbationReview record, @Param("example") ProbationReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    int updateByExample(@Param("record") ProbationReview record, @Param("example") ProbationReviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    int updateByPrimaryKeySelective(ProbationReview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PROBATION_REVIEWS
     *
     * @mbggenerated Tue Jul 09 14:59:17 ICT 2013
     */
    int updateByPrimaryKey(ProbationReview record);
}