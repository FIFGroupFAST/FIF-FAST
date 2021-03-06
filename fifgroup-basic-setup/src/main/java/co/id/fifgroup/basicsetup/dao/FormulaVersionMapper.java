package co.id.fifgroup.basicsetup.dao;

import co.id.fifgroup.basicsetup.domain.FormulaVersion;
import co.id.fifgroup.basicsetup.domain.FormulaVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface FormulaVersionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int countByExample(FormulaVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int deleteByExample(FormulaVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int deleteByPrimaryKey(Long versionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int insert(FormulaVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int insertSelective(FormulaVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    List<FormulaVersion> selectByExampleWithBLOBsWithRowbounds(FormulaVersionExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    List<FormulaVersion> selectByExampleWithBLOBs(FormulaVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    List<FormulaVersion> selectByExampleWithRowbounds(FormulaVersionExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    List<FormulaVersion> selectByExample(FormulaVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    FormulaVersion selectByPrimaryKey(Long versionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int updateByExampleSelective(@Param("record") FormulaVersion record, @Param("example") FormulaVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int updateByExampleWithBLOBs(@Param("record") FormulaVersion record, @Param("example") FormulaVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int updateByExample(@Param("record") FormulaVersion record, @Param("example") FormulaVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int updateByPrimaryKeySelective(FormulaVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int updateByPrimaryKeyWithBLOBs(FormulaVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    int updateByPrimaryKey(FormulaVersion record);
}