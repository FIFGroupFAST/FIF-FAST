package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.PtkpStage;
import co.id.fifgroup.personneladmin.domain.PtkpStageExample;

public interface PtkpStageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PTKP_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int countByExample(PtkpStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PTKP_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int deleteByExample(PtkpStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PTKP_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int insert(PtkpStage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PTKP_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int insertSelective(PtkpStage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PTKP_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    List<PtkpStage> selectByExampleWithRowbounds(PtkpStageExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PTKP_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    List<PtkpStage> selectByExample(PtkpStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PTKP_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int updateByExampleSelective(@Param("record") PtkpStage record, @Param("example") PtkpStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PTKP_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    int updateByExample(@Param("record") PtkpStage record, @Param("example") PtkpStageExample example);
}