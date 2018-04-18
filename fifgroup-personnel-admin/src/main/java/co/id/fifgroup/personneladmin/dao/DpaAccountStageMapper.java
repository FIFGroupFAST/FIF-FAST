package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.DpaAccountStage;
import co.id.fifgroup.personneladmin.domain.DpaAccountStageExample;

public interface DpaAccountStageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int countByExample(DpaAccountStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int deleteByExample(DpaAccountStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int insert(DpaAccountStage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int insertSelective(DpaAccountStage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    List<DpaAccountStage> selectByExampleWithRowbounds(DpaAccountStageExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    List<DpaAccountStage> selectByExample(DpaAccountStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int updateByExampleSelective(@Param("record") DpaAccountStage record, @Param("example") DpaAccountStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int updateByExample(@Param("record") DpaAccountStage record, @Param("example") DpaAccountStageExample example);
}