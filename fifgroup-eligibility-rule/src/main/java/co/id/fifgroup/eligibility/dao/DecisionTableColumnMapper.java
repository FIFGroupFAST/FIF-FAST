package co.id.fifgroup.eligibility.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.eligibility.domain.DecisionTableColumn;
import co.id.fifgroup.eligibility.domain.DecisionTableColumnExample;

public interface DecisionTableColumnMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int countByExample(DecisionTableColumnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int deleteByExample(DecisionTableColumnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int insert(DecisionTableColumn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int insertSelective(DecisionTableColumn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    List<DecisionTableColumn> selectByExample(DecisionTableColumnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    DecisionTableColumn selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int updateByExampleSelective(@Param("record") DecisionTableColumn record, @Param("example") DecisionTableColumnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int updateByExample(@Param("record") DecisionTableColumn record, @Param("example") DecisionTableColumnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int updateByPrimaryKeySelective(DecisionTableColumn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    int updateByPrimaryKey(DecisionTableColumn record);
}