package co.id.fifgroup.basicsetup.dao;

import co.id.fifgroup.basicsetup.domain.CompanyOtherInfoValue;
import co.id.fifgroup.basicsetup.domain.CompanyOtherInfoValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CompanyOtherInfoValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    int countByExample(CompanyOtherInfoValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    int deleteByExample(CompanyOtherInfoValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    int deleteByPrimaryKey(@Param("otherInfoDtlId") Long otherInfoDtlId, @Param("companySeqNum") Long companySeqNum);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    int insert(CompanyOtherInfoValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    int insertSelective(CompanyOtherInfoValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    List<CompanyOtherInfoValue> selectByExampleWithRowbounds(CompanyOtherInfoValueExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    List<CompanyOtherInfoValue> selectByExample(CompanyOtherInfoValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    CompanyOtherInfoValue selectByPrimaryKey(@Param("otherInfoDtlId") Long otherInfoDtlId, @Param("companySeqNum") Long companySeqNum);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    int updateByExampleSelective(@Param("record") CompanyOtherInfoValue record, @Param("example") CompanyOtherInfoValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    int updateByExample(@Param("record") CompanyOtherInfoValue record, @Param("example") CompanyOtherInfoValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    int updateByPrimaryKeySelective(CompanyOtherInfoValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_COY_OTHER_INFO_VAL
     *
     * @mbggenerated Thu Mar 28 19:03:57 ICT 2013
     */
    int updateByPrimaryKey(CompanyOtherInfoValue record);
}