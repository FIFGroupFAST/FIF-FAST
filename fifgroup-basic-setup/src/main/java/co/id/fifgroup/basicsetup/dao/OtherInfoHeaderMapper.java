package co.id.fifgroup.basicsetup.dao;

import co.id.fifgroup.basicsetup.domain.OtherInfoHeader;
import co.id.fifgroup.basicsetup.domain.OtherInfoHeaderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OtherInfoHeaderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    int countByExample(OtherInfoHeaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    int deleteByPrimaryKey(Long otherInfoHdrId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    int insert(OtherInfoHeader record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    int insertSelective(OtherInfoHeader record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    List<OtherInfoHeader> selectByExampleWithRowbounds(OtherInfoHeaderExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    List<OtherInfoHeader> selectByExample(OtherInfoHeaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    OtherInfoHeader selectByPrimaryKey(Long otherInfoHdrId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    int updateByExampleSelective(@Param("record") OtherInfoHeader record, @Param("example") OtherInfoHeaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    int updateByExample(@Param("record") OtherInfoHeader record, @Param("example") OtherInfoHeaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    int updateByPrimaryKeySelective(OtherInfoHeader record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_OTHER_INFO_HDR
     *
     * @mbggenerated Tue Mar 19 19:20:31 ICT 2013
     */
    int updateByPrimaryKey(OtherInfoHeader record);
}