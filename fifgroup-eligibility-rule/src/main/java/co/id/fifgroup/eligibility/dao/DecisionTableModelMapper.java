package co.id.fifgroup.eligibility.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.eligibility.domain.DecisionTableModel;
import co.id.fifgroup.eligibility.domain.DecisionTableModelExample;

public interface DecisionTableModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int countByExample(DecisionTableModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int deleteByExample(DecisionTableModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int deleteByPrimaryKey(@Param("id") String id, @Param("versionNumber") Integer versionNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int insert(DecisionTableModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int insertSelective(DecisionTableModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    List<DecisionTableModel> selectByExample(DecisionTableModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    DecisionTableModel selectByPrimaryKey(@Param("id") String id, @Param("versionNumber") Integer versionNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int updateByExampleSelective(@Param("record") DecisionTableModel record, @Param("example") DecisionTableModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int updateByExample(@Param("record") DecisionTableModel record, @Param("example") DecisionTableModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int updateByPrimaryKeySelective(DecisionTableModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_MODELS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int updateByPrimaryKey(DecisionTableModel record);
}