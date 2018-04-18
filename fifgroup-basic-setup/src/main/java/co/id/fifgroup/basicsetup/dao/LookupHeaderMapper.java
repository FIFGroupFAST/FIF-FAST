package co.id.fifgroup.basicsetup.dao;

import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.domain.LookupHeaderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface LookupHeaderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int countByExample(LookupHeaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int deleteByExample(LookupHeaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int deleteByPrimaryKey(Long lookupId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int insert(LookupHeader record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int insertSelective(LookupHeader record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    List<LookupHeader> selectByExampleWithRowbounds(LookupHeaderExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    List<LookupHeader> selectByExample(LookupHeaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    LookupHeader selectByPrimaryKey(Long lookupId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int updateByExampleSelective(@Param("record") LookupHeader record, @Param("example") LookupHeaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int updateByExample(@Param("record") LookupHeader record, @Param("example") LookupHeaderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int updateByPrimaryKeySelective(LookupHeader record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_LOOKUP_HDR
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int updateByPrimaryKey(LookupHeader record);
}