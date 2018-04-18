package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.OtherInfo;
import co.id.fifgroup.personneladmin.domain.OtherInfoExample;

public interface OtherInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    int countByExample(OtherInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    int deleteByExample(OtherInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    int deleteByPrimaryKey(Long otherInfoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    int insert(OtherInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    int insertSelective(OtherInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    List<OtherInfo> selectByExampleWithRowbounds(OtherInfoExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    List<OtherInfo> selectByExample(OtherInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    OtherInfo selectByPrimaryKey(Long otherInfoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    int updateByExampleSelective(@Param("record") OtherInfo record, @Param("example") OtherInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    int updateByExample(@Param("record") OtherInfo record, @Param("example") OtherInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    int updateByPrimaryKeySelective(OtherInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_OTHER_INFO
     *
     * @mbggenerated Mon Jun 03 20:09:04 ICT 2013
     */
    int updateByPrimaryKey(OtherInfo record);
}